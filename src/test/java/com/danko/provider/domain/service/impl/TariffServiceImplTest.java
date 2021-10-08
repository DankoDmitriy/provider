package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.service.TariffService;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.USER_TARIFFS_LIST;
import static com.danko.provider.controller.command.RequestAttribute.USER_TARIFF_LIST;

public class TariffServiceImplTest {

    @DataProvider(name = "find_by_tariff_id_data_provider")
    public Object[][] findByTariffIdDataProvider() {
        return new Object[][]{
                {
                        Optional.of(Tariff.builder().setTariffId(1).build()),
                        1
                },
                {
                        Optional.empty(),
                        1
                }
        };
    }

    @Test(dataProvider = "find_by_tariff_id_data_provider")
    public void findByIdTest(Optional<Tariff> optionalTariff, long tariffId) throws Exception {
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        TariffService tariffService = new TariffServiceImpl(tariffDaoMock, transactionManagerMock);
        Mockito.when(tariffDaoMock.findById(tariffId)).thenReturn(optionalTariff);
        tariffService.findById(tariffId);
        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        Mockito.verify(tariffDaoMock, Mockito.times(1)).findById(tariffId);
    }


    @DataProvider(name = "find_all_tariffs_for_customer_data_provider")
    public Object[] findAllTariffForCustomerDataProvider() {
        return new Object[]{
                new ArrayList<>(Arrays.asList(Tariff.builder().setTariffId(1).build())),
                null
        };
    }

    @Test(dataProvider = "find_all_tariffs_for_customer_data_provider")
    public void findAllTariffForCustomerTest(List<Tariff> tariffs) throws Exception {
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        TariffService tariffService = new TariffServiceImpl(tariffDaoMock, transactionManagerMock);

        Mockito.when(tariffDaoMock.findAllByStatus(TariffStatus.ACTIVE)).thenReturn(tariffs);

        tariffService.findAllTariffForCustomer(contentMock);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();

        Mockito.verify(contentMock, Mockito.times(1)).putRequestAttribute(USER_TARIFF_LIST, tariffs);
        Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(USER_TARIFFS_LIST);
    }
}
