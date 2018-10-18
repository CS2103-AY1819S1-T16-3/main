package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of orders.
 */
public class Display extends UiPart<Region> {

    private static final String FXML = "Display.fxml";
    private final Logger logger = LogsCenter.getLogger(Display.class);

    public Display() {
        super(FXML);
    }
}
