package boost.map.double2;

import boost.util.NotNull;

import java.util.*;
import java.util.Map.Entry;

public class DoubleMapFactory {
    private static final DoubleMap EMPTY = new DoubleMapEmpty();

    public static @NotNull <K> DoubleMap<K> empty() {
        return (DoubleMap<K>) EMPTY;
    }

    public static @NotNull <K>DoubleMap<K> make(Map<K, double[]> p_position) {
        if(p_position!=null) {
            switch(p_position.size()) {
                case 0: break;
                case 1: {
                    Map.Entry<K, double[]> entry = p_position.entrySet().iterator().next();
                    double[] value = entry.getValue();
                    return new DoubleMapSingle<K>(entry.getKey(), value[0]);
                }
                case 2: {
                    Iterator<Entry<K, double[]>> iterator = p_position.entrySet().iterator();
                    Map.Entry<K, double[]> entry = iterator.next();
                    K key1 = entry.getKey();
                    double[] value1 = entry.getValue();
                    entry = iterator.next();
                    double[] value2 = entry.getValue();
                    return make(key1, value1[0], entry.getKey(), value2[0]);
                }
                default:
                    return new DoubleMapMultiple<K>(p_position, true);
            }
        }
        return empty();
    }

    public static @NotNull <K> DoubleMap<K> make(K p_key, double p_value){
        return new DoubleMapSingle<K>(p_key, p_value);
    }

    public static @NotNull <K> DoubleMap<K> make(K p_key1, double p_value1, K p_key2, double p_value2){
        if(p_key1==p_key2 || p_key1!=null && p_key2!=null && p_key1.equals(p_key2))
            return new DoubleMapSingle<K>(p_key1, p_value1 + p_value2);
        return new DoubleMapDual<K>(p_key1, p_value1, p_key2, p_value2);
    }

    public static @NotNull <K> DoubleMap<K> make(K[] p_keys, double[] p_values) {
        if(p_keys == null) return empty();
        switch(p_keys.length) {
            case 0:
                return empty();
            case 1:
                return new DoubleMapSingle<K>(p_keys[0], p_values[0]);
            case 2:
                return make(p_keys[0], p_values[0], p_keys[1], p_values[1]);
        }
        Map<K, double[]> res = new HashMap<>();
        for(int i = 0; i < p_keys.length; i++) {
            double[] value = new double[1];
            value[0] = p_values[i];
            res.put(p_keys[i], value);
        }
        return new DoubleMapMultiple<K>(res, false);
    }

    public static @NotNull <K> DoubleMap<K> combine(@NotNull DoubleMap<K> p_actMoneyPosition, @NotNull DoubleMap<K> p_mtmMoneyPosition) {
        if(p_actMoneyPosition.isEmpty()) return p_mtmMoneyPosition;
        if(p_mtmMoneyPosition.isEmpty()) return p_actMoneyPosition;
        DoubleCollector<K> collector = new DoubleCollector<>(p_actMoneyPosition);
        collector.addAll(p_mtmMoneyPosition);
        return collector;
    }

}
