public class AVLNode {
    int data;
    AVLNode left, right;
    int height;

    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }

    // 計算平衡因子 (BF = 左高 - 右高)
    public int getBalance() {
        int lh = (left != null) ? left.height : 0;
        int rh = (right != null) ? right.height : 0;
        return lh - rh;
    }

    // 更新節點高度
    public void updateHeight() {
        int lh = (left != null) ? left.height : 0;
        int rh = (right != null) ? right.height : 0;
        this.height = Math.max(lh, rh) + 1;
    }

    /* ------------ 內部樹結構與操作 ------------ */
    static class AVLTree {
        AVLNode root;

        int height(AVLNode n) { return n == null ? 0 : n.height; }

        int balance(AVLNode n) { return n == null ? 0 : (height(n.left) - height(n.right)); }

        // 右旋
        AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            // 旋轉
            x.right = y;
            y.left = T2;

            // 更新高度
            y.updateHeight();
            x.updateHeight();

            System.out.println("【右旋】根: " + fmt(y) + " -> 新根: " + fmt(x));
            return x;
        }

        // 左旋
        AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            // 旋轉
            y.left = x;
            x.right = T2;

            // 更新高度
            x.updateHeight();
            y.updateHeight();

            System.out.println("【左旋】根: " + fmt(x) + " -> 新根: " + fmt(y));
            return y;
        }

        // 插入
        AVLNode insert(AVLNode node, int key) {
            if (node == null) return new AVLNode(key);

            if (key < node.data) node.left = insert(node.left, key);
            else if (key > node.data) node.right = insert(node.right, key);
            else return node; // 不插入重複

            // 回溯更新高度
            node.updateHeight();

            int bf = balance(node);

            // LL
            if (bf > 1 && key < node.left.data) return rightRotate(node);
            // RR
            if (bf < -1 && key > node.right.data) return leftRotate(node);
            // LR
            if (bf > 1 && key > node.left.data) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            // RL
            if (bf < -1 && key < node.right.data) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            return node;
        }

        public void insert(int key) {
            root = insert(root, key);
            System.out.print("當前樹狀態: ");
            printTree();
            System.out.println();
        }

        // 簡單搜尋
        public boolean search(int key) {
            AVLNode cur = root;
            while (cur != null) {
                if (key == cur.data) return true;
                cur = key < cur.data ? cur.left : cur.right;
            }
            return false;
        }

        // 中序列印：值(高|BF)
        public void printTree() { inorder(root); }

        void inorder(AVLNode n) {
            if (n == null) return;
            inorder(n.left);
            System.out.print(n.data + "(h=" + n.height + ",BF=" + (height(n.left)-height(n.right)) + ") ");
            inorder(n.right);
        }

        static String fmt(AVLNode n) {
            if (n == null) return "null";
            int lh = n.left == null ? 0 : n.left.height;
            int rh = n.right == null ? 0 : n.right.height;
            return "AVLNode(data=" + n.data + ", height=" + n.height + ", BF=" + (lh - rh) + ")";
        }
    }

    /* ------------ 這裡就是 main ------------ */
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        System.out.println("=== AVL 樹插入演示 ===");
        int[] values = {10, 20, 30, 40, 50, 25}; // 會觸發各種旋轉
        for (int v : values) {
            System.out.println("插入: " + v);
            tree.insert(v);
        }

        System.out.println("=== 搜尋測試 ===");
        System.out.println("搜尋 25: " + tree.search(25));
        System.out.println("搜尋 35: " + tree.search(35));
    }
}