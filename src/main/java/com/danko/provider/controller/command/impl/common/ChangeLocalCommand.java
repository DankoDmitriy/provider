package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.ParamName.USER_CHANGE_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.DEFAULT_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.ENGLISH_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_LOCAL;

public class ChangeLocalCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String newLocal = request.getParameter(USER_CHANGE_LOCAL);
        if (newLocal.equals(ENGLISH_LOCAL) || newLocal.equals(DEFAULT_LOCAL)) {
            session.setAttribute(SESSION_LOCAL, newLocal);
            logger.log(Level.DEBUG, "Change local...");
        }
        router.setPageUrl(HOME_PAGE);
        return router;
    }
}
