package org.hyperia.shell;

import org.apache.commons.cli.*;

import static java.lang.String.format;
import static org.hyperia.shell.HyperiaCommandLineOptions.OptionName.help;
import static org.hyperia.shell.io.Iox.formatArray;

public class HyperiaCommandLineOptions {
    private static final Options OPTIONS = createOptions();
    private final CommandLine line;
    private final String[] originalArgs;

    public enum OptionName {
        help;
    }

    public static HyperiaCommandLineOptions parseCommandLineOptions(String[] args) {
        try {
            CommandLine line = new GnuParser().parse(OPTIONS, args);
            return new HyperiaCommandLineOptions(args, line);
        } catch (Exception e) {
            throw new HyperiaShellConfigurationException(format("Could not parse arguments [%s]", formatArray(args, ", ")), e);
        }
    }

    public HyperiaCommandLineOptions(String[] originalArgs, CommandLine line) {
        this.originalArgs = originalArgs;        
        this.line = line;
    }

    public HyperiaShellCommand decideAppropriateCommand() {
        ShellConsole shellConsole = new ShellConsole();
        if (hasHelpOption())  {
            return new HelpShellCommand(formatArray(originalArgs, ", "), OPTIONS, shellConsole);
        }
        if (line.getArgList().size() > 0) {
            throw new HyperiaShellConfigurationException(format("Don't know what to do, no recognised commands in [%s]", formatArray(originalArgs, ", ")));
        }
        return new RunGameCommand(shellConsole);
    }

    private boolean hasHelpOption() {
        return line.hasOption(help.name());
    }

    private static Options createOptions() {
        Option help = new Option(OptionName.help.name(), "Shows this help message");                                   
        Options options = new Options();
        options.addOption(help);
        return options;
    }
}