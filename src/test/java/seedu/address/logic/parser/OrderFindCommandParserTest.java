package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.order.OrderFindCommand;
import seedu.address.logic.parser.order.OrderFindCommandParser;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;

public class OrderFindCommandParserTest {

    private OrderFindCommandParser parser = new OrderFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsOrderFindCommand() {
        OrderFindCommand expectedNameOrderFindCommand =
                new OrderFindCommand(new OrderNameContainsKeywordPredicate(Arrays.asList("Alex")));
        assertParseSuccess(parser, "find n/Alex", expectedNameOrderFindCommand);

        OrderFindCommand expectedPhoneOrderFindCommand =
                new OrderFindCommand(new OrderPhoneContainsKeywordPredicate("81223455"));
        assertParseSuccess(parser, "find p/81223455", expectedPhoneOrderFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Supplying both name and phone
        assertParseFailure(parser, "find n/testname p/12345",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderFindCommand.MESSAGE_USAGE));
    }
}
