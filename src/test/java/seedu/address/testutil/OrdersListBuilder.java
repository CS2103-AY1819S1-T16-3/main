package seedu.address.testutil;

import seedu.address.model.OrdersList;
import seedu.address.model.order.Order;

/**
 * A utility class to help with building OrdersList objects.
 * Example usage: <br>
 *     {@code OrdersList ol = new OrdersListBuilder().withPerson("John", "Doe").build();}
 */
public class OrdersListBuilder {
    private OrdersList ordersList;

    public OrdersListBuilder() {
        ordersList = new OrdersList();
    }

    public OrdersListBuilder(OrdersList ordersList) {
        this.ordersList = ordersList;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public OrdersListBuilder withOrder(Order order) {
        ordersList.addOrder(order);
        return this;
    }

    public OrdersList build() {
        return ordersList;
    }
}
