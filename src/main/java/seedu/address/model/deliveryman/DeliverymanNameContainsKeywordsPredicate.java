package seedu.address.model.deliveryman;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

public class DeliverymanNameContainsKeywordsPredicate implements Predicate<Deliveryman> {
    private final List<String> keywords;

    public DeliverymanNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Deliveryman deliveryman) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deliveryman.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DeliverymanNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
