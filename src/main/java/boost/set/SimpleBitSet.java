package boost.set;

public class SimpleBitSet {
    private final static int MAX_SIZE = 64;

    private long o_bits;

    public SimpleBitSet() {
        this(0);
    }

    public SimpleBitSet(int p_bits) {
        o_bits = p_bits;
    }

    public long bits() { return o_bits; }

    public SimpleBitSet set(int p_index) {
        o_bits |= bit(p_index);
        return this;
    }

    public SimpleBitSet clear(int p_index) {
        o_bits &= ~bit(p_index);
        return this;
    }

    public boolean get(int p_index) {
        return (o_bits & bit(p_index)) != 0;
    }

    public void not() {
        o_bits = ~o_bits;
    }

    public SimpleBitSet and(SimpleBitSet p_bs) {
        o_bits &= p_bs.o_bits;
        return this;
    }

    public SimpleBitSet or(SimpleBitSet p_bs) {
        o_bits |= p_bs.o_bits;
        return this;
    }

    public SimpleBitSet xor(SimpleBitSet p_bs) {
        o_bits ^= p_bs.o_bits;
        return this;
    }

    private static int bit(int p_index) {
        if(p_index < 0 || p_index >= MAX_SIZE)
            throw new IndexOutOfBoundsException("wrong index: "+p_index);
        return 1 << p_index;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        String separator = "";
        for(int i = 0; i < MAX_SIZE; i++) {
            str.append(separator).append(get(i));
            separator = ",";
        }
        return str.toString();
    }
}
