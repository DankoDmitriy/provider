package com.danko.provider.Main;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.impl.UserActionDaoImpl;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.util.PasswordHasher;

import java.sql.Connection;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateForm = "2021-09-09";
//        System.out.println(Timestamp.valueOf("2021-09-09 00:00:00"));
        LocalDate localDate = LocalDate.parse(dateForm,dateTimeFormatter);
        System.out.println(localDate);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        System.out.println(localDateTime);


//        todo - проверка userDao
//        ConnectionPool connectionPool = ConnectionPool.getInstance();


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

//        connectionPool.destroyConnectionPool();


//        System.out.println(UserUtil.hashString("1"));
    }
}
