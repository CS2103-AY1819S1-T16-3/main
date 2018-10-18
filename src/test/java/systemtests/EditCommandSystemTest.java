package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOOD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.AMY;
import static seedu.address.testutil.TypicalOrders.BOB;
import static seedu.address.testutil.TypicalOrders.KEYWORD_NAME_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.order.EditCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.model.Model;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;

public class EditCommandSystemTest extends OrderBookSystemTest {
    @Test
    public void edit() {
        Model model = getModel();
        String editCommand = OrderCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD;

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */
        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST;
        command = " " + editCommand + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + PHONE_DESC_BOB + " " + DATE_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + FOOD_DESC_BURGER + " ";
        Order editedOrder = new OrderBuilder(BOB).withFood(VALID_FOOD_BURGER).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: undo editing the last order in the list -> last order restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last order in the list -> last order edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateOrder(
                getModel().getFilteredOrderList().get(INDEX_FIRST.getZeroBased()), editedOrder);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a order with new values same as existing values -> edited */
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a order with new values same as another order's values but with different name -> edited */
        assertTrue(getModel().getOrderBook().getOrderList().contains(BOB));
        index = INDEX_SECOND;
        assertNotEquals(getModel().getFilteredOrderList().get(index.getZeroBased()), BOB);
        command = editCommand + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        editedOrder = new OrderBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: edit a order with new values same as another order's values but with different phone -> edited */
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        editedOrder = new OrderBuilder(BOB).withPhone(VALID_PHONE_AMY).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: edit a order with new values same as another order's values but with different date -> edited */
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_AMY
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        editedOrder = new OrderBuilder(BOB).withDate(VALID_DATE_AMY).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: edit a order with new values same as another order's values but with different phone and date
         * -> edited
         */
        index = INDEX_SECOND;
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + DATE_DESC_AMY
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        editedOrder = new OrderBuilder(BOB).withPhone(VALID_PHONE_AMY).withDate(VALID_DATE_AMY).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: clear food -> rejected */
        index = INDEX_FIRST;
        command = editCommand + " " + index.getOneBased() + " " + PREFIX_FOOD.getPrefix();
        assertCommandFailure(command, Food.MESSAGE_FOOD_CONSTRAINTS);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered order list, edit index within bounds of order book and order list -> edited */
        showOrdersWithName(KEYWORD_NAME_MATCHING_MEIER);
        index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredOrderList().size());
        command = editCommand + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        Order orderToEdit = getModel().getFilteredOrderList().get(index.getZeroBased());
        editedOrder = new OrderBuilder(orderToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedOrder);

        /* Case: filtered order list, edit index within bounds of order book but out of bounds of order list
         * -> rejected
         */
        showOrdersWithName(KEYWORD_NAME_MATCHING_MEIER);
        int invalidIndex = getModel().getOrderBook().getOrderList().size();
        assertCommandFailure(editCommand + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a order card is selected -------------------------- */

        /* Case: selects first card in the order list, edit a order -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllOrders();
        index = INDEX_FIRST;
        selectOrder(index);
        command = editCommand + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + DATE_DESC_AMY
                + ADDRESS_DESC_AMY + FOOD_DESC_BURGER;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new order's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(editCommand + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(editCommand + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredOrderList().size() + 1;
        assertCommandFailure(editCommand + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(editCommand + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased() + INVALID_DATE_DESC,
                OrderDate.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid food -> rejected */
        assertCommandFailure(editCommand + " " + INDEX_FIRST.getOneBased() + INVALID_FOOD_DESC,
                Food.MESSAGE_FOOD_CONSTRAINTS);

        /* Case: edit a order with new values same as another order's values -> rejected */
        executeCommand(OrderCommand.COMMAND_WORD + " " + OrderUtil.getAddCommand(BOB));
        assertTrue(getModel().getOrderBook().getOrderList().stream().anyMatch(x -> x.isSameOrder(BOB)));
        index = INDEX_FIRST;
        assertFalse(getModel().getFilteredOrderList().get(index.getZeroBased()).equals(BOB));
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + FOOD_DESC_RICE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ORDER);

        /* Case: edit a order with new values same as another order's values but with different food -> rejected */
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + FOOD_DESC_BURGER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ORDER);

        /* Case: edit a order with new values same as another order's values but with different address -> rejected */
        command = editCommand + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_AMY + FOOD_DESC_RICE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ORDER);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Person, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder) {
        assertCommandSuccess(command, toEdit, editedOrder, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the order at index {@code toEdit} being
     * updated to values specified {@code editedOrder}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateOrder(expectedModel.getFilteredOrderList().get(toEdit.getZeroBased()), editedOrder);
        expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see OrderBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
