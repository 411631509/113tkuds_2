// 檔名：AVLRotationExercise.java
public class AVLRotationExercise {

    /* ===== 節點 ===== */
    static class Node {
        int data;
        Node left, right;
        int height;

        Node(int data) {
            this.data = data;
            this.height = 1; // 葉節點高度 = 1
        }
    }

    /* ===== 小工具 ===== */
    static int h(Node n) { return n == null ? 0 : n.height; }

    static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    // 更新節點高度
    static void updateHeight(Node node) {
        if (node != null) node.height = Math.max(h(node.left), h(node.right)) + 1;
    }

    /* ===== 旋轉 ===== */
    // 右旋 (LL修正)
    //        y                 x
    //       / \               / \
    //      x   T3   ==>      T1  y
    //     / \                   / \
    //    T1 T2                 T2 T3
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // 旋轉
        x.right = y;
        y.left = T2;

        // 更新高度（先子後父）
        updateHeight(y);
        updateHeight(x);

        return x; // 新根
    }

    // 左旋 (RR修正)
    //      x                     y
    //     / \                   / \
    //    T1  y      ==>        x  T3
    //       / \               / \
    //      T2 T3             T1 T2
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // 旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        updateHeight(x);
        updateHeight(y);

        return y; // 新根
    }

    // 左右旋 (LR修正) = 左旋左子 + 右旋自己
    static Node leftRightRotate(Node node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    // 右左旋 (RL修正) = 右旋右子 + 左旋自己
    static Node rightLeftRotate(Node node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    /* ===== 輸出工具 ===== */
    static void printInOrder(Node n) {
        if (n == null) return;
        printInOrder(n.left);
        System.out.print(n.data + "(h=" + h(n) + ",BF=" + bf(n) + ") ");
        printInOrder(n.right);
    }

    static void printTree(String title, Node root) {
        System.out.print(title + " ");
        printInOrder(root);
        System.out.println();
    }

    /* ===== 測試四種不平衡 ===== */
    public static void main(String[] args) {
        // 1) LL 不平衡：插入 30, 20, 10
        Node ll = new Node(30);
        ll.left = new Node(20);
        ll.left.left = new Node(10);
        updateHeight(ll.left.left);
        updateHeight(ll.left);
        updateHeight(ll);

        printTree("LL 旋轉前:", ll);
        ll = rightRotate(ll);
        printTree("LL 右旋後:", ll);
        System.out.println();

        // 2) RR 不平衡：插入 10, 20, 30
        Node rr = new Node(10);
        rr.right = new Node(20);
        rr.right.right = new Node(30);
        updateHeight(rr.right.right);
        updateHeight(rr.right);
        updateHeight(rr);

        printTree("RR 旋轉前:", rr);
        rr = leftRotate(rr);
        printTree("RR 左旋後:", rr);
        System.out.println();

        // 3) LR 不平衡：插入 30, 10, 20
        Node lr = new Node(30);
        lr.left = new Node(10);
        lr.left.right = new Node(20);
        updateHeight(lr.left.right);
        updateHeight(lr.left);
        updateHeight(lr);

        printTree("LR 旋轉前:", lr);
        lr = leftRightRotate(lr);
        printTree("LR 左右旋後:", lr);
        System.out.println();

        // 4) RL 不平衡：插入 10, 30, 20
        Node rl = new Node(10);
        rl.right = new Node(30);
        rl.right.left = new Node(20);
        updateHeight(rl.right.left);
        updateHeight(rl.right);
        updateHeight(rl);

        printTree("RL 旋轉前:", rl);
        rl = rightLeftRotate(rl);
        printTree("RL 右左旋後:", rl);
    }
}