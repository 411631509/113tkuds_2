import java.io.*;
import java.util.*;

public class M05_GCD_LCM_Recursive {

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
        long nextLong() throws IOException {
            int c; long sgn = 1, x = 0;
            while ((c = read()) <= ' ') if (c == -1) return Long.MIN_VALUE;
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * sgn;
        }
    }

    // 遞迴版歐幾里得算法
    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        long a = fs.nextLong();
        long b = fs.nextLong();

        long g = gcd(a, b);
        long l = (a / g) * b;   // 先除後乘，避免溢位

        out.println("GCD: " + g);
        out.println("LCM: " + l);
        out.flush();
    }

    /*
     * Time Complexity: O(log min(a, b)))
     * 說明: 以遞迴歐幾里得算法計算 gcd；LCM 由 a/gcd*b 取得（先除後乘避免溢位），
     * 單次取模成本常數，迭代次數與較小數量級的對數成正比。
     */
}
