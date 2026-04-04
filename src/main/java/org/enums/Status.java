package org.enums;

public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String dbValue;

    Status(String dbValue) { this.dbValue = dbValue; }

    public String getDbValue() { return dbValue; }

    public static Status fromDbValue(String value) {
        for (Status s : values()) {
            if (s.dbValue.equalsIgnoreCase(value)) return s;
        }
        return PENDING;
    }
}