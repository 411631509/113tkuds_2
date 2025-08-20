import java.io.*;
import java.util.*;

public class M09_AVLValidate {

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

    // --- 驗證 BST（嚴格：min < val < max） ---
    static boolean isBST(Node root) {
        return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    static boolean isBST(Node node, long low, long high) {
        if (node == null) return true;
        if (!(low < node.val && node.val < high)) return false;
        return isBST(node.left, low, node.val) && isBST(node.right, node.val, high);
    }

    // --- 驗證 AVL：回傳高度；若不平衡則回傳 -2 作為失敗訊號 ---
    static int heightIfAVL(Node node) {
        if (node == null) return -1; // 空子樹高度 -1
        int lh = heightIfAVL(node.left);
        if (lh == -2) return -2;
        int rh = heightIfAVL(node.right);
        if (rh == -2) return -2;
        if (Math.abs(lh - rh) > 1) return -2; // 不合 AVL
        return Math.max(lh, rh) + 1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = fs.nextInt();

        Node root = buildTree(arr);

        if (!isBST(root)) {
            out.println("Invalid BST");
        } else if (heightIfAVL(root) == -2) {
            out.println("Invalid AVL");
        } else {
            out.println("Valid");
        }

        out.flush();
    }

    /*
     * Time Complexity: O(n)
     * 說明: 驗證 BST 與 AVL 各做一次 DFS，皆為 O(n)，n 為節點數。
     * 空間複雜度: O(h) 來自遞迴呼叫堆疊，h 為樹高（最壞 O(n)）。
     */
}
