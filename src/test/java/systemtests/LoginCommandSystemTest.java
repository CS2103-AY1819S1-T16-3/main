package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_KENNY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_KENNY;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.Model;

public class LoginCommandSystemTest extends OrderBookSystemTest {


    @Test
    public void cannotLoginAfterLogin() {
        Model expectedModel = getModel();
        String loginCommand = LoginCommand.COMMAND_WORD + " ";

        /* Case: Successful login, correct username and password */
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        String expectedResultMessage = String.format(
                MESSAGE_SUCCESS, "Username: " + VALID_MANAGER_USERNAME_ALICE);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Attempt to login after successful login */
        command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_HOON
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_HOON;
        expectedResultMessage = String.format(LoginCommand.MESSAGE_ALREADY_LOGGED_IN,
                VALID_MANAGER_USERNAME_ALICE) + "\n" + LoginCommand.MESSAGE_REDIRECT_TO_LOGOUT;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    @Test
    public void login() {
        Model expectedModel = getModel();
        String loginCommand = LoginCommand.COMMAND_WORD + " ";

        /* Case: Successful login, correct username and password */
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        String expectedResultMessage = String.format(
                MESSAGE_SUCCESS, "Username: " + VALID_MANAGER_USERNAME_ALICE);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    @Test
    public void login_fail_wrongPassword() {
        String loginCommand = LoginCommand.COMMAND_WORD + " ";

        /* Case: Fail to login, wrong password */
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_BENSON;
        String expectedResultMessage = String.format(
                MESSAGE_FAILURE, "Username: " + VALID_MANAGER_USERNAME_ALICE);

        assertCommandFailure(command, expectedResultMessage);
    }

    @Test
    public void login_fail_userNotExist() {
        String loginCommand = LoginCommand.COMMAND_WORD + " ";

        /* Case: Fail to login, wrong password */
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_KENNY
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_KENNY;
        String expectedResultMessage = String.format(
                MESSAGE_FAILURE, "Username: " + VALID_MANAGER_USERNAME_KENNY);

        assertCommandFailure(command, expectedResultMessage);
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
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
