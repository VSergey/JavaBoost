package boost.list;

import boost.util.NotNull;

import java.util.*;

public class Tuple extends AbstractList {
    private Object[] o_contents;
    private int o_hashcode;

    public Tuple(Object[] contents) {
        o_contents = contents;
    }

    public Object get(int index) {
        return o_contents[index];
    }

    public int size() {
        return o_contents.length;
    }

    public @Override boolean equals(Object that) {
        if (that instanceof Tuple) {
            Tuple other = ((Tuple) that);
            if (o_contents.length == other.o_contents.length) {
                for (int i = 0; i < o_contents.length; i++) {
                    if (!Objects.equals(o_contents[i], other.o_contents[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public @Override int hashCode() {
        if (o_hashcode == 0) {
            for (Object value : o_contents) {
                int hash = (value != null) ? value.hashCode() : 0xbabe;
                o_hashcode ^= hash;
            }
            if (o_hashcode == 0) {
                o_hashcode = 0xbabe;
            }
        }
        return o_hashcode;
    }

    public @NotNull List subList(int fromIndex, int toIndex) {
        int size = toIndex - fromIndex;
        Object[] newContent = new Object[size];
        System.arraycopy(o_contents, fromIndex, newContent, 0, size);
        return new Tuple(newContent);
    }
}
