package pt.dbservices.uiAutomation.testScripts.uiSanity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pt.dbservices.base.BaseTest;
import pt.dbservices.uiAutomation.pageObjectModel.HomePage;
import pt.dbservices.uiAutomation.pageObjectModel.MainMenuLinksPage;
import pt.dbservices.utilities.driver.DriverUtils;
import pt.dbservices.utilities.reports.Results;

import static pt.dbservices.utilities.driver.DriverUtils.navigateTo;
import static pt.dbservices.utilities.driver.DriverManager.getDriver;

public class LinkNavigationTests extends BaseTest {
    private Logger log = LogManager.getLogger(LinkNavigationTests.class.getName());
    private HomePage homePage ;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception {
        DriverUtils.launchBrowser();
        navigateTo(envProps.getProperty("uiBaseurl"));

        homePage = new HomePage(getDriver());
        Assert.assertTrue(homePage.isDisplay(),"Automation Practice Site - Home page is not displayed.");
        Results.pass("Automation Practice Site - Home page is displayed." );

    }

    @Test(description = "Verify WOMEN Tab  Link Navigations.")
    public void verify_WomenTab_LinkNavigationTest(){
        Results.setName("Link Navigation - WOMEN Tab");
        MainMenuLinksPage menuLinks = new MainMenuLinksPage(getDriver());
        menuLinks.clickTab_WOMEN();

        // click home icon to bring application to base state
        homePage.clickHomeIcon();
    }

}
