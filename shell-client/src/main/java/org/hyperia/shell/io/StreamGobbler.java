package org.hyperia.shell.io;

import org.apache.log4j.*;

import java.io.*;

public class StreamGobbler extends Thread {
    private static final Logger log = Logger.getLogger(StreamGobbler.class);
    private final InputStream in;
    private final Log4JLevel log4JLevel;
    private final OutputStream out;



    public abstract static class Log4JLevel  {
        public static Log4JLevel info = new Log4JLevel() {
            @Override public void log(Logger log, String message) {
                log.info(message);
            }
        };

        public static Log4JLevel error = new Log4JLevel() {
            @Override public void log(Logger log, String message) {
                log.error(message);
            }
        };

        @SuppressWarnings("unused")
        public abstract void log(Logger logger, String message);
    }

    public StreamGobbler(InputStream in, Log4JLevel log4JLevel) {
        this(in, log4JLevel, null);
    }

    StreamGobbler(InputStream in, Log4JLevel log4JLevel, OutputStream redirect) {
        this.in = in;
        this.log4JLevel = log4JLevel;
        this.out = redirect;
    }

    public void run() {
        try {
            PrintWriter writer = null;
            if (out != null) {
                writer = new PrintWriter(out);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            while (line != null) {
                if (writer != null) {
                    writer.println(line);
                }
                log4JLevel.log(log, line);
                line = br.readLine();
            }
            if (writer != null) {
                writer.flush();
            }
        } catch (IOException e) {
            throw new IoRuntimeException("Exception gobbling stream ", e);
        }
    }
}