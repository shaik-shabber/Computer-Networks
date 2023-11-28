import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DijkstraAlgorithm_OSPF {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of routers: ");
            int size = scanner.nextInt();

            String[] routerNames = new String[size];

            System.out.println("Enter the names of routers:");
            for (int i = 0; i < size; i++) {
                routerNames[i] = scanner.next();
            }

            int[][] adjacencyMatrix = readAdjacencyMatrix("network.txt", size);
            dijkstra(routerNames, adjacencyMatrix);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] readAdjacencyMatrix(String filePath, int size) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int[][] adjacencyMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            String[] values = br.readLine().trim().split("\\s+");
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = Integer.parseInt(values[j]);
            }
        }

        br.close();
        return adjacencyMatrix;
    }

    private static void dijkstra(String[] routerNames, int[][] graph) {
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
