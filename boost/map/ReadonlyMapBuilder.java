package boost.map;

import com.sun.javafx.beans.annotations.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ReadonlyMapBuilder {

    public @NonNull static <K,V> IReadonlyMap<K,V> empty() {
        return EmptyMap.EMPTY;
    }

    public @NonNull static <K,V> IReadonlyMap<K,V> singleton(K p_key, V p_value) {
        return new ReadonlySingleton<K,V>(p_key, p_value);
    }

    public @NonNull static <K,V> IReadonlyMap<K,V> twoValues(K p_key1, V p_value1, K p_key2, V p_value2) {
        return new TwoValuesMap<K,V>(p_key1, p_value1, p_key2, p_value2);
    }

    public @NonNull static <K,V> IReadonlyMap<K,V> create(@NonNull Map<K,V> p_map) {
        if(p_map.isEmpty()) return empty();
        if(p_map.size()==1) {
            Map.Entry<K, V> entry = p_map.entrySet().iterator().next();
            return singleton(entry.getKey(), entry.getValue());
        }
        if(p_map.size()==2) {
            Iterator<Entry<K, V>> i = p_map.entrySet().iterator();
            Map.Entry<K, V> entry1 = i.next();
            Map.Entry<K, V> entry2 = i.next();
            return new TwoValuesMap<K,V>(entry1, entry2);
        }
        return new ReadonlyMap<K,V>(p_map);
    }
}
