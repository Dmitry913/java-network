package org.school.java.lesson3.exceptions;

public class InvalidLogin extends RuntimeException {
    private static final String invalidLoginMessage = "Not valid login";

    public InvalidLogin() {
        super(invalidLoginMessage);
    }
}
