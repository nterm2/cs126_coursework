package structures;

// WPPair represents a key-value pair where the key is of type K and the value is a Number (or subclass of Number)
public class WPPair<K, V extends Number> implements Comparable<WPPair<K, V>> {
    // The key or identifier
    private K id;

    // The value associated with the key (must be a Number type)
    private V value;

    // Constructor: initializes the pair with a key and a value
    public WPPair(K id, V value) {
        this.id = id;
        this.value = value;
    }

    // Compares this WPPair with another based on their numeric value
    // Higher value pairs are considered "smaller" for sorting in descending order
    @Override
    public int compareTo(WPPair<K, V> other) {
        return Double.compare(other.value.doubleValue(), this.value.doubleValue());
    }

    // Returns the value associated with this pair
    public V getValue() {
        return this.value;
    }

    // Returns the key or ID associated with this pair
    public K getID() {
        return this.id;
    }
}
