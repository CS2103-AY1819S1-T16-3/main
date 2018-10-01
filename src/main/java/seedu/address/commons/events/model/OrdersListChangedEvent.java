package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyOrdersList;

/** Indicates the OrdersList in the model has changed*/
public class OrdersListChangedEvent extends BaseEvent {

    public final ReadOnlyOrdersList data;

    public OrdersListChangedEvent(ReadOnlyOrdersList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of orders " + data.getOrdersList().size();
    }
}
