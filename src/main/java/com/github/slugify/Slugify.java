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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for generating speaking URLs.
 *
 * @author Danny Trunk
 * @since 1.0
 */
public final class Slugify {
  /* default */ static final String BUNDLE_BASE_NAME = "slugify";

  private static final Logger LOGGER = LoggerFactory.getLogger(Slugify.class);

  private static final String TRANSLITERATOR_ID =
      "Cyrillic-Latin; Any-Latin; Latin-ASCII; [^\\p{Print}] Remove; ['\"] Remove";
  private static final String EMPTY = "";
  private static final String UNDERSCORE = "_";
  private static final String HYPHEN = "-";

  private static final Pattern PATTERN_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
  private static final Pattern PATTERN_HYPHEN_SEPARATOR = Pattern.compile("\\W+");
  private static final Pattern PATTERN_UNDERSCORE_SEPARATOR =
      Pattern.compile("[^a-zA-Z0-9-]+");
  private static final Pattern PATTERN_TRIM_DASH = Pattern.compile("(^-)|(-$)");
  private static final Pattern PATTERN_TRIM_UNDERSCORE = Pattern.compile("(^_)|(_$)");

  private final Transliterator transliterator;

  private final boolean underscoreSeparator;
  private final boolean lowerCase;

  private final Locale locale;

  private final Map<String, String> customReplacements;
  private final Map<String, String> replacements;

  /**
   * Sole constructor only used by the builder class.
   *
   * @param transliterator Sets whether to use
   *                       {@link com.ibm.icu.text.Transliterator Transliterator}-based
   *                       transliteration via ICU4J instead of normalization.
   * @param underscoreSeparator Sets whether to use underscores instead of hyphens as the
   *                            word separator.
   * @param lowerCase Sets whether to convert the slug to lower case.
   * @param locale Sets the {@link java.util.Locale Locale} used for built-in character
   *               replacements and lower-case conversion.
   * @param customReplacements Sets custom character replacements applied before built-in
   *                           locale replacements.
   */
  @Builder
  private Slugify(final Boolean transliterator, final Boolean underscoreSeparator,
                 final Boolean lowerCase, final Locale locale,
                 @Singular final Map<String, String> customReplacements) {
    this.transliterator = Boolean.TRUE.equals(transliterator)
        ? Transliterator.getInstance(TRANSLITERATOR_ID) : null;
    this.underscoreSeparator = Optional.ofNullable(underscoreSeparator).orElse(false);
    this.lowerCase = Optional.ofNullable(lowerCase).orElse(true);

    this.locale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);

    this.customReplacements = customReplacements;

    Map<String, String> builtinReplacements = null;
    try (InputStream resourceBundleInputStream = Slugify.class
        .getResourceAsStream("/" + BUNDLE_BASE_NAME + "_" + this.locale.getLanguage()
            + ".properties")) {
      if (resourceBundleInputStream != null) {
        final ResourceBundle replacementsBundle =
            new PropertyResourceBundle(resourceBundleInputStream);
        builtinReplacements = replacementsBundle.keySet().stream()
            .collect(Collectors.toMap(Function.identity(), replacementsBundle::getString));
      }
    } catch (IOException e) {
      LOGGER.atError()
          .addArgument(this.locale::getLanguage)
          .setCause(e)
          .log("Failed to load language bundle for locale: {}");
    }

    this.replacements = Optional.ofNullable(builtinReplacements).orElseGet(Collections::emptyMap);
  }

  /**
   * Creates a slug from the specified text.
   *
   * @param text Text to create a slug from.
   * @return A string representing the slug, or an empty string if {@code text} is
   *         {@code null}, empty, or blank.
   */
  public String slugify(final String text) {
    return Optional.ofNullable(text)
        // remove leading and trailing whitespaces
        .map(String::trim)
        // run subsequent calls only if string is not empty
        .filter(Predicate.not(String::isEmpty))
        // replace all custom replacements
        .map(str -> replaceAll(str, customReplacements))
        // replace all built-in replacements
        .map(str -> replaceAll(str, replacements))
        // transliterate or normalize
        .map(str -> transliterator != null ? transliterate(str) : normalize(str))
        // remove all remaining non ascii chars
        .map(str -> PATTERN_NON_ASCII.matcher(str).replaceAll(EMPTY))
        // replace remaining chars matching a pattern with underscore/hyphen
        .map(str -> underscoreSeparator
            ? PATTERN_UNDERSCORE_SEPARATOR.matcher(str).replaceAll(UNDERSCORE)
            : PATTERN_HYPHEN_SEPARATOR.matcher(str).replaceAll(HYPHEN))
        // remove leading and trailing separator chars
        .map(str -> underscoreSeparator
            ? PATTERN_TRIM_UNDERSCORE.matcher(str).replaceAll(EMPTY)
            : PATTERN_TRIM_DASH.matcher(str).replaceAll(EMPTY))
        // convert to lower case if needed
        .map(str -> lowerCase ? str.toLowerCase(locale) : str)
        // return empty string if input is null or empty
        .orElse(EMPTY);
  }

  private String replaceAll(final String input, final Map<String, String> replacements) {
    String result = input;
    for (final Map.Entry<String, String> entry : replacements.entrySet()) {
      result = result.replace(entry.getKey(), entry.getValue());
    }
    return result;
  }

  private String transliterate(final String input) {
    return transliterator.transliterate(input);
  }

  private String normalize(final String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFKD);
  }
}
