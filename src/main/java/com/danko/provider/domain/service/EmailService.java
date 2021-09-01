package com.danko.provider.domain.service;

import com.danko.provider.exception.ServiceException;

public interface EmailService {
   boolean sendActivateMail(String sendToEmail, String domain, String newActivateCode) throws ServiceException;
}
