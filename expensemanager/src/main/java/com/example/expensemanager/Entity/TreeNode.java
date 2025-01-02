package com.example.expensemanager.Entity;

public class TreeNode {
    public IncomeRecord data;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(IncomeRecord data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

