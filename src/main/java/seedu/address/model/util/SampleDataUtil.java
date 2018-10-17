package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UsersList;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.Route;
import seedu.address.model.route.RouteList;
import seedu.address.model.user.User;

/**
 * Contains utility methods for populating {@code OrderBook} with sample data.
 */
public class SampleDataUtil {
    public static Order[] getSampleOrders() {
        Order[] orders = new Order[]{
            new Order(new Name("Alex Yeoh"), new Phone("87438807"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Ice Tea")),
            new Order(new Name("Bernice Yu"), new Phone("99272758"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Chicken Rice")),
            new Order(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Nasi Goreng")),
            new Order(new Name("David Li"), new Phone("91031282"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Satay")),
            new Order(new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Fish and Chips")),
            new Order(new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new OrderDate("01-10-2018 10:00:00"),
                        getFoodSet("Sugercane Juice"))
        };
        return orders;
    }

    public static Route[] getSampleRoutes() {
        Set<Order> orderSetA = new HashSet<>();
        Set<Order> orderSetB = new HashSet<>();
        orderSetA.add(getSampleOrders()[0]);
        orderSetA.add(getSampleOrders()[1]);
        orderSetA.add(getSampleOrders()[2]);
        orderSetB.add(getSampleOrders()[3]);
        orderSetB.add(getSampleOrders()[4]);
        orderSetB.add(getSampleOrders()[5]);
        return new Route[] {
            new Route(orderSetA),
            new Route(orderSetB)
        };
    }

    public static Deliveryman[] getSampleDeliverymen() {
        return new Deliveryman[]{
            new Deliveryman(new Name("Hoh Chi Kao")),
            new Deliveryman(new Name("Tan Yin Jing")),
            new Deliveryman(new Name("Rajul Rahesh")),
            new Deliveryman(new Name("Manika Monuela"))
        };
    }

    public static ReadOnlyOrderBook getSampleOrderBook() {
        OrderBook sampleAb = new OrderBook();
        for (Order sampleOrder : getSampleOrders()) {
            sampleAb.addOrder(sampleOrder);
        }
        return sampleAb;
    }

    public static ReadOnlyRouteList getSampleRouteList() {
        RouteList sampleRl = new RouteList();
        for (Route sampleRoute : getSampleRoutes()) {
            sampleRl.addRoute(sampleRoute);
        }
        return sampleRl;
    }

    public static User[] getSampleUsers() {
        return new User[] {
            new User(new Name("Alice Pauline"), new Username("alicepauline"), new Password("alicepauline01")),
            new User(new Name("Benson Meier"), new Username("bensonmeier"), new Password("bensonmeier02")),
            new User(new Name("Carl Kurz"), new Username("carlkurz"), new Password("carlkurz03")),
            new User(new Name("Hoon Meier"), new Username("hoonmeier"), new Password("hoonmeier04"))
        };
    }

    public static ReadOnlyUsersList getSampleUsersList() {
        UsersList usersList = new UsersList();
        for (User user : getSampleUsers()) {
            usersList.addUser(user);
        }
        return usersList;
    }

    public static DeliverymenList getSampleDeliverymenList() {
        DeliverymenList sampleDl = new DeliverymenList();
        for (Deliveryman sampleD : getSampleDeliverymen()) {
            sampleDl.addDeliveryman(sampleD);
        }
        return sampleDl;
    }

    /**
     * Returns a food set containing the list of strings given.
     */
    public static Set<Food> getFoodSet(String... strings) {
        return Arrays.stream(strings)
                .map(Food::new)
                .collect(Collectors.toSet());
    }
}
