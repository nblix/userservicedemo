package org.nblix.usd.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PasswordHelper} utility class
 */
public class PasswordHelperTest {
    @Test
    public void testEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> PasswordHelper.encode(""));
    }

    @Test
    public void testDontEncodeNullValues() {
        assertThrows(IllegalArgumentException.class, () -> PasswordHelper.encode(null));
    }

    @Test
    public void testEncodePassword() {
        String origin = "someVerySecurePassword";
        String hashed = PasswordHelper.encode(origin);
        assertNotNull(hashed);
        assertFalse(hashed.isBlank());
        assertNotEquals(origin, hashed);
    }

    @Test
    public void testDontVerifyWrongPassword() {
        assertFalse(PasswordHelper.verify("some", "value"));
    }

    @Test
    public void testDontVerifyEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> PasswordHelper.verify("something", ""));
    }

    @Test
    public void testDontVerifyNullValue() {
        assertThrows(IllegalArgumentException.class, () -> PasswordHelper.verify("something", null));
    }

    @Test
    public void testVerifyPassword() {
        String origin = "someVerySecurePassword";
        String hashed = PasswordHelper.encode(origin);
        assertTrue(PasswordHelper.verify(origin, hashed));
    }
}
