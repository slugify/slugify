# Slugify
[![GitHub license](https://img.shields.io/github/license/slugify/slugify.svg)](https://github.com/slugify/slugify/blob/master/LICENSE)
[![javadoc](https://javadoc.io/badge2/com.github.slugify/slugify/javadoc.svg)](https://javadoc.io/doc/com.github.slugify/slugify)

## Description
Small utility library for generating speaking URLs.

## Usage Examples

### Basic
```java
final Slugify slg = Slugify.builder().build();
final String result = slg.slugify("Hello, world!");
// result: hello-world
```

### Underscore Separator
```java
final Slugify slg = Slugify.builder().underscoreSeparator(true).build();
final String result = slg.slugify("Hello, world!");
// result: hello_world
```

### Case Sensitive
```java
final Slugify slg = Slugify.builder().lowerCase(false).build();
final String result = slg.slugify("Hello, world!");
// result: Hello-world
```

### Specifying a Locale
```java
final Slugify slg = Slugify.builder().locale(Locale.GERMAN).build();
final String result = slg.slugify("ä");
// result: ae
```

### Custom Replacements
```java
final Slugify slg = Slugify.builder()
    // provided as a map
    .customReplacements(Map.of("Foo", "Hello", "bar", "world"))
    // provided as single key-value
    .customReplacement("Foo", "Hello")
    .customReplacement("bar", "world")
    .build();

final String result = slg.slugify("Foo, bar!");
// result: hello-world
```

### Transliteration

#### Requirements

###### Gradle 6+
For Gradle 6+ users there's a feature variant which can be used as follows:
```groovy
capabilities {
    requireCapability('com.github.slugify:slugify-transliterator')
}
```
For more information about feature variants please check the section [Modeling feature variants and optional dependencies of gradle's user guide](https://docs.gradle.org/current/userguide/feature_variants.html).

###### Other
Manually add the optional dependency `com.ibm.icu:icu4j` to your project.

#### Usage Example
```java
final Slugify slg = Slugify.builder().transliterator(true).build();
final String result = slg.slugify("Б");
// result: b
```
