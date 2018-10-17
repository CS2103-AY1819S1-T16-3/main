package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_IDA;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_LOGGED_IN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.order.ListCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;


public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    private String signupCommandWord = SignUpCommand.COMMAND_WORD + " ";
    private String signUpCommand = signupCommandWord + PREFIX_NAME + VALID_MANAGER_NAME_IDA
            + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_IDA
            + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_IDA;

    public LogicManagerTest() {
        signUp();
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand, signUpCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "/order delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand, signUpCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = OrderCommand.COMMAND_WORD + " " + ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand, signUpCommand);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredOrderList().remove(0);
    }

    /**
     * Signup a user to use the commands.
     */
    private void signUp() {
        String signupCommand = SignUpCommand.COMMAND_WORD + " ";
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_IDA
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_IDA
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_IDA;

        User ida = new UserBuilder()
                .withName(VALID_MANAGER_NAME_IDA)
                .withUsername(VALID_MANAGER_USERNAME_IDA)
                .withPassword(VALID_MANAGER_PASSWORD_IDA)
                .build();

        String expectedResultMessage = String.format(SignUpCommand.MESSAGE_SUCCESS, ida)
                + "\n"
                + MESSAGE_LOGGED_IN;
        assertCommandSuccess(command, expectedResultMessage, model);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getRouteList(),
                model.getUsersList(), model.getDeliverymenList(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, model);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     * - the internal model manager data are same as those in the {@code expectedModel} <br>
     * - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }
}
