package structures;

public class IntroSort {

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

    private static <T> void swap(T[] arr, int i, int j) {
        if (i != j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

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

    private static <T extends Comparable<T>> void heapSort(T[] arr, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static <T extends Comparable<T>> int medianOfThree(T[] arr, int a, int b, int c) {
        if (arr[a].compareTo(arr[b]) > 0) swap(arr, a, b);
        if (arr[a].compareTo(arr[c]) > 0) swap(arr, a, c);
        if (arr[b].compareTo(arr[c]) > 0) swap(arr, b, c);
        return b;
    }

    private static <T extends Comparable<T>> int partitionLomuto(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T extends Comparable<T>> void introsortUtil(T[] arr, int begin, int end, int depthLimit) {
        int size = end - begin + 1;

        if (size < 16) {
            insertionSort(arr, begin, end);
            return;
        }

        if (depthLimit == 0) {
            heapSort(arr, size);
            return;
        }

        int pivotIndex = medianOfThree(arr, begin, begin + size / 2, end);
        swap(arr, pivotIndex, end);
        int partitionPoint = partitionLomuto(arr, begin, end);

        introsortUtil(arr, begin, partitionPoint - 1, depthLimit - 1);
        introsortUtil(arr, partitionPoint + 1, end, depthLimit - 1);
    }

    public static <T extends Comparable<T>> void introsort(T[] arr) {
        int depthLimit = (int) (2 * Math.floor(Math.log(arr.length) / Math.log(2)));
        introsortUtil(arr, 0, arr.length - 1, depthLimit);
    }
}
