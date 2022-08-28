package core.basesyntax;

import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private final int INITIAL_CAPACITY = 1 << 4;
    private final float LOAD_FACTOR = 0.75f;
    private Node<K, V>[] table;
    private int size;

    private class Node<K,V> {
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }
        int index = getIndex(key);
        Node<K,V> current = new Node(key,value,null);
        if (table[index] == null) {
            table[index] = current;
            size++;
            return;
        }
        Node old = table[index];
        while (old != null) {
            if (key == null || key.equals(old.key)) {
                old.value = value;
                return;
            }
            if(old.next == null){
                old.next = current;
                break;
            }
            old = old.next;
        }
        size++;
    }

    @Override
    public V getValue(K key) {
        int index = getIndex(key);
        Node c = table[index];
        while (c != null) {
            if( Objects.equals(key, (K) c.key)) {
                return (V) c.value;
            }
            c = c.next;
        }
        return null;
    }

    private void resize() {
        size = 0;
        Node<K,V>[] mapAsArray = table;
        table = new Node[mapAsArray.length << 1];
        for (Node cur : mapAsArray) {
            while(cur != null) {
                put((K) cur.key, (V) cur.value);
                cur = cur.next;
            }
        }

    }

    private int getIndex(K key) {
        return (key == null) ? 0 : Math.abs(Objects.hash(key) % table.length);

















    }

    @Override
    public int getSize() {

        return size;
    }
}
