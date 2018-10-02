package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.logic.parser.deliveryman.DeliverymanFindCommandParser;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;

public class DeliverymanFindCommandParserTest {

    private DeliverymanFindCommandParser parser = new DeliverymanFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        DeliverymanFindCommand expectedFindCommand =
                new DeliverymanFindCommand(new DeliverymanNameContainsKeywordsPredicate(Arrays.asList("Alex")));
        assertParseSuccess(parser, " n/Alex", expectedFindCommand);
    }
}
