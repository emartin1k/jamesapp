import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.URL;
import java.time.Duration;

public class AbstractTest {

    private static final String EMULATOR_UUID = "emulator-5554";
    private static final String APP_PATH = "/apps/James Rider_1.22.0.apk";
    private static final String APP_PACKAGE = "com.hdw.james.rider";
    AndroidDriver driver;

    @BeforeTest
    public void setup() {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid(EMULATOR_UUID); // Change according to the emulator uuid
        options.setApp(System.getProperty("user.dir") + APP_PATH); // Change According to app name and location
        options.setCapability("appium:adbExecTimeout", 60000); // Adding extra timeout due to sometimes hanging up during execution
        options.setAppWaitPackage(APP_PACKAGE);
        options.setAppWaitActivity("*");
        options.setAppWaitForLaunch(true);
        options.setNoReset(true);

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);  // Default local url for Appium Server
        } catch (Exception e) {
            System.out.println("Error starting Application - Stacktrace: " + e.getMessage());
            screenshot(driver);
            Assert.fail("Error starting Application");
        }

        System.out.println("---------- Starting Tests ----------");
    }

    @AfterTest
    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }

        System.out.println("---------- Tests Finished ----------");
    }

    public static void waitElementById(AndroidDriver driver, int timeout, String elementId) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id(elementId)));
        } catch (Exception e) {
            System.out.println("Element by Id not found: " + elementId + " - Stacktrace: " + e.getMessage());
            screenshot(driver);
            Assert.fail("Element by Id: " + elementId + " not present.");
        }
    }

    public static void waitElementByClassName(AndroidDriver driver, int timeout, String elementId) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className(elementId)));
        } catch (Exception e) {
            System.out.println("Element by ClassName not found: " + elementId + " - Stacktrace: " + e.getMessage());
            screenshot(driver);
            Assert.fail("Element by ClassName: " + elementId + " not present.");
        }
    }

    public static void waitElementByXpath(AndroidDriver driver, int timeout, String elementId) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(elementId)));
        } catch (Exception e) {
            System.out.println("Element by XPath not found: " + elementId + " - Stacktrace: " + e.getMessage());
            screenshot(driver);
            Assert.fail("Element by XPath: " + elementId + " not present.");
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] screenshot(AndroidDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
