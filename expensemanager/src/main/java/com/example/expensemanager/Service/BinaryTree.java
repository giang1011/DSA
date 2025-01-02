package com.example.expensemanager.Service;

import com.example.expensemanager.Entity.IncomeRecord;
import com.example.expensemanager.Entity.TreeNode;

public class BinaryTree {
    private TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public void insert(IncomeRecord record) {
        root = insertRecursive(root, record);
    }

    private TreeNode insertRecursive(TreeNode current, IncomeRecord record) {
        if (current == null) {
            return new TreeNode(record);
        }

        double currentAmount = parseAmount(current.data.getAmount());
        double newAmount = parseAmount(record.getAmount());

        if (newAmount < currentAmount) {
            current.left = insertRecursive(current.left, record);
        } else if (newAmount > currentAmount) {
            current.right = insertRecursive(current.right, record);
        }
        return current;
    }

    public void traverseInOrder() {
        traverseInOrderRecursive(root);
    }

    private void traverseInOrderRecursive(TreeNode node) {
        if (node != null) {
            traverseInOrderRecursive(node.left);
            System.out.println(node.data);
            traverseInOrderRecursive(node.right);
        }
    }

    private double parseAmount(String amountStr) {
        return Double.parseDouble(amountStr.replace("VND ", "").replace(",", ""));
    }
}

