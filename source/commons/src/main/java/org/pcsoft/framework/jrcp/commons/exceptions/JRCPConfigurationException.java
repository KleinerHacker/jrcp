package org.pcsoft.framework.jrcp.commons.exceptions;

public class JRCPConfigurationException extends JRCPException{
    public JRCPConfigurationException() {
    }

    public JRCPConfigurationException(String message) {
        super(message);
    }

    public JRCPConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRCPConfigurationException(Throwable cause) {
        super(cause);
    }
}
