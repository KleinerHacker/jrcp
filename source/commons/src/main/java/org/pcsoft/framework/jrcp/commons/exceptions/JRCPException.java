package org.pcsoft.framework.jrcp.commons.exceptions;

public abstract class JRCPException extends RuntimeException {
    public JRCPException() {
    }

    public JRCPException(String message) {
        super(message);
    }

    public JRCPException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRCPException(Throwable cause) {
        super(cause);
    }
}
