package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.HOON;
import static seedu.address.testutil.TypicalOrders.IDA;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.collect.Streams;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;

public class XmlOrderBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlOrderBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readOrderBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readOrderBook(null);
    }

    private java.util.Optional<ReadOnlyOrderBook> readOrderBook(String filePath) throws Exception {
        return new XmlOrderBookStorage(Paths.get(filePath)).readOrderBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readOrderBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readOrderBook("NotXmlFormatOrderBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readOrderBook_invalidOrderOrderBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrderBook("invalidOrderOrderBook.xml");
    }

    @Test
    public void readOrderBook_invalidAndValidOrderOrderBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrderBook("invalidAndValidOrderOrderBook.xml");
    }

    @Test
    public void readAndSaveOrderBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        OrderBook original = getTypicalOrderBook();
        XmlOrderBookStorage xmlOrderBookStorage = new XmlOrderBookStorage(filePath);

        //Save in new file and read back
        xmlOrderBookStorage.saveOrderBook(original, filePath);
        ReadOnlyOrderBook readBack = xmlOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new OrderBook(readBack));
        assertTrue(Streams.zip(original.getOrderList().stream(),
            readBack.getOrderList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Modify data, overwrite exiting file, and read back
        original.addOrder(HOON);
        original.removeOrder(ALICE);
        xmlOrderBookStorage.saveOrderBook(original, filePath);
        readBack = xmlOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new OrderBook(readBack));
        assertTrue(Streams.zip(original.getOrderList().stream(),
            readBack.getOrderList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Save and read without specifying file path
        original.addOrder(IDA);
        xmlOrderBookStorage.saveOrderBook(original); //file path not specified
        readBack = xmlOrderBookStorage.readOrderBook().get(); //file path not specified
        assertEquals(original, new OrderBook(readBack));
        assertTrue(Streams.zip(original.getOrderList().stream(),
            readBack.getOrderList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

    }

    @Test
    public void saveOrderBook_nullOrderBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveOrderBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code orderBook} at the specified {@code filePath}.
     */
    private void saveOrderBook(ReadOnlyOrderBook orderBook, String filePath) {
        try {
            new XmlOrderBookStorage(Paths.get(filePath))
                    .saveOrderBook(orderBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveOrderBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveOrderBook(new OrderBook(), null);
    }


}
