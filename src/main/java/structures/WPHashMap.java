package structures;

public class WPHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 100;
    private static final int INCREASE_FACTOR = 2;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private EntryNode<K,V>[] buckets;
    private int capacity;

    // INitialise buckets to contain the default number of entries
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

    // Uses hashing to determine bucket index.
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

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

    // todo
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

    // return how many k-v pairs are being stored in the hashmap
    public int size() {
        return this.size;
    }
}
