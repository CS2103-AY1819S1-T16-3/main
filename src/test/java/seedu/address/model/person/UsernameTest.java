package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class UsernameTest {

    @Test
    void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        //invalid username
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("manager12@`")); //alphanumeric with special character
        assertFalse(Username.isValidUsername("koh chi hao 12")); //alphanumeric with spacing
        assertFalse(Username.isValidUsername("koh chi hao 12@/~")); //alphanumeric, spacing and special character
        assertFalse(Username.isValidUsername("@`/&*^%$#@")); //special character

        //valid username
        assertTrue(Username.isValidUsername("manager")); //alphanumeric
        assertTrue(Username.isValidUsername("manager12")); //alphanumeric
    }
}
