package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalOrders.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Food;
import seedu.address.model.order.OrderDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedOrderTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "12/10/2018";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final List<XmlAdaptedFood> VALID_FOOD = BENSON.getFood().stream()
            .map(XmlAdaptedFood::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        XmlAdaptedOrder order = new XmlAdaptedOrder(BENSON);
        assertEquals(BENSON, order.toModelType());
    }

    @Test
    public void toModelType_invalidOrder_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_FOOD);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(null,
            VALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_FOOD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_DATE, VALID_FOOD);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(
            VALID_NAME, null, VALID_ADDRESS, VALID_DATE, VALID_FOOD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }


    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_DATE, VALID_FOOD);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(
            VALID_NAME, VALID_PHONE, null, VALID_DATE, VALID_FOOD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_ADDRESS, INVALID_DATE, VALID_FOOD);
        String expectedMessage = OrderDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null, VALID_FOOD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullFood_throwsIllegalValueException() {
        XmlAdaptedOrder order =
                new XmlAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
