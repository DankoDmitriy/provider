package com.danko.provider.controller.command;

import com.danko.provider.Main.TMP.AdminEmployeeListCommand;
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
    USER_SEARCH(new AdminUserSearch()),
    USER_ADD(new AdminUserAddCommand()),
    USER_EDIT(new AdminUserEditCommand()),
    USER_BAN(new AdminUserBanCommand()),
    CHANGE_ROLE(new AdminUserRoleChangeCommand()),
    USER_PROFILE(new AdminUserProfileCommand()),
    USER_FINANCE(new AdminUserFinanceCommand()),
    USER_ACTION(new AdminUserActionsCommand()),
    TARIFF_LIST(new AdminTariffsListCommand()),
    TARIFF_ADD(new AdminTariffAddCommand()),
    TARIFF_EDIT(new AdminTariffEditCommand()),
    //    EMPLOYEE_LIST(new AdminEmployeeListCommand()),
    CARD_ADD(new AdminPaymentCardAddCommand()),
    CARD_SEARCH(new AdminPaymentCardSearchCommand());
    //    Дефлотная комманда добавить.
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
