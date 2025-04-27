package structures;

// My implementation of an arraylist
public class WPArrayList<E> {
    private Object[] internalArray;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 1000;

    public WPArrayList() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.internalArray = new Object[this.capacity];
    }

    // Before adding, check that the capacity of the internal array is large enough to account for the element that we are 
    // going to add using ensureCapacity. Then increase insert the element to be added into the internal array. Has an 
    // amortized time complexity of O(1) (expensive only when resizing)
    public boolean add(E element) {
        ensureCapacity();
        this.internalArray[this.size++] = element;
        return true;
    }

    // In the case that the supposed size of the array is greater than the capacity, 
    // if the capacity of array is less than 1000, then we double the capacity of the array, 
    // otherwise we increase the capacity of the internal array by the current capacity / 2.
    // This helps regulate memory usage, as past the 1000 element threshold, we don't increase array capacity as 
    // aggressively, leading to less memory being used. 
    private void ensureCapacity() {
        if (this.size >= this.capacity) {
            if (this.capacity < 1000) {
                this.capacity *= 2;
            } else {
                this.capacity += this.capacity / 2;
            }
            Object[] newInternalArray = new Object[this.capacity];
            System.arraycopy(this.internalArray, 0, newInternalArray, 0, this.size);
            this.internalArray = newInternalArray;
        }
    }

    // First check that an elment at the provided index exists - if not, throw an error. Otherwise, return the 
    // element at the provided index. Has time complexity of O(1)
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) this.internalArray[index];
    }

    // Get the index of a particular element. Need to iterate through each element in the 
    // arraylist. In the case that the element isn't present in the 
    // arraylist, return -1. Otherwise return the position at which the element exists in the 
    // arraylist. 0(n) time complexity. 
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (element == null) {
                if (this.internalArray[i] == null) return i;
            } else {
                if (element.equals(this.internalArray[i])) return i;
            }
        }
        return -1;
    }

    // Set an element in a given index within the arraylist. first check that the index is valid. 
    // In the case that it is, retrieve the current element and temporarily store it, then set the index to 
    // store the new element, and return the previously stored elemennt. Has a time complexity of O(1).
    public E set(int index, E element) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E replaced = (E) this.internalArray[index];
        this.internalArray[index] = element;
        return replaced;
    }

    // Remove an element from an ArrayList. Retrieve the index of the element we wish to remove O(n). In the case that 
    // the element exists in the arraylist, remove the given element, and if possible, decrease the size of the arraylist.
    public boolean remove(E element) {
        int toRemoveIndex = indexOf(element);
        if (toRemoveIndex >= 0) {
            int numMoved = this.size - toRemoveIndex - 1;
            if (numMoved > 0) {
                System.arraycopy(this.internalArray, toRemoveIndex + 1, this.internalArray, toRemoveIndex, numMoved);
            }
            this.internalArray[--size] = null;
            if (size < capacity / 4 && capacity > DEFAULT_CAPACITY * 2) {
                shrink();
            }
            return true;
        }
        return false;
    }

    // Method used to decrease the size of the arraylist. Set the new capacity to be the biggest between the original capacity of 
    // the internal array used by the arraylist and half of the current capacity of the arraylist. Uses System.arraycopy to move existing contents 
    // of internal array to smaller internal array. This ensures that we are not using an unessecary amount of memory. 
    private void shrink() {
        int newCapacity = Math.max(DEFAULT_CAPACITY, this.capacity / 2);
        Object[] newInternalArray = new Object[newCapacity];
        System.arraycopy(this.internalArray, 0, newInternalArray, 0, this.size);
        this.internalArray = newInternalArray;
        this.capacity = newCapacity;
    }

    // If the index is invalid (greater than the size or less than 0), we throw an ArrayIndxOutOfBounds exception.
    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    // Check if the arraylist contains the given element. This method relies on the indexOf method (which returns -1 if the the index of the element
    // doesn't exist), thus has a time complexity of O(n).
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    // Returns the size of the arraylist. 0(1) operation.
    public int size() {
        return this.size;
    }

    // Returns the capacity of the arraylist. 0(1) operation.
    public int capacity() {
        return this.capacity;
    }
}
