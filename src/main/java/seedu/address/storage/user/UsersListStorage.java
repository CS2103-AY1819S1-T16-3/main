package seedu.address.storage.user;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUsersList;

/**
 * Represents a storage for {@link seedu.address.model.UsersList}.
 */
public interface UsersListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getUsersListFilePath();

    /**
     * Returns UsersList data as a {@link ReadOnlyUsersList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException;

    /**
     * @see #getUsersListFilePath()
     */
    Optional<ReadOnlyUsersList> readUsersList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUsersList} to the storage.
     *
     * @param usersList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUsersList(ReadOnlyUsersList usersList) throws IOException;

    /**
     * @see #saveUsersList(ReadOnlyUsersList)
     */
    void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException;
}
