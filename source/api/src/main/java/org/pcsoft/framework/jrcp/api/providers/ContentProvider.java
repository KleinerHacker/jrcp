package org.pcsoft.framework.jrcp.api.providers;

import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

/**
 * A provider to use for (de-)serialization of content body objects based on content type. <b>You must define {@link ContentProviderType} annotation!
 * Please do not use this interface for an implementation directly!</b>
 *
 * @param <T> Target type of serialization
 */
public interface ContentProvider<T> {
    /**
     * Serialize the given object into target type
     *
     * @param o Object to serialize
     * @return The serialized target object (body)
     * @throws JRCPSerializationException Is thrown if serialization fails
     */
    T serialize(Object o) throws JRCPSerializationException;

    /**
     * Deserialize the given target object
     *
     * @param serialized Target object to deserialize (body)
     * @return The deserialized object
     * @throws JRCPSerializationException Is thrown if deserialization fails
     */
    Object deserialize(T serialized) throws JRCPSerializationException;
}
