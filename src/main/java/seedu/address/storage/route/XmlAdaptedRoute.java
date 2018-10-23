package seedu.address.storage.route;

import static seedu.address.model.TaggedObject.MESSAGE_INVALID_ID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.order.Order;
import seedu.address.model.route.Route;
import seedu.address.storage.XmlAdaptedOrder;

/**
 * JAXB-friendly version of the Route.
 */
public class XmlAdaptedRoute {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Route's %s field is missing!";

    @XmlAttribute
    @XmlID
    private String tag;

    @XmlElement(required = true)
    private String source;
    @XmlElement(required = true)
    private List<XmlAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRoute.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRoute() {
    }

    /**
     * Constructs an {@code XmlAdaptedRoute} with the given route details.
     */
    public XmlAdaptedRoute(String source, List<XmlAdaptedOrder> orders) {
        this.tag = UUID.randomUUID().toString();
        this.source = source;
        if (orders == null) {
            this.orders = new ArrayList<>();
        } else {
            this.orders = new ArrayList<>(orders);
        }
    }

    /**
     * Converts a given Route into this class for JAXB use.
     *
     * @param route future changes to this will not affect the created XmlAdaptedRoute
     */
    public XmlAdaptedRoute(Route route) {
        tag = route.getTag().toString();
        source = route.getSource().value;
        orders = route.getOrders().stream()
                .map(XmlAdaptedOrder::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted common object into the model's Route object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted route
     */
    public Route toModelType() throws IllegalValueException {
        final List<Order> orderStore = new ArrayList<>();
        for (XmlAdaptedOrder orderItem : orders) {
            orderStore.add(orderItem.toModelType());
        }

        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(source)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelSource = new Address(source);

        if (orderStore.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName()));
        }

        final Set<Order> modelOrder = new HashSet<>(orderStore);

        UUID modelTag;

        try {
            modelTag = UUID.fromString(tag);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ID);
        }

        return new Route(modelTag, modelSource, modelOrder);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRoute)) {
            return false;
        }

        XmlAdaptedRoute otherRoute = (XmlAdaptedRoute) other;
        return Objects.equals(source, otherRoute.source)
                && Objects.equals(orders, otherRoute.orders);
    }
}
