package seedu.address.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.BENSON_MANAGER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.user.UserBuilder;

public class UniqueUserListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueUserList uniqueUserList = new UniqueUserList();


    @Test
    public void contains_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.contains(null);
    }

    @Test
    public void contains_userNotInList_returnsFalse() {
        assertFalse(uniqueUserList.contains(ALICE_MANAGER));
    }

    @Test
    public void contains_userInList_returnsTrue() {
        uniqueUserList.add(ALICE_MANAGER);
        assertTrue(uniqueUserList.contains(ALICE_MANAGER));
    }

    @Test
    public void contains_userWithSameIdentityFieldsInList_returnsTrue() {
        uniqueUserList.add(ALICE_MANAGER);
        User editedAlice = new UserBuilder(ALICE_MANAGER).build();
        assertTrue(uniqueUserList.contains(editedAlice));
    }

    @Test
    public void add_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.add(null);
    }

    @Test
    public void add_duplicateUser_throwsDuplicatePersonException() {
        uniqueUserList.add(ALICE_MANAGER);
        thrown.expect(DuplicatePersonException.class);
        uniqueUserList.add(ALICE_MANAGER);
    }

    @Test
    public void setUser_nullTargetUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUser(null, ALICE_MANAGER);
    }

    @Test
    public void setUser_nullEditedUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUser(ALICE_MANAGER, null);
    }

    @Test
    public void setUser_targetUserNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueUserList.setUser(ALICE_MANAGER, ALICE_MANAGER);
    }

    @Test
    public void setUser_editedPersonIsSameUser_success() {
        uniqueUserList.add(ALICE_MANAGER);
        uniqueUserList.setUser(ALICE_MANAGER, ALICE_MANAGER);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(ALICE_MANAGER);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUser_editedUserHasSameIdentity_success() {
        uniqueUserList.add(ALICE_MANAGER);
        User editedAlice = new UserBuilder(ALICE_MANAGER).build();
        uniqueUserList.setUser(ALICE_MANAGER, editedAlice);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(editedAlice);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void remove_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.remove(null);
    }

    @Test
    public void remove_userDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueUserList.remove(ALICE_MANAGER);
    }

    @Test
    public void remove_existingUser_removesUser() {
        uniqueUserList.add(ALICE_MANAGER);
        uniqueUserList.remove(ALICE_MANAGER);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_nullUniqueUserList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUsers((UniqueUserList) null);
    }

    @Test
    public void setUsers_uniqueUserList_replacesOwnListWithProvidedUniqueUserList() {
        uniqueUserList.add(ALICE_MANAGER);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(BENSON_MANAGER);
        uniqueUserList.setUsers(expectedUniqueUserList);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueUserList.setUsers((List<User>) null);
    }

    @Test
    public void setUsers_list_replacesOwnListWithProvidedList() {
        uniqueUserList.add(ALICE_MANAGER);
        List<User> userList = Collections.singletonList(BENSON_MANAGER);
        uniqueUserList.setUsers(userList);
        UniqueUserList expectedUniqueUserList = new UniqueUserList();
        expectedUniqueUserList.add(BENSON_MANAGER);
        assertEquals(expectedUniqueUserList, uniqueUserList);
    }

    @Test
    public void setUsers_listWithDuplicateUsers_throwsDuplicatePersonException() {
        List<User> listWithDuplicateUsers = Arrays.asList(ALICE_MANAGER, ALICE_MANAGER);
        thrown.expect(DuplicatePersonException.class);
        uniqueUserList.setUsers(listWithDuplicateUsers);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueUserList.asUnmodifiableObservableList().remove(0);
    }
}
