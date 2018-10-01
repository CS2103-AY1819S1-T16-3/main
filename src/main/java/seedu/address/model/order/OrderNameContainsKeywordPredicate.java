package seedu.address.model.order;

import seedu.address.commons.util.StringUtil;

import java.util.List;

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
