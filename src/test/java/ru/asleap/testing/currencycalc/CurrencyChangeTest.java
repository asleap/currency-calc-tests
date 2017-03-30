package ru.asleap.testing.currencycalc;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Parameter;

import static org.junit.Assert.assertEquals;
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

    @Before
    public void setUp() {
        helper.openCalcPage();
    }

    @Test
    public void currencyChangeTest() {
        try {
            new WebDriverWait(driver, 15).until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//select[@name='converterFrom']")
                    ));
        } catch (TimeoutException e) {
            fail("Cannot find select");
        }
        WebElement converterFrom = driver.findElement(By.xpath("//select[@name='converterFrom']/../div[@class='select']"));
        converterFrom.click();
        try {
            converterFrom.findElement(By.xpath("./div[@class='visible']/span[contains(text(), '" + currencyFrom + "')]")).click();
        } catch (NoSuchElementException e) {
            fail("Currency currencyFrom " + currencyFrom + " not found");
        }

        try {
            new WebDriverWait(driver, 15).until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//select[@name='converterTo']")
                    ));
        } catch (TimeoutException e) {
            fail("Cannot find select");
        }
        WebElement converterTo = driver.findElement(By.xpath("//select[@name='converterTo']/../div[@class='select']"));
        converterTo.click();
        try {
            converterTo.findElement(By.xpath("./div[@class='visible']/span[contains(text(), '" + currencyTo + "')]")).click();
        } catch (NoSuchElementException e) {
            fail("Currency currencyTo " + currencyTo + " not found");
        }

        converterFrom.click();
        converterFrom.findElement(By.xpath("./div[@class='visible']/span[contains(text(), '" + currencyTo + "')]")).click();
        assertEquals(converterTo.findElement(By.xpath("./header/strong")).getText(), currencyFrom);
    }
}
