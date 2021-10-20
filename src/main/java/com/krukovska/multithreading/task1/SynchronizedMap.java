package com.krukovska.multithreading.task1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SynchronizedMap<K, V> implements Map<K, V> {

    private final Set<Map.Entry<K, V>> entries = new HashSet<>();

    @Override
    public synchronized int size() {
        return entries.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized V get(Object key) {
        for (Map.Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public synchronized V put(K key, V value) {
        entries.add(new Entry<>(key, value));
        return value;
    }

    @Override
    public synchronized V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Set<K> keySet() {
        return new HashSet<>();
    }

    @Override
    public synchronized Collection<V> values() {
        Set<V> values = new HashSet<>();
        for (Map.Entry<K, V> entry : entries) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entries;
    }


    public static class Entry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }
}
