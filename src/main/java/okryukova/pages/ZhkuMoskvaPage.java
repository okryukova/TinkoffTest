package okryukova.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import okryukova.constants.TinkoffBankConstants;

/**
 * Page object for https://www.tinkoff.ru/zhku-moskva/ <br>
 *
 * @author Olga Kryukova
 */
public class ZhkuMoskvaPage {
    private final WebDriver driver;

    @FindBy(css = "ul#mainMenu li:nth-child(3) span a span")
    private WebElement buttonPayments;

    @FindBy(css = ".Tabs__container_margin_m_vnNV1  div  ul  li:nth-child(2)  div  a  span")
    private WebElement buttonOplata;


    /**
     * @param driver
     */
    public ZhkuMoskvaPage (WebDriver driver) {
        this.driver = driver;
        if (!TinkoffBankConstants.ZHKU_MOSKVA_PAGE_TITLE.equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the  Zhku Moskva   page");
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Press button "Платежи" <br>
     *
     * @return PaymentsPage
     */
    public PaymentsPage pressButtonPayments() {
        buttonPayments.click();
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.PAYMENTS_PAGE_TITLE ));
        return new PaymentsPage(driver);
    }

    /**
     * Method checks that this page corresponds to https://www.tinkoff.ru/zhku-moskva/ <br>
     *
     * @return true if page corresponds to https://www.tinkoff.ru/zhku-moskva/ otherwise false
     */
    public boolean isZhyMoskvaPage(){
        if (TinkoffBankConstants.ZHKU_MOSKVA_PAGE_TITLE.equals(driver.getTitle())){
            return true;
        }
        return false;
    }

    /**
     * Press button "Оплата" <br>
     *
     * @return ZhkuMoskvaOplataPage
     */
    public ZhkuMoskvaOplataPage pressButtonPayment() {
        this.buttonOplata.click();
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.ZHKU_MOSKVA_OPLATA_PAGE_TITLE));
        return new ZhkuMoskvaOplataPage (driver);
    }
}

