package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.order.OrderFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.order.OrderCommandParser;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;


public class OrderCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrderCommandParser parser = new OrderCommandParser();

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        OrderFindCommand command = (OrderFindCommand) parser.parse(
                OrderFindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new OrderFindCommand(new OrderNameContainsKeywordPredicate(keywords)), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("");
        parser.parse("3");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("unknownCommand");
    }
}
