// 檔名：AVLRangeQueryExercise.java
import java.util.*;

public class AVLRangeQueryExercise {

    /* ===== 節點結構 ===== */
    static class Node {
        int data;
        Node left, right;
        int height;
        Node(int data) { this.data = data; this.height = 1; }
    }

    /* ===== 內部狀態 ===== */
    private Node root;

    /* ===== 小工具 ===== */
    private static int h(Node n) { return n == null ? 0 : n.height; }
    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }
    private static void update(Node n) { if (n != null) n.height = Math.max(h(n.left), h(n.right)) + 1; }

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
        if (b > 1) {
            if (bf(n.left) < 0) n.left = leftRotate(n.left); // LR
            return rightRotate(n);                           // LL
        }
        if (b < -1) {
            if (bf(n.right) > 0) n.right = rightRotate(n.right); // RL
            return leftRotate(n);                                // RR
        }
        return n;
    }

    /* ===== 插入 (AVL) ===== */
    public void insert(int key) { root = insertRec(root, key); }

    private Node insertRec(Node n, int key) {
        if (n == null) return new Node(key);
        if (key < n.data) n.left = insertRec(n.left, key);
        else if (key > n.data) n.right = insertRec(n.right, key);
        else return n; // 忽略重複
        return rebalance(n);
    }

    /* ===== 範圍查詢 =====
       回傳 [min, max] 之間（含邊界）的所有元素，遞增排序。
       透過 BST 性質剪枝：
       - 若 node.data > min，才需要往左子樹找
       - 若 node.data 在區間內，加入結果
       - 若 node.data < max，才需要往右子樹找
    */
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeQueryRec(root, min, max, result);
        return result;
    }

    private void rangeQueryRec(Node node, int min, int max, List<Integer> out) {
        if (node == null) return;

        // 只有當左邊可能存在 >= min 的值時才走左子樹
        if (node.data > min) rangeQueryRec(node.left, min, max, out);

        // 當前節點在區間內就加入
        if (node.data >= min && node.data <= max) out.add(node.data);

        // 只有當右邊可能存在 <= max 的值時才走右子樹
        if (node.data < max) rangeQueryRec(node.right, min, max, out);
    }

    /* ===== 方便測試的輔助 ===== */
    public void printInOrder() { printInOrder(root); System.out.println(); }
    private static void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.data + " ");
        printInOrder(n.right);
    }

    /* ===== 測試主程式 ===== */
    public static void main(String[] args) {
        AVLRangeQueryExercise tree = new AVLRangeQueryExercise();

        // 建一棵樹（已含重平衡）
        int[] vals = {30, 10, 50, 5, 20, 40, 60, 15, 25, 35, 45, 55, 70};
        for (int v : vals) tree.insert(v);

        System.out.print("中序(排序)輸出: ");
        tree.printInOrder(); // 應為遞增序列

        // 測試幾組範圍
        testRange(tree, 1, 100);   // 全部
        testRange(tree, 15, 45);   // 中間一段
        testRange(tree, 33, 37);   // 只落在 35
        testRange(tree, 0, 4);     // 無結果
        testRange(tree, 60, 60);   // 單點（剛好在樹中）
        testRange(tree, 22, 24);   // 單點不在樹（應為空）
    }

    private static void testRange(AVLRangeQueryExercise tree, int min, int max) {
        List<Integer> ans = tree.rangeQuery(min, max);
        System.out.println("rangeQuery[" + min + ", " + max + "] -> " + ans);
    }
}