package com.danko.provider.controller.command;

import com.danko.provider.controller.Router;
import com.danko.provider.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface of servlet command
 */
public interface Command {
    /**
     * Method is called by servlet when processing request
     *
     * @param request servlet request object
     * @return result of command execution. Return type object - Router. Router object containing page path and routing type
     */
    Router execute(HttpServletRequest request) throws CommandException;
}
