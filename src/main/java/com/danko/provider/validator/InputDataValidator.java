package com.danko.provider.validator;

import com.danko.provider.domain.entity.PeriodicityWriteOff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputDataValidator {
    private static InputDataValidator instance;
    private static final AtomicBoolean isInputDataValidator = new AtomicBoolean(false);
    private static final String LOGIN_NAME_REGEX = "^[0-9]{15}$";
    private static final String PASSWORD_REGEX = "^[0-9a-zA-Z]{8,20}$";
    private static final String FIRST_NAME_REGEX = "^[a-zA-ZА-Яа-я]{2,55}$";
    private static final String LAST_NAME_REGEX = "^[a-zA-ZА-Яа-я]{2,55}$";
    private static final String PATRONYMIC_REGEX = "^[a-zA-ZА-Яа-я]{2,55}$";
    private static final String EMAIL_REGEX = "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String EMAIL_SYMBOL_NUMBER = "^.{8,60}$";
    private static final String CONTRACT_NUMBER_SEARCH_REGEX = "^[0-9]{2,15}$";
    private static final String EMAIL_SEARCH_REGEX = "^[A-Za-z0-9_-]{5,15}$";

    private static final String TARIFF_DESCRIPTION_REGEX = "^[0-9a-zA-ZА-Яа-я -]{2,100}";
    private static final String TARIFF_PRICE_REGEX = "^[0-9]+[.][0-9]{1}[1-9]{1}$";
    private static final String TARIFF_TRAFFIC_REGEX = "^[1-9]{1}[0-9]{0,7}$";
    private static final String TARIFF_SPEED_REGEX = "^[1-9]{1}[0-9]{0,4}$";

    private static final String PAYMENTS_CARD_SERIES_REGEX = "^[A-Z]{3,5}$";
    private static final String PAYMENTS_CARD_AMOUNT_REGEX = "^[1-9]{1}[0-9]{0,4}$";
    private static final String PAYMENTS_CARD_COUNT_REGEX = "^[1-9]{1}[0-9]{0,4}$";
    private static final String PAYMENT_CARD_NUMBER_REGEX = "^[A-Z]{3,5}[0-9]{7}$";

    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]{0,16}$";

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private static final long DAYS_CARD_ACTIVE = 364;


    private InputDataValidator() {
    }

    public static InputDataValidator getInstance() {
        while (instance == null) {
            if (isInputDataValidator.compareAndSet(false, true)) {
                instance = new InputDataValidator();
            }
        }
        return instance;
    }

    public boolean singInParametersValid(String login, String password) {
        return login != null && password != null && login.matches(LOGIN_NAME_REGEX) && password.matches(PASSWORD_REGEX);
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public boolean isFirstNameValid(String firstName) {
        return firstName != null && firstName.matches(FIRST_NAME_REGEX);
    }

    public boolean isLastNameValid(String lastName) {
        return lastName != null && lastName.matches(LAST_NAME_REGEX);
    }

    public boolean isPatronymicValid(String patronymic) {
        return patronymic != null && patronymic.matches(PATRONYMIC_REGEX);
    }

    public boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX) && email.matches(EMAIL_SYMBOL_NUMBER);
    }

    public boolean isTariffDescriptionValid(String tariffDescription) {
        return tariffDescription != null && tariffDescription.matches(TARIFF_DESCRIPTION_REGEX);
    }

    public boolean isTariffPriceValid(String tariffPrice) {
        return tariffPrice != null && tariffPrice.matches(TARIFF_PRICE_REGEX);
    }

    public boolean isTariffTrafficValid(String tariffTraffic) {
        return tariffTraffic != null && tariffTraffic.matches(TARIFF_TRAFFIC_REGEX);
    }

    public boolean isTariffSpeedValid(String tariffSpeed) {
        return tariffSpeed != null && tariffSpeed.matches(TARIFF_SPEED_REGEX);
    }

    public boolean isTariffStatusValid(String tariffStatus) {
        boolean result = false;
        List<TariffStatus> tariffStatuses = Arrays.asList(TariffStatus.values());
        for (TariffStatus status : tariffStatuses) {
            if (status.name().equals(tariffStatus)) {
                result = true;
            }
        }
        return result;
    }

    public boolean isPeriodicityWriteOffValid(String tariffPeriodWriteOf) {
        boolean result = false;
        List<PeriodicityWriteOff> periodicityWriteOffs = Arrays.asList(PeriodicityWriteOff.values());
        for (PeriodicityWriteOff writeOff : periodicityWriteOffs) {
            if (writeOff.name().equals(tariffPeriodWriteOf)) {
                result = true;
            }
        }
        return result;
    }

    public boolean isPaymentCardSeriesValid(String series) {
        return series != null && series.matches(PAYMENTS_CARD_SERIES_REGEX);
    }

    public boolean isPaymentCardAmountValid(String amount) {
        return amount != null && amount.matches(PAYMENTS_CARD_AMOUNT_REGEX);
    }

    public boolean isPaymentCardCountValid(String count) {
        return count != null && count.matches(PAYMENTS_CARD_COUNT_REGEX);
    }

    public boolean isPaymentCardNumberValid(String number) {
        return number != null && number.matches(PAYMENT_CARD_NUMBER_REGEX);
    }

    public boolean isPaymentCardDateExpiredValid(String dateExpiredStr) {
        LocalDate localDate = LocalDate.parse(dateExpiredStr, dateTimeFormatter);
        LocalDateTime localDateTimeCardExpired = localDate.atStartOfDay();
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(DAYS_CARD_ACTIVE);
        return localDateTimeCardExpired.isAfter(expiredDate);
    }

    public boolean isIdValid(String idStr) {
        return idStr != null && idStr.matches(ID_CHECK_REGEX);
    }

    public boolean isUserRoleValid(String userRole) {
        for (UserRole role : UserRole.values()) {
            if (role.name().matches(userRole)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContractNumberForSearchValid(String contractSearch) {
        return contractSearch != null && contractSearch.matches(CONTRACT_NUMBER_SEARCH_REGEX);
    }

    public boolean isEmailForSearchValid(String email) {
        return email != null && email.matches(EMAIL_SEARCH_REGEX);
    }
}

