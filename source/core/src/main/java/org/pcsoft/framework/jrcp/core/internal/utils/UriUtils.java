package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for URI handling
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtils {

    /**
     * Create an uri with parameters
     *
     * @param uri         URI to use as base
     * @param pathParams  Map with all path parameters and its values to use
     * @param queryParams Map with all query parameters and its values to use
     * @return A new value based uri
     */
    public static String createUri(String uri, Map<String, Object> pathParams, Map<String, Object> queryParams) {
        log.debug("Create URI from " + uri);

        var currentUri = uri;

        for (final var pathParam : pathParams.entrySet()) {
            log.trace("> path param: " + pathParam.getKey());
            currentUri = currentUri.replace("{" + pathParam.getKey() + "}", pathParam.getValue().toString());
        }

        if (!queryParams.isEmpty()) {
            currentUri += "?";
            currentUri += queryParams.entrySet().stream()
                    .map(x -> {
                        log.trace("> query param: " + x.getKey());
                        return x.getKey() + "=" + x.getValue().toString();
                    })
                    .collect(Collectors.joining("&"));
        }

        log.debug("> URI created: " + currentUri);
        return currentUri;
    }

}
