package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.user.TypicalUsers.BENSON_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.HOON_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class SignUpCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalUsersList(), new UserPrefs());

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignUpCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_signUpSuccessful() throws Exception {
        User validUser = new UserBuilder(HOON_MANAGER).build();

        CommandResult commandResult = new SignUpCommand((Manager) validUser).execute(model, commandHistory);

        assertEquals(String.format(SignUpCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateUser_signUpFailure() throws Exception {
        User duplicateUser = new UserBuilder(BENSON_MANAGER).build();
        thrown.expect(CommandException.class);
        thrown.expectMessage(SignUpCommand.MESSAGE_DUPLICATE_USER);
        CommandResult commandResult = new SignUpCommand((Manager) duplicateUser).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        User alice = new UserBuilder()
                .withName(VALID_MANAGER_NAME_ALICE)
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_ALICE)
                .build();

        User benson = new UserBuilder()
                .withName(VALID_MANAGER_NAME_BENSON)
                .withUsername(VALID_MANAGER_USERNAME_BENSON)
                .withPassword(VALID_MANAGER_PASSWORD_BENSON)
                .build();

        SignUpCommand signUpAliceCommand = new SignUpCommand((Manager) alice);
        SignUpCommand signUpBensonCommand = new SignUpCommand((Manager) benson);

        // same object -> returns true
        assertTrue(signUpAliceCommand.equals(signUpAliceCommand));

        // same values -> returns true
        SignUpCommand signUpAliceCommandCopy = new SignUpCommand((Manager) alice);
        assertTrue(signUpAliceCommand.equals(signUpAliceCommandCopy));

        // different types -> returns false
        assertFalse(signUpAliceCommandCopy.equals(1));

        // null -> returns false
        assertFalse(signUpAliceCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(signUpAliceCommandCopy.equals(signUpBensonCommand));
    }


}
