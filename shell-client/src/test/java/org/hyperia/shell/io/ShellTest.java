package org.hyperia.shell.io;

import org.apache.log4j.*;
import org.junit.*;

import java.lang.reflect.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class ShellTest {

    private static final Logger log = Logger.getLogger(ShellTest.class);

    @Test
    public void executesSimpleCommand() {
        Shell shell = new Shell(SimpleCommandMain.class);
        
        String response = shell.execute();

        assertThat(response, containsString("Hello world"));
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
}