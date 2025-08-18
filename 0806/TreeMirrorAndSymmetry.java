public class TreeMirrorAndSymmetry {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 判斷是否為對稱樹（鏡像自己）
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return (a.val == b.val) &&
               isMirror(a.left, b.right) &&
               isMirror(a.right, b.left);
    }

    // 2. 將一棵樹轉換為其鏡像樹
    public static TreeNode mirrorTree(TreeNode root) {
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(temp);
        return root;
    }

    // 3. 比較兩棵樹是否互為鏡像
    public static boolean areMirrors(TreeNode a, TreeNode b) {
        return isMirror(a, b);
    }

    // 4. 檢查 subtree 是否為 mainTree 的子樹
    public static boolean isSubtree(TreeNode mainTree, TreeNode subTree) {
        if (mainTree == null) return false;
        if (isSameTree(mainTree, subTree)) return true;
        return isSubtree(mainTree.left, subTree) || isSubtree(mainTree.right, subTree);
    }

    private static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return (a.val == b.val) &&
               isSameTree(a.left, b.left) &&
               isSameTree(a.right, b.right);
    }

    // 輔助函式：中序列印
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // 主程式：測試功能
    public static void main(String[] args) {
        /*
            原始樹：
                  1
                /   \
               2     2
              / \   / \
             3   4 4   3
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        // 1. 對稱檢查
        System.out.println("✅ 是否為對稱樹: " + isSymmetric(root));

        // 2. 鏡像轉換
        TreeNode mirrored = mirrorTree(copyTree(root)); // 不要破壞原始樹
        System.out.print("🔁 鏡像樹中序：");
        printInOrder(mirrored);
        System.out.println();

        // 3. 是否互為鏡像
        TreeNode treeA = new TreeNode(1);
        treeA.left = new TreeNode(2);
        treeA.right = new TreeNode(3);

        TreeNode treeB = new TreeNode(1);
        treeB.left = new TreeNode(3);
        treeB.right = new TreeNode(2);

        System.out.println("🔍 兩樹是否互為鏡像: " + areMirrors(treeA, treeB));

        // 4. 子樹檢查
        TreeNode mainTree = new TreeNode(1);
        mainTree.left = new TreeNode(2);
        mainTree.right = new TreeNode(3);
        mainTree.left.left = new TreeNode(4);

        TreeNode subTree = new TreeNode(2);
        subTree.left = new TreeNode(4);

        System.out.println("🌳 子樹檢查結果: " + isSubtree(mainTree, subTree));
    }

    // 深度複製樹（避免破壞原始樹）
    public static TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode newNode = new TreeNode(root.val);
        newNode.left = copyTree(root.left);
        newNode.right = copyTree(root.right);
        return newNode;
    }
}