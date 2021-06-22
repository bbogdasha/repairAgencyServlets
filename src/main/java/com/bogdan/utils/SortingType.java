package com.bogdan.utils;

public enum SortingType {
    ID("id"),
    PRICE("price"),
    NAME("title");

    private final String value;

    SortingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortingType safeValueOf(final String value) {
        try {
            return SortingType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return NAME;
        }
    }
}
