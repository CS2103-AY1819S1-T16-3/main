package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.CARL_TO;
import static seedu.address.testutil.TypicalOrders.ELLE_TO;
import static seedu.address.testutil.TypicalOrders.FIONA_TO;
import static seedu.address.testutil.TypicalOrders.AH_BENG_TO;
import static seedu.address.testutil.TypicalOrders.getTypicalOrdersList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;


import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.order.FindOrderCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderContainsAnyKeywordsPredicate;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindOrderCommand}.
 */
public class FindOrderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalOrdersList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalOrdersList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        OrderContainsAnyKeywordsPredicate firstPredicate =
                new OrderNameContainsKeywordPredicate(Collections.singletonList("first"));
        OrderContainsAnyKeywordsPredicate secondPredicate =
                new OrderNameContainsKeywordPredicate(Collections.singletonList("second"));

        FindOrderCommand findFirstOrderCommand = new FindOrderCommand(firstPredicate);
        FindOrderCommand findSecondOrderCommand = new FindOrderCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommand));

        // same values -> returns true
        FindOrderCommand findFirstOrderCommandCopy = new FindOrderCommand(firstPredicate);
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOrderCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOrderCommand.equals(findSecondOrderCommand));
    }

    @Test
    public void execute_zeroKeywords_noOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);

        OrderContainsAnyKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        FindOrderCommand commandName = new FindOrderCommand(namePredicate);
        expectedModel.updateFilteredOrderList(namePredicate);
        assertCommandSuccess(commandName, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());

        OrderContainsAnyKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        FindOrderCommand commandPhone = new FindOrderCommand(phonePredicate);
        expectedModel.updateFilteredOrderList(phonePredicate);
        assertCommandSuccess(commandPhone, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_multipleKeywords_multipleOrdersFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 3);
        OrderContainsAnyKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindOrderCommand command = new FindOrderCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_TO, ELLE_TO, FIONA_TO), model.getFilteredOrderList());
    }

    @Test
    public void execute_fullNameKeyword_singleOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        OrderContainsAnyKeywordsPredicate predicate = prepareNamePredicate("Ah Beng");
        FindOrderCommand command = new FindOrderCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AH_BENG_TO), model.getFilteredOrderList());
    }

    @Test
    public void execute_phoneKeyword_singleOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        OrderContainsAnyKeywordsPredicate predicate = preparePhonePredicate(" 9876 5432 ");
        FindOrderCommand command = new FindOrderCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code OrderContainsAnyKeywordsPredicate}.
     */
    private OrderContainsAnyKeywordsPredicate prepareNamePredicate(String userInput) {
        return new OrderNameContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code OrderContainsAnyKeywordsPredicate}.
     */
    private OrderContainsAnyKeywordsPredicate preparePhonePredicate(String userInput) {
        return new OrderPhoneContainsKeywordPredicate(userInput.trim().replaceAll("\\s+", ""));
    }
}


