package boost.util.iterators;

import java.util.Iterator;

public class FlatIterator<E> extends AbstractFlatIterator<E> {
    private final Iterator<? extends Iterable<E>> o_iterators;

    public FlatIterator(Iterable<? extends Iterable<E>> p_iterators) {
        o_iterators = p_iterators.iterator();
    }

    public Iterator<E> nextIterator() {
        return o_iterators.hasNext() ? o_iterators.next().iterator() : null;
    }
}
