package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteApptCommand} object.
 */
public class DeleteApptCommandParser implements Parser<DeleteApptCommand> {

    @Override
    public DeleteApptCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SESSION);

        Index personIndex = ParserUtil.parseIndex(argMultimap.getPreamble(), DeleteApptCommand.MESSAGE_USAGE);

        List<String> sessionIndexStrings = argMultimap.getAllValues(PREFIX_SESSION);
        if (sessionIndexStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE));
        }

        List<Index> sessionIndices = new ArrayList<>();
        for (String sessionIndexString : sessionIndexStrings) {
            sessionIndices.add(ParserUtil.parseIndex(sessionIndexString, DeleteApptCommand.MESSAGE_USAGE));
        }

        return new DeleteApptCommand(personIndex, sessionIndices);
    }
}
