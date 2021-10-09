package com.danko.provider.controller.listener;

import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static com.danko.provider.controller.command.SessionAttribute.DEFAULT_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        if (user == null) {
            user = User.builder().setRole(UserRole.GUEST).build();
            session.setAttribute(SESSION_USER, user);
            session.setAttribute(SESSION_LOCAL, DEFAULT_LOCAL);
        }
    }
}
