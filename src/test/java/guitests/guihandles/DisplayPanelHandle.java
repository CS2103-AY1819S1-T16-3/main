package guitests.guihandles;

import java.net.URL;

import javafx.scene.Node;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class DisplayPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    public DisplayPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
    }

}
