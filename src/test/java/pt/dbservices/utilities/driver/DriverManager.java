package pt.dbservices.utilities.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pt.dbservices.utilities.fileReaders.ReadProperties;

import java.util.HashMap;

import static pt.dbservices.utilities.fileReaders.JData.getDirectory;

public class DriverManager {

    public final static String HEADLESS = "headless";

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static  WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static WebDriver createDriver() throws Exception {
        String browser = System.getProperty("browser");
        if(browser == null) {
            browser = ReadProperties.getDefaultProperty("browser");
        }
        boolean headless =  "true".equals(ReadProperties.getProperty(HEADLESS));
        switch (browser.toUpperCase())
        {
            case "IE":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setHeadless(headless);

                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                String dwnload = getDirectory("downloads",false).toString();
                chromePrefs.put("download.default_directory", dwnload );
                options.setExperimentalOption("prefs", chromePrefs);
                return new ChromeDriver(options);
        }
    }

}
