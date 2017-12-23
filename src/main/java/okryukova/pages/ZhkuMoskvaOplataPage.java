package okryukova.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import okryukova.constants.TinkoffBankConstants;

/**
 * Page object for https://www.tinkoff.ru/zhku-moskva/oplata/?tab=pay <br>
 *
 * @author Olga Kryukova
 */
public class ZhkuMoskvaOplataPage {
    private final WebDriver driver;

    @FindBy(css = "ul#mainMenu li:nth-child(3) span a span")
    private WebElement buttonPayments;

    @FindBy(css = "div.ui-form__row.ui-form__row_component.ui-form__row_button-submit  div  div  button  h2")
    private WebElement buttonOplata;

    @FindBy(id = "payerCode")
    private WebElement fieldCode;

    @FindBy(id = "period")
    private WebElement fieldPeriod;

    @FindBy(css = "form div:nth-child(4) input")
    private WebElement fieldSum;

    @FindBy(css = "div.ui-form__row.ui-form__row_text.ui-form__row_default-error-view-visible  div  div.ui-form-field-error-message.ui-form-field-error-message_ui-form")
    private WebElement payerCodeError;

    @FindBy(css = "div.ui-form__row.ui-form__row_date.ui-form__row_default-error-view-visible  div  div.ui-form-field-error-message.ui-form-field-error-message_ui-form")
    private WebElement periodError;

    @FindBy(css = "div:nth-child(4) div div div div div div div div.ui-form-field-error-message.ui-form-field-error-message_ui-form")
    private WebElement sumError;

    /**
     * @param driver
     */
    public ZhkuMoskvaOplataPage (WebDriver driver) {
        this.driver = driver;
        if (!TinkoffBankConstants.ZHKU_MOSKVA_OPLATA_PAGE_TITLE.equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the Zhku Moskva Oplata Page page");
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
     * Press button "Оплата" <br>
     *
     * @return ZhkuMoskvaOplataPage
     */
    public ZhkuMoskvaOplataPage pressButtonPayment() {
        this.buttonOplata.submit();//отплавка данных на сервер
        return this;
    }

    /**
     * The method fills fields of form payment according to the specified parameters <br>
     *
     * @param payerCode - corresponds to the payer code field
     * @param period - corresponds to the payment period field
     * @param paymentSum - corresponds to the payment amount field
     * @return ZhkuMoskvaOplataPage
     */
    public ZhkuMoskvaOplataPage fillFields(String payerCode, String period, String paymentSum ) {
        this.fieldCode.clear();
        this.fieldCode.sendKeys(payerCode);
        this.fieldPeriod.clear();
        this.fieldPeriod.sendKeys(period);
        this.fieldSum.clear();
        this.fieldSum.sendKeys(paymentSum);
        return this;
    }

    /**
     * Method returns an error message for field code <br>
     *
     * @return error message
     */
    public String getCodeError( ) {
        String message="" ;
        try {
            message=this.payerCodeError.getText();
            return message;
        } catch (NoSuchElementException e) {
            return message;
        }
    }

    /**
     * Method returns an error message for field period <br>
     *
     * @return error message
     */
    public String getPeriodError() {
        String message="" ;
        try {
            message=this.periodError.getText();
            return message;
        } catch (NoSuchElementException e) {
            return message;
        }
    }

    /**
     * Method returns an error message for field sum <br>
     *
     * @return error message
     */
    public String getSumError ( ) {
        String message="" ;
        try {
            message=this.sumError.getText();
            return message;
        } catch (NoSuchElementException e) {
            return message;
        }
    }
}
