package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_REQUIRE_LOGIN;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.OrderBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final OrderBookParser orderBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        orderBookParser = new OrderBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = orderBookParser.parseCommand(commandText);
            if (model.isUserLoggedIn() || isNotAuthenticatedCommand(command)) {
                return command.execute(model, history);
            } else {
                return new CommandResult(MESSAGE_REQUIRE_LOGIN);
            }
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    /**
     * Returns true if it is not an authenticated command.
     */
    private boolean isNotAuthenticatedCommand(Command command) {
        return command instanceof LoginCommand || command instanceof SignUpCommand
                || command instanceof HelpCommand || command instanceof HistoryCommand
                || command instanceof LogoutCommand;

    }
}
