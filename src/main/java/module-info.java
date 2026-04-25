/**
 * Slugify – a Java library for generating SEO-friendly URL slugs.
 */
module slugify {
    exports com.github.slugify;

    requires static com.ibm.icu; // optional at runtime: only needed when transliterator(true)
    requires static lombok;      // compile-time only: annotation processing
    requires org.slf4j;
}
