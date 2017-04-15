Slugify [![Build Status](https://secure.travis-ci.org/slugify/slugify.svg?branch=master)](http://travis-ci.org/slugify/slugify)
=======

SEO-friendly URLs with Slugify

Notice
------
If you want to use it directly in your JSP, take a look into [jstl][1]

Usage
-----
If you want to use Slugify in your Java code you only need the library itself.
Here's the dependency information for Maven:

```xml
<dependency>
	<groupId>com.github.slugify</groupId>
	<artifactId>slugify</artifactId>
	<version>2.1.9</version>
</dependency>
```

Now you're able to use it:

```java
Slugify slg = new Slugify();
String result = slg.slugify("Hello, world!");
// result: hello-world
```

You can set custom replacements for Slugify:

```java
Slugify slg = slg.withCustomReplacement("hello", "world").withCustomReplacement("foo", "bar");
// or multiple at once
slg = slg.withCustomReplacements(new HashMap<String, String>() {{
	put("hello", "world");
	put("foo", "bar");
}});

String result = slg.slugify("hello foo");
// result: world-bar
```

Or if you want case sensitivity:

```java
Slugify slg = new Slugify().withLowerCase(false);
String result = slg.slugify("Hello, World!");
// result: Hello-World
```

[1]: http://github.com/slugify/slugify/tree/master/jstl
