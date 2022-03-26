package org.pcsoft.framework.jrcp.core.internal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.pcsoft.framework.jrcp.api.providers.BinaryContentProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.providers.StringContentProvider;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPConfigurationException;

import java.io.UnsupportedEncodingException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityUtils {

    public static HttpEntity createEntity(Object body, ContentProvider provider) throws UnsupportedEncodingException {
        if (provider instanceof StringContentProvider) {
            return new StringEntity(((StringContentProvider) provider).serialize(body));
        } else if (provider instanceof BinaryContentProvider) {
            return new ByteArrayEntity(((BinaryContentProvider) provider).serialize(body));
        } else
            throw new JRCPConfigurationException("Content provider " + provider.getClass().getName() + " must implements a typed provider interface");
    }

}
