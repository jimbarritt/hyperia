package org.hyperia.shell.io;

import org.junit.*;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

public class ForkedShellTest {

    @Test
    public void capturesExitCode() throws Exception {
        ForkedShell forkedShell = new ForkedShell(SimpleCommandMain.class);

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.exitCode(), equalTo(0));
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


}