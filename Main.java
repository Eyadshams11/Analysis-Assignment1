import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> graph;
    private Set<Integer> visited;
    private Set<List<Integer>> cycles;

    public Graph() {
        this.graph = new HashMap<>();
        this.visited = new HashSet<>();
        this.cycles = new HashSet<>();
    }

    public void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    public void dfs(int node, String order) {
        System.out.println("DFS:");
        visited.clear();
        _dfs(node, order);
        System.out.println();
    }

    private void _dfs(int node, String order) {
        if (!visited.contains(node)) {
            System.out.print(node + " ");
            visited.add(node);
            List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(node, Collections.emptyList()));
            neighbors.sort(order.equals("left") ? Comparator.naturalOrder() : Comparator.reverseOrder());
            for (int neighbor : neighbors) {
                _dfs(neighbor, order);
            }
        }
    }

    public void bfs(int node, String order) {
        System.out.println("BFS:");
        visited.clear();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");
            List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(currentNode, Collections.emptyList()));
            neighbors.sort(order.equals("left") ? Comparator.naturalOrder() : Comparator.reverseOrder());
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public boolean hasCycle() {
        visited.clear();
        Set<Integer> stack = new HashSet<>();

        for (int node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (_hasCycle(node, stack, new ArrayList<>())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean _hasCycle(int node, Set<Integer> stack, List<Integer> currentCycle) {
        visited.add(node);
        stack.add(node);
        currentCycle.add(node);

        List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(node, Collections.emptyList()));
        for (int neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (_hasCycle(neighbor, stack, currentCycle)) {
                    return true;
                }
            } else if (stack.contains(neighbor)) {
                currentCycle.add(neighbor);
                cycles.add(new ArrayList<>(currentCycle));
                currentCycle.remove(currentCycle.size() - 1);
                return true;
            }
        }

        stack.remove(node);
        currentCycle.remove(currentCycle.size() - 1);
        return false;
    }

    public void printCycles() {
        System.out.println("Cycles:");
        for (List<Integer> cycle : cycles) {
            System.out.println(cycle);
        }
    }

    public boolean isBipartite() {
        Map<Integer, Integer> color = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        color.put(1, 0);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            List<Integer> neighbors = new ArrayList<>(graph.getOrDefault(currentNode, Collections.emptyList()));
            for (int neighbor : neighbors) {
                if (!color.containsKey(neighbor)) {
                    color.put(neighbor, 1 - color.get(currentNode));
                    queue.offer(neighbor);
                } else if (color.get(neighbor).equals(color.get(currentNode))) {
                    return false;
                }
            }
        }

        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);
        graph.addEdge(4, 2);

        graph.dfs(1, "left");
        graph.bfs(1, "left");

        if (graph.hasCycle()) {
            System.out.println("The graph contains cycles.");
            graph.printCycles();
        } else {
            System.out.println("The graph does not contain cycles.");
        }

        if (graph.isBipartite()) {
            System.out.println("The graph is bipartite.");
        } else {
            System.out.println("The graph is not bipartite.");
        }
    }
}
