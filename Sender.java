import java.io.*;
import java.net.*;

public class Sender {
    public static void main(String[] args) {
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the receiver's IP address (e.g., 192.168.1.196): ");
            String receiverIp = userInput.readLine();

            Socket socket = new Socket(receiverIp, 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter a message to send (type 'exit' to quit): ");
            String message = userInput.readLine();

            int sequenceNumber = 0; // Initial sequence number

            for (char bit : message.toCharArray()) {
                System.out.println("Sender: Sending bit '" + bit + "'");
                out.println(bit);
                
                // Display message while waiting for acknowledgment
                System.out.println("Sender: Waiting for acknowledgment...");

                // Wait for acknowledgment
                BufferedReader ackReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String acknowledgment = ackReader.readLine();
                System.out.println("Sender: Acknowledgment received: " + acknowledgment);

                // Alternate the sequence number
                sequenceNumber = 1 - sequenceNumber;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
