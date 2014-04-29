package mjs.common.utils;

import core.AbstractLoggableTest;
import mjs.common.crypto.EncryptionManager;
import mjs.common.crypto.Encryptor;
import org.junit.Before;
import org.junit.Test;

public class EncryptionManagerTest extends AbstractLoggableTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test method.
     */
    @Test
    public void testEncryption() {

        try {
            String password = "qwe`123";
            log.debug("Inital: " + password);
            Encryptor mgr = EncryptionManager.getInstance(2);
            String encrypted = mgr.encrypt(password);
            log.debug("Encrypted: " + encrypted);

            String unencrypted = mgr.decrypt(encrypted);
            log.debug("Unencrypted: " + unencrypted);
            assertTrue(password.equals(unencrypted));

        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        }
    }
    
}
