package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.*;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.*;


public class AdminUserEditCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]*$";
    private TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String firstName = request.getParameter(USER_EDIT_FIRST_NAME);
        String lastName = request.getParameter(USER_EDIT_LAST_NAME);
        String patronymic = request.getParameter(USER_EDIT_PATRONYMIC);
        String email = request.getParameter(USER_EDIT_E_MAIL);
        String userIdStr = request.getParameter(USER_EDIT_ID);

        try {
            if (userIdStr.matches(ID_CHECK_REGEX) && firstName != null &&
                    lastName != null && patronymic != null && email != null) {
//        Тут обработка на редактирование пользователя
                User userOrigin = stringToObjectUser(request.getParameter(USER_EDIT_ORIGIN));
                boolean result = userService.updateUserPersonalData(firstName, lastName, patronymic, email, userIdStr, userOrigin);
//                FIXME - сделать пользовательскую страницу для админа. и туда уже отправлять.
                List<User> users = Arrays.asList(userOrigin);
                request.setAttribute(ADMIN_USERS_LIST, users);
                request.setAttribute(ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE, result);
                router.setPageUrl(ADMIN_USERS_LIST_PAGE);
            } else {
                if (userIdStr.matches(ID_CHECK_REGEX)) {
//                FIXME Тут выбираем пользователя для редакирования и необходимые данные для формы
                    Optional<User> optionalUser = userService.findById(Long.parseLong(userIdStr));
                    if (!optionalUser.isEmpty()) {
                        User user = optionalUser.get();
                        user.setTariff(tariffService.findById(user.getTariffId()).get());
                        request.setAttribute(ADMIN_USER_EDIT_ORIGINAL, objectUserToString(user));
                        request.setAttribute(ADMIN_USER_EDIT, user);
                        router.setPageUrl(ADMIN_USER_EDIT_PAGE);
                    } else {
//                   FIXME Тут если Пользователя нет. отправляем на страницу пользователей.
                        router.setRouteType(Router.RouteType.REDIRECT);
                        router.setPageUrl(request.getContextPath() + ADMIN_USERS_LIST_PAGE_REDIRECT);
                    }
                } else {
//                FIXME    Некорректный ID Пользователя на страницу с пользователями.
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath() + ADMIN_USERS_LIST_PAGE_REDIRECT);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

    private String objectUserToString(User user) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }

    private User stringToObjectUser(String userStr) {
        try {
            byte[] objToBytes = Base64.getDecoder().decode(userStr);
            ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }
}
