package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAttdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteAttdCommand} object.
 */
public class DeleteAttdCommandParser implements Parser<DeleteAttdCommand> {

    public static final String MESSAGE_INVALID_ATTENDANCE_DATE_OR_TIME =
            "Attendance date must be in ISO 8601 local date or date-time format, "
                    + "e.g. 2026-01-29 or 2026-01-29T08:00:00";

    @Override
    public DeleteAttdCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION, PREFIX_DATE);

        Index personIndex = ParserUtil.parseIndex(argMultimap.getPreamble(), DeleteAttdCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_SESSION).isEmpty() || argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttdCommand.MESSAGE_USAGE));
        }

        Index sessionIndex = ParserUtil.parseIndex(
                argMultimap.getValue(PREFIX_SESSION).get(), DeleteAttdCommand.MESSAGE_USAGE);

        String attendanceTarget = argMultimap.getValue(PREFIX_DATE).get();
        try {
            LocalDateTime recordedAt = ParserUtil.parseIsoDateTime(attendanceTarget);
            return new DeleteAttdCommand(personIndex, sessionIndex, recordedAt);
        } catch (ParseException ignored) {
            try {
                LocalDate recordedDate = ParserUtil.parseIsoDate(attendanceTarget);
                return new DeleteAttdCommand(personIndex, sessionIndex, recordedDate);
            } catch (ParseException ignoredDate) {
                throw new ParseException(MESSAGE_INVALID_ATTENDANCE_DATE_OR_TIME);
            }
        }
    }
}
