import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DijkstraAlgorithm_o {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of routers: ");
        int size = scanner.nextInt();

        String[] routerNames = new String[size];
        Map<String, Integer> routerIndices = new HashMap<>();

        System.out.println("Enter the names of routers:");
        for (int i = 0; i < size; i++) {
            routerNames[i] = scanner.next();
            routerIndices.put(routerNames[i], i);
        }

        int[][] adjacencyMatrix = readAdjacencyMatrix(size, scanner);
        dijkstra(routerNames, routerIndices, adjacencyMatrix);

        scanner.close();
    }

    private static int[][] readAdjacencyMatrix(int size, Scanner scanner) {
        int[][] adjacencyMatrix = new int[size][size];

        System.out.println("Enter the adjacency matrix (use -1 for no connection):");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }

        return adjacencyMatrix;
    }

    private static void dijkstra(String[] routerNames, Map<String, Integer> routerIndices, int[][] graph) {
        int size = graph.length;
        int[] distance = new int[size];
        Arrays.fill(distance, INF);
        distance[0] = 0;

        boolean[] visited = new boolean[size];

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(size, (a, b) -> Integer.compare(a.cost, b.cost));
        priorityQueue.add(new Node(0, 0));

        while (!priorityQueue.isEmpty()) {
            int currentNode = priorityQueue.poll().node;

            if (visited[currentNode]) continue;

            visited[currentNode] = true;

            for (int i = 0; i < size; i++) {
                if (!visited[i] && graph[currentNode][i] != -1) {
                    int newDistance = distance[currentNode] + graph[currentNode][i];

                    if (newDistance < distance[i]) {
                        distance[i] = newDistance;
                        priorityQueue.add(new Node(i, newDistance));
                    }
                }
            }
        }

        // Print the result
        System.out.println("Router\tOrder\tCost");
        for (int i = 0; i < size; i++) {
            System.out.println(routerNames[i] + "\t" + i + "\t" + (distance[i] == INF ? "Infinity" : distance[i]));
        }
    }

    static class Node {
        int node;
        int cost;

        Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }
}
