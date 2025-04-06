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
        this.id = id;
    }

    // todo - add sorting based on 'order' of cast
    // something like, for each cast
    public CastCredit[] getFilmCast() {
        return this.cast;
    }

    // todo - add sorting based on 'id' of cast
    public CrewCredit[] getFilmCrew() {
        return this.crew;
    }

    public int getMovieID() {
        return this.id;
    }

    public int getCastSize() {
        return this.cast.length;
    }

    public int getCrewSize() {
        return this.crew.length;
    }
    // Methods for QuickSort 

    private void castQuickSort(CastCredit[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = castMedianOfThreePartition(arr, low, high);
            castQuickSort(arr, low, pivotIndex - 1);
            castQuickSort(arr, pivotIndex + 1, high);
        }
    }

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

    private void castSwap(CastCredit[] arr, int i, int j) {
        CastCredit temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
