package boost.list;

import java.util.List;

public interface Range <T extends Comparable> extends List<T> {
    T getFrom();

    T getTo();

    boolean isReverse();

    boolean containsWithinBounds(Object p_var);
}
