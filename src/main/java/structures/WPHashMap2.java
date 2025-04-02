package structures;

import java.util.Objects;

public class WPHashMap2<K, V> {
    private static final int INITIAL_CAPACITY = 4;
    private static final int INCREASE_FACTOR = 2;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private EntryNode<K,V>[] buckets;
    private int capacity;

    // INitialise buckets to contain the default number of entries
    public WPHashMap2() {
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
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        // Disallow storing null keys.
        if (key == null) {
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
    public V remove(K key) {
        int index = getBucketIndex(key);
        EntryNode<K, V> node = buckets[index];
        if (node != null) {
            if (node.next == null && Objects.equals(node.key, key)) {
                V value = buckets[index].value;
                size--;
                buckets[index] = null;
                return value;
            } else if (node.next != null && Objects.equals(node.key, key)) {
                V value = buckets[index].value;
                size--;
                buckets[index] = node.next;
                return value;
            } else {
                while (node.next != null) {
                    if (node.next.key.equals(key)) {
                        node.next = node.next.next;
                        this.size--;
                        return node.value;
                    }
                    node = node.next;
                }
            }
        }
        return null;
    }
    // todo
    public boolean containsKey(K key) {
        return this.getNode(key) != null;
    }


    // Increase size of intrnal array.
    private void reHash() {
        // Double hash map capacity
        capacity *= INCREASE_FACTOR;
        // Set buckets to array of buckets with the new length, all buckets storing null.
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
        return this.size();
    }
}
