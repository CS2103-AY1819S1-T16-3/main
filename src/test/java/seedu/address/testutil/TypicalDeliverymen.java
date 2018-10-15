package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * A utility class containing a list of {@code Deliverymen} objects to be used in tests.
 */
public class TypicalDeliverymen {

    public static final Deliveryman CHIKAO = new DeliverymanBuilder().withName("Hoh Chi Kao")
            .withId(0).build();
    public static final Deliveryman MANIKA = new DeliverymanBuilder().withName("Manika Monuela")
            .withId(1).build();
    public static final Deliveryman RAJUL = new DeliverymanBuilder().withName("Rajul Rahesh")
            .withId(2).build();
    public static final Deliveryman YINJING = new DeliverymanBuilder().withName("Tan Yin Jing")
            .withId(3).build();

    /**
     * Returns a {@code DeliverymenList} with all the typical Deliverymen.
     */
    public static DeliverymenList getTypicalDeliverymenList() {
        DeliverymenList dl = new DeliverymenList();
        for (Deliveryman dman : getTypicalDeliverymen()) {
            dl.addDeliveryman(dman);
        }
        return dl;
    }

    public static List<Deliveryman> getTypicalDeliverymen() {
        return new ArrayList<>(Arrays.asList(CHIKAO, MANIKA, RAJUL, YINJING));
    }
}
