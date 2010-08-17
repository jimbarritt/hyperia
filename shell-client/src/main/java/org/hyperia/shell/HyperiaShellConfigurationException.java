package org.hyperia.shell;

public class HyperiaShellConfigurationException extends RuntimeException {

    public HyperiaShellConfigurationException(String message) {
        super(message);
    }

    public HyperiaShellConfigurationException(String message, Exception cause) {
        super(message, cause);
    }
}