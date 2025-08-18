import java.util.*;

public class BinaryTreeBasicOperations {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 總和與平均值
    static class SumResult {
        int sum;
        int count;

        SumResult(int sum, int count) {
            this.sum = sum;
            this.count = count;
        }
    }

    public static SumResult calculateSum(TreeNode root) {
        if (root == null) return new SumResult(0, 0);

        SumResult left = calculateSum(root.left);
        SumResult right = calculateSum(root.right);

        int totalSum = left.sum + right.sum + root.val;
        int totalCount = left.count + right.count + 1;

        return new SumResult(totalSum, totalCount);
    }

    // 2. 最大與最小節點
    public static int findMax(TreeNode root) {
        if (root == null) return Integer.MIN_VALUE;
        return Math.max(root.val, Math.max(findMax(root.left), findMax(root.right)));
    }

    public static int findMin(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        return Math.min(root.val, Math.min(findMin(root.left), findMin(root.right)));
    }

    // 3. 計算樹的寬度（每層節點數最大值）
    public static int calculateWidth(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxWidth = Math.max(maxWidth, levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
        }

        return maxWidth;
    }

    // 4. 判斷是否為完全二元樹
    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        boolean end = false;

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            if (current == null) {
                end = true;
            } else {
                if (end) return false; // 出現空節點後又出現非空節點
                queue.add(current.left);
                queue.add(current.right);
            }
        }

        return true;
    }

    // 主程式：測試功能
    public static void main(String[] args) {
        /*
            測試用樹：
                     10
                   /    \
                 5       15
                / \     / 
               2   7   12
        */

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(12);

        // 1. 總和與平均
        SumResult result = calculateSum(root);
        System.out.println("節點總和: " + result.sum);
        System.out.printf("平均值: %.2f\n", (double) result.sum / result.count);

        // 2. 最大最小
        System.out.println("最大值節點: " + findMax(root));
        System.out.println("最小值節點: " + findMin(root));

        // 3. 樹的寬度
        System.out.println("最大寬度（節點最多的一層）: " + calculateWidth(root));

        // 4. 是否為完全二元樹
        System.out.println("是否為完全二元樹: " + isCompleteTree(root));
    }
}