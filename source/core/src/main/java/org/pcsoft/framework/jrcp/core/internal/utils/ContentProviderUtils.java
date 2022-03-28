package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPAnnotationException;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPExecutionException;

import java.util.Arrays;

/**
 * Utility class for content provider API
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContentProviderUtils {

    /**
     * Find the fit content provider based on given contentTypes
     *
     * @param providers Provider list to search in
     * @param contentTypes  Produces to search for
     * @return The fir content provider for contentTypes
     * @throws JRCPAnnotationException Is thrown if content provider implementation is incorrect
     * @throws JRCPExecutionException  Is thrown if no fit content provider was found for contentTypes
     */
    public static ContentProvider<?> find(ContentProvider<?>[] providers, String[] contentTypes) {
        if (contentTypes.length <= 0)
            throw new JRCPExecutionException("There are no contentTypes values");

        //FEATURE: multiple contentTypes?
        final var value = contentTypes[0];

        final var contentProvider = Arrays.stream(providers)
                .filter(x -> {
                    final var type = x.getClass().getAnnotation(ContentProviderType.class);
                    if (type == null)
                        throw new JRCPAnnotationException(x.getClass(), ContentProviderType.class);

                    return StringUtils.equals(type.value(), value);
                })
                .findFirst();

        if (contentProvider.isEmpty())
            throw new JRCPExecutionException("Unable to find a fit content provider for " + value);

        return contentProvider.get();
    }

}
