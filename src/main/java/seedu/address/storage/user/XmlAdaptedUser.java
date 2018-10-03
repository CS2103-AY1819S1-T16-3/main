package seedu.address.storage.user;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;

/**
 * JAXB-friendly version of the User.
 */
public class XmlAdaptedUser {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {
    }


    /**
     * Constructs an {@code XmlAdaptedUser} with the given person details.
     */
    public XmlAdaptedUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User source) {
        name = source.getName().fullName;
        username = source.getUsername().value;
        password = source.getPassword().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's User object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted user
     */
    public User toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);
        return new Manager(modelName, modelUsername, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedUser otherUser = (XmlAdaptedUser) other;
        return Objects.equals(name, otherUser.name)
                && Objects.equals(username, otherUser.username)
                && Objects.equals(password, otherUser.password);
    }
}
