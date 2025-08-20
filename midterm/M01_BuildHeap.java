import java.io.*;
import java.util.*;

public class M01_BuildHeap {

    // -------------- Fast I/O --------------
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String type = fs.next();                 // "max" or "min"
        int n = fs.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextInt();

        boolean isMax = "max".equalsIgnoreCase(type);
        buildHeap(a, isMax);

        for (int i = 0; i < n; i++) {
            if (i > 0) out.print(' ');
            out.print(a[i]);
        }
        out.println();
        out.flush();
    }

    // 自底向上建堆：從最後一個非葉節點開始下沉
    static void buildHeap(int[] a, boolean isMax) {
        for (int i = (a.length >>> 1) - 1; i >= 0; i--) {
            siftDown(a, i, a.length, isMax);
        }
    }

    // 迭代式下沉（heapifyDown）
    static void siftDown(int[] a, int i, int n, boolean isMax) {
        while (true) {
            int l = (i << 1) + 1;
            if (l >= n) break;
            int r = l + 1, best = l;

            // 選出較優的子節點（max-heap 選較大；min-heap 選較小）
            if (r < n && compare(a[r], a[l], isMax) > 0) best = r;

            if (compare(a[best], a[i], isMax) > 0) {
                int tmp = a[i]; a[i] = a[best]; a[best] = tmp;
                i = best;
            } else break;
        }
    }

    // 回傳 >0 代表 x 優於 y（依 isMax 決定比較方向）
    static int compare(int x, int y, boolean isMax) {
        return isMax ? Integer.compare(x, y) : Integer.compare(y, x);
    }

    /*
     * Time Complexity: O(n)
     * 說明: 以自底向上（Bottom-up）建堆，對每個節點做有限次下沉；各層成本相加為線性級數，
     * 故整體線性時間完成，不使用 PriorityQueue 與逐一 insert。
     */
}
