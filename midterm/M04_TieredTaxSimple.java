import java.io.*;
import java.util.*;

public class M04_TieredTaxSimple {

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

    // 稅率級距
    static final int[] bound = {120000, 500000, 1000000}; // 上限值
    static final int[] rate = {5, 12, 20, 30};            // 對應稅率(%)

    // 計算一個收入的稅額
    static int calcTax(int income) {
        int tax = 0;
        int prev = 0;
        for (int i = 0; i < bound.length; i++) {
            if (income > bound[i]) {
                tax += (bound[i] - prev) * rate[i] / 100;
                prev = bound[i];
            } else {
                tax += (income - prev) * rate[i] / 100;
                return tax;
            }
        }
        // 超過最後一級
        tax += (income - prev) * rate[rate.length - 1] / 100;
        return tax;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int income = fs.nextInt();
            int tax = calcTax(income);
            sum += tax;
            out.println("Tax: " + tax);
        }
        out.println("Average: " + (sum / n));
        out.flush();
    }

    /*
     * Time Complexity: O(n)
     * 說明: 每筆收入依稅率區間逐段計算，級距數量固定(常數)，
     * 單筆計算 O(1)，共 n 筆輸入，因此整體 O(n)。
     */
}
