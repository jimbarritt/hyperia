package org.hyperia.shell.io;

public class ShellResult {
    private final int exitCode;

    public ShellResult(int exitCode) {
        this.exitCode = exitCode;
    }

    public int exitCode() {
        return this.exitCode;
    }
}