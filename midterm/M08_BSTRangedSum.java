import java.io.*;
import java.util.*;

public class M08_BSTRangedSum {

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
            if (i < n && a[i] != -1) {
                cur.left = new Node(a[i]);
                q.offer(cur.left);
            }
            i++;
            if (i < n && a[i] != -1) {
                cur.right = new Node(a[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // 區間和
    static int rangeSum(Node root, int L, int R) {
        if (root == null) return 0;
        if (root.val < L) return rangeSum(root.right, L, R);
        if (root.val > R) return rangeSum(root.left, L, R);
        return root.val + rangeSum(root.left, L, R) + rangeSum(root.right, L, R);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = fs.nextInt();
        int L = fs.nextInt();
        int R = fs.nextInt();

        Node root = buildTree(arr);
        int ans = rangeSum(root, L, R);

        out.println("Sum: " + ans);
        out.flush();
    }

    /*
     * Time Complexity: O(m)
     * 說明: 針對 BST 節點做遞迴搜尋，若節點值小於 L 只走右子樹，大於 R 只走左子樹，
     * 省略不必要分支。m 為實際被訪問的節點數，最壞 O(n)，平均 O(log n) ~ O(k)。
     */
}
