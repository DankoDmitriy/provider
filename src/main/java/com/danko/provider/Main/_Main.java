package com.danko.provider.Main;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.impl.UserActionDaoImpl;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.util.PasswordHasher;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.List;

public class _Main {
    public static void main(String[] args) throws Exception {

//TODO - это для транзакшион менеджера.
//        ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
//        connectionThreadLocal.set(ConnectionPool.getInstance().getConnection());
//        connectionThreadLocal.remove();


//        System.out.println(PasswordHasher.hashString("000001"));
//        System.out.println(PasswordHasher.hashString("000002"));
//        System.out.println(PasswordHasher.hashString("000003"));
//        System.out.println(PasswordHasher.hashString("000004"));
//        System.out.println(PasswordHasher.hashString("000005"));
//        System.out.println(PasswordHasher.hashString("000006"));
//
//        System.out.println(PasswordHasher.hashString("1"));


//        todo - проверка userDao
        ConnectionPool connectionPool = ConnectionPool.getInstance();


//         TODO - делаем транзацию.
//        1. выключить auto-commit у connection.setAutoCommit(false)
//        2. Описываем действия, которые должны быть выполнены обязательно на данном connection. и выполняем их.
//        2.1. Необходимо сделать точку возврата. releaseSavepoint.
//        3. Затем если все ок. мы должны сделать соммит для фиксации изминений в БД. connection.commit();
//        4. Если выволнение было с ошибками. необходимо сделать откат. connection.rollback();
//        5. Выключить auto-commit у connection.setAutoCommit(true)
//        6. Вернуть коннекшен в пул. В нашей ситуации просто его закрыть. т.к. он в оболочке прокси.

//        Connection connection = connectionPool.getConnection();
//        connection.setAutoCommit(false);
////        statement.executeUpdate(SQL);
//        Savepoint savepointOne = connection.setSavepoint("SavepointOne");
//        connection.commit();
//        connection.releaseSavepoint(savepointOne);

//        UserDao userDao = new UserDaoImpl();
//////        TODO - Found ALL USERS
//        List<User> list = userDao.findAll();
//        System.out.println("Found ALL USERS");
//        list.stream().forEach(user -> System.out.println(user));
//
////      TODO - Found USER By ID
//        System.out.println("Found USER By ID");
//        Optional<User> userOptional = userDao.findById(2L);
//        if (!userOptional.isEmpty()) {
//            System.out.println(userOptional.get());
//        }

//      TODO - UPDATE USER

//        System.out.println("UPDATE USER");
//        User user = userOptional.get();
//        System.out.println(user);
//        user.setFirstName(user.getLastName() + "_UPDATE3");
//        userDao.update(user);
//        System.out.println(user);

//      TODO - ADD USER

//        System.out.println("ADD USER");
//        User user = User.builder()
//                .setFirstName("tesasdt_first_name")
//                .setLastName("test_lastsad_name")
//                .setPatronymic("patrsadonymic")
//                .setContractNumber("1233sadas3")
//                .setContractDate(LocalDateTime.now())
//                .setName("test_wsdvasdavs")
//                .setPassword("c4ca4238a0b923820dcc509a6f75849b")
//                .setEmail("teddst_user@site.local")
//                .setActivationCode("1234567898asd76543234567")
//                .setActivationCodeUsed(false)
//                .setBalance(new BigDecimal(0))
//                .setRole(UserRole.USER)
//                .setTariffId(2L)
//                .setTraffic(new BigDecimal(1024))
//                .setStatus(UserStatus.WAIT_ACTIVATE).build();
//        System.out.println(user);
//        long generatedId = userDao.add(user);
//        System.out.println(generatedId);
//        System.out.println(user);


//        todo - tariff dao

        //      TODO - FOUND ALL
//        TariffDao tariffDao = new TariffDaoImpl();
//        List<Tariff> list = tariffDao.findAll();
//        list.forEach(tariffF -> System.out.println(tariffF));
//
//
//        list = tariffDao.findAllByStatus(TariffStatus.ACTIVE);
//        list.forEach(tariffF -> System.out.println(tariffF));
//
//        //      TODO - FOUND BY ID
//        Optional<Tariff> optionalTariff = tariffDao.findById(2L);
//        if (!optionalTariff.isEmpty()) {
//            System.out.println(optionalTariff.get());
//        }
//        TODO-UPDATE
//        Tariff tariffID2 = optionalTariff.get();
//        tariffID2.setDescription(tariffID2.getDescription() + "1");
//        tariffID2.setMaxSpeed(100);
//        tariffID2.setMixSpeed(10);
//        tariffID2.setPrice(tariffID2.getPrice().add(new BigDecimal(15)));
//        tariffID2.setTraffic(new BigDecimal(10240));
//        tariffID2.setStatus(TariffStatus.ACTIVE);
//        tariffID2.setPeriod(PeriodicityWriteOff.MONTH);
//        tariffDao.update(tariffID2);

//      TODO - ADD
//
//        Tariff tariff = Tariff.builder().setDescription("asdasdad")
//                .setMaxSpeed(10)
//                .setMixSpeed(5)
//                .setTraffic(new BigDecimal(100))
//                .setPrice(new BigDecimal(35.6))
//                .setStatus(TariffStatus.ACTIVE)
//                .setPeriod(PeriodicityWriteOff.MONTH).build();
//
//        tariffDao.add(tariff);
//        list = tariffDao.findAll();
//        list.forEach(tariffF -> System.out.println(tariffF));


//        TODO - TRANSACTIONS

//        AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();

//        TODO - FIND ALL
//        List<AccountTransaction> list = accountTransactionDao.findAll();
//        list.forEach(accountTransaction -> System.out.println(accountTransaction));
//        System.out.println("--------------------------------------------");
//
////        TODO FIND BY ID
//        Optional<AccountTransaction> accountTransactionOptional = accountTransactionDao.findById(2l);
//        System.out.println(accountTransactionOptional.get());

//        TODO ADD
//        AccountTransaction accountTransaction = AccountTransaction.builder()
//                .setUserId(3)
//                .setDate(LocalDateTime.now())
//                .setType(TransactionType.ADJUSTMENT)
//                .setSum(new BigDecimal(1000)).build();
//
//        long newId = accountTransactionDao.add(accountTransaction);
//        Optional<AccountTransaction> newAccountTransactionOptional = accountTransactionDao.findById(newId);
//        System.out.println(newAccountTransactionOptional.get());

//        TODO UPDATE
//        Optional<AccountTransaction> accountTransactionOptional = accountTransactionDao.findById(2l);
//        System.out.println(accountTransactionOptional.get());
//        AccountTransaction transaction = accountTransactionOptional.get();
//        transaction.setSum(new BigDecimal(123));
//        accountTransactionDao.update(transaction);
//        accountTransactionOptional = accountTransactionDao.findById(2l);
//        System.out.println(accountTransactionOptional.get());


//        TODO - USER ACTIONS
//        UserActionDao userActionDao = new UserActionDaoImpl();
//        List<UserAction> list = userActionDao.findAll();
//        list.forEach(userAction -> System.out.println(userAction));
//
//        list = userActionDao.findAllByUserId(3l);
//        list.forEach(userAction -> System.out.println(userAction));

//        UserAction userAction2 = UserAction.builder().
//                setActionType(UserAction.ActionType.CHANGE_TARIFF)
//                .setDateTime(LocalDateTime.now()).buid();
//        userActionDao.add(userAction2, 3l, 2l);
//
//        System.out.println("***********************");
//        list = userActionDao.findAllByUserId(3l);
//        list.forEach(userAction -> System.out.println(userAction));


        connectionPool.destroyConnectionPool();


//        System.out.println(UserUtil.hashString("1"));
    }
}
