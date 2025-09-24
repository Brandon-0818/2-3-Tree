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
        return inorder(root);
    }
    /**
     * A helper method for inserting an element into the 2-3 Tree.
     * This method handles the recursive insertion logic and manages node splits.
     *
     * @param node The current node being processed.
     * @param x The element to be inserted into the tree.
     * @param promotedKey An array to store the key promoted during a split, if one occurs.
     * @param newChildren An array to store the new child nodes created during a split, if one occurs.
     * @return The current node if no split occurs, or the node itself if a split needs to be propagated up.
     */
    private TwoThreeNode<E> insertHelper(TwoThreeNode<E> node, E x, E[] promotedKey, TwoThreeNode<E>[] newChildren) {

        // base case: if the node is a leaf, insert the key here

        if (node.leaf) {
            return splitLeafNode(node, x, promotedKey, newChildren);
        }

        // recursive case: traverse to the appropriate child node
        // and insert the key there

        else {
            int pos;

            // determine which child to traverse based on the keys in the current node
            // if x is less than key1, go to left child

            if (x.compareTo(node.key1) < 0) {
                pos = 0;
            }

            // if the node is a 2-node or x is less than key2, go to middle child

            else if (node.key2 == null || x.compareTo(node.key2) < 0){
                pos = 1;
            }

            // otherwise, go to right child

            else {
                pos = 2;
            }

            // recursively insert into the appropriate child

            E[] childPromoted = (E[]) new Comparable[1];
            TwoThreeNode<E>[] childNewChildren = new TwoThreeNode[2];
            TwoThreeNode<E> split = null;

            // traverse to the left, middle, or right child based on the position

            if (pos == 0) {
                split = insertHelper(node.lc, x, childPromoted, childNewChildren);
            }
            else if (pos == 1) {
                split = insertHelper(node.mc, x, childPromoted, childNewChildren);
            }
            else {
                split = insertHelper(node.rc, x, childPromoted, childNewChildren);
            }

            // if the child was split, handle the split at the current node

            if (split == null) {
                return null;
            }

            // otherwise, split the current node

            return splitNonLeafNode(node, childPromoted[0], childNewChildren, pos, promotedKey, newChildren);
        }
    }
    /**
     * Splits a leaf node in the 2-3 Tree when it already contains two keys.
     * If the node is a 2-node, the new key is inserted in sorted order without splitting.
     * If the node is a 3-node, the keys are sorted, and the middle key is promoted.
     * Two new leaf nodes are created for the smallest and largest keys.
     *
     * @param node The leaf node to be split.
     * @param x The new key to be inserted into the leaf node.
     * @param promotedKey An array to store the key promoted during the split.
     * @param newChildren An array to store the two new child nodes created during the split.
     * @return Null if no split occurs, or the original node to signal a split.
     */
    private TwoThreeNode<E> splitLeafNode(TwoThreeNode<E> node, E x, E[] promotedKey, TwoThreeNode<E>[] newChildren) {

        // if the node is a 2-node, insert the key here

        if (node.key2 == null) {

            // insert the key in sorted order
            // if x is less than key1, insert it as the first key

            if (x.compareTo(node.key1) < 0) {
                node.key2 = node.key1;
                node.key1 = x;
            }

            // otherwise, insert it as the second key

            else {
                node.key2 = x;
            }

            // no split occurred

            return null;
        }
        else {

            // if the node is a 3-node, split it
            // create two new nodes and promote the middle key
            // sort the keys to find the middle key
            // create new leaf nodes for the new children
            // promote the middle key

            E[] keys = (E[]) new Comparable[]{node.key1, node.key2, x};
            Arrays.sort(keys);
            newChildren[0] = new TwoThreeNode<>(keys[0], null, null, true);
            newChildren[1] = new TwoThreeNode<>(keys[2], null, null, true);
            promotedKey[0] = keys[1];

            // signal that a split occurred

            return node;
        }
    }
    /**
     * Splits a non-leaf (internal) node in the 2-3 Tree when it already contains two keys.
     * If the node is a 2-node, the promoted key is inserted in sorted order without splitting.
     * If the node is a 3-node, the keys are sorted, and the middle key is promoted.
     * Two new internal nodes are created for the smallest and largest keys.
     *
     * @param node The internal node to be split.
     * @param childPromoted The key promoted from the child node that was split.
     * @param childNewChildren An array containing the two new child nodes created during the child split.
     * @param pos The position of the child node that was split (0 for left, 1 for middle, 2 for right).
     * @param promotedKey An array to store the key promoted during the split of the current node.
     * @param newChildren An array to store the two new child nodes created during the split of the current node.
     * @return Null if no split occurs, or the original node to signal a split.
     */
    private TwoThreeNode<E> splitNonLeafNode(TwoThreeNode<E> node, E childPromoted, TwoThreeNode<E>[] childNewChildren,
                                             int pos, E[] promotedKey, TwoThreeNode<E>[] newChildren) {

        // if the node is a 2-node, insert the promoted key here

        if (node.key2 == null) {

            // insert the key and rearrange the children
            // if the promoted key is less than key1, insert it as the first key
            // and rearrange the children accordingly

            if (pos == 0) {
                node.key2 = node.key1;
                node.key1 = childPromoted;
                node.rc = node.mc;
                node.lc = childNewChildren[0];
                node.mc = childNewChildren[1];
            }

            // if the promoted key is greater than key1, insert it as the second key
            // and rearrange the children accordingly

            else {
                node.key2 = childPromoted;
                node.mc = childNewChildren[0];
                node.rc = childNewChildren[1];
            }

            // no split occurred

            return null;
        }
        else {

            // if the node is a 3-node, split it
            // create two new nodes and promote the middle key
            // sort the keys to find the middle key
            // create new non-leaf nodes for the new children
            // promote the middle key
            // rearrange the children accordingly

            E[] keys = (E[]) new Comparable[]{node.key1, node.key2, childPromoted};
            Arrays.sort(keys);
            TwoThreeNode<E> left, right;

            // create new non-leaf nodes for the new children based on the position of the promoted key
            // if the promoted key is from the left child

            if (pos == 0) {
                left = new TwoThreeNode<>(keys[0], childNewChildren[0], childNewChildren[1], false);
                right = new TwoThreeNode<>(keys[2], node.mc, node.rc, false);
            }

            // if the promoted key is from the middle child

            else if (pos == 1) {
                left = new TwoThreeNode<>(keys[0], node.lc, childNewChildren[0], false);
                right = new TwoThreeNode<>(keys[2], childNewChildren[1], node.rc, false);
            }

            // if the promoted key is from the right child

            else {
                left = new TwoThreeNode<>(keys[0], node.lc, node.mc, false);
                right = new TwoThreeNode<>(keys[2], childNewChildren[0], childNewChildren[1], false);
            }

            // promote the middle key
            promotedKey[0] = keys[1];
            newChildren[0] = left;
            newChildren[1] = right;

            // signal that a split occurred

            return node;
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
     * Searches for a specific element in the 2-3 Tree.
     * This method traverses the tree starting from the root and checks
     * if the given element exists in any of the nodes.
     *
     * @param x The element to search for in the tree.
     * @return True if the element is found, false otherwise.
     */
    public boolean contains(E x) {

        // start from the root and traverse down the tree

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
     * Inserts a new element into the 2-3 Tree.
     * If the tree is empty, a new root node is created.
     * Otherwise, the element is inserted into the appropriate position,
     * and any necessary node splits are handled.
     *
     * @param x The element to be inserted into the tree.
     */
    public void insert(E x) {

        // if the tree is empty, create a new root node

        if (root == null) {
            root = new TwoThreeNode<>(x, null, null, true);
            return;
        }

        // otherwise, insert the data into the tree
        // and handle the case where the root is split
        // by creating a new root node

        E[] promotedKey = (E[]) new Comparable[1];
        TwoThreeNode<E>[] newChildren = new TwoThreeNode[2];
        TwoThreeNode<E> split = insertHelper(root, x, promotedKey, newChildren);

        // if the root is split, create a new root node

        if (split != null) {
            root = new TwoThreeNode<>(promotedKey[0], newChildren[0], newChildren[1], false);
        }
    }
}
