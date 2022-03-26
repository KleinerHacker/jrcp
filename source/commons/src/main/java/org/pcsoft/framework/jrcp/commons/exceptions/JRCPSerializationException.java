package org.pcsoft.framework.jrcp.commons.exceptions;

public class JRCPSerializationException extends JRCPExecutionException{
    public JRCPSerializationException() {
    }

    public JRCPSerializationException(String message) {
        super(message);
    }

    public JRCPSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRCPSerializationException(Throwable cause) {
        super(cause);
    }
}
