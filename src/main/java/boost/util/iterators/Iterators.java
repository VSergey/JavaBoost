package boost.util.iterators;

import boost.util.iterators.GroupingIterator.GroupCriteria;
import boost.util.NotNull;

import java.util.*;

public final class Iterators {
    private Iterators() {
    }

    private static final Iterator<Object> EMPTY = new Iterator<Object>() {
        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    };

    private static final Iterable<Object> EMPTY_ITERABLE = new Iterable<Object>() {
        public Iterator<Object> iterator() {
            return EMPTY;
        }
    };

    public @NotNull static <E> Iterator<E> empty() {
        // all java.util.Collections' empty impls. (EmptySet, EmptyList)
        // create a new empty iterator (on Collections.emptyList().iterator(),
        // Collections.emptySet().iterator(), ... invokations); seems it is
        // better to use a singletone
        return (Iterator<E>)EMPTY;
    }

    public @NotNull static <E> Iterable<E> emptyIterable() {
        return (Iterable<E>)EMPTY_ITERABLE;
    }

    public @NotNull static <E> Iterator<E> singleton(final E p_obj) {
        return new Iterator<E>() {
            private E o_obj = p_obj;

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext() {
                return o_obj != null;
            }

            public E next() {
                if (o_obj == null)
                    throw new NoSuchElementException();
                final E x_obj = o_obj;
                o_obj = null;
                return x_obj;
            }
        };
    }

    public @NotNull static <E> Iterator<E> twoValues(final E p_obj1, final E p_obj2) {
        return new Iterator<E>() {
            private int i = 0;

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext() {
                return i < 2;
            }

            public E next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                E result = i==0? p_obj1 : p_obj2;
                i++;
                return result;
            }
        };
    }

    public static <E> Iterator<E> composite(final E p_head, final Iterator<? extends E> p_tail) {
        return new Iterator<E>() {
            private E o_obj = p_head;

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext() {
                return o_obj != null || p_tail.hasNext();
            }

            public E next() {
                if (o_obj != null) {
                    final E x_obj = o_obj;
                    o_obj = null;
                    return x_obj;
                }
                return p_tail.next();
            }
        };
    }

    public static <E> Iterator<E> composite(final Iterator<? extends E> p_head,
                                            final Iterator<? extends E> p_tail) {
        return new Iterator<E>() {
            public boolean hasNext() {
                return p_head.hasNext() || p_tail.hasNext();
            }

            public E next() {
                if (p_head.hasNext())
                    return p_head.next();

                return p_tail.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <E> Iterator<E> composite(Iterable<Iterator<E>> p_iterators) {
        return new CompositeIterator<E>(p_iterators);
    }

    public static <A> Iterator<A> filter(final Iterator<A> p_iterator, final IFilteringFunction<A> p_filter) {
        return new Iterator<A>() {
            A o_current;
            void findNext() {
                while(o_current == null && p_iterator.hasNext()) {
                    o_current = p_iterator.next();
                    if (!p_filter.accept(o_current))
                        o_current = null;
                }
            }
            public boolean hasNext() {
                findNext();
                return o_current != null;
            }
            public A next() {
                findNext();
                A next = o_current;
                o_current = null;
                return next;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <A,B> Iterator<B> map(final Iterator<A> p_iterator, final IMappingFunction<A, B> p_func) {
        Iterator<B> it = new Iterator<B>() {
            public boolean hasNext() {
                return p_iterator.hasNext();
            }
            public B next() {
                return p_func.apply(p_iterator.next());
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public static <A,B> Iterable<B> map(final Iterable<A> p_iterable, final IMappingFunction<A, B> p_func) {
        return new Iterable<B>() {
            public Iterator<B> iterator() {
                return map(p_iterable.iterator(), p_func);
            }
        };
    }

    public static <A,B> Iterator<B> flatMap(final Iterator<A> p_iterator, final IFlatMapFunction<A,B> p_func) {
        if (!p_iterator.hasNext())
            return Collections.<B>emptyList().iterator();

        Iterator<B> it = new Iterator<B> () {
            B o_current;
            Iterator<B> o_current_it;
            void findNext() {
                while(o_current == null) {
                    while(o_current_it == null && p_iterator.hasNext()) {
                        o_current_it = p_func.apply(p_iterator.next()).iterator();
                    }
                    if (o_current_it == null)
                        break;

                    while(o_current == null && o_current_it.hasNext())
                        o_current = o_current_it.next();

                    if (o_current == null)
                        o_current_it = null;
                }
            }
            public boolean hasNext() {
                findNext();
                return o_current != null;
            }
            public B next() {
                findNext();
                B next = o_current;
                o_current = null;
                return next;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
        return it;
    }

    public static <E> Iterator<E> composite(Iterator<Iterator<E>> p_iterators) {
        return new CompositeIterator<E>(p_iterators);
    }

    public static <E> Iterator<E> flat(Iterable<? extends Iterable<E>> p_iterators) {
        return new FlatIterator<E>(p_iterators);
    }

    private static <E> Iterator<E> construct(Iterator<E>[] p_iters, int p_from, int p_to, Comparator<E> p_comparator) {
        if (p_to <= p_from)
            return Collections.<E>emptyList().iterator();
        if (p_from + 1 == p_to)
            return p_iters[p_from];

        int mid = (p_from + p_to) >>> 1;
        return new OrderedMergeIterator<E>(
                construct(p_iters, p_from, mid, p_comparator),
                construct(p_iters, mid, p_to, p_comparator),
                p_comparator);

    }

    public static <E> Iterator<E> ordered(List<Iterator<E>> p_iterators, final Comparator<E> p_comparator, final boolean p_unique) {
        Iterator<E>[] array = p_iterators.toArray(new Iterator[p_iterators.size()]);
        Iterator<E> result = construct(array, 0, array.length, p_comparator);
        if (p_unique)
            result = new UniqueIterator<E>(result, p_comparator);
        return result;
    }

    public static <E> Iterable<E> unmodifiable(final Iterable<? extends E> p_it) {
        return new Iterable<E>() {
            public Iterator<E> iterator() {
                return unmodifiable(p_it.iterator());
            }
        };
    }

    public static <E> Iterator<E> unmodifiable(final Iterator<? extends E> p_itr) {
        return new Iterator<E>() {
            public boolean hasNext() {
                return p_itr.hasNext();
            }
            public E next() {
                return p_itr.next();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <E> Iterator<Iterator<E>> groupBy(Iterator<E> p_it, GroupCriteria<E> p_criteria) {
        return new GroupingIterator<E>(p_it, p_criteria);
    }

    public static <E> Iterator<E> sort(Iterator<E> p_it, Comparator<E> p_comparator, E[] p_arr) {
        E[] arr = toArray(p_it, p_arr);
        Arrays.sort(arr, p_comparator);
        return new ArrayIterator<E>(arr);
    }

    public static <E extends Comparable<E>> Iterator<E> sort(Iterator<E> p_it, E[] p_arr) {
        E[] arr = toArray(p_it, p_arr);
        Arrays.sort(arr);
        return new ArrayIterator<E>(arr);
    }

    public static <E> E[] toArray(Iterator<E> p_it, E[] p_arr) {
        List<E> result = new ArrayList<E>();
        while (p_it.hasNext()) {
            result.add(p_it.next());
        }
        return result.toArray(p_arr);
    }

    public static <E> Object[] toArray(Iterator<E> p_it) {
        List<E> result = new ArrayList<E>();
        while (p_it.hasNext()) {
            result.add(p_it.next());
        }
        return result.toArray();
    }

    public static <E> List<E> toList(Iterator<E> p_it) {
        List<E> result = new ArrayList<>();
        while (p_it.hasNext())
            result.add(p_it.next());
        return result;
    }

}
