package org.hyperia.shell.io;

import java.io.*;

public class IoRuntimeException extends RuntimeException {
    public IoRuntimeException(String message, IOException e) {
        super(messageWithCause(message), e);
    }

    private static String messageWithCause(String message) {
        return String.format("%s (See cause for details)", message);
    }
}