package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Streams;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.OrderBook;
import seedu.address.testutil.TypicalOrders;

public class XmlSerializableOrderBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableOrderBookTest");
    private static final Path TYPICAL_ORDERS_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersOrderBook.xml");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderOrderBook.xml");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderOrderBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        XmlSerializableOrderBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ORDERS_FILE,
                XmlSerializableOrderBook.class);
        OrderBook orderBookFromFile = dataFromFile.toModelType();
        OrderBook typicalOrdersOrderBook = TypicalOrders.getTypicalOrderBook();
        assertEquals(orderBookFromFile, typicalOrdersOrderBook);
        assertTrue(Streams.zip(orderBookFromFile.getOrderList().stream(),
            typicalOrdersOrderBook.getOrderList().stream(), (a, b) -> a.hasSameId(b)).allMatch(x -> x));
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableOrderBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ORDER_FILE,
                XmlSerializableOrderBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableOrderBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ORDER_FILE,
                XmlSerializableOrderBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableOrderBook.MESSAGE_DUPLICATE_ORDER);
        dataFromFile.toModelType();
    }

}
