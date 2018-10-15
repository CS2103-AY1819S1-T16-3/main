package seedu.address.storage.deliveryman;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.deliveryman.XmlAdaptedDeliveryman.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedDeliverymanTest {
    private static final String INVALID_NAME = "D@mi+h";

    @Test
    public void toModelType_validDeliverymanDetails_returnsDeliveryman() throws Exception {
        XmlAdaptedDeliveryman deliveryman = new XmlAdaptedDeliveryman(RAJUL);
        assertEquals(RAJUL, deliveryman.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedDeliveryman deliveryman =
            new XmlAdaptedDeliveryman(0, INVALID_NAME);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deliveryman::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedDeliveryman deliveryman = new XmlAdaptedDeliveryman(0, (String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deliveryman::toModelType);
    }

}
