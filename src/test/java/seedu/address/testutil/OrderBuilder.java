package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleOrderUtil;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Address address;
    private Set<Food> food;

    public OrderBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        food = new HashSet<>();
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        name = orderToCopy.getName();
        phone = orderToCopy.getPhone();
        address = orderToCopy.getAddress();
        food = new HashSet<Food>(orderToCopy.getFood());
    }

    /**
     * Sets the {@code Name} of the {@code Order} that we are building.
     */
    public OrderBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code food} into a {@code Set<Food>} and set it to the {@code Order} that we are building.
     */
    public OrderBuilder withFood(String ... food) {
        this.food = SampleOrderUtil.getOrderSet(food);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Order} that we are building.
     */
    public OrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Order} that we are building.
     */
    public OrderBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Order build() {
        return new Order(name, phone, address, food);
    }
}
