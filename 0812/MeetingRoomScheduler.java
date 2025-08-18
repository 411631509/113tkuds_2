import java.util.*;

public class MeetingRoomScheduler {

    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        PriorityQueue<Integer> endTimes = new PriorityQueue<>();
        for (int[] meeting : intervals) {
            if (!endTimes.isEmpty() && meeting[0] >= endTimes.peek()) {
                endTimes.poll();
            }
            endTimes.offer(meeting[1]);
        }
        return endTimes.size();
    }

    public static int maxTotalTimeWithRooms(int[][] intervals, int rooms) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // 結束時間早的先
        int total = 0;
        PriorityQueue<Integer> roomEnd = new PriorityQueue<>();
        for (int[] meeting : intervals) {
            while (!roomEnd.isEmpty() && roomEnd.peek() > meeting[0] && roomEnd.size() >= rooms) {
                // 無可用房間 → 跳過
                break;
            }
            if (roomEnd.size() < rooms || meeting[0] >= roomEnd.peek()) {
                if (!roomEnd.isEmpty() && meeting[0] >= roomEnd.peek()) {
                    roomEnd.poll();
                }
                roomEnd.offer(meeting[1]);
                total += meeting[1] - meeting[0];
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int[][] m1 = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(minMeetingRooms(m1)); // 2

        int[][] m2 = {{1, 4}, {2, 3}, {4, 6}};
        System.out.println(maxTotalTimeWithRooms(m2, 1)); // 5
    }
}