import java.io.*;
import java.net.*;

public class Receiver {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Receiver: Waiting for a connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Receiver: Connection established with the sender.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String receivedMessage = "";
            int expectedSequenceNumber = 0;

            while (true) {
                String bit = in.readLine();
                if (bit == null || bit.equalsIgnoreCase("exit")) {
                    break;
                }

                // Simulate processing the received bit
                // Here, we're just acknowledging the bit received
                System.out.println("Receiver: Received bit: " + bit);

                // Send acknowledgment with the corresponding sequence number
                String acknowledgment = "Acknowledgment for bit " + bit + " with sequence number " + expectedSequenceNumber;
                out.println(acknowledgment);
                System.out.println("Receiver: Sent acknowledgment: " + acknowledgment);

                // Alternate the expected sequence number
                expectedSequenceNumber = 1 - expectedSequenceNumber;

                receivedMessage += bit;
            }

            System.out.println("Receiver: Complete message received: " + receivedMessage);
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
