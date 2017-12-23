package okryukova.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import okryukova.constants.TinkoffBankConstants;

/**
 * Page object for https://www.tinkoff.ru/<br>
 *
 * @author Olga Kryukova
 */
public class StartPage  {
    private final WebDriver driver;

    @FindBy ( css= "ul#mainMenu li:nth-child(3) span a span")
    private WebElement buttonPayments;

    /**
     *
     * @param driver
     */
    public StartPage(WebDriver driver) {
        this.driver = driver;
        driver.get(TinkoffBankConstants.TINKOFF_BANK_URL);
        if(!TinkoffBankConstants.START_PAGE_TITLE.equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the Start page");
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Press button "Платежи"<br>
     *
     * @return PaymentsPage
     */
    public PaymentsPage pressButtonPayments() {
        this.buttonPayments.click();
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.PAYMENTS_PAGE_TITLE ));
        return new PaymentsPage(driver);
    }
}
