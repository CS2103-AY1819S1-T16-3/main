package seedu.address.storage.route;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.storage.route.XmlAdaptedRoute.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRoutes.ROUTE_ALICE_BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.storage.XmlAdaptedOrder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalRoutes;

public class XmlAdaptedRouteTest {
    private static final int VALID_ID = 1;
    private static final String INVALID_ADDRESS = " ";
    private static final List<XmlAdaptedOrder> VALID_ORDERS = TypicalRoutes.ROUTE_ALICE_BENSON.getOrders().stream()
            .map(XmlAdaptedOrder::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRouteDetails_returnsRoute() throws Exception {
        XmlAdaptedRoute route = new XmlAdaptedRoute(ROUTE_ALICE_BENSON);
        assertEquals(ROUTE_ALICE_BENSON, route.toModelType());
    }

    @Test
    public void toModelType_invalidSource_throwsIllegalValueException() {
        XmlAdaptedRoute route = new XmlAdaptedRoute(VALID_ID, INVALID_ADDRESS, VALID_ORDERS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, route::toModelType);
    }

    @Test
    public void toModelType_nullSource_throwsIllegalValueException() {
        XmlAdaptedRoute route = new XmlAdaptedRoute(VALID_ID, null, VALID_ORDERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, route::toModelType);
    }

    @Test
    public void toModelType_nullOrders_throwsIllegalValueException() {
        XmlAdaptedRoute route = new XmlAdaptedRoute(VALID_ID, VALID_ADDRESS_AMY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Order.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, route::toModelType);
    }

}
