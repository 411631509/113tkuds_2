import java.util.*;

public class MergeKSortedArrays {

    static class Node {
        int val, ai, ei;
        Node(int v, int a, int e){ val=v; ai=a; ei=e; }
    }

    public static int[] merge(int[][] arrays) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        int total = 0;
        for (int i=0;i<arrays.length;i++) {
            if (arrays[i].length>0) {
                pq.offer(new Node(arrays[i][0], i, 0));
                total += arrays[i].length;
            }
        }
        int[] res = new int[total];
        int idx = 0;
        while(!pq.isEmpty()){
            Node cur = pq.poll();
            res[idx++] = cur.val;
            int ni = cur.ei + 1;
            if (ni < arrays[cur.ai].length) {
                pq.offer(new Node(arrays[cur.ai][ni], cur.ai, ni));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(merge(new int[][]{{1,4,5},{1,3,4},{2,6}})));
        System.out.println(Arrays.toString(merge(new int[][]{{1,2,3},{4,5,6},{7,8,9}})));
        System.out.println(Arrays.toString(merge(new int[][]{{1},{0}})));
    }
}