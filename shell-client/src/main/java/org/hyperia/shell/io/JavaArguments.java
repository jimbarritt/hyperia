package org.hyperia.shell.io;

import java.util.*;

import static java.lang.String.format;
import static org.hyperia.shell.io.Iox.EMPTY_STRING_ARRAY;

class JavaArguments {

    private  final String mainClassName;

    private String classpath;
    private Map<String, String> systemProperties = new HashMap<String, String>();

    public JavaArguments(Class mainClass) {
        this.mainClassName = mainClass.getName();
    }

    public JavaArguments withClasspath(String classpath) {
        this.classpath = classpath;
        return this;
    }

    public JavaArguments withSystemProperty(String name, String value) {
        systemProperties.put(name, value);
        return this;
    }


    String[] toStringArray() {
        List<String> arguments = new ArrayList<String>();
        arguments.add("java");
        if (classpath != null) {
            arguments.add("-classpath");
            arguments.add(classpath);
        }
        for (Map.Entry<String, String> entry : systemProperties.entrySet()) {
            arguments.add(format("-D%s=%s", entry.getKey(), entry.getValue()));
        }
        arguments.add(mainClassName);
        return arguments.toArray(EMPTY_STRING_ARRAY);
    }
}