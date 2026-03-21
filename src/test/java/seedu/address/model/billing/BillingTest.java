package seedu.address.model.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BillingTest {

    @Test
    public void constructor_validMonthlyRate_success() {
        // Test valid monthly rate
        Billing billing = new Billing(100.0);
        assertEquals(100.0, billing.getMonthlyRate());
    }

    @Test
    public void constructor_zeroMonthlyRate_success() {
        // Test zero monthly rate
        Billing billing = new Billing(0.0);
        assertEquals(0.0, billing.getMonthlyRate());
    }

    @Test
    public void constructor_negativeMonthlyRate_throwsIllegalArgumentException() {
        // Test negative monthly rate
        assertThrows(IllegalArgumentException.class, () -> new Billing(-1.0));
    }

    @Test
    public void defaultBilling_returnsDefaultBilling() {
        // Test default billing
        Billing defaultBilling = Billing.defaultBilling();
        assertEquals(0.0, defaultBilling.getMonthlyRate());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Billing billing = new Billing(50.0);
        assertTrue(billing.equals(billing));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Billing billing1 = new Billing(50.0);
        Billing billing2 = new Billing(50.0);
        assertTrue(billing1.equals(billing2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Billing billing1 = new Billing(50.0);
        Billing billing2 = new Billing(60.0);
        assertFalse(billing1.equals(billing2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Billing billing = new Billing(50.0);
        assertFalse(billing.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Billing billing = new Billing(50.0);
        assertFalse(billing.equals("50.0"));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Billing billing1 = new Billing(50.0);
        Billing billing2 = new Billing(50.0);
        assertEquals(billing1.hashCode(), billing2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        Billing billing1 = new Billing(50.0);
        Billing billing2 = new Billing(60.0);
        assertNotEquals(billing1.hashCode(), billing2.hashCode());
    }

    @Test
    public void toString_validBilling_returnsFormattedString() {
        Billing billing = new Billing(123.45);
        String expected = "Billing[monthlyRate=123.45]";
        assertEquals(expected, billing.toString());
    }
}