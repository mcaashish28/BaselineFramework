package pt.dbservices.uiAutomation.pageObjectModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pt.dbservices.base.BasePage;
import pt.dbservices.utilities.reports.Results;

public class MainMenuLinksPage extends BasePage {
    private Logger log = LogManager.getLogger(MainMenuLinksPage.class.getName());

    private Actions actions;
    public MainMenuLinksPage(WebDriver _driver) {
        super(_driver);
        actions = new Actions(_driver);
    }

    @FindBy(xpath = "//div[@id='block_top_menu']//a[@title='Women']")               // link: Main Menu 'Women' Tab
    private WebElement lnkMainMenuWomenTab;

    @FindBy(xpath = "(//div[@id='center_column']//span[contains(text(),'Women')])[1]")               // Banner with Women Text in Center Section
    private WebElement txtWomenBanner;

    public MainMenuLinksPage mouseHoverLnkMainMenu_WomenTab(){
        Assert.assertTrue(lnkMainMenuWomenTab.isDisplayed(), "Tab text 'WOMEN' is not displayed.");
        actions.moveToElement(lnkMainMenuWomenTab).click().build().perform();
        Results.pass("Mouse is hovered on Tab text 'WOMEN' successfully. ");
        log.info("Mouse is hovered on Tab text 'WOMEN' successfully. ");
        waitForPageLoad();
        return this;
    }

    public boolean clickTab_WOMEN() {
        waitForElementVisible(lnkMainMenuWomenTab);
        Assert.assertTrue(lnkMainMenuWomenTab.isDisplayed(), "Tab text 'WOMEN' is not displayed.");
        lnkMainMenuWomenTab.click();
        Results.pass("Tab text 'Women' is clicked successfully.");
        // verify Page is loaded with WOMEN Banner
        waitForPageLoad();
        waitForElementVisible(txtWomenBanner);
        Assert.assertTrue(txtWomenBanner.isDisplayed(), "Banner text 'WOMEN' is not displayed.");
        Results.pass("Banner text 'Women' is displayed successfully.",true);
        log.info("Banner text 'Women' is displayed successfully.");
        return true;
    }


}
