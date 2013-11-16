Slugify JSTL Integration
========================

Usage
-----
If you want to use Slugify in your JSPs you need the JSTL Integration and the library itself.
Here's the dependency information for Maven:

    <dependency>
		<groupId>com.github.slugify</groupId>
		<artifactId>slugify-integration-jstl</artifactId>
		<version>2.0.2</version>
    </dependency>

Now you're able to use it:

    <%@ taglib prefix="slg" uri="http://github.com/slugify" %>
    <!-- Result: Hello-world -->
    ${slg:slugify('Hello, world!')}
