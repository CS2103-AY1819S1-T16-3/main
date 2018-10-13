package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logout the user from FoodZoom.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "/logout";

    public static final String MESSAGE_SUCCESS = "Logout Success";

    public static final String MESSAGE_FAILURE = "No User to logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout from FoodZoom. ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isUserLoggedIn()) {
            model.clearUserInSession();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
