package boost.util.iterators;

/**
 * Created by Sergey.Voronezhtsev on 12/1/2017.
 */
public interface IFlatMapFunction<A, B> {
    Iterable<B> apply(A a);
}
