package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DELIVERYMAN_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveryman.CHIKAO;
import static seedu.address.testutil.TypicalDeliveryman.MANIKA;
import static seedu.address.testutil.TypicalDeliveryman.RAJUL;
import static seedu.address.testutil.TypicalOrders.getTypicalOrdersList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code DeliverymanFindCommandTest}.
 */
public class DeliverymanFindCommandTest {
    // TODO: Add deliveryman into Model Manager after merge
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalOrdersList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalOrdersList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DeliverymanNameContainsKeywordsPredicate firstPredicate =
                new DeliverymanNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DeliverymanNameContainsKeywordsPredicate secondPredicate =
                new DeliverymanNameContainsKeywordsPredicate(Collections.singletonList("second"));

        DeliverymanFindCommand findFirstOrderCommand = new DeliverymanFindCommand(firstPredicate);
        DeliverymanFindCommand findSecondOrderCommand = new DeliverymanFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommand));

        // same values -> returns true
        DeliverymanFindCommand findFirstOrderCommandCopy = new DeliverymanFindCommand(firstPredicate);
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOrderCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOrderCommand.equals(findSecondOrderCommand));
    }

    @Test
    public void execute_zeroKeywords_noDeliverymanFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERYMAN_LISTED_OVERVIEW, 0);

        DeliverymanNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        DeliverymanFindCommand commandName = new DeliverymanFindCommand(namePredicate);
        expectedModel.updateFilteredDeliverymenList(predicate);
        assertCommandSuccess(commandName, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeliverymenList());
    }

    @Test
    public void execute_multipleKeywords_multipleDeliverymanFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERYMAN_LISTED_OVERVIEW, 3);
        DeliverymanNameContainsKeywordsPredicate predicate = preparePredicate("Chi Monuela Rajul");
        DeliverymanFindCommand command = new DeliverymanFindCommand(predicate);
        expectedModel.updateFilteredDeliverymenList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHIKAO, MANIKA, RAJUL), model.getFilteredDeliverymenList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private DeliverymanNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DeliverymanNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
