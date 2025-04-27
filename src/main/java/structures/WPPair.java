package structures;

public class WPPair<K, V extends Number> implements Comparable<WPPair<K,V>>{
    K id;
    V value; 

    public WPPair(K id, V value) {
        this.id = id; 
        this.value = value;
    }

    @Override
    public int compareTo(WPPair<K, V> other) {
        return Double.compare(this.value.doubleValue(), other.value.doubleValue());
    }

    public V getValue() {
        return this.value;
    }

    public K getID() {
        return this.id;
    }

}
