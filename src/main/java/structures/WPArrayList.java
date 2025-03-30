package structures;

/**
 * get an eleement of the array
 * get the index of an array 
 * remove an element of an array 
 * set an eleemnt of an array 
 * We will need to store internal representation of the array (Array of objects)
 * The current size of the array list
 * and the capacity of the arraylist
 * 
 * Improvements: when internalarray reaches full capacity, doubles in size in size
 * can lead to memory issues. check if arrau is growing infitely
 */
public class WPArrayList<E> implements IWPList<E>{
    Object[] internalArray;
    int size;
    int capacity;

    public WPArrayList() {
        this.size = 0;
        this.capacity = 100;
        this.internalArray = new Object[this.capacity];
    }

    /**
     * First check that we have enough space in our internalArray to add an element. IN the case that we don't 
     * , create a new internalArray that is double the size of the old array. then copy all the elements in the 
     * filled up initialArray to new initial array, at which we set initialarray to new initialarray. In the case we 
     * do/don't, at the end add the element we wish to add to internalArray, and incremeent the size by 1`
     * 
     * CONSIDERATION - WHAT HAPPENS IF CAPACITY IS TOO LARGE AND FAILS? 
     */
    public boolean add(E element) {
        try {
            if (this.size >= this.capacity) {
                this.capacity *= 2;
                Object[] newInternalArray = new Object[this.capacity];
                for (int i=0; i < this.size; i++) {
                    newInternalArray[i] = this.internalArray[i];
                }
                this.internalArray = newInternalArray;
            }
            this.internalArray[this.size] = element;
            this.size++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    /**
     * return accessing the index specified by the array. in the case that the index
     * is out of bounds, the internal array will return an error. As internalarray 
     * is an array of objects, an individual element is an object, thus we cast the object
     * to E in order to be returned in the correct format, as opposed to being returned as 
     * an object. Not a checked cast, but suprpres warnings to not have warnings.
     */
    @SuppressWarnings("unchecked")
    public E get(int i) {
        return (E) this.internalArray[i];
    }

    /**
     * iterate through each eleemnt in the internal arrray. in case we find the 
     * elemnt we want. return the index. otherwise, throw an exception.
     */
    public int indexOf(E element) {
        for (int i=0; i < this.size; i++) {
            if (element.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
      attempts to replace an existing element within the arraylist with another element
      as such, check that the index provided is less than the size (or number of elements
      currently stored in the arraylit) - otherwise, an error is raised. Otherwise, 
      we get the element currently stored in the index we are trying to set a new value of,
      and then set that index to the element. return the replaced element back.
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
     * Need to implement indexOf, get, set 
     * Removes the first occurance of an element within the arraylist, if it exists. First attempts to get the index 
     * of thefirst occurance elemnt. In the case that it doesn't exist, we shouldn't be able to remove the element and an error is raised 
     * should be raised. Otherwise, we need to iterate from the index of the element we want to remove + 1 to the end of the
     * list, and set the elemnt to i - 1 (taking up empty space from the removed element.)
     * as such, the last lemnt willl not be null - set it to null, and decrase the size
     */
    public boolean remove(E element) {
        int toRemoveIndex = this.indexOf(element);
        if (toRemoveIndex >= 0) {
            for (int i=toRemoveIndex+1; i<this.size; i++) {
                this.set(i-1, this.get(i));
            }
            this.internalArray[size-1] = null;
            this.size--;
            return true;
        }
        return false;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(this.internalArray[i])) {return true;}
        }
        return false;
    }

    public int size() {
        return this.size;
    }
}
