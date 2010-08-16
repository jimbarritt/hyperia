package org.hyperia.shell.io;

import org.junit.*;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

public class ForkedShellTest {

    @Test
    public void capturesExitCode() throws Exception {
        ForkedShell forkedShell = new ForkedShell(SimpleCommandMain.class);

        ShellResult shellResult = forkedShell.execute();
        
        assertThat(shellResult.exitCode(), equalTo(0));
    }
}