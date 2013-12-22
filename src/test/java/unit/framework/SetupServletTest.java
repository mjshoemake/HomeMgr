package unit.framework;

import java.sql.Connection;

import core.ServletStarterTest;
import org.junit.Before;
import org.junit.Test;

import mjs.common.database.DatabaseDriver;
import mjs.common.utils.SingletonInstanceManager;
import mjs.setup.SetupServlet;

public class SetupServletTest extends ServletStarterTest {

    SetupServlet setupServlet = new SetupServlet();

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Method to pass block xml to PMG
     */
    @Test
    public void testStartup() {

        try {
        	startServer();

        	// Test get connection.
            SingletonInstanceManager mgr = SingletonInstanceManager.getInstance();
            DatabaseDriver driver = (DatabaseDriver)mgr.getInstance(DatabaseDriver.class.getName());
            Connection conn = driver.getConnection();
            driver.releaseConnection(conn);
        	
            System.out.println("Test complete.  Exiting.");

        } catch (Exception e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

}
