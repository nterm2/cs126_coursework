package structures;

public interface IWPList<E> {
    public boolean add(E element);

    public E get(int index); 

    public int indexOf(E element);

    public E set(int index, E element);

    public boolean remove(E element);

    public int size();

    public boolean contains(E element);

    public Object[] getInternalArray();
}
