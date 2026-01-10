package com.github.slugify;

import com.ibm.icu.text.Transliterator;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Singular;

/**
 * Class for generating speaking URLs.
 *
 * @author Danny Trunk
 * @since 1.0
 */
public class Slugify {
  protected static final String BUNDLE_BASE_NAME = "slugify";

  private static final String ASCII =
      "Cyrillic-Latin; Any-Latin; Latin-ASCII; [^\\p{Print}] Remove; ['\"] Remove";
  private static final String EMPTY = "";
  private static final String UNDERSCORE = "_";
  private static final String HYPHEN = "-";

  private static final Pattern PATTERN_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
  private static final Pattern PATTERN_HYPHEN_SEPARATOR = Pattern.compile("\\W+");
  private static final Pattern PATTERN_UNDERSCORE_SEPARATOR =
      Pattern.compile("[^a-zA-Z0-9\\-]+");
  private static final Pattern PATTERN_TRIM_DASH = Pattern.compile("(^-)|(-$)");

  private final boolean transliterator;
  private final boolean underscoreSeparator;
  private final boolean lowerCase;

  private final Locale locale;

  private final Map<String, String> customReplacements;
  private final Map<String, String> replacements;

  /**
   * Sole constructor only used by the builder class.
   *
   * @param transliterator Sets the transliterator property which is a boolean to determine whether
   *                       slugs should be transliterated instead of normalized.
   * @param underscoreSeparator Sets the underscoreSeparator property which is a boolean to
   *                            determine whether slugs should contain underscores instead of
   *                            hyphens as separators.
   * @param lowerCase Sets the lowerCase property which is a boolean to determine whether slugs
   *                  should be converted to lower case.
   * @param locale Sets the locale property which is a {@link java.util.Locale Locale} to determine
   *               which locale should be used to generate slugs.
   * @param customReplacements Sets the customReplacements property which is a
   *                           {@link java.util.Map Map} to determine which custom replacements
   *                           should be applied.
   */
  @Builder
  private Slugify(final Boolean transliterator, final Boolean underscoreSeparator,
                 final Boolean lowerCase, final Locale locale,
                 @Singular final Map<String, String> customReplacements) {
    this.transliterator = Optional.ofNullable(transliterator).orElse(false);
    this.underscoreSeparator = Optional.ofNullable(underscoreSeparator).orElse(false);
    this.lowerCase = Optional.ofNullable(lowerCase).orElse(true);

    this.locale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);

    this.customReplacements = Optional.ofNullable(customReplacements)
        .orElseGet(Collections::emptyMap);

    Map<String, String> builtinReplacements = null;
    try (InputStream resourceBundleInputStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(BUNDLE_BASE_NAME + "_" + this.locale.getLanguage() + ".properties")) {
      if (resourceBundleInputStream != null) {
        final ResourceBundle replacementsBundle =
            new PropertyResourceBundle(resourceBundleInputStream);
        builtinReplacements = replacementsBundle.keySet().stream()
            .collect(Collectors.toMap(Function.identity(), replacementsBundle::getString));
      }
    } catch (IOException ignored) {
      // Ignored since no language specific bundle file exists.
    }

    this.replacements = Optional.ofNullable(builtinReplacements).orElseGet(Collections::emptyMap);
  }

  /**
   * Creates a slug from the specified text.
   *
   * @param text Text to create a slug from.
   * @return A string representing the slug.
   */
  public String slugify(final String text) {
    return Optional.ofNullable(text)
        // remove leading and trailing whitespaces
        .map(String::trim)
        // run subsequent calls only if string is not empty
        .filter(Predicate.not(EMPTY::equals))
        // replace all custom replacements
        .map(str -> replaceAll(str, customReplacements))
        // replace all built-in replacements
        .map(str -> replaceAll(str, replacements))
        // transliterate or normalize
        .map(str -> transliterator ? transliterate(str) : normalize(str))
        // remove all remaining non ascii chars
        .map(str -> PATTERN_NON_ASCII.matcher(str).replaceAll(EMPTY))
        // replace remaining chars matching a pattern with underscore/hyphen
        .map(str -> underscoreSeparator
            ? PATTERN_UNDERSCORE_SEPARATOR.matcher(str).replaceAll(UNDERSCORE)
            : PATTERN_HYPHEN_SEPARATOR.matcher(str).replaceAll(HYPHEN))
        // remove leading and trailing dashes
        .map(str -> PATTERN_TRIM_DASH.matcher(str).replaceAll(EMPTY))
        // convert to lower case if needed
        .map(str -> lowerCase ? str.toLowerCase(locale) : str)
        // return empty string if input is null or empty
        .orElse(EMPTY);
  }

  private String replaceAll(final String input, final Map<String, String> replacements) {
    return replacements.keySet().stream()
        .map(key -> (Function<String, String>) str -> str.replace(key, replacements.get(key)))
        .reduce(Function.identity(), Function::andThen)
        .apply(input);
  }

  private String transliterate(final String input) {
    return Transliterator.getInstance(ASCII).transliterate(input);
  }

  private String normalize(final String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFKD);
  }
}
