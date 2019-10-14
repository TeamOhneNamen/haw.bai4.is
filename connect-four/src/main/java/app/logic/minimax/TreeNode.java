package app.logic.minimax;

import app.logic.Board;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//https://github.com/gt4dev/yet-another-tree-structure/blob/master/java/src/com/tree/TreeNode.java
public class TreeNode<T> {

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    private List<TreeNode<T>> elementsIndex;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        return this.addChildNode(childNode);
    }

    public TreeNode<T> addChildNode(TreeNode<T> childNode) {
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    //https://www.javagists.com/java-tree-data-structure
    public static <T> void printTree(TreeNode<T> node, String appender) {
        //TODO Print to .dot language
        System.out.print(appender);
        ((Board) node.data).print();
        node.children.forEach(each ->  printTree(each, appender + appender));
    }
}
