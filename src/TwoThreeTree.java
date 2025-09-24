import java.util.Arrays;

/**
 * The 2-3 Tree class
 * @author ____
 */
public class TwoThreeTree<E extends Comparable<E>> {

    // the root of the tree

    private TwoThreeNode<E> root;

    /**
     * The constructor of the 2-3 Tree
     */
    public TwoThreeTree() {
        root = null;
    }

    /**
     * The in-order traversal of the 2-3 Tree
     *
     * @return the String representation of in-order data
     */
    public String inorder() {
        String s = inorder(root);
        return s;
    }

    private TwoThreeNode<E> insertHelper(TwoThreeNode<E> node, E x, E[] promotedKey, TwoThreeNode<E>[] newChildren) {
        if (node.leaf) {
            return splitLeafNode(node, x, promotedKey, newChildren);
        }
        else {
            int pos;
            if (x.compareTo(node.key1) < 0) {
                pos = 0;
            }
            else if (node.key2 == null || x.compareTo(node.key2) < 0){
                pos = 1;
            }
            else {
                pos = 2;
            }

            E[] childPromoted = (E[]) new Comparable[1];
            TwoThreeNode<E>[] childNewChildren = new TwoThreeNode[2];
            TwoThreeNode<E> split = null;
            if (pos == 0) {
                split = insertHelper(node.lc, x, childPromoted, childNewChildren);
            }
            else if (pos == 1) {
                split = insertHelper(node.mc, x, childPromoted, childNewChildren);
            }
            else {
                split = insertHelper(node.rc, x, childPromoted, childNewChildren);
            }

            if (split == null) {
                return null;
            }
            return splitNonLeafNode(node, childPromoted[0], childNewChildren, pos, promotedKey, newChildren);
        }
    }

    private TwoThreeNode<E> splitLeafNode(TwoThreeNode<E> node, E x, E[] promotedKey, TwoThreeNode<E>[] newChildren) {
        if (node.key2 == null) {
            if (x.compareTo(node.key1) < 0) {
                node.key2 = node.key1;
                node.key1 = x;
            }
            else {
                node.key2 = x;
            }
            return null;
        }
        else {
            E[] keys = (E[]) new Comparable[]{node.key1, node.key2, x};
            Arrays.sort(keys);
            newChildren[0] = new TwoThreeNode<>(keys[0], null, null, true);
            newChildren[1] = new TwoThreeNode<>(keys[2], null, null, true);
            promotedKey[0] = keys[1];
            return node; // signal split
        }
    }

    private TwoThreeNode<E> splitNonLeafNode(TwoThreeNode<E> node, E childPromoted, TwoThreeNode<E>[] childNewChildren, int pos, E[] promotedKey, TwoThreeNode<E>[] newChildren) {
        if (node.key2 == null) {
            if (pos == 0) {
                node.key2 = node.key1;
                node.key1 = childPromoted;
                node.rc = node.mc;
                node.lc = childNewChildren[0];
                node.mc = childNewChildren[1];
            }
            else {
                node.key2 = childPromoted;
                node.mc = childNewChildren[0];
                node.rc = childNewChildren[1];
            }
            return null;
        }
        else {
            E[] keys = (E[]) new Comparable[]{node.key1, node.key2, childPromoted};
            Arrays.sort(keys);
            TwoThreeNode<E> left, right;
            if (pos == 0) {
                left = new TwoThreeNode<>(keys[0], childNewChildren[0], childNewChildren[1], false);
                right = new TwoThreeNode<>(keys[2], node.mc, node.rc, false);
            }
            else if (pos == 1) {
                left = new TwoThreeNode<>(keys[0], node.lc, childNewChildren[0], false);
                right = new TwoThreeNode<>(keys[2], childNewChildren[1], node.rc, false);
            }
            else {
                left = new TwoThreeNode<>(keys[0], node.lc, node.mc, false);
                right = new TwoThreeNode<>(keys[2], childNewChildren[0], childNewChildren[1], false);
            }
            promotedKey[0] = keys[1];
            newChildren[0] = left;
            newChildren[1] = right;
            return node; // signal split
        }
    }

    private String inorder(TwoThreeNode<E> r) {
        String s = "";
        if (r == null)
            return "";
        else if (r.leaf) {
            s = s + "key1: " + String.valueOf(r.key1) + " ";
            if (r.key2 != null)
                s = s + "key2: " + String.valueOf(r.key2) + " ";
        } else {
            s = s + inorder(r.lc);
            s = s + "key1: " + String.valueOf(r.key1) + " ";
            s = s + inorder(r.mc);
            if (r.key2 != null) {
                s = s + "key2: " + String.valueOf(r.key2) + " ";
                s = s + inorder(r.rc);
            }
        }
        return s;
    }

    /**
     * The search method
     *
     * @param x the data to be searched in the tree
     */
    public boolean contains(E x) {
        TwoThreeNode<E> current = root;
        while (current != null) {
            if (x.compareTo(current.key1) == 0 || (current.key2 != null && x.compareTo(current.key2) == 0)) {
                return true;
            }
            if (current.leaf) {
                return false;
            }
            if (x.compareTo(current.key1) < 0) {
                current = current.lc;
            }
            else if (current.key2 == null || x.compareTo(current.key2) < 0) {
                current = current.mc;
            }
            else {
                current = current.rc;
            }
        }
        return false;
    }

    /**
     * The data insertion method for the 2-3 Tree
     *
     * @param x the data to be inserted
     */
    public void insert(E x) {
        E[] promotedKey = (E[]) new Comparable[1];
        TwoThreeNode<E>[] newChildren = new TwoThreeNode[2];
        TwoThreeNode<E> split = insertHelper(root, x, promotedKey, newChildren);
        if (split != null) {
            root = new TwoThreeNode<>(promotedKey[0], newChildren[0], newChildren[1], false);
        }
    }
}
