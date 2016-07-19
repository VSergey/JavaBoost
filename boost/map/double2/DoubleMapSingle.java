package boost.map.double2;

import boost.util.floating.FloatingPointValues;

import java.util.*;

public class DoubleMapSingle<T> implements DoubleMap<T> {
    private final T o_key;
    private final double o_value;

    DoubleMapSingle(T p_key, double p_value) {
        o_key = p_key;
        o_value = p_value;
    }

    public boolean containsKey(T p_key) {
        return o_key == p_key || o_key != null && o_key.equals(p_key);
    }

    public double get(T p_key) {
        return (containsKey(p_key))? o_value: 0;
    }

    public @Override int hashCode()             { return o_key==null? 0: o_key.hashCode(); }
    public int size()                           { return 1; }
    public boolean isEmpty()                    { return false; }
    public boolean isZero(double p_precision)   { return Math.abs(o_value) < p_precision; }
    public T getAnyKey()                        { return o_key; }
    public Set<T> keySet()                      { return Collections.singleton(o_key); }
    public Map<T, Double> convertToMap()        { return Collections.singletonMap(o_key, o_value); }

    public DoubleMap<T> scale(double p_scale) {
        if(p_scale == 1.0) return this;
        return new DoubleMapSingle<T>(o_key, o_value*p_scale);
    }

    public DoubleMap<T> getNonZero(double p_precision) {
        if(isZero(p_precision))
            return DoubleMapFactory.empty();
        return this;
    }

    public @Override boolean equals(Object p_obj){
        if(this == p_obj) return true;
        if(p_obj instanceof DoubleMapSingle) {
            DoubleMapSingle ps = (DoubleMapSingle)p_obj;
            return Objects.equals(o_key, ps.o_key) && FloatingPointValues.roundedEqual(o_value, ps.o_value, 3);
        }
        return false;
    }

    public @Override String toString() {
        return "S{" + o_key + "->" + o_value + '}';
    }
}
