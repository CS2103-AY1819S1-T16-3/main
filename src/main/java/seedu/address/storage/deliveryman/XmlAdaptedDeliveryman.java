package seedu.address.storage.deliveryman;

import static seedu.address.model.IdObject.MESSAGE_INVALID_ID;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.person.Name;

/**
 * Represents the XML for storage of Deliveryman
 */
public class XmlAdaptedDeliveryman {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deliveryman's %s field is missing!";

    @XmlAttribute
    @XmlID
    private String id;

    @XmlElement(required = true)
    private String name;

    public XmlAdaptedDeliveryman() {}

    /**
     * Constructs an {@code XmlAdapterDeliveryman} with the given person details.
     */
    public XmlAdaptedDeliveryman(String id, String name) {
        this.id = id;
        this.name = name;
    }
    /**
     * Constructs an {@code XmlAdapterDeliveryman} with the given person details.
     */
    public XmlAdaptedDeliveryman(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    /**
     * Converts a given deliveryman into this class for JAXB use.
     *
     * @param source
     */
    public XmlAdaptedDeliveryman(Deliveryman source) {
        id = source.getId().toString();
        name = source.getName().fullName;
    }

    /**
     * Converts this jaxb-friendly adapted deliveryman object into the model's Deliveryman object.
     *
     * @throws IllegalValueException If there were any data constraints violated.
     */
    public Deliveryman toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        UUID modelId;

        try {
            modelId = UUID.fromString(id);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ID);
        }

        final Name modelName = new Name(name);

        return new Deliveryman(modelId, modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDeliveryman)) {
            return false;
        }

        XmlAdaptedDeliveryman otherDman = (XmlAdaptedDeliveryman) other;
        return id.equals(otherDman.id)
            && Objects.equals(name, otherDman.name);
    }
}
