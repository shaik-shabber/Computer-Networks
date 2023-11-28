import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the server IP address: ");
        String serverAddress = scanner.nextLine();

        int portNumber = 21;

        try {
            while (true) {
                Socket socket = new Socket(serverAddress, portNumber);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected to File Server. Enter file paths to request, type 'end' to disconnect.");

                System.out.print("Enter file path: ");
                String userInputString = userInput.readLine();
                serverWriter.println(userInputString);

                if (userInputString.equals("end")) {
                    // Close the socket and break the loop
                    socket.close();
                    break;
                }

                // Read and display the server's response
                String serverResponse = serverReader.readLine();
                System.out.println("Server Response: " + serverResponse);

                if (!serverResponse.equals("File not found")) {
                    System.out.println("File content:");
                    String line;
                    while (!(line = serverReader.readLine()).equals("DataEnd")) {
                        System.out.println(line);
                    }
                    System.out.println("Data in the file received completely.");
                }

                // Close the socket
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
