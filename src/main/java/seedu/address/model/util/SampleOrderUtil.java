package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.OrdersList;
import seedu.address.model.ReadOnlyOrdersList;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods for populating {@code FoodZoom} with sample data.
 */
public class SampleOrderUtil {
    public static Order[] getSampleOrders() {
        Order[] orders = {
            new Order(new Name("Alex Yeoh"), new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getOrderSet("Roti Prata")),
            new Order(new Name("Bernice Yu"), new Phone("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getOrderSet("Maggi Goreng")),
            new Order(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getOrderSet("Ice Milo")),
            new Order(new Name("David Li"), new Phone("91031282"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getOrderSet("Teh Tarik")),
            new Order(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getOrderSet("Nasi Goreng Pataya")),
            new Order(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getOrderSet("Chicken Tikka Masala"))
        };

        return orders;
    }

    public static ReadOnlyOrdersList getSampleOrdersList() {
        OrdersList sampleOl = new OrdersList();
        for (Order sampleOrder : getSampleOrders()) {
            sampleOl.addOrder(sampleOrder);
        }
        return sampleOl;
    }

    public static Set<Name> getOrderSet(String... names) {
        return Arrays.stream(names)
                .map(Name::new)
                .collect(Collectors.toSet());
    }
}
