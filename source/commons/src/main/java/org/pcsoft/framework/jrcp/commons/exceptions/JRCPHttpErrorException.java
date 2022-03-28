package org.pcsoft.framework.jrcp.commons.exceptions;

public class JRCPHttpErrorException extends JRCPExecutionException{
    public JRCPHttpErrorException(int httpCode, String cause) {
        super("HTTP Error " + httpCode + ": " + cause);
    }
}
