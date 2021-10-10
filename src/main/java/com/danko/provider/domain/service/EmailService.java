package com.danko.provider.domain.service;

import com.danko.provider.exception.ServiceException;

/**
 * The interface Email service.
 */
public interface EmailService {
    /**
     * Send activate mail boolean.
     *
     * @param sendToEmail     the send to email
     * @param domain          the domain
     * @param newActivateCode the new activate code
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean sendActivateMail(String sendToEmail, String domain, String newActivateCode) throws ServiceException;
}
