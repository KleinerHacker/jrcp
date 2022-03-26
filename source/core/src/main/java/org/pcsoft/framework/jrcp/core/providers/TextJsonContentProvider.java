package org.pcsoft.framework.jrcp.core.providers;

import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

/**
 * Represent a content provider for content type text/json
 */
@ContentProviderType("text/json")
public final class TextJsonContentProvider extends JsonContentProvider {
    @Override
    public Object serialize(Object o) throws JRCPSerializationException {
        return null;
    }

    @Override
    public Object deserialize(Object serialized) throws JRCPSerializationException {
        return null;
    }
}
