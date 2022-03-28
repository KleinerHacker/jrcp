package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.apache.http.client.methods.*;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;

import java.io.UnsupportedEncodingException;

/**
 * Utility class for HTTP request
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestUtils {

    /**
     * Creates a HTTP request based on method info with one of given content providers
     * @param restMethodInfo Info about proxy method call
     * @param uri URI to use as target
     * @param contentProviders Configured content providers
     * @return A HTTP request
     * @throws UnsupportedEncodingException Is thrown by HTTP entity creation
     */
    public static HttpUriRequest createRequest(RestMethodInfo restMethodInfo, String uri, ContentProvider<?>[] contentProviders) throws UnsupportedEncodingException {
        final String buildUri = UriUtils.createUri(uri + "/" + restMethodInfo.getUriPath(), restMethodInfo.getPathParameters(), restMethodInfo.getQueryParameters());

        final HttpUriRequest request;
        switch (restMethodInfo.getType()) {
            case GET:
                request = createGet(buildUri);
                break;
            case PUT:
                request = createPut(restMethodInfo, contentProviders, buildUri);
                break;
            case POST:
                request = createPost(restMethodInfo, contentProviders, buildUri);
                break;
            case DELETE:
                request = createDelete(buildUri);
                break;
            case HEAD:
                request = createHead(buildUri);
                break;
            case OPTIONS:
                request = createOptions(buildUri);
                break;
            case PATCH:
                request = createPatch(restMethodInfo, contentProviders, buildUri);
                break;
            case TRACE:
                request = createTrace(buildUri);
                break;
            default:
                throw new NotImplementedException("Unknown HTTP method: " + restMethodInfo.getType().name());
        }

        if (restMethodInfo.getConsumes().length > 0) {
            //FEATURE: Multiple consumes?
            request.addHeader("accept", restMethodInfo.getConsumes()[0]);
        }

        return request;
    }

    private static HttpUriRequest createPost(RestMethodInfo restMethodInfo, ContentProvider<?>[] contentProviders, String buildUri) throws UnsupportedEncodingException {
        final var httpPost = new HttpPost(buildUri);
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getProduces());
        final var entity = EntityUtils.createEntity(restMethodInfo.getBody(), provider);
        httpPost.setEntity(entity);

        return httpPost;
    }

    private static HttpUriRequest createPut(RestMethodInfo restMethodInfo, ContentProvider<?>[] contentProviders, String buildUri) throws UnsupportedEncodingException {
        final var httpPut = new HttpPut(buildUri);
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getProduces());
        final var entity = EntityUtils.createEntity(restMethodInfo.getBody(), provider);
        httpPut.setEntity(entity);

        return httpPut;
    }

    private static HttpUriRequest createGet(String buildUri) {
        return new HttpGet(buildUri);
    }

    private static HttpUriRequest createDelete(String buildUri) {
        return new HttpDelete(buildUri);
    }

    private static HttpUriRequest createPatch(RestMethodInfo restMethodInfo, ContentProvider<?>[] contentProviders, String buildUri) throws UnsupportedEncodingException {
        final var httpPatch = new HttpPatch(buildUri);
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getProduces());
        final var entity = EntityUtils.createEntity(restMethodInfo.getBody(), provider);
        httpPatch.setEntity(entity);

        return httpPatch;
    }

    private static HttpUriRequest createHead(String buildUri) {
        return new HttpHead(buildUri);
    }

    private static HttpUriRequest createOptions(String buildUri) {
        return new HttpOptions(buildUri);
    }

    private static HttpUriRequest createTrace(String buildUri) {
        return new HttpTrace(buildUri);
    }

}
