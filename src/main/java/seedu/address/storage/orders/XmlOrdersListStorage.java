package seedu.address.storage.orders;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyOrdersList;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * A class to access OrdersList data stored as an xml file on the hard disk.
 */
public class XmlOrdersListStorage implements OrdersListStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private Path filePath;

    public XmlOrdersListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getOrdersListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyOrdersList> readOrdersList() throws DataConversionException, IOException {
        return readOrdersList(filePath);
    }

    /**
     * Similar to {@link #readOrdersList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyOrdersList> readOrdersList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("OrdersList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableOrdersList xmlOrdersList = XmlOrderFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlOrdersList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveOrdersList(ReadOnlyOrdersList ordersList) throws IOException {
        saveOrdersList(ordersList, filePath);
    }

    /**
     * Similar to {@link #saveOrdersList(ReadOnlyOrdersList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveOrdersList(ReadOnlyOrdersList ordersList, Path filePath) throws IOException {
        requireNonNull(ordersList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlOrderFileStorage.saveDataToFile(filePath, new XmlSerializableOrdersList(ordersList));
    }
}
