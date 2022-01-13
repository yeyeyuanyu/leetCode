package xyz.yyhome.leetcode;

import xyz.yyhome.RecordTopic;
import xyz.yyhome.TopicType;
import xyz.yyhome.leetcode.common.TreeNode;

import javax.swing.*;
import java.util.*;
import java.util.function.IntFunction;

/**
 * @ClassName Solution001
 * @Description TODO leeCode练习题
 * @Author yy
 * @Date 2021/12/23 23:17
 * @Version 1.0
 */

public class Solution001 {
    public static TreeNode root = new TreeNode(1);
    static {
        root.left = new TreeNode(10);
        root.right = new TreeNode(4);
    }
    public static void main(String[] args) {
        System.out.println(new Solution001().findAllConcatenatedWordsInADict(new String[]{"sre","rrrrrr","mm","zz","z"}));
        System.out.println(new Solution001().countQuadruplets(new int[]{1,2,3,4}));
    }

    /**
     * 1995. 统计特殊四元组
     * 给你一个 下标从 0 开始 的整数数组 nums ，返回满足下述条件的 不同 四元组 (a, b, c, d) 的 数目 ：
     * nums[a] + nums[b] + nums[c] == nums[d] ，且
     * a < b < c < d
     * @param nums
     * @return
     */
    public int countQuadruplets(int[] nums) {
        int result = 0;
        int length = nums.length;
        //我TM的直接暴力
        for (int i = 0; i < length-3; i++) {
            for (int j = i+1; j < length-2; j++) {
                for (int k = j+1; k < length-1; k++) {
                    for (int v = k+1; v < length; v++) {
                        if (nums[i]+nums[j]+nums[k]==nums[v]){
                            result++;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 472. 连接词
     * 给你一个 不含重复 单词的字符串数组 words ，请你找出并返回 words 中的所有 连接词 。
     * 连接词 定义为：一个完全由给定数组中的至少两个较短单词组成的字符串。
     * @param words
     * @return
     */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<String>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() == 0) {
                continue;
            }
            if (dfs(word, 0)) {
                ans.add(word);
            } else {
                insert(word);
            }
        }
        return ans;
    }
    public boolean dfs(String word, int start) {
        if (word.length() == start) {
            return true;
        }
        Trie node = trie;
        for (int i = start; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            node = node.children[index];
            if (node == null) {
                return false;
            }
            if (node.isEnd) {
                if (dfs(word, i + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void insert(String word) {
        Trie node = trie;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }
    Trie trie = new Trie();
    class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }
    }

    /**
     * 1078. Bigram 分词
     * 给出第一个词 first 和第二个词 second，考虑在某些文本 text 中可能以 "first second third" 形式出现的情况，
     * 其中 second 紧随 first 出现，third 紧随 second 出现。
     * 对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
     * @param text
     * @param first
     * @param second
     * @return
     */
    public String[] findOcurrences(String text, String first, String second) {
        String[] split = text.split(" ");
        List<String> list = new ArrayList<>();
        int i = 2;
        while (i < split.length){
            if (split[i-2].equals(first) && split[i-1].equals(second)) {
                list.add(split[i]);
            }
            i++;
        }
        return list.toArray(new String[list.size()]);
        //我觉得没问题，但有个用例过不了，离谱。
        /*String[] split = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 2;
        while (i < split.length){
            if (split[i-2].equals(first) && split[i-1].equals(second)){
                stringBuilder.append(split[i]);
                stringBuilder.append(" ");
            }
            i++;
        }
        return stringBuilder.toString().split(" ");*/
    }

    /**
     * 如果一棵二叉树满足下述几个条件，则可以称为 奇偶树 ：
     *
     * 二叉树根节点所在层下标为 0 ，根的子节点所在层下标为 1 ，根的孙节点所在层下标为 2 ，依此类推。
     * 偶数下标 层上的所有节点的值都是 奇 整数，从左到右按顺序 严格递增
     * 奇数下标 层上的所有节点的值都是 偶 整数，从左到右按顺序 严格递减
     * 给你二叉树的根节点，如果二叉树为 奇偶树 ，则返回 true ，否则返回 false 。
     * 提示：
     * 树中节点数在范围 [1, 105] 内
     * 1 <= Node.val <= 106
     * @param root
     * @return
     */
    public boolean isEvenOddTree(TreeNode root) {
        if (root == null){
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        int deep = 0;
        queue.add(root);

        //记录当前层元素个数和下一层元素个数
        int currDeepNum = queue.size();
        int nextDeepNum = 0;
        int i;

        //记录前一个值和当前值
        int pre;
        int curr;

        while(!queue.isEmpty()){
            pre = 0;
            curr = 0;
            i = 0;

            //偶数下标 层上的所有节点的值都是 奇 整数，从左到右按顺序 严格递增
            root =  queue.remove();
            if (deep % 2 == 0){
                //每层的第一个没有前一个，单独判断不参加循环
                if ((curr = root.val) % 2 == 0){
                    return false;
                }
                pre = curr;
                nextDeepNum += queueAdd(queue,root);
                i++;
                while (i < currDeepNum){
                    root =  queue.remove();
                    if((curr = root.val) % 2 == 0){
                        return false;
                    }
                    if (curr <= pre){
                        return false;
                    }
                    pre = curr;
                    nextDeepNum += queueAdd(queue,root);
                    i++;
                }
            }
            //奇数下标 层上的所有节点的值都是 偶 整数，从左到右按顺序 严格递减
            else {
                if ((curr = root.val) % 2 != 0){
                    return false;
                }
                pre = curr;
                nextDeepNum += queueAdd(queue,root);
                i++;
                while (i < currDeepNum){
                    root =  queue.remove();
                    if((curr = root.val) % 2 != 0){
                        return false;
                    }
                    if (curr >= pre){
                        return false;
                    }
                    pre = curr;
                    nextDeepNum += queueAdd(queue,root);
                    i++;
                }
            }
            deep++;
            currDeepNum = nextDeepNum;
            nextDeepNum = 0;
        }
        return true;
    }
    private int queueAdd(Queue<TreeNode> queue, TreeNode root) {
        int num = 0;
        if (root.left != null){
            queue.add(root.left);
            num++;
        }
        if (root.right != null){
            queue.add(root.right);
            num++;
        }
        return num;
    }

    /**
     * 1705. 吃苹果的最大数目
     * 有一棵特殊的苹果树，一连 n 天，每天都可以长出若干个苹果。在第 i 天，树上会长出 apples[i] 个苹果，这些苹果将会在 days[i] 天后
     * （也就是说，第 i + days[i] 天时）腐烂，变得无法食用。也可能有那么几天，树上不会长出新的苹果，此时用 apples[i] == 0 且 days[i] == 0 表示。
     * 你打算每天 最多 吃一个苹果来保证营养均衡。注意，你可以在这 n 天之后继续吃苹果。
     * 给你两个长度为 n 的整数数组 days 和 apples ，返回你可以吃掉的苹果的最大数目。
     * @param apples
     * @param days
     * @return
     */
    @RecordTopic(type = {TopicType.GREEDY})
    public int eatenApples(int[] apples, int[] days) {
        int eat = 0;
        //优先队列，把天数作为优先值
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        int[] temp = null;
        int i = 0;
        int n = apples.length;
        while (i < n) {
            //当天有苹果就放入优先队列
            if (apples[i] > 0){
                pq.add(new int[]{days[i]+i, apples[i]});
            }
            //去除掉过期的苹果
            while(null!= (temp = pq.peek()) && temp[0] <= i){
                pq.remove();
            }
            //有就吃一个
            if(null != temp){
                eat++;
                temp[1]--;
                //吃一个后为0就去除掉
                if (temp[1] == 0){
                    pq.remove();
                }
            }
            i++;
        }
        //没有新的苹果，一直吃，直到过期完或吃完
        while (null != (temp = pq.peek())){
            //去除掉过期的苹果
            while(null!=(temp = pq.peek()) && temp[0] <= i){
                pq.remove();
            }
            //有就吃一个
            if(null != temp){
                eat++;
                temp[1]--;
                //吃一个后为0就去除掉
                if (temp[1] == 0){
                    pq.remove();
                }
            }
            i++;
        }
        return eat;
    }

    /**
     * 剑指 Offer 04. 二维数组中的查找
     * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * @param matrix
     * @param target
     * @return
     */
    @RecordTopic(type = {TopicType.ARRAY})
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        //空值直接返回false
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        //从左下角开始查找
        int i = matrix.length-1;
        int j = 0;
        while (i >= 0 && j < matrix[0].length){
            if (matrix[i][j] == target){
                return true;
            }else if (matrix[i][j] > target){
                i--;
            }else {
                j++;
            }
        }
        return false;
    }
    /**
     * 1005. K 次取反后最大化的数组和
     * @param nums
     * @param k
     * @return
     */
    @RecordTopic(type = {TopicType.ARRAY})
    public int largestSumAfterKNegations(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        int ans = Arrays.stream(nums).sum();
        for (int i = -100; i < 0; ++i) {
            if (freq.containsKey(i)) {
                int ops = Math.min(k, freq.get(i));
                ans += (-i) * ops * 2;
                freq.put(i, freq.get(i) - ops);
                freq.put(-i, freq.getOrDefault(-i, 0) + ops);
                k -= ops;
                if (k == 0) {
                    break;
                }
            }
        }
        if (k > 0 && k % 2 == 1 && !freq.containsKey(0)) {
            for (int i = 1; i <= 100; ++i) {
                if (freq.containsKey(i)) {
                    ans -= i * 2;
                    break;
                }
            }
        }
        return ans;
    }
}
