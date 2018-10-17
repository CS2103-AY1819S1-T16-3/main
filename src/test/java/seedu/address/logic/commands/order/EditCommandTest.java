package seedu.address.logic.commands.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.order.EditCommand.EditOrderDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalRouteList(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedOrder);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withFood(VALID_FOOD_BURGER).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withFood(VALID_FOOD_BURGER).build();
        EditCommand editCommand = new EditCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(lastOrder, editedOrder);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedOrder);
        expectedModel.commitOrderBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST);

        // edit order in filtered list into a duplicate in order book
        Order orderInList = model.getOrderBook().getOrderList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditOrderDescriptorBuilder(orderInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of order book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Order editedOrder = new OrderBuilder().build();
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.updateOrder(orderToEdit, editedOrder);
        expectedModel.commitOrderBook();

        // edit -> first order edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts orderbook back to previous state and filtered order list to show all orders
        expectedModel.undoOrderBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order edited again
        expectedModel.redoOrderBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> order book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        // single order book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOrderEdited() throws Exception {
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());

        showOrderAtIndex(model, INDEX_SECOND);
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateOrder(orderToEdit, editedOrder);
        expectedModel.commitOrderBook();

        // edit -> edits second order in unfiltered order list / first order in filtered order list
        editCommand.execute(model, commandHistory);

        // undo -> reverts orderbook back to previous state and filtered order list to show all orders
        expectedModel.undoOrderBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased()), orderToEdit);
        // redo -> edits same second order in unfiltered order list
        expectedModel.redoOrderBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_BOB)));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

}
