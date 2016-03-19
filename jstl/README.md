Slugify JSTL Integration
========================

Usage
-----
If you want to use Slugify in your JSPs you need the JSTL Integration and the library itself.
Here's the dependency information for Maven:

```xml
<dependency>
	<groupId>com.github.slugify</groupId>
	<artifactId>slugify-integration-jstl</artifactId>
	<version>2.1.5</version>
</dependency>
```

Now you're able to use it:

```html
<%@ taglib prefix="slg" uri="http://github.com/slugify" %>
<!-- Result: hello-world -->
${slg:slugify('Hello, world!')}
```

You can configure Slugify this way (JSTL):

```html
<%@ taglib prefix="slg" uri="http://github.com/slugify" %>
<slg:init lowerCase="false" />
<!-- Result: Hello-world -->
${slg:slugify('Hello, world!')}
```

Or this way (Java):

```java
InitSlugifyTag.getSlugify().setLowerCase(false);
```
