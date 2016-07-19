package boost.util.iterators;

import boost.util.iterators.GroupingIterator.GroupCriteria;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

public class TestIterators extends TestCase {
    public TestIterators(String p_name) {
        super(p_name);
    }

    public static TestSuite suite() {
        return new TestSuite(TestIterators.class);
    }

    private static Object[] toArray(Iterator<?> p_it) {
        final List<Object> values = new ArrayList<>();

        while (p_it.hasNext())
            values.add(p_it.next());

        return values.toArray(new Object[values.size()]);
    }

    private static void compare(Object[] p_arr, Iterator<?> p_it) {
        final Object[] arr = toArray(p_it);
        assertTrue("expected: " + Arrays.toString(p_arr) +
                        ", actual: " + Arrays.toString(arr),
                Arrays.equals(p_arr, arr));
    }

    private static void compare(Object[] p_arr1, Object[] p_arr2) {
        assertTrue("expected: " + Arrays.toString(p_arr1) +
                        ", actual: " + Arrays.toString(p_arr2),
                Arrays.equals(p_arr1, p_arr2));
    }

    public void testCompositeIterator() {
        Object[] arr1 = new Object[] {1,2,3};
        Object[] arr2 = new Object[] {};
        Object[] arr3 = new Object[] {4,5};

        List<Iterator<Object>> iters = new ArrayList<>();
        iters.add(new ArrayIterator<>(arr1));
        iters.add(new ArrayIterator<>(arr2));
        iters.add(new ArrayIterator<>(arr3));

        compare(new Object[] {1,2,3,4,5}, Iterators.composite(iters));

        compare(new Object[] {},
                Iterators.composite(Collections.<Iterator<Object>>singletonList(new ArrayIterator<>(new Object[]{})))
        );
    }

    public void testOrdering() {
        // test comparator order
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer p_o1, Integer p_o2) {
                return p_o1 - p_o2;
            }
        };
        ArrayList<Iterator<Integer>> iters = new ArrayList<>();
        iters.add(Arrays.asList(new Integer[] {1,2,3,5,null,6}).iterator());
        iters.add(Arrays.asList(new Integer[] {}).iterator());
        iters.add(Arrays.asList(new Integer[] {4,5}).iterator());
        iters.add(Arrays.asList(new Integer[] {1,10}).iterator());

        compare(new Object[] {1,1,2,3,4,5,5,6,10}, Iterators.ordered(iters, comparator, false));

        comparator = new Comparator<Integer>() {
            public int compare(Integer p_o1, Integer p_o2) {
                return p_o2 - p_o1;
            }
        };
        iters = new ArrayList<>();
        iters.add(Arrays.asList(new Integer[] {6,2,null,1}).iterator());
        iters.add(Arrays.asList(new Integer[] {}).iterator());
        iters.add(Arrays.asList(new Integer[] {5,4}).iterator());
        iters.add(Arrays.asList(new Integer[] {10, 1}).iterator());

        compare(new Object[] {10,6,5,4,2,1,1}, Iterators.ordered(iters, comparator, false));

        iters = new ArrayList<>();
        compare(new Object[] {}, Iterators.ordered(iters, comparator, false));

        iters = new ArrayList<>();
        iters.add(Arrays.asList(new Integer[] {1}).iterator());

        compare(new Object[] {1}, Iterators.ordered(iters, comparator, false));
    }

    public void testGrouping() {
        GroupCriteria<String> criteria = new GroupCriteria<String>() {
            public Object groupBy(String p_element) {
                return p_element.substring(0, 2);
            }
        };

        Iterator<Iterator<String>> groupByIt =
                Iterators.groupBy(Arrays.asList(new String[] {"ppa","ppb","ppc","pac","paa","aa"}).iterator(), criteria);

        assertTrue(groupByIt.hasNext());
        compare(new Object[] {"ppa","ppb","ppc"}, groupByIt.next());
        assertTrue(groupByIt.hasNext());
        compare(new Object[] {"pac","paa"}, groupByIt.next());
        assertTrue(groupByIt.hasNext());
        compare(new Object[] {"aa"}, groupByIt.next());

        assertTrue(!groupByIt.hasNext());

        groupByIt =
                Iterators.groupBy(Arrays.asList(new String[] {"ppa","ppb","ppc","pac","paa","aa"}).iterator(), criteria);

        assertTrue(groupByIt.hasNext());
        groupByIt.next();
        assertTrue(groupByIt.hasNext());
        compare(new Object[] {"pac","paa"}, groupByIt.next());
        assertTrue(groupByIt.hasNext());
        compare(new Object[] {"aa"}, groupByIt.next());

        assertTrue(!groupByIt.hasNext());
    }

    public void testToArray() {
        Integer[] array = Iterators.toArray(Arrays.asList(new Integer[] {6,2,null,1}).iterator(), new Integer[] {});
        compare(new Object[] {6,2,null,1}, array);

        Object[] array1 = Iterators.toArray(Arrays.asList(new Object[] {6,2,null,1}).iterator());
        compare(new Object[] {6,2,null,1}, array1);
    }

    public void testSorting() {
        Iterator<Integer> it = Arrays.asList(1,3,2,7,5,4,9,8,6).iterator();
        compare(new Object[] {1,2,3,4,5,6,7,8,9}, Iterators.sort(it, new Integer[] {}));
    }

    private static <E> List<E> toList(Iterator<E> p_it) {
        List<E> result = new ArrayList<>();
        while (p_it.hasNext())
            result.add(p_it.next());
        return result;
    }

    public void testOrderedMergeIterator() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Iterator<Integer> left = new ArrayIterator<>(new Integer[]{1, 3, 5, 7, 9});
        Iterator<Integer> right = new ArrayIterator<>(new Integer[]{0, 1, 2, 3, 4, 10});
        assertEquals(Arrays.asList(new Integer[] { 0, 1, 1, 2, 3, 3, 4, 5, 7, 9, 10 }),
                toList(new OrderedMergeIterator<>(left, right, comparator)));

        left = Collections.<Integer>emptyList().iterator();
        right = new ArrayIterator<>(new Integer[]{0});
        assertEquals(Arrays.asList(new Integer[] { 0 }),
                toList(new OrderedMergeIterator<>(left, right, comparator)));

        left = new ArrayIterator<>(new Integer[]{0});
        right = Collections.<Integer>emptyList().iterator();
        assertEquals(Arrays.asList(new Integer[] { 0 }),
                toList(new OrderedMergeIterator<>(left, right, comparator)));

        left = Collections.<Integer>emptyList().iterator();
        right = Collections.<Integer>emptyList().iterator();
        assertEquals(Collections.<Integer>emptyList(),
                toList(new OrderedMergeIterator<>(left, right, comparator)));
    }

    public void testUniqueIterator() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        assertEquals(Collections.<Integer>emptyList(),
                toList(new UniqueIterator<>(Collections.<Integer>emptyList().iterator(), comparator)));
        assertEquals(Collections.singletonList(1),
                toList(new UniqueIterator<>(Collections.singletonList(1).iterator(), comparator)));
        assertEquals(Collections.singletonList(1),
                toList(new UniqueIterator<>(Arrays.asList(new Integer[]{1, 1}).iterator(), comparator)));
        assertEquals(Arrays.asList(new Integer[] { 1, 2 }),
                toList(new UniqueIterator<>(Arrays.asList(new Integer[]{1, 1, 2, 2, 2}).iterator(), comparator)));
    }

    public void testFiltering() {
        Iterator<Integer> it = Arrays.asList(1,1,2,2,3,4,5,6,7,8,9).iterator();
        Iterators.IFilteringFunction<Integer> filter = new Iterators.IFilteringFunction<Integer>() {
            public boolean accept(Integer p_a) {
                return p_a % 2 == 0;
            }
        };

        compare(new Object[] {2,2,4,6,8}, Iterators.filter(it, filter));
    }

    public void testMap() {
        Iterator<Integer> it = Arrays.asList(1,1,2,2,3,4,5,6,null,8,9).iterator();
        Iterators.IMappingFunction<Integer, String> func = new Iterators.IMappingFunction<Integer, String>() {
            public String apply(Integer p_a) {
                return p_a == null ? null : p_a.toString();
            }
        };
        compare(new Object[] {"1","1","2","2","3","4","5","6",null,"8","9"}, Iterators.map(it, func));
    }

    public void testFlatMap() {
        Iterator<Integer> it = Arrays.asList(1,2,3,4,5,6,8,9).iterator();
        Iterators.IFlatMapFunction<Integer, Integer> func = new Iterators.IFlatMapFunction<Integer, Integer>() {
            public Iterable<Integer> apply(Integer p_a) {
                if (p_a == 8)
                    return Collections.emptyList();
                List<Integer> doubled = new ArrayList<>();
                doubled.add(p_a);
                doubled.add(p_a);
                return doubled;
            }
        };
        compare(new Object[] {1,1,2,2,3,3,4,4,5,5,6,6,9,9}, Iterators.flatMap(it, func));
    }

    public void testStackOverflowOnIncrease() {
        int N = 1000000;
        List<Integer> lst = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            lst.add(i);
        }

        Iterators.IFlatMapFunction<Integer, Integer> func = new Iterators.IFlatMapFunction<Integer, Integer>() {
            public Iterable<Integer> apply(Integer p_a) {
                List<Integer> doubled = new ArrayList<>();
                doubled.add(p_a);
                doubled.add(p_a);
                return doubled;
            }
        };
        Iterator<Integer> it = Iterators.flatMap(lst.iterator(), func);
        while(it.hasNext()) {
            it.next();
        }
    }
}
