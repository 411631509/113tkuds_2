import java.io.*;
import java.util.*;

public class M02_YouBikeNextArrival {

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
            while (c > ' ') { sb.append((char) c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            while ((c = read()) <= ' ') { if (c == -1) return Integer.MIN_VALUE; }
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * sgn;
        }
    }

    // 將 HH:mm 轉為分鐘數
    static int toMinutes(String t) {
        String[] parts = t.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return h * 60 + m;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        String[] times = new String[n];
        int[] mins = new int[n];

        for (int i = 0; i < n; i++) {
            times[i] = fs.next();
            mins[i] = toMinutes(times[i]);
        }

        String queryStr = fs.next();
        int q = toMinutes(queryStr);

        // 二分搜尋第一個 >= q 的位置
        int idx = Arrays.binarySearch(mins, q);
        if (idx < 0) idx = -(idx + 1);

        if (idx < n) out.println(times[idx]);
        else out.println("No bike");

        out.flush();
    }

    /*
     * Time Complexity: O(log n)
     * 說明: 已排序陣列中利用二分搜尋找到第一個大於等於查詢時間的位置；
     * 轉換時間與輸入輸出皆為 O(n)，查詢本身 O(log n)。
     */
}
