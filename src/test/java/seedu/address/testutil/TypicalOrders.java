package seedu.address.testutil;

import seedu.address.model.OrdersList;
import seedu.address.model.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final Order ALICE_TO = new OrderBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withFood("Roti Prata").build();
    public static final Order BENSON_TO = new OrderBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withFood("Ice Milo", "Teh Tarik").build();
    public static final Order CARL_TO = new OrderBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street")
            .withFood("Nasi Goreng").build();
    public static final Order DANIEL_TO = new OrderBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street")
            .withFood("Maggi Goreng").build();
    public static final Order ELLE_TO = new OrderBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave")
            .withFood("Maggi Goreng Pattaya").build();
    public static final Order FIONA_TO = new OrderBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo")
            .withFood("Paneer").build();
    public static final Order GEORGE_TO = new OrderBuilder().withName("George Best").withPhone("9482442")
            .withAddress("4th street")
            .withFood("Nasi Braniyi").build();
    public static final Order AH_BENG_TO = new OrderBuilder().withName("Ah Beng").withPhone("81234566")
            .withAddress("San Fran")
            .withFood("Tom Yum Maggi").build();

    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static OrdersList getTypicalOrdersList() {
        OrdersList ol = new OrdersList();
        for (Order order : getTypicalOrders()) {
            ol.addOrder(order);
        }
        return ol;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE_TO, BENSON_TO, CARL_TO, DANIEL_TO, ELLE_TO, FIONA_TO, GEORGE_TO, AH_BENG_TO));
    }
}
