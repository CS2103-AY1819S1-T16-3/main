package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.order.Order;
import seedu.address.model.route.Route;
import seedu.address.model.route.RouteList;
import seedu.address.testutil.route.RouteBuilder;

/**
 * A utility class containing a list of {@code Route} objects to be used in tests.
 */
public class TypicalRoutes {
    public static final String ALICE_ID = "92c54d56-e971-407a-81e7-5df71c3c82fc";
    public static final String CARL_ID = "6c974f14-e5f5-4652-ba14-4df75e259b25";
    public static final String DANIEL_ID = "ec11e415-4794-44c2-9d29-861d359d8b76";
    private static final Order ALICE = TypicalOrders.ALICE;
    private static final Order BENSON = TypicalOrders.BENSON;
    private static final Order CARL = TypicalOrders.CARL;
    private static final Order DANIEL = TypicalOrders.DANIEL;

    public static final Route ROUTE_ALICE_BENSON = new RouteBuilder()
            .withId(ALICE_ID).withOrder(ALICE).withOrder(BENSON).build();

    public static final Route ROUTE_CARL = new RouteBuilder()
            .withId(CARL_ID).withOrder(CARL).build();

    public static final Route ROUTE_DANIEL = new RouteBuilder()
            .withId(DANIEL_ID).withOrder(DANIEL).build();

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static RouteList getTypicalRouteList() {
        RouteList rl = new RouteList();
        for (Route route : getTypicalRoutes()) {
            rl.addRoute(route);
        }
        return rl;
    }

    public static List<Route> getTypicalRoutes() {
        return new ArrayList<>(Arrays.asList(ROUTE_ALICE_BENSON, ROUTE_CARL, ROUTE_DANIEL));
    }
}
