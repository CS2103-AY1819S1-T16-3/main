package seedu.address.model.order;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class OrderPhoneContainsKeywordPredicate implements OrderContainsAnyKeywordsPredicate {
    private final String keyword;

    public OrderPhoneContainsKeywordPredicate(String phone) {
        keyword = phone;
    }

    @Override
    public boolean test(Order order) {
        return keyword.equals(order.getPhone().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderPhoneContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((OrderPhoneContainsKeywordPredicate) other).keyword)); // state check
    }
}
