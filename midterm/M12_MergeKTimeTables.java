import java.io.*;
import java.util.*;

public class M12_MergeKTimeTables {

    // -------- Fast I/O --------
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c, s = 1, x = 0;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            if (c == '-') { s = -1; c = read(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * s;
        }
    }

    // -------- Heap Node --------
    static class Node {
        int time;
        int listId;
        int idx;
        Node(int t, int l, int i) { time = t; listId = l; idx = i; }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int K = fs.nextInt();
        List<int[]> lists = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            int len = fs.nextInt();
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) arr[j] = fs.nextInt();
            lists.add(arr);
        }

        // Min-Heap (依 time 升冪)
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.time - b.time);

        // 初始化，每個清單推第一個
        for (int i = 0; i < K; i++) {
            if (lists.get(i).length > 0) {
                pq.offer(new Node(lists.get(i)[0], i, 0));
            }
        }

        List<Integer> merged = new ArrayList<>();
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            merged.add(cur.time);

            int nextIdx = cur.idx + 1;
            if (nextIdx < lists.get(cur.listId).length) {
                pq.offer(new Node(lists.get(cur.listId)[nextIdx], cur.listId, nextIdx));
            }
        }

        for (int i = 0; i < merged.size(); i++) {
            if (i > 0) out.print(" ");
            out.print(merged.get(i));
        }
        out.println();
        out.flush();
    }

    /*
     * Time Complexity: O(N log K)
     * 說明: N 為總元素數，K 為清單數。每次彈出/推入 O(log K)，總共 N 次操作。
     * 輸出最終合併結果即得遞增序列。
     */
}
