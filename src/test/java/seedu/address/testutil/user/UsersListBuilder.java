package seedu.address.testutil.user;

import seedu.address.model.UsersList;
import seedu.address.model.user.User;

/**
 * A utility class to help with building UsersList objects.
 * Example usage: <br>
 *     {@code UsersList usersList = new UsersListBuilder().withUsers("John", "Doe").build();}
 */
public class UsersListBuilder {

    private UsersList usersList;

    public UsersListBuilder() {
        usersList = new UsersList();
    }

    public UsersListBuilder(UsersList usersList) {
        this.usersList = usersList;
    }

    /**
     * Adds a new {@code User} to the {@code UsersListBuilder} that we are building.
     */
    public UsersListBuilder withUser(User user) {
        usersList.addUser(user);
        return this;
    }

    public UsersList build() {
        return usersList;
    }
}
