package seedu.address.model.order;

import seedu.address.commons.util.StringUtil;

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
