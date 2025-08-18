import java.util.*;

public class BSTRangeQuerySystem {

    // 定義 BST 節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 插入節點（建 BST 用）
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }
        return root;
    }

    // 1. 範圍查詢：列出 [min, max] 範圍內的節點值
    public static void rangeQuery(TreeNode root, int min, int max, List<Integer> result) {
        if (root == null) return;

        if (root.val > min) rangeQuery(root.left, min, max, result);
        if (root.val >= min && root.val <= max) result.add(root.val);
        if (root.val < max) rangeQuery(root.right, min, max, result);
    }

    // 2. 範圍計數
    public static int rangeCount(TreeNode root, int min, int max) {
        if (root == null) return 0;

        if (root.val < min) {
            return rangeCount(root.right, min, max);
        } else if (root.val > max) {
            return rangeCount(root.left, min, max);
        } else {
            return 1 + rangeCount(root.left, min, max) + rangeCount(root.right, min, max);
        }
    }

    // 3. 範圍總和
    public static int rangeSum(TreeNode root, int min, int max) {
        if (root == null) return 0;

        if (root.val < min) {
            return rangeSum(root.right, min, max);
        } else if (root.val > max) {
            return rangeSum(root.left, min, max);
        } else {
            return root.val + rangeSum(root.left, min, max) + rangeSum(root.right, min, max);
        }
    }

    // 4. 最接近查詢：找出最接近 target 的值
    public static int closestValue(TreeNode root, int target) {
        int closest = root.val;

        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(closest - target)) {
                closest = root.val;
            }

            if (target < root.val) {
                root = root.left;
            } else if (target > root.val) {
                root = root.right;
            } else {
                return root.val;
            }
        }

        return closest;
    }

    // 主程式：測試
    public static void main(String[] args) {
        /*
            測試用 BST：
                       20
                     /    \
                   10      30
                  /  \    /  \
                 5   15  25  35
        */
        int[] values = {20, 10, 30, 5, 15, 25, 35};
        TreeNode root = null;
        for (int val : values) {
            root = insert(root, val);
        }

        // 範圍查詢
        int min = 12, max = 28;
        List<Integer> rangeResult = new ArrayList<>();
        rangeQuery(root, min, max, rangeResult);
        System.out.println("📍 範圍查詢 [" + min + ", " + max + "]: " + rangeResult);

        // 範圍計數
        int count = rangeCount(root, min, max);
        System.out.println("📊 範圍計數: " + count);

        // 範圍總和
        int sum = rangeSum(root, min, max);
        System.out.println("➕ 範圍總和: " + sum);

        // 最接近查詢
        int target = 27;
        int closest = closestValue(root, target);
        System.out.println("🎯 最接近 " + target + " 的節點值: " + closest);
    }
}