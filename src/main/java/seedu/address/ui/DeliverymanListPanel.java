package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeliveryManPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * Panel containing the list of orders.
 */
public class DeliverymanListPanel extends UiPart<Region> {
    private static final String FXML = "DeliverymanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliverymanListPanel.class);

    @FXML
    private ListView<Deliveryman> deliverymanListView;

    public DeliverymanListPanel(ObservableList<Deliveryman> deliveryManList) {
        super(FXML);

        setConnections(deliveryManList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Deliveryman> orderList) {
        deliverymanListView.setItems(orderList);
        deliverymanListView.setCellFactory(listView -> new DeliveryManListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deliverymanListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in delivery man list panel changed to : '" + newValue + "'");
                        raise(new DeliveryManPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            deliverymanListView.scrollTo(index);
            deliverymanListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class DeliveryManListViewCell extends ListCell<Deliveryman> {
        @Override
        protected void updateItem(Deliveryman deliveryman, boolean empty) {
            super.updateItem(deliveryman, empty);

            if (empty || deliveryman == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeliverymanCard(deliveryman, getIndex() + 1).getRoot());
            }
        }
    }

}
