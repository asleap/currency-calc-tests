package ru.asleap.testing.currencycalc;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Parameter;

import static org.junit.Assert.fail;


/**
 * Test that checks correct currency inversion when choosing
 * same currency in both from and to selects.
 */
public class CurrencyChangeTest extends CalcTest {
    @Parameter
    private String currencyFrom = "RUR";
    @Parameter
    private String currencyTo = "USD";

    @Test
    public void currencyChangeTest() {
        ensureDropdownIsPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        try {
            chooseCurrency(currencyFrom, dropdownFrom);
        } catch (NoSuchElementException e) {
            fail("Cannot find currency span: " + currencyFrom);
        }

        ensureDropdownIsPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        try {
            chooseCurrency(currencyTo, dropdownTo);
        } catch (NoSuchElementException e) {
            fail("Cannot find currency span: " + currencyTo);
        }

        dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(currencyTo, dropdownFrom);

        assertCurrencyIn(currencyFrom, dropdownTo);
        assertCurrencyIn(currencyTo, dropdownFrom);
    }
}
