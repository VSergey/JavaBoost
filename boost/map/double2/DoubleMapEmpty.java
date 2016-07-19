package boost.map.double2;

import java.util.*;

public class DoubleMapEmpty<T> implements DoubleMap<T> {

    DoubleMapEmpty() {}

    public boolean containsKey(T p_key)         { return false; }
    public double get(T p_key)                  { return 0; }
    public int size()                           { return 0; }
    public boolean isEmpty()                    { return true; }
    public boolean isZero(double p_precision)   { return true; }
    public T getAnyKey()                        { return null; }

    public Set<T> keySet()                      { return Collections.emptySet(); }
    public Map<T, Double> convertToMap()        { return Collections.emptyMap(); }
    public DoubleMap<T> scale(double p_scale)   { return this; }
    public DoubleMap<T> getNonZero(double p_precision) { return this; }

    public @Override int hashCode()             { return 0; }
    public @Override boolean equals(Object p_obj){
        return this == p_obj || p_obj instanceof DoubleMapEmpty;
    }
    public @Override String toString()          { return "EMPTY"; }

}
