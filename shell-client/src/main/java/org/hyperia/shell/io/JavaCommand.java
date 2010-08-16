package org.hyperia.shell.io;

import java.util.*;

import static java.lang.String.*;
import static org.hyperia.shell.io.JavaCommand.JavaOptions.*;

class JavaCommand {

    private final String mainClassName;

    private String inheritedClasspath;
    private Map<String, String> systemProperties = new HashMap<String, String>();
    private List<String> applicationArguments;

    public JavaCommand addApplicationArguments(List<String> applicationArguments) {
        this.applicationArguments = applicationArguments;
        return this;
    }

    static enum JavaOptions {
        classpath;

        public String toString() {
            return format("-%s", name());
        }
    }

    public JavaCommand(Class mainClass) {
        this.mainClassName = mainClass.getName();
    }

    public JavaCommand withClasspath(String classpath) {
        this.inheritedClasspath = classpath;
        return this;
    }

    public JavaCommand withSystemProperty(String name, String value) {
        systemProperties.put(name, value);
        return this;
    }


    String[] toStringArray() {
        List<String> commandArguments = new ArrayList<String>();
        commandArguments.add("java");
        if (inheritedClasspath != null) {
            commandArguments.add(classpath.toString());
            commandArguments.add(inheritedClasspath);
        }
        for (Map.Entry<String, String> entry : systemProperties.entrySet()) {
            commandArguments.add(format("-D%s=%s", entry.getKey(), entry.getValue()));
        }
        commandArguments.add(mainClassName);
        commandArguments.addAll(applicationArguments);
        return commandArguments.toArray(new String[commandArguments.size()]);
    }
}