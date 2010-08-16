package org.hyperia.shell.functionaltest;

import org.hyperia.shell.*;
import org.hyperia.shell.io.*;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class EndToEndTest {

    @Test
    public void showsASimpleHelpPage() {
        InternalShell internalShell = new InternalShell(HyperiaShellClientMain.class).withArg("--help");

        String output = internalShell.execute();

        assertThat(output, containsString("Hyperia"));
        assertThat(output, containsString("Help"));

    }
    
}