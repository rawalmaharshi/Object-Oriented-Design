import java.util.ArrayList;
import java.util.List;

class MapNode<K,V> {
    K key;
    V value;
    MapNode<K, V> next; //storing as linked list

    public MapNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class Map<K, V> {
    List<MapNode<K, V>> buckets;
    int size;
    int numBuckets;

    public Map() {
        numBuckets = 5;
        buckets = new ArrayList<>();
        //initialize with null
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(null);
        }
    }

    //Which index to insert key
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        return hashCode % numBuckets;
    }

    public void insert(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        MapNode<K, V> head = buckets.get(bucketIndex);
        //if key is already present; update it
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value; //update value
                return ;
            }
            head = head.next;
        }

        //else - new entry (in the front of the linked list)
        head = buckets.get(bucketIndex);
        MapNode<K, V> newNode = new MapNode<>(key, value);
        size++;
        newNode.next = head;
        buckets.set(bucketIndex, newNode);
        double loadFactor = (1.0 * size) / numBuckets;
        if (loadFactor > 0.7) {
            rehash();
        }
    }

    private void rehash() {
        List<MapNode<K, V>> temp = buckets;
        buckets = new ArrayList<>();
        for (int i = 0; i < 2 * numBuckets; i++) {
            buckets.add(null);
        }

        //Make a new bucket twice the earlier size
        size = 0;
        numBuckets *= 2;

        //copy
        for (int i = 0; i < temp.size(); i++) {
            MapNode<K, V> head = temp.get(i);
            while (head != null) {
                K key = head.key;
                V value = head.value;
                insert(key, value);
                head = head.next;
            }
        }
    }

    public V getValue(K key) {
        int bucketIndex = getBucketIndex(key);
        MapNode<K, V> head = buckets.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public V removeKey(K key) {
        int bucketIndex = getBucketIndex(key);
        MapNode<K, V> head = buckets.get(bucketIndex);
        MapNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                size--;
                //if 1st node in linked list is removed
                if (prev == null) {
                    buckets.set(bucketIndex, head.next);
                } else {
                    prev.next = head.next;
                }
                return head.value;
            }
            //move to next node in the list
            prev = head;
            head = head.next;
        }

        //key not found
        return null;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new Map<>();
        for (int i = 0; i < 20; i++) {
            map.insert("key-no-" + i, 1 + i);
        }

        map.removeKey("key-no-5");
        map.removeKey("key-no-10");

        for (int i = 0; i < 20; i++) {
            System.out.println("key-no-" + i + ": " + map.getValue("key-no-" + i));
        }
    }
}
