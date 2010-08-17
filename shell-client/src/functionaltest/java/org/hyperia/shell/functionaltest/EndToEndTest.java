package org.hyperia.shell.functionaltest;

import org.hyperia.game.*;
import org.hyperia.shell.*;
import org.hyperia.shell.io.*;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class EndToEndTest {
    private HyperiaServer server;


    @BeforeClass
    public void startHyperiaServer() {
        server = new HyperiaServer();
        server.start();
    }

    @AfterClass
    public void stopHyperiaServer() {
        server.stop();
    }
   
    @Test
    public void showsHelpMessage() {
        ForkedShell forkedShell = forkHyperia().withArg("--help");

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.output(), containsString("Help"));        
    }

    @Test
    public void beginsGame() {
        ForkedShell forkedShell = forkHyperia().withArg("serverUri=http://localhost:8699/");

        ShellResult shellResult = forkedShell.execute();

        assertThat(shellResult.output(), containsString("Game has started"));
    }

    private static ForkedShell forkHyperia() {
        return new ForkedShell(HyperiaShellClientMain.class);
    }
}