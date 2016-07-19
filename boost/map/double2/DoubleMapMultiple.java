package boost.map.double2;

import boost.util.NotNull;

import java.util.*;

public class DoubleMapMultiple<T> implements DoubleMap<T> {
    protected final @NotNull Map<T, double[]> o_map;
    private transient int o_hash;

    /**
     * Create position map with size > 2
     * @param p_map Object -> double[]
     */
    DoubleMapMultiple(@NotNull Map<T, double[]> p_map, boolean p_copy) {
        int size = p_map.size();
        if(size < 3) {
            switch (size) {
                case 0:
                    throw new IllegalArgumentException("Use DoubleMap.EMPTY");
                case 1:
                    throw new IllegalArgumentException("Use DoubleMapSingle");
                default:
                    throw new IllegalArgumentException("Use DoubleMapDual");
            }
        }
        o_map = p_copy? newMapInstance(p_map) : p_map;
    }

    protected DoubleMapMultiple(@NotNull Map<T, double[]> p_map) {
        o_map = p_map;
    }

    /**
     * Override in descendants if underlying map should be not HashMap instance.
     *
     * @param p_map
     *            the map which entries should be copyied in new map.
     *            <code>null</code> is available. in latter we just create an
     *            empty map.
     * @return a new map
     */
    protected Map<T, double[]> newMapInstance(@NotNull Map<T, double[]> p_map){
        return new HashMap<>(p_map);
    }

    public boolean containsKey(T p_key) {
        return o_map.containsKey(p_key);
    }

    public double get(T p_key){
        double[] result = o_map.get(p_key);
        return result == null ? 0d : result[0];
    }

    public Set<T> keySet(){
        return Collections.unmodifiableSet(o_map.keySet());
    }

    public int size()                   { return o_map.size(); }
    public boolean isEmpty()            { return o_map.isEmpty(); }

    public T getAnyKey() {
        Iterator<Map.Entry<T, double[]>> iterator = o_map.entrySet().iterator();
        return iterator.hasNext()? iterator.next().getKey() : null;
    }

    public boolean isZero(double p_precision) {
        for (double[] value : o_map.values()) {
            if (Math.abs(value[0]) < p_precision) continue;
            return false;
        }
        return true;
    }

    /**
     * Converts this PositionMap to Map(T->Double)
     * @return Map(T->Double)
     */
    public @NotNull Map<T, Double> convertToMap(){
        Map<T, Double> x_res = new HashMap<T, Double>(size());
        for (Map.Entry<T, double[]> entry : o_map.entrySet()) {
            x_res.put(entry.getKey(), entry.getValue()[0]);
        }
        return x_res;
    }

    private boolean isContainsZero(double p_precision) {
        for (Map.Entry<T, double[]> entry : o_map.entrySet()) {
            double[] v = entry.getValue();
            if (Math.abs(v[0]) < p_precision) return true;
        }
        return false;
    }

    public DoubleMap<T> getNonZero(double p_precision) {
        if(!isContainsZero(p_precision)) {
            return this;
        }
        return makeNonZero(p_precision);
    }

    protected DoubleMap<T> makeNonZero(double p_precision) {
        Map<T, double[]> res = new HashMap<>();
        for (Map.Entry<T, double[]> entry : o_map.entrySet()) {
            double[] v = entry.getValue();
            if (Math.abs(v[0]) < p_precision) continue;
            res.put(entry.getKey(), v);
        }
        return DoubleMapFactory.make(res);
    }

    public DoubleMap<T> scale(double p_scale) {
        if(p_scale == 1.0) return this;
        Map<T, double[]> result = new HashMap<>();
        for (Map.Entry<T, double[]> entry : o_map.entrySet()) {
            double[] value = entry.getValue();
            double[] newValue = new double[1];
            newValue[0] = value[0] * p_scale;
            result.put(entry.getKey(), newValue);
        }
        return new DoubleMapMultiple<T>(result, false);
    }

    public @Override int hashCode() {
        if (o_hash == 0) {
            o_hash = calculateHash();
        }
        return o_hash;
    }

    private int calculateHash() {
        int hash = 0;
        for (Map.Entry<T, double[]> en : o_map.entrySet()) {
            T key = en.getKey();
            double[] value = en.getValue();
            hash += (Objects.hashCode(key) ^ Arrays.hashCode(value));
        }
        if (hash == 0) {
            // prevent repeated hashCode() calculations for those maps whose hashCode() is zero
            hash = 17;
        }
        return hash;
    }

    public @Override boolean equals(Object p_obj){
        if(this == p_obj) return true;
        if(p_obj instanceof DoubleMapMultiple) {
            DoubleMapMultiple<T> pm = (DoubleMapMultiple<T>) p_obj;

            if (o_map.size() != pm.o_map.size())
                return false;
            for (Map.Entry<T, double[]> en : o_map.entrySet()) {
                T key = en.getKey();
                double[] thisValue = en.getValue();
                double[] thatValue = pm.o_map.get(key);
                if (thisValue == null) {
                    if (thatValue != null)
                        return false;
                    else if (!pm.o_map.containsKey(key))
                        return false;
                }
                else if (!Arrays.equals(thisValue, thatValue))
                    return false;
            }
            return true;
        }
        return false;
    }

    public @Override String toString() {
        StringBuilder res = new StringBuilder(100);
        res.append(toStringPrefix());
        for (Map.Entry<T, double[]> entry : o_map.entrySet()) {
            res.append(entry.getKey()).append('=').append(entry.getValue()[0]);
            res.append(", ");
        }
        res.append('}');
        return res.toString();
    }

    protected String toStringPrefix() { return "M{"; }
}
