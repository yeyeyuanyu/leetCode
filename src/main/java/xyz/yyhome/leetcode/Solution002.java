package xyz.yyhome.leetcode;

import java.util.List;

public class Solution002 {
    /**
     * 537. 复数乘法
     * 复数 可以用字符串表示，遵循 "实部+虚部i" 的形式，并满足下述条件：
     *
     * 实部 是一个整数，取值范围是 [-100, 100]
     * 虚部 也是一个整数，取值范围是 [-100, 100]
     * i2 == -1
     * 给你两个字符串表示的复数 num1 和 num2 ，请你遵循复数表示形式，返回表示它们乘积的字符串。
     * 输入：num1 = "1+1i", num2 = "1+1i"
     * 输出："0+2i"
     * 解释：(1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i ，你需要将它转换为 0+2i 的形式。
     * 示例 2：
     *
     * 输入：num1 = "1+-1i", num2 = "1+-1i"
     * 输出："0+-2i"
     * 解释：(1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i ，你需要将它转换为 0+-2i 的形式。
     */
    public String complexNumberMultiply(String num1,String num2){
        String[] complex1 = num1.split("\\+|i");
        String[] complex2 = num2.split("\\+|i");
        int real1 = Integer.parseInt(complex1[0]);
        int imag1 = Integer.parseInt(complex1[1]);
        int real2 = Integer.parseInt(complex2[0]);
        int imag2 = Integer.parseInt(complex2[1]);
        return String.format("%d+%di", real1 * real2 - imag1 * imag2, real1 * imag2 + imag1 * real2);
    }
    /**
     * 1712. 将数组分成三个子数组的方案数
     * 我们称一个分割整数数组的方案是 好的 ，当它满足：
     *
     * 数组被分成三个 非空 连续子数组，从左至右分别命名为 left ， mid ， right 。
     * left 中元素和小于等于 mid 中元素和，mid 中元素和小于等于 right 中元素和。
     * 给你一个 非负 整数数组 nums ，请你返回 好的 分割 nums 方案数目。由于答案可能会很大，请你将结果对 109 + 7 取余后返回。
     */
    public int waysToSplit(int[] nums) {
        int ans=0;
        int left=nums[0],mid=left+nums[1],right=mid+nums[2];
        for (int i = 3; i < nums.length; i++) {
            if (left < mid && mid < right){
                ans++;
            }

        }
        return ans;
    }
    /**
     * 1220. 统计元音字母序列的数目
     * 给你一个整数 n，请你帮忙统计一下我们可以按下述规则形成多少个长度为 n 的字符串：
     *
     * 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
     * 每个元音 'a' 后面都只能跟着 'e'
     * 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
     * 每个元音 'i' 后面 不能 再跟着另一个 'i'
     * 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
     * 每个元音 'u' 后面只能跟着 'a'
     * 由于答案可能会很大，所以请你返回 模 10^9 + 7 之后的结果。
     */
    public int countVowelPermutation(int n) {
        return 0;
    }

    /**
     * 373. 查找和最小的 K 对数字
     * 给定两个以 升序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
     * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
     * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        return null;
    }


    /**
     * 344. 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     */
    public void reverseString(char[] s) {
        char temp;
        for (int i = 0, j = s.length-1; i < j; i++,j--) {
            temp = s[i];
            s[i] =s[j];
            s[j] = temp;
        }
    }
}
