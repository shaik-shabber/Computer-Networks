import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkDiagnostics {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter your selection:");
            System.out.println("1. Ping any IP address");
            System.out.println("2. Trace route to any IP address");
            System.out.println("3. Exit");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter your choice : " );
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter the IP address to ping : ");
                        String pingAddress = reader.readLine();
                        runPing(pingAddress);
                        break;
                    case 2:
                        System.out.print("Enter the IP address to trace route:");
                        String traceRouteAddress = reader.readLine();
                        runTracert(traceRouteAddress);
                        break;
                    case 3:
                        System.out.print("Exiting the program.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static void runPing(String ipAddress) {
        try {
            Process pingProcess = Runtime.getRuntime().exec("ping " + ipAddress);
            printCommandOutput(pingProcess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private static void runTracert(String ipAddress) {
        try {
            Process traceRouteProcess = Runtime.getRuntime().exec("tracert " + ipAddress);
            printCommandOutput(traceRouteProcess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private static void printCommandOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
