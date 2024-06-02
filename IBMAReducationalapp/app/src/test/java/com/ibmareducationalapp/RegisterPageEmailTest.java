package com.ibmareducationalapp;

import junit.framework.TestCase;


public class RegisterPageEmailTest extends TestCase {

    public void testValidEmail() {
        String email = "zpvb45@durham.ac.uk";
        assertTrue(isValidEmail(email));
    }

    public void testInvalidEmail() {
        String email = "invalid@email.com";
        assertFalse(isValidEmail(email));
    }

    // Method to validate email using the regular expression
    private boolean isValidEmail(String email) {
        String pattern = "[a-zA-Z]{4}\\d{2}@durham\\.ac\\.uk";
        return email.matches(pattern);
    }

}