import java.util.*;

public class AVLPerformanceTest {
    public static void main(String[] args) {
        System.out.println("=== AVL 樹性能測試 ===");
        
        AVLTree avlTree = new AVLTree();
        Random random = new Random();
        int n = 10000;
        
        // 插入測試
        long startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            avlTree.insert(random.nextInt(100000));
        }
        long insertTime = System.nanoTime() - startTime;
        
        // 搜尋測試
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            avlTree.search(random.nextInt(100000));
        }
        long searchTime = System.nanoTime() - startTime;
        
        System.out.println("插入 " + n + " 個元素耗時: " + 
                          insertTime / 1_000_000 + " ms");
        System.out.println("搜尋 1000 次耗時: " + 
                          searchTime / 1_000_000 + " ms");
        System.out.println("平均插入時間複雜度: O(log n)");
        System.out.println("平均搜尋時間複雜度: O(log n)");
    }
}