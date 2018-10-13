package seedu.address.model.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.user.TypicalUsers.ALICE_MANAGER;
import static seedu.address.testutil.user.TypicalUsers.BENSON_MANAGER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserSessionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UserSession userSession = new UserSession();

    @Test
    public void constructor() {
        UserSession expectedUserSession = new UserSession(ALICE_MANAGER);
        assertTrue(expectedUserSession.isUserAlreadyLoggedIn());

        assertEquals(ALICE_MANAGER, expectedUserSession.getLoggedInUserDetails());
    }

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        UserSession expectedUserSession = new UserSession(null);
    }


    @Test
    public void setUserSession_haveUser_isEquals() {
        userSession.setUserSession(ALICE_MANAGER);
        assertEquals(ALICE_MANAGER, userSession.getLoggedInUserDetails());
    }

    @Test
    public void setUserSession_nullUser_notEquals() {
        assertNotEquals(ALICE_MANAGER, userSession.getLoggedInUserDetails());
    }

    @Test
    public void setUserSession_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        userSession.setUserSession(null);
    }

    @Test
    public void isUserAlreadyLoggedIn() {
        userSession.setUserSession(ALICE_MANAGER);
        assertTrue(userSession.isUserAlreadyLoggedIn());
    }

    @Test
    public void isUserAlreadyLoggedIn_nullUser() {
        thrown.expect(NullPointerException.class);
        userSession.setUserSession(null);
        assertFalse(userSession.isUserAlreadyLoggedIn());
    }

    @Test
    public void clearUserInSession() {
        userSession.setUserSession(BENSON_MANAGER);
        userSession.clearUserSession();
        assertNull(userSession.getLoggedInUserDetails());
    }

    @Test
    public void getLoggedInUserDetails() {
        userSession.setUserSession(BENSON_MANAGER);
        assertNotEquals(ALICE_MANAGER, userSession.getLoggedInUserDetails());
        assertEquals(BENSON_MANAGER, userSession.getLoggedInUserDetails());
    }

    @Test
    public void getLoggedInUserDetails_nullUser() {
        thrown.expect(NullPointerException.class);
        userSession.setUserSession(null);
        assertNull(userSession.getLoggedInUserDetails());
    }
}
