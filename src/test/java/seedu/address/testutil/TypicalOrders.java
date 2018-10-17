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
    public static final String ALICE_ID = "92c54d56-e971-407a-81e7-5df71c3c82fc";
    public static final String BENSON_ID = "e328789f-c707-49a9-8147-d93e213fb1d7";
    public static final String CARL_ID = "6c974f14-e5f5-4652-ba14-4df75e259b25";
    public static final String DANIEL_ID = "ec11e415-4794-44c2-9d29-861d359d8b76";
    public static final String ELLE_ID = "d0abc707-2363-46b2-8c45-92667f3d9e0e";
    public static final String FIONA_ID = "77bd3598-2d3a-450e-a37b-a79de94792dc";
    public static final String GEORGE_ID = "e7dc53e1-a1a0-4208-8f29-a6b24f215ff9";
    public static final String HOON_ID = "77bb5e80-d0ef-4898-b348-a63267c67385";
    public static final String IDA_ID = "df99ea87-75d9-4dba-82f8-2b11dca6789d";
    public static final String AMY_ID = "2760c196-a7ec-42c3-9bb4-ff0bbfd971c6";
    public static final String BOB_ID = "2b3363a5-899f-4006-9cb6-cb016e3c20cd";

    public static final Order ALICE = new OrderBuilder().withName("Alice Pauline")
            .withId(ALICE_ID)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withDate("01-10-2018 10:00:00")
            .withFood("Roti Prata").build();

    public static final Order BENSON = new OrderBuilder().withName("Benson Meier")
            .withId(BENSON_ID)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withDate("01-10-2018 10:00:00")
            .withFood("Egg Fried Rice", "Milo").build();

    public static final Order CARL = new OrderBuilder().withId(CARL_ID).withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").withDate("01-10-2018 10:00:00").withFood("Milkshake").build();
    public static final Order DANIEL = new OrderBuilder().withId(DANIEL_ID).withName("Daniel Meier")
            .withPhone("87652533").withAddress("10th street").withDate("01-10-2018 10:00:00")
            .withFood("Fish and Chips").build();
    public static final Order ELLE = new OrderBuilder().withId(ELLE_ID).withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").withDate("01-10-2018 10:00:00").withFood("Chicken Chop").build();
    public static final Order FIONA = new OrderBuilder().withId(FIONA_ID).withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").withDate("01-10-2018 10:00:00").withFood("Tuna Sandwich").build();
    public static final Order GEORGE = new OrderBuilder().withId(GEORGE_ID).withName("George Best").withPhone("9482442")
            .withAddress("4th street").withDate("01-10-2018 10:00:00").withFood("Ice cream").build();

    //Manually added
    public static final Order HOON = new OrderBuilder().withId(HOON_ID).withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").withDate("01-10-2018 10:00:00").withFood("Chocolate Milkshake").build();
    public static final Order IDA = new OrderBuilder().withId(IDA_ID).withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").withDate("01-10-2018 10:00:00").withFood("Bubble Tea").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY = new OrderBuilder().withId(AMY_ID).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
            .withFood(VALID_FOOD_BURGER).build();
    public static final Order BOB = new OrderBuilder().withId(BOB_ID).withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).withDate(VALID_DATE_BOB)
            .withFood(VALID_FOOD_RICE).build();

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
