package org.hyperia.shell.io;

import org.apache.log4j.*;
import org.junit.*;

import java.lang.reflect.*;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

/**
 * For this to work, you have to add the following in your log4j.xml
 * <param name="Follow" value="true"/>
 */
public class ShellTest {
    
    @Test
    public void executesSimpleCommand() {
        Shell shell = new Shell(SimpleCommandMain.class);

        String response = shell.execute();

        assertThat(response, containsString("Hello world"));
    }

    @Test
    public void executesCommandWithArgs() {
        Shell shell = new Shell(SayHelloCommandMain.class).withArg("foo").withArg("bar");

        String response = shell.execute();

        assertThat(response, containsString("Hello [foo] [bar]"));
    }

    @Test
    public void testInvokeMain() throws Exception {
        Method mainMethod = SimpleCommandMain.class.getMethod("main", new Class[]{String[].class});

        mainMethod.invoke(null, new Object[]{new String[]{}});
    }


    public static class SimpleCommandMain {
        private static final Logger log = Logger.getLogger(SimpleCommandMain.class);

        public static void main(String[] args) {
            log.info("Hello world");
        }
    }

    public static class SayHelloCommandMain {
        private static final Logger log = Logger.getLogger(SimpleCommandMain.class);

        public static void main(String[] args) {
            log.info(String.format("Hello [%s] [%s]", args[0], args[1]));
        }
    }
}