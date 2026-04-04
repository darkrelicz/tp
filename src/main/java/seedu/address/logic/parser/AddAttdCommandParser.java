package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAttdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddAttdCommand} object.
 */
public class AddAttdCommandParser implements Parser<AddAttdCommand> {

    public static final String MESSAGE_INVALID_ATTENDANCE_STATUS = "Attendance status must be 'y' or 'n'.";
    public static final String MESSAGE_DATE_NOT_ALLOWED_FOR_ABSENCE =
            "A date override can only be provided when recording an attended appointment.";
    public static final String MESSAGE_INVALID_ATTENDANCE_DATE_OR_TIME =
            "Attendance date must be in ISO 8601 local date or date-time format, "
                    + "e.g. 2026-01-29 or 2026-01-29T08:00:00";

    @Override
    public AddAttdCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION, PREFIX_DATE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SESSION, PREFIX_DATE);

        String trimmedPreamble = argMultimap.getPreamble().trim();
        if (trimmedPreamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttdCommand.MESSAGE_USAGE));
        }
        String[] preambleParts = trimmedPreamble.split("\\s+");
        if (preambleParts.length < 1 || preambleParts.length > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttdCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(preambleParts[0], AddAttdCommand.MESSAGE_USAGE);
        if (argMultimap.getValue(PREFIX_SESSION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttdCommand.MESSAGE_USAGE));
        }
        Index sessionIndex = ParserUtil.parseIndex(
            argMultimap.getValue(PREFIX_SESSION).get(), AddAttdCommand.MESSAGE_USAGE);
        boolean hasAttended = true;
        if (preambleParts.length == 2) {
            String attendanceStatus = preambleParts[1].toLowerCase();
            if (!attendanceStatus.equals("y") && !attendanceStatus.equals("n")) {
                throw new ParseException(MESSAGE_INVALID_ATTENDANCE_STATUS);
            }
            hasAttended = attendanceStatus.equals("y");
        }
        Optional<LocalDateTime> recordedAt = Optional.empty();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (!hasAttended) {
                throw new ParseException(MESSAGE_DATE_NOT_ALLOWED_FOR_ABSENCE);
            }
            recordedAt = Optional.of(parseRecordedAt(argMultimap.getValue(PREFIX_DATE).get()));
        }

        return new AddAttdCommand(personIndex, sessionIndex, hasAttended, recordedAt);
    }

    private LocalDateTime parseRecordedAt(String value) throws ParseException {
        try {
            return ParserUtil.parseIsoDateTime(value);
        } catch (ParseException ignored) {
            try {
                return ParserUtil.parseIsoDate(value).atStartOfDay();
            } catch (ParseException ignoredDate) {
                throw new ParseException(MESSAGE_INVALID_ATTENDANCE_DATE_OR_TIME);
            }
        }
    }
}
