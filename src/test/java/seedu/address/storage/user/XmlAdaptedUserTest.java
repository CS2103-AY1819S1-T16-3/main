package seedu.address.storage.user;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.user.XmlAdaptedUser.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.user.TypicalUsers.CARL_MANAGER;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;
import seedu.address.testutil.Assert;


public class XmlAdaptedUserTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_USERNAME = "manager12@`";
    private static final String INVALID_PASSWORD = "man12";

    private static final String VALID_NAME = CARL_MANAGER.getName().toString();
    private static final String VALID_USERNAME = CARL_MANAGER.getUsername().toString();
    private static final String VALID_PASSWORD = CARL_MANAGER.getPassword().toString();

    @Test
    public void toModelType_validUserDetails_returnsUser() throws Exception {
        XmlAdaptedUser user = new XmlAdaptedUser(CARL_MANAGER);
        assertEquals(CARL_MANAGER, user.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(INVALID_NAME, VALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(null, VALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(VALID_NAME, INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(VALID_NAME, null, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(VALID_NAME, VALID_USERNAME, INVALID_PASSWORD);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedUser user = new XmlAdaptedUser(VALID_NAME, VALID_USERNAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, user::toModelType);
    }


}
