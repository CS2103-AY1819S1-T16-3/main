package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;

import org.junit.Test;

import seedu.address.logic.commands.SignUpCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;
import seedu.address.model.user.Manager;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class SignUpCommandParserTest {

    private SignUpCommandParser parser = new SignUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new UserBuilder(ALICE_MANAGER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + NAME_DESC_ALICE
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                new SignUpCommand((Manager) expectedUser));


        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BENSON
                        + NAME_DESC_ALICE
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                new SignUpCommand((Manager) expectedUser));

        // multiple usernames - last username accepted
        assertParseSuccess(parser, NAME_DESC_ALICE
                        + USERNAME_DESC_BENSON
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                new SignUpCommand((Manager) expectedUser));

        // multiple password - last password accepted
        assertParseSuccess(parser, NAME_DESC_ALICE
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_BENSON
                        + PASSWORD_DESC_ALICE,
                new SignUpCommand((Manager) expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MANAGER_NAME_ALICE
                        + USERNAME_DESC_ALICE
                        + PASSWORD_DESC_ALICE,
                expectedMessage);

        // missing username prefix
        assertParseFailure(parser, NAME_DESC_ALICE
                        + VALID_MANAGER_USERNAME_ALICE
                        + PASSWORD_DESC_ALICE,
                expectedMessage);

        // missing password prefix
        assertParseFailure(parser, NAME_DESC_ALICE
                        + USERNAME_DESC_ALICE
                        + VALID_MANAGER_PASSWORD_ALICE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC
                + USERNAME_DESC_ALICE
                + PASSWORD_DESC_ALICE,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid username
        assertParseFailure(parser, NAME_DESC_ALICE
                        + INVALID_USERNAME_DESC
                        + PASSWORD_DESC_ALICE,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, NAME_DESC_ALICE
                        + USERNAME_DESC_ALICE
                        + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY
                        + NAME_DESC_BENSON
                        + USERNAME_DESC_BENSON
                        + PASSWORD_DESC_BENSON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
    }

}

