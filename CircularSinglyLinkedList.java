import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Mythri Muralikannan
 * @version 1.0
 * @userid mmuralikannan3
 * @GTID 903805814
 *
 * Collaborators: Ria Patel
 *
 * Resources: None
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {

        if (index < 0) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be negative.");
        } else if (index > size) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be more than the size of the ArrayList.");
        }

        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot be null.");
        }

        if (head == null) {
            head = new CircularSinglyLinkedListNode(data);
            head.setNext(head);
        } else if ((index == 0) || (index == size)) {
            CircularSinglyLinkedListNode copyNode = new CircularSinglyLinkedListNode(head.getData());
            copyNode.setNext(head.getNext());
            head.setData(data);
            head.setNext(copyNode);

            if (index == size) {
                head = head.getNext();
            }

        } else {
            CircularSinglyLinkedListNode newNode = new CircularSinglyLinkedListNode(data);
            CircularSinglyLinkedListNode current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }

        size++; //increase size

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0,data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size,data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be negative.");
        } else if (index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be more than the size of the ArrayList.");
        }

        CircularSinglyLinkedListNode removeNode;

        if (size == 1) {
            removeNode = head;
            head = null;
        } else if (index == 0) {
            if (size == 2) {
                T data = head.getData();
                head.setData(head.getNext().getData());
                head.setNext(head);
                size--;
                return data;
            } else {
                T data = head.getData();
                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());
                size--;
                return data;
            }
        } else if (index == size - 1) {
            CircularSinglyLinkedListNode current = head;
            while (current.getNext().getNext() != head) {
                current = current.getNext();
            }
            removeNode = current.getNext();
            current.setNext(head);
        } else {
            CircularSinglyLinkedListNode current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            removeNode = current.getNext();
            current.setNext(removeNode.getNext());
        }

        size--;
        return (T)removeNode.getData();

    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {

        if (index < 0) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be negative.");
        } else if (index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index cannot be more than the size of the ArrayList.");
        }

        CircularSinglyLinkedListNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return (T) current.getData();

    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }

        int lastIndex = -1;
        CircularSinglyLinkedListNode current = head;
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(data)) {
                lastIndex = i;
            }
            current = current.getNext();
        }

        if (lastIndex != -1) {
            return removeAtIndex(lastIndex);
        } else {
            throw new NoSuchElementException("The data is not in the list.");
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] returnArray = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            returnArray[i] = current.getData();
            current = current.getNext();
        }

        return returnArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
