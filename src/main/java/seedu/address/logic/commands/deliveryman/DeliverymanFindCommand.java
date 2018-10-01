package seedu.address.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;

/**
 * Finds and lists all deliveryman in deliveryman list whose name contains in the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DeliverymanFindCommand extends DeliverymanCommand {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the deliveryman whose name contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/KEYWORD \n"
            + "Example: " + COMMAND_WORD + " n/Alex";

    private final DeliverymanNameContainsKeywordsPredicate predicate;

    public DeliverymanFindCommand(DeliverymanNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredDeliverymenList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DELIVERYMAN_LISTED_OVERVIEW, model.getFilteredDeliverymenList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanFindCommand // instanceof handles nulls
                && predicate.equals(((DeliverymanFindCommand) other).predicate)); // state check
    }
}
