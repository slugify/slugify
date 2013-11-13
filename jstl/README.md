Slugify JSTL Integration
========================

Usage
-----
If you want to use Slugify in your JSPs you need the JSTL Integration and the library itself.
Here's the dependency information for Maven:

    <dependency>
		<groupId>com.github.slugify</groupId>
		<artifactId>slugify-integration-jstl</artifactId>
		<version>2.0.0</version>
    </dependency>

Now you're able to use it:

    <%@ taglib prefix="slg" uri="http://github.com/slugify" %>
    <!-- Result: hello-world -->
    ${slg:slugify('Hello, world!')}

You can set custom replacements here as well.
In your Java code do this (It's important to go the Singleton way):

    Slugify slg = Slugify.getInstance();
    slg.setCustomReplacements(new HashMap<String, String>() {{
    	put("ß", "ss");
    }});

In your JSP it will automatically use your custom replacements:

    <%@ taglib prefix="slg" uri="http://github.com/slugify" %>
    <!-- Result: dass -->
    ${slg:slugify('daß')}
