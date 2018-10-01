package seedu.address.logic.parser;

import seedu.address.logic.commands.FindOrderCommand;
import seedu.address.logic.commands.OrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;

import java.util.Arrays;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

public class FindOrderCommandParser implements Parser<OrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns an FindOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public OrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);

        if(argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get().trim();
            String[] nameKeywords = name.split("\\s+");
            return new FindOrderCommand(new OrderNameContainsKeywordPredicate(Arrays.asList(nameKeywords)));
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phone = argMultimap.getValue(PREFIX_PHONE).get().trim().replaceAll("\\s+","");;
            return new FindOrderCommand(new OrderPhoneContainsKeywordPredicate(phone));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if either {@code PREFIX_NAME} or {@code PREFIX_PHONE} value is present in the given
     * {@code ArgumentMultimap}
     */
    private static boolean isNameOrPhonePresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_NAME).isPresent()
                || argumentMultimap.getValue(PREFIX_PHONE).isPresent();
    }

    /**
     * Returns true if any of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
