package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code OrdersList} that keeps track of its own history.
 */
public class VersionedOrdersList extends OrdersList {

    private final List<ReadOnlyOrdersList> ordersListStateList;
    private int currentStatePointer;

    public VersionedOrdersList(ReadOnlyOrdersList initialState) {
        super(initialState);

        ordersListStateList = new ArrayList<>();
        ordersListStateList.add(new OrdersList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code OrdersList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        ordersListStateList.add(new OrdersList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        ordersListStateList.subList(currentStatePointer + 1, ordersListStateList.size()).clear();
    }

    /**
     * Restores the orders list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(ordersListStateList.get(currentStatePointer));
    }

    /**
     * Restores the orders list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(ordersListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has orders list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has orders list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < ordersListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedOrdersList)) {
            return false;
        }

        VersionedOrdersList otherVersionedOrdersList = (VersionedOrdersList) other;

        // state check
        return super.equals(otherVersionedOrdersList)
                && ordersListStateList.equals(otherVersionedOrdersList.ordersListStateList)
                && currentStatePointer == otherVersionedOrdersList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of ordersListState list, unable to redo.");
        }
    }
}
