package boost.map.double2;

import boost.util.NotNull;

import java.util.Map;
import java.util.Set;

public interface DoubleMap<T> {
    @NotNull Set<T> keySet();
    T getAnyKey();

    double get(T p_key);

    int size();

    boolean containsKey(T p_key);
    boolean isEmpty();
    boolean isZero(double p_precision);

    @NotNull DoubleMap<T> getNonZero(double p_precision);
    @NotNull DoubleMap<T> scale(double p_scale);
    @NotNull Map<T, Double> convertToMap();
}
