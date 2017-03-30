package ru.asleap.testing.currencycalc;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Helper class that contains several steps,
 * which are registered in Allure report.
 */
public class WebDriverHelper {
    private WebDriver driver;
    private final String CALC_PAGE_URL = "http://www.sberbank.ru/ru/quotes/converter";
    private static WebDriverHelper instance = new WebDriverHelper();

    public static WebDriverHelper getInstance() {
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebDriverHelper() {
        ChromeDriverManager.getInstance().setup();
        this.driver = new ChromeDriver();
    }

    @Step
    public void openCalcPage() {
        if (!driver.getCurrentUrl().equals(CALC_PAGE_URL)) {
            driver.get(CALC_PAGE_URL);
        } else {
            driver.navigate().refresh();
        }
    }

    @Step
    public WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    @Step
    public WebElement findElementByXpath(WebElement relative, String xpath) {
        return relative.findElement(By.xpath(xpath));
    }

    @Step
    public void findElementByXpathAndClick(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    @Step
    public void findElementByXpathAndClick(WebElement relative, String xpath) {
        relative.findElement(By.xpath(xpath)).click();
    }

    @Step
    public void clickElement(WebElement element) {
        element.click();
    }

    public void quit() {
        driver.quit();
    }
}
