package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.logic.parser.deliveryman.DeliverymanCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;


public class DeliverymanCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DeliverymanCommandParser parser = new DeliverymanCommandParser();

    @Test
    public void parse_find() throws Exception {
        String keyword = "foo";
        DeliverymanFindCommand command = (DeliverymanFindCommand) parser.parse(
                DeliverymanFindCommand.COMMAND_WORD + " n/" + keyword);
        assertEquals(new DeliverymanFindCommand(new DeliverymanNameContainsKeywordsPredicate(Arrays.asList(keyword))), command);
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
