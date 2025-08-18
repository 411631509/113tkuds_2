import java.util.*;

public class LevelOrderTraversalVariations {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 每層儲存在不同 List 中
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
        }

        return result;
    }

    // 2. 之字形層序走訪
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        boolean leftToRight = true;
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (leftToRight)
                    level.addLast(node.val);
                else
                    level.addFirst(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }

    // 3. 每層最後一個節點
    public static List<Integer> rightmostPerLevel(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode last = null;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                last = node;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (last != null) result.add(last.val);
        }

        return result;
    }

    // 4. 垂直層序走訪
    static class NodeInfo {
        TreeNode node;
        int x;

        NodeInfo(TreeNode node, int x) {
            this.node = node;
            this.x = x;
        }
    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Map<Integer, List<Integer>> columnTable = new TreeMap<>();
        Queue<NodeInfo> queue = new LinkedList<>();
        queue.offer(new NodeInfo(root, 0));

        while (!queue.isEmpty()) {
            NodeInfo info = queue.poll();
            TreeNode node = info.node;
            int x = info.x;

            columnTable.computeIfAbsent(x, k -> new ArrayList<>()).add(node.val);

            if (node.left != null) queue.offer(new NodeInfo(node.left, x - 1));
            if (node.right != null) queue.offer(new NodeInfo(node.right, x + 1));
        }

        for (List<Integer> column : columnTable.values()) {
            result.add(column);
        }

        return result;
    }

    // 主程式：測試四種走訪
    public static void main(String[] args) {
        /*
                測試用樹：
                      1
                     / \
                    2   3
                   / \ / \
                  4  5 6  7
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // 1. 每層儲存在 List 中
        System.out.println("📘 分層儲存：");
        List<List<Integer>> levelOrder = levelOrder(root);
        for (List<Integer> level : levelOrder) {
            System.out.println(level);
        }

        // 2. 之字形層序
        System.out.println("\n🔁 之字形層序：");
        List<List<Integer>> zigzag = zigzagLevelOrder(root);
        for (List<Integer> level : zigzag) {
            System.out.println(level);
        }

        // 3. 每層最後一個節點
        System.out.println("\n🎯 每層最後一個節點：");
        List<Integer> rightmost = rightmostPerLevel(root);
        System.out.println(rightmost);

        // 4. 垂直層序走訪
        System.out.println("\n🧭 垂直層序走訪：");
        List<List<Integer>> vertical = verticalOrder(root);
        for (List<Integer> column : vertical) {
            System.out.println(column);
        }
    }
}