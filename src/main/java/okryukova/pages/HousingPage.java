package okryukova.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import okryukova.constants.TinkoffBankConstants;

/**
 * Page object for https://www.tinkoff.ru/payments/categories/kommunalnie-platezhi/<br>
 *
 * @author Olga Kryukova
 */
public class HousingPage {
    private final WebDriver driver;
    private String serviceProvider;

    @FindBy( css= "div.Input__input_1cCj6 div label input")
    private WebElement search;

    @FindBy( css= "div:nth-child(1) div div.SearchSuggest__entryText_10JW0 div")
    private WebElement searchResult;

    @FindBy( css= "ul  li:nth-child(1)  span.ui-link.ui-menu__link.ui-menu__link_logo.ui-menu__link_icons.ui-menu__link_icons_active  a  span")
    private WebElement buttonZhkYMoskva;

    @FindBy( css= "span.ui-link.payment-page__title_inner")
    private WebElement currentRegion;

    /**
     * @param driver
     */
    public HousingPage(WebDriver driver) {
        this.serviceProvider="";
        this.driver = driver;
        if (!TinkoffBankConstants.HOUSING_PAGE_TITLE.equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the start Housing page");
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Press button "ЖКУ-Москва"<br>
     *
     * @return ZhkuMoskvaPage
     */
    public ZhkuMoskvaPage pressButtonZHkyMoskva() {
        WebDriverWait waitTitle = new WebDriverWait(driver, 15);
        waitTitle.until(ExpectedConditions.visibilityOf(this.buttonZhkYMoskva));
        this.serviceProvider = buttonZhkYMoskva.getText();
        buttonZhkYMoskva.click();
        waitTitle = new WebDriverWait(driver, 10);
        waitTitle.until(ExpectedConditions.titleContains(TinkoffBankConstants.ZHKU_MOSKVA_PAGE_TITLE));
        return new ZhkuMoskvaPage (driver);
    }

    /**
     * Check that the selected region corresponds to cityName<br>
     *
     * @param cityName
     * @return -true if selected region corresponds to cityName otherwise false
     */
    public boolean checkRegion(String cityName) {
        if (this.currentRegion.getText().equals(cityName) ) {
            return true;
        }
        return false;
        }

    /**
     * Method returns field serviceProvider <br>
     *
     * @return String
     */
    public String getServiceProvider(){
        return serviceProvider;
    }

    /**
     * The method replaces the current region to the specified <br>
     *
     * @param regionName -specified region
     * @return HousingPage
     */
    public HousingPage changeRegion(String regionName) {
        this.currentRegion.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement requiredReg = driver.findElement(By.xpath("//span[text()=\"" + regionName + "\"]"));
        requiredReg.click();
        return this;
    }

    /**
     * The method checks that the specified provider does not exist in the list <br>
     *
     * @param providerName - specified provider
     * @return - result of search (error message)
     */
    public String checkProviderNotExist(String providerName) {
        this.search.sendKeys(providerName);
        return this.searchResult.getText();
    }
}

