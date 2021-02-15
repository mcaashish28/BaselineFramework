package pt.dbservices.utilities.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import pt.dbservices.utilities.reports.Results;

import static pt.dbservices.utilities.driver.DriverManager.getDriver;
import static pt.dbservices.utilities.driver.DriverManager.setWebDriver;

public class DriverUtils {

    public static void waitForPageLoad(){
        try {
            Thread.sleep(1000);
            // wait for DOM to become Ready state
            new WebDriverWait(getDriver(), 30).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        }
        catch (Exception ex){}
    }

    public static void launchBrowser() throws Exception {
        setWebDriver(DriverManager.createDriver());
        getDriver().manage().window().setSize(new Dimension(1900, 950));
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
    }

    public static void closeBrowser(){
        if(getDriver() != null)
            getDriver().quit();
        setWebDriver(null);
    }
    public static void navigateTo(String url) throws Exception {
        if(getDriver() == null)
            launchBrowser();
        waitForPageLoad();
        getDriver().get(url);
        waitForPageLoad();
        Results.info("Navigated to => "+url);
    }
    public static void refreshBrowser() {
        waitForPageLoad();
        getDriver().navigate().refresh();
        waitForPageLoad();
        Results.info("Refreshed the browser session.");
    }

}
