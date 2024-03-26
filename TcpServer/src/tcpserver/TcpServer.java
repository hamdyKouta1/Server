package tcpserver;

import JDBC.JDBC;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.io.IOException;
import java.sql.Timestamp;

public class TcpServer {

    public static Timestamp time;

    public static void main(String[] args) throws ClassNotFoundException {
        JDBC mesg = new JDBC();
        try {
            time = new Timestamp(System.currentTimeMillis());
            // Here, we create a ServerSocket instance named serverSocket
            ServerSocket serverSocket = new ServerSocket(5001);
            System.out.println("Listening for clients...");

            // Keep the server running to accept multiple connections
            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                String clientSocketIP = clientSocket.getInetAddress().toString();
                int clientSocketPort = clientSocket.getPort();
                String portSring = Integer.toString(clientSocketPort);
                System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort + "]  " + "Client Connection Successful!");

                // Input and output streams for communication with the client
                DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

                String clientMessage;
                do {
                    try {
                        // Read message from client
                        clientMessage = dataIn.readUTF();

                        System.out.println(clientMessage);

                        mesg.saveChatMsg(portSring, "TCP", clientMessage);

                        // Send response back to client
                        String serverMessage = "Hi, this is coming from Server!";
                        dataOut.writeUTF(serverMessage);
                    } catch (IOException e) {
                        // Handle EOFException when client closes connection
                        System.out.println("Client closed connection.");
                        break;
                    }
                } while (!clientMessage.equalsIgnoreCase("exit"));

                // Close streams and client socket
                dataIn.close();
                dataOut.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
