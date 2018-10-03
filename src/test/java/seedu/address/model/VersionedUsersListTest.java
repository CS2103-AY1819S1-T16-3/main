package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.BENSON_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.CARL_MANAGER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.user.UsersListBuilder;

public class VersionedUsersListTest {

    private final ReadOnlyUsersList usersListWithAlice = new UsersListBuilder().withUser(ALICE_MANAGER).build();
    private final ReadOnlyUsersList usersListWithBenson = new UsersListBuilder().withUser(BENSON_MANAGER).build();
    private final ReadOnlyUsersList usersListWithCarl = new UsersListBuilder().withUser(CARL_MANAGER).build();
    private final ReadOnlyUsersList emptyUsersList = new UsersListBuilder().build();


    @Test
    public void commit_singleUsersList_noStatesRemovedCurrentStateSaved() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList);

        versionedUsersList.commit();
        assertUsersListStatus(versionedUsersList,
                Collections.singletonList(emptyUsersList),
                emptyUsersList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleUsersListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {

        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);

        versionedUsersList.commit();
        assertUsersListStatus(versionedUsersList,
                Arrays.asList(emptyUsersList, usersListWithAlice, usersListWithBenson),
                usersListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleUsersListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 2);

        versionedUsersList.commit();
        assertUsersListStatus(versionedUsersList,
                Collections.singletonList(emptyUsersList),
                emptyUsersList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleUsersListPointerAtEndOfStateList_returnsTrue() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);

        assertTrue(versionedUsersList.canUndo());
    }

    @Test
    public void canUndo_multipleUsersListPointerAtStartOfStateList_returnsTrue() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 1);

        assertTrue(versionedUsersList.canUndo());
    }

    @Test
    public void canUndo_singleUsersList_returnsFalse() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList);

        assertFalse(versionedUsersList.canUndo());
    }

    @Test
    public void canUndo_multipleUsersListPointerAtStartOfStateList_returnsFalse() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 2);

        assertFalse(versionedUsersList.canUndo());
    }

    @Test
    public void canRedo_multipleUsersListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 1);

        assertTrue(versionedUsersList.canRedo());
    }

    @Test
    public void canRedo_multipleUsersListPointerAtStartOfStateList_returnsTrue() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 2);

        assertTrue(versionedUsersList.canRedo());
    }

    @Test
    public void canRedo_singleUsersList_returnsFalse() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList);

        assertFalse(versionedUsersList.canRedo());
    }

    @Test
    public void canRedo_multipleUsersListPointerAtEndOfStateList_returnsFalse() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);

        assertFalse(versionedUsersList.canRedo());
    }

    @Test
    public void undo_multipleUsersListPointerAtEndOfStateList_success() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);

        versionedUsersList.undo();
        assertUsersListStatus(versionedUsersList,
                Collections.singletonList(emptyUsersList),
                usersListWithAlice,
                Collections.singletonList(usersListWithBenson));
    }

    @Test
    public void undo_multipleUsersListPointerNotAtStartOfStateList_success() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 1);

        versionedUsersList.undo();
        assertUsersListStatus(versionedUsersList,
                Collections.emptyList(),
                emptyUsersList,
                Arrays.asList(usersListWithAlice, usersListWithBenson));
    }

    @Test
    public void undo_singleUsersList_throwsNoUndoableStateException() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList);

        assertThrows(VersionedUsersList.NoUndoableStateException.class, versionedUsersList::undo);
    }

    @Test
    public void undo_multipleUsersListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 2);

        assertThrows(VersionedUsersList.NoUndoableStateException.class, versionedUsersList::undo);
    }

    @Test
    public void redo_multipleUsersListPointerNotAtEndOfStateList_success() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 1);

        versionedUsersList.redo();
        assertUsersListStatus(versionedUsersList,
                Arrays.asList(emptyUsersList, usersListWithAlice),
                usersListWithBenson,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleUsersListPointerAtStartOfStateList_success() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);
        shiftCurrentStatePointerLeftwards(versionedUsersList, 2);

        versionedUsersList.redo();
        assertUsersListStatus(versionedUsersList,
                Collections.singletonList(emptyUsersList),
                usersListWithAlice,
                Collections.singletonList(usersListWithBenson));
    }

    @Test
    public void redo_singleUsersList_throwsNoRedoableStateException() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList);

        assertThrows(VersionedUsersList.NoRedoableStateException.class, versionedUsersList::redo);
    }

    @Test
    public void redo_multipleUsersListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedUsersList versionedUsersList = prepareUsersList(emptyUsersList, usersListWithAlice,
                usersListWithBenson);

        assertThrows(VersionedUsersList.NoRedoableStateException.class, versionedUsersList::redo);
    }

    /**
     * Asserts that {@code versionedUsersList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedUsersList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedUsersList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertUsersListStatus(VersionedUsersList versionedUsersList,
                                       List<ReadOnlyUsersList> expectedStatesBeforePointer,
                                       ReadOnlyUsersList expectedCurrentState,
                                       List<ReadOnlyUsersList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new UsersList(versionedUsersList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedUsersList.canUndo()) {
            versionedUsersList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyUsersList expectedUsersList : expectedStatesBeforePointer) {
            assertEquals(expectedUsersList, new UsersList(versionedUsersList));
            versionedUsersList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyUsersList expectedUsersList : expectedStatesAfterPointer) {
            versionedUsersList.redo();
            assertEquals(expectedUsersList, new UsersList(versionedUsersList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedUsersList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedUsersList.undo());
    }



    /**
     * Creates and returns a {@code VersionedUsersList} with the {@code usersListStates} added into it, and the
     * {@code VersionedUsersList#currentStatePointer} at the end of list.
     */
    private VersionedUsersList prepareUsersList(ReadOnlyUsersList... usersListStates) {
        assertFalse(usersListStates.length == 0);

        VersionedUsersList versionedUsersList = new VersionedUsersList(usersListStates[0]);
        for (int i = 1; i < usersListStates.length; i++) {
            versionedUsersList.resetData(usersListStates[i]);
            versionedUsersList.commit();
        }

        return versionedUsersList;
    }

    /**
     * Shifts the {@code versionedUsersList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedUsersList versionedUsersList, int count) {
        for (int i = 0; i < count; i++) {
            versionedUsersList.undo();
        }
    }
}
