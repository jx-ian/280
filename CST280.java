package cst280;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author jiahaoxian
 */

public class CST280 {

    public static void main(String[] args) throws FileNotFoundException {
        CST280 graph = new CST280("input.txt");
        graph.printFile("output.txt");
        graph.printOutput();
        graph.reverseGraph();
        graph.printReverseGraph();
    }
    

    private Map<Integer, List<Integer>> adjList;

    public CST280(String inputFileName) throws FileNotFoundException {
        this.adjList = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(inputFileName))) {
            while (scanner.hasNextInt()) {
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                addEdge(source, target);
            }
        }
    }
    

    private void addEdge(int source, int target) {
        List<Integer> adjNodes = this.adjList.getOrDefault(source, new ArrayList<>());
        adjNodes.add(target);
        this.adjList.put(source, adjNodes);
    }
    
    
    public void reverseGraph() {
        Map<Integer, List<Integer>> reverseAdjList = new HashMap<>();
        for (int vertex : this.adjList.keySet()) {
            List<Integer> adjNodes = this.adjList.get(vertex);
            for (int adjNode : adjNodes) {
                reverseAdjList.computeIfAbsent(adjNode, k -> new ArrayList<>()).add(vertex);
            }
        }
        this.adjList = reverseAdjList;
    }
    

    public void printFile(String outputFileName) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(outputFileName))) {
            for (int vertex : this.adjList.keySet()) {
                List<Integer> adjNodes = this.adjList.get(vertex);
                int outDegree = adjNodes.size();
                writer.print(vertex + " " + outDegree);

                for (int adjNode : adjNodes) {
                    writer.print(" " + adjNode);
                }
                writer.println();
            }
        }
    }
    

    public void printOutput() {
        for (int vertex : this.adjList.keySet()) {
            List<Integer> adjNodes = this.adjList.get(vertex);
            int outDegree = adjNodes.size();
            System.out.print("Vertex " + vertex + ": rank = -1" + ", Out-degree = " + outDegree +"\n");
            System.out.print("Edges from " + vertex + " to: ");
            for (int i = 0; i < adjNodes.size(); i++) {
                if (i == adjNodes.size() - 1) {
                    System.out.print(adjNodes.get(i) + ".");
                } else {
                    System.out.print(adjNodes.get(i) + ", ");
                }
            }
            System.out.println("\n");
        }
    }
    
    
    public void printReverseGraph() {
        for (int vertex : this.adjList.keySet()) {
            System.out.print("Vertex " + vertex + ": ");
            List<Integer> adjNodes = this.adjList.get(vertex);
            for (int i = 0; i < adjNodes.size(); i++) {
                if (i == adjNodes.size() - 1) {
                    System.out.print(adjNodes.get(i) + ".");
                } else {
                    System.out.print(adjNodes.get(i) + ", ");
                }
            }
            System.out.println();
        }
    }
}
