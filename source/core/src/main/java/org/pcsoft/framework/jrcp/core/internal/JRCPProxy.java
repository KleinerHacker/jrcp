package org.pcsoft.framework.jrcp.core.internal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.types.ValidationResult;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPConfigurationException;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPExecutionException;
import org.pcsoft.framework.jrcp.core.internal.utils.RequestUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * A class to create the JRCP Proxy objects
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JRCPProxy {
    /**
     * Creates the proxy for the {@link org.pcsoft.framework.jrcp.core.JRCPClient}
     *
     * @param interfaceClass     API interface class to implement as proxy
     * @param classLoader        Alternative class loader
     * @param annotationProvider Provider for annotation reading on API interface class
     * @param contentProviders   Providers to handle serialization of / from standard HTTP content types
     * @param uri                The base URI from {@link  org.pcsoft.framework.jrcp.core.JRCPClient}
     * @param <T>                Type of API interface class
     * @return A proxy instance of implemented API interface
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass, ClassLoader classLoader, AnnotationProvider annotationProvider, ContentProvider[] contentProviders,
                               String uri) {
        log.info("Create proxy for API interface " + interfaceClass.getName());

        final ValidationResult validationResult = annotationProvider.isApiInterfaceValid(interfaceClass);
        if (validationResult.hasError())
            throw new JRCPConfigurationException(validationResult.getError());

        return (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass},
                (proxy, method, args) -> handler(proxy, method, args, annotationProvider, contentProviders, uri));
    }

    private static Object handler(Object proxy, Method method, Object[] args, AnnotationProvider annotationProvider, ContentProvider[] contentProviders,
                                  String uri) {
        log.info("Invoke proxy method " + method);

        final var restMethodInfo = annotationProvider.getRestMethod(method, args);
        if (restMethodInfo == null) {
            if (!method.isDefault())
                throw new JRCPExecutionException("Unable to handle not REST method " + method + ": Is not default nor a REST relevant method");

            try {
                log.debug("Invoke method " + method + " as default from interface");
                return method.invoke(proxy, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new JRCPExecutionException("Unable to invoke default method " + method, e);
            }
        }

        if (method.isDefault()) {
            log.warn("Method " + method + " is a interface default method and will be overwritten in JRCP!");
        }

        try (final var httpClient = HttpClientBuilder.create().build()) {
            final var request = RequestUtils.createRequest(restMethodInfo, uri, contentProviders);
            try (final var response = httpClient.execute(request)) {
                return null; //TODO
            }
        } catch (IOException e) {
            throw new JRCPExecutionException("There is an error in communication", e);
        }
    }
}
