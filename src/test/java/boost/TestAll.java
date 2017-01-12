package boost;

import boost.map.TestReadonlyMapImpl;
import boost.util.floating.TestFloatingPointValues;
import boost.util.iterators.TestIterators;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author: svoronezhtsev
 * Date: 18-Jul-16
 */
public class TestAll {

    public static TestSuite suite() throws Exception {
        TestSuite suite = new TestSuite();
        suite.addTest(TestFloatingPointValues.suite());
        suite.addTest(TestIterators.suite());
        suite.addTest(TestReadonlyMapImpl.suite());

        return suite;
    }

    public static void main(String[] args) throws Exception {
        TestRunner.run(TestAll.suite());
        System.exit(0);
    }
}
