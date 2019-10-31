package dolla.parser;

//@@author omupenguin
public interface ParserStringList {

    // Modify specific modes
    static final String MODE_MODIFY_ENTRY = "modify entry";
    static final String MODE_MODIFY_LIMIT = "modify limit";
    static final String MODE_MODIFY_DEBT = "modify debt";
    static final String MODE_MODIFY_SHORTCUT = "modify shortcut";

    // Commands specific to Entry mode
    static final String ENTRY_COMMAND_ADD = "add";
    static final String ENTRY_COMMAND_LIST = "entries";

    // Commands specific to Limit mode
    static final String LIMIT_COMMAND_LIST = "limits";
    static final String LIMIT_COMMAND_SET = "set";

    // Commands specific to Debt mode
    static final String DEBT_COMMAND_OWE = "owe";
    static final String DEBT_COMMAND_BORROW = "borrow";
    static final String DEBT_COMMAND_LIST = "debts";
    static final String BILL_COMMAND_BILL = "bill";
    static final String BILL_COMMAND_LIST = "bills";

    // Shared Commands
    static final String COMMAND_MODIFY = "modify";
    static final String COMMAND_SORT = "sort";
    static final String COMMAND_SEARCH = "search";
    static final String COMMAND_REMOVE = "remove";


}
