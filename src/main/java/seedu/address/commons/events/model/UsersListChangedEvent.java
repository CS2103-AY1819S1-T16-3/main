package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyUsersList;

/** Indicates the UsersList in the model has changed*/
public class UsersListChangedEvent extends BaseEvent {

    public final ReadOnlyUsersList data;

    public UsersListChangedEvent(ReadOnlyUsersList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of users " + data.getUserList().size();
    }
}
