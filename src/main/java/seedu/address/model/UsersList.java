package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.user.UniqueUserList;
import seedu.address.model.user.User;

/**
 * Wraps all data at the users list.
 * Duplicates are not allowed.
 */
public class UsersList implements ReadOnlyUsersList {

    private final UniqueUserList users;

    {
        users = new UniqueUserList();
    }

    public UsersList() {
    }

    /**
     * Creates an UsersList using the User in the {@code toBeCopied}
     */
    public UsersList(ReadOnlyUsersList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the user list with {@code users}.
     * {@code persons} must not contain duplicate user.
     */
    public void setUsers(List<User> users) {
        this.users.setUsers(users);
    }

    /**
     * Resets the existing data of this {@code UsersList} with {@code newData}.
     */
    public void resetData(ReadOnlyUsersList newData) {
        requireNonNull(newData);
        setUsers(newData.getUserList());
    }

    /**
     * Returns true if a user with the same identity as {@code user} exists in the usersList.
     */
    public boolean hasUser(User user) {
        requireNonNull(user);
        return users.contains(user);
    }

    /**
     * Returns true if a user with the same identity as {@code user} exists in the usersList.
     */
    public boolean login(User toLogin) {
        requireNonNull(toLogin);
        return users.check(toLogin);
    }

    /**
     * Adds a user to the user list.
     * The user must not already exist in the user list.
     */
    public void addUser(User u) {
        users.add(u);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedUser}.
     * {@code target} must exist in the users list.
     * The person identity of {@code editedPerson} must not be the same as another existing user in the users list.
     */
    public void updateUser(User target, User editedUser) {
        requireNonNull(editedUser);
        users.setUser(target, editedUser);
    }

    /**
     * Removes {@code key} from this {@code UsersList}.
     * {@code key} must exist in the userslist.
     */
    public void removeUser(User key) {
        users.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return users.asUnmodifiableObservableList().size() + " users";
        // TODO: refine later
    }

    @Override
    public ObservableList<User> getUserList() {
        return users.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsersList // instanceof handles nulls
                && users.equals(((UsersList) other).users));
    }

    @Override
    public int hashCode() {
        return users.hashCode();
    }

}


