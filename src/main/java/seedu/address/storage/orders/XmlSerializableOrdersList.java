package seedu.address.storage.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OrdersList;
import seedu.address.model.ReadOnlyOrdersList;
import seedu.address.model.order.Order;

/**
 * An Immutable OrdersList that is serializable to XML format
 */
@XmlRootElement(name = "orderslist")
public class XmlSerializableOrdersList {
    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";

    @XmlElement
    private List<XmlAdaptedOrder> orders;

    /**
     * Creates an empty XmlSerializableOrdersList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableOrdersList() {
        orders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableOrdersList(ReadOnlyOrdersList src) {
        this();
        orders.addAll(src.getOrdersList().stream().map(XmlAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this orderslist into the model's {@code OrdersList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedOrder}.
     */
    public OrdersList toModelType() throws IllegalValueException {
        OrdersList ordersList = new OrdersList();
        for (XmlAdaptedOrder o : orders) {
            Order person = o.toModelType();
            if (ordersList.hasOrder(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            ordersList.addOrder(person);
        }
        return ordersList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableOrdersList)) {
            return false;
        }
        return orders.equals(((XmlSerializableOrdersList) other).orders);
    }
}
