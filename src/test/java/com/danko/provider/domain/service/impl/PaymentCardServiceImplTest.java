package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.PaymentCardSerialDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.util.StringHasher;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.ADMIN_PAYMENTS_CARD_ADD_PAGE;
import static com.danko.provider.controller.command.ParamName.FIND_CARD_NUMBER_CARD;
import static com.danko.provider.controller.command.ParamName.PAYMENT_CARD_ADD_AMOUNT;
import static com.danko.provider.controller.command.ParamName.PAYMENT_CARD_ADD_COUNT;
import static com.danko.provider.controller.command.ParamName.PAYMENT_CARD_ADD_DATE_EXPIRED;
import static com.danko.provider.controller.command.ParamName.PAYMENT_CARD_ADD_SERIES;

public class PaymentCardServiceImplTest {

    @DataProvider(name = "find_by_number_data_provider")
    public Object[][] findByNumberDataProvider() {
        return new Object[][]{
                {
                        new String[]{"AAA0000001"},
                        Optional.of(PaymentCard.builder().setCardId(1).setCardStatus(PaymentCard.CardStatus.USED).build()),
                        Optional.of(User.builder().setUserId(1).build()),
                },
                {
                        new String[]{"AAA0000001"},
                        Optional.of(PaymentCard.builder().setCardId(1).setCardStatus(PaymentCard.CardStatus.NOT_USED).build()),
                        Optional.of(User.builder().setUserId(1).build()),
                }
        };
    }

    @Test(dataProvider = "find_by_number_data_provider")
    public void findByNumberTest(
            String[] cardNumber,
            Optional<PaymentCard> optionPaymentCard,
            Optional<User> optionalUser
    ) throws Exception {
        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        PaymentCardSerialDao paymentCardSerialDaoMock = Mockito.mock(PaymentCardSerialDao.class);
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        PaymentCardService paymentCardService = new PaymentCardServiceImpl(paymentCardDaoMock, paymentCardSerialDaoMock, userDaoMock, transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(FIND_CARD_NUMBER_CARD)).thenReturn(cardNumber);
        Mockito.when(paymentCardDaoMock.findByCardNumber(StringHasher.hashString(cardNumber[0]))).thenReturn(optionPaymentCard);
        Mockito.when(paymentCardDaoMock.getUserIdActivatedCard(optionPaymentCard.get().getCardId())).thenReturn(optionalUser.get().getUserId());
        Mockito.when(userDaoMock.findById(optionalUser.get().getUserId())).thenReturn(optionalUser);

        paymentCardService.findByNumber(contentMock);
        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        Mockito.verify(paymentCardDaoMock, Mockito.times(1)).findByCardNumber(StringHasher.hashString(cardNumber[0]));
        switch (optionPaymentCard.get().getCardStatus()) {
            case USED: {
                Mockito.verify(userDaoMock, Mockito.times(1)).findById(optionalUser.get().getUserId());
                Mockito.verify(contentMock, Mockito.times(3)).putRequestAttribute(Mockito.anyString(), Mockito.anyObject());
                break;
            }
            case NOT_USED: {
                Mockito.verify(contentMock, Mockito.times(2)).putRequestAttribute(Mockito.anyString(), Mockito.anyObject());
                break;
            }
        }
    }

    @DataProvider(name = "add_card_free_and_busy_series_data_provider")
    public Object[][] addCardsDataProvider() {
        return new Object[][]{
                {
                        new String[]{"AAA"},
                        new String[]{"100"},
                        new String[]{"10"},
                        new String[]{"2023-01-01"},
                        Optional.of(PaymentCardSerial.builder().setSerial("AAA").setSerialId(1l))
                },
                {
                        new String[]{"AAA"},
                        new String[]{"100"},
                        new String[]{"10"},
                        new String[]{"2023-01-01"},
                        Optional.empty()
                }
        };
    }

    @Test(dataProvider = "add_card_free_and_busy_series_data_provider")
    public void addCardsFreeAndBusySeriesPositiveTest(
            String[] series,
            String[] amount,
            String[] count,
            String[] dateExpiredStr,
            Optional<PaymentCardSerial> optionalPaymentCardSerial
    ) throws Exception {

        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        PaymentCardSerialDao paymentCardSerialDaoMock = Mockito.mock(PaymentCardSerialDao.class);
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        PaymentCardService paymentCardService = new PaymentCardServiceImpl(paymentCardDaoMock, paymentCardSerialDaoMock, userDaoMock, transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_SERIES)).thenReturn(series);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_AMOUNT)).thenReturn(amount);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_COUNT)).thenReturn(count);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_DATE_EXPIRED)).thenReturn(dateExpiredStr);
        Mockito.when(paymentCardSerialDaoMock.findBySeries(series[0])).thenReturn(optionalPaymentCardSerial);

        paymentCardService.addCards(contentMock);
        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        if (optionalPaymentCardSerial.isEmpty()) {
            Mockito.verify(transactionManagerMock, Mockito.times(1)).commit();
        }
    }


    @DataProvider(name = "add_card_incorrect_parameters_data_provider")
    public Object[][] addCardsIncorrectInputParametersDataProvider() {
        return new Object[][]{
                {
                        new String[]{"AAAAAAAA"},
                        new String[]{"100"},
                        new String[]{"10"},
                        new String[]{"2023-01-01"},
                        Optional.empty()
                },
                {
                        new String[]{"AAA"},
                        new String[]{"100a"},
                        new String[]{"10"},
                        new String[]{"2023-01-01"},
                        Optional.empty()
                },
                {
                        new String[]{"AAA"},
                        new String[]{"100"},
                        new String[]{"10a"},
                        new String[]{"2023-01-01"},
                        Optional.empty()
                },
                {
                        new String[]{"AAA"},
                        new String[]{"100"},
                        new String[]{"10"},
                        new String[]{"2020-01-01"},
                        Optional.empty()
                }
        };
    }

    @Test(dataProvider = "add_card_incorrect_parameters_data_provider")
    public void addCardsIncorrectInputParametersNegativeTest(
            String[] series,
            String[] amount,
            String[] count,
            String[] dateExpiredStr,
            Optional<PaymentCardSerial> optionalPaymentCardSerial
    ) throws Exception {

        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        PaymentCardSerialDao paymentCardSerialDaoMock = Mockito.mock(PaymentCardSerialDao.class);
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        PaymentCardService paymentCardService = new PaymentCardServiceImpl(paymentCardDaoMock, paymentCardSerialDaoMock, userDaoMock, transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_SERIES)).thenReturn(series);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_AMOUNT)).thenReturn(amount);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_COUNT)).thenReturn(count);
        Mockito.when(contentMock.getRequestParameter(PAYMENT_CARD_ADD_DATE_EXPIRED)).thenReturn(dateExpiredStr);
        Mockito.when(paymentCardSerialDaoMock.findBySeries(series[0])).thenReturn(optionalPaymentCardSerial);

        paymentCardService.addCards(contentMock);
        Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_PAYMENTS_CARD_ADD_PAGE);

    }
}
