import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 12345; // Port on which the server will listen

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle the client's request
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Create input and output streams for communication with the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read and display messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client: " + message);

                // Echo the message back to the client
                out.println("Server: " + message);
            }

            // Close the client socket when the client disconnects
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}