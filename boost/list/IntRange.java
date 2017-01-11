package boost.list;

import java.math.BigInteger;
import java.util.*;

public class IntRange extends AbstractList<Integer> implements Range<Integer> {
    private int o_from;
    private int o_to;
    private boolean o_reverse;

    /**
     * Creates a new <code>IntRange</code>. If <code>from</code> is greater
     * than <code>to</code>, a reverse range is created with
     * <code>from</code> and <code>to</code> swapped.
     *
     * @param p_from the first number in the range.
     * @param p_to   the last number in the range.
     * @throws IllegalArgumentException if the range would contain more than
     *                                  {@link Integer#MAX_VALUE} values.
     */
    public IntRange(int p_from, int p_to) {
        if(p_from > p_to) {
            o_from = p_to;
            o_to = p_from;
            o_reverse = true;
        } else {
            o_from = p_from;
            o_to = p_to;
        }
        if(o_to - o_from >= 2147483647) {
            throw new IllegalArgumentException("range must have no more than 2147483647 elements");
        }
    }

    /**
     * Creates a new <code>IntRange</code>.
     *
     * @param p_from    the first value in the range.
     * @param p_to      the last value in the range.
     * @param p_reverse <code>true</code> if the range should count from
     *                <code>to</code> to <code>from</code>.
     * @throws IllegalArgumentException if <code>from</code> is greater than <code>to</code>.
     */
    protected IntRange(int p_from, int p_to, boolean p_reverse) {
        if (p_from > p_to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }

        o_from = p_from;
        o_to = p_to;
        o_reverse = p_reverse;
    }

    public @Override boolean equals(Object p_that) {
        return p_that instanceof IntRange?
                this.equals((IntRange)p_that):
                super.equals(p_that);
    }

    public boolean equals(IntRange p_that) {
        return p_that != null &&
                o_reverse == p_that.o_reverse &&
                o_from == p_that.o_from &&
                o_to == p_that.o_to;
    }

    public Integer getFrom() { return o_from; }
    public Integer getTo() { return o_to; }

    public int getFromInt() { return o_from; }
    public int getToInt() { return o_to; }

    public boolean isReverse() { return o_reverse; }

    public boolean containsWithinBounds(Object o) {
        return contains(o);
    }

    public Integer get(int index) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " should not be negative");
        } else if(index >= this.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + " too big for range: " + this);
        }
        return o_reverse?o_to - index:index + o_from;
    }

    public int size() {
        return o_to - o_from + 1;
    }

    public @Override Iterator<Integer> iterator() {
        return new IntRange.IntRangeIterator();
    }

    public @Override List<Integer> subList(int p_fromIndex, int p_toIndex) {
        if(p_fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + p_fromIndex);
        } else if(p_toIndex > this.size()) {
            throw new IndexOutOfBoundsException("toIndex = " + p_toIndex);
        } else if(p_fromIndex > p_toIndex) {
            throw new IllegalArgumentException("fromIndex(" + p_fromIndex + ") > toIndex(" + p_toIndex + ")");
        } else {
            return (List<Integer>)(p_fromIndex == p_toIndex?
                    new EmptyRange(o_from):
                    new IntRange(p_fromIndex + o_from, p_toIndex + o_from - 1, o_reverse));
        }
    }

    public @Override String toString() {
        return o_reverse?"" + o_to + ".." + o_from:"" + o_from + ".." + o_to;
    }

    public @Override boolean contains(Object value) {
        if(value instanceof Integer) {
            Integer bigint1 = (Integer)value;
            int i = bigint1.intValue();
            return i >= o_from && i <= o_to;
        } else if(!(value instanceof BigInteger)) {
            return false;
        } else {
            BigInteger bigint = (BigInteger)value;
            return bigint.compareTo(BigInteger.valueOf((long)o_from)) >= 0 &&
                    bigint.compareTo(BigInteger.valueOf((long)o_to)) <= 0;
        }
    }

    public @Override boolean containsAll(Collection other) {
        if (!(other instanceof IntRange)) {
            return super.containsAll(other);
        }
        IntRange range = (IntRange) other;
        return o_from <= range.o_from && range.o_to <= o_to;
    }

    /**
     * Iterates through each number in an <code>IntRange</code>.
     */
    private class IntRangeIterator implements Iterator<Integer> {
        /**
         * Counts from 0 up to size - 1.
         */
        private int o_index;

        /**
         * The number of values in the range.
         */
        private int o_size = size();

        /**
         * The next value to return.
         */
        private int o_value = o_reverse ? o_to : o_from;

        /**
         * {@inheritDoc}
         */
        public boolean hasNext() {
            return o_index < o_size;
        }

        /**
         * {@inheritDoc}
         */
        public Integer next() {
            if (o_index++ > 0) {
                if (o_index > o_size) {
                    return null;
                } else {
                    if (o_reverse) {
                        --o_value;
                    } else {
                        ++o_value;
                    }
                }
            }
            return o_value;
        }

        /**
         * Not supported.
         *
         * @throws java.lang.UnsupportedOperationException
         *          always
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
