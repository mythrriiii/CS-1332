import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Mythri Muralikannan
 * @version 1.0
 * @userid mmuralikannan3
 * @GTID 903805814
 *
 * Collaborators: Ria Patel

 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {

        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        size = 0;
        for (T item: data) {
            add(item);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        root = add(data, root);

    }

    /** Private add helper method for recursive calls.
     *
     * @param data the data to add
     * @param currNode node pointer
     * @return pointer reinforcement
     */

    private BSTNode<T> add(T data, BSTNode<T> currNode) {

        BSTNode<T> dataNode = new BSTNode<>(data);

        if (currNode == null) {
            currNode = dataNode;
            size++;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(add(data, currNode.getRight()));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(add(data, currNode.getLeft()));
        }
        return currNode;

    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
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
    private BSTNode<T> remove(BSTNode<T> currNode, T data, BSTNode<T> dummy) {
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
                BSTNode<T> successor = new BSTNode<>(null);
                currNode.setRight(removeSuccessor(currNode.getRight(), successor));
                currNode.setData(successor.getData());
            }
        }
        return currNode;

    }

    /** Private helper method to remove Successor node.
     *
     * @param currNode node pointer
     * @param dummy blank node to help with changing values
     * @return successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> currNode, BSTNode<T> dummy) {

        if (currNode.getLeft() == null) {
            dummy.setData(currNode.getData());
            return currNode.getRight();
        } else {
            currNode.setLeft(removeSuccessor(currNode.getLeft(), dummy));
            return currNode;
        }

    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
    private T get(T data, BSTNode<T> currNode) {

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
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {

        List<T> dataList = new ArrayList<>();
        preorder(dataList, root);
        return dataList;

    }

    /**Recursive preorder helper method.
     *
     * @param list list containing BST elements in preorder order
     * @param node current node
     */
    private void preorder(List<T> list, BSTNode<T> node) {

        if (!(node == null)) {
            list.add(node.getData());
            preorder(list, node.getLeft());
            preorder(list, node.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {

        List<T> dataList = new ArrayList<>();
        inorder(dataList, root);
        return dataList;

    }

    /**Recursive inorder helper method.
     *
     * @param list list containing BST elements in inorder order
     * @param node current node
     */
    private void inorder(List<T> list, BSTNode<T> node) {

        if (!(node == null)) {
            inorder(list, node.getLeft());
            list.add(node.getData());
            inorder(list, node.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {

        List<T> dataList = new ArrayList<>();
        postorder(dataList, root);
        return dataList;

    }

    /**Recursive postorder helper method.
     *
     * @param list list containing BST elements in postorder order
     * @param node current node
     */
    private void postorder(List<T> list, BSTNode<T> node) {

        if (!(node == null)) {
            postorder(list, node.getLeft());
            postorder(list, node.getRight());
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> dataList = new ArrayList<>();
        Queue<BSTNode<T>> dataQueue = new LinkedList<>();

        if (size == 0) {
            return dataList;
        } else {
            dataQueue.add(root);
        }

        while (!(dataQueue.isEmpty())) {
            BSTNode<T> data = dataQueue.remove();
            dataList.add(data.getData());
            if ((data.getLeft() != null)) {
                dataQueue.add(data.getLeft());
            }
            if ((data.getRight() != null)) {
                dataQueue.add(data.getRight());
            }
        }

        return dataList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /** Private height recursive helper method.
     *
     * @param node current node pointer
     * @return the height
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }

        return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {

        if (k < 0) {
            throw new IllegalArgumentException("The number of elements cannot be negative.");
        } else if (k > size) {
            throw new IllegalArgumentException("The number of elements cannot exceed the size of the list.");
        }

        List<T> dataList = new ArrayList<>();
        kLargestHelper(root, dataList, k);

        return dataList;

    }

    /** Recursive kLargest helper method.
     *
     * @param node current node
     * @param nodeList list of largest elements in ascending order
     * @param k required length of the list.
     */
    private void kLargestHelper(BSTNode<T> node, List<T> nodeList, int k) {

        if (node == null) {
            return;
        }

        kLargestHelper(node.getRight(), nodeList, k);
        if (nodeList.size() < k) {
            nodeList.add(0, node.getData());
        }
        if (nodeList.size() < k) {
            kLargestHelper(node.getLeft(), nodeList, k);
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
    public BSTNode<T> getRoot() {
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
