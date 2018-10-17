package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

/**
 * Login the user into FoodZoom.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "/login";

    public static final String MESSAGE_SUCCESS = "Login Success for %1$s";

    public static final String MESSAGE_FAILURE = "Login Failure for %1$s";

    public static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in as %1$s";

    public static final String MESSAGE_REDIRECT_TO_LOGOUT = "Please logout before attempting to login to another "
            + "account";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to FoodZoom. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johndoe "
            + PREFIX_PASSWORD + "johndoepassword";

    private final User toLogin;

    /**
     * Creates an LoginCommand to add the specified {@code user}
     */
    public LoginCommand(User user) {
        requireNonNull(user);
        toLogin = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.isUserLoggedIn()) {
            User loggedInUser = model.getLoggedInUserDetails();
            String result = String.format(MESSAGE_ALREADY_LOGGED_IN, loggedInUser.getUsername())
                    + "\n"
                    + MESSAGE_REDIRECT_TO_LOGOUT;
            return new CommandResult(result);
        }

        if (model.isRegisteredUser(toLogin)) {
            model.storeUserInSession(toLogin);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE, toLogin));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
