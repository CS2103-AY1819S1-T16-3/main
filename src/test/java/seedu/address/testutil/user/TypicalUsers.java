package seedu.address.testutil.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UsersList;
import seedu.address.model.user.User;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {
    public static final User ALICE_MANAGER = new UserBuilder()
            .withName("Alice Pauline")
            .withUsername("alicepauline")
            .withPassword("alicepauline01")
            .build();

    public static final User BENSON_MANAGER = new UserBuilder()
            .withName("Benson Meier")
            .withUsername("bensonmeier")
            .withPassword("bensonmeier02")
            .build();

    public static final User CARL_MANAGER = new UserBuilder()
            .withName("Carl Kurz")
            .withUsername("carlkurz")
            .withPassword("carlkurz03")
            .build();

    // Manually added
    public static final User HOON_MANAGER = new UserBuilder()
            .withName("Hoon Meier")
            .withUsername("hoonmeier")
            .withPassword("hoonmeier04")
            .build();

    public static final User IDA_MANAGER = new UserBuilder()
            .withName("Ida Mueller")
            .withUsername("idamueller")
            .withPassword("idamueller05")
            .build();

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UsersList} with all the typical users.
     */
    public static UsersList getTypicalUsersList() {
        UsersList usersList = new UsersList();
        for (User user : getTypicalUsers()) {
            usersList.addUser(user);
        }
        return usersList;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(ALICE_MANAGER, BENSON_MANAGER, CARL_MANAGER));
    }
}
