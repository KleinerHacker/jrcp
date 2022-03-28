package org.pcsoft.framework.jrcp.core;

import lombok.extern.slf4j.Slf4j;
import org.pcsoft.framework.jrcp.api.providers.StatusProvider;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPExecutionException;
import org.pcsoft.framework.jrcp.core.internal.JRCPProxy;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent a JRCP client implementation. To create an instance use {@link #createBuilder(String)} method.
 */
@Slf4j
public final class JRCPClient {
    /**
     * Create a JRCP client based on given uri.
     *
     * @param uri The uri to use for this client. <b>Please note: This is a basic uri</b>. All other Uri parts will append to this uri.
     * @return A builder to create a JRCP client
     */
    public static JRCPClientBuilder createBuilder(String uri) {
        return new JRCPClientBuilder(uri);
    }

    private final Map<Class<?>, Object> apiInterfaceProxies = new HashMap<>();

    JRCPClient(Class<?>[] apiInterfaceClasses, ContentProvider<?>[] contentProviders, AnnotationProvider annotationProvider, StatusProvider statusProvider,
               ClassLoader classLoader, String uri) {
        log.info("Create JRCP Client");

        for (final var clazz : apiInterfaceClasses) {
            log.debug("Handle API interface " + clazz.getName());

            final Object proxy = JRCPProxy.create(clazz, classLoader, annotationProvider, contentProviders, statusProvider, uri);
            apiInterfaceProxies.put(clazz, proxy);
        }
    }

    /**
     * Returns the proxy of the given API interface class
     *
     * @param clazz API interface class to search proxy for
     * @param <T>   Type argument of type API interface
     * @return The found proxy implementation of given API interface class
     * @throws JRCPExecutionException Is thrown if no proxy class exists for the given API interface class.
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        if (!apiInterfaceProxies.containsKey(clazz))
            throw new JRCPExecutionException("Class " + clazz.getName() + " is not a registered API interface");

        return (T) apiInterfaceProxies.get(clazz);
    }
}
