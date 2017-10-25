package com.github.slugify;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;

public class Slugify {
	private static final String BUILTIN_REPLACEMENTS_FILENAME = "replacements.properties";
	private static final Properties REPLACEMENTS = new Properties();

	private final static String ASCII = "Bulgarian-Latin/BGN; Any-Latin; Latin-ASCII; [^\\p{Print}] Remove; ['\"] Remove; Any-Lower";
	private final static String EMPTY = "";

	private final static Pattern PATTERN_NORMALIZE_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
	private final static Pattern PATTERN_NORMALIZE_HYPHEN_SEPARATOR = Pattern.compile("[\\W\\s+]+");
	private final static Pattern PATTERN_NORMALIZE_UNDERSCORE_SEPARATOR = Pattern.compile("[[^a-zA-Z0-9\\-]\\s+]+");
	private final static Pattern PATTERN_NORMALIZE_TRIM_DASH = Pattern.compile("^-|-$");

	private static final Transliterator TRANSLITERATOR = Transliterator.getInstance(ASCII);

	private final Map<String, String> customReplacements = new HashMap<String, String>();
	private final Map<Character, String> builtinReplacements = new HashMap<Character, String>();

	private boolean transliterator = false;
	private boolean underscoreSeparator = false;
	private boolean lowerCase = true;

	@Deprecated
	public Slugify(boolean lowerCase) {
		this();

		withLowerCase(lowerCase);
	}

	public Slugify() {
		loadReplacements(BUILTIN_REPLACEMENTS_FILENAME);
		createPatternCache();
	}

	public Slugify withCustomReplacement(final String from, final String to) {
		customReplacements.put(from, to);
		return this;
	}

	public Slugify withCustomReplacements(final Map<String, String> customReplacements) {
		this.customReplacements.putAll(customReplacements);
		return this;
	}

	public Slugify withUnderscoreSeparator(final boolean underscoreSeparator) {
		this.underscoreSeparator = underscoreSeparator;
		return this;
	}

	public Slugify withTransliterator(final boolean transliterator) {
		this.transliterator = transliterator;
		return this;
	}

	public Slugify withLowerCase(final boolean lowerCase) {
		this.lowerCase = lowerCase;
		return this;
	}

	public String slugify(final String text) {
		String input = text;
		if (isNullOrBlank(input)) {
			return EMPTY;
		}

		input = input.trim();
		input = customReplacements(input);
		input = builtInReplacements(input);
		
		if (transliterator) {
			input = transliterate(input);
		} else {
			input = normalize(input);
		}

		if (lowerCase) {
			input = input.toLowerCase();
		}

		return input;
	}

	public Map<String, String> getCustomReplacements() {
		return customReplacements;
	}

	private String customReplacements(String input) {
		Map<String, String> customReplacements = getCustomReplacements();
		for (Entry<String, String> entry : customReplacements.entrySet()) {
			input = input.replace(entry.getKey(), entry.getValue());
		}

		return input;
	}

    private String builtInReplacements(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char s : input.toCharArray()) {
            if (builtinReplacements.containsKey(s)) {
                stringBuilder.append(builtinReplacements.get(s));
            } else {
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }

	private Slugify loadReplacements(final String resourceFileName) {
		if (!REPLACEMENTS.isEmpty()) {
			return this;
		}

		try {
			final InputStream replacementsStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
			REPLACEMENTS.load(replacementsStream);
			replacementsStream.close();
			return this;
		} catch (Exception e) {
			throw new RuntimeException(String.format("Resource '%s' not loaded!", resourceFileName), e);
		}
	}

    private void createPatternCache() {
        if (!builtinReplacements.isEmpty()) {
            return;
        }
        for (Entry<Object, Object> e : REPLACEMENTS.entrySet()) {
            if (e.getKey().toString().length() > 1) {
                throw new IllegalArgumentException("Builtin replacements can only be characters");
            }
            builtinReplacements.put(e.getKey().toString().charAt(0), e.getValue().toString());
        }
    }

	private static boolean isNullOrBlank(final String string) {
		return string == null || string.trim().isEmpty();
	}

	private String transliterate(final String input) {
		String text = TRANSLITERATOR.transliterate(input);
		text = matchAndReplace(text);
		return text;
	}

	private String normalize(final String input) {
		String text = Normalizer.normalize(input, Normalizer.Form.NFKD);
		text = matchAndReplace(text);
		return text;
	}

	private String matchAndReplace(final String input) {
		String text = PATTERN_NORMALIZE_NON_ASCII.matcher(input).replaceAll(EMPTY);
		text = underscoreSeparator ? PATTERN_NORMALIZE_UNDERSCORE_SEPARATOR.matcher(text).replaceAll("_") :
				PATTERN_NORMALIZE_HYPHEN_SEPARATOR.matcher(text).replaceAll("-");
		text = PATTERN_NORMALIZE_TRIM_DASH.matcher(text).replaceAll(EMPTY);

		return text;
	}
}
