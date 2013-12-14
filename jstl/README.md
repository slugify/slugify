Slugify JSTL Integration
========================

Usage
-----
If you want to use Slugify in your JSPs you need the JSTL Integration and the library itself.
Here's the dependency information for Maven:

    <dependency>
		<groupId>com.github.slugify</groupId>
		<artifactId>slugify-integration-jstl</artifactId>
		<version>2.1.2</version>
    </dependency>

Now you're able to use it:

    <%@ taglib prefix="slg" uri="http://github.com/slugify" %>
    <!-- Result: hello-world -->
    ${slg:slugify('Hello, world!')}

You can configure Slugify this way (both attributes are optional and can have Boolean/Locale or String as value):

    <%@ taglib prefix="slg" uri="http://github.com/slugify" %>
    <slg:init lowerCase="false" locale="en" />
    <!-- Result: Hello-world -->
    ${slg:slugify('Hello, world!')}
