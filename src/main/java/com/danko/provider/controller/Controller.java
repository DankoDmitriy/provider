package com.danko.provider.controller;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.controller.command.ActionFactory;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.service.ServiceProvider;
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

@WebServlet(value = "/controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
        ServiceProvider.getInstance();
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
//        Забираем параметр действия.
        System.out.println("************CONTROLLER START***********");
        String commandString = req.getParameter(COMMAND);
//        FIXME-DEL LOG
        logger.log(Level.DEBUG, "COMMAND = " + commandString);
        System.out.println("COMMAND = " + commandString);
        System.out.println("**************");

        Command command = ActionFactory.getCommand(commandString);
        try {
            Router router = command.execute(req);
            switch (router.getRouteType()) {
                case FORWARD -> req.getRequestDispatcher(router.getPageUrl()).forward(req, resp);
                case REDIRECT -> resp.sendRedirect(router.getPageUrl());
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, "Internal error: {}", e);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnectionPool();
    }
}
