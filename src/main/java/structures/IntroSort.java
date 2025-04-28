package structures;

public class IntroSort {

    /**
     * Sorts a portion of an array using the insertion sort algorithm. The method sorts
     * elements in the array from the specified 'begin' index to the 'end' index in ascending order.
     * Insertion sort is efficient for small arrays but has a time complexity of O(n^2) in the worst case.
     * 
     * @param arr the array to be sorted
     * @param begin the starting index of the portion to be sorted (inclusive)
     * @param end the ending index of the portion to be sorted (inclusive)
     * @param <T> the type of elements in the array, which must be Comparable
     */
    private static <T extends Comparable<T>> void insertionSort(T[] arr, int begin, int end) {
        for (int i = begin + 1; i <= end; i++) {
            T value = arr[i];
            int j = i - 1;
            while (j >= begin && arr[j].compareTo(value) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = value;
        }
    }

    /**
     * Swaps the elements at the specified positions in the array. If the indices 'i' and 'j' are different,
     * the elements at those indices are exchanged.
     * 
     * @param arr the array containing the elements to be swapped
     * @param i the index of the first element to be swapped
     * @param j the index of the second element to be swapped
     * @param <T> the type of elements in the array
     */
    private static <T> void swap(T[] arr, int i, int j) {
        if (i != j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    /**
     * Maintains the heap property of a subtree rooted at index 'i' in the array. This method is used in heap sort
     * to ensure that the subtree rooted at the given index satisfies the heap property.
     * 
     * @param arr the array representing the heap
     * @param n the size of the heap (array)
     * @param i the index of the root of the subtree to heapify
     * @param <T> the type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
        int largest = i; 
        int l = 2 * i + 1; 
        int r = 2 * i + 2; 

        if (l < n && arr[l].compareTo(arr[largest]) > 0) largest = l;

        if (r < n && arr[r].compareTo(arr[largest]) > 0) largest = r;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    /**
     * Sorts an array using the HeapSort algorithm, which guarantees an O(n log n) worst-case time complexity. 
     * The algorithm first builds a max heap from the array and then repeatedly extracts the maximum element 
     * from the heap to place it at the end of the array, adjusting the heap each time.
     *
     * @param arr the array to be sorted
     * @param n the size of the array
     * @param <T> the type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapSort(T[] arr, int n) {
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);

        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i); // Move current root to end
            heapify(arr, i, 0); // call max heapify on the reduced heap
        }
    }

    /**
     * Chooses the pivot for a quicksort algorithm as the median of three elements in the array.
     * The three elements are selected at the provided indices (low, mid, and high), 
     * and the method rearranges them so that the median element is at the middle index (b).
     * This is commonly used in the QuickSort algorithm to improve performance by reducing 
     * the likelihood of encountering worst-case behavior.
     *
     * @param arr the array containing the elements to be considered
     * @param a the index of the first element (low)
     * @param b the index of the second element (mid)
     * @param c the index of the third element (high)
     * @param <T> the type of elements in the array, which must be comparable
     * @return the index of the pivot (which is the median element)
     */
    private static <T extends Comparable<T>> int medianOfThree(T[] arr, int a, int b, int c) {
        if (arr[a].compareTo(arr[b]) > 0) swap(arr, a, b);
        if (arr[a].compareTo(arr[c]) > 0) swap(arr, a, c);
        if (arr[b].compareTo(arr[c]) > 0) swap(arr, b, c);
        return b;
    }

    /**
     * Performs a partition operation using the Lomuto partition scheme, which is commonly used in 
     * the QuickSort algorithm. The method chooses the last element of the array as the pivot, 
     * then rearranges the elements in the array such that all elements smaller than or equal to 
     * the pivot come before it, and all elements greater than the pivot come after it.
     * Finally, the pivot is placed in its correct sorted position and the method returns its index.
     *
     * @param arr the array containing the elements to be partitioned
     * @param low the starting index of the section of the array to be partitioned
     * @param high the ending index of the section of the array to be partitioned
     * @param <T> the type of elements in the array, which must be comparable
     * @return the index of the pivot element after partitioning
     */
    private static <T extends Comparable<T>> int partitionLomuto(T[] arr, int low, int high) {
        T pivot = arr[high]; // pivot is always the last element
        int i = low - 1; // place for swapping

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1; // return final pivot position
    }

    /**
     * The core recursive function for performing IntroSort, which is a hybrid sorting algorithm 
     * combining QuickSort, HeapSort, and InsertionSort. IntroSort begins by using QuickSort, but 
     * switches to HeapSort if the recursion depth exceeds a specified limit, or to InsertionSort 
     * for small arrays (typically with fewer than 16 elements).
     * 
     * This method operates by recursively partitioning the array, selecting pivots using the 
     * median-of-three rule, and sorting the partitions. Once the recursion depth exceeds the 
     * predefined depth limit or if the array is small enough, the algorithm switches to more 
     * efficient sorting algorithms for the given scenario.
     *
     * @param arr the array to be sorted
     * @param begin the starting index of the section of the array to be sorted
     * @param end the ending index of the section of the array to be sorted
     * @param depthLimit the maximum recursion depth before switching to HeapSort
     * @param <T> the type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void introsortUtil(T[] arr, int begin, int end, int depthLimit) {
        int size = end - begin + 1;

        // Use insertion sort for small arrays (threshold = 16)
        if (size < 16) {
            insertionSort(arr, begin, end);
            return;
        }

        // If recursion depth exceeds limit, switch to heapsort
        if (depthLimit == 0) {
            heapSort(arr, size);
            return;
        }

        // Otherwise, continue with quicksort
        int pivotIndex = medianOfThree(arr, begin, begin + size / 2, end);
        swap(arr, pivotIndex, end); // move pivot to end
        int partitionPoint = partitionLomuto(arr, begin, end);

        // Recursively sort elements before and after partition
        introsortUtil(arr, begin, partitionPoint - 1, depthLimit - 1);
        introsortUtil(arr, partitionPoint + 1, end, depthLimit - 1);
    }

    /**
     * Public method to initiate the IntroSort algorithm. This method calculates the recursion depth limit 
     * based on the length of the array and then calls the core recursive method, {@link #introsortUtil}, 
     * to perform the sorting.
     * 
     * IntroSort is a hybrid sorting algorithm that begins with QuickSort, switches to HeapSort if the 
     * recursion depth exceeds a predetermined limit, and uses InsertionSort for small arrays. The recursion 
     * depth limit is calculated as 2 * log2(n), where n is the size of the array, to provide a balance 
     * between recursion depth and performance.
     * 
     * @param arr the array to be sorted, which must implement Comparable
     * @param <T> the type of elements in the array, which must be comparable
     */
    public static <T extends Comparable<T>> void introsort(T[] arr) {
        int depthLimit = (int) (2 * Math.floor(Math.log(arr.length) / Math.log(2))); // 2 * log2(n)
        introsortUtil(arr, 0, arr.length - 1, depthLimit);
    }
}
