package seedu.address.model.route;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.IdObject;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;

/**
 * Represents a Route in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Route extends IdObject {
    /** Tracker for assigning id. */
    private static int idCounter = 0;

    private static final String DEFAULT_SOURCE = "12 Clementi Rd";

    // Identity fields
    private final Address source;
    private final Set<Order> orders = new HashSet<>();

    /**
     * Creates a new instance of Route with the destination.
     * @param orders The orders delivered in this route.
     */
    public Route(Set<Order> orders) {
        super(idCounter++);
        requireNonNull(orders);
        this.source = new Address(DEFAULT_SOURCE);
        this.orders.addAll(orders);
    }

    public Route(Address source, Set<Order> orders) {
        super(idCounter++);
        requireAllNonNull(source, orders);
        this.source = source;
        this.orders.addAll(orders);
    }

    public Address getSource() {
        return source;
    }

    /**
     * Returns an order set
     */
    public Set<Order> getOrders() {
        return orders;
    }

    /**
     * Returns true if both routes have all the same identity fields.
     * This defines a weaker notion of equality between two routes.
     */
    public boolean isSameRoute(Route otherRoute) {
        if (otherRoute == this) {
            return true;
        }

        return otherRoute != null
                && otherRoute.getSource().equals(getSource())
                && otherRoute.getOrders().equals(getOrders());
    }

    /**
     * Returns true if both routes have the same identity and data fields.
     * This defines a stronger notion of equality between two routes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Route)) {
            return false;
        }

        Route otherRoute = (Route) other;
        return otherRoute.getSource().equals(getSource())
                && otherRoute.getOrders().equals(getOrders());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(source, orders);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Route")
                .append("Source: ")
                .append(getSource())
                .append(" Orders: ");
        getOrders().forEach(builder::append);
        return builder.toString();
    }
}
