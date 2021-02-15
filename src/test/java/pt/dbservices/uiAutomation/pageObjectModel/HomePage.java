package pt.dbservices.uiAutomation.pageObjectModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pt.dbservices.base.BasePage;
import pt.dbservices.utilities.assertion.AssertUtils;
import pt.dbservices.utilities.reports.Results;

import static org.testng.Assert.assertEquals;

public class HomePage extends BasePage {
    private Logger log = LogManager.getLogger(HomePage.class.getName());
    public HomePage(WebDriver _driver) {
        super(_driver);
    }

    @FindBy(xpath = "//div[@class='header_user_info']//a[@class='login']")
    private WebElement lnkSignIn;

    @FindBy(xpath = "//div[contains(@class,'readcrumb')]//i[@class='icon-home']")   // home icon
    private WebElement iconHome;


    public boolean isDisplay() {
        waitForPageLoad();
        waitForElementVisible(lnkSignIn);
        AssertUtils.softAssertContains(lnkSignIn.getText(),"Sign in"," Home Page");
        Results.pass("Automation Practice Site - Home page is displayed.");
        return true;
    }
    public boolean clickHomeIcon() {
        waitForElementVisible(iconHome);
        Assert.assertTrue(iconHome.isDisplayed(), "Icon 'Home' is not displayed.");
        iconHome.click();
        Results.pass("Icon 'Home' is clicked successfully.");
        waitForPageLoad();
        return true;
    }

}
