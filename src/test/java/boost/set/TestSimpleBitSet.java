package boost.set;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.util.BitSet;

/**
 * Created by Sergey.Voronezhtsev on 12/1/2017.
 */
public class TestSimpleBitSet extends TestCase {

    public TestSimpleBitSet(String p_name) {
        super(p_name);
    }

    public void test() {
        SimpleBitSet bitSet = new SimpleBitSet();
        bitSet.set(5);
        assertTrue(bitSet.get(5));
        assertTrue(!bitSet.get(3));
        bitSet.not();
        assertTrue(bitSet.bits() == -33);
        bitSet.clear(3);
        assertTrue(bitSet.bits() == -41);
        SimpleBitSet bitSet2 = new SimpleBitSet(0xf);
        SimpleBitSet bitSet3 = new SimpleBitSet();
        bitSet3.set(6).set(7);
        bitSet.xor(bitSet2).or(bitSet3).and(bitSet2);
        assertTrue(bitSet.bits() == 8);
    }

    public static TestSuite suite() {
        return new TestSuite(TestSimpleBitSet.class);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
        System.exit(0);
    }
}
