package org.pcsoft.framework.jrcp.api.providers;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentProviderType {
    String value();
}
