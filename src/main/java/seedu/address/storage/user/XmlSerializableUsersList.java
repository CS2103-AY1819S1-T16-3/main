package seedu.address.storage.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UsersList;
import seedu.address.model.user.User;

/**
 * An Immutable User that is serializable to XML format
 */
@XmlRootElement(name = "userslist")
public class XmlSerializableUsersList {

    public static final String MESSAGE_DUPLICATE_USER = "User list contains duplicate user(s).";

    @XmlElement
    private List<XmlAdaptedUser> users;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUsersList() {
        users = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableUsersList(ReadOnlyUsersList src) {
        this();
        users.addAll(src.getUserList().stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }


    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedPerson}.
     */
    public UsersList toModelType() throws IllegalValueException {
        UsersList usersList = new UsersList();
        for (XmlAdaptedUser u : users) {
            User user = u.toModelType();
            if (usersList.hasUser(user)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_USER);
            }
            usersList.addUser(user);
        }
        return usersList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUsersList)) {
            return false;
        }
        return users.equals(((XmlSerializableUsersList) other).users);
    }

}
