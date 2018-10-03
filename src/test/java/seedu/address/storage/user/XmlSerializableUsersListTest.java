package seedu.address.storage.user;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.UsersList;
import seedu.address.testutil.user.TypicalUsers;

public class XmlSerializableUsersListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableUsersListTest");
    private static final Path TYPICAL_USERS_FILE = TEST_DATA_FOLDER.resolve("typicalUsersList.xml");
    private static final Path INVALID_USER_FILE = TEST_DATA_FOLDER.resolve("invalidUsersList.xml");
    private static final Path DUPLICATE_USER_FILE = TEST_DATA_FOLDER.resolve("duplicateUsersList.xml");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalUsersFile_success() throws Exception {
        XmlSerializableUsersList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_USERS_FILE,
                XmlSerializableUsersList.class);

        UsersList usersListFromFile = dataFromFile.toModelType();
        UsersList typicalUsersList = TypicalUsers.getTypicalUsersList();
        assertEquals(usersListFromFile, typicalUsersList);
    }

    @Test
    public void toModelType_invalidUserFile_throwsIllegalValueException() throws Exception {
        XmlSerializableUsersList dataFromFile = XmlUtil.getDataFromFile(INVALID_USER_FILE,
                XmlSerializableUsersList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableUsersList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_USER_FILE,
                XmlSerializableUsersList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableUsersList.MESSAGE_DUPLICATE_USER);
        dataFromFile.toModelType();
    }

}
