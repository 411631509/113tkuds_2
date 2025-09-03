import java.util.*;

class Solution {
    // 題目：3Sum
    // 找出所有三元組 (i, j, k)，使 nums[i] + nums[j] + nums[k] == 0，且不能重複
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // 先排序，方便去重與雙指針

        for (int i = 0; i < nums.length - 2; i++) {
            // 若第一個數已經大於 0，後面不可能湊出 0
            if (nums[i] > 0) break;
            // 跳過重複的起始數
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    // 跳過重複的數字
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) {
                    left++; // 總和太小 → 左指針右移
                } else {
                    right--; // 總和太大 → 右指針左移
                }
            }
        }
        return res;
    }
}

/*
解題思路（排序 + 雙指針）：
1. 對 nums 排序，方便處理重複與指針移動。
2. 外層迴圈固定一個數 nums[i]，然後用雙指針 (left, right) 在 i 右側尋找。
3. 若 nums[i] + nums[left] + nums[right] == 0 → 收錄結果，並跳過重複值。
4. 若總和 < 0 → left++，若總和 > 0 → right--。
5. 注意去重：對 i、left、right 都要檢查避免重複。

例子：
nums = [-1,0,1,2,-1,-4] → [[-1,-1,2], [-1,0,1]]

時間複雜度：O(n^2)，排序 O(n log n)，外層迴圈 O(n)，內層雙指針 O(n)。
空間複雜度：O(1)（輸出結果不計算）。
*/