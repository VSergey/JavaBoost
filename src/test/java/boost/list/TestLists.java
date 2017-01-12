package boost.list;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Sergey.Voronezhtsev on 12/1/2017.
 */
public class TestLists extends TestCase {

    public TestLists(String p_name) {
        super(p_name);
    }

    public void testIterableList() {
        List<String> l1 = Arrays.asList("a","b","c");
        List<String> l2 = Arrays.asList("d","e","f");
        List<String> l3 = Arrays.asList("g","h","i");

        IterableList<String> list = new IterableList(l1, l2, l3);
        StringBuilder builder = new StringBuilder();
        for(String s : list) {
            builder.append(s);
        }
        assertEquals("abcdefghi", builder.toString());
    }

    public void testIntRange() {
        IntRange range = new IntRange(5, 10);
        assertEquals(6, range.size());
        assertTrue(range.contains(6));
        assertTrue(range.contains(new BigInteger("9")));
        assertEquals(range.get(2), new Integer(7));

        StringBuilder builder = new StringBuilder();
        List<Integer> list = new ArrayList<>();
        for(Integer i : range) {
            builder.append(i);
            list.add(i);
        }
        assertEquals("5678910", builder.toString());
        assertTrue(range.containsAll(list));
        list.add(1);
        assertTrue(!range.containsAll(list));

        IntRange range2 = new IntRange(5, 10, true);
        StringBuilder builder2 = new StringBuilder();
        for(Integer i : range2) {
            builder2.append(i);
            list.add(i);
        }
        assertEquals("1098765", builder2.toString());

        List<Integer> subList = range.subList(1, 4);
        StringBuilder builder3 = new StringBuilder();
        for(Integer i : subList) {
            builder3.append(i);
            list.add(i);
        }
        assertEquals("678", builder3.toString());
    }

    public static TestSuite suite() {
        return new TestSuite(TestLists.class);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
        System.exit(0);
    }
}
