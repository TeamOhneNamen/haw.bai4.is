package app.logic.minimax;

import app.logic.Board;
import app.ui.Controller;

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

    //colorfull determines if the node should be filled with a color for showing if
    // the minimizer or the mximizer is next to play
    public static <T> String treeToString(TreeNode<T> node, boolean colorful) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("digraph G {\n");
        stringBuffer.append(branchToString(node,colorful));
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static <T> String branchToString(TreeNode<T> node, boolean colorful) {
        StringBuffer stringBuffer = new StringBuffer();
        String stringOfNode;
        String color = "yellow";
        stringOfNode = ((Board)node.data).toSimpleStringWithScore();
        if(colorful){
            if(((Board)node.data).nextPlayerColor.equals(Board.MAXIMIZER)){
                color = "red";
            }else if(((Board)node.data).isPruned()){
                color = "green";
            }else {
                color = "blue";
            }
            stringBuffer.append("\""+stringOfNode+"\""+" [color="+color+"]");
        }
        node.children.forEach(child -> {
            stringBuffer.append("\""+stringOfNode+"\" -> \""+((Board)child.data).toSimpleStringWithScore()+"\";\n");
        });
        node.children.forEach(child -> {
            stringBuffer.append(branchToString(child,colorful));
        });
        return stringBuffer.toString();
    }
}
