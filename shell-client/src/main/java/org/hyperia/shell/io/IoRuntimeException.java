package org.hyperia.shell.io;

import java.io.*;

public class IoRuntimeException extends RuntimeException {
    public IoRuntimeException(String message, Throwable cause) {
        super(messageWithCause(message), cause);
    }

    private static String messageWithCause(String message) {
        return String.format("%s (See cause for details)", message);
    }
}