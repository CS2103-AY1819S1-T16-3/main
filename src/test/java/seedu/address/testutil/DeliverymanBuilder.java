package seedu.address.testutil;

import java.util.UUID;

import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.person.Name;

/**
 * A utility class to help build a deliveryman
 */
public class DeliverymanBuilder {
    public static final String DEFAULT_NAME = "Deliver E";

    private Name name;
    private UUID id;

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
     * @param idString
     */
    public DeliverymanBuilder withId(String idString) {
        this.id = UUID.fromString(idString);
        return this;
    }

    /**
     * Builds the {@code Deliveryman}
     */
    public Deliveryman build() {
        if (id != null) {
            return new Deliveryman(id, name);
        } else {
            return new Deliveryman(name);
        }
    }
}
