package seedu.address.storage.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRoutes.ROUTE_ALICE_BENSON;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.Streams;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.RouteList;

public class XmlRouteListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlRouteListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRouteList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRouteList(null);
    }

    private java.util.Optional<ReadOnlyRouteList> readRouteList(String filePath) throws Exception {
        return new XmlRouteListStorage(Paths.get(filePath)).readRouteList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRouteList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRouteList("NotXmlFormatRouteList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readRouteList_invalidRouteRouteList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRouteList("invalidRouteRouteList.xml");
    }

    @Test
    public void readRouteList_invalidAndValidRouteRouteList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRouteList("invalidAndValidRouteRouteList.xml");
    }

    @Test
    public void readAndSaveRouteList_allInRoute_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempRouteList.xml");
        RouteList original = getTypicalRouteList();
        XmlRouteListStorage xmlRouteListStorage = new XmlRouteListStorage(filePath);

        //Save in new file and read back
        xmlRouteListStorage.saveRouteList(original, filePath);
        ReadOnlyRouteList readBack = xmlRouteListStorage.readRouteList(filePath).get();
        assertEquals(original, new RouteList(readBack));
        assertTrue(Streams.zip(original.getRouteList().stream(),
            readBack.getRouteList().stream(), (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Modify data, overwrite exiting file, and read back
        original.removeRoute(ROUTE_ALICE_BENSON);
        xmlRouteListStorage.saveRouteList(original, filePath);
        readBack = xmlRouteListStorage.readRouteList(filePath).get();
        assertEquals(original, new RouteList(readBack));
        assertTrue(Streams.zip(original.getRouteList().stream(),
            readBack.getRouteList().stream(), (a, b) -> a.hasSameId(b)).allMatch(x -> x));

        //Save and read without specifying file path
        original.addRoute(ROUTE_ALICE_BENSON);
        xmlRouteListStorage.saveRouteList(original); //file path not specified
        readBack = xmlRouteListStorage.readRouteList().get(); //file path not specified
        assertEquals(original, new RouteList(readBack));
        assertTrue(Streams.zip(original.getRouteList().stream(),
            readBack.getRouteList().stream(), (a, b) -> a.hasSameId(b)).allMatch(x -> x));

    }

    @Test
    public void saveRouteList_nullRouteList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRouteList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code routeList} at the specified {@code filePath}.
     */
    private void saveRouteList(ReadOnlyRouteList routeList, String filePath) {
        try {
            new XmlRouteListStorage(Paths.get(filePath))
                    .saveRouteList(routeList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRouteList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRouteList(new RouteList(), null);
    }
}
