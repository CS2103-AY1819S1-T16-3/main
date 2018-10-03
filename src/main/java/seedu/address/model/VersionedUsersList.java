package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code UsersList} that keeps track of its own history.
 */
public class VersionedUsersList extends UsersList {

    private final List<ReadOnlyUsersList> usersListState;
    private int currentStatePointer;

    public VersionedUsersList(ReadOnlyUsersList initialState) {
        super(initialState);

        usersListState = new ArrayList<>();
        usersListState.add(new UsersList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code UsersList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        usersListState.add(new UsersList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        usersListState.subList(currentStatePointer + 1, usersListState.size()).clear();
    }


    /**
     * Restores the users list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(usersListState.get(currentStatePointer));
    }

    /**
     * Restores the users list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(usersListState.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has users list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has users list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < usersListState.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedUsersList)) {
            return false;
        }

        VersionedUsersList otherVersionedUsersList = (VersionedUsersList) other;

        // state check
        return super.equals(otherVersionedUsersList)
                && usersListState.equals(otherVersionedUsersList.usersListState)
                && currentStatePointer == otherVersionedUsersList.currentStatePointer;
    }
    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of usersListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of usersListState list, unable to redo.");
        }
    }
}
