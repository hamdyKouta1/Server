/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package msgPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elrayes
 */
public class UdpClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String message = request.getParameter("message");

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            // Get the IP address of the UDP server (replace "localhost" with the actual server IP)
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 5001;

            // Convert message to bytes
            byte[] sendData = message.getBytes();

            // Create a DatagramPacket to send to the server
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Send the packet to the server
            clientSocket.send(sendPacket);

            response.getWriter().println("Message sent successfully to the server.");
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().println("Error sending message: " + e.getMessage());
        }
    }
}
