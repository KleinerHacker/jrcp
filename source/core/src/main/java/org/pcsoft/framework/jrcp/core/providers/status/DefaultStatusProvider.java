package org.pcsoft.framework.jrcp.core.providers.status;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pcsoft.framework.jrcp.api.providers.StatusProvider;
import org.pcsoft.framework.jrcp.api.types.RestMethodType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPHttpErrorException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultStatusProvider implements StatusProvider {
    public static final DefaultStatusProvider INSTANCE = new DefaultStatusProvider();

    @Override
    public void validateStatus(int httpCode, String cause, RestMethodType type, String uri) throws JRCPHttpErrorException {
        if (httpCode / 100 != 2 && httpCode / 100 != 3)
            throw new JRCPHttpErrorException(httpCode, cause);
    }
}
