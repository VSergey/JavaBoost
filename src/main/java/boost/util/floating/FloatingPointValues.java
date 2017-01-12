package boost.util.floating;

public class FloatingPointValues {

    private FloatingPointValues() {
    }

    private static final double ROUNDING_ADDENDUM = 0.5;
    private static final int POWERS_LENGTH = 16;
    private static final double[] POSITIVE_POWERS;
    private static final double[] NEGATIVE_POWERS;
    static {
        POSITIVE_POWERS = new double[POWERS_LENGTH];
        NEGATIVE_POWERS = new double[POWERS_LENGTH];
        double p = 1.0;
        double n = 1.0;
        for (int i = 0; i < POWERS_LENGTH; i++) {
            POSITIVE_POWERS[i] = p;
            NEGATIVE_POWERS[i] = n;
            p *= 10.0;
            n /= 10.0;
        }
    }

    /**
     * There can be different double NaN values:
     * - signalling; has a mantissa from 0x0000000000001 to 0x7ffffffffffff and
     *   a sign bit either 0 or 1;
     * - indeterminate; has a mantissa of 0x8000000000000 and a sign bit of 1
     * - quiet; has a mantissa from 0x8000000000000 to 0xfffffffffffff when the
     *   sign bit is 0, and from 0xfffffffffffff to 0x8000000000001 when the
     *   latter is 1
     * i.e., all NaN values have a non-zero mantissa, a maximum exponent (0x7ff),
     * and a sign bit either 0 or 1.  There is no need to distinguish them, so,
     * suppose all NaN values have a hash code that can be obtained by XORing two
     * halves of the indeterminate.
     */
    private static final int NAN_HASH = 0xfff80000;
    /**
     * Highest 4 bytes of the negative zero (-0.0).
     */
    private static final int ZERO_HASH = 0x80000000;

    /**
     * Checks if the two specified <code>double</code> values are within
     * the specified accuracy.  This method is NON-TRANSITIVE, i.e. if
     * X = Y, Y = Z, then not necesserily X = Z.
     *
     * @param p_first
     * @param p_second
     * @param p_exp must be a positive value
     * @return a result of comparison
     *
     * @throws IllegalArgumentException if some unacceptable accuracy has
     * been specified
     */
    public static boolean closeEnough(double p_first, double p_second, int p_exp) {
        if (p_first == p_second)
            // two equal doubles; also -0.0 and 0.0, two infinities of the same sign
            return true;

        if (Double.isNaN(p_first))
            return Double.isNaN(p_second);
        if (Double.isNaN(p_second))
            return false;

        final double accuracy = NEGATIVE_POWERS[p_exp];
        // if hit on two infinities having different signs, their difference will be NaN,
        // and thus a result will be false
        return Math.abs(p_first - p_second) < accuracy;
    }

    /**
     * Checks if the two specified <code>double</code> values are equal
     * when appropriately rounded.
     *
     * @param p_first
     * @param p_second
     * @param p_exp must be between 0 and 15 inclusively
     * @return a result of comparison
     *
     * @throws ArrayIndexOutOfBoundsException if the <code>p_exp</code> is
     * not within the allowable range
     */
    public static boolean roundedEqual(double p_first, double p_second, int p_exp) {
        if (p_first == p_second)
            // two equal doubles; also -0.0 and 0.0, two infinities of the same sign
            return true;

        if (Double.isNaN(p_first))
            return Double.isNaN(p_second);
        if (Double.isNaN(p_second))
            return false;

        final double p = POSITIVE_POWERS[p_exp];
        final double first = Math.floor(p_first * p + ROUNDING_ADDENDUM);
        final double second = Math.floor(p_second * p + ROUNDING_ADDENDUM);

        // two questionable situations arise here:
        //
        // 1. either p_first or p_second is infinity and the other is a finite number of the same sign;
        //    if the finite number gives an infinity, a result of comparison will be true, while the
        //    original numbers are not equal under any approximation
        // 2. same as in 1., but both of the numbers are finite and have the same sign
        //
        // I do believe such situations must be rare, so we can suppose all is OK
        return first == second;
    }

    /**
     * Calculates a hash code of the specified <code>double</code> value
     * when the latter is appropriately rounded.
     *
     * @param p_value
     * @param p_exp must be between 0 and 15 inclusively
     * @return the resulting hash code
     *
     * @throws ArrayIndexOutOfBoundsException if the <code>p_exp</code> is
     * not within the allowable range
     */
    public static int roundedHashCode(double p_value, int p_exp) {
        if (p_value == 0.0)
            // both 0.0 and -0.0
            return ZERO_HASH;

        if (Double.isNaN(p_value))
            return NAN_HASH;

        double p = POSITIVE_POWERS[p_exp];
        double value = Math.floor(p_value * p + ROUNDING_ADDENDUM);
        long bits = Double.doubleToRawLongBits(value);
        return (int)(bits ^ (bits >>> 32));
    }

    /**
     * Compares two {@code double} values numerically.  All {@code NaN} values
     * are assumed to be indistinguishable.
     */
    public static boolean numericallyEqual(double p_a, double p_b) {
        if (p_a == p_b)
            return true;

        // either two distinct reals or at least one of the numbers is NaN; anyway,
        // true will be returned only if both of the numbers are NaNs
        return Double.isNaN(p_a) && Double.isNaN(p_b);
    }

    /**
     * Compares two {@code double}s numerically.  Same as {@link Double#compare(double, double)},
     * but considers -0.0 and 0.0 to be equal.
     */
    public static int compareNumerically(double p_a, double p_b) {
        if (p_a < p_b)
            return -1;
        else if (p_a == p_b)
            // two equal doubles; also -0.0 and 0.0, two infinities of the same sign
            return 0;
        else if (p_a > p_b)
            return 1;
        else if (Double.isNaN(p_a))
            return Double.isNaN(p_b) ? 0 : 1;
        else
            return -1; // p_b is NaN (indeterminate) which is greater than any non-NaN value
    }

    public static boolean isInvalid(double p_value) {
        return Double.isInfinite(p_value) || Double.isNaN(p_value);
    }

    public static double round(double p_value,int n){
        if(n == -1) {
            return p_value;
        }
        if(n < -1) {
            int absN = (int) Math.round(Math.pow(0.1, n+1));
            return Math.floor((p_value + (double)absN / 2) / absN) * absN;
        } else {
            int sign = p_value > 0 ? 1 : -1;
            return Double.isNaN(p_value) ? Double.NaN : sign * Math.round((Math.abs(p_value) + 1E-10) * Math.round(Math.pow(10, n))) /
                    (double)Math.round(Math.pow(10, n));
        }
    }
}
