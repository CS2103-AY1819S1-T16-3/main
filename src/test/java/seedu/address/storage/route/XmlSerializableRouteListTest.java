package seedu.address.storage.route;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.google.common.collect.Streams;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.route.RouteList;
import seedu.address.testutil.TypicalRoutes;

public class XmlSerializableRouteListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableRouteListTest");
    private static final Path TYPICAL_ROUTE_FILE =
            TEST_DATA_FOLDER.resolve("typicalRouteRouteList.xml");
    private static final Path INVALID_ROUTE_FILE = TEST_DATA_FOLDER.resolve("invalidRouteRouteList.xml");
    private static final Path DUPLICATE_ROUTE_FILE =
            TEST_DATA_FOLDER.resolve("duplicateRouteRouteList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRouteFile_success() throws Exception {
        XmlSerializableRouteList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ROUTE_FILE,
                XmlSerializableRouteList.class);
        RouteList routeListFromFile = dataFromFile.toModelType();
        RouteList typicalRouteRouteList = TypicalRoutes.getTypicalRouteList();
        assertEquals(routeListFromFile, typicalRouteRouteList);
        assertTrue(Streams);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRouteList dataFromFile = XmlUtil.getDataFromFile(INVALID_ROUTE_FILE,
                XmlSerializableRouteList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRoute_throwsIllegalValueException() throws Exception {
        XmlSerializableRouteList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ROUTE_FILE,
                XmlSerializableRouteList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableRouteList.MESSAGE_DUPLICATE_ROUTE);
        dataFromFile.toModelType();
    }
}
