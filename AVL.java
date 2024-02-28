import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Mythri Muralikannan
 * @version 1.0
 * @userid mmuralikannan3
 * @GTID 903805814
 *
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        for (T item: data) {
            if (item == null) {
                throw new IllegalArgumentException("Data cannot be null.");
            } else {
                add(item);
            }
        }

    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        root = addHelper(root, data);

    }

    /** Private add helper method for recursive calls.
     *
     * @param data the data to add
     * @param currNode node pointer
     * @return pointer reinforcement
     */

    public AVLNode<T> addHelper(AVLNode<T> currNode, T data) {

        if (currNode == null) {
            size++;
            return new AVLNode<>(data);
        }

        if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(addHelper(currNode.getLeft(), data));
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(addHelper(currNode.getRight(), data));
        }

        update(currNode);
        return balanceHelper(currNode);
    }

    /**Private update method to update height and the balance factor.
     *
     * @param currNode the node to update
     */
    private void update(AVLNode<T> currNode) {

        if (currNode != null) {
            int lHeight = (currNode.getLeft() == null) ? -1 : currNode.getLeft().getHeight();
            int rHeight = (currNode.getRight() == null) ? -1 : currNode.getRight().getHeight();

            currNode.setHeight((Math.max(lHeight, rHeight)) + 1);
            currNode.setBalanceFactor(lHeight - rHeight);
        }
    }

    /**Private method that helps with automatically balancing the tree.
     *
     * @param currNode Node to be balanced
     * @return the balanced node
     */
    private AVLNode<T> balanceHelper(AVLNode<T> currNode) {
        int bF = currNode.getBalanceFactor();
        int lBF = (currNode.getLeft() == null) ? 0 : currNode.getLeft().getBalanceFactor();
        int rBF = (currNode.getRight() == null) ? 0 : currNode.getRight().getBalanceFactor();

        if (bF > 1) {    //left heavy
            if (lBF >= 0) {
                currNode = rightRotate(currNode);
            } else {
                currNode.setLeft(leftRotate(currNode.getLeft()));
                currNode = rightRotate(currNode);
            }
        } else if (bF < -1) {   //right heavy
            if (rBF <= 0) {
                currNode = leftRotate(currNode);
            } else {
                currNode.setRight(rightRotate(currNode.getRight()));
                currNode = leftRotate(currNode);
            }
        }

        return currNode;
    }

    /**Private left rotation method
     *
     * @param currNode node to perform left rotate on
     * @return the child node post rotation
     */
    private AVLNode<T> leftRotate(AVLNode<T> currNode) {
        AVLNode<T> child = currNode.getRight();
        currNode.setRight(child.getLeft());
        child.setLeft(currNode);
        update(currNode);
        update(child);
        return child;
    }

    /**Private right rotation method
     *
     * @param currNode node to perform right rotate on
     * @return the child node post rotation
     */
    private AVLNode<T> rightRotate(AVLNode<T> currNode) {
        AVLNode<T> child = currNode.getLeft();
        currNode.setLeft(child.getRight());
        child.setRight(currNode);
        update(currNode);
        update(child);
        return child;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        AVLNode<T> dummy = new AVLNode<>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**Private remove helper method to help with recursive calls.
     *
     * @param currNode node pointer
     * @param data the data to be removed
     * @param dummy blank node to get the node to be removed
     * @return node with the data
     */
    private AVLNode<T> remove(AVLNode<T> currNode, T data, AVLNode<T> dummy) {
        if (currNode == null) {
            throw new NoSuchElementException("Data not in the tree.");
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(remove(currNode.getLeft(), data, dummy));
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(remove(currNode.getRight(), data, dummy));
        } else {
            dummy.setData(currNode.getData());
            size--;

            if (currNode.getRight() == null && currNode.getLeft() == null) {
                return null;
            } else if (currNode.getLeft() != null && currNode.getRight() == null) {
                return currNode.getLeft();
            } else if (currNode.getRight() != null && currNode.getLeft() == null) {
                return currNode.getRight();
            } else {
                AVLNode<T> predecessor = new AVLNode<>(null);
                currNode.setLeft(removePredecessor(currNode.getLeft(), predecessor));
                currNode.setData(predecessor.getData());
            }
        }

        update(currNode);
        return balanceHelper(currNode);

    }

    /** Private helper method to remove Predecessor node.
     *
     * @param currNode node pointer
     * @param dummy blank node to help with changing values
     * @return predecessor node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> currNode, AVLNode<T> dummy) {

        if (currNode.getRight() == null) {
            dummy.setData(currNode.getData());
            return currNode.getLeft();
        } else {
            currNode.setRight(removePredecessor(currNode.getRight(), dummy));
            update(currNode);
            return balanceHelper(currNode);
        }

    }



    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return get(data, root);
    }

    /**Private helper get method.
     *
     * @param data data to get
     * @param currNode node pointer
     * @return the data to get
     */
    private T get(T data, AVLNode<T> currNode) {

        if (currNode == null) {
            throw new NoSuchElementException("Data not in the tree.");
        }

        if (data.equals(currNode.getData())) {
            return currNode.getData();
        } else if (data.compareTo(currNode.getData()) > 0) {
            return get(data, currNode.getRight());
        } else if (data.compareTo(currNode.getData()) < 0) {
            return get(data, currNode.getLeft());
        }

        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return (root == null) ? -1 : root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {

        List<T> deepestBranchList = new ArrayList<>();
        deepestBranchesHelper(root, deepestBranchList);
        return deepestBranchList;

    }

    /**Private helper method to return a list with the deepest branches in preorder.
     *
     * @param currNode current node
     * @param list list with deepest branches in preorder
     */
    private void deepestBranchesHelper(AVLNode<T> currNode, List<T> list) {

        if (currNode == null) {
            return;
        }

        list.add(currNode.getData());
        if (currNode.getLeft() != null && currNode.getBalanceFactor() >= 0) {
            deepestBranchesHelper(currNode.getLeft(), list);
        }

        if (currNode.getRight() != null && currNode.getBalanceFactor() <= 0) {
            deepestBranchesHelper(currNode.getRight(), list);
        }

    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {

        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("The second data item should be greater than the first.");
        }

        List<T> sortedList = new ArrayList<>();

        sortedInBetweenHelper(data1, data2, root, sortedList);


        return sortedList;
    }

    /** Private helper method to get a list in sorted order.
     *
     * @param data1 the smaller data
     * @param data2 the bigger data
     * @param currNode current node
     * @param sortedList sorted list
     */
    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> currNode, List<T> sortedList) {

        if (currNode == null) {
            return;
        }

        if (data1.compareTo(currNode.getData()) < 0) {
            sortedInBetweenHelper(data1, data2, currNode.getLeft(), sortedList);
        }
        if (data1.compareTo(currNode.getData()) < 0 && data2.compareTo(currNode.getData()) > 0) {
            sortedList.add(currNode.getData());
        }
        if (data2.compareTo(currNode.getData()) > 0) {
            sortedInBetweenHelper(data1, data2, currNode.getRight(), sortedList);
        }

    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
