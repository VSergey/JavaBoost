package boost.util.iterators;

/**
 * Created by Sergey.Voronezhtsev on 12/1/2017.
 */
public interface IFilteringFunction<A> {
    boolean accept(A p_a);
}
