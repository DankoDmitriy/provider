package com.danko.provider.controller.command;

import com.danko.provider.controller.command.impl.admin.UserListCommand;
import com.danko.provider.controller.command.impl.common.ChangeLocalCommand;
import com.danko.provider.controller.command.impl.common.HomeCommand;
import com.danko.provider.controller.command.impl.common.LoginCommand;
import com.danko.provider.controller.command.impl.common.LogoutCommand;
import com.danko.provider.controller.command.impl.user.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ACTIVATION(new ActivationCommand()),
    USERS_LIST_COMMAND(new UserListCommand()),
    PERSONAL_FINANCE_OPERATIONS(new PersonalFinanceOperationsCommand()),
    USER_CHANGE_PASSWORD(new ChangePasswordCommand()),
    USER_CHANGE_TARIFF(new ChangeTariffCommand()),
    USER_TARIFF_LIST(new TariffListCommand()),
    LOCAL(new ChangeLocalCommand()),
    USER_ACTION_LIST(new ActionCommand()),
    PAY(new PayCommand()),
    HOME(new HomeCommand());
    //    Дефлотная комманда добавить.
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
