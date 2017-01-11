package boost.map;

import boost.util.NotNull;

import java.util.*;

class TwoValuesMap<K,V> implements IReadonlyMap<K,V> {
    private static final long serialVersionUID = 1L;

    private Set<Entry<K, V>> o_entrySet;
    private final K o_key1;
    private final K o_key2;
    private final V o_value1;
    private final V o_value2;

    TwoValuesMap(Map.Entry<K,V> p_entry1, Map.Entry<K,V> p_entry2) {
        this(p_entry1.getKey(), p_entry1.getValue(), p_entry2.getKey(), p_entry2.getValue());
    }

    TwoValuesMap(K p_key1, V p_value1, K p_key2, V p_value2) {
        o_key1 = p_key1;
        o_value1 = p_value1;
        o_key2 = p_key2;
        o_value2 = p_value2;
    }

    public int size() { return 2; }
    public boolean isEmpty() { return false; }

    public boolean containsKey(Object p_key) {
        return Objects.equals(o_key1, p_key) || Objects.equals(o_key2, p_key);
    }

    public boolean containsValue(Object p_value) {
        return Objects.equals(o_value1, p_value) || Objects.equals(o_value2, p_value);
    }

    public V get(Object p_key) {
        if(Objects.equals(o_key1, p_key)) return o_value1;
        if(Objects.equals(o_key2, p_key)) return o_value2;
        return null;
    }

    public V getAnyValue() { return o_value1; }
    public Map.Entry<K, V> getAny() { return new EntryImpl<K,V>(o_key1, o_value1); }

    public @NotNull Set<K> keySet() {
        return new AbstractSet<K>() {
            public @NotNull Iterator<K> iterator() {
                return new Iterator<K>() {
                    private int i = 0;
                    public void remove() { throw new UnsupportedOperationException(); }
                    public boolean hasNext() { return i < 2; }

                    public K next() {
                        if(!hasNext()) throw new NoSuchElementException();
                        i++;
                        return i==1? o_key1 : o_key2;
                    }
                };
            }
            public int size() { return 2; }
        };
    }

    public @NotNull Set<Entry<K, V>> entrySet() {
        if(o_entrySet == null) {
            o_entrySet = new AbstractSet<Entry<K, V>>() {
                public @NotNull Iterator<Entry<K, V>> iterator() {
                    return TwoValuesMap.this.iterator();
                }
                public int size() { return 2; }
            };
        }
        return o_entrySet;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private int i = 0;
            public boolean hasNext() { return i < 2; }
            public void remove() { throw new UnsupportedOperationException(); }
            public Map.Entry<K, V> next() {
                if(!hasNext()) throw new NoSuchElementException();
                Map.Entry<K,V> entry = i==0?
                        new EntryImpl<K,V>(o_key1, o_value1) :
                        new EntryImpl<K,V>(o_key2, o_value2);
                i++;
                return entry;
            }
        };
    }

    public @NotNull Collection<V> values() {
        return new AbstractCollection<V>() {
            public int size() { return 2; }
            public @Override boolean isEmpty() { return false; }
            public @NotNull Iterator<V> iterator() {
                return new Iterator<V>() {
                    private int i = 0;
                    public boolean hasNext() { return i < 2; }
                    public void remove() { throw new UnsupportedOperationException(); }
                    public V next() {
                        if(!hasNext()) throw new NoSuchElementException();
                        V result = i==0? o_value1 : o_value2;
                        i++;
                        return result;
                    }
                };
            }
            public @Override boolean contains(Object p_obj) {
                return Objects.equals(o_value1, p_obj) || Objects.equals(o_value2, p_obj);
            }
            public @Override @NotNull Object[] toArray() {
                return new Object[] {o_value1, o_value2};
            }
            public @Override boolean containsAll(Collection<?> c) {
                if(c != null) {
                    switch(c.size()) {
                        case 0: return true;
                        case 1: return c.contains(o_value1) || c.contains(o_value2);
                        case 2: return c.contains(o_value1) && c.contains(o_value2);
                    }
                }
                return false;
            }
            public boolean equals(Object o) {
                if (o == this)
                    return true;

                if (!(o instanceof Collection))
                    return false;
                Collection c = (Collection) o;
                if (c.size() != size())
                    return false;
                try {
                    return containsAll(c);
                } catch (ClassCastException unused)   {
                    return false;
                } catch (NullPointerException unused) {
                    return false;
                }
            }
            public int hashCode() {
                return Objects.hashCode(o_value1) + Objects.hashCode(o_value2);
            }

            public @Override boolean remove(Object o) { throw new UnsupportedOperationException(); }
            public @Override boolean addAll(Collection<? extends V> c) { throw new UnsupportedOperationException(); }
            public @Override boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            public @Override boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            public @Override void clear() { throw new UnsupportedOperationException(); }
        };
    }

    public void addAllTo(@NotNull Map<K, V> p_map) {
        p_map.put(o_key1, o_value1);
        p_map.put(o_key2, o_value2);
    }

    public void addAllValuesTo(@NotNull Collection<V> p_list) {
        p_list.add(o_value1);
        p_list.add(o_value2);
    }

    public @Override boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map))
            return false;
        Map<K,V> m = (Map<K,V>) o;
        if (m.size() != size())
            return false;

        if (o_value1 == null) {
            if (!(m.get(o_key1)==null && m.containsKey(o_key1)))
                return false;
        } else {
            if (!o_value1.equals(m.get(o_key1)))
                return false;
        }
        if (o_value2 == null) {
            if (!(m.get(o_key2)==null && m.containsKey(o_key2)))
                return false;
        } else {
            if (!o_value2.equals(m.get(o_key2)))
                return false;
        }
        return true;
    }

    public @Override int hashCode() {
        int result = Objects.hashCode(o_key1) ^ Objects.hashCode(o_value1);
        result += Objects.hashCode(o_key2) ^ Objects.hashCode(o_value2);
        return result;
    }

    public @Override String toString() {
        return "["+o_key1+"->"+o_value1+", "+o_key2+"->"+o_value2+"]";
    }

    public V put(K key, V value) { throw new UnsupportedOperationException(); }
    public V remove(Object key) { throw new UnsupportedOperationException(); }
    public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    public void clear() { throw new UnsupportedOperationException(); }
}
