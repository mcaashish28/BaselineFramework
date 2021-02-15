package pt.dbservices.utilities.reports;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pt.dbservices.base.BaseTest;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static pt.dbservices.utilities.reports.ExtentReport.getTest;
import static pt.dbservices.utilities.driver.DriverManager.getDriver;


public class Results {

    public static void pass(String message,boolean isScreenshotRequired){
        if(isScreenshotRequired)
            getTest().log(LogStatus.PASS,message+getTest().addScreenCapture(Results.capture(getDriver())));
        else
            getTest().log(LogStatus.PASS,message);
        Reporter.log("PASS: "+message);
    }

    public static void pass(String message){
        getTest().log(LogStatus.PASS,message);
        Reporter.log("PASS: "+message);
    }
    public static void setName(String testName){
        ExtentTest test = getTest();
        test.getTest().setName(testName);
    }

    public static void info(String message){
        System.out.println("INFO : "+message);
        ExtentReport.getTest().log(LogStatus.INFO,message);
        Reporter.log("INFO: "+message);
    }

    public static void info(String message,boolean isScreenshotRequired){
        System.out.println("INFO : "+message);
        if(isScreenshotRequired)
            ExtentReport.getTest().log(LogStatus.INFO,message +
                    getTest().addScreenCapture(Results.capture(getDriver())));
        else
            ExtentReport.getTest().log(LogStatus.INFO,message);
        Reporter.log("INFO: "+message);
    }

    public static void fail(Throwable throwable,boolean isScreenshotRequired){
        getTest().log(LogStatus.FAIL, throwable);
        if(isScreenshotRequired)
            fail("Screenshot",true);
        Reporter.log("FAIL: "+throwable);
    }

    public static void fail(String message,boolean isScreenshotRequired){
        if(isScreenshotRequired)
            getTest().log(LogStatus.FAIL, message+"  :- <br /> <br />" +
                    getTest().addScreenCapture(Results.capture(getDriver())));
        Reporter.log("FAIL: "+message);
    }

    public static void fail(String message){
        getTest().log(LogStatus.FAIL, "Error Details :- \n" + message);
    }

    public static void warn(String message){
        getTest().log(LogStatus.WARNING, message);
        Reporter.log("WARNING: "+message);
    }

    public static void warn(String message,boolean isScreenshotRequired){
        if(isScreenshotRequired)
            getTest().log(LogStatus.WARNING, message+"  :- <br /> <br />" +
                    getTest().addScreenCapture(Results.capture(getDriver())));
        Reporter.log("WARNING: "+message);
    }

    public static void fatal(String message){
        getTest().log(LogStatus.FATAL, message);
        Reporter.log("FATAL: "+message);
    }

    public static void skip(String message){
        getTest().log(LogStatus.SKIP, message);
        Reporter.log("SKIP: "+message);
    }

    public static void skip(String message,boolean isScreenshotRequired){
        if(isScreenshotRequired)
            getTest().log(LogStatus.SKIP, message+"  :- <br /> <br />" +
                    getTest().addScreenCapture(Results.capture(getDriver())));
        Reporter.log("SKIP: "+message);
    }

    public static void error(String message){
        getTest().log(LogStatus.ERROR, message);
        Reporter.log("ERROR: "+message);
    }

    public static void error(String message,boolean isScreenshotRequired){
        if(isScreenshotRequired)
            getTest().log(LogStatus.ERROR, message+ getTest().addScreenCapture(Results.capture(getDriver())));
        Reporter.log("ERROR: "+message);
    }

    private static String capture(WebDriver driver) {
        return capture(driver, "Test_"+ generateRandomNumber());
    }

    public static String generateRandomNumber() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime() +
                ThreadLocalRandom.current().nextInt());
    }

    public static String capture(WebDriver driver, String screenShotName) {
        String screenshotPath = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy h-m-s");
        Date date = new Date();
        try {
//            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            Screenshot screenshot = new AShot().takeScreenshot(driver);
            File destinationFile = new File(BaseTest.reportPath + "/ErrorScreenshots/" + screenShotName +
                    dateFormat.format(date) + ".png");

            ImageIO.write(screenshot.getImage(), "PNG", destinationFile);
            String[] relativePath = destinationFile.toString().split("ExtentReports");

            if (System.getProperty("os.name").contains("Windows"))
                screenshotPath = ".\\" + relativePath[1];
            else
                screenshotPath = ".//" + relativePath[1];
        } catch (Throwable e) {
            //if it fails to take screenshot then this block will execute
            System.out.println("Failure to take screenshot " + e);
        }
        return screenshotPath;
    }

    public static String GetReportsFolderPath() {
        return String.valueOf(new File(System.getProperty("user.dir") + "/ExtentReports/"));
    }
    public static void CreateScreenshotFolder(String reportPath) {
        File dir = new File(reportPath + "/ErrorScreenshots/");
        if (!dir.exists()) dir.mkdirs();
    }

}
