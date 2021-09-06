package com.danko.provider.Main.TMP;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSender {
    private static Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_FILE_PATH = "properties/mailSender.properties";
    private static final String USER = "mail.user.user";
    private static final String PASSWORD = "mail.user.password";
    private final Properties emailProperties = new Properties();
    private String sendToEmail;
    private String mailSubject;
    private String mailText;

    public EmailSender(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

    public boolean sendMail() {
        boolean result = true;
        if (readEmailProperties(emailProperties)) {
            try {
                if (readEmailProperties(emailProperties)) {
                    String userLogin = emailProperties.getProperty(USER);
                    String userPassword = emailProperties.getProperty(PASSWORD);

                    Session session = createSession(emailProperties, userLogin, userPassword);

                    Message message = new MimeMessage(session);
                    message.setSubject(mailSubject);
                    message.setContent(mailText, "text/html");
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));

                    Transport.send(message);
                    logger.log(Level.INFO,"Email Sent successfully....");
//                    FIXME- удалить. SOUT.
                    System.out.println("Email Sent successfully....");
                } else {
                    result = false;
                }
            } catch (MessagingException e) {
                logger.log(Level.ERROR, "Email can`t send: {}", e);
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    private boolean readEmailProperties(Properties properties) {
        boolean result = true;
        try {
            InputStream inputStream = EmailSender.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
            if (inputStream != null) {
                emailProperties.load(inputStream);
            } else {
                logger.log(Level.ERROR, "Properties file for Email sender is not correct. ");
                result = false;
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error with properties file." + e);
        }
        return result;
    }

    private Session createSession(Properties properties, String user, String password) {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        return session;
    }
}
