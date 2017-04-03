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
 * Tests correct multiplication of converter wrt current conversion rate.
 */
@RunWith(Parameterized.class)
public class ConversionTest extends CalcTest {
    @Parameter
    private final String baseCurrency = "RUR";
    @Parameter
    private String conversionCurrency;
    @Parameter
    private double amount;
    @Parameter
    private double eps;

    public ConversionTest(String conversionCurrency, double amount, double eps) {
        this.conversionCurrency = conversionCurrency;
        this.amount = amount;
        this.eps = eps;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() throws IOException {
        URL resource = ConversionTest.class.getClassLoader().getResource("conversions.csv");
        List<String[]> conversions = readCsvFile(resource);
        Collection<Object[]> data = new ArrayList<Object[]>();
        for (String[] conversion : conversions) {
            if (conversion.length == 3) {
                data.add(new Object[]{
                        conversion[0],
                        Double.parseDouble(conversion[1]),
                        Double.parseDouble(conversion[2])
                });
            }
        }
        return data;
    }

    @Test
    public void buyConversionTest() {
        ensureDropdownPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(baseCurrency, dropdownFrom);

        ensureDropdownPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        chooseCurrency(conversionCurrency, dropdownTo);

        ensureTableReloaded(conversionCurrency);
        int units = getUnits();
        double buyRate = getBuyRate();
        setAmount(amount);
        clickShowButton();

        ensureConversionResultPresent();
        double conversionResult = getConversionResult();
        double calculatedResult = Math.round(amount / buyRate * units * 100) / 100D;
        assertConversionResult(conversionResult, calculatedResult, eps);
    }

    @Test
    public void sellConversionTest() {
        ensureDropdownPresent(DROPDWON_FROM_NAME);
        WebElement dropdownFrom = clickDropdownMenu(DROPDWON_FROM_NAME);
        chooseCurrency(conversionCurrency, dropdownFrom);

        ensureDropdownPresent(DROPDOWN_TO_NAME);
        WebElement dropdownTo = clickDropdownMenu(DROPDOWN_TO_NAME);
        chooseCurrency(baseCurrency, dropdownTo);

        ensureTableReloaded(conversionCurrency);
        int units = getUnits();
        double sellRate = getSellRate();
        setAmount(amount);
        clickShowButton();

        ensureConversionResultPresent();
        double conversionResult = getConversionResult();
        double calculatedResult = Math.round(amount * sellRate / units * 100) / 100D;
        assertConversionResult(conversionResult, calculatedResult, eps);
    }
}
