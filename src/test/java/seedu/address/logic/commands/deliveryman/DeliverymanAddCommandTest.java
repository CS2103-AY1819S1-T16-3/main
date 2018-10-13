package seedu.address.logic.commands.deliveryman;

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
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.order.Order;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.Route;
import seedu.address.model.user.User;
import seedu.address.testutil.DeliverymanBuilder;

/**
 * Contains tests related to the DeliverymanAddCommand class.
 */
public class DeliverymanAddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeliverymanAddCommand(null);
    }

    @Test
    public void execute_deliverymanAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeliverymanAdded modelStub = new ModelStubAcceptingDeliverymanAdded();
        Deliveryman validDeliveryman = new DeliverymanBuilder().withName("Linus").build();

        CommandResult commandResults = new DeliverymanAddCommand(validDeliveryman).execute(modelStub, commandHistory);

        assertEquals(String.format(DeliverymanAddCommand.MESSAGE_SUCCESS, validDeliveryman),
            commandResults.feedbackToUser);
        assertEquals(Arrays.asList(validDeliveryman), modelStub.deliverymenAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        Deliveryman validDeliveryman = new DeliverymanBuilder().withName("Matthew").build();
        DeliverymanAddCommand deliverymanAddCommand = new DeliverymanAddCommand(validDeliveryman);
        ModelStub modelStub = new ModelStubWithDeliveryman(validDeliveryman);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeliverymanAddCommand.MESSAGE_DUPLICATE_DELIVERYMAN);
        deliverymanAddCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Deliveryman matthew = new DeliverymanBuilder().withName("Matthew").build();
        Deliveryman linus = new DeliverymanBuilder().withName("Linus").build();
        DeliverymanAddCommand addMatthewCommand = new DeliverymanAddCommand(matthew);
        DeliverymanAddCommand addLinusCommand = new DeliverymanAddCommand(linus);

        // same object -> returns true
        assertTrue(addMatthewCommand.equals(addMatthewCommand));

        // same values -> returns true
        DeliverymanAddCommand addMatthewCommandCopy = new DeliverymanAddCommand(matthew);
        assertTrue(addMatthewCommand.equals(addMatthewCommandCopy));

        // different types -> returns false
        assertFalse(addMatthewCommand.equals(1));

        // null -> returns false
        assertFalse(addMatthewCommand.equals(null));

        // different person -> returns false
        assertFalse(addMatthewCommand.equals(addLinusCommand));
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
    private class ModelStubWithDeliveryman extends ModelStub {
        private final Deliveryman deliveryman;

        ModelStubWithDeliveryman(Deliveryman deliveryman) {
            requireNonNull(deliveryman);
            this.deliveryman = deliveryman;
        }

        @Override
        public boolean hasDeliveryman(Deliveryman deliveryman) {
            requireNonNull(deliveryman);
            return this.deliveryman.isSameDeliveryman(deliveryman);
        }
    }

    /**
     * A Model stub that always accept the order being added.
     */
    private class ModelStubAcceptingDeliverymanAdded extends ModelStub {
        final ArrayList<Deliveryman> deliverymenAdded = new ArrayList<>();

        @Override
        public boolean hasDeliveryman(Deliveryman deliveryman) {
            requireNonNull(deliveryman);
            return deliverymenAdded.stream().anyMatch(deliveryman::isSameDeliveryman);
        }

        @Override
        public void addDeliveryman(Deliveryman deliveryman) {
            requireNonNull(deliveryman);
            deliverymenAdded.add(deliveryman);
        }

        @Override
        public void commitDeliverymenList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public DeliverymenList getDeliverymenList() {
            return new DeliverymenList();
        }
    }
}
