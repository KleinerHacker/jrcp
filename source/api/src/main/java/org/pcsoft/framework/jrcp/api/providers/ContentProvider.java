package org.pcsoft.framework.jrcp.api.providers;

import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

public interface ContentProvider<T> {
    T serialize(Object o) throws JRCPSerializationException;
    Object deserialize(T serialized) throws JRCPSerializationException;
}
