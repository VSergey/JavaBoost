package boost.util.iterators;

import java.util.*;

/**
 * Filters an ordered iterator to return only unique elements
 */
public class UniqueIterator<E> implements Iterator<E> {
    private final Iterator<E> o_it;
    private final Comparator<E> o_comparator;

    /** The next element to return from {@link #next()} or null if
     * end of {@link #o_it} is reached */
    private E o_next;

    public UniqueIterator(Iterator<E> p_it, Comparator<E> p_comparator) {
        o_it = p_it;
        o_comparator = p_comparator;

        o_next = o_it.hasNext() ? o_it.next() : null;
    }

    public boolean hasNext() {
        return o_next != null;
    }

    public E next() {
        if (o_next == null)
            throw new NoSuchElementException();

        // save current element as last returned...
        E last = o_next;

        // ...and find the next element to return
        for (;;) {
            if (!o_it.hasNext()) {
                o_next = null;
                break;
            }
            o_next = o_it.next();
            assert o_next != null;
            if (o_comparator.compare(last, o_next) != 0)
                break;
        }

        return last;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
