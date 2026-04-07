package seedu.address.model.recurrence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class RecurrenceTest {

    @Test
    public void next_monthlyFromJanuary31_returnsFebruary28() {
        LocalDate from = LocalDate.of(2026, 1, 31);
        assertEquals(LocalDate.of(2026, 2, 28), Recurrence.MONTHLY.next(from));
    }

    @Test
    public void next_weekly_addsSevenDays() {
        LocalDate from = LocalDate.of(2026, 3, 28);
        assertEquals(LocalDate.of(2026, 4, 4), Recurrence.WEEKLY.next(from));
    }

    @Test
    public void next_biweekly_addsFourteenDays() {
        LocalDate from = LocalDate.of(2026, 3, 28);
        assertEquals(LocalDate.of(2026, 4, 11), Recurrence.BIWEEKLY.next(from));
    }

    @Test
    public void next_none_returnsSameDate() {
        LocalDate from = LocalDate.of(2026, 3, 28);
        assertEquals(from, Recurrence.NONE.next(from));
    }

    @Test
    public void next_monthlyFromJanuary31InLeapYear_returnsFebruary29() {
        LocalDate from = LocalDate.of(2024, 1, 31);
        assertEquals(LocalDate.of(2024, 2, 29), Recurrence.MONTHLY.next(from));
    }

    @Test
    public void previous_monthlyFromMarch31_returnsFebruaryEnd() {
        LocalDate from = LocalDate.of(2026, 3, 31);
        assertEquals(LocalDate.of(2026, 2, 28), Recurrence.MONTHLY.previous(from));
    }

    @Test
    public void previous_none_returnsSameDate() {
        LocalDate from = LocalDate.of(2026, 3, 28);
        assertEquals(from, Recurrence.NONE.previous(from));
    }

    @Test
    public void nextAndPrevious_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Recurrence.WEEKLY.next(null));
        assertThrows(NullPointerException.class, () -> Recurrence.BIWEEKLY.previous(null));
        assertThrows(NullPointerException.class, () -> Recurrence.MONTHLY.next(null));
        assertThrows(NullPointerException.class, () -> Recurrence.NONE.previous(null));
    }
}
