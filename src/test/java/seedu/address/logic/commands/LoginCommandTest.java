package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class LoginCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalUsersList(), new UserPrefs());

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_loginSuccessful() throws Exception {
        User validUser = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_ALICE)
                .build();

        CommandResult commandResult = new LoginCommand((Manager) validUser).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_userFailedByModel_loginFail() throws Exception {
        User validUser = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_BENSON)
                .build();

        CommandResult commandResult = new LoginCommand((Manager) validUser).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_FAILURE, validUser), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_userNotExist_loginFail() throws Exception {
        User validUser = new UserBuilder()
                .withUsername(INVALID_USERNAME_DESC)
                .withPassword(VALID_MANAGER_PASSWORD_BENSON)
                .build();

        CommandResult commandResult = new LoginCommand((Manager) validUser).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_FAILURE, validUser), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }


    @Test
    public void equals() {
        User alice = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_ALICE)
                .build();

        User benson = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_BENSON)
                .withPassword(VALID_MANAGER_PASSWORD_BENSON)
                .build();

        LoginCommand loginAliceCommand = new LoginCommand((Manager) alice);
        LoginCommand loginBensonCommand = new LoginCommand((Manager) benson);

        // same object -> returns true
        assertTrue(loginAliceCommand.equals(loginAliceCommand));

        // same values -> returns true
        LoginCommand loginAliceCommandCopy = new LoginCommand((Manager) alice);
        assertTrue(loginAliceCommand.equals(loginAliceCommandCopy));

        // different types -> returns false
        assertFalse(loginAliceCommand.equals(1));

        // null -> returns false
        assertFalse(loginAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(loginAliceCommand.equals(loginBensonCommand));
    }


}
