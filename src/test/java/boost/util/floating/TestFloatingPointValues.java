package boost.util.floating;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestFloatingPointValues extends TestCase {
    public TestFloatingPointValues(String p_name) {
        super(p_name);
    }

    public void testHashTableApplicability() {
        // the idea is simple: if we want to use some rounding
        // strategy when implementing the equals(Object) and
        // hashCode() methods, we MUST get two identical hash
        // codes for any two double values which appeared to
        // be equal under this rounding strategy

        assertTrue(123.456789, 123.5, 1);
        assertTrue(123.456789, 123.46, 2);
        assertTrue(123.456789, 123.459, 2);
        assertTrue(123.456789, 123.455000001, 2);

        assertTrue(-317.0009732, -317.0009727012, 6);

        assertTrue(Double.longBitsToDouble(0x0000000000000000L), // positive zero
                Double.longBitsToDouble(0x8000000000000000L), // negative zero
                1);

        assertTrue(Double.NaN, Double.NaN, 1);
        assertTrue(Double.longBitsToDouble(0x7ff1234567890abcL), // some signalling NaN
                Double.longBitsToDouble(0xfff9278513adf45eL), // some quiet NaN
                1);

        assertTrue(!FloatingPointValues.roundedEqual(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.POSITIVE_INFINITY, Double.NaN, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NaN, Double.POSITIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NEGATIVE_INFINITY, Double.NaN, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NaN, Double.NEGATIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NEGATIVE_INFINITY, 123.1234, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.POSITIVE_INFINITY, 123.1234, 1));
        assertTrue(!FloatingPointValues.roundedEqual(Double.NaN, 123.1234, 1));
        assertTrue(!FloatingPointValues.roundedEqual(12.7654, Double.POSITIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(12.7654, Double.NEGATIVE_INFINITY, 1));
        assertTrue(!FloatingPointValues.roundedEqual(12.7654, Double.NaN, 1));

        assertTrue(!FloatingPointValues.roundedEqual(0.0001, -0.0001, 4));
        assertTrue(2.01, 1.99, 1);
        assertTrue(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 15);
        assertTrue(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 15);
    }

    private static void assertTrue(double p_first, double p_second, int p_exp) {
        assertTrue(p_first + " must be equal to " + p_second,
                FloatingPointValues.roundedEqual(p_first, p_second, p_exp));
        assertEquals(p_first + " is said to be equal to " + p_second +
                        ", but they have different hash codes",
                FloatingPointValues.roundedHashCode(p_first, p_exp),
                FloatingPointValues.roundedHashCode(p_second, p_exp));
    }

    public void testNumericalEquality() {
        testNumericalEquality(new Cmp() {
            public boolean eq(double p_a, double p_b) {
                return FloatingPointValues.numericallyEqual(p_a, p_b);
            }
        });
        testNumericalEquality(new Cmp() {
            public boolean eq(double p_a, double p_b) {
                return FloatingPointValues.compareNumerically(p_a, p_b) == 0;
            }
        });
    }

    private interface Cmp {
        boolean eq(double p_a, double p_b);
    }

    private static void testNumericalEquality(Cmp p_cmp) {
        assertTrue(p_cmp.eq(137.0, 137));
        assertTrue(!p_cmp.eq(-295.0, -294.999999999));

        // zeroes
        assertTrue(p_cmp.eq(0,0.0));
        assertTrue(p_cmp.eq(Double.longBitsToDouble(0L), // positive zero
                0));
        assertTrue(p_cmp.eq(Double.longBitsToDouble(0x8000000000000000L), // negative zero
                0));
        assertTrue(p_cmp.eq(Double.longBitsToDouble(0x8000000000000000L), // negative zero
                Double.longBitsToDouble(0L))); // positive zero

        // infinities
        assertTrue(p_cmp.eq(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(p_cmp.eq(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(!p_cmp.eq(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));

        // NaNs
        assertTrue(p_cmp.eq(Double.NaN, Double.NaN));
        assertTrue(p_cmp.eq(Double.NaN, // indeterminate
                Double.longBitsToDouble(0x7ff000DeadBeef00L))); // some signalling NaN
        assertTrue(p_cmp.eq(Double.NaN, // indeterminate
                Double.longBitsToDouble(0xfff8000000000001L))); // some quiet NaN
        assertTrue(p_cmp.eq(Double.longBitsToDouble(0x7ff000DeadBeef00L), // some signalling NaN
                Double.longBitsToDouble(0xfff8000000000001L))); // some quiet NaN

        // various
        assertTrue(!p_cmp.eq(Double.NaN, 0));
        assertTrue(!p_cmp.eq(Double.longBitsToDouble(0x7ff000DeadBeef00L),
                Double.longBitsToDouble(0x8000000000000000L)));
        assertTrue(!p_cmp.eq(Double.NaN, Double.POSITIVE_INFINITY));
        assertTrue(!p_cmp.eq(11, Double.NEGATIVE_INFINITY));
    }

    public static TestSuite suite() {
        return new TestSuite(TestFloatingPointValues.class);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
        System.exit(0);
    }}
