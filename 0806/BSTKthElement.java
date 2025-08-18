import java.util.*;

public class BSTKthElement {

    // 節點結構：帶有子樹大小
    static class TreeNode {
        int val;
        int size; // 當前節點為根的子樹節點總數
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            this.size = 1;
        }
    }

    // 插入節點（更新 size）
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else root.right = insert(root.right, val);

        root.size = 1 + getSize(root.left) + getSize(root.right);
        return root;
    }

    // 刪除節點（更新 size）
    public static TreeNode delete(TreeNode root, int val) {
        if (root == null) return null;

        if (val < root.val) {
            root.left = delete(root.left, val);
        } else if (val > root.val) {
            root.right = delete(root.right, val);
        } else {
            // 找到節點，進行刪除
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            TreeNode minNode = findMin(root.right);
            root.val = minNode.val;
            root.right = delete(root.right, minNode.val);
        }

        root.size = 1 + getSize(root.left) + getSize(root.right);
        return root;
    }

    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private static int getSize(TreeNode node) {
        return node == null ? 0 : node.size;
    }

    // 1. 找出第 k 小的元素（O(log n)）
    public static Integer kthSmallest(TreeNode root, int k) {
        if (root == null) return null;

        int leftSize = getSize(root.left);

        if (k <= leftSize) {
            return kthSmallest(root.left, k);
        } else if (k == leftSize + 1) {
            return root.val;
        } else {
            return kthSmallest(root.right, k - leftSize - 1);
        }
    }

    // 2. 找出第 k 大的元素
    public static Integer kthLargest(TreeNode root, int k) {
        int total = getSize(root);
        return kthSmallest(root, total - k + 1); // 第 k 大 = 第 (n - k + 1) 小
    }

    // 3. 找出第 k 小到第 j 小的所有元素（中序遍歷）
    public static List<Integer> rangeKtoJ(TreeNode root, int k, int j) {
        List<Integer> result = new ArrayList<>();
        inOrderRange(root, result, new int[]{0}, k, j);
        return result;
    }

    private static void inOrderRange(TreeNode node, List<Integer> result, int[] count, int k, int j) {
        if (node == null || count[0] >= j) return;

        inOrderRange(node.left, result, count, k, j);

        count[0]++;
        if (count[0] >= k && count[0] <= j) {
            result.add(node.val);
        }

        inOrderRange(node.right, result, count, k, j);
    }

    // 輔助函式：中序列印
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // 主程式：測試
    public static void main(String[] args) {
        int[] values = {20, 10, 30, 5, 15, 25, 35};
        TreeNode root = null;
        for (int val : values) {
            root = insert(root, val);
        }

        System.out.print("🌲 BST 中序遍歷：");
        printInOrder(root);
        System.out.println();

        // 測試第 k 小 / 大
        System.out.println("第 3 小的元素: " + kthSmallest(root, 3)); // 10
        System.out.println("第 2 大的元素: " + kthLargest(root, 2)); // 30

        // 測試範圍查詢
        System.out.println("第 2 小到第 5 小的元素: " + rangeKtoJ(root, 2, 5)); // [10, 15, 20, 25]

        // 測試動態刪除與第 k 小查詢
        root = delete(root, 15);
        System.out.print("刪除 15 後 BST：");
        printInOrder(root);
        System.out.println();
        System.out.println("現在第 3 小的元素: " + kthSmallest(root, 3)); // 應為 20
    }
}