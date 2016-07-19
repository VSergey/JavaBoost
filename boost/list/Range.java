package boost.list;

import java.util.List;

public interface Range <T extends Comparable> extends List<T> {
    Comparable getFrom();

    Comparable getTo();

    boolean isReverse();

    boolean containsWithinBounds(Object p_var);
}
