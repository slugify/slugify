# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- SLF4J error logging when an `IOException` occurs while loading a language bundle
- Javadoc step in the CI check workflow to enforce `-Xdoclint:all` at build time
- PMD design rule category added to the ruleset
- JMH benchmarks for the transliterator and custom replacements code paths

### Changed
- `Class.getResourceAsStream()` replaces context `ClassLoader` for resource loading,
  ensuring compatibility with JPMS in OSGi and application server environments
- `Transliterator` instance is now cached per `Slugify` instance instead of being
  created on every `slugify()` call (~3.7× performance improvement in transliterator mode)
- `Slugify` class is now declared `final`
- Checkstyle configured to fail the build on any warning (`maxWarnings = 0`)
- Java 21 or higher is now required
- Gradle configuration cache enabled
- GitHub Actions pinned to commit SHAs for supply-chain security
- Dropped dependency-check plugin (replaced by Renovate; was also incompatible with
  Gradle configuration cache, see
  [dependency-check-gradle#339](https://github.com/dependency-check/dependency-check-gradle/issues/339))
- Dropped versions plugin (replaced by Renovate; was also incompatible with Gradle
  configuration cache, see
  [gradle-versions-plugin#666](https://github.com/ben-manes/gradle-versions-plugin/issues/666))

### Fixed
- Leading and trailing underscores are now trimmed when using underscore separator mode
- Removed `@SuppressWarnings("PMD.UnitTestShouldIncludeAssert")` made obsolete by the
  PMD 7.24.0 fix for false positives in lambda assertions
  ([pmd#4272](https://github.com/pmd/pmd/issues/4272))
- Removed unused `slugify.properties` base file left over from a prior
  `ResourceBundle.getBundle()` approach

## [3.0.7] - 2024-06-12

### Fixed
- `lowerCase(false)` is now respected in transliterator mode; previously the
  transliterator always produced lower-case output regardless of the setting
  ([#212](https://github.com/slugify/slugify/issues/212))

## [3.0.6] - 2023-10-11

### Fixed
- Corrected Unicode codepoints for Swedish `ä`/`Ä` and `ö`/`Ö` in
  `slugify_sv.properties`; the previous values were Norwegian

## [3.0.5] - 2023-06-10

### Added
- `module-info.java`: the library is now a proper Java module named `slugify`
  ([#133](https://github.com/slugify/slugify/issues/133))

## [3.0.4] - 2023-04-26

### Added
- Built-in character replacements for Ukrainian (`uk`)

## [3.0.3] - 2023-04-13

### Added
- GitHub Actions workflow for CI checks (Checkstyle, PMD, tests)

### Fixed
- Publishing credentials are now handled null-safe
  ([#92](https://github.com/slugify/slugify/issues/92))

## [3.0.2] - 2022-07-17

### Added
- Built-in character replacements for Icelandic (`is`): `ð`/`Ð`, `þ`/`Þ`
- German capital `ẞ` → `SS` in `slugify_de.properties`

### Changed
- `Slugify` constructor is now private; use `Slugify.builder()` to create instances

### Fixed
- JUnit 5 tests were not executed during the Gradle build

## [3.0.1] - 2022-06-23

### Changed
- Migrated test suite from JUnit 4 to JUnit 5

### Fixed
- Module metadata was missing the `group` field, causing Gradle dependency resolution
  errors for consumers

## [3.0.0] - 2022-06-21

### Added
- Checkstyle and PMD static analysis
- JMH benchmarks

### Changed
- Migrated build from Maven to Gradle
- Language-specific replacements are now loaded from locale-aware resource bundles
  (`slugify_<language>.properties`); built-in replacements are only applied when the
  corresponding locale is active
- Java 11 or higher is now required

## [2.5.0] - 2021-04-06

### Changed
- ICU4J (`com.ibm.icu:icu4j`) is now an optional dependency; declare the
  `slugify-transliterator` feature variant to use transliteration

## [2.4.0] - 2019-08-03

### Changed
- Updated ICU4J to 64.2

## [2.3.0] - 2018-12-08

### Added
- Built-in character replacements for Arabic (`ar`)
- JMH benchmark module

### Fixed
- Corrected `Ж` → `Zh` transliteration (was `yo`)

## [2.2.0] - 2017-11-13

### Changed
- Migrated to Java 8
- Updated ICU4J from 59.1 to 60.1

## [2.1.12] - 2017-10-25

### Fixed
- Normalization is now consistent for both `-` and `_` separators
  ([#47](https://github.com/slugify/slugify/pull/47))

## [2.1.11] - 2017-10-15

### Fixed
- [#45](https://github.com/slugify/slugify/issues/45)

## [2.1.10] - 2017-10-07

### Changed
- Pre-compiled regex patterns to reduce per-call allocation overhead
  ([#46](https://github.com/slugify/slugify/pull/46))

## [2.1.9] - 2017-04-15

### Added
- Built-in character replacements for Greek (`el`)
  ([#44](https://github.com/slugify/slugify/pull/44))

## [2.1.8] - 2017-02-24

### Added
- Built-in character replacements for Nordic locales (`da`, `no`, `sv`): `å`/`Å`,
  `ø`/`Ø`, `æ`/`Æ` ([#42](https://github.com/slugify/slugify/pull/42))

## [2.1.7] - 2016-10-12

### Fixed
- [#36](https://github.com/slugify/slugify/issues/36)

## [2.1.6] - 2016-08-22

### Added
- Fluent API ([#33](https://github.com/slugify/slugify/pull/33))

### Changed
- Removed plus sign handling ([#33](https://github.com/slugify/slugify/pull/33))

## [2.1.5] - 2016-03-19

### Added
- Built-in character replacements for Polish (`pl`)
  ([#21](https://github.com/slugify/slugify/pull/21))

## [2.1.4] - 2015-10-08

### Added
- Built-in character replacements for Cyrillic
  ([#20](https://github.com/slugify/slugify/pull/20))

## [2.1.3] - 2015-01-16

### Changed
- Language-specific replacements are now stored in per-language `.properties` files
  (locale-aware bundles)

## [2.1.2] - 2013-12-14

No user-facing changes.

## [2.1.1] - 2013-12-13

### Fixed
- [#12](https://github.com/slugify/slugify/issues/12)
- [#14](https://github.com/slugify/slugify/issues/14)

## [2.1.0] - 2013-12-11

### Added
- Turkish character replacement improvements
  ([#11](https://github.com/slugify/slugify/pull/11))

### Fixed
- Multiple consecutive non-word characters are now collapsed into a single `-`
- [#13](https://github.com/slugify/slugify/issues/13)

## [2.0.2] - 2013-11-16

### Fixed
- [#10](https://github.com/slugify/slugify/issues/10)

## [2.0.1] - 2013-11-15

### Fixed
- [#9](https://github.com/slugify/slugify/issues/9)

## [2.0.0] - 2013-11-13

### Added
- Built-in replacement for Turkish dotless `ı`
  ([#8](https://github.com/slugify/slugify/pull/8))

### Changed
- Complete rewrite of the slugification logic

## [1.0.0] - 2012-07-14

### Added
- Initial release
