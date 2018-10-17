package seedu.address.testutil.route;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.route.Route;
import seedu.address.testutil.TypicalOrders;

/**
 * A utility class to help with building Route objects.
 */
public class RouteBuilder {

    public static final String DEFAULT_SOURCE = "12 Clementi Rd";
    public static final Order DEFAULT_ORDER = TypicalOrders.ALICE;

    private Address source;
    private Set<Order> orders = new HashSet<>();
    private UUID id;

    public RouteBuilder() {
        source = new Address(DEFAULT_SOURCE);
    }

    /**
     * Initializes the RouteBuilder with the data of {@code routeToCopy}.
     */
    public RouteBuilder(Route routeToCopy) {
        id = routeToCopy.getId();
        source = routeToCopy.getSource();
        orders = routeToCopy.getOrders();
    }

    /**
     * Sets the {@code id } of the {@code Route} that we are building.
     */
    public RouteBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    /**
     * Sets the {@code source} of the {@code Route} that we are building.
     */
    public RouteBuilder withSource(String source) {
        this.source = new Address(source);
        return this;
    }

    /**
     * Sets the {@code orders} of the {@code Route} that we are building.
     */
    public RouteBuilder withOrders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    /**
     * Adds the {@code order} to the set of {@code orders} of the {@code Route} that we are building.
     */
    public RouteBuilder withOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    /**
     * Builds and returns the route it builds.
     */
    public Route build() {
        if (id != null) {
            return new Route(id, source, destination);
        } else {
            return new Route(source, destination);
        }
    }

}
