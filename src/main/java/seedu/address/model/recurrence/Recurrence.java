package seedu.address.model.recurrence;

import java.time.LocalDate;

/**
 * Recurrence schedule options for recurring dates.
 */
public enum Recurrence {
    WEEKLY {
        @Override
        public LocalDate next(LocalDate from) {
            return from.plusWeeks(1);
        }
    },
    BIWEEKLY {
        @Override
        public LocalDate next(LocalDate from) {
            return from.plusWeeks(2);
        }
    },
    MONTHLY {
        @Override
        public LocalDate next(LocalDate from) {
            return from.plusMonths(1);
        }
    },
    NONE {
        @Override
        public LocalDate next(LocalDate from) {
            return from;
        }
    };

    /**
     * Returns the next scheduled date according to this recurrence.
     */
    public abstract LocalDate next(LocalDate from);
}
