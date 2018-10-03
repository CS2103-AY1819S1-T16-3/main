package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;

/**
 * Wraps all data at the orders-lost level
 * Duplicates are not allowed (by .isSameOrders comparison)
 */
public class OrdersList implements ReadOnlyOrdersList {

    private final UniqueOrderList orders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orders = new UniqueOrderList();
    }

    public OrdersList() {}

    /**
     * Creates an OrdersList using the Persons in the {@code toBeCopied}
     */
    public OrdersList(ReadOnlyOrdersList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setPersons(orders);
    }

    /**
     * Resets the existing data of this {@code OrdersList} with {@code newData}.
     */
    public void resetData(ReadOnlyOrdersList newData) {
        requireNonNull(newData);

        setOrders(newData.getOrdersList());
    }

    //// order-level operations

    /**
     * Returns true if a order with the same identity as {@code order} exists in the orders list.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds an order to the orders list.
     * The order must not already exist in the orders list.
     */
    public void addOrder(Order p) {
        orders.add(p);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the order list.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the order list.
     */
    public void updateOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code OrdersList}.
     * {@code key} must exist in the order list.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Order> getOrdersList() {
        return orders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrdersList // instanceof handles nulls
                && orders.equals(((OrdersList) other).orders));
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
