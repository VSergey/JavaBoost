package boost.util.iterators;

/**
 * An {@link java.util.Iterator iterator} implementation to iterate
 * through arrays of various types.
 */
public class ArrayIterator<T> extends AbstractArrayIterator<T, T> {
    public ArrayIterator(T[] p_elements) {
        super(p_elements);
    }

    protected T get(T p_element) {
        return p_element;
    }
}
