package seedu.address.testutil;

import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.person.Name;

/**
 * A utility class to help build a deliveryman
 */
public class DeliverymanBuilder {
    public static final String DEFAULT_NAME = "Deliver E";

    private Name name;
    private int id = -1;

    public DeliverymanBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the DeliverymanBuilder with the data of {@code deliverymanToCopy}.
     */
    public DeliverymanBuilder(Deliveryman deliverymanToCopy) {
        id = deliverymanToCopy.getId();
        name = deliverymanToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Deliveryman} that we are building.
     */
    public DeliverymanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code id} of the {@code Deliveryman} that we are building
     * @param id
     */
    public DeliverymanBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public Deliveryman build() {
        if (id >= 0) {
            return new Deliveryman(id, name);
        } else {
            return new Deliveryman(name);
        }
    }
}
