package ru.asleap.testing.currencycalc;


import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;


/**
 * Base abstract class for currency calculator tests.
 */
public abstract class CalcTest {
    protected static WebDriver driver;
    protected static WebDriverHelper helper;

    @BeforeClass
    public static void init() {
        helper = WebDriverHelper.getInstance();
        driver = helper.getDriver();
    }
}
