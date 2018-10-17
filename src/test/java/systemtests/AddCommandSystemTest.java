package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.AMY;
import static seedu.address.testutil.TypicalOrders.BOB;
import static seedu.address.testutil.TypicalOrders.CARL;
import static seedu.address.testutil.TypicalOrders.HOON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.order.AddCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;

public class AddCommandSystemTest extends OrderBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an order to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Order toAdd = AMY;
        String addCommand = OrderCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD;
        command = "   " + addCommand + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + "   " + ADDRESS_DESC_AMY + "   " + DATE_DESC_AMY + "  " + FOOD_DESC_BURGER + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addOrder(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an order with all fields same as another order in the order book except name -> added */
        toAdd = new OrderBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = addCommand + NAME_DESC_BOB + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + FOOD_DESC_BURGER;
        assertCommandSuccess(command, toAdd);

        /* Case: add an order with all fields same as another order in the address book except phone
         * -> added
         */
        toAdd = new OrderBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        command = OrderCommand.COMMAND_WORD + " " + OrderUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllOrders();
        assertCommandSuccess(ALICE);

        /* Case: add an order, command with parameters in random order -> added */
        toAdd = BOB;
        command = addCommand + DATE_DESC_BOB + FOOD_DESC_RICE + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add an order -> added */
        assertCommandSuccess(HOON);


        /* ------------------------ Perform add operation while an order card is selected --------------------------- */

        /* Case: selects first card in the order list, add an order -> added, card selection remains unchanged */
        selectOrder(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate order -> rejected */
        command = OrderCommand.COMMAND_WORD + " " + OrderUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ORDER);

        /* Case: add a duplicate order except with different address -> rejected */
        toAdd = new OrderBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = OrderCommand.COMMAND_WORD + " " + OrderUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ORDER);

        /* Case: missing name -> rejected */
        command = addCommand + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = addCommand + NAME_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = addCommand + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = addCommand + NAME_DESC_AMY + PHONE_DESC_AMY + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing food -> rejected */
        command = addCommand + NAME_DESC_AMY + PHONE_DESC_AMY + DATE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + OrderUtil.getOrderDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = addCommand + INVALID_NAME_DESC + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = addCommand + NAME_DESC_AMY + INVALID_PHONE_DESC + ADDRESS_DESC_AMY + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = addCommand + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_ADDRESS_DESC + DATE_DESC_AMY + FOOD_DESC_BURGER;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = addCommand + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + INVALID_DATE_DESC + FOOD_DESC_BURGER;
        assertCommandFailure(command, OrderDate.MESSAGE_DATE_CONSTRAINTS);

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Order toAdd) {
        assertCommandSuccess(OrderCommand.COMMAND_WORD + " " + OrderUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Order)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Order toAdd) {
        Model expectedModel = getModel();
        expectedModel.addOrder(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Order)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
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
