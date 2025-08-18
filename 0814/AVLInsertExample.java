public class AVLInsertExample {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        
        System.out.println("=== AVL 樹插入演示 ===");
        
        // 插入序列會導致不平衡的數據
        int[] values = {10, 20, 30, 40, 50, 25};
        
        for (int value : values) {
            System.out.println("插入: " + value);
            tree.insert(value);
            System.out.print("當前樹狀態: ");
            tree.printTree();
            System.out.println();
        }
        
        System.out.println("=== 搜尋測試 ===");
        System.out.println("搜尋 25: " + tree.search(25));
        System.out.println("搜尋 35: " + tree.search(35));
    }
}