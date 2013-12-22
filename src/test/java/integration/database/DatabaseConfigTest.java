package integration.database;

import org.junit.Before;
import org.junit.Test;

import mjs.common.database.DatabaseConfig;
import core.ServletStarterTest;

public class DatabaseConfigTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    @Test
    public void testInitialize() {

        try {
            DatabaseConfig.initialize("/config/database.xml");
            
            System.out.println("Test complete.  Exiting.");
            assertTrue("Completed successfully.", true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
