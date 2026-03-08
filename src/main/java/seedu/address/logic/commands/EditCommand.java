package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;


/**
 * Edits the details of an existing person in the address book as per subcommand
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // to shift to EditXYZCommand.java where XYZ stands for attendance, payment, etc.
    // all edit commands should extend EditCommand
    public static final String ATTENDANCE_SUB_COMMAND_WORD = "attd";
    public static final String PAYMENT_SUB_COMMAND_WORD = "payment";
    public static final String TAG_SUB_COMMAND_WORD = "tag";
    public static final String SUBJECT_SUB_COMMAND_WORD = "subject";
    public static final String PARENT_SUB_COMMAND_WORD = "parent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: SUBCOMMAND (person, payment, ...) "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + EditPersonCommand.SUB_COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
}
