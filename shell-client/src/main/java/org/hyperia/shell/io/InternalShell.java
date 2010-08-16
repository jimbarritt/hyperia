package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import static java.lang.String.format;
import static java.lang.System.setOut;
import static java.nio.charset.Charset.defaultCharset;
import static org.hyperia.shell.io.Iox.tryToClose;

public class InternalShell {
    private static final Logger log = Logger.getLogger(InternalShell.class);
    
    private final Class mainClass;
    private final Method mainMethod;
    private final List<String> arguments;
    private final String[] EMPTY_STRING_ARRAY = new String[]{};

    public InternalShell(Class mainClass) {
        this.mainClass = mainClass;
        this.mainMethod = findMainMethod(mainClass);
        this.arguments = new ArrayList<String>();
    }

    public InternalShell withArg(String arg) {
        arguments.add(arg);
        return this;
    }

    public String execute() {
        log.debug(String.format("Executing main class [%s]", mainClass.getName()));
        PrintStream previousOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream capturedOut = new PrintStream(out);
        try {
            setOut(capturedOut);
            invokeMainMethod();
            capturedOut.flush();
            return out.toString(defaultCharset().name());
        } catch (IOException e) {
            throw new IoRuntimeException(format("Executing main class [%s]", mainClass.getName()), e);
        } finally {
            tryToClose(capturedOut);
            setOut(previousOut);
            log.debug("Main class execution completed");
        }

    }

    private void invokeMainMethod() {
        try {
            mainMethod.invoke(null, new Object[]{arguments.toArray(EMPTY_STRING_ARRAY)});
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
            return mainClass.getMethod("main", new Class[]{String[].class});
        } catch (NoSuchMethodException e) {
            throw new IoRuntimeException(format("Could not find main method on class [%s]", mainClass.getName()), e);
        }
    }

}