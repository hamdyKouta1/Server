package msgPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TcpClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("127.0.0.1", 5001), 1000);

            // Send message to the server
            String message = request.getParameter("message");
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            dataOut.writeUTF(message);
            dataOut.flush();

            // Receive response from the server
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            String serverMessage = dataIn.readUTF();

            // Write server's response to the client's browser
            response.getWriter().println("Server Response: " + serverMessage);

        } catch (IOException e) {
            // Handle connection or IO errors
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
