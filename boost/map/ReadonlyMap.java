package boost.map;

import com.sun.javafx.beans.annotations.NonNull;

import java.util.*;

class ReadonlyMap<K,V> implements IReadonlyMap<K,V> {
    private static final long serialVersionUID = 1L;
    private final Map<K,V> o_map;

    ReadonlyMap(@NonNull Map<K, V> p_map) {
        if(p_map.isEmpty()) throw new IllegalArgumentException("map can't be empty");
        o_map = p_map;
    }

    public int size() { return o_map.size(); }
    public boolean isEmpty() { return false; }

    public boolean containsKey(Object p_key) {
        return o_map.containsKey(p_key);
    }

    public boolean containsValue(Object p_value) {
        return o_map.containsValue(p_value);
    }

    public V get(Object p_key) {
        return o_map.get(p_key);
    }

    public V getAnyValue() {
        return getAny().getValue();
    }

    public Map.Entry<K, V> getAny() {
        return o_map.entrySet().iterator().next();
    }

    public @NonNull Set<K> keySet() {
        return Collections.unmodifiableSet(o_map.keySet());
    }

    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(o_map.entrySet());
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }

    public void addAllTo(@NonNull Map<K, V> p_map) {
        p_map.putAll(o_map);
    }

    public void addAllValuesTo(@NonNull Collection<V> p_list) {
        p_list.addAll(o_map.values());
    }

    public @NonNull Collection<V> values() {
        return Collections.unmodifiableCollection(o_map.values());
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o_map.equals(o);
    }

    @Override
    public int hashCode() {
        return o_map.hashCode();
    }

    public @Override String toString() {
        return o_map.toString();
    }

    public V put(K key, V value) { throw new UnsupportedOperationException(); }
    public V remove(Object key) { throw new UnsupportedOperationException(); }
    public void putAll(@NonNull Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    public void clear() {
        throw new UnsupportedOperationException(); }
}
