import java.util.*;

public class StockMaximizer {

    public static int maxProfit(int[] prices, int K) {
        if (prices == null || prices.length < 2 || K <= 0) return 0;
        List<Integer> profits = new ArrayList<>();

        int buy = 0, sell = 0;
        for (int i = 1; i < prices.length; i++) {
            // 找上升段
            if (prices[i] >= prices[i - 1]) {
                sell = i;
            } else {
                if (sell > buy) profits.add(prices[sell] - prices[buy]);
                buy = sell = i;
            }
        }
        if (sell > buy) profits.add(prices[sell] - prices[buy]);

        // 用最大堆取出前 K 筆利潤
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.addAll(profits);

        int total = 0;
        for (int i = 0; i < K && !maxHeap.isEmpty(); i++) {
            total += maxHeap.poll();
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{2, 4, 1}, 2));               // 2
        System.out.println(maxProfit(new int[]{3, 2, 6, 5, 0, 3}, 2));      // 7
        System.out.println(maxProfit(new int[]{1, 2, 3, 4, 5}, 2));         // 4
    }
}