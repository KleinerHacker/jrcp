package org.pcsoft.framework.jrcp.support.spring;

import org.apache.commons.lang.NotImplementedException;
import org.pcsoft.framework.jrcp.api.providers.AnnotationProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;
import org.pcsoft.framework.jrcp.api.types.RestMethodType;
import org.pcsoft.framework.jrcp.api.types.ValidationResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        if (method.getAnnotation(GetMapping.class) != null)
            return getRestMethodGet(method, args);
        if (method.getAnnotation(PostMapping.class) != null)
            return null; //TODO
        if (method.getAnnotation(PutMapping.class) != null)
            return null; //TODO
        if (method.getAnnotation(DeleteMapping.class) != null)
            return null; //TODO

        final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            for (final var m : requestMapping.method()) {
                switch (m) {
                    case GET:
                        return getRestMethodGet(method, args);
                    case PUT:
                        return null; //TODO
                    case DELETE:
                        return null; //TODO
                    case POST:
                        return null; //TODO
                    case HEAD:
                        return null; //TODO
                    case OPTIONS:
                        return null; //TODO
                    case PATCH:
                        return null; //TODO
                    case TRACE:
                        return null; //TODO
                    default:
                        throw new NotImplementedException("Unknown REST method: " + m.name());
                }
            }
        }

        return null;
    }

    private RestMethodInfo getRestMethodGet(Method method, Object[] args) {
        final var getMapping = method.getAnnotation(GetMapping.class);
        final var requestMapping = method.getAnnotation(RequestMapping.class);

        final var path = getMapping != null ? getMapping.path() : requestMapping.path();
        final var consumes = getMapping != null ? getMapping.consumes() : requestMapping.consumes();
        final var produces = getMapping != null ? getMapping.produces() : requestMapping.produces();

        final var pathParams = Arrays.stream(method.getParameters())
                .filter(x -> x.getAnnotation(PathVariable.class) != null)
                .collect(Collectors.toMap(x -> x.getAnnotation(PathVariable.class).value(), x -> null)); //TODO
        final var queryParams = Arrays.stream(method.getParameters())
                .filter(x -> x.getAnnotation(RequestParam.class) != null)
                .collect(Collectors.toMap(x -> x.getAnnotation(RequestParam.class).value(), x -> null)); //TODO

        final var bodyValue = Arrays.stream(method.getParameters()); //TODO

        //TODO: Multiple paths
        final RestMethodInfo restMethodInfo = new RestMethodInfo(RestMethodType.GET, path[0], consumes, produces, bodyValue);
        restMethodInfo.getPathParameters().putAll(pathParams);
        restMethodInfo.getQueryParameters().putAll(queryParams);

        return restMethodInfo;
    }
}
