package org.pcsoft.framework.jrcp.support.spring.utils;

import lombok.*;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.pcsoft.framework.jrcp.api.types.RestMethodInfo;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestMethodUtils {
    public static RestMethodInfo buildRestMethodInfo(RequestMethod requestMethod, Method method, Object[] args) {
        final HttpMethodData data;
        switch (requestMethod) {
            case HEAD:
            case TRACE:
            case OPTIONS:
                data = extractDataForHttpMethod(method);
                break;
            case GET:
                data = extractDataForHttpMethod(method, GetMapping.class, mapping -> new HttpMethodData(mapping.path(), mapping.consumes(), mapping.produces()));
                break;
            case POST:
                data = extractDataForHttpMethod(method, PostMapping.class, mapping -> new HttpMethodData(mapping.path(), mapping.consumes(), mapping.produces()));
                break;
            case PUT:
                data = extractDataForHttpMethod(method, PutMapping.class, mapping -> new HttpMethodData(mapping.path(), mapping.consumes(), mapping.produces()));
                break;
            case PATCH:
                data = extractDataForHttpMethod(method, PatchMapping.class, mapping -> new HttpMethodData(mapping.path(), mapping.consumes(), mapping.produces()));
                break;
            case DELETE:
                data = extractDataForHttpMethod(method, DeleteMapping.class, mapping -> new HttpMethodData(mapping.path(), mapping.consumes(), mapping.produces()));
                break;
            default:
                throw new NotImplementedException(requestMethod.name());
        }

        final var parameterInfoList = toParameterInfo(method.getParameters(), args);
        final var pathParams = Arrays.stream(parameterInfoList)
                .filter(x -> x.getParameter().getAnnotation(PathVariable.class) != null)
                .collect(Collectors.toMap(
                        x -> StringUtils.defaultString(x.getParameter().getAnnotation(PathVariable.class).value(), x.getParameter().getName()),
                        ParameterInfo::getValue
                ));
        final var queryParams = Arrays.stream(parameterInfoList)
                .filter(x -> x.getParameter().getAnnotation(RequestParam.class) != null)
                .collect(Collectors.toMap(
                        x -> StringUtils.defaultString(x.getParameter().getAnnotation(RequestParam.class).value(), x.getParameter().getName()),
                        ParameterInfo::getValue
                ));
        final var bodyValue = Arrays.stream(parameterInfoList)
                .filter(x -> x.getParameter().getAnnotation(RequestBody.class) != null)
                .findFirst().map(ParameterInfo::getValue).orElse(null);

        //FEATURE: Multiple paths
        final var restMethodInfo = new RestMethodInfo(RestMethodConverter.convert(requestMethod),
                data.getPath()[0], data.getConsumes(), data.getProduces(), bodyValue, method.getAnnotation(ResponseBody.class) != null);
        restMethodInfo.getPathParameters().putAll(pathParams);
        restMethodInfo.getQueryParameters().putAll(queryParams);

        return restMethodInfo;
    }

    private static HttpMethodData extractDataForHttpMethod(Method method) {
        return extractDataForHttpMethod(method, null, null);
    }

    private static <T extends Annotation> HttpMethodData extractDataForHttpMethod(Method method, Class<T> annotationClazz, Function<T, HttpMethodData> extractor) {
        final var requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return  new HttpMethodData(requestMapping.path(), requestMapping.consumes(), requestMapping.produces());

        if (annotationClazz == null || extractor == null)
            throw new IllegalStateException("No more annotations found to get HTTP method info on " + method);

        final var methodMapping = method.getAnnotation(annotationClazz);
        if (methodMapping == null)
            throw new IllegalStateException("No more annotations found to get HTTP method info on " + method);

        return extractor.apply(methodMapping);
    }

    private static ParameterInfo[] toParameterInfo(Parameter[] parameters, Object[] args) {
        final var result = new ParameterInfo[parameters.length];
        for (var i = 0; i < result.length; i++) {
            result[i] = new ParameterInfo(parameters[i], args[i]);
        }

        return result;
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    private static final class ParameterInfo {
        @EqualsAndHashCode.Include
        private final Parameter parameter;
        private final Object value;
    }

    @AllArgsConstructor
    @Getter
    private static final class HttpMethodData {
        private final String[] path;
        private final String[] consumes;
        private final String[] produces;
    }
}
