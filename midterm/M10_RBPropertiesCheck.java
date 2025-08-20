import java.io.*;
import java.util.*;

public class M10_RBPropertiesCheck {

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
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            while (c > ' ') { sb.append((char)c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }

    static class Node {
        int val;
        char color; // 'B' or 'R'
        Node left, right;
        int idx;
        Node(int v, char c, int id) { val = v; color = c; idx = id; }
    }

    static Node buildTree(int[] vals, char[] colors) {
        int n = vals.length;
        if (n == 0 || vals[0] == -1) return null;
        Node root = new Node(vals[0], colors[0], 0);
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            int i = cur.idx;
            int li = 2 * i + 1, ri = 2 * i + 2;
            if (li < n && vals[li] != -1) {
                cur.left = new Node(vals[li], colors[li], li);
                q.offer(cur.left);
            }
            if (ri < n && vals[ri] != -1) {
                cur.right = new Node(vals[ri], colors[ri], ri);
                q.offer(cur.right);
            }
        }
        return root;
    }

    // check red-red violation and compute black height
    static class Result {
        boolean ok;
        int blackHeight;
        String error;
        Result(boolean ok, int bh, String e) { this.ok = ok; this.blackHeight = bh; this.error = e; }
    }

    static Result dfs(Node node) {
        if (node == null) return new Result(true, 1, null); // NIL 視為黑
        Result L = dfs(node.left);
        if (!L.ok) return L;
        Result R = dfs(node.right);
        if (!R.ok) return R;
        if (L.blackHeight != R.blackHeight) return new Result(false, -1, "BlackHeightMismatch");
        if (node.color == 'R') {
            if ((node.left != null && node.left.color == 'R') ||
                (node.right != null && node.right.color == 'R')) {
                return new Result(false, -1, "RedRedViolation at index " + node.idx);
            }
            return new Result(true, L.blackHeight, null);
        } else { // black node
            return new Result(true, L.blackHeight + 1, null);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int[] vals = new int[n];
        char[] colors = new char[n];
        for (int i = 0; i < n; i++) {
            vals[i] = fs.nextInt();
            colors[i] = fs.next().charAt(0);
        }

        Node root = buildTree(vals, colors);

        if (root != null && root.color != 'B') {
            out.println("RootNotBlack");
        } else {
            Result res = dfs(root);
            if (res.ok) out.println("RB Valid");
            else out.println(res.error);
        }

        out.flush();
    }

    /*
     * Time Complexity: O(n)
     * 說明: 建樹 O(n)，一次 DFS 檢查性質 O(n)。總體 O(n)，n 為節點數。
     */
}
