package seedu.address.logic.commands.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.route.CreateRouteCommand.MESSAGE_SUCCESS_ALL_VALID;
import static seedu.address.logic.commands.route.CreateRouteCommand.MESSAGE_SUCCESS_SOME_INVALID;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.route.Route;

public class CreateRouteCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalRouteList(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private Order validOrder = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
    private Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRoute_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateRouteCommand(null);
    }

    @Test
    public void execute_orderAllAcceptedByModel_addSuccessful() {
        Set<Order> orders = new HashSet<>();
        orders.add(validOrder);
        Set<Index> orderIds = new HashSet<>();
        orderIds.add(INDEX_FIRST);
        CreateRouteCommand createRouteCommand = new CreateRouteCommand(orderIds);
        Route validRoute = new Route(orders);

        String expectedMessage = String.format(MESSAGE_SUCCESS_ALL_VALID, INDEX_FIRST.getOneBased());

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.addRoute(validRoute);
        expectedModel.commitRouteList();

        assertCommandSuccess(createRouteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_orderSomeAcceptedByModel_addSuccessful() {
        Set<Order> orders = new HashSet<>();
        orders.add(validOrder);
        Set<Index> orderIds = new HashSet<>();
        orderIds.add(INDEX_FIRST);
        orderIds.add(outOfBoundIndex);
        CreateRouteCommand createRouteCommand = new CreateRouteCommand(orderIds);
        Route validRoute = new Route(orders);

        String expectedMessage = String.format(MESSAGE_SUCCESS_SOME_INVALID,
                INDEX_FIRST.getOneBased(), outOfBoundIndex.getOneBased());

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.addRoute(validRoute);
        expectedModel.commitRouteList();

        assertCommandSuccess(createRouteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRoute_throwsCommandException() throws Exception {
        Set<Order> orders = new HashSet<>();
        orders.add(validOrder);
        Set<Index> orderIds = new HashSet<>();
        orderIds.add(INDEX_FIRST);
        Route validRoute = new Route(orders);

        CreateRouteCommand createRouteCommand = new CreateRouteCommand(orderIds);
        Model modelWithDuplicate = new ModelManager(model.getOrderBook(), model.getRouteList(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        modelWithDuplicate.addRoute(validRoute);
        modelWithDuplicate.commitRouteList();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateRouteCommand.MESSAGE_DUPLICATE_ROUTE);
        createRouteCommand.execute(modelWithDuplicate, commandHistory);
    }

    @Test
    public void execute_orderIndexTooLarge_throwsCommandException() throws Exception {
        Set<Index> orderIds = new HashSet<>();
        orderIds.add(outOfBoundIndex);
        CreateRouteCommand createRouteCommand = new CreateRouteCommand(orderIds);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        createRouteCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Set<Index> indexOne = new HashSet<>();
        indexOne.add(INDEX_FIRST);
        Set<Index> indexTwo = new HashSet<>();
        indexOne.add(INDEX_SECOND);
        CreateRouteCommand addFirstCommand = new CreateRouteCommand(indexOne);
        CreateRouteCommand addSecondCommand = new CreateRouteCommand(indexTwo);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        CreateRouteCommand addFirstCommandCopy = new CreateRouteCommand(indexOne);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different destination -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
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
