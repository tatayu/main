package dolla.parser;

import dolla.model.DollaData;
import dolla.Tag;
import dolla.Time;
import dolla.command.Command;
import dolla.command.modify.InitialModifyCommand;
import dolla.command.ShowListCommand;
import dolla.command.ShowBillListCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.AddBillCommand;
import dolla.command.SortCommand;
import dolla.command.ActionCommand;
import dolla.command.RemoveCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveNameCommand;
import dolla.model.Debt;
import dolla.model.RecordList;
import dolla.ui.DebtUi;
import dolla.ui.SearchUi;

import java.util.ArrayList;

//@@author tatayu
/**
 * DebtsParser is a class that handles the input command and
 * execute the command according to the command under the debt mode.
 */
public class DebtsParser extends Parser {

    public DebtsParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DEBT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals(DEBT_COMMAND_LIST)) { //show debt list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(BILL_COMMAND_LIST)) { //show bill list
            return new ShowBillListCommand(mode);
        } else if (commandToRun.equals(DEBT_COMMAND_OWE) || commandToRun.equals(DEBT_COMMAND_BORROW)) {
            String type = commandToRun;
            Tag t = new Tag();
            if (verifyDebtCommand()) {
                Debt debt = new Debt(type, inputArray[1], amount, description, date, t.getTagName());
                t.handleTag(debt);
                return new AddDebtsCommand(type, inputArray[1], amount, description, date, t.getTagName());
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(BILL_COMMAND_BILL)) {
            int people = 0;
            double amount = 0;
            ArrayList<String> nameList = new ArrayList<String>();
            try {
                people = Integer.parseInt(inputArray[1]);
                amount = stringToDouble(inputArray[2]);
                for (int i = 3; i < 3 + people; i++) {
                    String name = inputArray[i];
                    nameList.add(name);
                }
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidBillFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddBillCommand(BILL_COMMAND_BILL, people, amount, nameList);
        } else if (commandToRun.equals(BILL_COMMAND_PAID)) {
            int billNum = 0;
            String name = null;
            RecordList recordList;
            DollaData dollaData = new DollaData();
            recordList = dollaData.getBillRecordList();
            if (recordList.size() == 0) {
                DebtUi.printEmptyBillMessage();
            }
            try {
                if (verifyPaidCommand(inputArray[1], recordList) && inputArray[2] != null) {
                    billNum = Integer.parseInt(inputArray[1]);
                    name = inputArray[2];
                } else {
                    DebtUi.printInvalidPaidFormatError();
                    return new ErrorCommand();
                }
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidPaidFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new RemoveNameCommand(billNum, name);
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                // TODO
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SEARCH)) {
            String component = null;
            String content = null;
            try {
                if (verifyDebtSearchComponent(inputArray[1]) && inputArray[2] != null) {
                    component = inputArray[1];
                    content = inputArray[2];
                } else {
                    SearchUi.printInvalidDebtSearchComponent();
                }
            } catch (IndexOutOfBoundsException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            } catch (NullPointerException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            }
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REDO)
                || commandToRun.equals(COMMAND_UNDO)
                || commandToRun.equals(COMMAND_REPEAT)) {
            return new ActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
        return new ErrorCommand();
    }
}
