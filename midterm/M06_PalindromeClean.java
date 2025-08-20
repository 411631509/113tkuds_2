import java.io.*;

public class M06_PalindromeClean {

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
        String nextLine() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) != -1 && c != '\n' && c != '\r') {
                sb.append((char)c);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String s = fs.nextLine();
        if (s == null) s = "";

        // 轉小寫，只保留 a-z
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        // 雙指標檢查回文
        String str = cleaned.toString();
        int l = 0, r = str.length() - 1;
        boolean ok = true;
        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) {
                ok = false;
                break;
            }
            l++;
            r--;
        }

        out.println(ok ? "Yes" : "No");
        out.flush();
    }

    /*
     * Time Complexity: O(n)
     * 說明: 一次遍歷清洗字串 O(n)，再用雙指標比對 O(n)。
     * 總體時間複雜度為 O(n)，n 為輸入字串長度。
     */
}
