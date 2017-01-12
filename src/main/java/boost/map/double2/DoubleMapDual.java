package boost.map.double2;

import boost.util.NotNull;
import boost.util.floating.FloatingPointValues;

import java.util.*;

class DoubleMapDual<T> implements DoubleMap<T> {
    private final T o_key1;
    private final T o_key2;
    private final double o_value1;
    private final double o_value2;

    DoubleMapDual(T p_key1, double p_value1, T p_key2, double p_value2) {
        o_key1 = p_key1;
        o_value1 = p_value1;
        o_key2 = p_key2;
        o_value2 = p_value2;
    }

    public boolean containsKey(T p_key) {
        return o_key1 == p_key || o_key2 == p_key ||
                p_key != null && (p_key.equals(o_key1) || p_key.equals(o_key2));
    }

    public double get(T p_key) {
        if(o_key1 == p_key) return o_value1;
        if(o_key2 == p_key) return o_value2;
        if(p_key == null) return 0;
        if(p_key.equals(o_key1))
            return o_value1;
        if(p_key.equals(o_key2))
            return o_value2;
        return 0;
    }

    public int size()               { return 2; }
    public boolean isEmpty()        { return false; }
    public T getAnyKey()            { return o_key1; }

    public boolean isZero(double p_precision) {
        return Math.abs(o_value1) < p_precision && Math.abs(o_value2) < p_precision;
    }

    public @NotNull Set<T> keySet() {
        Set<T> keys = new HashSet<T>();
        keys.add(o_key1);
        keys.add(o_key2);
        return keys;
    }

    public @NotNull Map<T, Double> convertToMap() {
        Map<T, Double> res = new HashMap<T, Double>();
        res.put(o_key1, o_value1);
        res.put(o_key2, o_value2);
        return res;
    }

    public @NotNull DoubleMap<T> getNonZero(double p_precision) {
        if(Math.abs(o_value1) < p_precision) {
            if( Math.abs(o_value2) < p_precision) return DoubleMapFactory.empty();
            return new DoubleMapSingle<T>(o_key2, o_value2);
        } else if( Math.abs(o_value2) < p_precision) {
            return new DoubleMapSingle<T>(o_key1, o_value1);
        }
        return this;
    }

    public @NotNull DoubleMap<T> scale(double p_scale) {
        if(p_scale == 1.0) return this;
        return new DoubleMapDual<T>(o_key1, o_value1*p_scale, o_key2, o_value2*p_scale);
    }

    public int hashCode() {
        int hash = o_key1==null? 0: o_key1.hashCode();
        hash ^= o_key2==null? 0: o_key2.hashCode();
        return hash;
    }

    public boolean equals(Object p_obj){
        if(this == p_obj) return true;
        if(p_obj instanceof DoubleMapDual) {
            DoubleMapDual ps = (DoubleMapDual)p_obj;
            if(comparePair(o_key1, o_value1, ps.o_key1, ps.o_value1))
                return comparePair(o_key2, o_value2, ps.o_key2, ps.o_value2);
            else if(comparePair(o_key1, o_value1, ps.o_key2, ps.o_value2))
                return comparePair(o_key2, o_value2, ps.o_key1, ps.o_value1);
        }
        return false;
    }

    private boolean comparePair(Object p_key1, double p_value1, Object p_key2, double p_value2) {
        return Objects.equals(p_key1, p_key2) && FloatingPointValues.roundedEqual(p_value1, p_value2, 3);
    }

    public String toString() {
        return "D{" + o_key1 + "->" + o_value1 + ", " + o_key2 + "->" + o_value2 + '}';
    }}
