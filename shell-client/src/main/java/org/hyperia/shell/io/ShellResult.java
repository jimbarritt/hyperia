package org.hyperia.shell.io;

public class ShellResult {
    private final int exitCode;
    private String output;

    public ShellResult(int exitCode, String output) {
        this.exitCode = exitCode;
        this.output = output;
    }

    public int exitCode() {
        return this.exitCode;
    }

    public String output() {
        return output;
    }
}