package seedu.address.storage.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.collect.Streams;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.deliveryman.DeliverymenList;

public class XmlDeliverymenListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlDeliverymenListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDeliverymenList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDeliverymenList(null);
    }

    private java.util.Optional<DeliverymenList> readDeliverymenList(String filePath) throws Exception {
        return new XmlDeliverymenListStorage(Paths.get(filePath))
            .readDeliverymenList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDeliverymenList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDeliverymenList("NotXmlFormatDeliverymenList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readDeliverymenList_invalidPersonDeliverymenList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDeliverymenList("invalidDeliverymanDeliverymenList.xml");
    }

    @Test
    public void readDeliverymenList_invalidAndValidPersonDeliverymenList_throwDataConversionException()
        throws Exception {
        thrown.expect(DataConversionException.class);
        readDeliverymenList("invalidAndValidDeliverymenDeliverymenList.xml");
    }

    @Test
    public void readAndSaveDeliverymenList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempDeliverymenList.xml");
        DeliverymenList original = getTypicalDeliverymenList();
        XmlDeliverymenListStorage xmlDeliverymenListStorage = new XmlDeliverymenListStorage(filePath);

        //Save in new file and read back
        xmlDeliverymenListStorage.saveDeliverymenList(original, filePath);
        DeliverymenList readBack = xmlDeliverymenListStorage.readDeliverymenList(filePath).get();
        assertEquals(original, new DeliverymenList(readBack));
        assertTrue(Streams.zip(original.getDeliverymenList().stream(),
            readBack.getDeliverymenList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Modify data, overwrite exiting file, and read back
        original.removeDeliveryman(RAJUL);
        xmlDeliverymenListStorage.saveDeliverymenList(original, filePath);
        readBack = xmlDeliverymenListStorage.readDeliverymenList(filePath).get();
        assertEquals(original, new DeliverymenList(readBack));
        assertTrue(Streams.zip(original.getDeliverymenList().stream(),
            readBack.getDeliverymenList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Save and read without specifying file path
        original.addDeliveryman(RAJUL);
        xmlDeliverymenListStorage.saveDeliverymenList(original); //file path not specified
        readBack = xmlDeliverymenListStorage.readDeliverymenList().get(); //file path not specified
        assertEquals(original, new DeliverymenList(readBack));
        assertTrue(Streams.zip(original.getDeliverymenList().stream(),
            readBack.getDeliverymenList().stream(),
            (a, b) -> a.hasSameId(b)).allMatch(x -> x));

    }

    @Test
    public void saveDeliverymenList_nullDeliverymenList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDeliverymenList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code DeliverymenList} at the specified {@code filePath}.
     */
    private void saveDeliverymenList(DeliverymenList deliverymenList, String filePath) {
        try {
            new XmlDeliverymenListStorage(Paths.get(filePath))
                .saveDeliverymenList(deliverymenList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDeliverymenList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDeliverymenList(new DeliverymenList(), null);
    }
}
