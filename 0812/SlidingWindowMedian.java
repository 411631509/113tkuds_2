import java.util.*;

public class SlidingWindowMedian {

    private PriorityQueue<Integer> lo = new PriorityQueue<>(Comparator.reverseOrder()); // max
    private PriorityQueue<Integer> hi = new PriorityQueue<>(); // min
    private Map<Integer,Integer> delayed = new HashMap<>();
    private int loSize = 0, hiSize = 0;

    private void add(int num){
        if (lo.isEmpty() || num <= lo.peek()) { lo.offer(num); loSize++; }
        else { hi.offer(num); hiSize++; }
        rebalance();
    }

    private void remove(int num){
        delayed.put(num, delayed.getOrDefault(num,0)+1);
        if (!lo.isEmpty() && num <= lo.peek()) loSize--; else hiSize--;
        prune(lo); prune(hi);
        rebalance();
    }

    private void rebalance(){
        if (loSize > hiSize + 1) { hi.offer(lo.poll()); loSize--; hiSize++; prune(lo); }
        else if (hiSize > loSize) { lo.offer(hi.poll()); hiSize--; loSize++; prune(hi); }
    }

    private void prune(PriorityQueue<Integer> heap){
        while(!heap.isEmpty()){
            int x = heap.peek();
            Integer c = delayed.get(x);
            if (c == null || c == 0) break;
            heap.poll();
            if (c == 1) delayed.remove(x); else delayed.put(x, c-1);
        }
    }

    private double median(){
        if (loSize > hiSize) return lo.peek();
        return ((double)lo.peek() + (double)hi.peek()) / 2.0;
    }

    public double[] medianSlidingWindow(int[] nums, int k){
        if (k == 0) return new double[0];
        double[] ans = new double[Math.max(0, nums.length - k + 1)];
        for (int i=0;i<nums.length;i++){
            add(nums[i]);
            if (i >= k - 1) {
                ans[i - k + 1] = median();
                remove(nums[i - k + 1]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        SlidingWindowMedian s = new SlidingWindowMedian();
        System.out.println(Arrays.toString(s.medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println(Arrays.toString(s.medianSlidingWindow(new int[]{1,2,3,4}, 2)));
    }
}