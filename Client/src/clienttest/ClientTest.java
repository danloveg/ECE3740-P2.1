package clienttest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientTest {
    
    public static void main(String[] args) {
        userinterface.StandardIO myUI = new userinterface.StandardIO();
        client.Client clientSocket = new client.Client();
        
        boolean quit = false;
        
        myUI.display("c:\tConnect to server\nq:\tQuit program\nt:\tGet the time from server\nd:\tDisconnect from server\n");
        
        while (true != quit) {
            String command = myUI.getUserInput().trim();
            
            // Try finding a command
            switch (command) {
                // Connect to the server
                case "c":
                    clientSocket.ConnectToServer();
                    myUI.display("Successfully connected to server on port " + 
                                  clientSocket.GetPortNumber());
                    break;
                // Disconnect from server
                case "d":
                    clientSocket.SendMessageToServer((byte) 'd');
                    clientSocket.DisconnectFromServer();
                    myUI.display("Disconnected from server on port " + 
                                  clientSocket.GetPortNumber());
                    break;
                // Disconnect from server and exit program
                case "q":
                    clientSocket.SendMessageToServer((byte) 'd');
                    clientSocket.DisconnectFromServer();
                    myUI.display("Disconnected from server, quiting...");
                    quit = true;
                    break;
                // Get the time from the server
                case "t":
                    clientSocket.SendMessageToServer((byte) 't');
                    String message = clientSocket.GetMessageFromServer();
                    myUI.display("Response: " + message);
                    break;
                // Command not recognized, notify the user
                default:
                    myUI.display("Command \"" + command + "\" not recognized.");
                    break;
            }
        }
    }
}
