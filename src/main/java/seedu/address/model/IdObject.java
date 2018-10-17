package seedu.address.model;

import java.util.UUID;

/**
 * Represents the Objects that need an ID for storage & reference
 */
public abstract class IdObject {
    public static final String MESSAGE_INVALID_ID = "Not an valid ID! Please check SML File.";

    private UUID id;

    protected IdObject() {
        id = null;
    }

    protected IdObject(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void assignId() {
        id = UUID.randomUUID();
    }
}
