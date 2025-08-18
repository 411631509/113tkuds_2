// 檔名：PersistentAVLExercise.java
import java.util.*;

/**
 * 持久化 AVL 樹（Immutable nodes + Path Copying）
 * - 每次 insert 產生新版本，不影響舊版本
 * - 共享未被修改的子樹節點，節省空間
 * - 提供 search / inorder 列印任一版本
 *
 * 介面摘要：
 *   int  insertNewVersion(int baseVersion, int key)  // 以 base 版為基礎插入 key，回傳新版本號
 *   boolean search(int version, int key)             // 在指定版本查詢 key
 *   void printInOrder(int version)                   // 列印指定版本的中序
 *   int  currentVersion()                            // 目前最新版本號
 */
public class PersistentAVLExercise {

    /* ================= 不可變節點 ================= */
    static final class Node {
        final int data;
        final Node left, right;
        final int height;

        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = Math.max(h(left), h(right)) + 1;
        }

        static int h(Node n) { return (n == null) ? 0 : n.height; }
        static int bf(Node n) { return (n == null) ? 0 : h(n.left) - h(n.right); }
        static int h(Node a, Node b) { return Math.max(h(a), h(b)) + 1; }
        static int h(Node a, Node b, Node c) { return Math.max(h(a), Math.max(h(b), h(c))) + 1; }
    }

    /* ================= 版本儲存 ================= */
    private final List<Node> versions = new ArrayList<Node>();

    public PersistentAVLExercise() {
        // 版本 0：空樹
        versions.add(null);
    }

    /** 目前最新版本號（0-based；初始為 0）。 */
    public int currentVersion() {
        return versions.size() - 1;
    }

    /** 取得某版本的根節點（內部用）。 */
    private Node rootOf(int version) {
        if (version < 0 || version >= versions.size())
            throw new IllegalArgumentException("Invalid version: " + version);
        return versions.get(version);
    }

    /* ================= Public API ================= */

    /** 以 baseVersion 為基礎插入 key，產生新版本並回傳新版本號。 */
    public int insertNewVersion(int baseVersion, int key) {
        Node baseRoot = rootOf(baseVersion);
        Node newRoot = insert(baseRoot, key);
        versions.add(newRoot);
        return versions.size() - 1;
    }

    /** 在指定版本搜尋 key。 */
    public boolean search(int version, int key) {
        Node cur = rootOf(version);
        while (cur != null) {
            if (key == cur.data) return true;
            cur = (key < cur.data) ? cur.left : cur.right;
        }
        return false;
    }

    /** 列印指定版本的中序（排序序）。 */
    public void printInOrder(int version) {
        Node r = rootOf(version);
        printInOrderRec(r);
        System.out.println();
    }

    private void printInOrderRec(Node n) {
        if (n == null) return;
        printInOrderRec(n.left);
        System.out.print(n.data + " ");
        printInOrderRec(n.right);
    }

    /* ================== 持久化插入（Path Copying） ================== */

    private static int h(Node n) { return Node.h(n); }
    private static int bf(Node n) { return Node.bf(n); }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key, null, null);
        }

        if (key < node.data) {
            // 路徑複製：生成一個新節點，左子樹替換為遞迴插入結果，右子樹共享
            Node newLeft = insert(node.left, key);
            Node newNode = new Node(node.data, newLeft, node.right);
            return rebalance(newNode);
        } else if (key > node.data) {
            Node newRight = insert(node.right, key);
            Node newNode = new Node(node.data, node.left, newRight);
            return rebalance(newNode);
        } else {
            // 已存在：直接回傳原節點（完全共享，不創建新節點）
            return node;
        }
    }

    /* ================== 不可變旋轉（回傳新根） ================== */

    // 右旋：
    //        y                 x
    //       / \               / \
    //      x   C   ===>      A   y
    //     / \                   / \
    //    A   B                 B   C
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node A = x.left;
        Node B = x.right;
        Node C = y.right;

        // 新 y 節點：值=原 y.data，左= B，右= C
        Node newY = new Node(y.data, B, C);
        // 新 x 節點：值=原 x.data，左= A，右= newY
        return new Node(x.data, A, newY);
    }

    // 左旋：
    //      x                     y
    //     / \                   / \
    //    A   y      ===>       x   C
    //       / \               / \
    //      B   C             A   B
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node A = x.left;
        Node B = y.left;
        Node C = y.right;

        // 新 x 節點
        Node newX = new Node(x.data, A, B);
        // 新 y 節點
        return new Node(y.data, newX, C);
    }

    private Node rebalance(Node n) {
        int balance = bf(n);

        // LL
        if (balance > 1 && bf(n.left) >= 0) {
            return rotateRight(n);
        }
        // LR
        if (balance > 1 && bf(n.left) < 0) {
            Node newLeft = rotateLeft(n.left);
            Node newN = new Node(n.data, newLeft, n.right);
            return rotateRight(newN);
        }
        // RR
        if (balance < -1 && bf(n.right) <= 0) {
            return rotateLeft(n);
        }
        // RL
        if (balance < -1 && bf(n.right) > 0) {
            Node newRight = rotateRight(n.right);
            Node newN = new Node(n.data, n.left, newRight);
            return rotateLeft(newN);
        }
        return n; // 無需旋轉
    }

    /* ================== Demo ================== */
    public static void main(String[] args) {
        PersistentAVLExercise pavl = new PersistentAVLExercise();

        // 版本 0：空樹
        System.out.println("Version 0 (empty):");
        pavl.printInOrder(0);

        // 逐步基於上一版插入，產生新版本
        int v1 = pavl.insertNewVersion(0, 30);  // v1：{30}
        int v2 = pavl.insertNewVersion(v1, 10); // v2：{10,30}
        int v3 = pavl.insertNewVersion(v2, 50); // v3：{10,30,50}
        int v4 = pavl.insertNewVersion(v3, 5);  // v4：插入 5
        int v5 = pavl.insertNewVersion(v4, 20); // v5：插入 20
        int v6 = pavl.insertNewVersion(v5, 40); // v6：插入 40
        int v7 = pavl.insertNewVersion(v6, 60); // v7：插入 60
        int v8 = pavl.insertNewVersion(v7, 25); // v8：插入 25（會觸發重平衡）

        System.out.println("Version " + v1 + ":"); pavl.printInOrder(v1);
        System.out.println("Version " + v2 + ":"); pavl.printInOrder(v2);
        System.out.println("Version " + v3 + ":"); pavl.printInOrder(v3);
        System.out.println("Version " + v4 + ":"); pavl.printInOrder(v4);
        System.out.println("Version " + v5 + ":"); pavl.printInOrder(v5);
        System.out.println("Version " + v6 + ":"); pavl.printInOrder(v6);
        System.out.println("Version " + v7 + ":"); pavl.printInOrder(v7);
        System.out.println("Version " + v8 + ":"); pavl.printInOrder(v8);

        // 歷史查詢
        System.out.println("\nSearch 25 in v5: " + pavl.search(v5, 25)); // false
        System.out.println("Search 25 in v8: " + pavl.search(v8, 25));   // true

        System.out.println("\nCurrent latest version = " + pavl.currentVersion());
    }
}