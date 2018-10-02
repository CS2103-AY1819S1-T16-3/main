package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderContainsAnyKeywordsPredicate;


/**
 * Finds and lists all orders in orders list whose name or phone contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class OrderFindCommand extends OrderCommand {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the order whose name or phone number contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME p/PHONE\n"
            + "Example: " + COMMAND_WORD + " n/John \n"
            + "Example: " + COMMAND_WORD + " p/1234578";

    private final OrderContainsAnyKeywordsPredicate predicate;

    public OrderFindCommand(OrderContainsAnyKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredOrderList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderFindCommand // instanceof handles nulls
                && predicate.equals(((OrderFindCommand) other).predicate)); // state check
    }
}
