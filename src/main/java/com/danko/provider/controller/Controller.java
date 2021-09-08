package com.danko.provider.controller;

import com.danko.provider.controller.command.ActionFactory;
import com.danko.provider.controller.command.Command;
import com.danko.provider.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.danko.provider.controller.command.ParamName.COMMAND;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandString = req.getParameter(COMMAND);
        Command command = ActionFactory.getCommand(commandString);
        try {
            Router router = command.execute(req);
            switch (router.getRouteType()) {
                case FORWARD -> req.getRequestDispatcher(router.getPageUrl()).forward(req, resp);
                case REDIRECT -> resp.sendRedirect(router.getPageUrl());
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, "Internal error: {}", e);
//            FIXME -  resp.sendRedirect(ERROR_500_PAGE);
        }
    }
    @Override
    public void destroy() {
    }
}
