package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.dao.impl.UserDaoImpl;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.domain.service.EmailService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.util.EmailSender;
import com.danko.provider.util.UrlUtil;
import com.danko.provider.util.UserUtil;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();
    private UserDao userDao = new UserDaoImpl();
//    private TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> list;
        try {
            list = userDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new ServiceException("Could not find all users in database.", e);
        }
        return list;
    }

    @Override
    public Optional<User> findByNameAndPassword(String name, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            //TODO - ADD CHECK FOR VALIDATION LOGIN AND PASSWORD
            String passwordHash = UserUtil.hashString(password);
            optionalUser = userDao.findByNameAndPassword(name, passwordHash);
            return optionalUser;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find user by login and password: {}", e);
            throw new ServiceException("can't find user by login and password.", e);
        }
    }

    @Override
    public boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl) throws ServiceException {
        try {
            boolean result = true;
            if (password != null && InputDataValidator.newUserPasswordValid(password)) {
                String passwordHash = UserUtil.hashString(password);
                String newActivateCode = UserUtil.generationOfActivationCode();
                result = userDao.updatePassword(userId, passwordHash, newActivateCode, UserStatus.WAIT_ACTIVATE);

                EmailService emailService = ServiceProvider.getInstance().getEmailService();
                String domain = UrlUtil.requestUrlToDomain(requestUrl) + contextPath;
                emailService.sendActivateMail(email, domain, newActivateCode);
            } else {
                throw new ServiceException("Input password is not correct.");
            }
            return result;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Password has not been updated: {}", e);
            throw new ServiceException("Password has not been updated.", e);
        }
    }

    @Override
    public boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException {
        try {
            if (verificationOfActivationCode(activateCode)) {
                return userDao.updateActivationCodeStatus(activateCode, userStatus);
            }
            return false;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code status has not been updated: {}", e);
            throw new ServiceException("Activation code status has not been updated.", e);
        }
    }

    @Override
    public boolean verificationOfActivationCode(String activateCode) throws ServiceException {
        try {
            return userDao.verificationOfActivationCode(activateCode);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code has not been verification: {}", e);
            throw new ServiceException("Activation code has not been verification.", e);
        }
    }

    @Override
    public BigDecimal updateTariffPlan(long userId, long tariffId) throws ServiceException {
        BigDecimal newUserTraffic = null;
        TariffService tariffService = ServiceProvider.getInstance().getTariffService();
        try {
            Optional<Tariff> tariffOptional = tariffService.findById(tariffId);
            if (!tariffOptional.isEmpty()) {
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    Tariff tariff = tariffOptional.get();
                    User user = optionalUser.get();
                    if (tariff.getStatus().equals(TariffStatus.ACTIVE) & user.getStatus().equals(UserStatus.ACTIVE)) {
                        newUserTraffic = tariff.getTraffic().add(user.getTraffic());
                        userDao.updateTariffAndTrafficValue(userId, tariffId, newUserTraffic);
                    }
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Tariff has not been updated: {}", e);
            throw new ServiceException("Tariff has not been updated:.", e);
        }
        return newUserTraffic;
    }
}
