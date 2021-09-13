package com.danko.provider.controller.command;

import com.danko.provider.controller.command.impl.admin.*;
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
    TARIFF_LIST(new AdminTariffsListCommand()),
    TARIFF_ADD(new AdminTariffAddCommand()),
    EMPLOYEE_LIST(new AdminEmployeeListCommand()),
    CARD_ADD(new AdminPaymentCardAddCommand());

    //    Дефлотная комманда добавить.
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
