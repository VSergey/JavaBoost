package boost.util;

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
        boolean swing = Boolean.getBoolean("use-swing");

        if (swing) {
            String[] testArgs=new String[]{TestAll.class.getName()};
            junit.swingui.TestRunner runner=new junit.swingui.TestRunner();
            // by default JUnit installs its own class loader that can
            // dynamically reload tested classes.
            // That breaks Class.forName() and need switch it off.
            runner.setLoading(false);
            runner.start(testArgs);
        } else {
            TestRunner.run(TestAll.suite());
            System.exit(0);
        }
    }
}
