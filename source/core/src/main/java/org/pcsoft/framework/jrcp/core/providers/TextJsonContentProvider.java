package org.pcsoft.framework.jrcp.core.providers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

/**
 * Represent a content provider for content type text/json
 */
@ContentProviderType("text/json")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextJsonContentProvider extends JsonContentProvider {
    public static final TextJsonContentProvider INSTANCE = new TextJsonContentProvider();

    @Override
    public Object serialize(Object o) throws JRCPSerializationException {
        return null;
    }

    @Override
    public Object deserialize(Object serialized) throws JRCPSerializationException {
        return null;
    }
}
