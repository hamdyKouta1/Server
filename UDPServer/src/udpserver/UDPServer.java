package udpserver;

import JDBC.JDBC;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.ParseException;

public class UDPServer {

    
    public static void main(String[] args) throws ClassNotFoundException {
            JDBC mesg = new JDBC();

        try {
            // Create a DatagramSocket instance
            DatagramSocket serverSocket = new DatagramSocket(new InetSocketAddress("127.0.0.1", 5001));
            System.out.println("Listening for clients...");

            // Buffer to hold incoming data
            byte[] receiveData = new byte[1024];

            // Keep the server running to accept multiple connections
            while (true) {
                // Receive incoming DatagramPacket
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // Extract client information
                String clientSocketAddress = receivePacket.getAddress().toString();
                int clientSocketPort = receivePacket.getPort();
                System.out.println("[IP: " + clientSocketAddress + " ,Port: " + clientSocketPort + "]  " + "Client Connection Successful!");
                String portString = Integer.toString(clientSocketPort);
                // Extract client message
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(clientMessage);
                        mesg.saveChatMsg(portString, "UDP server", clientMessage);

                // Send response back to client
                String serverMessage = "Hi, this is coming from Server!";
                byte[] sendData = serverMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
