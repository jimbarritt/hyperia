package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;
import java.util.*;

import static java.lang.String.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.hyperia.shell.io.Iox.*;
import static org.hyperia.shell.io.Log4jStreamGobbler.Log4JLevel.*;

public class ForkedShell {
    private static final Logger log = Logger.getLogger(ForkedShell.class);

    private final Class mainClass;
    private String systemClasspath;
    private List<String> arguments;

    public ForkedShell(Class mainClass) {
        this.mainClass = mainClass;
        this.systemClasspath = System.getProperty("java.class.path");
        this.arguments = new ArrayList<String>();
    }

    public ForkedShell withArg(String arg) {
        arguments.add(arg);
        return this;
    }

    public ShellResult execute() {
        JavaCommand javaCommand = new JavaCommand(mainClass).withClasspath(systemClasspath);
        javaCommand.addApplicationArguments(arguments);
        addNonJavaSystemPropertiesTo(javaCommand);
        return executeCommand(javaCommand.toStringArray());
    }

    private static void addNonJavaSystemPropertiesTo(JavaCommand javaCommand) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            String keyString = (String) key;
            if (!keyString.startsWith("java")) {
                javaCommand.withSystemProperty(keyString, System.getProperty(keyString));
            }
        }
    }    

    public ShellResult executeCommand(String... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format("Executing command: %s", formatArray(arguments, " ")));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Process process = Runtime.getRuntime().exec(arguments);

            Log4jStreamGobbler errorGobbler = new Log4jStreamGobbler(process.getErrorStream(), error);
            Log4jStreamGobbler outputGobbler = new Log4jStreamGobbler(process.getInputStream(), info, out);

            errorGobbler.start();
            outputGobbler.start();

            int exitCode = process.waitFor();
            out.flush();
            return new ShellResult(exitCode, out.toString(defaultCharset().name()));
        } catch (Exception e) {
            throw new IoRuntimeException(format("Forking main class [%s]", mainClass.getName()), e);
        } finally {
            tryToClose(out);
        }
    }

}