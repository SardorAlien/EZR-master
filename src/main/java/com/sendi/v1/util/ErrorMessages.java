package com.sendi.v1.util;

public enum ErrorMessages {
    USER_DUPLICATION("User already exists with the username: "),
    USER_DUPLICATION_EMAIL("User already exists with the email: "),

    NO_SUCH_AUTHORITY("No such authority with permission: " ),
    NO_SUCH_AUTHORITY_ID("No such authority with id: " ),
    AUTHORITY_DUPLICATION("Authority already exists with the permission: "),

    NO_SUCH_ROLE("No such role with name: "),
    NO_SUCH_ROLE_ID("No such role with name: "),
    ROLE_DUPLICATION("Role already exists with the name: ");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
