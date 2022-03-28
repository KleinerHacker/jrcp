package org.pcsoft.framework.jrcp.api.providers;

import org.pcsoft.framework.jrcp.api.types.RestMethodType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPHttpErrorException;

public interface StatusProvider {
    void validateStatus(int httpCode, String cause, RestMethodType type, String uri) throws JRCPHttpErrorException;
}
