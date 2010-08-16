package org.hyperia.shell.io;

import org.apache.log4j.*;
import org.junit.*;

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


    public static class SimpleCommandMain {
        private static final Logger log = Logger.getLogger(SimpleCommandMain.class);
        public static void main(String[] args) {
            log.info("Hello world");
        }
    }
}