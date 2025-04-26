package structures;

public class WPArrayList<E> {
    private Object[] internalArray;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    public WPArrayList() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.internalArray = new Object[this.capacity];
    }

    public boolean add(E element) {
        ensureCapacity();
        this.internalArray[this.size++] = element;
        return true;
    }
    // In the case that the size of the internal 
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

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) this.internalArray[index];
    }

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

    public E set(int index, E element) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E replaced = (E) this.internalArray[index];
        this.internalArray[index] = element;
        return replaced;
    }

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

    private void shrink() {
        int newCapacity = Math.max(DEFAULT_CAPACITY, this.capacity / 2);
        Object[] newInternalArray = new Object[newCapacity];
        System.arraycopy(this.internalArray, 0, newInternalArray, 0, this.size);
        this.internalArray = newInternalArray;
        this.capacity = newCapacity;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }
}
