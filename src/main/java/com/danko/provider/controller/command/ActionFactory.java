package com.danko.provider.controller.command;

import com.danko.provider.controller.command.impl.common.DefaultCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static Logger logger = LogManager.getLogger();

    public static Command getCommand(String commandStr) {
        Command command = new DefaultCommand();
        if (commandStr == null || commandStr.isEmpty()) {
            return command;
        }
        try {
            CommandType commandType = CommandType.valueOf(commandStr.toUpperCase());
            return commandType.getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            return command;
        }
    }
}
