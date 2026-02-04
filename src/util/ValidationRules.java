package util;

import exceptions.InvalidInputException;

public interface ValidationRules {
    default void requireNonBlank(String field, String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException(field + " is required.");
        }
    }

    static void requirePositive(String field, double value) {
        if (value <= 0) {
            throw new InvalidInputException(field + " must be greater than 0.");
        }
    }
}
