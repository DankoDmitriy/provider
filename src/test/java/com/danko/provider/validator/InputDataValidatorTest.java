package com.danko.provider.validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class InputDataValidatorTest {
    private InputDataValidator validator;

    @BeforeClass
    public void createValidator() {
        validator = InputDataValidator.getInstance();
    }

    @Test
    public void singInParametersValidPositiveTest(){
        assertEquals(validator.singInParametersValid("111111111111111","3sasdwqfcaxta"),true);

    }
}
