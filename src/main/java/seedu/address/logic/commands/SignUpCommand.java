package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Manager;

/**
 * Sign up the user into FoodZoom.
 */
public class SignUpCommand extends Command {

    public static final String COMMAND_WORD = "/signup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a user to FoodZoom. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_USERNAME + "johndoe "
            + PREFIX_PASSWORD + "johndoepassword";

    public static final String MESSAGE_SUCCESS = "New user added: %1$s";
    public static final String MESSAGE_DUPLICATE_USER = "This user already exists in FoodZoom.";

    private final Manager toAdd;

    /**
     * Creates an SignUpCommand to add the specified {@code Manager}
     */
    public SignUpCommand(Manager manager) {
        requireNonNull(manager);
        toAdd = manager;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasUser(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_USER);
        }

        model.addUser(toAdd);
        model.commitUsersList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignUpCommand // instanceof handles nulls
                && toAdd.equals(((SignUpCommand) other).toAdd));
    }
}
