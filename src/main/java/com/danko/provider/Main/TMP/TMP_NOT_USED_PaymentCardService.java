package com.danko.provider.Main.TMP;

public class TMP_NOT_USED_PaymentCardService {

//    PaymentCardService
    //    Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws ServiceException;
    //    //    FIXME - Наверное вообще не нужен будет этот метод.
//    @Override
//    public Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws ServiceException {
//        try {
//            //        TODO - Добавить валидацию номера и пина карты.
//            transactionManager.startTransaction();
//            String cardNumberHash = StringHasher.hashString(cardNumber);
//            String cardPinHash = StringHasher.hashString(cardPin);
//            return paymentCardDao.findByCardNumberAndPin(cardNumberHash, cardPinHash);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        } finally {
//            try {
//                transactionManager.endTransaction();
//            } catch (DaoException e) {
//                throw new ServiceException(e);
//            }
//        }
//    }
}
