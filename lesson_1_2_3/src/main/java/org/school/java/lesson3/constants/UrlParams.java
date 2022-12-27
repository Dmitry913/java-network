package org.school.java.lesson3.constants;

public enum UrlParams {
    LOGIN("login"), PASSWORD("password");

    private final String value;

    UrlParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
