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
     * @return the String representation of in-order data
     */
    public String inorder() {
        String s = inorder(root);
        return s;
    }

    // The helper method for in-order traversal of the 2-3 Tree

    private String inorder(TwoThreeNode<E> r) {
        String s = "";
        if (r == null)
            return "";
        else
        if (r.leaf) {
            s = s + "key1: " + String.valueOf(r.key1) + " ";
            if (r.key2 != null)
                s = s + "key2: " + String.valueOf(r.key2) + " ";
        }
        else {
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
     * @param x the data to be searched in the tree
     */
    public boolean contains(E x) {
// TO BE IMPLEMENTED
// placeholder code
        return false;
    }
    /**
     * The data insertion method for the 2-3 Tree
     * @param x the data to be inserted
     */
    public void insert(E x) {
// TO BE IMPLEMENTED
// placeholder code
        return;
    }
}
