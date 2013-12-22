package unit.framework;

import core.ServletStarterTest;
import org.junit.Before;
import org.junit.Test;

import mjs.common.tags.ShowPaginatedListTag;
import mjs.common.model.Field;

public class PaginatedListTagTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testConvertFieldWidth() {

        try {
         ShowPaginatedListTag tag = new ShowPaginatedListTag();
         Field field = new Field("meals_pk", 
                                 "int",
                                 "",
                                 0,
                                 false,
                                 false,
                                 "120px",
                                 "Meal", 
                                 false,
                                 null);     
         
         String newWidth = tag.getFilterFieldWidth(field);
         System.out.println("StartWidth: " + field.getListColumnWidth());
         System.out.println("New Width:  " + newWidth);
         
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

}
