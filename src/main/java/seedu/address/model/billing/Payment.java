package seedu.address.model.billing;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents payment record for a tutee.
 * Guarantees: immutable.
 */
public class Payment {

    public static final Payment EMPTY = new Payment(Recurrence.MONTHLY, Collections.emptySet());

    private final Recurrence recurrence;
    private final Set<LocalDate> paidDates;

    /**
     * Creates a {@code Payment} object with payment frequency and payment history
     * @param recurrence Payment frequency
     * @param paidDates Payment history
     */
    public Payment(Recurrence recurrence, Set<LocalDate> paidDates) {
        requireNonNull(recurrence);
        requireNonNull(paidDates);
        this.recurrence = recurrence;
        this.paidDates = Collections.unmodifiableSet(new LinkedHashSet<>(paidDates));
    }

    /**
     * Returns a {@code Payment} object with recorded first payment date
     * @param date A valid first payment date
     * @return {@code Payment} object
     */
    public static Payment withInitialDate(LocalDate date) {
        requireNonNull(date);
        Set<LocalDate> dates = new LinkedHashSet<>();
        dates.add(date);
        return new Payment(Recurrence.MONTHLY, dates);
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public Set<LocalDate> getPaidDates() {
        return paidDates;
    }

    /**
     * Record payment made on {@code date}
     * @param date A valid date
     * @return {@code Payment} object with updated payment history
     */
    public Payment recordPayment(LocalDate date) {
        requireNonNull(date);
        Set<LocalDate> next = new LinkedHashSet<>(paidDates);
        next.add(date);
        return new Payment(recurrence, next);
    }

    /**
     * Checks if given {@code date} is in recorded payment history
     * @param date A valid date
     * @return {@code True} if date is present in payment history,
     *      {@code False} otherwise
     */
    public boolean hasPaidOn(LocalDate date) {
        requireNonNull(date);
        return paidDates.contains(date);
    }

    public Optional<LocalDate> getLastPaidDate() {
        if (paidDates.isEmpty()) {
            return Optional.empty();
        }
        LocalDate last = null;
        for (LocalDate d : paidDates) {
            if (last == null || d.isAfter(last)) {
                last = d;
            }
        }
        return Optional.of(last);
    }

    public Optional<LocalDate> getNextDueDate() {
        return getLastPaidDate().map(recurrence::nextDueDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Payment)) {
            return false;
        }
        Payment otherPayment = (Payment) other;
        return recurrence == otherPayment.recurrence
                && paidDates.equals(otherPayment.paidDates);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(recurrence, paidDates);
    }

    @Override
    public String toString() {
        return String.format("Payment[recurrence=%s, paidDates=%s]", recurrence, paidDates);
    }
}
