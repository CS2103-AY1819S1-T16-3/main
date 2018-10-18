package guitests.guihandles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.order.Order;

/**
 * Provides a handle to a order card in the order list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String FOOD_FIELD_ID = "#foodList";

    private final Label idLabel;
    private final Label addressLabel;
    private final Label foodLabel;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        foodLabel = getChildNode(FOOD_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getFood() {
        return Arrays.asList(foodLabel.getText().split(","));
    }

    /**
     * Returns true if this handle contains {@code order}.
     */
    public boolean equals(Order order) {
        return getAddress().equals(order.getAddress().value)
                && ImmutableMultiset.copyOf(getFood()).equals(ImmutableMultiset.copyOf(order.getFood().stream()
                .map(food -> food.foodName)
                .collect(Collectors.toList())));
    }
}
