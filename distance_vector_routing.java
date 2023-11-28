import java.util.Scanner;

public class distance_vector_routing {
    public static void main(String[] args)
     {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of routers: ");
        int numRouters = scanner.nextInt();
        
        String[] routerNames = new String[numRouters];
        for (int i = 0; i < numRouters; i++) {
            System.out.print("Enter the name of Router " + (i + 1) + ": ");
            routerNames[i] = scanner.next();
        }
        
        int[][] distanceMatrix = new int[numRouters][numRouters];
        
        System.out.println("Enter the distance between routers (use -1 for infinity):");
        for (int i = 0; i < numRouters; i++) {
            for (int j = 0; j < numRouters; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    System.out.print("Distance from " + routerNames[i] + " to " + routerNames[j] + ": ");
                    int distance = scanner.nextInt();
                    if (distance < -1) {
                        System.out.println("Distance cannot be less than -1. Setting to -1 (infinity).");
                        distance = -1;
                    }
                    distanceMatrix[i][j] = distance;
                }
            }
        }

        DistanceVectorRouting dvRouting = new DistanceVectorRouting(numRouters, routerNames, distanceMatrix);
        dvRouting.runRoutingAlgorithm();

        // Print the routing table for each router
        for (int i = 0; i < numRouters; i++) {
            System.out.println("Routing Table for " + routerNames[i]);
            dvRouting.printRoutingTable(i);
            System.out.println();
        }
    }
}

class DistanceVectorRouting {
    private int numRouters;
    private String[] routerNames;
    private int[][] distanceMatrix;
    private int[][] routingTable;

    public DistanceVectorRouting(int numRouters, String[] routerNames, int[][] distanceMatrix) {
        this.numRouters = numRouters;
        this.routerNames = routerNames;
        this.distanceMatrix = distanceMatrix;
        this.routingTable = new int[numRouters][numRouters];

        // Initialize the routing table with direct links
        for (int i = 0; i < numRouters; i++) {
            for (int j = 0; j < numRouters; j++) {
                routingTable[i][j] = (i == j) ? i : -1;
            }
        }
    }

    public void runRoutingAlgorithm() {
        for (int k = 0; k < numRouters; k++) {
            for (int i = 0; i < numRouters; i++) {
                for (int j = 0; j < numRouters; j++) {
                    if (distanceMatrix[i][k] != -1 && distanceMatrix[k][j] != -1) {
                        int newDistance = distanceMatrix[i][k] + distanceMatrix[k][j];
                        if (newDistance < distanceMatrix[i][j] || distanceMatrix[i][j] == -1) {
                            distanceMatrix[i][j] = newDistance;
                            routingTable[i][j] = k;
                        }
                    }
                }
            }
        }
    }

    public void printRoutingTable(int router) {
        for (int i = 0; i < numRouters; i++) {
            if (i != router) {
                System.out.println("To " + routerNames[i] + " via " + (routingTable[router][i] != -1 ? routerNames[routingTable[router][i]] : "Direct") + ": Distance " + distanceMatrix[router][i]);
            }
        }
    }
}
