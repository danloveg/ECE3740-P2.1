package servertest;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerTest {

    public static void main(String[] args) {

        InetAddress clientIPAddress;
        userinterface.StandardIO myUI = new userinterface.StandardIO();
        server.Server myServer = new server.Server(5555, 1);
        byte msg = ' ';
        /*The server accepts the following client commands:
         d:	Disconnect From Server
         t:	Request the time
         */

        //Server user menu
        myUI.display("1:\tQuit\n2:\tlisten\n3:\tSet Port\n4:\tGet Port\n5:\tget Client IP Address\n6:\tStart Server Socket\n");
        while (true) {
            String myCommand = myUI.getUserInput();
            switch (Integer.parseInt(myCommand)) {
                case 1: //QUIT
                    myServer.stopServer();
                    System.exit(-1);
                    break;
                case 3: //SET PORT
                    break;
                case 4: //GET PORT
                    break;
                case 5: //GET CLIENT IP ADDRESS
                    clientIPAddress = myServer.getClientIPAddress();
                    myUI.display(clientIPAddress.toString());
                    break;
                case 2: //LISTEN
                    boolean serverShouldListen = true;
                    while (serverShouldListen) {
                        myUI.display("Server is now listening, ...");
                        clientIPAddress = myServer.listen();
                        myUI.display("Received and accepted a client connection, ...");
                        myUI.display("the client's IP address is: " + clientIPAddress.toString());
                        myUI.display("Now interacting with Client");

                        boolean getNextCmd = true;
                        while (getNextCmd) {
                            msg = myServer.getMessageFromClient();
                            switch (msg) {
                                case 'd':
                                    myServer.sendMessageToClient((byte) 'd'); //Acknowledge
                                    myServer.disconnectClient();
                                    myUI.display("Client has been disconnected, by request");
                                    getNextCmd = false;
                                    break;
                                case 't':
                                    Calendar cal = Calendar.getInstance();
                                    cal.getTime();
                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                    for (int i = 0; i < sdf.format(cal.getTime()).length(); i++) {
                                        msg = (byte) sdf.format(cal.getTime()).charAt(i);
                                        myServer.sendMessageToClient(msg);
                                    }
                                    myUI.display("Sent date to client, ...");
                                    break;
                            }
                        }
                    }
                    break;
                case 6: //START SERVER SOCKET
                    myServer.startServer();
                    myUI.display("Server Socket has been created.");
                    break;
                default:
                    break;
            }
        }
    }
}
