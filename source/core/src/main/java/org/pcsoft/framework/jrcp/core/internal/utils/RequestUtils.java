package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;

import java.io.UnsupportedEncodingException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestUtils {

    public static HttpUriRequest createRequest(RestMethodInfo restMethodInfo, String uri, ContentProvider[] contentProviders) throws UnsupportedEncodingException {
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
            default:
                throw new NotImplementedException("Unknown HTTP method: " + restMethodInfo.getType().name());
        }

        if (restMethodInfo.getConsumes().length > 0) {
            //FEATURE: Multiple consumes?
            request.addHeader("accept", restMethodInfo.getConsumes()[0]);
        }

        return null; //TODO
    }

    private static HttpUriRequest createPost(RestMethodInfo restMethodInfo, ContentProvider[] contentProviders, String buildUri) throws UnsupportedEncodingException {
        final HttpUriRequest request;
        final var httpPost = new HttpPost(buildUri);
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getProduces());
        final var entity = EntityUtils.createEntity(restMethodInfo.getBody(), provider);
        httpPost.setEntity(entity);

        request = httpPost;
        return request;
    }

    private static HttpUriRequest createPut(RestMethodInfo restMethodInfo, ContentProvider[] contentProviders, String buildUri) throws UnsupportedEncodingException {
        final HttpUriRequest request;
        final var httpPut = new HttpPut(buildUri);
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getProduces());
        final var entity = EntityUtils.createEntity(restMethodInfo.getBody(), provider);
        httpPut.setEntity(entity);

        request = httpPut;
        return request;
    }

    private static HttpUriRequest createGet(String buildUri) {
        final HttpUriRequest request;
        request = new HttpGet(buildUri);
        return request;
    }

}
