// 檔名：AVLDeleteExercise.java
public class AVLDeleteExercise {

    /* ===== 節點 ===== */
    static class Node {
        int data;
        Node left, right;
        int height;
        Node(int data) { this.data = data; this.height = 1; }
    }

    /* ===== 成員 ===== */
    private Node root;

    /* ===== 工具 ===== */
    private static int h(Node n) { return n == null ? 0 : n.height; }

    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    private static void update(Node n) {
        if (n != null) n.height = Math.max(h(n.left), h(n.right)) + 1;
    }

    private static Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        update(y); update(x);
        return x;
    }

    private static Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        update(x); update(y);
        return y;
    }

    private static Node rebalance(Node n) {
        update(n);
        int b = bf(n);
        // LL / LR
        if (b > 1) {
            if (bf(n.left) < 0) n.left = leftRotate(n.left); // LR
            return rightRotate(n); // LL
        }
        // RR / RL
        if (b < -1) {
            if (bf(n.right) > 0) n.right = rightRotate(n.right); // RL
            return leftRotate(n); // RR
        }
        return n;
    }

    /* ===== 插入（供測試用）===== */
    public void insert(int key) { root = insertRec(root, key); }

    private Node insertRec(Node n, int key) {
        if (n == null) return new Node(key);
        if (key < n.data) n.left = insertRec(n.left, key);
        else if (key > n.data) n.right = insertRec(n.right, key);
        else return n; // 忽略重複
        return rebalance(n);
    }

    /* ===== 刪除 ===== */
    public void delete(int key) { root = deleteRec(root, key); }

    private Node deleteRec(Node n, int key) {
        if (n == null) return null;

        if (key < n.data) n.left = deleteRec(n.left, key);
        else if (key > n.data) n.right = deleteRec(n.right, key);
        else {
            // 命中：三種情況
            // 1) 0 或 1 個子節點：直接接上去
            if (n.left == null || n.right == null) {
                Node child = (n.left != null) ? n.left : n.right;
                return child; // 可能是 null（葉節點）
            }
            // 2) 兩個子節點：用右子樹最小者(後繼)替代
            Node succ = findMin(n.right);
            n.data = succ.data;
            n.right = deleteRec(n.right, succ.data);
        }

        // 回溯重平衡
        return rebalance(n);
    }

    private static Node findMin(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    /* ===== 輔助輸出 ===== */
    private static void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.data + "(BF=" + bf(n) + ",h=" + h(n) + ") ");
        printInOrder(n.right);
    }

    public void printTree() { printInOrder(root); System.out.println(); }

    public boolean search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.data) return true;
            cur = (key < cur.data) ? cur.left : cur.right;
        }
        return false;
    }

    /* ===== 測試：刪除三類情況 ===== */
    public static void main(String[] args) {
        AVLDeleteExercise tree = new AVLDeleteExercise();

        // 建樹
        int[] vals = {30, 20, 40, 10, 25, 35, 50, 5, 15, 45};
        for (int v : vals) tree.insert(v);
        System.out.print("初始樹: "); tree.printTree();

        // 1) 刪除葉節點：15
        System.out.println("\n刪除【葉節點】15");
        tree.delete(15);
        tree.printTree();

        // 2) 刪除單子節點：50（只有左子 45）
        System.out.println("\n刪除【單子節點】50");
        tree.delete(50);
        tree.printTree();

        // 3) 刪除雙子節點：30（有左右子）
        System.out.println("\n刪除【雙子節點】30");
        tree.delete(30);
        tree.printTree();

        // 簡單驗證
        System.out.println("\n搜尋 30: " + tree.search(30));
        System.out.println("搜尋 25: " + tree.search(25));
    }
}