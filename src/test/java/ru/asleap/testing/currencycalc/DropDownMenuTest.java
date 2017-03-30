package ru.asleap.testing.currencycalc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Before
    public void setUp() {
        helper.openCalcPage();
    }

    @Test
    public void dropDownMenuTest() {
        try {
            new WebDriverWait(driver, 15).until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//select[@name='converterFrom']")
                    ));
        } catch (TimeoutException e) {
            fail("Could not load page");
        }

        WebElement converterFrom = driver.findElement(By.xpath("//select[@name='converterFrom']/../div[@class='select']"));
        converterFrom.click();
        try {
            converterFrom.findElement(By.xpath("./div[@class='visible']/span[contains(text(), '" + currency + "')]")).click();
            assertEquals(converterFrom.findElement(By.xpath("./header/strong")).getText(), currency);
        } catch (NoSuchElementException e) {
            fail("Currency " + currency + " not found");
        }

        WebElement converterTo = driver.findElement(By.xpath("//select[@name='converterTo']/../div[@class='select']"));
        converterTo.click();
        try {
            converterTo.findElement(By.xpath("./div[@class='visible']/span[contains(text(), '" + currency + "')]")).click();
            assertEquals(converterTo.findElement(By.xpath("./header/strong")).getText(), currency);
        } catch (NoSuchElementException e) {
            fail("Currency " + currency + " not found");
        }
    }
}
