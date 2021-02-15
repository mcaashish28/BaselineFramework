package pt.dbservices.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pt.dbservices.utilities.driver.DriverUtils;
import pt.dbservices.utilities.fileReaders.ReadProperties;
import pt.dbservices.utilities.reports.ExtentReport;
import pt.dbservices.utilities.reports.Results;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static pt.dbservices.apiAutomation.utils.RequestManager.deleteRequestFromThreadLocal;
import static pt.dbservices.utilities.driver.DriverUtils.launchBrowser;
import static pt.dbservices.utilities.fileReaders.JData.getDirectory;
import static pt.dbservices.utilities.reports.ExtentReport.startTest;
import static pt.dbservices.utilities.reports.ExtentReport.getTest;
import static pt.dbservices.utilities.reports.ExtentReport.endTest;

public class BaseTest {
    public static Properties envProps;
    private Logger log = LogManager.getLogger(BaseTest.class.getName());
    public static String reportPath;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws IOException {
        reportPath = Results.GetReportsFolderPath();
        loadEnvProperties();
        ExtentReport.createReporter(reportPath);
        getDirectory("downloads",true);
        Results.CreateScreenshotFolder(reportPath);

    }

    private void loadEnvProperties() throws IOException {
        envProps = ReadProperties.getEnvProperties(System.getProperty("env"));
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult iTestResult, ITestContext iTestContext) throws InterruptedException {
        startTest(iTestResult.getMethod().getMethodName(),iTestResult.getMethod().getDescription());
        getTest().assignCategory(iTestContext.getName());
        randomWaitToStartTest();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult  iTestResult) throws Exception {
        if (iTestResult.getStatus() == ITestResult.SUCCESS){}
        else if (iTestResult.getStatus() == ITestResult.FAILURE)
            Results.fail(iTestResult.getThrowable(), true);
        else if(iTestResult.getStatus() == ITestResult.SKIP || iTestResult.getStatus() == ITestResult.CREATED)
            Results.skip("Test Skipped -> " + iTestResult.getThrowable(),true);
        else
            Results.fatal(String.valueOf(iTestResult.getThrowable()));
        cleanupDriver();
        endTest();
    }

    private static void randomWaitToStartTest() throws InterruptedException {
        int timeToWaitInSec = ThreadLocalRandom.current().nextInt(0, 10);
        Results.info("Random wait to start the test => "+ timeToWaitInSec + " seconds.");
        Thread.sleep(timeToWaitInSec*1000);
    }

    private void cleanupDriver() throws Exception {
        deleteRequestFromThreadLocal();
        DriverUtils.closeBrowser();
    }


}
