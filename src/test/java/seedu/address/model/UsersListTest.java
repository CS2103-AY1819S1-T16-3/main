package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class UsersListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UsersList usersList = new UsersList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), usersList.getUserList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        usersList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyUsersList_replacesData() {
        UsersList newData = getTypicalUsersList();
        newData.resetData(newData);
        assertEquals(newData, usersList);
    }

    @Test
    public void resetData_withDuplicateUsers_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        User editedAlice = new UserBuilder(ALICE_MANAGER).build();
        List<User> newUsers = Arrays.asList(ALICE_MANAGER, editedAlice);

        UsersListStub newData = new UsersListStub(newUsers);

        thrown.expect(DuplicatePersonException.class);
        usersList.resetData(newData);
    }

    @Test
    public void hasUser_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        usersList.hasUser(null);
    }

    @Test
    public void hasUser_userNotInUsersList_returnsFalse() {
        assertFalse(usersList.hasUser(ALICE_MANAGER));
    }

    @Test
    public void hasUser_userInUsersList_returnsTrue() {
        usersList.addUser(ALICE_MANAGER);
        assertTrue(usersList.hasUser(ALICE_MANAGER));
    }

    @Test
    public void getUserList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        usersList.getUserList().remove(0);
    }

    /**
     * A stub ReadOnlyUsersList whose users list can violate interface constraints.
     */
    private static class UsersListStub implements ReadOnlyUsersList {
        private final ObservableList<User> users = FXCollections.observableArrayList();

        UsersListStub(Collection<User> users) {
            this.users.setAll(users);
        }

        @Override
        public ObservableList<User> getUserList() {
            return users;
        }
    }
}
