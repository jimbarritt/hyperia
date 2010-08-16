package org.hyperia.shell.io;

import org.apache.log4j.*;

import static java.lang.String.format;

public class SayHelloCommandMain {
    private static final Logger log = Logger.getLogger(SimpleCommandMain.class);

    public static void main(String[] args) {
        log.info(format("Hello [%s] [%s]", args[0], args[1]));
    }
}