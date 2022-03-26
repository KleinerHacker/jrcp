package org.pcsoft.framework.jrcp.core;

import lombok.extern.slf4j.Slf4j;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPExecutionException;
import org.pcsoft.framework.jrcp.core.internal.JRCPProxy;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class JRCPClient {
    public static JRCPClientBuilder createBuilder(String uri) {
        return new JRCPClientBuilder(uri);
    }

    private final Map<Class<?>, Object> apiInterfaceProxies = new HashMap<>();

    JRCPClient(Class<?>[] apiInterfaceClasses, ContentProvider[] contentProviders, AnnotationProvider annotationProvider, ClassLoader classLoader, String uri) {
        log.info("Create JRCP Client");

        for (final var clazz : apiInterfaceClasses) {
            log.debug("Handle API interface " + clazz.getName());

            final Object proxy = JRCPProxy.create(clazz, classLoader, annotationProvider, contentProviders, uri);
            apiInterfaceProxies.put(clazz, proxy);
        }
    }

    @SuppressWarnings("unchecked")
    public <T>T getProxy(Class<T> clazz) {
        if (!apiInterfaceProxies.containsKey(clazz))
            throw new JRCPExecutionException("Class " + clazz.getName() + " is not a registered API interface");

        return (T) apiInterfaceProxies.get(clazz);
    }
}
