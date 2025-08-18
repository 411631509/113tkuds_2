import java.util.*;

public class TreePathProblems {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 所有根到葉節點的路徑
    public static List<List<Integer>> allRootToLeafPaths(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        traversePaths(root, new ArrayList<>(), result);
        return result;
    }

    private static void traversePaths(TreeNode node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;

        path.add(node.val);

        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(path));
        } else {
            traversePaths(node.left, path, result);
            traversePaths(node.right, path, result);
        }

        path.remove(path.size() - 1);
    }

    // 2. 是否存在根到葉總和為 target 的路徑
    public static boolean hasPathSum(TreeNode root, int target) {
        if (root == null) return false;

        if (root.left == null && root.right == null) {
            return root.val == target;
        }

        return hasPathSum(root.left, target - root.val) ||
               hasPathSum(root.right, target - root.val);
    }

    // 3. 找出和最大的根到葉路徑
    public static int maxRootToLeafSum(TreeNode root) {
        return maxSumHelper(root, 0);
    }

    private static int maxSumHelper(TreeNode node, int currentSum) {
        if (node == null) return Integer.MIN_VALUE;

        currentSum += node.val;

        if (node.left == null && node.right == null) {
            return currentSum;
        }

        return Math.max(
                maxSumHelper(node.left, currentSum),
                maxSumHelper(node.right, currentSum)
        );
    }

    // 4. 任意兩節點間最大路徑和（樹的直徑）
    static int maxPathSum = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode root) {
        maxPathSum = Integer.MIN_VALUE;
        maxPathSumHelper(root);
        return maxPathSum;
    }

    private static int maxPathSumHelper(TreeNode node) {
        if (node == null) return 0;

        int left = Math.max(0, maxPathSumHelper(node.left));
        int right = Math.max(0, maxPathSumHelper(node.right));

        maxPathSum = Math.max(maxPathSum, node.val + left + right);

        return node.val + Math.max(left, right);
    }

    // 5. 練習 3.8：根據前序與中序重建二元樹
    public static TreeNode buildTreeFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length - 1, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildTreeHelper(int[] pre, int ps, int pe, int is, int ie, Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie) return null;

        TreeNode root = new TreeNode(pre[ps]);
        int inRoot = inMap.get(pre[ps]);
        int numsLeft = inRoot - is;

        root.left = buildTreeHelper(pre, ps + 1, ps + numsLeft, is, inRoot - 1, inMap);
        root.right = buildTreeHelper(pre, ps + numsLeft + 1, pe, inRoot + 1, ie, inMap);

        return root;
    }

    // 中序印出樹（輔助）
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // 主程式：功能測試
    public static void main(String[] args) {
        /*
                  10
                 /  \
                5    12
               / \     \
              2   7     20
        */

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(12);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(20);

        // 1. 所有根到葉路徑
        System.out.println("📍 所有根到葉節點路徑：");
        for (List<Integer> path : allRootToLeafPaths(root)) {
            System.out.println(path);
        }

        // 2. 是否存在某目標總和的路徑
        System.out.println("\n🎯 是否存在總和為 22 的路徑: " + hasPathSum(root, 22));

        // 3. 最大根到葉總和
        System.out.println("\n📈 最大根到葉總和: " + maxRootToLeafSum(root));

        // 4. 任意節點間最大路徑總和
        System.out.println("\n🌐 任意節點間最大路徑總和: " + maxPathSum(root));

        // 5. 重建二元樹
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode rebuilt = buildTreeFromPreIn(preorder, inorder);
        System.out.print("\n🛠️ 依前序與中序重建的中序輸出：");
        printInOrder(rebuilt);
        System.out.println();
    }
}