package org.pcsoft.framework.jrcp.core.providers.content;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pcsoft.framework.jrcp.api.providers.StringContentProvider;
import org.pcsoft.framework.jrcp.commons.exceptions.JRCPSerializationException;

/**
 * Basic class for JSON based content provider
 */
abstract class JsonContentProvider implements StringContentProvider {
    @Override
    public String serialize(Object o) throws JRCPSerializationException {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new JRCPSerializationException("Unable to serialize object of type " + o.getClass().getName() + " to JSON", e);
        }
    }

    @Override
    public Object deserialize(String serialized) throws JRCPSerializationException {
        try {
            return new ObjectMapper().readValue(serialized, Object.class);
        } catch (JsonProcessingException e) {
            throw new JRCPSerializationException("Unable to deserialize string to type " + Object.class.getName() + " from JSON", e);
        }
    }
}
