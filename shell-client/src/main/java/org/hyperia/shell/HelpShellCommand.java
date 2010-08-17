package org.hyperia.shell;

import org.apache.commons.cli.*;
import org.apache.log4j.*;

import java.io.*;

import static java.lang.String.format;

public class HelpShellCommand implements HyperiaShellCommand {
    private static final Logger log = Logger.getLogger(HelpShellCommand.class);

    private final String args;
    private final Options options;
    private final ShellConsole shellConsole;
    private String helpHeader = null;
    private String helpFooter = null;

    public HelpShellCommand(String args, Options options, ShellConsole shellConsole) {
        this.args = args;
        this.options = options;
        this.shellConsole = shellConsole;
    }

    @Override public void execute() {
        log.debug(format("invoked with [%s]", args));
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter writer = new PrintWriter(shellConsole.writer());
        formatter.printHelp(writer, 50,  "hyperia", helpHeader, options, 4, 4, helpFooter );
        writer.flush();
    }
}