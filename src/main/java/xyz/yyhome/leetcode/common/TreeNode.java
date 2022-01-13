package xyz.yyhome.leetcode.common;

/**
 * @ClassName TreeNode
 * @Description TODO leetcode提供的刷题需要的类 Definition for a binary tree node
 * @Author yy
 * @Date 2021/12/25 12:47
 * @Version 1.0
 */

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
