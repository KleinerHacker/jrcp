package org.pcsoft.framework.jrcp.support.spring;

import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;
import org.pcsoft.framework.jrcp.api.types.ValidationResult;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPExecutionException;
import org.pcsoft.framework.jrcp.support.spring.utils.RestMethodUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * Implementation of an annotation provider to use annotations of spring
 */
public final class SpringAnnotationProvider implements AnnotationProvider {
    @Override
    public ValidationResult isApiInterfaceValid(Class<?> clazz) {
        return clazz.getAnnotation(RestController.class) != null ? ValidationResult.createPositive() :
                ValidationResult.createNegative("Missing required annotation " + RestController.class.getSimpleName() + " on class " + clazz.getName());
    }

    @Override
    public RestMethodInfo getRestMethod(Method method, Object[] args) {
        final RequestMethod requestMethod;
        if (method.getAnnotation(GetMapping.class) != null)
            requestMethod = RequestMethod.GET;
        else if (method.getAnnotation(PostMapping.class) != null)
            requestMethod = RequestMethod.POST;
        else if (method.getAnnotation(PutMapping.class) != null)
            requestMethod = RequestMethod.PUT;
        else if (method.getAnnotation(DeleteMapping.class) != null)
            requestMethod = RequestMethod.DELETE;
        else if (method.getAnnotation(PatchMapping.class) != null)
            requestMethod = RequestMethod.PATCH;
        else if (method.getAnnotation(RequestMapping.class) != null)
            requestMethod = method.getAnnotation(RequestMapping.class).method()[0];
        else
            throw new JRCPExecutionException("Unable to find any valid REST annotation on method " + method);

        return RestMethodUtils.buildRestMethodInfo(requestMethod, method, args);
    }


}
