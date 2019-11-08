package dolla.parser;


import dolla.command.*;

public class ShortcutParser extends Parser {

    /**
     * Creates an instance of a parser.
     *
     * @param inputLine The entire string containing the user's input.
     */
    public ShortcutParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_SHORTCUT;
    }

    @Override
    public Command parseInput() {
        switch (commandToRun) {
        case SHORTCUT_COMMAND_LIST:
            return new ShowListCommand(mode);
        case ENTRY_COMMAND_LIST:
            return new ShowListCommand(MODE_ENTRY);
        case COMMAND_REMOVE:
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        case SHORTCUT_COMMAND_CREATE:
            if (verifyShortcut()) {
                return new AddShortcutCommand(inputArray[1], mode);
            } else {
                return new ErrorCommand();
            }
        case SHORTCUT_COMMAND_EXECUTE:
            if (verifyShortcut()) {
                return new ExecuteShortcutCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        case COMMAND_REDO:
        case COMMAND_UNDO:
            return new ActionCommand(mode, commandToRun);
        case COMMAND_SORT:
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        default:
            return invalidCommand();
        }
    }
}
