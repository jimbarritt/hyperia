package org.hyperia.shell.io;

import java.io.*;

public class Shell {
    public Shell(Class<?> mainClass) {

    }

    public Shell withArg(String s) {
        return this;
    }

    public String execute() {
        OutputStream previousOut = System.out;
        try {
            ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(capturedOut));
            return null;
        } finally {
            System.setOut(previousOut);
        }
    }
}