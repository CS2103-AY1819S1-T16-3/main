package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

/**
 * JAXB-friendly adapted version of the Name.
 */
public class XmlAdaptedName {
    @XmlValue
    private String name;

    /**
     * Constructs an XmlAdaptedName.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedName() {}

    /**
     * Constructs a {@code XmlAdaptedName} with the given {@code name}.
     */
    public XmlAdaptedName(String name) {
        this.name = name;
    }

    /**
     * Converts a given name into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedName(Name source) {
        name = source.fullName;
    }

    /**
     * Converts this jaxb-friendly adapted name object into the model's Name object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Name toModelType() throws IllegalValueException {
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedName)) {
            return false;
        }

        return name.equals(((XmlAdaptedName) other).name);
    }
}
