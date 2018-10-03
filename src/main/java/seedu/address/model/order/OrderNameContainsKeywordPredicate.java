package seedu.address.model.order;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Order}'s {@code Name} matches any of the keywords given.
 */
public class OrderNameContainsKeywordPredicate implements OrderContainsAnyKeywordsPredicate {
    private final List<String> keywords;

    public OrderNameContainsKeywordPredicate(List<String> names) {
        keywords = names;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(order.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderNameContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((OrderNameContainsKeywordPredicate) other).keywords)); // state check
    }
}
