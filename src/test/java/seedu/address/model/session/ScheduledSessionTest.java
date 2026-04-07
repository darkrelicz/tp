package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceHistory;
import seedu.address.model.recurrence.Recurrence;

public class ScheduledSessionTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 1, 1, 10, 0);
    private static final LocalDateTime NEXT = LocalDateTime.of(2026, 1, 8, 10, 0);

    @Test
    public void withAdvancedNext_noneRecurrence_returnsSameInstance() {
        ScheduledSession session = new ScheduledSession(Recurrence.NONE, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertSame(session, session.withAdvancedNext());
    }

    @Test
    public void withAdvancedNext_recurringSession_advancesNextDate() {
        ScheduledSession session = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                        AttendanceHistory.EMPTY, "Algebra");

        ScheduledSession advanced = session.withAdvancedNext();

        assertTrue(advanced.getNext().equals(NEXT.plusWeeks(1)));
    }

    @Test
    public void withRolledBackNext_noneRecurrence_returnsSameInstance() {
        ScheduledSession session = new ScheduledSession(Recurrence.NONE, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertSame(session, session.withRolledBackNext());
    }

    @Test
    public void withRolledBackNext_recurringSession_rollsBackNextDate() {
        ScheduledSession session = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                        AttendanceHistory.EMPTY, "Algebra");

        ScheduledSession rolledBack = session.withRolledBackNext();

        assertTrue(rolledBack.getNext().equals(NEXT.minusWeeks(1)));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ScheduledSession session = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertTrue(session.equals(session));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertTrue(first.equals(second));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ScheduledSession session = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertFalse(session.equals(5));
    }

    @Test
    public void equals_differentRecurrence_returnsFalse() {
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.BIWEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertFalse(first.equals(second));
    }

    @Test
    public void equals_differentStart_returnsFalse() {
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.WEEKLY, START.plusDays(1), NEXT,
                AttendanceHistory.EMPTY, "Algebra");

        assertFalse(first.equals(second));
    }

    @Test
    public void equals_differentNext_returnsFalse() {
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.WEEKLY, START, NEXT.plusDays(1),
                AttendanceHistory.EMPTY, "Algebra");

        assertFalse(first.equals(second));
    }

    @Test
    public void equals_differentAttendanceHistory_returnsFalse() {
        AttendanceHistory firstHistory = AttendanceHistory.EMPTY;
        AttendanceHistory secondHistory = AttendanceHistory.EMPTY.addAttendance(
                new Attendance(true, LocalDateTime.of(2026, 1, 1, 9, 0)));
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                firstHistory, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                secondHistory, "Algebra");

        assertFalse(first.equals(second));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        ScheduledSession first = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Algebra");
        ScheduledSession second = new ScheduledSession(Recurrence.WEEKLY, START, NEXT,
                AttendanceHistory.EMPTY, "Geometry");

        assertFalse(first.equals(second));
    }
}
