/**
 * Slugify – a Java library for generating SEO-friendly URL slugs.
 */
module slugify {
    exports com.github.slugify;

    requires static com.ibm.icu;
    requires static lombok;
    requires org.slf4j;
}
