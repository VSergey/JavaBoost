package boost.util.iterators;

import java.util.*;

/**
 * Merges two ordered iterators into a single one.
 */
public class OrderedMergeIterator<E> implements Iterator<E> {
    private final Iterator<E> o_left_it;
    private final Iterator<E> o_right_it;
    private final Comparator<E> o_comparator;

    /** Becomes null only when o_left_it is exhausted */
    private E o_left;

    /** Becomes null only when o_right_it is exhausted */
    private E o_right;

    public OrderedMergeIterator(Iterator<E> p_left_it, Iterator<E> p_right_it, Comparator<E> p_comparator) {
        o_left_it = p_left_it;
        o_right_it = p_right_it;
        o_comparator = p_comparator;

        o_left = fetch(o_left_it);
        o_right = fetch(o_right_it);
    }

    /** Next element from the iterator or null on end-of-stream;
     * skips nulls in the original iterator */
    private E fetch(Iterator<E> p_it) {
        while (p_it.hasNext()) {
            E value = p_it.next();
            if (value != null)
                return value;
        }
        return null;
    }

    private E tickLeft() {
        E value = o_left;
        o_left = fetch(o_left_it);
        return value;
    }

    private E tickRight() {
        E value = o_right;
        o_right = fetch(o_right_it);
        return value;
    }

    public boolean hasNext() {
        return o_left != null || o_right != null;
    }

    public E next() {
        E result;
        if (o_left != null) {
            if (o_right != null) {
                if (o_comparator.compare(o_left, o_right) <= 0)
                    result = tickLeft();
                else
                    result = tickRight();
            }
            else result = tickLeft();
        }
        else if (o_right != null) result = tickRight();
        else throw new NoSuchElementException();

        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
