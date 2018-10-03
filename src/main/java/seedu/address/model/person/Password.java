package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's password in FoodZoom.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumeric characters , should be at least 6 characters "
                    + "and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}]{6,}";
    public final String value;


    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        isValidPassword(password);
        value = password;
    }


    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
