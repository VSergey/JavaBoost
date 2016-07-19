package boost.map;

import boost.util.iterators.Iterators;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.*;

class EmptyMap<K,V> implements IReadonlyMap<K,V> {
    private static final long serialVersionUID = 1L;
    static final IReadonlyMap EMPTY = new EmptyMap();

    private EmptyMap() {}

    public int size() { return 0; }

    public boolean isEmpty() { return true; }
    public boolean containsKey(Object key) { return false; }
    public boolean containsValue(Object value) { return false; }

    public V get(Object key) { return null; }
    public V getAnyValue() { return null; }
    public Map.Entry<K, V> getAny() { return null; }

    public Iterator<Entry<K, V>> iterator() {
        return Iterators.empty();
    }

    public @NonNull Collection<V> values() {
        return Collections.<V>emptyList();
    }

    public @NonNull Set<K> keySet() {
        return Collections.emptySet();
    }

    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        return Collections.emptySet();
    }

    public void addAllTo(@NonNull Map<K, V> p_map) { }
    public void addAllValuesTo(@NonNull Collection<V> p_list) { }

    public boolean equals(Object o) {
        return (o instanceof Map) && ((Map<?,?>)o).isEmpty();
    }

    public int hashCode() { return 0; }

    public @Override String toString() {
        return "EmptyMap";
    }

    public V put(K key, V value) { throw new UnsupportedOperationException(); }
    public V remove(Object key) { throw new UnsupportedOperationException(); }
    public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    public void clear() { throw new UnsupportedOperationException(); }
}
