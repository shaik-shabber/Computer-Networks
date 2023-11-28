import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int serverPort = 12345; // Port on which the server is listening

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            // Create input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String message=" ";
            Scanner sc = new Scanner(System.in);
            // Send a message to the server
            do
            {
            System.out.println("Enter the message to send to the server(to exit type (exit)): ");
            message = sc.nextLine();
            if(message.equalsIgnoreCase("exit"))
            {
                break;
            }
            out.println(message);

            // Read and display the server's response
            String response = in.readLine();
            System.out.println("Server response: " + response);
            } while(true);

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}