import java.util.*;

public class BSTValidationAndRepair {

    // 定義樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 驗證是否為有效 BST（使用 min/max 範圍）
    public static boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private static boolean validate(TreeNode node, Integer min, Integer max) {
        if (node == null) return true;

        if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }

        return validate(node.left, min, node.val) &&
               validate(node.right, node.val, max);
    }

    // 2. 找出不符合 BST 規則的節點（中序違反）
    public static List<TreeNode> findInvalidNodes(TreeNode root) {
        List<TreeNode> violations = new ArrayList<>();
        findInvalidHelper(root, new TreeNode[]{null}, violations);
        return violations;
    }

    private static void findInvalidHelper(TreeNode node, TreeNode[] prev, List<TreeNode> violations) {
        if (node == null) return;

        findInvalidHelper(node.left, prev, violations);

        if (prev[0] != null && node.val <= prev[0].val) {
            violations.add(prev[0]);
            violations.add(node);
        }
        prev[0] = node;

        findInvalidHelper(node.right, prev, violations);
    }

    // 3. 修復兩個節點交換錯誤的 BST（假設只有兩個節點錯誤）
    public static void recoverBST(TreeNode root) {
        TreeNode[] nodes = new TreeNode[2]; // 錯誤節點
        TreeNode[] prev = new TreeNode[]{null};
        findSwappedNodes(root, prev, nodes);

        if (nodes[0] != null && nodes[1] != null) {
            int temp = nodes[0].val;
            nodes[0].val = nodes[1].val;
            nodes[1].val = temp;
        }
    }

    private static void findSwappedNodes(TreeNode node, TreeNode[] prev, TreeNode[] result) {
        if (node == null) return;

        findSwappedNodes(node.left, prev, result);

        if (prev[0] != null && node.val < prev[0].val) {
            if (result[0] == null) {
                result[0] = prev[0];
            }
            result[1] = node;
        }

        prev[0] = node;
        findSwappedNodes(node.right, prev, result);
    }

    // 4. 計算最少需要移除多少節點使其成為合法 BST
    public static int minRemovalsForValidBST(TreeNode root) {
        List<Integer> inOrder = new ArrayList<>();
        getInOrder(root, inOrder);
        return inOrder.size() - lengthOfLIS(inOrder);
    }

    // 中序遍歷（得到節點值序列）
    private static void getInOrder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        getInOrder(node.left, list);
        list.add(node.val);
        getInOrder(node.right, list);
    }

    // 最長遞增子序列（LIS）
    private static int lengthOfLIS(List<Integer> nums) {
        List<Integer> lis = new ArrayList<>();
        for (int num : nums) {
            int i = Collections.binarySearch(lis, num);
            if (i < 0) i = -i - 1;
            if (i == lis.size()) lis.add(num);
            else lis.set(i, num);
        }
        return lis.size();
    }

    // 輔助：中序印出
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // 主程式：測試
    public static void main(String[] args) {
        /*
            錯誤 BST：
                  10
                 /  \
               5     2   ← 錯誤：應該比 10 大
              /
             1
        */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(2); // 應該是 15
        root.left.left = new TreeNode(1);

        System.out.print("🧪 原始中序：");
        printInOrder(root);
        System.out.println();

        // 1. 是否為 BST
        System.out.println("✅ 是否為合法 BST: " + isValidBST(root));

        // 2. 找出違規節點
        List<TreeNode> badNodes = findInvalidNodes(root);
        System.out.print("❌ 違規節點: ");
        for (TreeNode n : badNodes) System.out.print(n.val + " ");
        System.out.println();

        // 3. 修復 BST（假設僅兩節點交換）
        System.out.println("🔧 修復 BST 中...");
        recoverBST(root);
        System.out.print("✅ 修復後中序：");
        printInOrder(root);
        System.out.println("\n合法 BST？" + isValidBST(root));

        // 4. 計算最少移除節點數
        TreeNode invalidRoot = new TreeNode(3);
        invalidRoot.left = new TreeNode(5);
        invalidRoot.right = new TreeNode(2);
        invalidRoot.left.left = new TreeNode(4);
        invalidRoot.left.right = new TreeNode(1);
        System.out.print("\n🚫 非法樹中序：");
        printInOrder(invalidRoot);
        System.out.println("\n最少需移除節點數: " + minRemovalsForValidBST(invalidRoot));
    }
}