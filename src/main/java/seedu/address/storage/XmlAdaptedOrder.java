package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * JAXB-friendly version of the Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    @XmlAttribute
    @XmlID
    private String id;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private List<XmlAdaptedFood> food = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOrder() {
    }

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given order details.
     */
    public XmlAdaptedOrder(int id, String name, String phone, String address, String date, List<XmlAdaptedFood> food) {
        this.id = Integer.toString(id);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;

        if (food == null) {
            this.food = new ArrayList<>();
        } else {
            this.food = new ArrayList<>(food);
        }
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        id = Integer.toString(source.getId());
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        date = source.getDate().toString();
        food = source.getFood().stream()
                .map(XmlAdaptedFood::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Order toModelType() throws IllegalValueException {
        final List<Food> foodStore = new ArrayList<>();
        for (XmlAdaptedFood foodItem : food) {
            foodStore.add(foodItem.toModelType());
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


        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!OrderDate.isValidDate(date)) {
            throw new IllegalValueException(OrderDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final OrderDate modelDate = new OrderDate(date);

        if (foodStore.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName()));
        }

        int modelId;

        try {
            modelId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Not an ID!");
        }

        final Set<Food> modelFood = new HashSet<>(foodStore);


        return new Order(modelId, modelName, modelPhone, modelAddress, modelDate, modelFood);
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
        return id.equals(otherOrder.id)
                && Objects.equals(name, otherOrder.name)
                && Objects.equals(phone, otherOrder.phone)
                && Objects.equals(address, otherOrder.address)
                && Objects.equals(date, otherOrder.date)
                && food.equals(otherOrder.food);
    }
}
