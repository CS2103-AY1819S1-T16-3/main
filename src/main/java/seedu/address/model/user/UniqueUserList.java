package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of unique users
 */
public class UniqueUserList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameUser);
    }

    /**
     * Returns true if the list contains an equivalent person as the given username and password.
     */
    public boolean check(User toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameExistingUser);
    }

    /**
     * Adds a user to the list.
     * The user must not already exist in the list.
     */
    public void add(User toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the user {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setUser(User target, User editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameUser(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(User toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setUsers(UniqueUserList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setUsers(List<User> users) {
        requireAllNonNull(users);
        if (!usersAreUnique(users)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(users);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<User> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                && internalList.equals(((UniqueUserList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique users.
     */
    private boolean usersAreUnique(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).isSameUser(users.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
