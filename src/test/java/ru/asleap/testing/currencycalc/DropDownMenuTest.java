package ru.asleap.testing.currencycalc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Parameter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Checks whether the currencies enumerated
 * in 'currency-names.csv'are available to choose.
 */
@RunWith(Parameterized.class)
public class DropDownMenuTest extends CalcTest {
    @Parameter
    private String currency;

    public DropDownMenuTest(String currency) {
        this.currency = currency;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() throws IOException {
        URL resource = DropDownMenuTest.class.getClassLoader().getResource("currency-names.csv");
        List<String[]> currencySets = readCsvFile(resource);
        Collection<Object[]> data = new ArrayList<Object[]>();
        for (String[] set : currencySets) {
            for (String currency : set) {
                if (currency.length() != 0) {
                    data.add(new Object[]{
                            currency
                    });
                }
            }
        }
        return data;
    }

    @Test
    public void dropDownMenuTest() {
        ensureDropdownPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(currency, dropdownFrom);
        assertCurrencyInDropdown(currency, dropdownFrom);

        ensureDropdownPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        chooseCurrency(currency, dropdownTo);
        assertCurrencyInDropdown(currency, dropdownTo);
    }
}
