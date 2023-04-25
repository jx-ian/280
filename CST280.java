package cst280;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * @author jiahaoxian
 */

public class CST280{

    public static void main(String[] args) {
        try {
            CST280 graph = new CST280("input.txt");
            graph.printOutput(0.85, 10);
            graph.printOutputToFile(0.85, 10, "pagerank.txt");
            } 
        catch (FileNotFoundException e) {
            System.out.println("input file not found!");
        }
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

   
    public void printOutput(double dampingFactor, int maxIterations) throws FileNotFoundException {
        Map<Integer, Double> pageRank = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        double initialRank = 1.0 / this.adjList.size();

        for (int vertex : this.adjList.keySet()) {
            pageRank.put(vertex, initialRank);
            inDegree.put(vertex, 0);
        }

        for (int vertex : this.adjList.keySet()) {
            for (int neighbor : this.adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }
        

        for (int i = 0; i < maxIterations; i++) {
            Map<Integer, Double> newPageRank = new HashMap<>();
            double sum = 0.0;
            for (int vertex : this.adjList.keySet()) {
                double rank = 0.0;
                for (int neighbor : this.adjList.get(vertex)) {
                    rank += pageRank.get(neighbor) / this.adjList.get(neighbor).size();
                }
                newPageRank.put(vertex, rank);
                sum += rank;
            }
            for (int vertex : this.adjList.keySet()) {
                pageRank.put(vertex, dampingFactor * (newPageRank.get(vertex) / sum) + (1.0 - dampingFactor) * initialRank);
            }
        }
        
        for (int vertex : this.adjList.keySet()) {
            double rank = pageRank.get(vertex);
            int outDegree = this.adjList.get(vertex).size();
            int inDegreeVal = inDegree.get(vertex);
            String adjNodes = String.join(", ", this.adjList.get(vertex).stream().map(n -> Integer.toString(n)).collect(Collectors.toList()));
            adjNodes += ".";
            System.out.print("Vertex " + vertex + ":");
            System.out.print(" Rank = " + rank);
            System.out.print(", \nIn-degree = " + inDegreeVal);
            System.out.print(", Out-degree = " + outDegree);
            System.out.println(", \nAdjacent nodes = " + adjNodes);
            List<Integer> adjNodesReverse = new ArrayList<>();
            for (int neighbor : this.adjList.keySet()) {
                if (this.adjList.get(neighbor).contains(vertex)) {
                    adjNodesReverse.add(neighbor);
                }
            }
            String adjNodesReverseStr = String.join(", ", adjNodesReverse.stream().map(n -> Integer.toString(n)).collect(Collectors.toList()));
            adjNodesReverseStr += ".";
            System.out.println("Reverse Adjacent nodes = " + adjNodesReverseStr);
            System.out.println("");
        }
    }
    
    
        public void printOutputToFile(double dampingFactor, int maxIterations, String outputFile) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(outputFile);
        Map<Integer, Double> pageRank = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        double initialRank = 1.0 / this.adjList.size();

        for (int vertex : this.adjList.keySet()) {
            pageRank.put(vertex, initialRank);
            inDegree.put(vertex, 0);
        }

        for (int vertex : this.adjList.keySet()) {
            for (int neighbor : this.adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        for (int i = 0; i < maxIterations; i++) {
            Map<Integer, Double> newPageRank = new HashMap<>();
            double sum = 0.0;
            for (int vertex : this.adjList.keySet()) {
                double rank = 0.0;
                for (int neighbor : this.adjList.get(vertex)) {
                    rank += pageRank.get(neighbor) / this.adjList.get(neighbor).size();
                }
                newPageRank.put(vertex, rank);
                sum += rank;
            }
            for (int vertex : this.adjList.keySet()) {
                pageRank.put(vertex, dampingFactor * (newPageRank.get(vertex) / sum) + (1.0 - dampingFactor) * initialRank);
            }
        }

        for (int vertex : this.adjList.keySet()) {
            double rank = pageRank.get(vertex);
            int outDegree = this.adjList.get(vertex).size();
            int inDegreeVal = inDegree.get(vertex);
            String adjNodes = String.join(", ", this.adjList.get(vertex).stream().map(n -> Integer.toString(n)).collect(Collectors.toList()));
            adjNodes += ".";
            writer.print("Vertex " + vertex + ":");
            writer.print(" Rank = " + rank);
            writer.print(", \nIn-degree = " + inDegreeVal);
            writer.print(", Out-degree = " + outDegree);
            writer.println(", \nAdjacent nodes = " + adjNodes);
            List<Integer> adjNodesReverse = new ArrayList<>();
            for (int neighbor : this.adjList.keySet()) {
                if (this.adjList.get(neighbor).contains(vertex)) {
                    adjNodesReverse.add(neighbor);
                }
            }
            String adjNodesReverseStr = String.join(", ", adjNodesReverse.stream().map(n -> Integer.toString(n)).collect(Collectors.toList()));
            adjNodesReverseStr += ".";
            writer.println("Reverse Adjacent nodes = " + adjNodesReverseStr);
            writer.println("");
        }

        writer.close();
    }
}
