package boost.map;

import java.util.Map;
import java.util.Objects;

class EntryImpl<K,V> implements Map.Entry<K,V> {
    private final K o_key;
    private final V o_value;

    EntryImpl(K p_key, V p_value) {
        o_key = p_key;
        o_value = p_value;
    }

    public K getKey() { return o_key; }
    public V getValue() { return o_value; }

    public V setValue(V value) { throw new UnsupportedOperationException(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryImpl entry = (EntryImpl) o;
        return Objects.equals(o_key, entry.o_key) && Objects.equals(o_value, entry.o_value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(o_key);
        result = 31 * result + Objects.hashCode(o_value);
        return result;
    }
}
