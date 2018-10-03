package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new UserBuilder()
                .withUsername(VALID_MANAGER_USERNAME_ALICE)
                .withPassword(VALID_MANAGER_PASSWORD_ALICE)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                new LoginCommand((Manager) expectedUser));

        // multiple usernames - last username accepted
        assertParseSuccess(parser, USERNAME_DESC_BENSON
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                new LoginCommand((Manager) expectedUser));

        // multiple password - last password accepted
        assertParseSuccess(parser, USERNAME_DESC_ALICE
                        + PASSWORD_DESC_BENSON
                        + PASSWORD_DESC_ALICE,
                new LoginCommand((Manager) expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, VALID_MANAGER_USERNAME_ALICE + PASSWORD_DESC_ALICE, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_ALICE + VALID_MANAGER_PASSWORD_ALICE, expectedMessage);
    }
}
