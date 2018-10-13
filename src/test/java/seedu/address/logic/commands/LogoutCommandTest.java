package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalRouteList(),
            getTypicalUsersList(), getTypicalDeliverymenList(), new UserPrefs());

    @Test
    public void execute_noLoggedInUser_logoutFail() throws CommandException {
        CommandResult commandResult = new LogoutCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_FAILURE, commandResult.feedbackToUser);
    }

    @Test
    public void execute_haveLoggedInUser_logoutSuccess() throws CommandException {
        /* Login user*/
        User validUser = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_ALICE)
                .build();

        CommandResult commandResult = new LoginCommand(validUser).execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);

        /* Logout user */
        commandResult = new LogoutCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

}
