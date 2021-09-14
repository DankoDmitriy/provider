package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_EDIT_PAGE;

public class AdminUserEditCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(ADMIN_USER_EDIT_PAGE);
        return router;
    }
}
