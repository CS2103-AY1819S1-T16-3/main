package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the user has logged out.
 */
public class UserLoggedOutEvent extends BaseEvent {


    public UserLoggedOutEvent() {
    }

    @Override
    public String toString() {
        return "User have successfully logout.";
    }
}
