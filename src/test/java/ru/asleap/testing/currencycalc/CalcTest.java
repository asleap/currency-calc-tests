package ru.asleap.testing.currencycalc;


import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Base abstract class for currency calculator tests.
 * Contains several steps that are shared among test cases,
 * util methods and common constants.
 */
public abstract class CalcTest {
    protected static WebDriver driver;
    private static final String CALC_PAGE_URL = "http://www.sberbank.ru/ru/quotes/converter";

    protected final String DROPDWON_FROM_NAME = "converterFrom";
    protected final String DROPDOWN_TO_NAME = "converterTo";

    @BeforeClass
    public static void init() {
        driver = WebDriverHelper.getInstance().getDriver();
    }

    @Before
    public void setUp() {
        refreshCalcPage();
    }

    public void refreshCalcPage() {
        if (!driver.getCurrentUrl().equals(CALC_PAGE_URL)) {
            driver.get(CALC_PAGE_URL);
        } else {
            driver.navigate().refresh();
        }
    }

    public void waitPresence(String xpath, long timeoutSeconds) throws TimeoutException {
        new WebDriverWait(driver, timeoutSeconds).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(xpath)
                ));
    }

    @Step
    public void ensureDropdownIsPresent(String dropdownName) {
        try {
            waitPresence("//select[@name='" + dropdownName + "']", 15);
        } catch (TimeoutException e) {
            fail("Dropdown menu is not present: " + dropdownName);
        }
    }

    @Step
    public WebElement clickDropdownMenu(String menuName) {
        WebElement menu = driver.findElement(By.xpath(
                "//select[@name='" + menuName + "']/../div[@class='select']"
        ));
        menu.click();
        return menu;
    }

    @Step
    public void chooseCurrency(String currency, WebElement dropdownMenu) throws NoSuchElementException {
        WebElement currencySpan = dropdownMenu.findElement(By.xpath(
                "./div[@class='visible']/span[contains(text(), '" + currency + "')]"
        ));
        currencySpan.click();
    }

    @Step
    public void assertCurrencyIn(String currency, WebElement dropdownMenu) {
        assertEquals(dropdownMenu.findElement(By.xpath("./header/strong")).getText(), currency);
    }
}
