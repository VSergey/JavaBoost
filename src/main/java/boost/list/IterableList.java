package boost.list;

import boost.util.iterators.Iterators;

import java.util.*;

public class IterableList<T> implements Iterable<T> {
    private final List<Iterable<T>> o_list;

    public IterableList(List<Iterable<T>> p_list) {
        o_list = p_list;
    }

    public IterableList(Iterable<T> ... p_list) {
        o_list = Arrays.asList(p_list);
    }

    public Iterator<T> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<T> {

        private int o_index;
        private Iterator<T> o_iterator;

        private ListIterator() {
            o_index = 0;
            nextIterable();
        }

        public boolean hasNext() {
            if (o_iterator.hasNext())
                return true;
            nextIterable();
            return o_iterator.hasNext();
        }

        private void nextIterable() {
            while (o_index < IterableList.this.o_list.size()) {
                o_iterator = IterableList.this.o_list.get(o_index ++).iterator();
                if (o_iterator.hasNext())
                    return;
            }
            o_iterator = Iterators.empty();
        }

        public T next() {
            if (o_iterator.hasNext())
                return o_iterator.next();
            nextIterable();
            return o_iterator.next();
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }
}

