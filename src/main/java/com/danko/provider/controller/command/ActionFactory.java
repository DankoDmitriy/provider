package com.danko.provider.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static Logger logger = LogManager.getLogger();

    public static Command getCommand(String commandStr) {
//        TODO проверка на ULL и Empty (дефолт) - illegalargumentException если нет такого отправить на стартовую страницу. 510 страница учебника
        if (commandStr == null || commandStr.isEmpty()) {
            throw new IllegalArgumentException("Command is empty");
        }
        CommandType commandType = CommandType.valueOf(commandStr.toUpperCase());
        return commandType.getCommand();

    }
}
