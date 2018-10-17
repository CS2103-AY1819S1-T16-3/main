package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_IDA;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_ALREADY_LOGGED_IN;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_REDIRECT_TO_LOGOUT;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_DUPLICATE_USER;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_LOGGED_IN;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.person.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.person.Password.MESSAGE_PASSWORD_CONSTRAINTS;
import static seedu.address.model.person.Username.MESSAGE_USERNAME_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.SignUpCommand;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class SignUpCommandSystemTest extends OrderBookSystemTest {

    private String signupCommand = SignUpCommand.COMMAND_WORD + " ";

    @Test
    public void cannotSignupAfterSignup() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_IDA
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_IDA
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_IDA;

        User ida = new UserBuilder()
                .withName(VALID_MANAGER_NAME_IDA)
                .withUsername(VALID_MANAGER_USERNAME_IDA)
                .withPassword(VALID_MANAGER_PASSWORD_IDA)
                .build();

        String expectedResultMessage = String.format(MESSAGE_SUCCESS, ida)
                + "\n"
                + MESSAGE_LOGGED_IN;


        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_HOON
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_HOON
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_HOON;

        User hoon = new UserBuilder()
                .withName(VALID_MANAGER_NAME_HOON)
                .withUsername(VALID_MANAGER_USERNAME_HOON)
                .withPassword(VALID_MANAGER_PASSWORD_HOON)
                .build();

        expectedResultMessage = String.format(MESSAGE_ALREADY_LOGGED_IN, ida.getUsername())
                + "\n"
                + MESSAGE_REDIRECT_TO_LOGOUT;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    @Test
    public void signup_exisitingUser_failure() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_DUPLICATE_USER);
    }

    @Test
    public void signup_invalidName_failure() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + INVALID_NAME_DESC
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void signup_invalidUsername_failure() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + INVALID_USERNAME_DESC
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_USERNAME_CONSTRAINTS);
    }

    @Test
    public void signup_invalidPassword_failure() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + INVALID_PASSWORD_DESC;

        assertCommandFailure(command, MESSAGE_PASSWORD_CONSTRAINTS);
    }


    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ORDERS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see OrderBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
