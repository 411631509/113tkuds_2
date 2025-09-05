import java.io.*;
import java.util.*;

/**
 * 題目 20 防災物資組合總和（II 版：每元素最多用一次，需去重）
 * 檔名：LC40_CombinationSum2_Supplies.java
 *
 * 輸入：
 *   第一行：n target
 *   第二行：n 個整數
 * 輸出：
 *   每行一組升序組合（和為 target；組合需去重）
 *
 * 技巧：排序 + 同層去重（i > idx 且 a[i] == a[i-1] 跳過）
 */
public class LC40_CombinationSum2_Supplies {
    static List<List<Integer>> ans = new ArrayList<>();
    static int[] a;
    static int n, target;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        target = Integer.parseInt(st.nextToken());

        a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(a);

        dfs(0, 0, new ArrayList<>());

        for (List<Integer> comb : ans) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(comb.get(i));
            }
            System.out.println();
        }
    }

    // 每元素最多用一次 -> 下一層從 i+1 開始；同層去重
    static void dfs(int idx, int sum, List<Integer> path) {
        if (sum == target) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = idx; i < n; i++) {
            if (i > idx && a[i] == a[i - 1]) continue; // 同層去重
            int v = a[i];
            if (sum + v > target) break; // 剪枝
            path.add(v);
            dfs(i + 1, sum + v, path);  // 元素只能用一次
            path.remove(path.size() - 1);
        }
    }
}
