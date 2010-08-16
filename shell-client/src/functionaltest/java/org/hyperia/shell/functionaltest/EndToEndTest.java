package org.hyperia.shell.functionaltest;

import org.hyperia.shell.*;
import org.hyperia.shell.io.*;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class EndToEndTest {

    @Test
    public void showsAHelloMessage() {
        ForkedShell forkedShell = forkHyperia();

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.output(), containsString("Welcome to Hyperia"));
    }



    @Test
    public void showsHelpMessage() {
        ForkedShell forkedShell = forkHyperia().withArg("--help");

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.output(), containsString("Help"));        
    }

    private static ForkedShell forkHyperia() {
        return new ForkedShell(HyperiaShellClientMain.class);
    }
}