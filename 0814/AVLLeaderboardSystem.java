// 檔名：AVLLeaderboardSystem.java（Java 8 相容版）
import java.util.*;
import java.util.AbstractMap;

/**
 * 排行榜以「分數高到低」排序；同分以玩家名稱字典序排序。
 * 透過 AVL + 子樹大小(size) 支援排名/TopK/選第k名。
 */
public class AVLLeaderboardSystem {

    /* ================= 節點定義 ================= */
    static class Node {
        String player;
        int score;
        Node left, right;
        int height;  // AVL 高度
        int size;    // 子樹節點數（用於排名）

        Node(String player, int score) {
            this.player = player;
            this.score = score;
            this.height = 1;
            this.size = 1;
        }
    }

    /* ================= 內部狀態 ================= */
    private Node root;
    // 快速查玩家當前分數（更新時先刪舊再插新）
    private final Map<String, Integer> scoreOf = new HashMap<String, Integer>();

    /* ================= 小工具 ================= */
    private static int h(Node n) { return n == null ? 0 : n.height; }
    private static int sz(Node n) { return n == null ? 0 : n.size; }
    private static void update(Node n) {
        if (n != null) {
            n.height = Math.max(h(n.left), h(n.right)) + 1;
            n.size   = sz(n.left) + sz(n.right) + 1;
        }
    }
    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    // 比較：分數高者「較小」（往左），同分以玩家名詞典序較小者在前
    private static int cmp(String p1, int s1, String p2, int s2) {
        if (s1 != s2) return (s1 > s2) ? -1 : 1;
        int nameCmp = p1.compareTo(p2);
        if (nameCmp < 0) return -1;
        if (nameCmp > 0) return 1;
        return 0;
    }

    /* ================= 旋轉/平衡 ================= */
    private static Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        update(y); update(x);
        return x;
    }
    private static Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        update(x); update(y);
        return y;
    }
    private static Node rebalance(Node n) {
        update(n);
        int b = bf(n);
        if (b > 1) {
            if (bf(n.left) < 0) n.left = leftRotate(n.left); // LR
            return rightRotate(n);                           // LL
        }
        if (b < -1) {
            if (bf(n.right) > 0) n.right = rightRotate(n.right); // RL
            return leftRotate(n);                                // RR
        }
        return n;
    }

    /* ================= 插入/刪除 ================= */
    private Node insert(Node n, String player, int score) {
        if (n == null) return new Node(player, score);
        int c = cmp(player, score, n.player, n.score);
        if (c < 0) n.left  = insert(n.left,  player, score);
        else if (c > 0) n.right = insert(n.right, player, score);
        else { // 同名同分
            n.score = score;
            return n;
        }
        return rebalance(n);
    }

    private Node findMin(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    private Node delete(Node n, String player, int score) {
        if (n == null) return null;
        int c = cmp(player, score, n.player, n.score);
        if (c < 0) n.left = delete(n.left, player, score);
        else if (c > 0) n.right = delete(n.right, player, score);
        else {
            if (n.left == null || n.right == null) {
                Node child = (n.left != null) ? n.left : n.right;
                return child; // 可能為 null
            } else {
                Node succ = findMin(n.right);
                n.player = succ.player;
                n.score  = succ.score;
                n.right  = delete(n.right, succ.player, succ.score);
            }
        }
        return rebalance(n);
    }

    /* ================= 對外 API ================= */
    /** 新增或更新分數（更新會先刪舊再插新）。 */
    public void addOrUpdateScore(String player, int score) {
        if (scoreOf.containsKey(player)) {
            int old = scoreOf.get(player);
            root = delete(root, player, old);
        }
        root = insert(root, player, score);
        scoreOf.put(player, score);
    }

    /** 取得玩家名次（第一名=1；不存在回傳 -1）。 */
    public int getRank(String player) {
        Integer sc = scoreOf.get(player);
        if (sc == null) return -1;
        return getRankByKey(root, player, sc);
    }

    private int getRankByKey(Node n, String player, int score) {
        int rankBefore = 0;
        while (n != null) {
            int c = cmp(player, score, n.player, n.score);
            if (c < 0) {
                n = n.left; // 更前面，往左
            } else if (c > 0) {
                rankBefore += sz(n.left) + 1; // 略過左子樹+當前
                n = n.right;
            } else {
                rankBefore += sz(n.left);
                return rankBefore + 1;
            }
        }
        return -1;
    }

    /** 回傳前 K 名（依排名順序）。 */
    public List<Map.Entry<String, Integer>> topK(int k) {
        List<Map.Entry<String, Integer>> out = new ArrayList<Map.Entry<String, Integer>>(k);
        inorderTopK(root, out, k);
        return out;
    }

    private void inorderTopK(Node n, List<Map.Entry<String, Integer>> out, int k) {
        if (n == null || out.size() >= k) return;
        inorderTopK(n.left, out, k);
        if (out.size() < k) out.add(new AbstractMap.SimpleEntry<String, Integer>(n.player, n.score));
        if (out.size() < k) inorderTopK(n.right, out, k);
    }

    /** 取得第 k 名（1-based），不存在回傳 null。 */
    public Map.Entry<String, Integer> selectKth(int k) {
        if (k <= 0 || k > sz(root)) return null;
        Node n = root;
        int kk = k;
        while (n != null) {
            int leftSize = sz(n.left);
            if (kk == leftSize + 1) return new AbstractMap.SimpleEntry<String, Integer>(n.player, n.score);
            if (kk <= leftSize) n = n.left;
            else { kk -= leftSize + 1; n = n.right; }
        }
        return null;
    }

    /** 中序輸出（從高到低） */
    public void printRanking() {
        System.out.println("=== Ranking (high -> low) ===");
        printInOrder(root);
        System.out.println();
    }
    private void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print("[" + n.player + ":" + n.score + "] ");
        printInOrder(n.right);
    }

    /* ================= Demo ================= */
    public static void main(String[] args) {
        AVLLeaderboardSystem lb = new AVLLeaderboardSystem();

        lb.addOrUpdateScore("Alice", 1200);
        lb.addOrUpdateScore("Bob",   1500);
        lb.addOrUpdateScore("Cindy", 1500);
        lb.addOrUpdateScore("Derek", 900);
        lb.addOrUpdateScore("Evan",  1300);

        lb.printRanking();

        System.out.println("Rank(Bob)   = " + lb.getRank("Bob"));
        System.out.println("Rank(Cindy) = " + lb.getRank("Cindy"));
        System.out.println("Rank(Derek) = " + lb.getRank("Derek"));

        System.out.println("\n>> Update Alice -> 1600");
        lb.addOrUpdateScore("Alice", 1600);
        lb.printRanking();
        System.out.println("Rank(Alice) = " + lb.getRank("Alice"));

        System.out.println("\nTop 3:");
        List<Map.Entry<String, Integer>> top3 = lb.topK(3);
        for (int i = 0; i < top3.size(); i++) {
            Map.Entry<String, Integer> e = top3.get(i);
            System.out.println((i + 1) + ". " + e.getKey() + " : " + e.getValue());
        }

        System.out.println("\nSelect 2nd: " + lb.selectKth(2));
    }
}