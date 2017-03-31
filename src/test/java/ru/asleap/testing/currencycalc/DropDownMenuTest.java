package ru.asleap.testing.currencycalc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.fail;


/**
 * Checks whether the currencies enumerated
 * in 'currency-names.csv'are available to choose.
 */
@RunWith(Parameterized.class)
public class DropDownMenuTest extends CalcTest {
    @Parameter("Currency")
    private String currency;

    public DropDownMenuTest(String currency) {
        this.currency = currency;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() throws IOException {
        URL resource = DropDownMenuTest.class.getClassLoader().getResource("currency-names.csv");
        List<String[]> currencySets = getCurrencyNames(resource);
        Collection<Object[]> data = new ArrayList<Object[]>();
        for (String[] set : currencySets) {
            for (String currency : set) {
                data.add(new Object[]{
                        currency
                });
            }
        }
        return data;
    }

    public static List<String[]> getCurrencyNames(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        List<String[]> result = new ArrayList<String[]>();
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line.split(","));
        }
        reader.close();
        return result;
    }

    @Test
    public void dropDownMenuTest() {
        ensureDropdownIsPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        try {
            chooseCurrency(currency, dropdownFrom);
        } catch (NoSuchElementException e) {
            fail("Cannot find currency span: " + currency);
        }
        assertCurrencyIn(currency, dropdownFrom);

        ensureDropdownIsPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        try {
            chooseCurrency(currency, dropdownTo);
        } catch (NoSuchElementException e) {
            fail("Cannot find currency span: " + currency);
        }
        assertCurrencyIn(currency, dropdownTo);
    }
}
