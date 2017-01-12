package boost.list;

import java.util.*;

public class EmptyRange extends AbstractList implements Range {

    /**
     * The value at which the range originates (may be <code>null</code>).
     */
    private Comparable o_at;

    /**
     * Creates a new {@link EmptyRange}.
     *
     * @param at the value at which the range starts (may be <code>null</code>).
     */
    public EmptyRange(Comparable at) {
        o_at = at;
    }

    /**
     * {@inheritDoc}
     */
    public Comparable getFrom() { return o_at; }

    /**
     * {@inheritDoc}
     */
    public Comparable getTo() { return o_at; }

    /**
     * Never true for an empty range.
     *
     * @return <code>false</code>
     */
    public boolean isReverse() { return false; }

    /**
     * Never true for an empty range.
     *
     * @return <code>false</code>
     */
    public boolean containsWithinBounds(Object o) { return false; }

    public @Override String toString() {
        return (null == o_at)
                ? "null..<null"
                : o_at.toString() + "..<" + o_at.toString();
    }

    /**
     * Always 0 for an empty range.
     *
     * @return 0
     */
    public int size() { return 0; }

    /**
     * Always throws <code>IndexOutOfBoundsException</code> for an empty range.
     *
     * @throws IndexOutOfBoundsException always
     */
    public Object get(int index) {
        throw new IndexOutOfBoundsException("can't get values from Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException always
     */
    public @Override boolean add(Object o) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override boolean addAll(Collection c) {
        throw new UnsupportedOperationException("cannot add to Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override boolean remove(Object o) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override Object remove(int index) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("cannot remove from Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("cannot retainAll in Empty Ranges");
    }

    /**
     * Always throws <code>UnsupportedOperationException</code> for an empty range.
     *
     * @throws UnsupportedOperationException
     */
    public @Override Object set(int index, Object element) {
        throw new UnsupportedOperationException("cannot set in Empty Ranges");
    }
}
