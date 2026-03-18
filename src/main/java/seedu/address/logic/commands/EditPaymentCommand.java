package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;

/**
 * Edits the appointment start date-time of an existing person in the address book.
 */
public class EditPaymentCommand extends EditCommand {

    public static final String SUB_COMMAND_WORD = "payment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SUB_COMMAND_WORD
            + ": Records the day tuition fees was paid by the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: appt INDEX (must be a positive integer) "
            + PREFIX_APPOINTMENT_START + "DATETIME\n"
            + "Example: " + COMMAND_WORD + " " + SUB_COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_START + "2026-01-13T08:00:00";

    public static final String MESSAGE_EDIT_PAYMENT_SUCCESS = "Recorded date tuition fees paid by %1$s: %2$s";

    private final Index index;
    private final LocalDateTime paymentDate;

    /**
     * @param index of the person in the filtered person list to edit
     * @param paymentDate payment date-time to set
     */
    public EditPaymentCommand(Index index, LocalDateTime paymentDate) {
        requireNonNull(index);
        requireNonNull(paymentDate);
        this.index = index;
        this.paymentDate = paymentDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit)
                .withPaymentDate(Optional.of(paymentDate))
                .build();

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String formattedStart = paymentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new CommandResult(String.format(MESSAGE_EDIT_PAYMENT_SUCCESS,
                editedPerson.getName().fullName, formattedStart));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditPaymentCommand)) {
            return false;
        }

        EditPaymentCommand otherCommand = (EditPaymentCommand) other;
        return index.equals(otherCommand.index)
                && paymentDate.equals(otherCommand.paymentDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("paymentDate", paymentDate)
                .toString();
    }
}
