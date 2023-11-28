import java.util.Arrays;
import java.util.Scanner;

public class DijkstraRouting {
    private int numRouters;
    private int[][] graph;
    private int[] distance;
    private boolean[] visited;

    public DijkstraRouting(int numRouters) {
        this.numRouters = numRouters;
        this.graph = new int[numRouters][numRouters];
        this.distance = new int[numRouters];
        this.visited = new boolean[numRouters];

        // Initialize the graph with maximum values and distances to infinity
        for (int i = 0; i < numRouters; i++) {
            Arrays.fill(graph[i], Integer.MAX_VALUE);
            distance[i] = Integer.MAX_VALUE;
        }
    }

    public void addEdge(int source, int destination, int weight) {
        graph[source][destination] = weight;
        graph[destination][source] = weight; // Assuming bidirectional links
    }

    public void findShortestPath(int sourceRouter) {
        distance[sourceRouter] = 0;

        for (int i = 0; i < numRouters - 1; i++) {
            int minDistance = getMinimumDistance();
            visited[minDistance] = true;

            for (int j = 0; j < numRouters; j++) {
                if (!visited[j] && graph[minDistance][j] != Integer.MAX_VALUE &&
                        distance[minDistance] != Integer.MAX_VALUE &&
                        (distance[minDistance] + graph[minDistance][j] < distance[j])) {
                    distance[j] = distance[minDistance] + graph[minDistance][j];
                }
            }
        }
    }

    private int getMinimumDistance() {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < numRouters; i++) {
            if (!visited[i] && distance[i] <= minDistance) {
                minDistance = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void printRoutingTable() {
        for (int i = 0; i < numRouters; i++) {
            System.out.println("Router " + i + " Distance: " + distance[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of routers: ");
        int numRouters = scanner.nextInt();

        Main dijkstra = new Main(numRouters);

        for (int i = 0; i < numRouters; i++) {
            for (int j = i + 1; j < numRouters; j++) {
                System.out.print("Enter the weight between Router " + i + " and Router " + j + ": ");
                int weight = scanner.nextInt();
                dijkstra.addEdge(i, j, weight);
            }
        }

        System.out.print("Enter the source router: ");
        int sourceRouter = scanner.nextInt();

        dijkstra.findShortestPath(sourceRouter);

        System.out.println("Shortest path distances from Router " + sourceRouter + ":");
        dijkstra.printRoutingTable();
    }
}
