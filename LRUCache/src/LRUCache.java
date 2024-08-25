import java.util.HashMap;
import java.util.Map;

class ListNode<K, V> {
    K key;
    V value;
    ListNode<K, V> prev;
    ListNode<K, V> next;

    public ListNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class LRUCache<K, V> {
    int capacity;
    Map<K, ListNode<K, V>> map;
    ListNode<K, V> head;
    ListNode<K, V> tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new ListNode(null, null);
        tail = new ListNode(null, null);
        head.next  = tail;
        tail.prev = head;
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        ListNode node = map.get(key);
        remove(node);
        add(node);

        return (V) node.value;
    }

    public void put(K key, V value) {
        //check if already present in hashmap, if yes, remove it from LinkedList
        if (map.containsKey(key)) {
            ListNode oldNode = map.get(key);
            remove(oldNode);
        }

        ListNode node = new ListNode(key, value);
        map.put(key, node);
        add(node);

        if (map.size() > capacity) {
            //head is the oldest node in the list, remove it
            //head.next since we are keeping a sentinel head
            ListNode nodeToDelete = head.next;
            remove(nodeToDelete);
            map.remove(nodeToDelete.key);
        }
    }

    private void add(ListNode node) {
        ListNode end = tail.prev;
        end.next = node;
        node.prev = end;
        node.next = tail;
        tail.prev = node;
    }

    private void remove(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(5);
        for (int i = 0; i < 7; i++) {
            cache.put("key-no-" + i, 1 + i);
        }

        for (int i = 0; i < 7; i++) {
            System.out.println("key-no-" + i + ": " + cache.get("key-no-" + i));
        }
    }
}
