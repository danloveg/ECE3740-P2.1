package server;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server {

    InputStream input;
    OutputStream output;
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    byte msg = ' ', clientCommand = ' ';
    int portNumber = 5555, backlog = 1;

    public Server(int portNumber, int backlog) {
        this.portNumber = portNumber;
        this.backlog = backlog;
    }

    public void startServer() {
        if (serverSocket != null) {
            stopServer();
        } else {
            try {
                serverSocket = new ServerSocket(portNumber, backlog);
            } catch (IOException e) {
                System.err.println("Cannot create ServerSocket, exiting program.");
                System.exit(1);
            } finally {
            }
        }

    }

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Cannot close ServerSocket, exiting program.");
                System.exit(1);
            } finally {
            }

        }
    }

    public void disconnectClient() {
        try {
            clientSocket.close();
            input = null;
            output = null;
            clientSocket = null;
        } catch (IOException e) {
            System.err.println("cannot close stream/client socket; exiting.");
            System.exit(1);
        } finally {
        }
    }

    public InetAddress listen() {
        try {
            clientSocket = serverSocket.accept();
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("listen exception, exiting program.");
            System.exit(1);
        } finally {
            return clientSocket.getInetAddress();
        }
    }

    public byte getMessageFromClient() {
        try {
            msg = (byte) input.read();
        } catch (IOException e) {
            System.err.println("cannot read from socket; exiting program.");
            System.exit(1);
        } finally {
            return msg;
        }
    }

    public void sendMessageToClient(byte msg) {
        try {
            output.write(msg);
            output.flush();
        } catch (IOException e) {
            System.err.println("cannot send to socket; exiting program.");
            System.exit(1);
        } finally {
        }
    }

    public InetAddress getClientIPAddress() {
        return clientSocket.getInetAddress();
    }

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPort() {
        return this.portNumber;
    }
}