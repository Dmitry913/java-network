package org.school.java.lesson3.constants;

public enum HeaderName {
    CONTENT_TYPE("Content-Type", "application/x-www-form-urlencoded");

    private final String key;
    private final String value;

    HeaderName(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

}
