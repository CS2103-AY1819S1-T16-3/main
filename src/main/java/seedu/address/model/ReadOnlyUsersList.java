package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.user.User;

/**
 * Unmodifiable view of users
 */
public interface ReadOnlyUsersList {

    /**
     * Returns an unmodifiable view of the user list.
     * This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();
}
