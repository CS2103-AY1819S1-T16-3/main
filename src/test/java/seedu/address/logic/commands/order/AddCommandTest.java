package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.order.Order;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.Route;
import seedu.address.model.user.User;
import seedu.address.testutil.OrderBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        Order validOrder = new OrderBuilder().build();

        CommandResult commandResult = new AddCommand(validOrder).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validOrder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validOrder), modelStub.ordersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        Order validOrder = new OrderBuilder().build();
        AddCommand addCommand = new AddCommand(validOrder);
        ModelStub modelStub = new ModelStubWithOrder(validOrder);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_ORDER);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Order alice = new OrderBuilder().withName("Alice").build();
        Order bob = new OrderBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeliveryman(Deliveryman deliveryman) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrderBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDeliverymenData(DeliverymenList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyOrderBook getOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DeliverymenList getDeliverymenList() {
            throw new AssertionError(" This message should not be called");
        }

        @Override
        public boolean hasOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOrder(Order target, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredUsersList(Predicate<User> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<User> getFilteredUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isRegisteredUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUsersList getUsersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUserLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void storeUserInSession(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getLoggedInUserDetails() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearUserInSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetRouteData(ReadOnlyRouteList newData) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRouteList getRouteList() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeliveryman(Deliveryman person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeliveryman(Deliveryman target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRoute(Route route) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRoute(Route target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Deliveryman> getFilteredDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRoute(Route route) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeliverymenList(Predicate<Deliveryman> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDeliverymenList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRoute(Route target, Route editedRoute) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Route> getFilteredRouteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRouteList(Predicate<Route> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean canUndoRouteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoRouteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoRouteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRouteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitRouteList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single order.
     */
    private class ModelStubWithOrder extends ModelStub {
        private final Order order;

        ModelStubWithOrder(Order order) {
            requireNonNull(order);
            this.order = order;
        }

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return this.order.isSameOrder(order);
        }
    }

    /**
     * A Model stub that always accept the order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();

        @Override
        public boolean hasOrder(Order person) {
            requireNonNull(person);
            return ordersAdded.stream().anyMatch(person::isSameOrder);
        }

        @Override
        public void addOrder(Order order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public void commitOrderBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyOrderBook getOrderBook() {
            return new OrderBook();
        }
    }

}
