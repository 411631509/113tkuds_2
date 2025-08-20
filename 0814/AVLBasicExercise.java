// 檔名：AVLBasicExercise.java
public class AVLBasicExercise {

    /* ====== 節點結構 ====== */
    static class Node {
        int data;
        Node left, right;
        int height;

        Node(int data) {
            this.data = data;
            this.height = 1; // 葉節點高度設為 1
        }
    }

    /* ====== 成員 ====== */
    private Node root;

    /* ====== 小工具 ====== */
    private int h(Node n) { return n == null ? 0 : n.height; }

    private int bf(Node n) { return (n == null) ? 0 : h(n.left) - h(n.right); }

    private void update(Node n) {
        if (n != null) n.height = Math.max(h(n.left), h(n.right)) + 1;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t2 = x.right;
        x.right = y;
        y.left = t2;
        update(y);
        update(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node t2 = y.left;
        y.left = x;
        x.right = t2;
        update(x);
        update(y);
        return y;
    }

    private Node rebalance(Node n) {
        update(n);
        int balance = bf(n);

        // LL
        if (balance > 1 && bf(n.left) >= 0) return rotateRight(n);
        // LR
        if (balance > 1 && bf(n.left) < 0) {
            n.left = rotateLeft(n.left);
            return rotateRight(n);
        }
        // RR
        if (balance < -1 && bf(n.right) <= 0) return rotateLeft(n);
        // RL
        if (balance < -1 && bf(n.right) > 0) {
            n.right = rotateRight(n.right);
            return rotateLeft(n);
        }
        return n; // 已平衡
    }

    /* ====== 插入：先 BST，再平衡 ====== */
    public void insert(int data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, int data) {
        if (node == null) return new Node(data);              // 標準 BST 插入

        if (data < node.data) node.left = insertRec(node.left, data);
        else if (data > node.data) node.right = insertRec(node.right, data);
        else return node; // 忽略重複

        return rebalance(node);                               // 插入後回溯平衡
    }

    /* ====== 搜尋 ====== */
    public boolean search(int data) { return searchRec(root, data); }

    private boolean searchRec(Node node, int data) {
        if (node == null) return false;
        if (data == node.data) return true;
        return data < node.data ? searchRec(node.left, data) : searchRec(node.right, data);
    }

    /* ====== 計算高度（遞迴） ====== */
    public int height(Node node) {
        if (node == null) return 0;
        int lh = height(node.left);
        int rh = height(node.right);
        return Math.max(lh, rh) + 1;
    }ㄋ

    /* 對外提供：取得整棵樹高度 */
    public int height() { return height(root); }

    /* ====== 驗證是否為有效 AVL ====== */
    public boolean isValidAVL() { return checkAVL(root) != -1; }

    // 回傳子樹高度；若不是 AVL 回傳 -1
    private int checkAVL(Node node) {
        if (node == null) return 0;
        int lh = checkAVL(node.left);
        if (lh == -1) return -1;
        int rh = checkAVL(node.right);
        if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;    // 平衡因子超過範圍
        return Math.max(lh, rh) + 1;
    }

    /* ====== 列印（中序：值(平衡因子)）===== */
    private void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.data + "(" + bf(node) + ") ");
        printInOrder(node.right);
    }

    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    /* ====== 測試主程式 ====== */
    public static void main(String[] args) {
        AVLBasicExercise tree = new AVLBasicExercise();

        int[] arr = {10, 20, 30, 40, 50, 25}; // 會觸發旋轉
        System.out.println("=== 插入測試 ===");
        for (int x : arr) {
            System.out.println("插入: " + x);
            tree.insert(x);
            tree.printTree();
        }

        System.out.println("\n=== 搜尋測試 ===");
        System.out.println("搜尋 25: " + tree.search(25));
        System.out.println("搜尋 35: " + tree.search(35));

        System.out.println("\n樹高度: " + tree.height());
        System.out.println("是有效 AVL？ " + tree.isValidAVL());
    }
}