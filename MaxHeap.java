import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Mythri Muralikannan
 * @version 1.0
 * @userid mmuralikannan3
 * @GTID 903805814
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {

        if (data == null || data.size() == 0) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        backingArray = (T[]) new Comparable[(data.size() * 2) + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data can't be null.");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }
        for (int i = size / 2; i >= 1; i--) {
            downHeaping(i);
        }

    }

    /**
     * Helper method to help with down heaping process.
     *
     * @param index index of the value of parent for the iteration
     */
    private void downHeaping(int index) {
        while ((2 * index) <= size) {
            int childIndex = 2 * index;
            int rightIndex = childIndex + 1;

            if ((rightIndex <= size) && (backingArray[childIndex].compareTo(backingArray[rightIndex]) < 0)) {
                childIndex = rightIndex;
            }

            if (backingArray[index].compareTo(backingArray[childIndex]) > 0) {
                break;
            }

            T temp = backingArray[index];
            backingArray[index] = backingArray[childIndex];
            backingArray[childIndex] = temp;

            index = childIndex;
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        if ((size + 1) == backingArray.length) {
            T[] oldArray = backingArray;
            backingArray = (T[]) new Comparable[oldArray.length * 2];
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i] = oldArray[i];
            }
        }

        backingArray[size + 1] = data;
        int currentIndex = size + 1;

        while (currentIndex > 1 && backingArray[currentIndex / 2].compareTo(backingArray[currentIndex]) < 0) {

            T temp = backingArray[currentIndex];
            backingArray[currentIndex] = backingArray[currentIndex / 2];
            backingArray[currentIndex / 2] = temp;
            currentIndex = currentIndex / 2;
        }

        size++;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {

        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T returnData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        downHeaping(1);

        return returnData;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {

        if (size == 0) {
            throw new NoSuchElementException("The Heap is empty.");
        }

        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);

    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
