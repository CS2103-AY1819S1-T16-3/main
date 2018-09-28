package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;

/**
 * Unmodifiable view of an orders list
 */
public interface ReadOnlyOrdersList {
    /**
     * Returns an unmodifiable view of the order list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<Order> getOrdersList();
}
