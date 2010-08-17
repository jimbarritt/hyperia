package org.hyperia.shell;

import java.io.*;

public class ShellConsole {

    private final PrintStream out = System.out;

    public void printf(String format, Object... args) {
        out.printf(format, args);
    }

    public PrintStream writer() {
        return out;
    }
}