package boost.util.iterators;

import java.util.*;

public class GroupingIterator<E> implements Iterator<Iterator<E>> {
    private final Iterator<E> o_it;
    private final GroupCriteria<E> o_criteria;
    private E o_current;
    private E o_next;

    public GroupingIterator(Iterator<E> p_it, GroupCriteria<E> p_criteria) {
        o_it = p_it;
        o_criteria = p_criteria;
    }
    private void findNext() {
        Object group = o_current == null ? null : o_criteria.groupBy(o_current);
        while(o_next == null || Objects.equals(group, o_criteria.groupBy(o_next))) {
            if (o_it.hasNext()) {
                o_next = o_it.next();
            } else {
                break;
            }
        }
    }
    public boolean hasNext() {
        findNext();
        return o_next != null;
    }

    public Iterator<E> next() {
        findNext();
        o_current = o_next;
        o_next = null;
        if (o_current == null)
            throw new NoSuchElementException();
        return new SubIterator(o_criteria.groupBy(o_current));
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public interface GroupCriteria<E> {
        Object groupBy(E p_element);
    }

    private class SubIterator implements Iterator<E> {
        private final Object o_group;
        private boolean o_is_obsolete;

        SubIterator(Object p_group) {
            o_group = p_group;
        }
        private void findNext() {
            while(o_current == null) {
                if (o_it.hasNext())
                    o_current = o_it.next();
                else
                    break;
            }
            if (o_current == null || !o_group.equals(o_criteria.groupBy(o_current))) {
                o_is_obsolete = true;
                o_next = o_current;
                o_current = null;
            }
        }

        public boolean hasNext() {
            findNext();
            return !o_is_obsolete;
        }

        public E next() {
            findNext();
            if (o_is_obsolete)
                throw new NoSuchElementException();
            E e = o_current;
            o_current = null;
            return e;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
