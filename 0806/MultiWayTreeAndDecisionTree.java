import java.util.*;

public class MultiWayTreeAndDecisionTree {

    // ===== 多路樹節點定義 =====
    static class TreeNode {
        String value;
        List<TreeNode> children;

        TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        void addChild(TreeNode child) {
            children.add(child);
        }
    }

    // ===== 1. DFS 走訪 =====
    public static void dfs(TreeNode node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        for (TreeNode child : node.children) {
            dfs(child);
        }
    }

    // ===== 2. BFS 走訪 =====
    public static void bfs(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.value + " ");
            queue.addAll(current.children);
        }
    }

    // ===== 3. 決策樹：猜數字遊戲模擬 =====
    static class DecisionNode {
        String question;
        DecisionNode yes;
        DecisionNode no;

        DecisionNode(String question) {
            this.question = question;
        }
    }

    public static void playGuessingGame(DecisionNode node, Scanner scanner) {
        if (node.yes == null && node.no == null) {
            System.out.println("我猜的是：" + node.question);
            return;
        }

        System.out.println(node.question + " (y/n)");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("y")) {
            playGuessingGame(node.yes, scanner);
        } else if (input.equals("n")) {
            playGuessingGame(node.no, scanner);
        } else {
            System.out.println("請輸入 y 或 n。");
            playGuessingGame(node, scanner);
        }
    }

    // ===== 4. 計算多路樹的高度 =====
    public static int getHeight(TreeNode node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;
        int maxChildHeight = 0;
        for (TreeNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, getHeight(child));
        }
        return maxChildHeight + 1;
    }

    // ===== 5. 印出每個節點的度數 =====
    public static void printDegrees(TreeNode node) {
        if (node == null) return;
        System.out.println("節點 " + node.value + " 的度數為: " + node.children.size());
        for (TreeNode child : node.children) {
            printDegrees(child);
        }
    }

    // ===== 主程式測試 =====
    public static void main(String[] args) {
        // === 建立一棵多路樹 ===
        TreeNode root = new TreeNode("A");
        TreeNode b = new TreeNode("B");
        TreeNode c = new TreeNode("C");
        TreeNode d = new TreeNode("D");
        TreeNode e = new TreeNode("E");
        TreeNode f = new TreeNode("F");

        root.addChild(b);
        root.addChild(c);
        b.addChild(d);
        b.addChild(e);
        c.addChild(f);

        System.out.println("📌 DFS 走訪：");
        dfs(root);
        System.out.println("\n📌 BFS 走訪：");
        bfs(root);

        System.out.println("\n📏 多路樹高度：" + getHeight(root));
        System.out.println("📋 各節點度數：");
        printDegrees(root);

        // === 決策樹模擬 ===
        System.out.println("\n🎮 開始猜數字遊戲（決策樹）：");
        DecisionNode start = new DecisionNode("數字 > 5 嗎？");
        start.yes = new DecisionNode("數字是 6");
        start.no = new DecisionNode("數字是 3");

        Scanner scanner = new Scanner(System.in);
        playGuessingGame(start, scanner);
        scanner.close();
    }
}