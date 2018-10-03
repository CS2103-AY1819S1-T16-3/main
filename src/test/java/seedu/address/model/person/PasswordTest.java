package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class PasswordTest {

    @Test
    void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid password
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("mana")); //4 characters only
        assertFalse(Password.isValidPassword("mana@@")); //6 characters with special character
        assertFalse(Password.isValidPassword("manager12@`")); //alphanumeric with special character
        assertFalse(Password.isValidPassword("koh chi hao 12")); //alphanumeric with spacing
        assertFalse(Password.isValidPassword("koh chi hao 12@/~")); //alphanumeric, spacing and special character
        assertFalse(Password.isValidPassword("@`/&*^%$#@")); //special character

        //valid password
        assertTrue(Password.isValidPassword("manage")); //6 alphabet character
        assertTrue(Password.isValidPassword("managerpass12")); //more than 6 characters
        assertTrue(Password.isValidPassword("mana12")); // 6 alphanumeric character
        assertTrue(Password.isValidPassword("123456")); //6 digits only
    }
}
