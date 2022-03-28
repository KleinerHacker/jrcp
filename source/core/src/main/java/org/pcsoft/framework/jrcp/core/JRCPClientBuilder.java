package org.pcsoft.framework.jrcp.core;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;
import org.pcsoft.framework.jrcp.api.providers.StatusProvider;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPConfigurationException;
import org.pcsoft.framework.jrcp.core.providers.content.ApplicationJsonContentProvider;
import org.pcsoft.framework.jrcp.core.providers.content.TextJsonContentProvider;
import org.pcsoft.framework.jrcp.core.providers.status.DefaultStatusProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A builder to create a {@link  JRCPClient}
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class JRCPClientBuilder {
    private final List<Class<?>> apiInterfaceClasses = new ArrayList<>();
    private final List<ContentProvider<?>> contentProviders = new ArrayList<>();

    private AnnotationProvider annotationProvider;
    private StatusProvider statusProvider = DefaultStatusProvider.INSTANCE;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    private final String uri;

    /**
     * Adds one or more API interface classes. <b>Must be an interface</b>!
     *
     * @param apiInterfaceClasses API interface class to add
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withApiInterface(Class<?>... apiInterfaceClasses) {
        for (final var clazz : apiInterfaceClasses) {
            if (!clazz.isInterface())
                throw new JRCPConfigurationException("Class " + clazz.getName() + " is not an interface");
        }

        this.apiInterfaceClasses.addAll(List.of(apiInterfaceClasses));
        return this;
    }

    /**
     * Setup an annotation provider to use for annotation reading. <b>Must call one times</b>!
     *
     * @param provider Prvider to use
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withAnnotationProvider(AnnotationProvider provider) {
        annotationProvider = provider;
        return this;
    }

    /**
     * Adds one or more content provider instances to use for (de-)serialization of content body objects
     *
     * @param providers Providers to add
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withContentProvider(ContentProvider<?>... providers) {
        contentProviders.addAll(List.of(providers));
        return this;
    }

    /**
     * Adds the standard builtin content providers.
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withStandardContentProviders() {
        contentProviders.addAll(Arrays.asList(
                (ContentProvider<?>) ApplicationJsonContentProvider.INSTANCE,
                (ContentProvider<?>) TextJsonContentProvider.INSTANCE
        ));
        return this;
    }

    /**
     * Setup a status provider to use for HTTP error handling. Normally it use {@link DefaultStatusProvider} implementation.
     *
     * @param statusProvider Status provider to use
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withStatusProvider(StatusProvider statusProvider) {
        this.statusProvider = statusProvider;
        return this;
    }

    /**
     * Setup an alternative class loader. Default is the system class loader
     *
     * @param classLoader Class loader to use
     * @return The builder itself (fluent API)
     */
    public JRCPClientBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    /**
     * Build the JRCP client instance
     *
     * @return A JRCP client instance
     * @throws JRCPConfigurationException Is thrown if builder required more data
     */
    public JRCPClient build() {
        log.info("Build JRCP Client...");

        if (apiInterfaceClasses.isEmpty())
            throw new JRCPConfigurationException("There are no registered API interfaces");
        if (contentProviders.isEmpty())
            throw new JRCPConfigurationException("There are no registered Content Providers");
        if (annotationProvider == null)
            throw new JRCPConfigurationException("There is no registered Annotation Provider");

        return new JRCPClient(
                apiInterfaceClasses.toArray(new Class[0]),
                contentProviders.toArray(new ContentProvider[0]),
                annotationProvider,
                statusProvider,
                classLoader,
                uri);
    }
}
