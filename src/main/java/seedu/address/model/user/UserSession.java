package seedu.address.model.user;

import static java.util.Objects.requireNonNull;

/**
 * UserSession represent a logged in user session.
 */
public class UserSession {

    private User loggedInUser;
    private boolean isLoggedIn;

    /**
     * Initializes an empty UserSession.
     */
    public UserSession() {
        isLoggedIn = false;
    }

    /**
     * Initializes a UserSession with user.
     * @param toLogin User that has successfully logged in.
     */
    public UserSession(User toLogin) {
        requireNonNull(toLogin);
        loggedInUser = toLogin;
        this.isLoggedIn = true;
    }

    /**
     * Set up user session.
     * @param toLogin User that has successfully logged in.
     */
    public void setUserSession(User toLogin) {
        requireNonNull(toLogin);
        this.loggedInUser = toLogin;
        this.isLoggedIn = true;
    }

    /**
     * Check if user is already logged into FoodZoom.
     */
    public boolean isUserAlreadyLoggedIn() {
        return this.isLoggedIn;
    }

    /**
     * To clear user session details.
     */
    public void clearUserSession() {
        this.isLoggedIn = false;
        this.loggedInUser = null;
    }

    /**
     * Return logged in user details.
     */
    public User getLoggedInUserDetails() {
        return this.loggedInUser;
    }
}
