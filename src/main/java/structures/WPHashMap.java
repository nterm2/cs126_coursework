package structures;

public class WPHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 1024;
    private static final int INCREASE_FACTOR = 2;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private EntryNode<K,V>[] buckets;
    private int capacity;

    // Initialise buckets to contain the default number of entries
    @SuppressWarnings("unchecked")
    public WPHashMap() {
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
        this.buckets = new EntryNode[capacity];
    }

    // Entrynode represents element in linkedlist, representing an entry.
    // Store key, value, and pointer to next entry within the bucket. If at
    // the end of the linked list, set next to null.
    private static class EntryNode<K, V> {
        private K key;
        private V value;
        private EntryNode<K, V> next;

        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Calculates the bucket index for a given key.
     * 
     * This method computes the bucket index by applying the hash code of the key and performing a bitwise AND with the 
     * capacity minus one. This helps in determining the correct bucket in the hash table where the key-value pair should be stored.
     * The method assumes that the capacity of the hash table is a power of two, ensuring efficient bucket placement.
     * 
     * @param key The key for which the bucket index is calculated.
     * @return The index of the bucket where the key-value pair should be stored.
     */
    private int getBucketIndex(K key) {
        return key.hashCode() & (capacity - 1);
    }

    /**
     * Adds a key-value pair to the dictionary.
     * 
     * This method stores the given key and value in the dictionary. It first checks if the key or value is null, in which case it does nothing. 
     * If the current size-to-capacity ratio exceeds the default load factor, it triggers a rehash to resize the dictionary. 
     * The method calculates the bucket index based on the key and either adds the new entry if the bucket is empty or appends the entry 
     * to the linked list in case of collisions. If the key already exists, it updates the corresponding value.
     * 
     * @param key The key associated with the value to be added.
     * @param value The value associated with the key.
     */
    public void put(K key, V value) {
        // Disallow storing null keys.
        if (key == null || value == null) {
            return;
        }
        // In the case that the current factor is greater than or equal to default load
        // factor, rehash the dictionary
        if (((double) size / capacity) >= DEFAULT_LOAD_FACTOR) {
            reHash();
        }
        // calculate the bucket index based on the key
        int index = getBucketIndex(key);
        // create a new entry
        EntryNode<K, V> entryNode = new EntryNode<>(key, value);

        EntryNode<K, V> current = buckets[index];
        // In the case that the bucket is empty, simply add the entrynode to the bucket.
        if (current == null) {
            buckets[index] = entryNode;
        } else {
            // Store previous node in linked list
            EntryNode<K, V> previous = null;

            while (current != null) {
                // In the case the key already exists, simply update the value of that node and exit.
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                // Otherwise, move on to the next element
                previous = current;
                current = current.next;
            }
            // At this stage, previous represents last element in the linked list. 
            // Set next for previous to point to th new entry
            previous.next = entryNode;
        }
        
        this.size++;
    }

    /**
     * Retrieves the value associated with the given key.
     * 
     * This method calculates the appropriate bucket index using the key's hash code and then searches through 
     * the linked list in the bucket to find the entry with the matching key. If the key exists, the corresponding 
     * value is returned. If the key is not found, it returns null.
     * 
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the specified key, or null if the key is not found.
     */
    public V get(K key) {
        int index = getBucketIndex(key);

        EntryNode<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes the entry with the specified key from the dictionary.
     * 
     * This method calculates the appropriate bucket index for the given key, then iterates through the linked 
     * list in the bucket to find the entry with the matching key. If found, the entry is removed by adjusting 
     * the pointers in the linked list. If the entry is the first in the list, the head is updated. The size of 
     * the dictionary is decremented upon successful removal.
     * 
     * @param key The key of the entry to be removed.
     * @return {@code true} if the entry was removed, {@code false} if no such key exists in the dictionary.
     */
    public boolean remove(K key) {
        // get bucket index for kv pair we want to remove
        int index = getBucketIndex(key);

        // get the first entry stored in the designated bucket.
        // store previous 
        EntryNode<K, V> previousNode = null;
        EntryNode<K, V> currentNode = buckets[index];

        // iterate through each entry whilst current is not null
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                // in the case that the entry we are currently considering matches 
                // the key, if this is the first node, update the head to point to the next node
                if (previousNode == null) {
                    buckets[index] = currentNode.next;
                } else {
                    // Otherwise, set the next pointer of the previous node to the next pointer of the
                    // currnent node, in essence removing the current pointer.
                    previousNode.next = currentNode.next;
                }
                size--;
                return true;
            }
            // otherwise set current to current.next, previous to current.
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        // if current node is false, we return null - there doesn't exist a key in the hashmap
        // that we can remove
        return false;
    }

    /**
     * Returns an array containing all the keys in the dictionary.
     * 
     * This method iterates through all the buckets in the dictionary and collects the keys from each entry node 
     * into an array. The size of the array is equal to the current size of the dictionary. The keys are cast to 
     * {@code Integer} before being added to the array.
     * 
     * @return An array of {@code Integer} keys stored in the dictionary.
     */
    public Integer[] getKeys() {
        Integer[] keysArray = new Integer[size]; // Allocate array of exact required size
        int index = 0;
    
        for (EntryNode<K, V> bucket : buckets) {
            EntryNode<K, V> current = bucket;
    
            while (current != null) {
                keysArray[index++] = (Integer) current.key; // Directly store the key
                current = current.next;
            }
        }
        return keysArray;
    }    
    
    /**
     * Checks whether the dictionary contains a specified key.
     * 
     * This method checks if the key exists in the dictionary by attempting to retrieve 
     * its corresponding value. If the key is found, it returns {@code true}; otherwise, 
     * it returns {@code false}.
     * 
     * @param key The key to check for in the dictionary.
     * @return {@code true} if the dictionary contains the specified key, {@code false} otherwise.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    // Increase size of intrnal array.
    private void reHash() {
        // Double hash map capacity
        capacity *= INCREASE_FACTOR;
        // Set buckets to array of buckets with the new length, all buckets storing null.
        @SuppressWarnings("unchecked")
        EntryNode<K,V>[] newBuckets = new EntryNode[capacity];
        // Iterate through each bucket
        for (EntryNode<K, V> bucket: buckets) {
            EntryNode<K, V> current = bucket;

            while (current != null){
                EntryNode<K, V> next = current.next;

                // calcualte bucket index for current key
                int index = getBucketIndex(current.key);

                // insert the current entry into the new bucket array
                current.next = newBuckets[index];
                newBuckets[index] = current;

                // move onto the next entry
                current = next;
            }
        }
        buckets = newBuckets;
    }

    /**
     * Returns the number of key-value pairs currently stored in the dictionary.
     * 
     * This method retrieves the value of the {@code size} field, which tracks how many 
     * key-value pairs are present in the dictionary. It provides a way to check the 
     * current capacity of the dictionary.
     * 
     * @return The number of key-value pairs stored in the dictionary.
     */
    public int size() {
        return this.size;
    }
}
