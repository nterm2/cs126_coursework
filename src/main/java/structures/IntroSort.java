package structures;

public class IntroSort {

    // Simple insertion sort for small arrays
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

    // Swaps two elements in an array
    private static <T> void swap(T[] arr, int i, int j) {
        if (i != j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // Heapify a subtree rooted at index i (used by heap sort)
    private static <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left child
        int r = 2 * i + 2; // right child

        // If left child is larger than root
        if (l < n && arr[l].compareTo(arr[largest]) > 0) largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r].compareTo(arr[largest]) > 0) largest = r;

        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // HeapSort algorithm to guarantee O(n log n) worst-case
    private static <T extends Comparable<T>> void heapSort(T[] arr, int n) {
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);

        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i); // Move current root to end
            heapify(arr, i, 0); // call max heapify on the reduced heap
        }
    }

    // Choose pivot as the median of three elements (low, mid, high)
    private static <T extends Comparable<T>> int medianOfThree(T[] arr, int a, int b, int c) {
        if (arr[a].compareTo(arr[b]) > 0) swap(arr, a, b);
        if (arr[a].compareTo(arr[c]) > 0) swap(arr, a, c);
        if (arr[b].compareTo(arr[c]) > 0) swap(arr, b, c);
        return b;
    }

    // Lomuto partition scheme (used for QuickSort phase)
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

    // The core recursive function for IntroSort
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

    // Public method to start introsort
    public static <T extends Comparable<T>> void introsort(T[] arr) {
        int depthLimit = (int) (2 * Math.floor(Math.log(arr.length) / Math.log(2))); // 2 * log2(n)
        introsortUtil(arr, 0, arr.length - 1, depthLimit);
    }
}
