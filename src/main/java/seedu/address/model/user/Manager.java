package seedu.address.model.user;

import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;

/**
 * Represents a User in the FoodZoom.
 */
public class Manager extends User {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param username
     * @param password
     */
    public Manager(Name name, Username username, Password password) {
        super(name, username, password);
    }

    public Manager(Username username, Password password) {
        super(username, password);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Manager)) {
            return false;
        }

        Manager otherPerson = (Manager) other;
        if (otherPerson.getName() == null) {
            return otherPerson.getUsername().equals(getUsername())
                    && otherPerson.getPassword().equals(getPassword());
        } else {
            return otherPerson.getName().equals(getName())
                    && otherPerson.getUsername().equals(getUsername())
                    && otherPerson.getPassword().equals(getPassword());
        }

    }
}

