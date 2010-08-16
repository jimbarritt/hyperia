package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;

import static java.lang.String.format;
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
        return executeCommand(createArgArray());
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
        log.debug(format("Executing command: %s", arguments));
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