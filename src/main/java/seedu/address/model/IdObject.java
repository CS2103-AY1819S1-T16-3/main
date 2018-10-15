package seedu.address.model;

/**
 * Represents the Objects that need an ID for storage & reference
 */
public abstract class IdObject {
    private final int id;

    public IdObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
