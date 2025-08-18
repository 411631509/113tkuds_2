import java.util.*;

public class RecursiveTreePreview {

    // ====== 1. 遞迴計算資料夾的總檔案數 ======
    static class FileNode {
        String name;
        boolean isFile;
        List<FileNode> children;

        FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            this.children = new ArrayList<>();
        }

        void add(FileNode child) {
            children.add(child);
        }
    }

    public static int countFiles(FileNode node) {
        if (node.isFile) return 1;

        int count = 0;
        for (FileNode child : node.children) {
            count += countFiles(child);
        }
        return count;
    }

    // ====== 2. 遞迴列印多層選單結構 ======
    static class MenuItem {
        String title;
        List<MenuItem> subItems;

        MenuItem(String title) {
            this.title = title;
            this.subItems = new ArrayList<>();
        }

        void add(MenuItem item) {
            subItems.add(item);
        }
    }

    public static void printMenu(MenuItem item, int level) {
        System.out.println(repeatString("  ", level) + "- " + item.title);
        for (MenuItem sub : item.subItems) {
            printMenu(sub, level + 1);
        }
    }

    // 支援 Java 8 的 repeat 替代方案
    public static String repeatString(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    // ====== 3. 遞迴展平巢狀陣列 ======
    public static List<Integer> flatten(List<Object> nestedList) {
        List<Integer> result = new ArrayList<>();
        flattenHelper(nestedList, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static void flattenHelper(List<Object> list, List<Integer> result) {
        for (Object item : list) {
            if (item instanceof Integer) {
                result.add((Integer) item);
            } else if (item instanceof List) {
                flattenHelper((List<Object>) item, result);
            }
        }
    }

    // ====== 4. 遞迴計算巢狀清單最大深度 ======
    @SuppressWarnings("unchecked")
    public static int maxDepth(List<Object> nestedList) {
        int max = 1;
        for (Object item : nestedList) {
            if (item instanceof List) {
                int depth = 1 + maxDepth((List<Object>) item);
                max = Math.max(max, depth);
            }
        }
        return max;
    }

    // ====== 主程式：測試各項功能 ======
    public static void main(String[] args) {

        // --- 測試 1: 檔案系統 ---
        FileNode root = new FileNode("root", false);
        FileNode folderA = new FileNode("folderA", false);
        FileNode folderB = new FileNode("folderB", false);
        FileNode file1 = new FileNode("file1.txt", true);
        FileNode file2 = new FileNode("file2.txt", true);
        FileNode file3 = new FileNode("file3.txt", true);

        folderA.add(file1);
        folderA.add(file2);
        folderB.add(file3);
        root.add(folderA);
        root.add(folderB);

        System.out.println("📁 資料夾總檔案數: " + countFiles(root));

        // --- 測試 2: 多層選單 ---
        MenuItem main = new MenuItem("主選單");
        MenuItem settings = new MenuItem("設定");
        MenuItem profile = new MenuItem("個人資料");
        MenuItem security = new MenuItem("安全性");
        MenuItem about = new MenuItem("關於");

        settings.add(profile);
        settings.add(security);
        main.add(settings);
        main.add(about);

        System.out.println("\n📋 多層選單：");
        printMenu(main, 0);

        // --- 測試 3: 巢狀陣列展平 ---
        List<Object> nestedArray = Arrays.asList(1, Arrays.asList(2, 3), Arrays.asList(Arrays.asList(4, 5), 6));
        System.out.println("\n🔁 展平陣列: " + flatten(nestedArray));

        // --- 測試 4: 巢狀清單最大深度 ---
        List<Object> nestedList = Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, Arrays.asList(4))));
        System.out.println("\n📏 巢狀清單最大深度: " + maxDepth(nestedList));
    }
}