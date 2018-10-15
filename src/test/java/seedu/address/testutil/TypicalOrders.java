package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.OrderBook;
import seedu.address.model.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final Order ALICE = new OrderBuilder().withName("Alice Pauline")
            .withId(0)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withDate("01-10-2018 10:00:00")
            .withFood("Roti Prata").build();

    public static final Order BENSON = new OrderBuilder().withName("Benson Meier")
            .withId(1)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withDate("01-10-2018 10:00:00")
            .withFood("Egg Fried Rice", "Milo").build();

    public static final Order CARL = new OrderBuilder().withId(2).withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").withDate("01-10-2018 10:00:00").withFood("Milkshake").build();
    public static final Order DANIEL = new OrderBuilder().withId(3).withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street").withDate("01-10-2018 10:00:00").withFood("Fish and Chips").build();
    public static final Order ELLE = new OrderBuilder().withId(4).withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").withDate("01-10-2018 10:00:00").withFood("Chicken Chop").build();
    public static final Order FIONA = new OrderBuilder().withId(5).withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").withDate("01-10-2018 10:00:00").withFood("Tuna Sandwich").build();
    public static final Order GEORGE = new OrderBuilder().withId(6).withName("George Best").withPhone("9482442")
            .withAddress("4th street").withDate("01-10-2018 10:00:00").withFood("Ice cream").build();

    //Manually added
    public static final Order HOON = new OrderBuilder().withId(7).withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").withDate("01-10-2018 10:00:00").withFood("Chocolate Milkshake").build();
    public static final Order IDA = new OrderBuilder().withId(8).withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").withDate("01-10-2018 10:00:00").withFood("Bubble Tea").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY = new OrderBuilder().withId(0).withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY).withFood(VALID_FOOD_BURGER).build();
    public static final Order BOB = new OrderBuilder().withId(1).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withDate(VALID_DATE_BOB).withFood(VALID_FOOD_RICE)
            .build();

    public static final String KEYWORD_NAME_MATCHING_MEIER = "n/Meier"; // A keyword name that matches MEIER
    public static final String KEYWORD_PHONE_MATCHING_BENSON = "p/98765432"; // A keyword phone that matches FIONA

    private TypicalOrders() {
    } // prevents instantiation

    /**
     * Returns an {@code OrderBook} with all the typical orders.
     */
    public static OrderBook getTypicalOrderBook() {
        OrderBook ab = new OrderBook();
        for (Order order : getTypicalOrders()) {
            ab.addOrder(order);
        }
        return ab;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
