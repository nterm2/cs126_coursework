package structures;

/**
 * Implement a hashmap from scratch, using chaining for collision resolution 
 * Used for now as it is easier to understand right now, but may switch to a 
 * more efficent solution (but complex solution), such as double hashing . 
 * quadratic probing. 
 */

public class WPHashMap<K, V> implements IWPHashMap<K, V>{
    private Entry<K, V>[] table; // List of entries representing a table
    private int capacity = 10; // Initial number of buckets

    // A bucket in the table stores a linked list of entries. Therefore, each 
    // entry stores the key, value, and then a pointer to the next entry in the 
    // linked list.
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next; 

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // Constructor initialises the table to be an empty array of entries
    @SuppressWarnings("unchecked")
    public WPHashMap() {
        table = new Entry[capacity];
    }

    // Method to put a AN ENTRY INSTO THE MAP M. if the key k is 
    // not already in m, then return null, else, return old value 
    // associated with k. 
    public void put(K newKey, V data) {
        if (newKey == null)
            return;    //does not allow to store null.

        //calculate hash of key.
        int hash = hash(newKey);
        //create new entry.
        Entry<K, V> newEntry = new Entry<K, V>(newKey, data, null);

        //if table location does not contain any entry, store entry there.
        if (table[hash] == null) {
            table[hash] = newEntry;
        } else {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];

            while (current != null) { //we have reached last entry of bucket.
                if (current.key.equals(newKey)) {
                    if (previous == null) {  //node has to be insert on first of bucket.
                        newEntry.next = current.next;
                        table[hash] = newEntry;
                        return;
                    } else {
                        newEntry.next = current.next;
                        previous.next = newEntry;
                        return;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }
    /**
     * Method returns value corresponding to key.
     *
     * @param key
     */
    public V get(K key) {
        int hash = hash(key);
        if (table[hash] == null) {
            return null;
        } else {
            Entry<K, V> temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key))
                    return temp.value;
                temp = temp.next; //return value corresponding to key.
            }
            return null;   //returns null if key is not found.
        }
    }


    /**
     * Method removes key-value pair from HashMapCustom.
     *
     * @param key
     */
    public boolean remove(K deleteKey) {

        int hash = hash(deleteKey);

        if (table[hash] == null) {
            return false;
        } else {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];

            while (current != null) { //we have reached last entry node of bucket.
                if (current.key.equals(deleteKey)) {
                    if (previous == null) {  //delete first entry node.
                        table[hash] = table[hash].next;
                        return true;
                    } else {
                        previous.next = current.next;
                        return true;
                    }
                }
                previous = current;
                current = current.next;
            }
            return false;
        }

    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

}
