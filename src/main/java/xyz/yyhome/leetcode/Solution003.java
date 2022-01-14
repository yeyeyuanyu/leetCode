package xyz.yyhome.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution003 {

    public boolean validTicTacToe(String[] board) {
        int x = 0;
        int o = 0;
        for (String row : board) {
            for (char c : row.toCharArray()) {
                x = (c == 'X') ? (x + 1) : x;
                o = (c == 'O') ? (o + 1) : o;
            }
        }
        if (o > x)
            return false;
        else if (o == x){
            //x don't win
            if (win(board,'X')) return false;
        }else {
            //x > o o don't win
            if (win(board,'O')) return false;
        }
        return true;
    }
    public boolean win(String[] board, char p) {
        for (int i = 0; i < 3; ++i) {
            if (p == board[0].charAt(i) && p == board[1].charAt(i) && p == board[2].charAt(i)) {
                return true;
            }
            if (p == board[i].charAt(0) && p == board[i].charAt(1) && p == board[i].charAt(2)) {
                return true;
            }
        }
        if (p == board[0].charAt(0) && p == board[1].charAt(1) && p == board[2].charAt(2)) {
            return true;
        }
        if (p == board[0].charAt(2) && p == board[1].charAt(1) && p == board[2].charAt(0)) {
            return true;
        }
        return false;
    }

    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        int mid = 0;
        while (left <= right){
            //mid = (left + right)/2;
            mid = left + (right - left)/2;
            if (isBadVersion(mid)){
                right = mid -1;
            }else {
                left = mid + 1;
            }
        }
        return left;
    }
    private boolean isBadVersion(int n) {
        if (n >= 1){
            return true;
        }
        return false;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {

    }
    public String countAndSay(int n) {
        StringBuilder s1 = new StringBuilder("1");
        StringBuilder s2 = new StringBuilder();
        for (int i = 1; i < n; i++) {
            int num = 1;
            char temp = s1.charAt(0);
            for (int si = 1; si < s1.length(); si++) {
                if (temp == s1.charAt(si)){
                    num++;
                }else {
                    s2.append(num);
                    s2.append(temp);
                    temp = s1.charAt(si);
                    num = 1;
                }
            }
            s2.append(num);
            s2.append(temp);
            s1 = s2;
            s2 = new StringBuilder();
        }
        return s1.toString();
    }
    public int myAtoi(String s) {
        s = s.trim();
        if (s.length() == 0)
            return 0;
        if (!isFirst(s.charAt(0)))
            return 0;
        StringBuilder str = new StringBuilder();
        str.append(s.charAt(0));
        char temp;
        for (int i = 1; i < s.length(); i++) {
            temp = s.charAt(i);
            if (Character.isDigit(temp)){
                str.append(temp);
                continue;
            }
            break;
        }
        try {
            return Integer.parseInt(str.toString());
        }catch (NumberFormatException e){
            if (str.length() == 1)
                return 0;
            else if (str.charAt(0) == '-')
                return Integer.MIN_VALUE;
            else
                return Integer.MAX_VALUE;
        }
    }
    private boolean isFirst(char charAt) {
        if (charAt == '-' || charAt == '+' || Character.isDigit(charAt)){
            return true;
        }
        return false;
    }

    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        char left;
        char right;
        int i = 0;
        int j = s.length()-1;
        while (i < j){
            left = s.charAt(i);
            if (!Character.isLetterOrDigit(left)){
                i++;
                continue;
            }
            right = s.charAt(j);
            if (!Character.isLetterOrDigit(right)){
                j--;
                continue;
            }
            if (right != left){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public int firstUniqChar(String s) {
        int result = s.length();
        boolean flag = false;
        char[] cc = new char[26];
        for (int i = 0; i < s.length(); i++) {
            cc[s.charAt(i)-'a']++;
        }
        for (int i = 0; i < cc.length; i++) {
            if (cc[i] == 1){
                result = Math.min(s.indexOf(i+97),result);
                flag = true;
            }
        }
        return flag?result:-1;
    }

    public int reverse(int x) {
        try {
            return  Integer.parseInt(new String(reverseString(Integer.toString(x).toCharArray())));
        } catch (NumberFormatException e){
            return 0;
        }
    }
    public char[] reverseString(char[] s) {
        char temp;
        int i = 0;
        if (s[0] == '-'){
            i=1;
        }
        for (int j = s.length-1; i < j; i++,j--) {
            temp = s[i];
            s[i] =s[j];
            s[j] = temp;
        }
        return s;
    }

    /**
     * 将字节数组转换成十六进制字符串
     * @param bytes
     * @return
     */
    static char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static String byte2str(byte []bytes){
        int len = bytes.length;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < len; i++) {
            byte byte0 = bytes[i];
            result.append(hex[byte0 >>> 4 & 0xf]);
            result.append(hex[byte0 & 0xf]);
        }
        return result.toString();
    }

    /**
     *
     * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     * @param s source
     * @param p target
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<Integer>();
        Set<Character> set = new HashSet<>();
        Set<Character> temp = new HashSet<>();
        int windowLength = p.length();
        for (int i = 0; i < windowLength; i++) {
            set.add(p.charAt(i));
        }
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < windowLength; j++) {
                if (set.contains(chars[i])){

                }
            }
        }
        return list;
    }
}
