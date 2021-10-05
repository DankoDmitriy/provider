package com.danko.provider.validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class InputDataValidatorTest {
    private InputDataValidator validator;

    @BeforeClass
    public void createValidator() {
        validator = InputDataValidator.getInstance();
    }

    @DataProvider(name = "sing_in_parameters_data_provider")
    public Object[][] singInParametersDataProvider() {
        return new Object[][]{
                {"11111", "dasdrfq3qsda", false},
                {"1234567891234567", "dasdrfq3qsda", false},
                {"123456789123456", "dasdrfq3qsda", true}
        };
    }


    @DataProvider(name = "password_data_provider")
    public Object[][] passwordDataProvider() {
        return new Object[][]{
                {"1234567", false},
                {"123456789011111111111", false},
                {"фывфвфывувфы", false},
                {"asdmlj3S3r4ak", true},
                {"<sdaksjdnedeee", false}
        };
    }

    @DataProvider(name = "first_name_data_provider")
    public Object[][] firstNameDataProvider() {
        return new Object[][]{
                {"a", false},
                {"publicbooleanisPaymentCardDateExpiredValidStringdateExpiredStr", false},
                {"1234567", false},
                {"Ariel-Rusalka", false},
                {"Sebastian Krab", false},
                {"Alex", true},
                {"<Alex", false}
        };
    }

    @DataProvider(name = "last_name_data_provider")
    public Object[][] lastNameDataProvider() {
        return new Object[][]{
                {"a", false},
                {"publicbooleanisPaymentCardDateExpiredValidStringdateExpiredStr", false},
                {"1234567", false},
                {"Ariel-Rusalka", false},
                {"Sebastian Krab", false},
                {"Alex", true},
                {"<Alex", false}
        };
    }

    @DataProvider(name = "patronymic_data_provider")
    public Object[][] patronymicDataProvider() {
        return new Object[][]{
                {"a", false},
                {"publicbooleanisPaymentCardDateExpiredValidStringdateExpiredStr", false},
                {"1234567", false},
                {"Ariel-Rusalka", false},
                {"Sebastian Krab", false},
                {"Alex", true},
                {"<Alex", false}
        };
    }

    @DataProvider(name = "email_data_provider")
    public Object[][] emailDataProvider() {
        return new Object[][]{
                {"sd@a.ru", false},
                {"publicbooleanisPaymentCardDateExpiredValidStringdate@expired.com", false},
                {"myemail->@gmail.com", false},
                {"myemail-!@gmail.com", false},
                {"my_email_is@mail.com", true},
                {"my.emailis@mail.com", true}
        };
    }

    @DataProvider(name = "tariff_description_data_provider")
    public Object[][] tariffDescriptionDataProvider() {
        return new Object[][]{
                {"s", false},
                {"publicbooleanisPaymentCardDateExpiredValidStringdatewefadfsdfdsafsdfsfaasdferfrbbye45yrtgdfgddsfadsfadf", false},
                {"dsdsf!", false},
                {"sdffsf>", false},
                {"Base 1", true},
                {"Base-1", true}
        };
    }

    @DataProvider(name = "tariff_price_data_provider")
    public Object[][] tariffPriceDataProvider() {
        return new Object[][]{
                {"0.00", false},
                {"0.000", false},
                {"0.01", true},
                {"9999.99", true},
                {"1.99", true}
        };
    }

    @DataProvider(name = "tariff_traffic_data_provider")
    public Object[][] tariffTrafficDataProvider() {
        return new Object[][]{
                {"123456789", false},
                {"012345678", false},
                {"0", false},
                {"aasccs", false},
                {"10240", true}
        };
    }

    @DataProvider(name = "tariff_speed_data_provider")
    public Object[][] tariffSpeedDataProvider() {
        return new Object[][]{
                {"123456", false},
                {"01234", false},
                {"0", false},
                {"12345", true},
                {"5", true}
        };
    }

    @DataProvider(name = "tariff_status_data_provider")
    public Object[][] tariffStatusDataProvider() {
        return new Object[][]{
                {"base", false},
                {"block", false},
                {"active", false},
                {"Asdd", false},
                {"BASE", true},
                {"BLOCK", true},
                {"ACTIVE", true}
        };
    }

    @DataProvider(name = "tariff_periodicity_write_off_data_provider")
    public Object[][] tariffPeriodicityWriteOffDataProvider() {
        return new Object[][]{
                {"day", false},
                {"month", false},
                {"never", false},
                {"Asdd", false},
                {"DAY", true},
                {"MONTH", true},
                {"NEVER", true}
        };
    }

    @DataProvider(name = "payment_card_series_data_provider")
    public Object[][] PaymentCardSeriesDataProvider() {
        return new Object[][]{
                {"da", false},
                {"aaaaaa", false},
                {"qs1sd", false},
                {"DA", false},
                {"AAAAAA", false},
                {"AAA1AA", false},
                {"AAA", true},
                {"AAAAA", true}
        };
    }

    @DataProvider(name = "payment_card_amount_data_provider")
    public Object[][] PaymentCardAmountDataProvider() {
        return new Object[][]{
                {"012", false},
                {"123456", false},
                {"1a233", false},
                {"asdasd", false},
                {"1.0", false},
                {"0123", false},
                {"1", true},
                {"12345", true}
        };
    }

    @DataProvider(name = "payment_card_count_data_provider")
    public Object[][] PaymentCardCountDataProvider() {
        return new Object[][]{
                {"012", false},
                {"123456", false},
                {"1a233", false},
                {"asdasd", false},
                {"1.0", false},
                {"0123", false},
                {"1", true},
                {"12345", true}
        };
    }

    @DataProvider(name = "payment_card_number_data_provider")
    public Object[][] PaymentCardNumberDataProvider() {
        return new Object[][]{
                {"AAA000000", false},
                {"AA0000000", false},
                {"AAA0000000000000", false},
                {"AA00000000000000", false},
                {"AAA.0000000", false},
                {"AA<A>AA0000001", false},
                {"AA00000000", false},
                {"AAA0000001", true},
                {"AAAA0000001", true},
                {"AAAAA0000001", true}
        };
    }

    @DataProvider(name = "payment_card_date_expired_data_provider")
    public Object[][] PaymentCardDateExpiredDataProvider() {
        return new Object[][]{
                {"2021-01-01", false},
                {"2022-01-01", false},
                {"2023-01-01", true}
        };
    }

    @DataProvider(name = "id_data_provider")
    public Object[][] IdDataProvider() {
        return new Object[][]{
                {"1000000000000000001", false},
                {"0112", false},
                {"1", true},
                {"1021", true}
        };
    }

    @DataProvider(name = "user_role_provider")
    public Object[][] UserRoleDataProvider() {
        return new Object[][]{
                {"guest", false},
                {"user", false},
                {"admin", false},
                {"asdasdad", false},
                {"GUEST", true},
                {"USER", true},
                {"ADMIN", true}
        };
    }

    @DataProvider(name = "contract_number_for_search_data_provider")
    public Object[][] ContractNumberForSearchDataProvider() {
        return new Object[][]{
                {"1", false},
                {"11111111111111111", false},
                {"asdasd", false},
                {"10", true}
        };
    }

    @DataProvider(name = "email_for_search_data_provider")
    public Object[][] EmailForSearchDataProvider() {
        return new Object[][]{
                {"a", false},
                {"aaaaaaaaaaaaaaaaaaaa", false},
                {"asd<asd", false},
                {"asdasdasd", true}
        };
    }

    @Test(dataProvider = "sing_in_parameters_data_provider")
    public void singInParametersValidPositiveTest(String login, String password, boolean expected) {
        boolean result = validator.singInParametersValid(login, password);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "password_data_provider")
    public void isPasswordValidTest(String password, boolean expected) {
        boolean result = validator.isPasswordValid(password);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "first_name_data_provider")
    public void isFirstNameValidTest(String inputStr, boolean expected) {
        boolean result = validator.isFirstNameValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "last_name_data_provider")
    public void isLastNameValidTest(String inputStr, boolean expected) {
        boolean result = validator.isLastNameValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "patronymic_data_provider")
    public void isPatronymicValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPatronymicValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "email_data_provider")
    public void isEmailValidTest(String inputStr, boolean expected) {
        boolean result = validator.isEmailValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_description_data_provider")
    public void isTariffDescriptionValidTest(String inputStr, boolean expected) {
        boolean result = validator.isTariffDescriptionValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_price_data_provider")
    public void isTariffPriceValidTest(String inputStr, boolean expected) {
        boolean result = validator.isTariffPriceValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_traffic_data_provider")
    public void isTariffTrafficValidTest(String inputStr, boolean expected) {
        boolean result = validator.isTariffTrafficValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_speed_data_provider")
    public void isTariffSpeedValidTest(String inputStr, boolean expected) {
        boolean result = validator.isTariffSpeedValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_status_data_provider")
    public void isTariffStatusValidTest(String inputStr, boolean expected) {
        boolean result = validator.isTariffStatusValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "tariff_periodicity_write_off_data_provider")
    public void isPeriodicityWriteOffValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPeriodicityWriteOffValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "payment_card_series_data_provider")
    public void isPaymentCardSeriesValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPaymentCardSeriesValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "payment_card_amount_data_provider")
    public void isPaymentCardAmountValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPaymentCardAmountValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "payment_card_count_data_provider")
    public void isPaymentCardCountValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPaymentCardCountValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "payment_card_number_data_provider")
    public void isPaymentCardNumberValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPaymentCardNumberValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "payment_card_date_expired_data_provider")
    public void isPaymentCardDateExpiredValidTest(String inputStr, boolean expected) {
        boolean result = validator.isPaymentCardDateExpiredValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "id_data_provider")
    public void isIdValidTest(String inputStr, boolean expected) {
        boolean result = validator.isIdValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "user_role_provider")
    public void isUserRoleValidTest(String inputStr, boolean expected) {
        boolean result = validator.isUserRoleValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "contract_number_for_search_data_provider")
    public void isContractNumberForSearchValidTest(String inputStr, boolean expected) {
        boolean result = validator.isContractNumberForSearchValid(inputStr);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "email_for_search_data_provider")
    public void isEmailForSearchValidTest(String inputStr, boolean expected) {
        boolean result = validator.isEmailForSearchValid(inputStr);
        assertEquals(result, expected);
    }
}
