package ru.asleap.testing.currencycalc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CurrencyChangeTest.class, DropDownMenuTest.class})
public class CalcTestSuite {
    private static WebDriverHelper helper;

    @BeforeClass
    public static void setUp() {
        helper = WebDriverHelper.getInstance();
    }

    @AfterClass
    public static void tearDown() {
        helper.quit();
    }
}
