package seedu.address.testutil.user;

import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;

/**
 * A utility class to help with building UsersList objects.
 * Example usage: <br>
 *     {@code UsersList usersList = new UserBuilder().withPerson("John", "Doe").build();}
 */
public class UserBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_USERNAME = "alicepauline";
    public static final String DEFAULT_PASSWORD = "alicepauline01";

    private Name name;
    private Username username;
    private Password password;


    public UserBuilder() {
        name = new Name(DEFAULT_NAME);
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
    }


    /**
     * Initializes the UserBuilder with the data of {@code userToCopy}.
     */
    public UserBuilder(User userToCopy) {
        name = userToCopy.getName();
        username = userToCopy.getUsername();
        password = userToCopy.getPassword();
    }

    /**
     * Sets the {@code Name} of the {@code User} that we are building.
     */
    public UserBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code User} that we are building.
     */
    public UserBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code User} that we are building.
     */
    public UserBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    public User build() {
        return new Manager(name, username, password);
    }

}
