package boost.map.double2;

import boost.util.floating.FloatingPointValues;

import java.util.*;
import java.util.Map.Entry;

public class DoubleCollector<T> extends DoubleMapMultiple<T> {

    public DoubleCollector() {
        super(new HashMap<T, double[]>());
    }

    public DoubleCollector(int p_size) {
        super(new HashMap<T, double[]>(p_size));
    }

    public DoubleCollector(Map<T, double[]> p_map) {
        super(p_map);
    }

    public DoubleCollector(boolean p_ordered){
        super(p_ordered?new TreeMap<T, double[]>():new HashMap<T, double[]>());
    }

    public DoubleCollector(DoubleMap<T> p_map) {
        this(p_map.size());
        addAll(p_map);
    }

    public DoubleCollector(T p_key, double p_value) {
        super(new HashMap<T, double[]>());
        add(p_key, p_value);
    }

    public DoubleCollector<T> add(T p_key, double p_value){
        add(p_key, o_map.get(p_key), p_value);
        return this;
    }

    public DoubleCollector<T> addAll(DoubleMap<T> p_map){
        for (T key : p_map.keySet()) {
            add(key, p_map.get(key));
        }
        return this;
    }

    public DoubleCollector<T> addAll(DoubleMap<T> p_map, double p_scale){
        for (T key : p_map.keySet()) {
            add(key, p_map.get(key) * p_scale);
        }
        return this;
    }

    public DoubleCollector<T> addForAll(double p_value){
        for (T key : keySet()) {
            add(key, p_value);
        }
        return this;
    }

    public DoubleCollector<T> multiply(double p_value){
        for (double[] value : o_map.values())
            value[0] *= p_value;
        return this;
    }

    public DoubleCollector<T> round(int p_n) {
        for (double[] value : o_map.values())
            value[0] = FloatingPointValues.round(value[0], p_n);
        return this;
    }

    /**
     * Compact memory allocation if possible
     * @return new instance if can compact
     */
    public DoubleMap<T> compact() {
        return DoubleMapFactory.make(o_map);
    }

    public void put(T p_key, double p_newValue) {
        double[] previousValue = o_map.get(p_key);
        if(previousValue == null) o_map.put(p_key, previousValue = new double[1]);
        previousValue[0] = p_newValue;
    }

    public void remove(T p_key) {
        double[] previousValue = o_map.get(p_key);
        if(previousValue == null) return;
        previousValue[0] = 0d;
    }

    public void clear() { o_map.clear(); }

    public @Override DoubleMap<T> getNonZero(double p_precision) {
        return makeNonZero(p_precision);
    }

    public @Override DoubleMap<T> scale(double p_scale) {
        return scaleCollector(p_scale).compact();
    }

    public DoubleCollector<T> scaleCollector(double p_scale) {
        if(p_scale == 1.0) return this;
        return new DoubleCollector<T>().addAll(this, p_scale);
    }

    public double removeLowerAbsThen(double p_value) {
        double deletedSum = 0;
        for (Iterator<Entry<T, double[]>> it = o_map.entrySet().iterator(); it.hasNext(); ) {
            Entry<T, double[]> entry = it.next();
            double val = entry.getValue()[0];
            if (Math.abs(val) < p_value) {
                deletedSum += val;
                it.remove();
            }
        }
        return deletedSum;
    }

    public void normalize(double p_total) {
        for (Entry<T, double[]> entry : o_map.entrySet()) {
            double weight[] = entry.getValue();
            weight[0] /= p_total;
        }
    }

    private void add(T p_key, double[] p_value, double p_addition){
        double[] previousValue = p_value;
        if(p_value == null){
            o_map.put(p_key, previousValue = new double[1]);
        }
        previousValue[0] += p_addition;
    }

    // this is magic value for mutable objects
    public @Override int hashCode() { return 100; }

    protected @Override String toStringPrefix() { return "C{"; }

}
