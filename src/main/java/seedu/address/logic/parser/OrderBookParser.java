package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.logic.commands.route.RouteCommand;
import seedu.address.logic.parser.deliveryman.DeliverymanCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.order.OrderCommandParser;
import seedu.address.logic.parser.route.RouteCommandParser;

/**
 * Parses user input.
 */
public class OrderBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case OrderCommand.COMMAND_WORD:
            return new OrderCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RouteCommand.COMMAND_WORD:
            return new RouteCommandParser().parse(arguments);

        case SignUpCommand.COMMAND_WORD:
            return new SignUpCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case DeliverymanCommand.COMMAND_WORD:
            return new DeliverymanCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
