# Slugify
[![GitHub license](https://img.shields.io/github/license/slugify/slugify.svg)](https://github.com/slugify/slugify/blob/master/LICENSE)
[![Check](https://github.com/slugify/slugify/actions/workflows/gradle-check.yml/badge.svg)](https://github.com/slugify/slugify/actions/workflows/gradle-check.yml)
[![javadoc](https://javadoc.io/badge2/com.github.slugify/slugify/javadoc.svg)](https://javadoc.io/doc/com.github.slugify/slugify)

## Description
Small utility library for generating speaking URLs.

## Installation

Slugify is published in the [Maven Central Repository](https://central.sonatype.com/artifact/com.github.slugify/slugify).

### Maven
```xml
<dependency>
    <groupId>com.github.slugify</groupId>
    <artifactId>slugify</artifactId>
    <version>4.0.1</version>
</dependency>
```

### Gradle (Groovy)
```groovy
implementation 'com.github.slugify:slugify:4.0.1'
```

### Gradle (Kotlin DSL)
```kotlin
implementation("com.github.slugify:slugify:4.0.1")
```

> Check [Maven Central](https://central.sonatype.com/artifact/com.github.slugify/slugify) for the latest version.

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

### Preserve Case
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

Built-in character replacements exist for a subset of locales (see `src/main/resources/slugify_*.properties`).
For all other locales, no built-in replacements are applied — custom replacements or transliteration
can be used instead.

### Custom Replacements
```java
// provided as single key-value pairs
final Slugify slg = Slugify.builder()
    .customReplacement("Foo", "Hello")
    .customReplacement("bar", "world")
    .build();

// alternatively provided as a map
final Slugify slg = Slugify.builder()
    .customReplacements(Map.of("Foo", "Hello", "bar", "world"))
    .build();

final String result = slg.slugify("Foo, bar!");
// result: hello-world
```

Custom replacements take precedence over built-in locale replacements and are applied first.

### Transliteration

#### Requirements

###### Gradle
There's a feature variant which can be used as follows:
```groovy
capabilities {
    requireCapability('com.github.slugify:slugify-transliterator')
}
```
For more information about feature variants please check the section [How to Create Feature Variants for a Library in Gradle](https://docs.gradle.org/current/userguide/how_to_create_feature_variants_of_a_library.html).

###### Other
Manually add the optional dependency `com.ibm.icu:icu4j` to your project.

#### Usage Example
```java
final Slugify slg = Slugify.builder().transliterator(true).build();
final String result = slg.slugify("Б");
// result: b
```
