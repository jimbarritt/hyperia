package org.hyperia.game;

import org.junit.*;

public class HyperiaServerIntegrationTest {
    private static final HyperiaServer server = new HyperiaServer();

    @BeforeClass
    public static void startServer() {        
        server.start();
    }

    @AfterClass
    public static void stopServer() {
        server.stop();
    }

    @Test
    public void canGetTheRootUri() {
        
    }
    
}