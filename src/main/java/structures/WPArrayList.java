package structures;

public class WPArrayList<E>{
    Object[] internalArray;
    int size;
    int capacity;

    public WPArrayList() {
        this.size = 0;
        this.capacity = 100;
        this.internalArray = new Object[this.capacity];
    }

    public Object[] getInternalArray() {
        return this.internalArray;
    }

    /**
     * Adds an element to the internal array.
     * This method first checks whether there is enough space in the current internal array to accommodate the new element.
     * If there is insufficient space, the array is resized by doubling its capacity. The elements from the original array
     * are then copied to the new array, and the reference to the internal array is updated accordingly.
     * Once there is sufficient space (either by default or after resizing), the new element is added to the array, 
     * and the size of the array is incremented by 1.
     * 
     * Consideration: If the new capacity exceeds system limits or fails during resizing,
     * the operation may throw an OOM error. Ensure that the system has adequate memory when handling 
     * extremely large arrays.
     *
     * @param element The element to be added to the array
     * @return true if the element was successfully added; false otherwise
     */
    public boolean add(E element) {
        if (this.size >= this.capacity) {
            this.capacity *= 2;
            Object[] newInternalArray = new Object[this.capacity];
            System.arraycopy(this.internalArray, 0, newInternalArray, 0, this.size);
            this.internalArray = newInternalArray;
        }
        this.internalArray[this.size++] = element;
        return true;
    }
    
    /**
     * Returns the element at the specified index in the array.
     * If the index is out of bounds, the method will throw an error.
     * Since the internal array is an array of objects, the individual element is treated as an object.
     * The element is then cast to the generic type E to be returned in the correct format,
     * rather than as a generic object.
     * Note that this is not a checked cast, but the warning is suppressed to avoid compilation warnings.
     *
     * @param i The index of the element to retrieve
     * @return The element at the specified index, cast to type {@code E}
     */
    @SuppressWarnings("unchecked")
    public E get(int i) {
        return (E) this.internalArray[i];
    }

    /**
     * Iterates through each element in the internal array to find the index of the specified element.
     * If the element is found, the index of the element is returned. If the element is not found, 
     * a value of -1 is returned to indicate the element is not present.
     *
     * @param element The element to search for in the internal array
     * @return The index of the specified element, or -1 if the element is not found
     */
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (element.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Attempts to replace an existing element at the specified index with a new element.
     * The method first checks that the provided index is less than the current size of the list.
     * If the index is out of bounds, an error is raised. If the index is valid, the current element 
     * at that index is retrieved and replaced with the new element. The method then returns the 
     * element that was replaced.
     *
     * @param i The index of the element to replace
     * @param element The new element to set at the specified index
     * @return The element that was previously stored at the specified index
     * @throws ArrayIndexOutOfBoundsException If the provided index is greater than or equal to the current size of the list
     */
    public E set(int i, E element) {
        if (i >= this.size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
        E replaced = this.get(i); 
        this.internalArray[i] = element;
        return replaced;
    }

    /**
     * Removes the first occurrence of the specified element from the list, if it exists.
     * The method first attempts to find the index of the element using the indexOf method.
     * If the element is not found, the method returns false, indicating that the element could not be removed.
     * If the element is found, the elements following it are shifted one position to the left to fill the gap left
     * by the removed element. The last element is then set to null, and the size of the list is decreased by one.
     *
     * @param element The element to remove from the list
     * @return true if the element was successfully removed, false if the element was not found
     */
    public boolean remove(E element) {
        int toRemoveIndex = this.indexOf(element);
        if (toRemoveIndex >= 0) {
            int numMoved = size - toRemoveIndex - 1;
            if (numMoved > 0) {
                System.arraycopy(internalArray, toRemoveIndex + 1, internalArray, toRemoveIndex, numMoved);
            }
            internalArray[--size] = null;
            return true;
        }
        return false;
    }

    /**
     * Checks if the specified element exists in the list.
     * The method iterates through the internal array and checks for equality with each element.
     * If the element is found, it returns true. If the element is not found by the end of the list, 
     * it returns false.
     *
     * @param element The element to check for presence in the list
     * @return true if the element is found in the list, false otherwise
     */
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(this.internalArray[i])) {return true;}
        }
        return false;
    }

    /**
     * Returns the current number of elements in the list.
     * This method provides the size of the list, reflecting the number of elements
     * that have been added to the list. It does not account for any unused space 
     * in the underlying array.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return this.size;
    }
}