package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.order.Order;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.Route;
import seedu.address.model.user.User;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Route> PREDICATE_SHOW_ALL_ROUTES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<User> PREDICATE_SHOW_ALL_USERS = unused -> true;
    /** {@code Predicate} that always evaluates to true */
    Predicate<Deliveryman> PREDICATE_SHOW_ALL_DELIVERYMEN = unused -> true;

    // ==================== order book/order related methods =======================

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyOrderBook newData);

    /**
     * Returns the OrderBook
     */
    ReadOnlyOrderBook getOrderBook();

    /**
     * Returns true if a person with the same identity as {@code order} exists in the order book.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the address book.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * {@code order} must not already exist in the address book.
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the address book.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the order book.
     */
    void updateOrder(Order target, Order editedOrder);

    /**
     * Returns an unmodifiable view of the filtered order list
     */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    /**
     * Returns true if the model has previous order book states to restore.
     */
    boolean canUndoOrderBook();

    /**
     * Returns true if the model has undone order book states to restore.
     */
    boolean canRedoOrderBook();

    /**
     * Restores the model's order book to its previous state.
     */
    void undoOrderBook();

    /**
     * Restores the model's order book to its previously undone state.
     */
    void redoOrderBook();

    /**
     * Saves the current order book state for undo/redo.
     */
    void commitOrderBook();

    // ======================== User related methods =========================

    /**
     * Return true if user exist inside user list.
     */
    boolean hasUser(User user);

    /**
     * Add user to usersList.
     */
    void addUser(User user);

    /**
     * Saves the current users list for undo/redo.
     */
    void commitUsersList();

    /**
     * Updates the filter of the filtered user list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredUsersList(Predicate<User> predicate);


    /**
     * Returns an unmodifiable view of the filtered users list
     */
    ObservableList<User> getFilteredUsersList();

    /**
     * Return true if user is registered with application.
     */
    boolean isRegisteredUser(User user);

    /**
     * Returns the UsersList
     */
    ReadOnlyUsersList getUsersList();


    /**
     * Return true if user is logged into FoodZoom.
     */
    boolean isUserLoggedIn();

    /**
     * Store the user session details.
     */
    void storeUserInSession(User user);

    /**
     * Return the logged in user details.
     */
    User getLoggedInUserDetails();

    /**
     * Clear the user session details.
     */
    void clearUserInSession();

    // ======================== Route related methods =========================

    /** Clears existing backing model and replaces with the provided new data. */
    void resetRouteData(ReadOnlyRouteList newData);

    /** Returns the RouteList */
    ReadOnlyRouteList getRouteList();

    /**
     * Returns true if a route with the same identity as {@code route} exists in the route list.
     */
    boolean hasRoute(Route route);

    /**
     * Deletes the given route.
     * The route must exist in the route list.
     */
    void deleteRoute(Route target);

    /**
     * Adds the given route.
     * {@code route} must not already exist in the route list.
     */
    void addRoute(Route route);

    /**
     * Replaces the given route {@code target} with {@code editedRoute}.
     * {@code target} must exist in the route list.
     * The route identity of {@code editedRoute} must not be the same as another existing route in the route list.
     */
    void updateRoute(Route target, Route editedRoute);

    /** Returns an unmodifiable view of the filtered route list */
    ObservableList<Route> getFilteredRouteList();

    /**
     * Updates the filter of the filtered route list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRouteList(Predicate<Route> predicate);

    /**
     * Returns true if the model has previous route list states to restore.
     */
    boolean canUndoRouteList();

    /**
     * Returns true if the model has undone route list states to restore.
     */
    boolean canRedoRouteList();

    /**
     * Restores the model's route list to its previous state.
     */
    void undoRouteList();

    /**
     * Restores the model's route list to its previously undone state.
     */
    void redoRouteList();

    /**
     * Saves the current route list state for undo/redo.
     */
    void commitRouteList();

    // ======================== Deliveryman related methods =========================

    /** Clears existing backing model and replaces with the provided new data. */
    void resetDeliverymenData(DeliverymenList newData);

    /** Returns the DeliverymenList */
    DeliverymenList getDeliverymenList();

    /**
     * Returns true if a deliveryman with the same identity as {@code deliveryman} exists in the address book.
     */
    boolean hasDeliveryman(Deliveryman deliveryman);

    /**
     * Deletes the given deliveryman.
     * The deliveryman must exist in the address book.
     */
    void deleteDeliveryman(Deliveryman target);

    /**
     * Adds the given deliveryman.
     * {@code deliveryman} must not already exist in the address book.
     */
    void addDeliveryman(Deliveryman deliveryman);

    /**
     * Replaces the given deliveryman {@code target} with {@code editedDeliveryman}.
     * {@code target} must exist in the address book.
     * The deliveryman identity of {@code editedDeliveryman} must not be the same as another
     * existing deliveryman in the deliverymen list.
     */
    void updateDeliveryman(Deliveryman target, Deliveryman editedDeliveryman);

    /** Returns an unmodifiable view of the filtered deliveryman list */
    ObservableList<Deliveryman> getFilteredDeliverymenList();

    /**
     * Updates the filter of the filtered deliveryman list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliverymenList(Predicate<Deliveryman> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoDeliverymenList();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoDeliverymenList();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoDeliverymenList();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoDeliverymenList();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitDeliverymenList();
}
