/**
 * The class representation for 2-node and 3-node of a 2-3 Tree
 * @author Srini Badri
 */
public class TwoThreeNode<E> {
    /*
     * the first key
     */
    E key1;
    /*
     * the second key, non-null only if the node is a 3-node
     */
    E key2;
    /*
     * the left child
     */
    TwoThreeNode<E> lc;
    /*
     * the right child of 2-node, and the middle child of 3-node
     */
    TwoThreeNode<E> mc;
    /*
     * the right child, valid only if the node is a 3-node
     */
    TwoThreeNode<E> rc;
    /*
    * boolean indicating whether the node is a leaf - true if the node is leaf,
    false otherwise
    */
    boolean leaf;
    /**
     * The constructor of the node as a 2-node
     * @param k the key of the 2-node
     * @param lc the left child of the 2-node
     * @param mc the right child of the 2-node
     * @param leaf boolean indicating whether the 2-node is a leaf
     */
    public TwoThreeNode(E k, TwoThreeNode<E> lc, TwoThreeNode<E> mc, boolean leaf)
    {
        key1 = k;
        key2 = null;
        this.lc = lc;
        this.mc = mc;
        this.rc = null;
        this.leaf = leaf;
    }
    /**
     * The constructor of the node as a 3-node
     * @param k1 the first key of the 3-node
     * @param k2 the second key of the 3-node
     * @param lc the left child of the 3-node
     * @param mc the middle child of the 3-node
     * @param rc the right child of the 3-node
     * @param leaf boolean indicating whether the node is a leaf
     */
    public TwoThreeNode(E k1, E k2, TwoThreeNode<E> lc, TwoThreeNode<E> mc,
                        TwoThreeNode<E> rc, boolean leaf) {
        key1 = k1;
        key2 = k2;
        this.lc = lc;
        this.mc = mc;
        this.rc = rc;
        this.leaf = leaf;
    }
}
