package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * Stores orderslist data in an XML file
 */
public class XmlOrderFileStorage {
    /**
     * Saves the given orderslist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableOrdersList ordersList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, ordersList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableOrdersList loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableOrdersList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}

