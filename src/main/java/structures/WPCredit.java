package structures;
import stores.*;

public class WPCredit {
    CastCredit[] cast;
    CrewCredit[] crew;
    int id;

    public WPCredit(CastCredit[] cast, CrewCredit[] crew, int id) {
        this.cast = cast;
        castQuickSort(cast, 0, cast.length - 1);
        this.crew = crew; 
        IntroSort.introsort(crew);
        this.id = id;
    }

    /**
     * Returns an array of cast credits for the film.
     * 
     * This method retrieves the list of cast members associated with the film. The cast is currently unsorted, 
     * but sorting based on the 'order' of the cast is planned to be added in the future.
     * 
     * @return An array of {@code CastCredit} objects representing the film's cast.
     */
    public CastCredit[] getFilmCast() {
        return this.cast;
    }

    /**
     * Returns an array of crew credits for the film.
     * 
     * This method retrieves the list of crew members associated with the film. The crew is currently unsorted, 
     * but sorting based on the 'id' of the cast is planned to be added in the future.
     * 
     * @return An array of {@code CrewCredit} objects representing the film's crew.
     */
    public CrewCredit[] getFilmCrew() {
        return this.crew;
    }

    /**
     * Returns the movie ID associated with the film.
     * 
     * This method retrieves the unique identifier of the movie.
     * 
     * @return The ID of the movie.
     */
    public int getMovieID() {
        return this.id;
    }

    /**
     * Returns the number of cast members associated with the film.
     * 
     * This method calculates and returns the size of the cast array, representing how many cast members are associated 
     * with the film.
     * 
     * @return The number of cast members in the film.
     */
    public int getCastSize() {
        return this.cast.length;
    }

    /**
     * Returns the number of crew members associated with the film.
     * 
     * This method calculates and returns the size of the crew array, representing how many crew members are associated 
     * with the film.
     * 
     * @return The number of crew members in the film.
     */
    public int getCrewSize() {
        return this.crew.length;
    }

    /**
     * Sorts an array of {@code CastCredit} objects using the QuickSort algorithm.
     * 
     * This method recursively partitions the array and sorts the segments, ensuring that 
     * the cast members are arranged in a specific order based on their 'order' property.
     * 
     * @param arr The array of {@code CastCredit} objects to be sorted.
     * @param low The starting index of the segment to be sorted.
     * @param high The ending index of the segment to be sorted.
     */
    private void castQuickSort(CastCredit[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = castMedianOfThreePartition(arr, low, high);
            castQuickSort(arr, low, pivotIndex - 1);
            castQuickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array of {@code CastCredit} objects using the median-of-three strategy.
     * 
     * This method selects a pivot element based on the median value of the first, middle, 
     * and last elements of the array segment. It then partitions the array around the pivot.
     * 
     * @param arr The array of {@code CastCredit} objects to be partitioned.
     * @param low The starting index of the segment to be partitioned.
     * @param high The ending index of the segment to be partitioned.
     * @return The index of the pivot after partitioning.
     */
    private int castMedianOfThreePartition(CastCredit[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        int lowOrder = arr[low].getOrder();
        int midOrder = arr[mid].getOrder();
        int highOrder = arr[high].getOrder();

        // Find the median index among low, mid, high
        int medianIndex;
        if ((lowOrder <= midOrder && midOrder <= highOrder) || (highOrder <= midOrder && midOrder <= lowOrder)) {
            medianIndex = mid;
        } else if ((midOrder <= lowOrder && lowOrder <= highOrder) || (highOrder <= lowOrder && lowOrder <= midOrder)) {
            medianIndex = low;
        } else {
            medianIndex = high;
        }

        // Move median to the end to use as pivot
        castSwap(arr, medianIndex, high);
        return castPartition(arr, low, high);
    }

    /**
     * Partitions the array of {@code CastCredit} objects around a pivot.
     * 
     * This method rearranges the array elements so that all elements less than or equal to
     * the pivot are placed before it, and all elements greater than the pivot are placed after it.
     * 
     * @param arr The array of {@code CastCredit} objects to be partitioned.
     * @param low The starting index of the segment to be partitioned.
     * @param high The ending index of the segment to be partitioned.
     * @return The index of the pivot after partitioning.
     */
    private int castPartition(CastCredit[] arr, int low, int high) {
        int pivot = arr[high].getOrder();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getOrder() <= pivot) {
                i++;
                castSwap(arr, i, j);
            }
        }

        castSwap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in an array of {@code CastCredit} objects.
     * 
     * This method exchanges the positions of two elements in the array.
     * 
     * @param arr The array of {@code CastCredit} objects where the swap will occur.
     * @param i The index of the first element to be swapped.
     * @param j The index of the second element to be swapped.
     */
    private void castSwap(CastCredit[] arr, int i, int j) {
        CastCredit temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
