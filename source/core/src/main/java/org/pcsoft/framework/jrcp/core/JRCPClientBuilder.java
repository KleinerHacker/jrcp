package org.pcsoft.framework.jrcp.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPConfigurationException;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.providers.ContentProvider;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class JRCPClientBuilder {
    private final List<Class<?>> apiInterfaceClasses = new ArrayList<>();
    private final List<ContentProvider> contentProviders = new ArrayList<>();

    private AnnotationProvider annotationProvider;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private final String uri;

    public JRCPClientBuilder withApiInterface(Class<?> apiInterfaceClass) {
        if (!apiInterfaceClass.isInterface())
            throw new JRCPConfigurationException("Class " + apiInterfaceClass.getName() + " is not an interface");

        apiInterfaceClasses.add(apiInterfaceClass);
        return this;
    }

    public JRCPClientBuilder withAnnotationProvider(AnnotationProvider provider) {
        annotationProvider = provider;
        return this;
    }

    public JRCPClientBuilder withContentProvider(ContentProvider... providers) {
        contentProviders.addAll(List.of(providers));
        return this;
    }

    public JRCPClientBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

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
                classLoader,
                uri);
    }
}
