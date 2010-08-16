package org.hyperia.shell.functionaltest;

import org.hyperia.shell.*;
import org.hyperia.shell.io.*;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class EndToEndTest {

    @Test
    public void showsASimpleHelpPage() {
        ForkedShell forkedShell = new ForkedShell(HyperiaShellClientMain.class).withArg("--help");

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.output(), containsString("Hyperia"));
        assertThat(shellResult.output(), containsString("Help"));

    }
    
}