import java.io.*;
import java.util.*;

public class M07_BinaryTreeLeftView {

    // ---------- Fast I/O ----------
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

    // ---------- Tree Node ----------
    static class Node {
        int val;
        Node left, right;
        Node(int v) { val = v; }
    }

    // 從層序（含 -1 為空）建樹
    static Node buildTree(int[] a) {
        int n = a.length;
        if (n == 0 || a[0] == -1) return null;
        Node root = new Node(a[0]);
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < n) {
            Node cur = q.poll();
            // left
            if (i < n && a[i] != -1) {
                cur.left = new Node(a[i]);
                q.offer(cur.left);
            }
            i++;
            // right
            if (i < n && a[i] != -1) {
                cur.right = new Node(a[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // 取左視圖
    static List<Integer> leftView(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node cur = q.poll();
                if (i == 0) res.add(cur.val); // 每層第一個即左視圖
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = fs.nextInt();

        Node root = buildTree(arr);
        List<Integer> lv = leftView(root);

        out.print("LeftView:");
        for (int i = 0; i < lv.size(); i++) {
            out.print(i == 0 ? " " : " ");
            out.print(lv.get(i));
        }
        out.println();
        out.flush();
    }

    /*
     * Time Complexity: O(n)
     * 說明: 以層序資料建樹與 BFS 逐層掃描各一次，皆為 O(n)。
     * 空間複雜度: O(n)（隊列與樹節點）。
     */
}
