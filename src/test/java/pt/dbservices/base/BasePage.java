package pt.dbservices.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pt.dbservices.utilities.driver.DriverUtils;

public abstract class BasePage {

    private static final int TIMEOUT = 10;
    private static final int POLLING = 100;

    public WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver _driver){
        this.driver = _driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(driver,this);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    protected void waitForElementVisible(WebElement element) {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementVisible(By loc) {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    private void waitForElementToBeDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void clickUsingMouseAction(WebElement elementToClick){
        scrollIntoView(elementToClick);
        Actions actions = new Actions(driver);
        actions.moveToElement(elementToClick);
        actions.click().build().perform();
        waitForPageLoad();
    }

    protected void clickUsingJavascriptExecutor(WebElement elementToClick) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
        waitForPageLoad();
    }

    protected void refreshWindow(){
        ((JavascriptExecutor) driver).executeScript("window.location.reload(true)");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void waitForPageLoad(){
        DriverUtils.waitForPageLoad();
    }
}
