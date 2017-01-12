package boost.util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class AbstractFlatIterator<E> implements Iterator<E> {
    protected Iterator<E> o_current;

    public abstract Iterator<E> nextIterator();

    private void findNext() {
        while (o_current == null || !o_current.hasNext()) {
            o_current = nextIterator();
            if (o_current == null)
                break;
        }
    }

    public boolean hasNext() {
        findNext();
        return o_current != null;
    }

    public E next() {
        findNext();
        if (o_current == null)
            throw new NoSuchElementException();
        E element = o_current.next();
        if (!o_current.hasNext())
            o_current = null;
        return element;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
