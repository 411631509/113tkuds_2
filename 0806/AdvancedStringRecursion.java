import java.util.*;

public class AdvancedStringRecursion {

    // 1. 遞迴產生字串的所有排列組合
    public static void permute(String str) {
        permuteHelper("", str);
    }

    private static void permuteHelper(String prefix, String remaining) {
        if (remaining.isEmpty()) {
            System.out.println(prefix);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            String next = prefix + remaining.charAt(i);
            String rest = remaining.substring(0, i) + remaining.substring(i + 1);
            permuteHelper(next, rest);
        }
    }

    // 2. 遞迴字串匹配（模擬 strStr: 找 needle 在 haystack 中的起始索引）
    public static int recursiveMatch(String haystack, String needle) {
        return matchHelper(haystack, needle, 0);
    }

    private static int matchHelper(String haystack, String needle, int index) {
        if (index + needle.length() > haystack.length()) return -1;
        if (haystack.substring(index, index + needle.length()).equals(needle)) return index;
        return matchHelper(haystack, needle, index + 1);
    }

    // 3. 遞迴移除字串中的重複字符（保留第一次出現）
    public static String removeDuplicates(String str) {
        return removeDuplicatesHelper(str, 0, new HashSet<>());
    }

    private static String removeDuplicatesHelper(String str, int index, Set<Character> seen) {
        if (index == str.length()) return "";
        char c = str.charAt(index);
        if (seen.contains(c)) {
            return removeDuplicatesHelper(str, index + 1, seen);
        } else {
            seen.add(c);
            return c + removeDuplicatesHelper(str, index + 1, seen);
        }
    }

    // 4. 遞迴列出所有子字串組合（不重複，不限連續）
    public static void allSubsequences(String str) {
        allSubseqHelper(str, 0, "");
    }

    private static void allSubseqHelper(String str, int index, String current) {
        if (index == str.length()) {
            if (!current.isEmpty()) System.out.println(current);
            return;
        }

        // include current character
        allSubseqHelper(str, index + 1, current + str.charAt(index));
        // exclude current character
        allSubseqHelper(str, index + 1, current);
    }

    // 主程式：測試功能
    public static void main(String[] args) {
        // 測試排列組合
        System.out.println("字串排列組合 (abc):");
        permute("abc");

        // 測試字串匹配
        String haystack = "hello world";
        String needle = "lo";
        int index = recursiveMatch(haystack, needle);
        System.out.println("\n字串匹配：" + needle + " 在 " + haystack + " 中的位置: " + index);

        // 測試移除重複字符
        String input = "banana";
        String noDuplicates = removeDuplicates(input);
        System.out.println("\n移除重複字符：" + input + " → " + noDuplicates);

        // 測試所有子字串組合
        System.out.println("\n所有子字串組合 (abc):");
        allSubsequences("abc");
    }
}