package com.danko.provider.controller.listener;

import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static com.danko.provider.controller.command.SessionAttribute.USER;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
//        FIXME - тут добавить локализацию.
        HttpSession session = sessionEvent.getSession();
//        FIXME -DEL IT
        System.out.println("********  HttpSessionListener ********");
        User user = (User) session.getAttribute(USER);
        if (user == null) {
            user = User.builder().setRole(UserRole.GUEST).build();
            session.setAttribute(USER, user);
        }
    }
}
