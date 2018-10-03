package seedu.address.model.order;

import java.util.function.Predicate;

/**
 * Tests that a {@code Order} matches any of the keywords given.
 */
public interface OrderContainsAnyKeywordsPredicate extends Predicate<Order> {}
