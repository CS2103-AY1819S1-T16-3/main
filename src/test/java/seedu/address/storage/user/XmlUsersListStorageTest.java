package seedu.address.storage.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.HOON_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.IDA_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UsersList;

public class XmlUsersListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlUsersListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUsersList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readUsersList(null);
    }

    private java.util.Optional<ReadOnlyUsersList> readUsersList(String filePath) throws Exception {
        return new XmlUsersListStorage(Paths.get(filePath)).readUsersList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUsersList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUsersList("NotXmlFormatUsersList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readUsersList_invalidUsersList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readUsersList("invalidUsersList.xml");
    }

    @Test
    public void readUsersList_invalidAndValidUsersList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readUsersList("invalidAndValidUsersList.xml");
    }

    @Test
    public void readAndSaveUsersList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        UsersList original = getTypicalUsersList();
        XmlUsersListStorage xmlUsersListStorage = new XmlUsersListStorage(filePath);

        //Save in new file and read back
        xmlUsersListStorage.saveUsersList(original, filePath);
        ReadOnlyUsersList readBack = xmlUsersListStorage.readUsersList(filePath).get();
        assertEquals(original, new UsersList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addUser(HOON_MANAGER);
        original.removeUser(ALICE_MANAGER);
        xmlUsersListStorage.saveUsersList(original, filePath);
        readBack = xmlUsersListStorage.readUsersList(filePath).get();
        assertEquals(original, new UsersList(readBack));

        //Save and read without specifying file path
        original.addUser(IDA_MANAGER);
        xmlUsersListStorage.saveUsersList(original); //file path not specified
        readBack = xmlUsersListStorage.readUsersList().get(); //file path not specified
        assertEquals(original, new UsersList(readBack));

    }

    @Test
    public void saveUsersList_nullUsersList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUsersList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code usersList} at the specified {@code filePath}.
     */
    private void saveUsersList(ReadOnlyUsersList usersList, String filePath) {
        try {
            new XmlUsersListStorage(Paths.get(filePath))
                    .saveUsersList(usersList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUsersList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUsersList(new UsersList(), null);
    }


}
