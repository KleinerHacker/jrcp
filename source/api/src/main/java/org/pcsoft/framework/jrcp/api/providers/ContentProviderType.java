package org.pcsoft.framework.jrcp.api.providers;

import java.lang.annotation.*;

/**
 * Annotation required for {@link  ContentProvider} interface implementation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentProviderType {
    /**
     * The HTTP content type to support with this implementation
     * @return HTTP content type
     */
    String value();
}
