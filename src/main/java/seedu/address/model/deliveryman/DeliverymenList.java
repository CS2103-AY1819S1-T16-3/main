package seedu.address.model.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data into a deliverymen list.
 * Duplicates are not allowed.
 */
public class DeliverymenList {
    private final UniqueDeliverymenList deliverymenList;

    {
        deliverymenList = new UniqueDeliverymenList();
    }

    public DeliverymenList() {
    }

    public DeliverymenList(DeliverymenList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /// list overwrite operations

    /**
     * Replaces the contents of the deliverymen list with {@code deliverymen}.
     * {@code deliverymen} must not contain duplicate persons.
     */
    public void setDeliverymen(List<Deliveryman> deliverymen) {
        this.deliverymenList.setDeliverymen(deliverymen);
    }

    /**
     * Resets the contents of the deliverymen list with the contents of {@code newData}.
     * @param newData The DeliverymenList to get the contents from
     */
    public void resetData(DeliverymenList newData) {
        requireNonNull(newData);

        setDeliverymen(newData.getDeliverymenList());
    }

    /// deliveryman-level operations

    /**
     * Returns true if the {@code DeliverymenList} contains a {@code deliveryman}
     * @param deliveryman
     */
    public boolean hasDeliveryman(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
        return deliverymenList.contains(deliveryman);
    }

    /**
     * Adds a deliveryman to the {@code deliverymenList}
     * @param d
     */
    public void addDeliveryman(Deliveryman d) {
        if (d.getId() == null) {
            d.assignId();
        }
        deliverymenList.add(d);
    }

    /**
     * Replaces the {@code target} deliveryman with an {@code edited} deliveryman.
     */
    public void updateDeliveryman(Deliveryman target, Deliveryman editedD) {
        requireNonNull(editedD);

        deliverymenList.setDeliveryman(target, editedD);
    }

    public void removeDeliveryman(Deliveryman key) {
        deliverymenList.remove(key);
    }

    @Override
    public String toString() {
        return deliverymenList.asUnmodifiableObservableList().size() + " deliverymen";
    }

    public ObservableList<Deliveryman> getDeliverymenList() {
        return deliverymenList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeliverymenList
                    && deliverymenList.equals(((DeliverymenList) other).deliverymenList));
    }

    @Override
    public int hashCode() {
        return deliverymenList.hashCode();
    }
}
