package seedu.address.model.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRoutes.ROUTE_ALICE_BENSON;
import static seedu.address.testutil.TypicalRoutes.ROUTE_CARL;
import static seedu.address.testutil.TypicalRoutes.ROUTE_DANIEL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.route.RouteListBuilder;

public class VersionedRouteListTest {

    private final ReadOnlyRouteList routeListWithAliceBenson = new RouteListBuilder()
            .withRoute(ROUTE_ALICE_BENSON).build();
    private final ReadOnlyRouteList routeListWithCarl = new RouteListBuilder().withRoute(ROUTE_CARL).build();
    private final ReadOnlyRouteList routeListWithDaniel = new RouteListBuilder().withRoute(ROUTE_DANIEL).build();
    private final ReadOnlyRouteList emptyRouteList = new RouteListBuilder().build();

    @Test
    public void commit_singleRouteList_noStatesRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                emptyRouteList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRouteListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Arrays.asList(emptyRouteList, routeListWithAliceBenson, routeListWithCarl),
                routeListWithCarl,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRouteListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        versionedRouteList.commit();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                emptyRouteList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtEndOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);

        assertTrue(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtStartOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        assertTrue(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_singleRouteList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertFalse(versionedRouteList.canUndo());
    }

    @Test
    public void canUndo_multipleRouteListPointerAtStartOfStateList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertFalse(versionedRouteList.canUndo());
    }

    @Test
    public void canRedo_multipleRouteListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        assertTrue(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_multipleRouteListPointerAtStartOfStateList_returnsTrue() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertTrue(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_singleRouteList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertFalse(versionedRouteList.canRedo());
    }

    @Test
    public void canRedo_multipleRouteListPointerAtEndOfStateList_returnsFalse() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);

        assertFalse(versionedRouteList.canRedo());
    }

    @Test
    public void undo_multipleRouteListPointerAtEndOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);

        versionedRouteList.undo();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                routeListWithAliceBenson,
                Collections.singletonList(routeListWithCarl));
    }

    @Test
    public void undo_multipleRouteListPointerNotAtStartOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        versionedRouteList.undo();
        assertRouteListListStatus(versionedRouteList,
                Collections.emptyList(),
                emptyRouteList,
                Arrays.asList(routeListWithAliceBenson, routeListWithCarl));
    }

    @Test
    public void undo_singleRouteList_throwsNoUndoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertThrows(VersionedRouteList.NoUndoableStateException.class, versionedRouteList::undo);
    }

    @Test
    public void undo_multipleRouteListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        assertThrows(VersionedRouteList.NoUndoableStateException.class, versionedRouteList::undo);
    }

    @Test
    public void redo_multipleRouteListPointerNotAtEndOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);

        versionedRouteList.redo();
        assertRouteListListStatus(versionedRouteList,
                Arrays.asList(emptyRouteList, routeListWithAliceBenson),
                routeListWithCarl,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleRouteListPointerAtStartOfStateList_success() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 2);

        versionedRouteList.redo();
        assertRouteListListStatus(versionedRouteList,
                Collections.singletonList(emptyRouteList),
                routeListWithAliceBenson,
                Collections.singletonList(routeListWithCarl));
    }

    @Test
    public void redo_singleRouteList_throwsNoRedoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(emptyRouteList);

        assertThrows(VersionedRouteList.NoRedoableStateException.class, versionedRouteList::redo);
    }

    @Test
    public void redo_multipleRouteListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRouteList versionedRouteList = prepareRouteListList(
                emptyRouteList, routeListWithAliceBenson, routeListWithCarl);

        assertThrows(VersionedRouteList.NoRedoableStateException.class, versionedRouteList::redo);
    }

    @Test
    public void equals() {
        VersionedRouteList versionedRouteList = prepareRouteListList(routeListWithAliceBenson, routeListWithCarl);

        // same values -> returns true
        VersionedRouteList copy = prepareRouteListList(routeListWithAliceBenson, routeListWithCarl);
        assertTrue(versionedRouteList.equals(copy));

        // same object -> returns true
        assertTrue(versionedRouteList.equals(versionedRouteList));

        // null -> returns false
        assertFalse(versionedRouteList.equals(null));

        // different types -> returns false
        assertFalse(versionedRouteList.equals(1));

        // different state list -> returns false
        VersionedRouteList differentRouteListList = prepareRouteListList(routeListWithCarl, routeListWithDaniel);
        assertFalse(versionedRouteList.equals(differentRouteListList));

        // different current pointer index -> returns false
        VersionedRouteList differentCurrentStatePointer = prepareRouteListList(
                routeListWithAliceBenson, routeListWithCarl);
        shiftCurrentStatePointerLeftwards(versionedRouteList, 1);
        assertFalse(versionedRouteList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRouteList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedRouteList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedRouteList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertRouteListListStatus(VersionedRouteList versionedRouteList,
                                             List<ReadOnlyRouteList> expectedStatesBeforePointer,
                                             ReadOnlyRouteList expectedCurrentState,
                                             List<ReadOnlyRouteList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RouteList(versionedRouteList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRouteList.canUndo()) {
            versionedRouteList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyRouteList expectedRouteList : expectedStatesBeforePointer) {
            assertEquals(expectedRouteList, new RouteList(versionedRouteList));
            versionedRouteList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyRouteList expectedRouteList : expectedStatesAfterPointer) {
            versionedRouteList.redo();
            assertEquals(expectedRouteList, new RouteList(versionedRouteList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRouteList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRouteList.undo());
    }

    /**
     * Creates and returns a {@code VersionedRouteList} with the {@code routeListStates} added into it, and the
     * {@code VersionedRouteList#currentStatePointer} at the end of list.
     */
    private VersionedRouteList prepareRouteListList(ReadOnlyRouteList... routeListStates) {
        assertFalse(routeListStates.length == 0);

        VersionedRouteList versionedRouteList = new VersionedRouteList(routeListStates[0]);
        for (int i = 1; i < routeListStates.length; i++) {
            versionedRouteList.resetData(routeListStates[i]);
            versionedRouteList.commit();
        }

        return versionedRouteList;
    }

    /**
     * Shifts the {@code versionedRouteList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRouteList versionedRouteList, int count) {
        for (int i = 0; i < count; i++) {
            versionedRouteList.undo();
        }
    }
}