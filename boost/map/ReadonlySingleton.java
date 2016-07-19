package boost.map;

import com.sun.javafx.beans.annotations.NonNull;

import java.util.*;

class ReadonlySingleton<K,V> implements IReadonlyMap<K,V> {
    private static final long serialVersionUID = 1L;

    private Set<Entry<K, V>> o_entrySet;
    private final K o_key;
    private final V o_value;

    ReadonlySingleton(K p_key, V p_value) {
        o_key = p_key;
        o_value = p_value;
    }

    public int size() { return 1; }

    public boolean isEmpty() { return false; }

    public boolean containsKey(Object p_key) {
        return Objects.equals(o_key, p_key);
    }

    public boolean containsValue(Object p_value) {
        return Objects.equals(o_value, p_value);
    }

    public V get(Object p_key) {
        return containsKey(p_key)? o_value : null;
    }
    public V getAnyValue() { return o_value; }
    public Map.Entry<K, V> getAny() { return new EntryImpl<K,V>(o_key, o_value); }

    public @NonNull Set<K> keySet() {
        return Collections.singleton(o_key);
    }

    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        if(o_entrySet == null) {
            o_entrySet = new AbstractSet<Entry<K, V>>() {
                @Override public @NonNull Iterator<Entry<K, V>> iterator() {
                    return new SingletonIterator();
                }
                @Override public int size() { return 1; }
            };
        }
        return o_entrySet;
    }

    public @NonNull Iterator<Map.Entry<K, V>> iterator() {
        return new SingletonIterator();
    }

    public @NonNull Collection<V> values() {
        return Collections.singletonList(o_value);
    }

    public void addAllTo(@NonNull Map<K, V> p_map) {
        p_map.put(o_key, o_value);
    }

    public void addAllValuesTo(@NonNull Collection<V> p_list) {
        p_list.add(o_value);
    }

    public @Override boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map))
            return false;
        Map<K,V> m = (Map<K,V>) o;
        if (m.size() != size())
            return false;
        Entry<K, V> entry = m.entrySet().iterator().next();
        return Objects.equals(o_key, entry.getKey()) && Objects.equals(o_value, entry.getValue());
    }

    public @Override int hashCode() {
        return Objects.hashCode(o_key) ^ Objects.hashCode(o_value);
    }

    public @Override String toString() {
        return "["+o_key+"->"+o_value+"]";
    }

    public V put(K key, V value) { throw new UnsupportedOperationException(); }
    public V remove(Object key) { throw new UnsupportedOperationException(); }
    public void putAll(@NonNull Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    public void clear() { throw new UnsupportedOperationException(); }

    private class SingletonIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        private boolean o_hasNext = true;

        public boolean hasNext() { return o_hasNext; }
        public K getKey() { return o_key; }
        public V getValue() { return o_value; }

        public Map.Entry<K, V> next() {
            if (!o_hasNext) throw new NoSuchElementException();
            o_hasNext = false;
            return this;
        }

        public void remove() { throw new UnsupportedOperationException(); }
        public V setValue(V value) { throw new UnsupportedOperationException(); }
    };
}

