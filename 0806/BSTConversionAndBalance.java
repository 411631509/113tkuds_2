import java.util.*;

public class BSTConversionAndBalance {

    // 二元樹節點（也作為雙向節點）
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // ==== 1. 將 BST 轉換為排序雙向鏈結串列 ====

    static TreeNode prev = null;
    static TreeNode head = null;

    public static TreeNode bstToDoublyList(TreeNode root) {
        prev = null;
        head = null;
        convertBSTToDLL(root);
        return head;
    }

    private static void convertBSTToDLL(TreeNode node) {
        if (node == null) return;

        convertBSTToDLL(node.left);

        if (prev != null) {
            prev.right = node;
            node.left = prev;
        } else {
            head = node;
        }
        prev = node;

        convertBSTToDLL(node.right);
    }

    // ==== 2. 將排序陣列轉換為平衡 BST ====

    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildBalancedBST(nums, 0, nums.length - 1);
    }

    private static TreeNode buildBalancedBST(int[] arr, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(arr[mid]);
        root.left = buildBalancedBST(arr, left, mid - 1);
        root.right = buildBalancedBST(arr, mid + 1, right);
        return root;
    }

    // ==== 3. 檢查是否為平衡 BST，並回傳是否平衡 ====

    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private static int checkHeight(TreeNode node) {
        if (node == null) return 0;
        int lh = checkHeight(node.left);
        int rh = checkHeight(node.right);
        if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    // 額外：印出每個節點的平衡因子
    public static void printBalanceFactors(TreeNode root) {
        if (root == null) return;
        int lh = height(root.left);
        int rh = height(root.right);
        int balance = lh - rh;
        System.out.println("Node " + root.val + " → Balance Factor: " + balance);
        printBalanceFactors(root.left);
        printBalanceFactors(root.right);
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // ==== 4. 將 BST 的每個節點轉為大於等於它的總和 ====

    static int sum = 0;

    public static void convertToGreaterSumTree(TreeNode root) {
        sum = 0;
        reverseInorderSum(root);
    }

    private static void reverseInorderSum(TreeNode node) {
        if (node == null) return;
        reverseInorderSum(node.right);
        sum += node.val;
        node.val = sum;
        reverseInorderSum(node.left);
    }

    // ==== 輔助輸出：中序列印 ====

    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // ==== 輔助輸出：列印雙向鏈表 ====

    public static void printDoublyList(TreeNode head) {
        System.out.print("雙向鏈表：");
        TreeNode curr = head;
        TreeNode tail = null;
        while (curr != null) {
            System.out.print(curr.val + " ");
            if (curr.right == null) tail = curr;
            curr = curr.right;
        }

        System.out.print("\n反向走訪：");
        while (tail != null) {
            System.out.print(tail.val + " ");
            tail = tail.left;
        }
        System.out.println();
    }

    // ==== 主程式 ====

    public static void main(String[] args) {

        // 建立一棵 BST
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(6);
        root.right = new TreeNode(14);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(8);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(16);

        // 1. BST ➜ 雙向鏈表
        TreeNode dll = bstToDoublyList(root);
        printDoublyList(dll);

        // 2. 排序陣列 ➜ 平衡 BST
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7};
        TreeNode balanced = sortedArrayToBST(sortedArr);
        System.out.print("\n平衡 BST（中序）：");
        printInOrder(balanced);
        System.out.println("\n是否平衡？" + isBalanced(balanced));

        // 3. 印出每個節點的平衡因子
        System.out.println("\n平衡因子列表：");
        printBalanceFactors(balanced);

        // 4. 節點轉為大於等於總和
        TreeNode greaterBST = sortedArrayToBST(new int[]{1, 2, 3, 4});
        System.out.print("\n原始 BST（中序）：");
        printInOrder(greaterBST);
        convertToGreaterSumTree(greaterBST);
        System.out.print("\n轉換後的 Greater Sum Tree：");
        printInOrder(greaterBST);
    }
}