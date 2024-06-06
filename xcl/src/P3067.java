import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.IntStream;

public class P3067 {
    public static void main(String[] args) {
        P3067 p = new P3067();
//        System.out.println(Arrays.stream(p.countPairsOfConnectableServers(
//                new int[][]{{0, 1, 1}, {1, 2, 5}, {2, 3, 13}, {3, 4, 9}, {4, 5, 2}}, 1)).boxed().toList());
//
//        System.out.println(Arrays.stream(p.countPairsOfConnectableServers(
//                new int[][]{{0, 6, 3}, {6, 5, 3}, {0, 3, 1}, {3, 2, 7}, {3, 1, 6}, {3, 4, 2}}, 3)).boxed().toList());

        System.out.println(Arrays.stream(p.countPairsOfConnectableServers(
                new int[][]{{1, 0, 2}, {2, 1, 4}, {3, 2, 4}, {4, 0, 3}, {5, 1, 4}, {6, 2, 2}, {7, 6, 4}, {8, 1, 2}, {9, 8, 3}}, 1)).boxed().toList());
    }

    private record ToNode(int nid, int distance) {}

    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;

        ArrayList<ArrayList<ToNode>> adj = new ArrayList<>();
        IntStream.range(0, n).forEach(_ -> adj.add(new ArrayList<>()));

        for (int[] edge : edges) {
            adj.get(edge[0]).add(new ToNode(edge[1], edge[2]));
            adj.get(edge[1]).add(new ToNode(edge[0], edge[2]));
        }

        BitSet visited = new BitSet(n);
        return IntStream.range(0, n)
                .map(i -> count(i, signalSpeed, adj, visited))
                .toArray();
    }

    private int count(int current, int speed, ArrayList<ArrayList<ToNode>> adj, BitSet visited) {
        visited.set(current);
        try {
            int[] branches = adj.get(current).stream()
                    .mapToInt(to -> count(to.distance, to.nid, speed, adj, visited))
                    .toArray();
            int sum = Arrays.stream(branches).sum();
            int squaredSum = Arrays.stream(branches).map(i -> i * i).sum();
            return (sum * sum - squaredSum) / 2;
        } finally {
            visited.clear(current);
        }
    }

    private int count(int distance, int current, int speed, ArrayList<ArrayList<ToNode>> adj, BitSet visited) {
        visited.set(current);
        try {
            return (distance % speed == 0 ? 1 : 0) +
                    adj.get(current).stream()
                            .filter(to -> !visited.get(to.nid))
                            .mapToInt(to -> count(distance + to.distance, to.nid, speed, adj, visited))
                            .sum();
        } finally {
            visited.clear(current);
        }
    }
}
