package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.user.User;

/**
 * Indicates the user has logged in
 */
public class UserLoggedInEvent extends BaseEvent {

    public final User data;

    public UserLoggedInEvent(User data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "User " + data.getUsername() + " have logged in";
    }
}
