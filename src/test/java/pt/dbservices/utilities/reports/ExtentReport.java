package pt.dbservices.utilities.reports;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.Listeners;
import pt.dbservices.base.BaseTest;
import pt.dbservices.utilities.fileReaders.ReadProperties;
import pt.dbservices.utilities.listeners.TestListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static pt.dbservices.utilities.fileReaders.ReadProperties.getProperty;

@Listeners(TestListener.class)
public class ExtentReport {
    private static ExtentReports extent;
    static Map extentTestMap = new HashMap();
    static ExtentReports extentReport;
    public static Properties envProps;

    static {
        try {
            extentReport = ExtentReport.getReporter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExtentReports getReporter() throws IOException {
        if (extent == null) {
            createReporter(BaseTest.reportPath);
        }
        return extent;
    }

    public synchronized static ExtentReports createReporter(String reportPath) throws IOException {
        if (extent == null) {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                extent = new ExtentReports(reportPath + "\\ExtentReportResults.html", true);
                extent.loadConfig(new File(System.getProperty("user.dir")+"\\configuration\\extent-config.xml"));
            }
            else {
                extent = new ExtentReports(reportPath + "/ExtentReportResults.html", true);
                extent.loadConfig(new File(System.getProperty("user.dir")+"/configuration/extent-config.xml"));
            }
        }

        envProps = ReadProperties.getEnvProperties(System.getProperty("env"));

        if(envProps.getProperty("release_name") != null)
            extent.addSystemInfo("Release",envProps.getProperty("release_name"));
        if(getProperty("env") != null)
            extent.addSystemInfo("Environment",getProperty("env"));
        if(getProperty("browser") != null)
            extent.addSystemInfo("Browser",getProperty("browser"));
        if(getProperty("locale") != null)
            extent.addSystemInfo("Language",getProperty("locale"));
        if(getProperty("headless") != null)
            extent.addSystemInfo("Headless",getProperty("headless"));

        return extent;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extentReport.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
        extentReport.flush();
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extentReport.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

}
