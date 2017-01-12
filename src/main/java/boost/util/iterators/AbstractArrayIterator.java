package boost.util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class AbstractArrayIterator<E, V> implements Iterator<V> {
    protected final E[] o_elements;
    protected int o_index;
    protected E o_current;

    protected AbstractArrayIterator(E[] p_elements) {
        if (p_elements == null)
            throw new NullPointerException();

        o_elements = p_elements;
    }

    public final boolean hasNext() {
        if (o_current == null)
            findNext();

        return o_current != null;
    }

    private void findNext() {
        while (o_current == null && o_index < o_elements.length) {
            o_current = o_elements[o_index++];

            if (o_current == null)
                onNullElement();
            else if (!accept(o_current))
                o_current = null;
        }
    }

    protected void onNullElement() {
        // by default, check the next,
        // if any, element
    }

    protected boolean accept(E p_element) {
        // by default, accept all
        // non-null elements
        return true;
    }

    protected void onNextInvocation() {
        // by default, do nothing
    }

    protected abstract V get(E p_element);

    public final V next() {
        if (o_current == null) {
            findNext();
            if (o_current == null)
                throw new NoSuchElementException();
        }

        onNextInvocation();

        E element = o_current;
        o_current = null;

        return get(element);
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
