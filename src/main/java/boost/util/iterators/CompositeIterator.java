package boost.util.iterators;

import java.util.Iterator;

public class CompositeIterator<E> extends AbstractFlatIterator<E> {
    private final Iterator<Iterator<E>> o_iterators;

    public CompositeIterator(Iterable<Iterator<E>> p_iterators) {
        o_iterators = p_iterators.iterator();
    }

    public CompositeIterator(Iterator<Iterator<E>> p_iterators) {
        o_iterators = p_iterators;
    }


    public Iterator<E> nextIterator() {
        return o_iterators.hasNext() ? o_iterators.next():null;
    }
}
