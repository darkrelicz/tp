package seedu.address.model.billing;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents billing configuration for a tutee.
 * Guarantees: immutable.
 */
public class Billing {

    private static final double DEFAULT_MONTHLY_RATE = 0.0;

    private final double monthlyRate;

    /**
     * Creates a billing record with monthly fee.
     * @param monthlyRate A non-negative amount
     */
    public Billing(double monthlyRate) {
        checkArgument(monthlyRate >= 0, "Monthly rate must be non-negative");
        this.monthlyRate = monthlyRate;
    }

    /**
     * Returns a new {@code Billing} object with default monthly rate
     * @return {@code Billing} object with default monthly rate
     */
    public static Billing defaultBilling() {
        return new Billing(DEFAULT_MONTHLY_RATE);
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Billing)) {
            return false;
        }
        Billing otherBilling = (Billing) other;
        return Double.compare(monthlyRate, otherBilling.monthlyRate) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(monthlyRate);
    }

    @Override
    public String toString() {
        return String.format("Billing[monthlyRate=%.2f]", monthlyRate);
    }
}

