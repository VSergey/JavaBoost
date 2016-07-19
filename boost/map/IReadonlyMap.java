package boost.map;

import boost.util.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface IReadonlyMap<K,V> extends Map<K,V>, Iterable<Map.Entry<K, V>>, Serializable {
    /**
     * Returns any value contained in this map
     *
     * @return any value contained in this map
     */
    V getAnyValue();

    /**
     * Returns any Map.Entry contained in this map
     *
     * @return any Map.Entry contained in this map
     */
    Map.Entry<K, V> getAny();

    void addAllTo(@NotNull Map<K,V> p_map);

    void addAllValuesTo(@NotNull Collection<V> p_list);
}
