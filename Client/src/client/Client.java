package client;

import java.io.*;
import java.net.*;

public class Client {
    BufferedReader input;
    OutputStream output;
    int portNumber;
    Socket clientSocket = null;
    
    
    /**
     * Create a new client with default port number 5555.
     */
    public Client() {
       this.portNumber = 5555;   
    }
    
    
    /**
     * Create a new client with a specified port number.
     * @param portNumber The port number to connect to.
     */
    public Client(int portNumber) {
        this.portNumber = portNumber;
    }
    
    
    /**
     * Connect to a server on the port number. Gets the local IP address to use
     * as the host (InetAddress.getLocalHost()).
     */
    public void ConnectToServer() {
        if (null == this.clientSocket) {
            try {
                clientSocket = new Socket(InetAddress.getLocalHost(), portNumber);
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = clientSocket.getOutputStream();
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }
    
    
    /**
     * Disconnect from the server. Closes the client connection to the server
     * and closes the input and output streams.
     */
    public void DisconnectFromServer() {
        try {
            // Close the socket
            if (null != this.clientSocket) {
                clientSocket.close();
                clientSocket = null;
            }

            // Close input
            if (null != this.input) {
                input.close();
                input = null;
            }

            // Close output
            if (null != this.output) {
                output.close();
                output = null;
            }
        } catch (IOException e) {
            System.err.println("Could not close client connection, exiting program.");
            System.exit(1);
        }
    }
    
    
    /**
     * Send a byte to the server.
     * @param command The byte to send to the server.
     */
    public void SendMessageToServer(byte command) {
        
        if (null != this.clientSocket && null != this.output) {
            try  {
                output.write(command);
                output.flush();
            } catch (IOException e) {
                System.err.println("Cannot send to socket, exiting program.");
                System.exit(1);
            }
        }
    }
    
    
    /**
     * Get a message from the server.
     * @return The string from the server.
     */
    public String GetMessageFromServer() {
        StringBuilder message = new StringBuilder(20);
        char serverByte;
        boolean timeout = false;
        
        if (null != this.clientSocket && null != this.input) {
            try {
                // Wait for the stream to be ready. Timeout after 200ms.
                long startTime = System.currentTimeMillis();
                while (!this.input.ready() && false == timeout) {
                    if (System.currentTimeMillis() - startTime > 200) {
                        timeout = true;
                        System.err.println("Timed out!");
                    }
                }
                
                // If there was no timeout, get the characters from the server.

                if (false == timeout) {
                    // NOTE: this is a terrible way of getting the message from the
                    //       server, because it will only ever get 8 (and ONLY 8)
                    //       characters from the server. If it sends less, this
                    //       loop will get stuck. There was no terminating byte
                    //       or message to work with, though.
                    while (message.length() < 8) {
                        if (this.input.ready()) {
                            serverByte = (char) input.read();
                            message.append(serverByte);
                        }
                    }
                }
                
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            }
        }
        return message.toString();
    }
    
    /**
     * Get the current port number.
     * @return The port number.
     */
    public int GetPortNumber() {
        return this.portNumber;
    }
    
    public String toString() {
        return "Current port number: " + this.portNumber;
    }
}
