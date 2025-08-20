import java.io.*;
import java.util.*;

public class M03_TopKConvenience {

    // -------- Fast I/O --------
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            while (c > ' ') { sb.append((char)c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * sgn;
        }
    }

    static class Item {
        String name;
        int qty;
        Item(String n, int q) { name = n; qty = q; }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int K = fs.nextInt();

        // 最小堆（容量 K）：堆頂是「目前前 K 中最差」的那個
        // 以 qty 升冪；若 qty 相同，以 name 降冪（讓字典序較大的當成較差，優先被移除）
        PriorityQueue<Item> pq = new PriorityQueue<>(new Comparator<Item>() {
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(a.qty, b.qty);      // 小在前
                return b.name.compareTo(a.name); // 名稱大者在前(較差)
            }
        });

        for (int i = 0; i < n; i++) {
            String name = fs.next();
            int qty = fs.nextInt();
            Item cur = new Item(name, qty);
            if (pq.size() < K) pq.offer(cur);
            else {
                Item worst = pq.peek();
                // 若更好（qty 大；或 qty 相等但名稱字典序較小），就換掉最差
                if (qty > worst.qty || (qty == worst.qty && name.compareTo(worst.name) < 0)) {
                    pq.poll();
                    pq.offer(cur);
                }
            }
        }

        // 取出並依「高到低；同量名稱升冪」排序後輸出
        List<Item> ans = new ArrayList<>(pq);
        Collections.sort(ans, new Comparator<Item>() {
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(b.qty, a.qty);      // 大在前
                return a.name.compareTo(b.name);                                // 名稱小在前
            }
        });

        for (Item it : ans) {
            out.println(it.name + " " + it.qty);
        }
        out.flush();
    }

    /*
     * Time Complexity: O(n log K)
     * 說明: 使用容量為 K 的 Min-Heap 維持前 K 大元素，
     * 每筆資料至多觸發一次推入/彈出，單筆成本 O(log K)；
     * 最後對 K 筆做一次排序 O(K log K) 以高到低輸出。
     * 平手處理：qty 相同以名稱字典序升冪決定順序（穩定且可重現）。
     */
}
