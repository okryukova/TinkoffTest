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
 * Page object for https://www.tinkoff.ru/payments/<br>
 *
 * @author Olga Kryukova
 */
public class PaymentsPage {
    private final WebDriver driver;

    @FindBy( css= "div.Input__input_1cCj6 div label input")
    private WebElement search;

    @FindBy( css= "div  div:nth-child(1)  div div:nth-child(1)  div  div.SearchSuggest__entryText_10JW0  div.Text__text_3WKsv.Text__text_size_17_HhZgX.Text__text_sizeDesktop_17_IHzjA.Text__text_wrap_ellipsis_2wQkF")
    private WebElement firstResultSearch;

    @FindBy( css= "a[title=\"ЖКХ\"]")
    private WebElement buttonHousingPayment;

    /**
     *
     * @param driver
     */
    public PaymentsPage(WebDriver driver) {
        this.driver = driver;
        if(!TinkoffBankConstants.PAYMENTS_PAGE_TITLE.equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the Payments page");
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Search provider by specified name<br>
     *
     * @param providerName - name of the sought provider
     * @return PaymentsPage
     */
    public PaymentsPage searchServiceProvider(String providerName) {
        this.search.sendKeys(providerName);
        return this;
    }

    /**
     * The method returns the name of the provider which is in the first position on search results<br>
     *
     * @return  String - The method returns the name of the provider which is in the first position on search results
     *
     */
    public String getFirstSearchElement() {
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.visibilityOf(this.firstResultSearch));
        return this.firstResultSearch.getText();
    }

    /**
     * Select Zhky Moskva provider<br>
     *
     * @return ZhkuMoskvaPage
     */
    public ZhkuMoskvaPage selectZhkyMoskva() {
        this.firstResultSearch.click();
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.ZHKU_MOSKVA_PAGE_TITLE));
        return new ZhkuMoskvaPage(driver);
    }

    /**
     * Press button "ЖКХ"<br>
     *
     * @return HousingPage
     */
    public HousingPage pressButtonHousing() {
        WebDriverWait waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.visibilityOf(this.buttonHousingPayment));
        this.buttonHousingPayment.click();
        waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.HOUSING_PAGE_TITLE));
        return new HousingPage(driver);
    }
}
