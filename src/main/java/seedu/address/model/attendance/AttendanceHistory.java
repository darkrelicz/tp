package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable attendance history for a scheduled session.
 */
public final class AttendanceHistory {

    public static final AttendanceHistory EMPTY = new AttendanceHistory();

    private final List<Attendance> records;

    /**
     * Creates an empty attendance history.
     */
    public AttendanceHistory() {
        this.records = List.of();
    }

    /**
     * Creates an attendance history from the provided records.
     */
    public AttendanceHistory(Attendance... records) {
        requireAllNonNull((Object[]) records);
        List<Attendance> copiedRecords = new ArrayList<>();
        Collections.addAll(copiedRecords, records);
        this.records = List.copyOf(copiedRecords);
    }

    private AttendanceHistory(List<Attendance> records) {
        this.records = List.copyOf(records);
    }

    /**
     * Returns a new {@code AttendanceHistory} with {@code attendance} appended.
     */
    public AttendanceHistory addAttendance(Attendance attendance) {
        requireAllNonNull(attendance);
        List<Attendance> updatedRecords = new ArrayList<>(records);
        updatedRecords.add(attendance);
        return new AttendanceHistory(updatedRecords);
    }

    /**
     * Returns a new {@code AttendanceHistory} with all records on {@code date} removed.
     */
    public AttendanceHistory removeAttendance(LocalDate date) {
        requireAllNonNull(date);
        List<Attendance> updatedRecords = records.stream()
                .filter(record -> !record.getRecordedAt().toLocalDate().equals(date))
                .toList();
        return new AttendanceHistory(updatedRecords);
    }

    /**
     * Returns true if there is an attendance record on {@code date}.
     */
    public boolean hasRecordOn(LocalDate date) {
        requireAllNonNull(date);
        return records.stream().anyMatch(record -> record.getRecordedAt().toLocalDate().equals(date));
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }

    public List<Attendance> getRecords() {
        return records;
    }

    public Optional<Attendance> getLastRecord() {
        if (records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(records.get(records.size() - 1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceHistory)) {
            return false;
        }

        AttendanceHistory otherAttendance = (AttendanceHistory) other;
        return records.equals(otherAttendance.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(records);
    }

    @Override
    public String toString() {
        return records.toString();
    }
}
