import java.util.*;

public class TreeReconstruction {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 根據前序 + 中序重建
    public static TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPreIn(preorder, 0, preorder.length - 1, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPreIn(int[] pre, int ps, int pe, int is, int ie, Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie) return null;
        TreeNode root = new TreeNode(pre[ps]);
        int inRoot = inMap.get(pre[ps]);
        int leftSize = inRoot - is;
        root.left = buildPreIn(pre, ps + 1, ps + leftSize, is, inRoot - 1, inMap);
        root.right = buildPreIn(pre, ps + leftSize + 1, pe, inRoot + 1, ie, inMap);
        return root;
    }

    // 2. 根據後序 + 中序重建
    public static TreeNode buildFromPostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPostIn(postorder, 0, postorder.length - 1, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPostIn(int[] post, int ps, int pe, int is, int ie, Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie) return null;
        TreeNode root = new TreeNode(post[pe]);
        int inRoot = inMap.get(post[pe]);
        int leftSize = inRoot - is;
        root.left = buildPostIn(post, ps, ps + leftSize - 1, is, inRoot - 1, inMap);
        root.right = buildPostIn(post, ps + leftSize, pe - 1, inRoot + 1, ie, inMap);
        return root;
    }

    // 3. 根據層序重建完全二元樹（假設滿足完全二元樹條件）
    public static TreeNode buildFromLevelOrder(int[] levelOrder) {
        if (levelOrder.length == 0) return null;

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (i < levelOrder.length) {
            TreeNode current = queue.poll();

            if (i < levelOrder.length) {
                current.left = new TreeNode(levelOrder[i++]);
                queue.offer(current.left);
            }

            if (i < levelOrder.length) {
                current.right = new TreeNode(levelOrder[i++]);
                queue.offer(current.right);
            }
        }

        return root;
    }

    // 驗證：中序走訪輸出
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // 驗證：前序走訪輸出
    public static void printPreOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    // 驗證：後序走訪輸出
    public static void printPostOrder(TreeNode root) {
        if (root == null) return;
        printPostOrder(root.left);
        printPostOrder(root.right);
        System.out.print(root.val + " ");
    }

    // 驗證：層序走訪輸出
    public static void printLevelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            System.out.print(node.val + " ");
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
    }

    // 主程式：測試
    public static void main(String[] args) {
        // 測試用資料
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        int[] levelOrder = {1, 2, 3, 4, 5, 6, 7};

        // 1. 前序 + 中序
        TreeNode preInTree = buildFromPreIn(preorder, inorder);
        System.out.println("✅ 從前序+中序重建樹的中序輸出：");
        printInOrder(preInTree);
        System.out.println("\n前序輸出驗證：");
        printPreOrder(preInTree);

        // 2. 後序 + 中序
        TreeNode postInTree = buildFromPostIn(postorder, inorder);
        System.out.println("\n\n✅ 從後序+中序重建樹的中序輸出：");
        printInOrder(postInTree);
        System.out.println("\n後序輸出驗證：");
        printPostOrder(postInTree);

        // 3. 層序重建完全二元樹
        TreeNode levelTree = buildFromLevelOrder(levelOrder);
        System.out.println("\n\n✅ 從層序重建完全二元樹的層序輸出：");
        printLevelOrder(levelTree);
        System.out.println("\n中序驗證：");
        printInOrder(levelTree);
    }
}