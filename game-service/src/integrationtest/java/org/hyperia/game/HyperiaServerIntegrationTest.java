package org.hyperia.game;

import org.junit.*;

public class HyperiaServerIntegrationTest {
    private HyperiaServer server;

    @BeforeClass
    public void startServer() {
        server = new HyperiaServer();
        server.start();
    }

    @AfterClass
    public void stopServer() {
        server.stop();
    }

    @Test
    public void canGetTheRootUri() {
        
    }
}