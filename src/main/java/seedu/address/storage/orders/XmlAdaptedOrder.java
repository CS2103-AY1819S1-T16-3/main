package seedu.address.storage.orders;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.storage.XmlAdaptedName;

import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JAXB-friendly version of the Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private Set<XmlAdaptedName> food = new HashSet<>();

    /**
     * Constructs an XmlAdaptedOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOrder() {}

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given person details.
     */
    public XmlAdaptedOrder(String name, String phone, String address, Set<XmlAdaptedName> food) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        if (food != null) {
            this.food = new HashSet<>(food);
        }
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        food = source.getFood().stream()
                .map(XmlAdaptedName::new)
                .collect(Collectors.toSet());
    }

    /**
     * Converts this jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Order toModelType() throws IllegalValueException {
        final Set<Name> orderFood = new HashSet<>();
        for (XmlAdaptedName f : food) {
            orderFood.add(f.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Name> modelFood = new HashSet<>(orderFood);
        return new Order(modelName, modelPhone, modelAddress, modelFood);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOrder)) {
            return false;
        }

        XmlAdaptedOrder otherOrder = (XmlAdaptedOrder) other;
        return Objects.equals(name, otherOrder.name)
                && Objects.equals(phone, otherOrder.phone)
                && Objects.equals(address, otherOrder.address)
                && food.equals(otherOrder.food);
    }
}
