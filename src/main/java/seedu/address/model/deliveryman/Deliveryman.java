package seedu.address.model.deliveryman;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.UUID;

import seedu.address.model.IdObject;
import seedu.address.model.person.Name;

/**
 * Represents a Deliveryman in FoodZoom.
 * Guarantees: has a name that is unique.
 */
public class Deliveryman extends IdObject {

    private final Name name;
    //TODO:  add field for orders/routes attached to deliveryman.

    public Deliveryman(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    /** This constructor is used when the {@code id} is specified. */
    public Deliveryman(UUID id, Name name) {
        super(id);
        requireAllNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns if this is the same person as {@code other}
     */
    public boolean isSameDeliveryman(Deliveryman other) {
        if (other == this) {
            return true;
        }

        return other != null && other.getName().equals(getName());
    }

    /**
     * Returns true if both deliverymen have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deliveryman)) {
            return false;
        }

        Deliveryman otherPerson = (Deliveryman) other;
        return otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
