package com.danko.provider.controller.command;

import com.danko.provider.controller.Router;
import com.danko.provider.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
