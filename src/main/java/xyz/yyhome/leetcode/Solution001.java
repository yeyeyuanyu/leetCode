package xyz.yyhome.leetcode;

import xyz.yyhome.RecordTopic;
import xyz.yyhome.TopicType;
import xyz.yyhome.leetcode.common.TreeNode;

import java.util.*;

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
        System.out.println(new Solution001().checkPerfectNumber(28));
    }
    /**
     * 1154. 一年中的第几天
     * 给你一个日期，请你设计一个算法来判断它是对应一周中的哪一天。
     * 输入为三个整数：day、month 和 year，分别表示日、月、年。
     * 您返回的结果必须是这几个值中的一个。
     */
    private static String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static String dayOfTheWeek(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(year,month,day));
        int week_index = cal.get(Calendar.DAY_OF_WEEK);
        return week[week_index];
    }

    /**
     * 507. 完美数
     * 对于一个 正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
     * 给定一个 整数 n， 如果是完美数，返回 true，否则返回 false
     */
    public boolean checkPerfectNumber(int num) {
        int ans = 0;
        for (int i = 1; i <= num; i++) {
            if (num % i == 0){
                ans = ans + i;
            }
        }
        return ans == num*2;

        //这题解能把我气死
        //return num == 6 || num == 28 || num == 496 || num == 8128 || num == 33550336;
    }

    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     */
    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, ans = n;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 709. 转换成小写字母
     * 给你一个字符串 s ，将该字符串中的大写字母转换成相同的小写字母，返回新的字符串。
     * 大写变小写、小写变大写 : 字符 ^= 32;
     * 大写变小写、小写变小写 : 字符 |= 32;
     * 小写变大写、大写变大写 : 字符 &= -33;
     */
    public String toLowerCase(String s) {
        return s.toLowerCase();
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
     * 1609. 奇偶树
     * 如果一棵二叉树满足下述几个条件，则可以称为 奇偶树 ：
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

    /**
     * 747. 至少是其他数字两倍的最大数
     * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
     * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。如果是，则返回 最大元素的下标 ，否则返回 -1 。
     * 示例 1：
     * 输入：nums = [3,6,1,0]
     * 输出：1
     * 解释：6 是最大的整数，对于数组中的其他整数，6 大于数组中其他元素的两倍。6 的下标是 1 ，所以返回 1 。
     */
    public int  dominantIndex(int[] nums) {
        int len = nums.length;
        if (nums.length == 1) return 0;
        int [] temp = nums.clone();
        Arrays.sort(temp);
        return temp[len-1] >= temp[len-2]*2 ? geti(temp[len-1],nums):-1;
    }
    private static int geti(int val, int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val){
                return i;
            }
        }
        return -1;
    }

    /**
     * 334. 递增的三元子序列
     * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
     *如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
     */
    public boolean increasingTriplet(int[] nums) {
        int len = nums.length;
        if (len<3) return false;
        int i=nums[0],j=Integer.MAX_VALUE;
        for (int ki = 1; ki < len; ki++) {
            if (nums[ki] > j){
                return true;
            }
            if (nums[ki] > i){
                j = nums[ki];
            }else {
                i = nums[ki];
            }
        }
        return false;
    }

    /**
     * 1576. 替换所有的问号
     * 给你一个仅包含小写英文字母和 '?' 字符的字符串 s，请你将所有的 '?' 转换为若干小写字母，使最终的字符串不包含任何 连续重复 的字符。
     * 注意：你 不能 修改非 '?' 字符。
     * 题目测试用例保证 除 '?' 字符 之外，不存在连续重复的字符。
     * 在完成所有转换（可能无需转换）后返回最终的字符串。如果有多个解决方案，请返回其中任何一个。可以证明，在给定的约束条件下，答案总是存在的。
     */
    public static String modifyString(String s) {
        if (s==null) return null;
        if (s.length() == 0) return s;
        if (s.length() == 1) return s.equals("?")?"a":s;

        char[] chs = ("a"+s+"a").toCharArray();
        for (int i = 1; i < chs.length-1; i++) {
            if (chs[i] == '?'){
                chs[i] = getCharNotEqualsParam(chs[i-1],chs[i+1]);
            }
        }
        return new String(chs).substring(1,chs.length-1);
    }
    private static char[] zm = {'a', 'b', 'c'};
    private static char getCharNotEqualsParam(char ch, char ch1) {
        int i = 0;
        while ( i < zm.length ){
            if (ch != zm[i] && ch1 !=zm[i]){
                break;
            }
            i++;
        }
        return zm[i];
    }

    /**
     * 71. 简化路径
     * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
     * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
     * 请注意，返回的 规范路径 必须遵循下述格式：
     * 始终以斜杠 '/' 开头。
     * 两个目录名之间必须只有一个斜杠 '/' 。
     * 最后一个目录名（如果存在）不能 以 '/' 结尾。
     * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
     * 返回简化后得到的 规范路径 。
     */
    public static String simplifyPath(String path) {
        String[] strs = path.split("/");
        List<String> ans = new LinkedList<>();
        String temp;
        for (int i = 0; i < strs.length; i++) {
            temp = strs[i];
            if("".equals(temp) || ".".equals(temp)){
                continue;
            }
            if ("..".equals(temp)){
                if (ans.size() != 0) {
                    ans.remove(ans.size() - 1);
                }
                continue;
            }
            ans.add(temp);
        }
        if (ans.size() != 0){
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < ans.size(); i++) {
                result.append("/"+ans.get(i));
            }
            return result.toString();
        }
        return "/";
    }

    /**
     * 1614. 括号的最大嵌套深度
     * 如果字符串满足以下条件之一，则可以称之为 有效括号字符串（valid parentheses string，可以简写为 VPS）：
     * 字符串是一个空字符串 ""，或者是一个不为 "(" 或 ")" 的单字符。
     * 字符串可以写为 AB（A 与 B 字符串连接），其中 A 和 B 都是 有效括号字符串 。
     * 字符串可以写为 (A)，其中 A 是一个 有效括号字符串 。
     * 类似地，可以定义任何有效括号字符串 S 的 嵌套深度 depth(S)：
     * 题目数据保证括号表达式 s 是 有效的括号表达式
     */
    public static int maxDeep(String str){
        int ans = 0;
        char[] chars = str.toCharArray();
        int temp = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '('){
                temp ++;
            }else if (chars[i] == ')'){
                temp --;
            }
            ans = Math.max(ans,temp);
        }
        return ans;
    }

    /**
     * 1816. 截断句子
     * 句子 是一个单词列表，列表中的单词之间用单个空格隔开，且不存在前导或尾随空格。每个单词仅由大小写英文字母组成（不含标点符号）。
     * 例如，"Hello World"、"HELLO" 和 "hello world hello world" 都是句子。
     * 给你一个句子 s 和一个整数 k，请你将 s 截断 ，使截断后的句子仅含 前 k 个单词。返回 截断 s 后得到的句子。
     */
    public static String truncateSentence(String s, int k) {
        int end = 0;
        int temp = 0;
        for (int i = 0; i < k; i++) {
            temp = s.indexOf(" ",end+1);
            end = temp == -1 ? s.length() : temp;
            System.out.println(temp);
        }
        return s.substring(0,end);
    }

    /**
     * 242. 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        char[] ss = new char[26];
        char[] tt = new char[26];
        for (int i = 0; i < s.length(); i++) {
            ss[s.charAt(i)-'a']++;
            tt[t.charAt(i)-'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (ss[i] != tt[i]){
                return false;
            }
        }
        return true;
    }

}
