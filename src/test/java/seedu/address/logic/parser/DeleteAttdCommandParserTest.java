package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDANCE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDANCE_DATE_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ATTENDANCE_DATE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAttdCommand;

public class DeleteAttdCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAttdCommand.MESSAGE_USAGE);

    private final DeleteAttdCommandParser parser = new DeleteAttdCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 s/1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 d/2026-01-29", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1 s/1 d/2026-01-29", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 s/1 d/2026-01-29", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 abc s/1 d/2026-01-29", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser, "1 s/1" + INVALID_ATTENDANCE_DATE_DESC,
                DeleteAttdCommandParser.MESSAGE_INVALID_ATTENDANCE_DATE_OR_TIME);
    }

    @Test
    public void parse_validDate_success() {
        assertParseSuccess(parser, "1 s/1" + ATTENDANCE_DATE_DESC,
                new DeleteAttdCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, LocalDate.parse("2026-01-29")));
    }

    @Test
    public void parse_validDateTime_success() {
        assertParseSuccess(parser, "1 s/1" + ATTENDANCE_DATE_TIME_DESC,
                new DeleteAttdCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON,
                        LocalDateTime.parse("2026-01-29T08:30:00")));
    }
}
