package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * Represents a selection change in the Person List Panel
 */
public class DeliveryManPanelSelectionChangedEvent extends BaseEvent {


    private final Deliveryman newSelection;

    public DeliveryManPanelSelectionChangedEvent(Deliveryman newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Deliveryman getNewSelection() {
        return newSelection;
    }
}
