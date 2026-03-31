package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

/**
 * Jackson-friendly version of appointment {@link Attendance}.
 */
class JsonAdaptedAppointmentAttendance {

    private static final String ATTENDANCE_DATE_MESSAGE_CONSTRAINTS =
            "Appointment attendance date must be in ISO 8601 local date or date-time format, "
                    + "e.g. 2026-01-29 or 2026-01-29T08:00:00";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.withResolverStyle(ResolverStyle.STRICT);

    private final boolean hasAttended;
    private final String recordedDate;

    @JsonCreator
    public JsonAdaptedAppointmentAttendance(@JsonProperty("hasAttended") boolean hasAttended,
                                            @JsonProperty("recordedDate") String recordedDate) {
        this.hasAttended = hasAttended;
        this.recordedDate = recordedDate;
    }

    public JsonAdaptedAppointmentAttendance(Attendance source) {
        this.hasAttended = source.hasAttended();
        this.recordedDate = source.getRecordedAt().format(DATE_TIME_FORMATTER);
    }

    public Attendance toModelType() throws IllegalValueException {
        if (recordedDate == null) {
            throw new IllegalValueException(String.format(
                    JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, "appointment attendance date"));
        }

        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(recordedDate, DATE_TIME_FORMATTER);
            return new Attendance(hasAttended, parsedDateTime);
        } catch (DateTimeParseException ignored) {
            try {
                return new Attendance(hasAttended, LocalDate.parse(recordedDate, DATE_FORMATTER));
            } catch (DateTimeParseException e) {
                throw new IllegalValueException(ATTENDANCE_DATE_MESSAGE_CONSTRAINTS);
            }
        }
    }
}
