package com.danko.provider.Main;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.impl.UserActionDaoImpl;
import com.danko.provider.domain.entity.*;
import com.danko.provider.util.PasswordHasher;
import org.apache.logging.log4j.util.Supplier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class _Main {
    public static void main(String[] args) throws Exception {

//TODO - это для транзакшион менеджера.
//        ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
//        connectionThreadLocal.set(ConnectionPool.getInstance().getConnection());
//        connectionThreadLocal.remove();


//        TODO - конвертация объектов.
        User user = User.builder().setRole(UserRole.USER)
                .setName("asdasd")
                .setFirstName("asdasdasd")
                .setLastName("asdasdas")
                .setEmail("asdasdads@gmail.com")
                .setTariffId(1l)
                .setBalance(new BigDecimal("10.0"))
                .setUserId(1l)
                .setTraffic(new BigDecimal("10240"))
                .setContractDate(LocalDateTime.now())
                .setContractNumber("1111")
                .setPatronymic("sadasd")
                .setStatus(UserStatus.WAIT_ACTIVATE).build();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
        String result = new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));

        String base64String = result;
        byte[] objToBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        User userReturn = (User) ois.readObject();

        System.out.println(user.equals(userReturn));
        System.out.println(user);
        System.out.println(userReturn);

//        TODO BigDecimal

//        BigDecimal bigDecimal = new BigDecimal("10.09");
//        System.out.println(bigDecimal);


//        TODO ENUM
//
//        Stream<TariffStatus> strinam = Arrays.stream(TariffStatus.values());
//        strinam.forEach(status -> System.out.println(status));
//
//        List<TariffStatus> tariffStatuses = Arrays.asList(TariffStatus.values());
//        tariffStatuses.forEach(status -> System.out.println(status.name()));


//        TODO String format

//        String result = String.format("%06d", 516);
//        System.out.println(result);
//        int year = LocalDateTime.now().getYear();
//        String contractNumberAndUserName = new StringBuilder().append(year).append(String.format("%011d", 10)).toString();
//        System.out.println(contractNumberAndUserName);


//        System.out.println(PasswordHasher.hashString("000001"));
//        System.out.println(PasswordHasher.hashString("000002"));
//        System.out.println(PasswordHasher.hashString("000003"));
//        System.out.println(PasswordHasher.hashString("000004"));
//        System.out.println(PasswordHasher.hashString("000005"));
//        System.out.println(PasswordHasher.hashString("000006"));
//
//        System.out.println(PasswordHasher.hashString("1"));

//        TODO - localdatetome
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String dateForm = "2021-09-09";
////        System.out.println(Timestamp.valueOf("2021-09-09 00:00:00"));
//        LocalDate localDate = LocalDate.parse(dateForm,dateTimeFormatter);
//        System.out.println(localDate);
//        LocalDateTime localDateTime = localDate.atStartOfDay();
//        System.out.println(localDateTime);


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
