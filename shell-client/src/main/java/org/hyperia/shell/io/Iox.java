package org.hyperia.shell.io;

import java.io.*;

public class Iox {
    public static void tryToClose(OutputStream out) {
        if (out == null) {
            return;
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new IoRuntimeException("Closing outputStream", e);
        }

    }
}