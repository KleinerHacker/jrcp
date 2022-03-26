package org.pcsoft.framework.jrcp.core.providers;

import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

@ContentProviderType("application/json")
public final class ApplicationJsonContentProvider extends JsonContentProvider {
    @Override
    public Object serialize(Object o) throws JRCPSerializationException {
        return null;
    }

    @Override
    public Object deserialize(Object serialized) throws JRCPSerializationException {
        return null;
    }
}
