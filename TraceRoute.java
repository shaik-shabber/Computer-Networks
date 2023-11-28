import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TraceRoute {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter your selection:");
            System.out.println("1. Ping any IP address");
            System.out.println("2. Trace route to any IP address");
            System.out.println("3. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    System.out.print("Enter the IP address to ping: ");
                    String ipAddress = getUserInput();
                    executeCommand("ping", ipAddress);
                    break;
                case 2:
                    System.out.print("Enter the IP address to trace route: ");
                    ipAddress = getUserInput();
                    executeCommand("traceroute", ipAddress);
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static int getUserChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(getUserInput());
        } catch (NumberFormatException e) {
            // Invalid input
        }
        return choice;
    }

    private static String getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void executeCommand(String command, String ipAddress) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command, ipAddress);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
