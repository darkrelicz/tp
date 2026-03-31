package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable attendance record for a scheduled appointment.
 */
public final class Attendance {

    private final boolean hasAttended;
    private final LocalDateTime recordedAt;

    /**
     * Creates an attendance record.
     */
    public Attendance(boolean hasAttended, LocalDate recordedDate) {
        this(hasAttended, recordedDate.atStartOfDay());
    }

    /**
     * Creates an attendance record.
     */
    public Attendance(boolean hasAttended, LocalDateTime recordedAt) {
        requireAllNonNull(recordedAt);
        this.hasAttended = hasAttended;
        this.recordedAt = recordedAt;
    }

    public boolean hasAttended() {
        return hasAttended;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return hasAttended == otherAttendance.hasAttended
                && recordedAt.equals(otherAttendance.recordedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasAttended, recordedAt);
    }

    @Override
    public String toString() {
        return "Attendance{"
                + "hasAttended=" + hasAttended
                + ", recordedAt=" + recordedAt
                + "}";
    }
}
