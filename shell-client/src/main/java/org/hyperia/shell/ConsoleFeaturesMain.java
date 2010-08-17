package org.hyperia.shell;

public class ConsoleFeaturesMain {

    public static void main(String[] args) {
        System.out.println("Hello");
        try {
            int consoleLines = 55;
            System.out.println("Hello");
            System.out.print((char) 27 + "[2J");

            System.out.println("After screen is cleared : lines=" + consoleLines);
            System.out.println("Then some more stuff");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < (consoleLines - 4); i++) {
                sb.append("\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}