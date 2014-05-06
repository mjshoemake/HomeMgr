package mjs.common.utils;

import org.junit.Before;
import org.junit.Test;
import core.AbstractLoggableTest;

public class StringUtilsTest extends AbstractLoggableTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test method.
     */
    @Test
    public void testFormat() {

        try {
            String input = "ABCDE%sHIJKL";
            String result = String.format(input, "FG");
        	System.out.println(result);
            assertTrue("Format worked.", result.equals("ABCDEFGHIJKL"));
        	
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

    /**
     * Test method.
     */
    @Test
    public void testSplit() {

        try {
            String input = "fname+lname";
            System.out.println("Splitting: " + input);
            String[] result = input.split("\\+");
            System.out.println("# of items: " + result.length);
            assertTrue("Should have two items.", result.length == 2);
            System.out.println("Item #1: " + result[0]);
            assertTrue("Verify item #1.", result[0].equals("fname"));
            System.out.println("Item #1: " + result[1]);
            assertTrue("Verify item #1.", result[1].equals("lname"));

            System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();
        }
    }

}
