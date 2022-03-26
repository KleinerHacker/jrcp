package org.pcsoft.framework.jrcp.api.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public final class RestMethodInfo {
    private final RestMethodType type;
    private final String uriPath;

    private final Map<String, Object> pathParameters = new HashMap<>();
    private final Map<String, Object> queryParameters = new HashMap<>();

    private final String[] consumes;
    private final String[] produces;

    private final Object body;
}
