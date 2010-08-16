package org.hyperia.shell;

import org.apache.log4j.*;

import static org.hyperia.shell.HyperiaCommandLineOptions.parseCommandLineOptions;

public class HyperiaShellClientMain {
    private static final Logger log = Logger.getLogger(HyperiaShellClientMain.class);
    
    public static void main(String[] args) {
        try {
            new HyperiaShellClientMain().go(args);
        } catch (Exception e) {
            log.error("Unhandled exception", e);
            System.exit(99);
        }
    }

    private void go(String[] args) {
        log.info("Welcome to Hyperia version 1.0");

        parseCommandLineOptions(args).decideAppropriateCommand().execute();
    }
}