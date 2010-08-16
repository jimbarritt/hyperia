package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;
import java.nio.charset.*;

import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;
import static org.hyperia.shell.io.Io.tryToClose;

public class Shell {
    private static final Logger log = Logger.getLogger(Shell.class);
    private Class<?> mainClass;

    public Shell(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    public Shell withArg(String s) {
        return this;
    }

    public String execute() {
        log.debug(String.format("Executing main class [%s]", mainClass.getName()));
        PrintStream previousOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream capturedOut = new PrintStream(out);
        try {
            System.setOut(capturedOut);
            
            capturedOut.flush();
            
            return out.toString(defaultCharset().name());
        } catch (IOException e) {
            throw new IoRuntimeException(format("Executing main class [%s]", mainClass.getName()), e);
        } finally {
            tryToClose(capturedOut);
            System.setOut(previousOut);
        }
    }
}