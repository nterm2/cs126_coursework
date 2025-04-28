package structures;

/**
 * WPPair represents a key-value pair where the key is of type {@code K} and the value is a {@code Number} 
 * (or subclass of {@code Number}).
 */
public class WPPair<K, V extends Number> implements Comparable<WPPair<K, V>> {
    // The key or identifier
    private K id;

    // The value associated with the key (must be a Number type)
    private V value;

    /**
     * Constructor: Initializes the pair with a key and a value.
     * 
     * This constructor creates a new key-value pair with the specified key and value.
     * 
     * @param id The key or identifier of the pair.
     * @param value The value associated with the key, must be a {@code Number}.
     */
    public WPPair(K id, V value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Compares this {@code WPPair} with another based on their numeric value.
     * 
     * Higher value pairs are considered "smaller" for sorting in descending order.
     * 
     * @param other The other {@code WPPair} to compare to.
     * @return A negative integer, zero, or a positive integer as this pair's value is less than, equal to, or greater than the specified pair's value.
     */
    @Override
    public int compareTo(WPPair<K, V> other) {
        return Double.compare(other.value.doubleValue(), this.value.doubleValue());
    }

    /**
     * Returns the value associated with this pair.
     * 
     * This method retrieves the value of the pair, which is a {@code Number}.
     * 
     * @return The value associated with this pair.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Returns the key or ID associated with this pair.
     * 
     * This method retrieves the key or identifier associated with this pair.
     * 
     * @return The key or ID of the pair.
     */
    public K getID() {
        return this.id;
    }
}

