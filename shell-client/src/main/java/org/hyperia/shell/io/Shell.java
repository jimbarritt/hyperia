package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;
import java.lang.reflect.*;

import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;
import static org.hyperia.shell.io.Io.tryToClose;

public class Shell {
    private static final Logger log = Logger.getLogger(Shell.class);

    private static final Object[] EMPTY_ARGS = new Object[] {};

    private final Class<?> mainClass;
    private Method mainMethod;

    public Shell(Class<?> mainClass) {
        this.mainClass = mainClass;
        this.mainMethod = findMainMethod(mainClass);
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

            invokeMainMethod();

            return out.toString(defaultCharset().name());
        } catch (IOException e) {
            throw new IoRuntimeException(format("Executing main class [%s]", mainClass.getName()), e);
        } finally {
            tryToClose(capturedOut);
            System.setOut(previousOut);
        }
    }

    private void invokeMainMethod() {
        try {
            mainMethod.invoke(new Object[] {});
        } catch (IllegalAccessException e) {
            throw new IoRuntimeException(format("Restricted access to class [%s], is it private?", mainClass.getName()), e);
        } catch (InvocationTargetException e) {
            throw new IoRuntimeException(format("An exception was thrown invoking main method of class [%s]", mainClass.getName()), e);
        } catch (Exception e) {
            throw new IoRuntimeException(format("Problem invoking main method of class [%s]", mainClass.getName()), e);            
        }
    }

    private static Method findMainMethod(Class<?> mainClass) {
        try {
            return mainClass.getDeclaredMethod("main", String.class);
        } catch (NoSuchMethodException e) {
            throw new IoRuntimeException(format("Could not find main method on class [%s]", mainClass.getName()), e);
        }
    }

}