package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.service.EmailService;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.danko.provider.controller.command.PageUrl.ACTIVATE_PAGE;

public class EmailServiceImpl implements EmailService {
    private static Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_FILE_PATH = "properties/mailSender.properties";
    private static final String USER = "mail.user.user";
    private static final String PASSWORD = "mail.user.password";
    private final Properties emailProperties = new Properties();
    private String user;
    private String password;

    public EmailServiceImpl() {
        try {
            InputStream inputStream = EmailServiceImpl.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
            emailProperties.load(inputStream);
            user = emailProperties.getProperty(USER);
            password = emailProperties.getProperty(PASSWORD);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error with properties file. {}", e);
        }
    }

    public boolean sendActivateMail(String sendToEmail, String domain, String newActivateCode) throws ServiceException {
        boolean result = true;
        String mailSubject = "You are changing password";
        String mailText = createActivationUrl(domain, newActivateCode);
        Message message = createMessage(sendToEmail, mailSubject, mailText);

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Email can`t send: {}", e);
            throw new ServiceException("Email can`t send:", e);
        }
        logger.log(Level.INFO, "Email Sent successfully....");
        System.out.println("Email Sent successfully....");
        return result;
    }

    private String createActivationUrl(String domain, String newActivateCode) {
        StringBuilder mailText = new StringBuilder("You are changing password. ");
        mailText.append("To continue working, activate your account. ");
        mailText.append("To activate, follow the link: ");
        mailText.append(domain);
        mailText.append(ACTIVATE_PAGE);
        mailText.append(newActivateCode);
        mailText.append(" If you haven't changed your password, just ignore this email. Thanks.");
        return mailText.toString();

    }

    private Message createMessage(String sendToEmail, String mailSubject, String mailText) {
        Session session = createSession();
        Message message = new MimeMessage(session);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Problem with init Email Message", e);
            return null;
        }
        return message;
    }

    private Session createSession() {
        Session session = Session.getDefaultInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        return session;
    }
}
