package com.github.angrycellophane.foobar.l4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem:
 * Escape Pods
 * ===========
 *
 * You've blown up the LAMBCHOP doomsday device and broken the bunnies out of Lambda's prison - and now you need to escape from the space station as quickly and as orderly as possible! The bunnies have all gathered in various locations throughout the station, and need to make their way towards the seemingly endless amount of escape pods positioned in other parts of the station. You need to get the numerous bunnies through the various rooms to the escape pods. Unfortunately, the corridors between the rooms can only fit so many bunnies at a time. What's more, many of the corridors were resized to accommodate the LAMBCHOP, so they vary in how many bunnies can move through them at a time.
 *
 * Given the starting room numbers of the groups of bunnies, the room numbers of the escape pods, and how many bunnies can fit through at a time in each direction of every corridor in between, figure out how many bunnies can safely make it to the escape pods at a time at peak.
 *
 * Write a function solution(entrances, exits, path) that takes an array of integers denoting where the groups of gathered bunnies are, an array of integers denoting where the escape pods are located, and an array of an array of integers of the corridors, returning the total number of bunnies that can get through at each time step as an int. The entrances and exits are disjoint and thus will never overlap. The path element path[A][B] = C describes that the corridor going from A to B can fit C bunnies at each time step.  There are at most 50 rooms connected by the corridors and at most 2000000 bunnies that will fit at a time.
 *
 * For example, if you have:
 * entrances = [0, 1]
 * exits = [4, 5]
 * path = [
 *   [0, 0, 4, 6, 0, 0],  # Room 0: Bunnies
 *   [0, 0, 5, 2, 0, 0],  # Room 1: Bunnies
 *   [0, 0, 0, 0, 4, 4],  # Room 2: Intermediate room
 *   [0, 0, 0, 0, 6, 6],  # Room 3: Intermediate room
 *   [0, 0, 0, 0, 0, 0],  # Room 4: Escape pods
 *   [0, 0, 0, 0, 0, 0],  # Room 5: Escape pods
 * ]
 *
 * Then in each time step, the following might happen:
 * 0 sends 4/4 bunnies to 2 and 6/6 bunnies to 3
 * 1 sends 4/5 bunnies to 2 and 2/2 bunnies to 3
 * 2 sends 4/4 bunnies to 4 and 4/4 bunnies to 5
 * 3 sends 4/6 bunnies to 4 and 4/6 bunnies to 5
 *
 * So, in total, 16 bunnies could make it to the escape pods at 4 and 5 at each time step.  (Note that in this example, room 3 could have sent any variation of 8 bunnies to 4 and 5, such as 2/6 and 6/6, but the final solution remains the same.)
 *
 * Solution:
 * Let's say there is a graph where every node of the graph represents a room and and an edge between nodes is a corridor that is constrained by a given capacity.
 * With the given source (the entrances) nodes and sink nodes (the exists) this becomes a flow network - https://en.wikipedia.org/wiki/Flow_network
 * Finding the total number of bunnies that can get through at each time step is the same as finding the maximum flow between each source and sink nodes in the given flow network.
 * Dinic's algorithm can be used to find the maximum flow between each source and sink nodes, the total result is the sum of the maximum flows.
 * https://en.wikipedia.org/wiki/Dinic%27s_algorithm
 */

public class L4Solution2 {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        Graph graph = Graph.from(path);
        int maxFlow = 0;
        for (int entrance : entrances) {
            for (int exit : exits) {
                maxFlow += graph.maxFlow(entrance, exit);
            }
        }
        return maxFlow;
    }

    static class Graph {
        final List<Edge>[] nodes;

        @SuppressWarnings("unchecked")
        private Graph(int size) {
            this.nodes = new List[size];
            for (int i = 0; i < size; i++) {
                this.nodes[i] = new ArrayList<>();
            }
        }

        private void addEdge(int from, int to, int capacity) {
            this.nodes[from].add(new Edge(to, nodes[to].size(), capacity));
            this.nodes[to].add(new Edge(from, nodes[from].size() - 1, 0));
        }

        private boolean dinicBfs(int from, int to, int[] distance) {
            Arrays.fill(distance, -1);
            distance[from] = 0;
            int[] queue = new int[nodes.length];
            int queueSize = 0;
            queue[queueSize++] = from;
            for (int i = 0; i < queueSize; i++) {
                int node = queue[i];
                for (Edge edge : this.nodes[node]) {
                    if (distance[edge.to] < 0 && edge.flow < edge.capacity) {
                        distance[edge.to] = distance[node] + 1;
                        queue[queueSize++] = edge.to;
                    }
                }
            }

            return distance[to] >= 0;
        }

        private int dinicDfs(int[] ptr, int[] distance, int to, int from, int baseFlow) {
            if (from == to)
                return baseFlow;
            for (; ptr[from] < nodes[from].size(); ++ptr[from]) {
                Edge edge = nodes[from].get(ptr[from]);
                if (distance[edge.to] == distance[from] + 1 && edge.flow < edge.capacity) {
                    int adjustedFlow = dinicDfs(ptr, distance, to, edge.to, Math.min(baseFlow, edge.capacity - edge.flow));
                    if (adjustedFlow > 0) {
                        edge.flow += adjustedFlow;
                        nodes[edge.to].get(edge.index).flow -= adjustedFlow;
                        return adjustedFlow;
                    }
                }
            }
            return 0;
        }

        public int maxFlow(int from, int to) {
            int flow = 0;
            int[] distance = new int[nodes.length];
            while (dinicBfs(from, to, distance)) {
                int[] ptr = new int[nodes.length];
                while (true) {
                    int df = dinicDfs(ptr, distance, to, from, Integer.MAX_VALUE);
                    if (df == 0)
                        break;
                    flow += df;
                }
            }
            return flow;
        }

        public static Graph from(int[][] path) {
            Graph graph = new Graph(path.length);
            for (int i = 0; i < path.length; i++) {
                for (int j = 0; j < path[i].length; j++) {
                    if (path[i][j] != 0) {
                        graph.addEdge(i, j, path[i][j]);
                    }
                }
            }
            return graph;
        }
    }

    static class Edge {
        final int to; // represents target node of the edge
        final int index; // index of the reversed edge in adjacency list to quickly find it.
        final int capacity;
        int flow; // flow value in the edge

        public Edge(int to, int index, int capacity) {
            this.to = to;
            this.index = index;
            this.capacity = capacity;
        }
    }
}
