package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.pcsoft.framework.jrcp.api.providers.BinaryContentProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.providers.StringContentProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPConfigurationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtils {

    public static Object extractValueFromResponse(CloseableHttpResponse response, RestMethodInfo restMethodInfo, ContentProvider<?>[] contentProviders) throws IOException {
        final var provider = ContentProviderUtils.find(contentProviders, restMethodInfo.getConsumes());
        if (provider instanceof StringContentProvider) {
            final var str = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            return ((StringContentProvider) provider).deserialize(str);
        } else if (provider instanceof BinaryContentProvider) {
            final var bytes = EntityUtils.toByteArray(response.getEntity());
            return ((BinaryContentProvider) provider).deserialize(bytes);
        } else
            throw new JRCPConfigurationException("Content provider " + provider.getClass().getName() + " must implements a typed provider interface");
    }

}
