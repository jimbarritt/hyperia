package org.hyperia.shell;

public class RunGameCommand implements HyperiaShellCommand {
    private final ShellConsole shellConsole;

    public RunGameCommand(ShellConsole shellConsole) {
        this.shellConsole = shellConsole;
    }

    @Override public void execute() {
        shellConsole.printf("Game has started");
    }
}