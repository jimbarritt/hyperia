package org.hyperia.shell.io;

import org.junit.*;

import java.lang.reflect.*;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

/**
 * For this to work, you have to add the following in your log4j.xml
 * <param name="Follow" value="true"/>
 */
public class InternalShellTest {
    
    @Test
    public void executesSimpleCommand() {
        InternalShell internalShell = new InternalShell(SimpleCommandMain.class);

        String response = internalShell.execute();

        assertThat(response, containsString("Hello world"));
    }

    @Test
    public void executesCommandWithArgs() {
        InternalShell internalShell = new InternalShell(SayHelloCommandMain.class).withArg("foo").withArg("bar");

        String response = internalShell.execute();

        assertThat(response, containsString("Hello [foo] [bar]"));
    }

    @Test
    public void testInvokeMain() throws Exception {
        Method mainMethod = SimpleCommandMain.class.getMethod("main", new Class[]{String[].class});

        mainMethod.invoke(null, new Object[]{new String[]{}});
    }


}