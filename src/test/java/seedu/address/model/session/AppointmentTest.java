package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.AttendanceHistory;
import seedu.address.model.recurrence.Recurrence;

public class AppointmentTest {

    @Test
    public void constructor_duplicateSessionDescriptions_throwsIllegalArgumentException() {
        List<ScheduledSession> sessions = List.of(
                createSession("2026-01-01T10:00:00", "Algebra"),
                createSession("2026-01-08T10:00:00", " Algebra "));

        assertThrows(IllegalArgumentException.class, Appointment.MESSAGE_DUPLICATE_SESSION_DESCRIPTION, () ->
                new Appointment(sessions));
    }

    @Test
    public void addSession_duplicateSessionDescription_throwsIllegalArgumentException() {
        Appointment appointment = new Appointment(List.of(createSession("2026-01-01T10:00:00", "Algebra")));

        assertThrows(IllegalArgumentException.class, Appointment.MESSAGE_DUPLICATE_SESSION_DESCRIPTION, () ->
                appointment.addSession(createSession("2026-01-08T10:00:00", " Algebra ")));
    }

    @Test
    public void withSessionAt_duplicateSessionDescription_throwsIllegalArgumentException() {
        Appointment appointment = new Appointment(List.of(
                createSession("2026-01-01T10:00:00", "Algebra"),
                createSession("2026-01-08T10:00:00", "Consultation")));

        assertThrows(IllegalArgumentException.class, Appointment.MESSAGE_DUPLICATE_SESSION_DESCRIPTION, () ->
                appointment.withSessionAt(1, createSession("2026-01-08T10:00:00", " Algebra ")));
    }

    @Test
    public void getSessions_returnsImmutableDefensiveCopy() {
        Appointment appointment = new Appointment(List.of(createSession("2026-01-01T10:00:00", "Algebra")));

        List<ScheduledSession> returnedSessions = appointment.getSessions();

        assertNotSame(returnedSessions, appointment.getSessions());
        assertThrows(UnsupportedOperationException.class, () -> returnedSessions.add(
                createSession("2026-01-08T10:00:00", "Consultation")));
        assertEquals(1, appointment.getSessions().size());
    }

    @Test
    public void constructor_withMutableSourceList_isDefensive() {
        List<ScheduledSession> sourceSessions = new ArrayList<>();
        sourceSessions.add(createSession("2026-01-01T10:00:00", "Algebra"));

        Appointment appointment = new Appointment(sourceSessions);
        sourceSessions.add(createSession("2026-01-08T10:00:00", "Consultation"));

        assertEquals(1, appointment.getSessions().size());
    }

    private ScheduledSession createSession(String start, String description) {
        LocalDateTime dateTime = LocalDateTime.parse(start);
        return new ScheduledSession(Recurrence.NONE, dateTime, dateTime, AttendanceHistory.EMPTY, description);
    }
}
