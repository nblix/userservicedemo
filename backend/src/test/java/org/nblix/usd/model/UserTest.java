package org.nblix.usd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Simple test for the {@link User} model class without the need of the spring
 * context etc.
 * 
 */
public class UserTest {
    /**
     * Make sure the equals method works as expected.
     */
    @Test
    public void testUserEquals() {
        User u1 = createUser("some", "verySecurePassword");
        User u2 = createUser("some", "verySecurePassword");
        assertNotNull(u1);
        assertNotNull(u2);
        assertEquals(u1, u1);
        assertEquals(u1, u2);
    }

    private User createUser(String name, String pwd) {
        return new User(name, pwd);
    }
}
