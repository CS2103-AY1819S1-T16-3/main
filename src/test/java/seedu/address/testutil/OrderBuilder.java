package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "01-10-2018 10:00:00";
    public static final String DEFAULT_FOOD = "Fried Rice";

    private Name name;
    private Phone phone;
    private Address address;
    private OrderDate date;
    private Set<Food> food;
    private int id = -1;

    public OrderBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        date = new OrderDate(DEFAULT_DATE);
        food = SampleDataUtil.getFoodSet(DEFAULT_FOOD);
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        id = orderToCopy.getId();
        name = orderToCopy.getName();
        phone = orderToCopy.getPhone();
        address = orderToCopy.getAddress();
        date = orderToCopy.getDate();
        food = new HashSet<>(orderToCopy.getFood());
    }

    /**
     * Sets the {@code id} of the {@code Order} that we are building
     * @param id
     */
    public OrderBuilder withId(int id) {
        this.id = id;
        return this;
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
    public OrderBuilder withFood(String... food) {
        this.food = SampleDataUtil.getFoodSet(food);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public OrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Order} that we are building.
     */
    public OrderBuilder withDate(String orderDate) {
        this.date = new OrderDate(orderDate);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public OrderBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }


    /**
     * Builds and returns an order.
     */
    public Order build() {
        if (id >= 0) {
            return new Order(id, name, phone, address, date, food);
        } else {
            return new Order(name, phone, address, date, food);
        }
    }

}
