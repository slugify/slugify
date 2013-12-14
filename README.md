Slugify [![Build Status](https://secure.travis-ci.org/slugify/slugify.png?branch=master)](http://travis-ci.org/slugify/slugify)
=======

SEO-friendly URLs with Slugify

Notice
------
If you want to use it directly in your JSP, take a look into [jstl][1]

Usage
-----
If you want to use Slugify in your Java code you only need the library itself.
Here's the dependency information for Maven:

    <dependency>
		<groupId>com.github.slugify</groupId>
		<artifactId>slugify</artifactId>
		<version>2.1.2</version>
    </dependency>

Now you're able to use it:

    Slugify slg = new Slugify();
    // Result: hello-world
    String s = slg.slugify("Hello, world!");

You can set custom replacements for Slugify:

    Slugify slg = new Slugify();
    slg.setCustomReplacements(new HashMap<String, String>() {{
    	put("foo", "bar");
    }});

    // Result: hello-bar
    String s = slg.slugify("Hello foo");

Or if you want case sensitivity:

    Slugify slg = new Slugify(false);
    // Result: Hello-World
    String s = slg.slugify("Hello, World!");

[1]: http://github.com/slugify/slugify/tree/master/jstl
