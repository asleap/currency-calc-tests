package ru.asleap.testing.currencycalc;


import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * CalcTest is a base class for currency calculator tests.
 */
public class CalcTest {
    protected static WebDriver driver;
    protected static WebDriverHelper helper;

    @BeforeClass
    public static void init() {
        ChromeDriverManager.getInstance().setup();
        if (driver == null) {
            driver = new ChromeDriver();
        }
        if (helper == null) {
            helper = new WebDriverHelper(driver);
        }
    }

    @AfterClass
    public static void quit() {
        helper.quit();
    }
}
