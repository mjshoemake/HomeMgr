package mjs.common.core;

import core.AbstractLoggableTest;
import mjs.model.User;
import org.junit.Before;
import org.junit.Test;

public class SeerObjectTest extends AbstractLoggableTest {

    SeerObject obj;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        obj = new SeerObject(User.class.getName());
    }

    /**
     * Test method.
     */
    @Test
    public void testGetPropertyValue() {

        try {
            User user = new User();
            user.setUser_pk(5);
            user.setUsername("mshoemake");
            user.setFname("Mike");
            user.setLname("Shoemake");
            user.setUsername("mshoemake");
            String value = obj.getPropertyValue(user, "fname+lname");
        	System.out.println("Value: " + value);
            assertTrue("Correct property value.", value.equals("Mike Shoemake"));
        	
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
}
