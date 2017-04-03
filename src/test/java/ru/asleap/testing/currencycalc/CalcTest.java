package ru.asleap.testing.currencycalc;


import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Base abstract class for currency calculator tests.
 * Contains several steps that are shared among test cases,
 * util methods and common constants.
 */
public abstract class CalcTest {
    private static final String CALC_PAGE_URL = "http://www.sberbank.ru/ru/quotes/converter";
    private static final int WAIT_TIMEOUT = 15;

    protected static WebDriver driver;

    protected static final String DROPDWON_FROM_NAME = "converterFrom";
    protected static final String DROPDOWN_TO_NAME = "converterTo";

    @BeforeClass
    public static void init() {
        driver = WebDriverHelper.getInstance().getDriver();
    }

    @Before
    public void setUp() {
        refreshCalcPage();
    }

    protected static List<String[]> readCsvFile(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        List<String[]> result = new ArrayList<String[]>();
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line.split(","));
        }
        reader.close();
        return result;
    }

    protected void refreshCalcPage() {
        if (!driver.getCurrentUrl().equals(CALC_PAGE_URL)) {
            driver.get(CALC_PAGE_URL);
        } else {
            driver.navigate().refresh();
        }
    }

    @Step
    public void ensureDropdownPresent(String dropdownName) {
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT).until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//select[@name='" + dropdownName + "']")
                    ));
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
    public void chooseCurrency(String currency, WebElement dropdownMenu) {
        try {
            WebElement currencySpan = dropdownMenu.findElement(By.xpath(
                    "./div[@class='visible']/span[contains(text(), '" + currency + "')]"
            ));
            currencySpan.click();
        } catch (NoSuchElementException e) {
            fail("Cannot find currency span: " + currency);
        }
    }

    @Step
    public void assertCurrencyInDropdown(String currency, WebElement dropdownMenu) {
        assertEquals(dropdownMenu.findElement(By.xpath("./header/strong")).getText(), currency);
    }

    @Step
    public void ensureTableReloaded(String currency) {
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(ExpectedConditions.textToBe(
                            By.xpath("//div[@class='current-table']/table/tbody/tr/td[1]/span"),
                            currency));
        } catch (TimeoutException e) {
            fail("Table did not reload");
        }
    }

    @Step
    public int getUnits() {
        while (true) {
            String text = driver.findElement(By.xpath("//div[@class='current-table']/table/tbody/tr/td[2]"))
                    .getText().trim().replace(" ", "");
            int result;
            try {
                result = Integer.parseInt(text.replace(',', '.'));
                return result;
            } catch (NumberFormatException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Step
    public double getBuyRate() {
        while (true) {
            String text = driver.findElement(By.xpath("//div[@class='current-table']/table/tbody/tr/td[3]/span"))
                    .getText().trim().replace(" ", "");
            double result;
            try {
                result = Double.parseDouble(text.replace(',', '.'));
                return result;
            } catch (NumberFormatException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Step
    public double getSellRate() {
        while (true) {
            String text = driver.findElement(By.xpath("//div[@class='current-table']/table/tbody/tr/td[4]/span"))
                    .getText().trim().replace(" ", "");
            double result;
            try {
                result = Double.parseDouble(text.replace(',', '.'));
                return result;
            } catch (NumberFormatException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Step
    public void setAmount(double amount) {
        String value = String.valueOf(amount);
        WebElement input = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Сумма']")));
        input.clear();
        input.sendKeys(value);
    }

    @Step
    public void clickShowButton() {
        driver.findElement(By.xpath("//button[contains(text(), 'Показать')]")).click();
    }

    @Step
    public void ensureConversionResultPresent() {
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(ExpectedConditions.textToBe(
                            By.xpath("//div[@class='converter-result']/h3"),
                            "Вы получите:"));
        } catch (TimeoutException e) {
            fail("Conversion result is not present");
        }
    }

    @Step
    public double getConversionResult() {
        String text = driver.findElement(By.xpath("//div[@class='converter-result']/h4/span[1]"))
                .getText().trim().replace(" ", "");
        return Double.parseDouble(text.replace(",", "."));
    }

    @Step
    public void assertConversionResult(double conversionResult, double calculatedResult, double eps) {
        double difference = Math.abs(conversionResult - calculatedResult);
        if (difference > eps) {
            fail("Difference between conversion result ["
                    + conversionResult
                    + "] and calculated result ["
                    + calculatedResult
                    + "] exceeds eps [" + eps + "]");
        }
    }
}
