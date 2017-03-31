package ru.asleap.testing.currencycalc;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * Helper singleton class that aggregates WebDriver instance for all tests
 */
public class WebDriverHelper {
    private WebDriver driver;
    private static WebDriverHelper instance = new WebDriverHelper();

    private WebDriverHelper() {
        ChromeDriverManager.getInstance().setup();
        this.driver = new ChromeDriver();
    }

    public static WebDriverHelper getInstance() {
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quit() {
        driver.quit();
    }
}
