package com.sendi.v1.util;

public enum ErrorMessages {
    USER_DUPLICATION("User already exists with the username or email: "),
    USER_DUPLICATION_EMAIL("User already exists with the email: "),
    NO_SUCH_USER("No such user with username: "),
    NO_SUCH_USER_ID("No such user with id: "),

    NO_SUCH_AUTHORITY("No such authority with permission: " ),
    NO_SUCH_AUTHORITY_ID("No such authority with id: " ),
    AUTHORITY_DUPLICATION("Authority already exists with the permission: "),

    NO_SUCH_ROLE("No such role with name: "),
    NO_SUCH_ROLE_ID("No such role with id: "),
    ROLE_DUPLICATION("Role already exists with the role name: "),

    NO_SUCH_DECK_ID("No such deck with id: "),

    NO_SUCH_FLASHCARD_ID("No such flashcard with id: "),

    QUESTION_COUNT_LESS("Question count cannot be less than 20. Your number of questions requested: "),

    MISS_MATCH_DECK_FLASHCARD("Flashcard doesn't belong to the deck with deck id: "),

    NO_SUCH_FLASHCARD_WITH_ID("Mismatch between the flashcardId and the question: "),

    TOO_MANY_AUTH_FAIL("Too many invalid password authentication failures with the username: ");

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
