package okryukova.tets;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebDriver;

import okryukova.pages.*;
import okryukova.constants.TinkoffBankConstants;

/**
 *This is a test class
 *
 * @author Olga Kryuova
 *
 */
public class CommunalPaymentsTest {
    private WebDriver driver;
    private String [][] dataTest ={
            {"", "", "", "Поле обязательное", "Поле обязательное", "Поле обязательное"},
            {"4444444554", "01.2017", "15000", "", "", ""},
            {"0", "1", "0", "Поле неправильно заполнено", "Поле заполнено некорректно", "Минимальная сумма перевода — 10 \u20BD"},
            {"4444444554", "4545", "15001", "", "Поле заполнено некорректно", "Максимальная сумма перевода — 15 000 \u20BD"},
            {"12", "01.2017", "15", "Поле неправильно заполнено", "", ""}
    };

    @BeforeClass // Runs this method before the first test method in the current class is invoked
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();
    }

    /**
     * Test for check of communal payments/<br>
     *
     * Scenario:<br>
     * 1. Go to https://www.tinkoff.ru/ page<br>
     * 2. Select "Платежи" and go to https://www.tinkoff.ru/payments/ page<br>
     * 3. Select "Коммунальные платежи" and go to https://www.tinkoff.ru/payments/categories/kommunalnie-platezhi/ page<br>
     * 4. Check region. Should be "г. Москва" ( otherwise choose "г. Москва")<br>
     * 5. Select the first service provider, save its name and go to https://www.tinkoff.ru/zhku-moskva/ page<br>
     * 6. On the payment page go to "Оплатить ЖКУ в Москве" <br>
     * 7. Check input fields with invalid values<br>
     * 8. Repeat step 2<br>
     * 9. In the search field enter service provider name from step 5<br>
     * 10. Check the tested service provider is the first in the search result<br>
     * 11. Go to service provider and check that page is the same as in step #5<br>
     * 12. Perform steps 2 and 3<br>
     * 13. Select region - "г. Санкт-Петербург"<br>
     * 14. Check tested service provider does not exist in the list<br>
     */
    @Test
    public void paymentTest() {
        // STEP 1: Go to https://www.tinkoff.ru/ page
        StartPage startPage = new StartPage(driver);

        //STEP 2: go to https://www.tinkoff.ru/payments/ page
        PaymentsPage paymentsPage=startPage.pressButtonPayments();

        //STEP 3: go to https://www.tinkoff.ru/payments/categories/kommunalnie-platezhi/ page
        HousingPage housingPage=paymentsPage.pressButtonHousing();

        //STEP 4: Check region
        if(!housingPage.checkRegion(TinkoffBankConstants.MOSCOW_CITY)){
            housingPage=housingPage.changeRegion(TinkoffBankConstants.MOSCOW_CITY);
        }

        // STEP 5: Select the first service provider, save its name and go to https://www.tinkoff.ru/zhku-moskva/ page
        ZhkuMoskvaPage  zhkuMoskvaPage=housingPage.pressButtonZHkyMoskva();
        String provider=housingPage.getServiceProvider();

        //STEP6:On the payment page go to "Оплатить ЖКУ в Москве"
        ZhkuMoskvaOplataPage zhkuMoskvaOplataPage= zhkuMoskvaPage.pressButtonPayment();

        //STEP 7: Check input fields with invalid values
        for(int i=0; i<dataTest.length; i++) {
            zhkuMoskvaOplataPage= zhkuMoskvaOplataPage.fillFields(dataTest[i][0],dataTest[i][1],dataTest[i][2]);
            zhkuMoskvaOplataPage=zhkuMoskvaOplataPage.pressButtonPayment();
            assertEquals(zhkuMoskvaOplataPage.getCodeError(), dataTest[i][3]);
            assertEquals(zhkuMoskvaOplataPage.getPeriodError(), dataTest[i][4]);
            assertEquals(zhkuMoskvaOplataPage.getSumError(),dataTest[i][5]);
        }

        //STEP 8: go to https://www.tinkoff.ru/payments/ page
        paymentsPage=zhkuMoskvaOplataPage.pressButtonPayments();

        //STEP 9:  In the search field enter service provider name from step 5
        paymentsPage=paymentsPage.searchServiceProvider(provider);

        //STEP 10: Check the tested service provider is the first in the search result
        assertEquals(paymentsPage.getFirstSearchElement(),provider);

        //STEP 11: Go to service provider and check that page is the same as in step 5
        ZhkuMoskvaPage zhkuMoskvaPage2 =paymentsPage.selectZhkyMoskva();
        assertTrue(zhkuMoskvaPage2.isZhyMoskvaPage());

        //STEP 12: Perform steps 2 and 3
        paymentsPage=zhkuMoskvaPage2.pressButtonPayments();
        housingPage=paymentsPage.pressButtonHousing();

        //STEP 13: Select region - "г. Санкт-Петербург"
        housingPage=housingPage.changeRegion(TinkoffBankConstants.ST_PETERSBURG_CITY);

        //STEP 14: Check tested service provider does not exist in the list
        assertEquals(housingPage.checkProviderNotExist(provider),"Ничего не найдено");
    }

   @AfterClass //Runs this method after all tests in the current class is invoked
    public void afterClass() {
        driver.quit();
    }
}
