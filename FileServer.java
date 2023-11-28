import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) {
        int portNumber = 21;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("File Server is running on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from: " + clientSocket.getInetAddress());

                // Handle client request in a new thread
                new Thread(new FileServerHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class FileServerHandler implements Runnable {
    private Socket clientSocket;

    public FileServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.equals("end")) {
                    break; // Disconnect on "end" message
                }

                System.out.println("Requested file path: " + clientMessage);

                String filePath = clientMessage;
                sendFileContent(filePath, writer);
            }

            System.out.println("Connection closed for: " + clientSocket.getInetAddress());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFileContent(String filePath, PrintWriter writer) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            writer.println("File found");

            System.out.println("Data found. Sending data...");

            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.println(line);
            }
            writer.println("DataEnd"); // End of file content marker
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Sending message to the client.");
            writer.println("File not found");
        }
    }
}
