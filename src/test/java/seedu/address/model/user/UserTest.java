package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.CARL_MANAGER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.user.UserBuilder;

public class UserTest {

    public static final String VALID_USERNAME_AMY = "Amy Bee";
    public static final String VALID_PASSWORD_AMY = "amybee";
    public static final String VALID_NAME_BOB = "Bobby";
    public static final String VALID_USERNAME_BOB = "Bobby";
    public static final String VALID_PASSWORD_BOB = "bobby01";


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameUser() {
        // same object -> returns true
        assertTrue(ALICE_MANAGER.isSameUser(ALICE_MANAGER));

        // null -> returns false
        assertFalse(ALICE_MANAGER.isSameUser(null));

        // same name, different username and password -> returns false
        User editedAlice = new UserBuilder(ALICE_MANAGER)
                .withUsername(VALID_USERNAME_AMY)
                .withPassword(VALID_PASSWORD_AMY)
                .build();
        assertFalse(ALICE_MANAGER.isSameUser(editedAlice));

        // same name, same username and different password -> returns true
        editedAlice = new UserBuilder(ALICE_MANAGER)
                .withPassword(VALID_PASSWORD_AMY)
                .build();
        assertTrue(ALICE_MANAGER.isSameUser(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        User aliceCopy = new UserBuilder(ALICE_MANAGER).build();
        assertTrue(ALICE_MANAGER.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_MANAGER.equals(ALICE_MANAGER));

        // null -> returns false
        assertFalse(ALICE_MANAGER.equals(null));

        // different type -> returns false
        assertFalse(ALICE_MANAGER.equals(5));

        // different person -> returns false
        assertFalse(ALICE_MANAGER.equals(CARL_MANAGER));

        // different name -> returns false
        User editedAlice = new UserBuilder(ALICE_MANAGER).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_MANAGER.equals(editedAlice));

    }
}
