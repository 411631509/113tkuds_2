// 檔名：AVLTree.java
public class AVLTree {
    private AVLNode root;

    // 取得節點高度
    private int getHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }

    /* ================== 插入 ================== */
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLNode insertNode(AVLNode node, int data) {
        // 1) 標準 BST 插入
        if (node == null) return new AVLNode(data);

        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node; // 重複值不插入
        }

        // 2) 更新高度
        node.updateHeight();

        // 3) 取得平衡因子
        int balance = node.getBalance();

        // 4) 四種失衡情況
        // LL
        if (balance > 1 && data < node.left.data) {
            return AVLRotations.rightRotate(node);
        }
        // RR
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }
        // LR
        if (balance > 1 && data > node.left.data) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }
        // RL
        if (balance < -1 && data < node.right.data) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    /* ================== 搜尋 ================== */
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public boolean search(int data) {
        return searchNode(root, data);
    }

    private boolean searchNode(AVLNode node, int data) {
        if (node == null) return false;
        if (data == node.data) return true;
        if (data < node.data) return searchNode(node.left, data);
        return searchNode(node.right, data);
    }

    /* ================== 刪除 ================== */
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void delete(int data) {
        root = deleteNode(root, data);
    }

    private AVLNode deleteNode(AVLNode node, int data) {
        // 1) 標準 BST 刪除
        if (node == null) return null;

        if (data < node.data) {
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪的節點
            if (node.left == null || node.right == null) {
                // 只有 0 或 1 個子節點：直接把子節點接上去
                AVLNode child = (node.left != null) ? node.left : node.right;
                return child;
            } else {
                // 兩個子節點：取右子樹最小者(中序後繼)
                AVLNode succ = findMin(node.right);
                node.data = succ.data;
                node.right = deleteNode(node.right, succ.data);
            }
        }

        // 2) 更新高度
        node.updateHeight();

        // 3) 重新平衡
        int balance = node.getBalance();

        // LL
        if (balance > 1 && node.left.getBalance() >= 0) {
            return AVLRotations.rightRotate(node);
        }
        // LR
        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }
        // RR
        if (balance < -1 && node.right.getBalance() <= 0) {
            return AVLRotations.leftRotate(node);
        }
        // RL
        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    // 找最小值節點（右子樹中的最左邊）
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    /* ================== 驗證/輸出 ================== */
    // 驗證是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVL(root) != -1;
    }

    private int checkAVL(AVLNode node) {
        if (node == null) return 0;
        int lh = checkAVL(node.left);
        int rh = checkAVL(node.right);
        if (lh == -1 || rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return Math.max(lh, rh) + 1;
    }

    // 中序列印：顯示值與平衡因子
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }

    /* ================== main：可直接執行 ================== */
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        System.out.println("=== AVL 樹插入演示 ===");
        int[] values = {10, 20, 30, 40, 50, 25};  // 會觸發 RR / RL 旋轉
        for (int v : values) {
            System.out.println("插入: " + v);
            tree.insert(v);
            System.out.print("當前樹狀態: ");
            tree.printTree();
        }

        System.out.println("\n=== 搜尋測試 ===");
        System.out.println("搜尋 25: " + tree.search(25));
        System.out.println("搜尋 35: " + tree.search(35));

        System.out.println("\n=== 刪除測試 ===");
        tree.delete(40);
        System.out.print("刪除 40 後: ");
        tree.printTree();
        System.out.println("是 AVL? " + tree.isValidAVL());
    }
}