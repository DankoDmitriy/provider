package com.danko.provider.controller.command;

import com.danko.provider.controller.command.impl.admin.AdminTariffsListCommand;
import com.danko.provider.controller.command.impl.admin.AdminUserAddCommand;
import com.danko.provider.controller.command.impl.admin.AdminUsersListCommand;
import com.danko.provider.controller.command.impl.common.ChangeLocalCommand;
import com.danko.provider.controller.command.impl.common.HomeCommand;
import com.danko.provider.controller.command.impl.common.LoginCommand;
import com.danko.provider.controller.command.impl.common.LogoutCommand;
import com.danko.provider.controller.command.impl.user.*;

public enum CommandType {
    //    Common
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    HOME(new HomeCommand()),
    LOCAL(new ChangeLocalCommand()),
    ACTIVATION(new ActivationCommand()),

    //User
    USER_CHANGE_PASSWORD(new ChangePasswordCommand()),
    USER_CHANGE_TARIFF(new ChangeTariffCommand()),
    USER_TARIFF_LIST(new TariffListCommand()),
    USER_ACTION_LIST(new ActionCommand()),
    PERSONAL_FINANCE_OPERATIONS(new PersonalFinanceOperationsCommand()),
    PAY(new PayCommand()),

    //    Admin
    USERS_LIST(new AdminUsersListCommand()),
    USER_ADD(new AdminUserAddCommand()),
    TARIFF_LIST(new AdminTariffsListCommand());

    //    Дефлотная комманда добавить.
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
