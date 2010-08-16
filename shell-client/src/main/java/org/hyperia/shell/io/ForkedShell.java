package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;
import java.util.*;

import static java.lang.String.format;
import static org.hyperia.shell.io.Iox.formatArray;
import static org.hyperia.shell.io.Iox.tryToClose;
import static org.hyperia.shell.io.StreamGobbler.Log4JLevel.error;
import static org.hyperia.shell.io.StreamGobbler.Log4JLevel.info;

public class ForkedShell {
    private static final Logger log = Logger.getLogger(ForkedShell.class);

    

    private final Class mainClass;
    private String systemClasspath;

    public ForkedShell(Class mainClass) {
        this.mainClass = mainClass;
        this.systemClasspath = System.getProperty("java.class.path");
    }

    public ShellResult execute() {
        JavaArguments javaArguments = new JavaArguments(mainClass).withClasspath(systemClasspath);
        addNonJavaSystemPropertiesTo(javaArguments);
        return executeCommand(javaArguments.toStringArray());
    }

    private static void addNonJavaSystemPropertiesTo(JavaArguments javaArguments) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            String keyString = (String)key;
            if (!keyString.startsWith("java")) {
                javaArguments.withSystemProperty(keyString, System.getProperty(keyString));
            }
        }
    }

    private String[] createArgArray() {
        return new String[] {
            "java",
            "-Dlog4j.configuration=" + System.getProperty("log4j.configuration"),
            "-classpath",
            systemClasspath.replaceAll(" ", "\\\\ "),
            mainClass.getName()
        };
    }

    public ShellResult executeCommand(String... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format("Executing command: %s", formatArray(arguments, " ")));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Process process = Runtime.getRuntime().exec(arguments);

            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), error);
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), info, out);

            errorGobbler.start();
            outputGobbler.start();

            int exitCode = process.waitFor();
            out.flush();
            return new ShellResult(exitCode);
        } catch (Exception e) {
            throw new IoRuntimeException(format("Forking main class [%s]", mainClass.getName()), e);
        } finally {
            tryToClose(out);
        }
    }


}