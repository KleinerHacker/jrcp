package org.pcsoft.framework.jrcp.api.providers;

import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;
import org.pcsoft.framework.jrcp.api.types.ValidationResult;

import java.lang.reflect.Method;

/**
 * Provider for an annotation framework to use for API interface
 */
public interface AnnotationProvider {
    /**
     * Check that the given API interface is valid
     * @param clazz API interface class to check
     * @return A validation result with an optional error message
     */
    ValidationResult isApiInterfaceValid(Class<?> clazz);

    /**
     * Check that the given method is relevant for REST handling
     * @param method Method to check from API interface
     * @return Rest method that was found or NULL if this is not a Rest method
     */
    RestMethodInfo getRestMethod(Method method, Object[] args);
}
