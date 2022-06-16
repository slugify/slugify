Slugify [![Build Status](https://secure.travis-ci.org/slugify/slugify.svg?branch=master)](http://travis-ci.org/slugify/slugify)
=======

SEO-friendly URLs with Slugify

Usage
-----
Add Slugify as a dependency to your project:

```
implementation 'com.github.slugify:slugify:3.0.0'
```

Now you're able to use it:

```java
String result = Slugify.builder().build().slugify("Hello, world!");
// result: hello-world
```

You can set custom replacements for Slugify:

```java
Slugify slg = Slugify.builder().customReplacement("hello", "world").customReplacement("foo", "bar").build();
// or provided as a java.util.Map
slg = Slugify.builder().customReplacements(new HashMap<String, String>() {{
	put("hello", "world");
	put("foo", "bar");
}}).build();

String result = slg.slugify("hello foo");
// result: world-bar
```

Or if you want case sensitivity:

```java
Slugify slg = Slugify.builder().lowerCase(false).build();
String result = slg.slugify("Hello, World!");
// result: Hello-World
```
