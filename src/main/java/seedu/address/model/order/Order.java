package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.IdObject;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Represents an Order in the order book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order extends IdObject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final OrderDate orderDate;
    private final Set<Food> food = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(Name name, Phone phone, Address address, OrderDate orderDate, Set<Food> food) {
        requireAllNonNull(name, phone, address, orderDate, food);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.food.addAll(food);

        this.orderDate = orderDate;
    }

    /**
     * This constructor is used to create an {@code order} with a specified id.
     */
    public Order(UUID id, Name name, Phone phone, Address address, OrderDate orderDate, Set<Food> food) {
        super(id);
        requireAllNonNull(name, phone, address, orderDate, food);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.food.addAll(food);

        this.orderDate = orderDate;
    }
    public Name getName() {
        return name;
    }

    public OrderDate getDate() {
        return this.orderDate;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns a food set
     */
    public Set<Food> getFood() {
        return food;
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getName().equals(getName())
                && (otherOrder.getPhone().equals(getPhone()))
                && (otherOrder.getDate().equals(getDate()));
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return ((getId() == null && otherOrder.getId() == null)
                || getId().equals(otherOrder.getId()))
                && otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getAddress().equals(getAddress())
                && (otherOrder.getDate().equals(getDate()))
                && otherOrder.getFood().equals(getFood());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, food);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" Date: ")
                .append(getDate())
                .append(" Food: ");
        getFood().forEach(builder::append);
        return builder.toString();
    }

}
