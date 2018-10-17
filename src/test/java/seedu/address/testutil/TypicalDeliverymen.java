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

    public static final String CHIKAO_ID = "0a52f570-4f41-4460-b303-e0567910e9b3";
    public static final String MANIKA_ID = "e6f8a44a-fc0c-4a38-b650-4f5a9947fd31";
    public static final String RAJUL_ID = "778909e5-f134-472a-bfd8-d34b35cf0b62";
    public static final String YINJING_ID = "71cac1b0-33ea-4d0e-b1bd-c9e72cd3351f";

    public static final Deliveryman CHIKAO = new DeliverymanBuilder().withName("Hoh Chi Kao")
            .withId(CHIKAO_ID).build();
    public static final Deliveryman MANIKA = new DeliverymanBuilder().withName("Manika Monuela")
            .withId(RAJUL_ID).build();
    public static final Deliveryman RAJUL = new DeliverymanBuilder().withName("Rajul Rahesh")
            .withId(MANIKA_ID).build();
    public static final Deliveryman YINJING = new DeliverymanBuilder().withName("Tan Yin Jing")
            .withId(YINJING_ID).build();

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
