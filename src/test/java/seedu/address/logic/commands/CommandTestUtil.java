package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.order.EditCommand;
import seedu.address.model.Model;
import seedu.address.model.OrderBook;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;
import seedu.address.model.order.NameContainsKeywordsPredicate;
import seedu.address.model.order.Order;
import seedu.address.testutil.EditOrderDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_RAJUL = "Rajul Rahesh";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_ANGMOKIO = "123 Ang Mo Kio St";
    public static final String VALID_DATE_AMY = "01-10-2018 10:00:00";
    public static final String VALID_DATE_BOB = "02-10-2018 10:00:00";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_FOOD_BURGER = "burger";
    public static final String VALID_FOOD_RICE = "fried rice";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_RAJUL = " " + PREFIX_NAME + VALID_NAME_RAJUL;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_ANGMOKIO = " " + PREFIX_ADDRESS + VALID_ADDRESS_ANGMOKIO;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String FOOD_DESC_BURGER = " " + PREFIX_FOOD + VALID_FOOD_BURGER;
    public static final String FOOD_DESC_RICE = " " + PREFIX_FOOD + VALID_FOOD_RICE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "09/11/18";
    public static final String INVALID_FOOD_DESC = " " + PREFIX_FOOD + "milo*"; // '*' not allowed in food

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_MANAGER_NAME_ALICE = "Alice Pauline";
    public static final String VALID_MANAGER_NAME_BENSON = "Benson Meier";
    public static final String VALID_MANAGER_NAME_IDA = "Ida Mueller";
    public static final String VALID_MANAGER_NAME_HOON = "Hoon Meier";
    public static final String VALID_MANAGER_NAME_KENNY = "Kenny Chan";
    public static final String VALID_MANAGER_USERNAME_ALICE = "alicepauline";
    public static final String VALID_MANAGER_USERNAME_BENSON = "bensonmeier";
    public static final String VALID_MANAGER_USERNAME_IDA = "idamueller";
    public static final String VALID_MANAGER_USERNAME_HOON = "hoonmeier";
    public static final String VALID_MANAGER_USERNAME_KENNY = "kennychan";
    public static final String VALID_MANAGER_PASSWORD_ALICE = "alicepauline01";
    public static final String VALID_MANAGER_PASSWORD_BENSON = "bensonmeier02";
    public static final String VALID_MANAGER_PASSWORD_IDA = "idamueller05";
    public static final String VALID_MANAGER_PASSWORD_HOON = "hoonmeier04";
    public static final String VALID_MANAGER_PASSWORD_KENNY = "kennychan06";

    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_MANAGER_NAME_ALICE;
    public static final String NAME_DESC_BENSON = " " + PREFIX_NAME + VALID_MANAGER_NAME_BENSON;
    public static final String USERNAME_DESC_ALICE = " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE;
    public static final String USERNAME_DESC_BENSON = " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_BENSON;
    public static final String PASSWORD_DESC_ALICE = " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
    public static final String PASSWORD_DESC_BENSON = " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_BENSON;

    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "James&"; // '&' not allowed in usernames
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "Jam&"; // '&' not allowed in password

    public static final EditCommand.EditOrderDescriptor DESC_AMY;
    public static final EditCommand.EditOrderDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withFood(VALID_FOOD_BURGER).build();
        DESC_BOB = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withFood(VALID_FOOD_RICE, VALID_FOOD_BURGER).build();
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
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        OrderBook expectedOrderBook = new OrderBook(actualModel.getOrderBook());
        List<Order> expectedFilteredList = new ArrayList<>(actualModel.getFilteredOrderList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedOrderBook, actualModel.getOrderBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredOrderList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s order list.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        Order order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        final String[] splitName = order.getName().fullName.split("\\s+");
        model.updateFilteredOrderList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOrderList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the deliveryman at the given {@code targetIndex} in the
     * {@code model}'s deliverymen list.
     */
    public static void showDeliverymanAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeliverymenList().size());

        Deliveryman deliveryman = model.getFilteredDeliverymenList().get(targetIndex.getZeroBased());
        final String[] splitName = deliveryman.getName().fullName.split("\\s+");
        model.updateFilteredDeliverymenList(new DeliverymanNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDeliverymenList().size());
    }
    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Order firstOrder = model.getFilteredOrderList().get(0);
        model.deleteOrder(firstOrder);
        model.commitOrderBook();
    }

}
