package org.hyperia.shell.io;

import org.apache.log4j.*;
import org.junit.*;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class ForkedShellTest {

    @Test
    public void capturesExitCodeAndOutput() throws Exception {
        ForkedShell forkedShell = new ForkedShell(SimpleCommandMain.class);

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.exitCode(), is(0));
        assertThat(shellResult.output(), containsString("Hello world"));
    }

    @Test
    public void capturesExitCodeWhenSystemExits() {
        ForkedShell forkedShell = new ForkedShell(SystemExitMain.class);

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.exitCode(), is(99));
    }


    @Test
    public void escapeSpaces() {
        String test = "This is a test";

        String result = test.replaceAll(" ", "\\\\ ");

        assertThat(result, is("This\\ is\\ a\\ test"));
    }

    @Test
    public void simpleExec() throws Exception {
        String[] args = new String[]{
                "java",
                "-cp",
                "/Users/jim/work/code/external/apache/apache-ant-1.8.0",
                "-version"
        };

        new ForkedShell(String.class).executeCommand(args);
    }

    public static class SystemExitMain {
        private static final Logger log = Logger.getLogger(SystemExitMain.class);

        public static void main(String[] args) {
            log.info("Im going to exit with 99");
            System.exit(99);
        }
    }
}