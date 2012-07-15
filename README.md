Slugify
=======

SEO-friendly URLs with Slugify

Notice
------
If you want to use it directly in your JSP, take a look at [slugify-taglib][1]

Usage
-----
If you want to use Slugify in your Java code you need the library itself.
Here's the dependency information for Maven:

    <dependency>
		<groupId>com.github.slugify</groupId>
		<artifactId>slugify</artifactId>
		<version>1.0-RELEASE</version>
    </dependency>

Now you're able to use it:

    // Result: hello-world
    String s = Slugify.slugify( "Hello, world!" );

[1]: http://github.com/slugify/slugify-taglib
