package seedu.address.storage.user;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.storage.XmlFileStorage;

/**
 * A class to access UsersList data stored as an xml file on the hard disk.
 */
public class XmlUsersListStorage implements UsersListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUsersListStorage.class);

    private Path filePath;

    public XmlUsersListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUsersListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException {
        return readUsersList(filePath);
    }

    /**
     * Similar to {@link #readUsersList()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyUsersList> readUsersList(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("UsersList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUsersList xmlUsersList = XmlFileStorage.loadUsersDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlUsersList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUsersList(ReadOnlyUsersList usersList) throws IOException {
        saveUsersList(usersList, filePath);
    }

    /**
     * Similar to {@link #saveUsersList(ReadOnlyUsersList)}
     *
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException {
        requireNonNull(usersList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveUsersDataToFile(filePath, new XmlSerializableUsersList(usersList));
    }
}
