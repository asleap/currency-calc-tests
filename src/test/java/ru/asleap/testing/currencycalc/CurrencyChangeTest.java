package ru.asleap.testing.currencycalc;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Parameter;


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
        ensureDropdownPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(currencyFrom, dropdownFrom);

        ensureDropdownPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        chooseCurrency(currencyTo, dropdownTo);

        dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(currencyTo, dropdownFrom);

        assertCurrencyInDropdown(currencyFrom, dropdownTo);
        assertCurrencyInDropdown(currencyTo, dropdownFrom);
    }
}
