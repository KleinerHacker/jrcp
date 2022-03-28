package org.pcsoft.framework.jrcp.support.spring.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.pcsoft.framework.jrcp.api.types.RestMethodType;
import org.springframework.web.bind.annotation.RequestMethod;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestMethodConverter {

    public static RestMethodType convert(RequestMethod requestMethod) {
        switch (requestMethod) {
            case GET:
                return RestMethodType.GET;
            case HEAD:
                return RestMethodType.HEAD;
            case POST:
                return RestMethodType.POST;
            case PUT:
                return RestMethodType.PUT;
            case PATCH:
                return RestMethodType.PATCH;
            case DELETE:
                return RestMethodType.DELETE;
            case OPTIONS:
                return RestMethodType.OPTIONS;
            case TRACE:
                return RestMethodType.TRACE;
            default:
                throw new NotImplementedException(requestMethod.name());
        }
    }

}
