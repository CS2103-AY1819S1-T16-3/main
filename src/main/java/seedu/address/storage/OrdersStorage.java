package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrdersList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface OrdersStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getOrdersListFilePath();

    /**
     * Returns Orders data as a {@link ReadOnlyOrdersList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrdersList> readOrdersList() throws DataConversionException, IOException;

    /**
     * @see #getOrdersListFilePath()
     */
    Optional<ReadOnlyOrdersList> readOrdersList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrdersList} to the storage.
     * @param orders cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrdersList(ReadOnlyOrdersList orders) throws IOException;

    /**
     * @see #saveOrdersList(ReadOnlyOrdersList)
     */
    void saveOrdersList(ReadOnlyOrdersList orders, Path filePath) throws IOException;
}
