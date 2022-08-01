package org.nblix.usd.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to encode and verify passwords.
 */
public class PasswordHelper {
    /**
     * This method will perform a simple hashing using SHA-512. For a productive
     * environment we should of course use some kind of salting to make this
     * encoding stronger.
     * 
     * @param password - the original password to be encoded. Might not be null or
     *                 empty.
     * @return the hashed password
     */
    public static String encode(final String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("No password given!");
        }

        String hashedPassword = null;
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] bytes = md.digest(password.getBytes());
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Wrap it up in a rntime exception
            throw new IllegalStateException("Failed to encode the given password! Specified algorythmus not found!");
        }

        return hashedPassword;
    }

    public static boolean verify(final String password, final String hashedPassword) {
        if (password == null || password.isBlank() || hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Neither the password nor the hashed password must be null or empty!");
        }

        if (password.equals(hashedPassword)) {
            return false;
        }

        return PasswordHelper.encode(password).equals(hashedPassword);
    }
}
