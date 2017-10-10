package userinterface;

import java.io.*;

public class StandardIO {

    BufferedReader console = null;

    public StandardIO() {
        console = new BufferedReader(new InputStreamReader(System.in));
        if (console == null) {
            System.err.println("No console, exiting program.");
            System.exit(1);
        }
    }

    public String getUserInput() {
        String userInput = "no input";

        try {
            userInput = console.readLine();
            return userInput;
        } catch (IOException e) {
            System.err.println("No Standard Input device, exiting program.");
            System.exit(1);
        }
        return userInput;
    }
    
    public void display(String theResult) {
        System.out.println(theResult);
    }
}
