import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int maxValueIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[maxValueIndex]) > 0) {
                    maxValueIndex = j;
                }
            }

            // swap max value of sub array with th
            T temp = arr[i];
            arr[i] = arr[maxValueIndex];
            arr[maxValueIndex] = temp;

        }

    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;

        while (swapsMade) {
            swapsMade = false;
            int innerLoopEnd = endIndex;
            for (int i = startIndex; i < innerLoopEnd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;

                    swapsMade = true;
                    endIndex = i;
                }
            }

            if (swapsMade) {
                swapsMade = false;
                int innerLoopStart = startIndex;
                for (int i = endIndex; i > innerLoopStart; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;

                        swapsMade = true;
                        startIndex = i;
                    }
                }
            }

        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        arr = mergeHelper(arr, comparator);

    }

    /**Recursive Helper method for Merge Sort.
     *
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @return sorted array
     * @param <T> data type to sort
     */
    private static <T> T[] mergeHelper(T[] arr, Comparator<T> comparator) {

        if (arr.length == 1) {
            return arr;
        }

        int length = arr.length;
        int middleIndex = length / 2;

        T[] leftArray = copyRange(arr, 0, middleIndex - 1);
        T[] rightArray = copyRange(arr, middleIndex, length - 1);

        mergeHelper(leftArray, comparator);
        mergeHelper(rightArray, comparator);

        int i = 0;
        int j = 0;

        while ((i != leftArray.length) && (j != rightArray.length)) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[i + j] = leftArray[i];
                i++;
            } else {
                arr[i + j] = rightArray[j];
                j++;
            }
        }

        while (i < leftArray.length) {
            arr[i + j] = leftArray[i];
            i++;
        }

        while (j < rightArray.length) {
            arr[i + j] = rightArray[j];
            j++;
        }

        return arr;

    }

    /** Helper method to copy the range from to to.
     * @param <T> data type to sort
     * @param original the array from which a range is to be copied
     * @param from the initial index of the range to be copied
     * @param to the final index of the range to be copied
     * @return subarray with inputted range
     */
    private static <T> T[] copyRange(T[] original, int from, int to) {
        T[] returnArr = (T[]) new Object[to - from + 1];
        for (int i = 0; i < returnArr.length; i++) {
            returnArr[i] = original[from + i];
        }

        return returnArr;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {

        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        if (!(k >= 1 && k <= arr.length)) {
            throw new IllegalArgumentException("K is not in range.");
        }

        return kthSelectHelper(k, arr, comparator, rand, 0, arr.length - 1);
    }

    /** Kth Select helper method which implement quick sort.
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      the start index to be copied
     * @param end        the end index to be copied
     * @return           the kth element
     */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator,
                                  Random rand, int start, int end) {

        if ((end - start) < 1) {
            return arr[end];
        }

        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];

        arr[pivotIndex] = arr[start];
        arr[start] = pivotValue;

        int i = start + 1;
        int j = end;

        while (i <= j) {

            while ((i <= j) && (comparator.compare(arr[i], pivotValue) <= 0)) {
                i++;
            }

            while ((i <= j) && (comparator.compare(arr[j], pivotValue) >= 0)) {
                j--;
            }

            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

        }

        arr[start] = arr[j];
        arr[j] = pivotValue;

        if (j == (k - 1)) {
            return arr[j];
        }

        if (j > (k - 1)) {
            return kthSelectHelper(k, arr, comparator, rand, start, j - 1);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, j + 1, end);
        }


    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        LinkedList[] buckets = new LinkedList[19];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        boolean continueSort = true;
        int mod = 10;
        int prevTenthPlace = 1;

        while (continueSort) {

            continueSort = false;

            for (int i: arr) {

                if ((i / (prevTenthPlace * 10)) != 0) {
                    continueSort = true;
                }

                int digit = (i / prevTenthPlace) % mod;
                buckets[digit + 9].add(i);

            }

            int index = 0;

            for (LinkedList<Integer> bucket: buckets) {
                if (bucket != null) {
                    while (bucket.size() != 0) {
                        arr[index] = bucket.poll();
                        index++;
                    }
                }
            }

            prevTenthPlace *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(java.util.List<Integer> data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        java.util.PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
        int[] returnArray = new int[data.size()];

        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = minHeap.remove();
        }

        return returnArray;

    }
}
