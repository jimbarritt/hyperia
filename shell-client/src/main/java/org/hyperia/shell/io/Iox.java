package org.hyperia.shell.io;

import java.io.*;

public class Iox {
    static final String[] EMPTY_STRING_ARRAY = new String[] {};

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

    public static <T> String formatArray(T[] array, String separator) {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (T item : array) {
            sb.append(item);
            i++;
            if (i < array.length) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}