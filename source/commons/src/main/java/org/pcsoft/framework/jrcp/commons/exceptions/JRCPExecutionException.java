package org.pcsoft.framework.jrcp.commons.exceptions;

public class JRCPExecutionException extends JRCPException{
    public JRCPExecutionException() {
    }

    public JRCPExecutionException(String message) {
        super(message);
    }

    public JRCPExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRCPExecutionException(Throwable cause) {
        super(cause);
    }
}
