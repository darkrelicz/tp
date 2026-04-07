package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceHistory;
import seedu.address.model.recurrence.Recurrence;
import seedu.address.model.session.ScheduledSession;

/**
 * Jackson-friendly version of {@link ScheduledSession}.
 */
class JsonAdaptedScheduledSession {

    private static final String SESSION_START_MESSAGE_CONSTRAINTS =
            "Session start date-time must be in ISO 8601 local format, e.g. 2026-01-13T08:00:00";
    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.withResolverStyle(ResolverStyle.STRICT);

    private final String start;
    private final String next;
    private final String recurrence;
    private final String description;
    private final List<JsonAdaptedAppointmentAttendance> attendanceRecords;

    @JsonCreator
    public JsonAdaptedScheduledSession(@JsonProperty("start") String start,
                                       @JsonProperty("next") String next,
                                       @JsonProperty("recurrence") String recurrence,
                                       @JsonProperty("description") String description,
                                       @JsonProperty("attendanceRecords")
                                       List<JsonAdaptedAppointmentAttendance> attendanceRecords) {
        this.start = start;
        this.next = next;
        this.recurrence = recurrence;
        this.description = description;
        this.attendanceRecords = attendanceRecords;
    }

    public JsonAdaptedScheduledSession(ScheduledSession source) {
        this.start = source.getStart().toString();
        this.next = source.getNext().toString();
        this.recurrence = source.getRecurrence().name();
        this.description = source.getDescription();
        this.attendanceRecords = source.getAttendanceHistory().getRecords().stream()
                .map(JsonAdaptedAppointmentAttendance::new)
                .toList();
    }

    public ScheduledSession toModelType() throws IllegalValueException {
        if (start == null) {
            throw new IllegalValueException(String.format(
                    JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, "session start"));
        }

        LocalDateTime modelStart;
        try {
            modelStart = LocalDateTime.parse(start, DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(SESSION_START_MESSAGE_CONSTRAINTS);
        }

        LocalDateTime modelNext = modelStart;
        if (next != null) {
            try {
                modelNext = LocalDateTime.parse(next, DATETIME_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new IllegalValueException(SESSION_START_MESSAGE_CONSTRAINTS);
            }
        }

        Recurrence modelRecurrence = Recurrence.NONE;
        if (recurrence != null) {
            try {
                modelRecurrence = Recurrence.valueOf(recurrence);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(
                        "Session recurrence must be one of: WEEKLY, BIWEEKLY, MONTHLY, NONE");
            }
        }

        AttendanceHistory modelAttendanceHistory = AttendanceHistory.EMPTY;
        if (attendanceRecords != null) {
            for (JsonAdaptedAppointmentAttendance attendanceRecord : attendanceRecords) {
                modelAttendanceHistory = modelAttendanceHistory.addAttendance(attendanceRecord.toModelType());
            }
        }

        return new ScheduledSession(modelRecurrence, modelStart, modelNext,
                modelAttendanceHistory, description == null ? "" : description);
    }
}
