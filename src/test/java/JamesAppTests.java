import io.appium.java_client.AppiumBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

public class JamesAppTests extends AbstractTest {
    // Note: Test names starting with the prefix testXX_ are designed to easily establish the execution order.
    // Current tests are designed to run in the established order and have the necessary navigation steps to do so, as an improvement this can be removed from the tests logic and can be added to an AfterTest section

    @Test(testName = "ChangeName", description = "Successful User Name Change")
    public void test01_ChangeName() {
        // To start running this tests, a previous login is required since this suite does not cover succesful login

        waitElementById(driver, 3, "com.hdw.james.rider:id/MAIN_MENU_ID");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/MAIN_MENU_ID")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/profileName");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/profileName")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/firstNameInput");
        var newTime = System.currentTimeMillis() / 1000L;
        var firstNameInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/firstNameInput")).getFirst();
        firstNameInput.click();
        firstNameInput.sendKeys("Esteban-" + newTime);

        var lastNameInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/lastNameInput")).getFirst();
        lastNameInput.click();
        lastNameInput.sendKeys("Corona-" + newTime);

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/profileName");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/profileName")).getFirst().click();

        waitElementById(driver, 2, "com.hdw.james.rider:id/snackbar_text");
        var updatesOk = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/snackbar_text")).getFirst().getText();
        Assert.assertEquals(updatesOk, "Profile updated successfully");

        waitElementById(driver, 3, "com.hdw.james.rider:id/firstNameInput");
        Assert.assertEquals(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/firstNameInput")).getFirst().getText(), "Esteban-" + newTime);
        Assert.assertEquals(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/lastNameInput")).getFirst().getText(), "Corona-" + newTime);

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();
        waitElementById(driver, 3, "com.hdw.james.rider:id/profileName");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();
    }

    @Test(testName = "ChangePassword", description = "Successful User Password Change and Restore")
    public void test02_ChangePassword() {
        // Note: This test was designed to be able to be executed over and over since the test itself changes the pass to a new and back to the old value and does all the navigation in the middle

        waitElementById(driver, 3, "com.hdw.james.rider:id/MAIN_MENU_ID");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/MAIN_MENU_ID")).getFirst().click();

        waitElementByXpath(driver, 3, "//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"SETTINGS\"]");
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"SETTINGS\"]")).getFirst().click();

        waitElementByXpath(driver, 3, "//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"ACCOUNT\"]");
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"ACCOUNT\"]")).getFirst().click();

        // Set new pass
        waitElementByXpath(driver, 3, "//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"CHANGE PASSWORD\"]");
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"CHANGE PASSWORD\"]")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/currentPasswordInput");
        var currPass = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/currentPasswordInput")).getFirst();
        currPass.click();
        currPass.sendKeys("esteban1234");

        var newPass = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/newPasswordInput")).getFirst();
        newPass.click();
        newPass.sendKeys("esteban5678");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();
        waitElementById(driver, 2, "com.hdw.james.rider:id/snackbar_text");
        Assert.assertEquals(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/snackbar_text")).getFirst().getText(), "Password updated successfully");

        // return to the old one
        waitElementByXpath(driver, 3, "//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"CHANGE PASSWORD\"]");
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"CHANGE PASSWORD\"]")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/currentPasswordInput");
        var currPassRestore = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/currentPasswordInput")).getFirst();
        currPassRestore.click();
        currPassRestore.sendKeys("esteban5678");

        var newPassRestore = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/newPasswordInput")).getFirst();
        newPassRestore.click();
        newPassRestore.sendKeys("esteban1234");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();
        waitElementById(driver, 2, "com.hdw.james.rider:id/snackbar_text");
        Assert.assertEquals(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/snackbar_text")).getFirst().getText(), "Password updated successfully");

        driver.findElements(AppiumBy.accessibilityId("Navigate up")).getFirst().click();
        waitElementByXpath(driver, 3, "//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"ACCOUNT\"]");
        driver.findElements(AppiumBy.accessibilityId("Navigate up")).getFirst().click();
        waitElementById(driver, 3, "com.hdw.james.rider:id/profileName");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/DEFAULT_TEXT_ACTION_MENU_ID")).getFirst().click();
    }

    @Test(testName = "LogoutOk", description = "Successful Logout")
    public void test03_LogoutOk() {
        waitElementById(driver, 3, "com.hdw.james.rider:id/MAIN_MENU_ID");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/MAIN_MENU_ID")).getFirst().click();

        waitElementByClassName(driver, 3, "android.widget.TextView");
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.hdw.james.rider:id/title\" and @text=\"SIGN OUT\"]")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/input");

        var getStartedMessage = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/title")).getFirst().getText();
        Assert.assertEquals(getStartedMessage, "Enter your phone number");
    }

    @Test(testName = "LoginWrongOtp", description = "Login with wrong OTP")
    @Ignore
    public void test04_LoginWrongOtp() {
        // Note: This test was ignored since the message at the end depending on how many attempts or running the suite we do, it will show one or other message

        waitElementById(driver, 2, "com.hdw.james.rider:id/input");

        var phoneInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/input")).getFirst();
        phoneInput.click();
        phoneInput.clear();
        phoneInput.sendKeys("2013514000");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/spinner")).getFirst().click();
        waitElementByClassName(driver, 2, "android.widget.TextView");
        var areaCodeList = driver.findElements(AppiumBy.className("android.widget.TextView"));
        var usaAreaCode = areaCodeList.get(1);
        usaAreaCode.click();

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/continueButton")).getFirst().click();

        waitElementByClassName(driver, 2, "android.widget.EditText");
        driver.findElements(AppiumBy.className("android.widget.EditText")).forEach(codeInput -> codeInput.sendKeys("0"));

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/continueButton")).getFirst().click();

        waitElementById(driver, 2, "com.hdw.james.rider:id/snackbar_text");
        var errorMessage = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/snackbar_text")).getFirst().getText();
        Assert.assertEquals("Invalid credentials", errorMessage);

        driver.findElements(AppiumBy.accessibilityId("Navigate up")).getFirst().click();
    }

    @Test(testName = "LoginOk", description = "Successful Login")
    @Ignore
    public void test05_LoginOk() {
        // Note: This test is ignored due to having issues with displaying the login page

        waitElementById(driver, 5, "com.hdw.james.rider:id/getStartedButton");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/getStartedButton")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/button");

        var emailInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/emailInput")).getFirst();
        emailInput.click();
        emailInput.sendKeys("esteban.d.martin@hotmail.com");

        var passwordInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/passwordInput")).getFirst();
        passwordInput.click();
        passwordInput.sendKeys("esteban1234");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/button")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/permissionsLocationButton");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/permissionsLocationButton")).getFirst().click();

        waitElementById(driver, 3, "com.android.permissioncontroller:id/permission_allow_one_time_button");
        driver.findElements(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_one_time_button")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/permissionsNotificationButton");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/permissionsNotificationButton")).getFirst().click();

        waitElementById(driver, 3, "com.android.permissioncontroller:id/permission_allow_button");
        driver.findElements(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/permissionsContinueButton");
        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/permissionsContinueButton")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/MAIN_MENU_ID");
        Assert.assertTrue(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/MAIN_MENU_ID")).getFirst().isDisplayed(), "Session correctly started");
    }

    @Test(testName = "LoginFailWithScreenshot", description = "Attempt to Login with failure to take the Screenshot")
    public void test06_LoginFailWithScreenshot() {
        Allure.addAttachment("This is a failing test with screenshot sample", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        waitElementById(driver, 2, "com.hdw.james.rider:id/input");

        var phoneInput = driver.findElements(AppiumBy.id("com.hdw.james.rider:id/input")).getFirst();
        phoneInput.click();
        phoneInput.clear();
        phoneInput.sendKeys("2013514000");

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/spinner")).getFirst().click();
        waitElementByClassName(driver, 2, "android.widget.TextView");
        var areaCodeList = driver.findElements(AppiumBy.className("android.widget.TextView"));
        var usaAreaCode = areaCodeList.get(1);
        usaAreaCode.click();

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/continueButton")).getFirst().click();

        waitElementByClassName(driver, 2, "android.widget.EditText");
        driver.findElements(AppiumBy.className("android.widget.EditText")).forEach(codeInput -> codeInput.sendKeys("0"));

        driver.findElements(AppiumBy.id("com.hdw.james.rider:id/continueButton")).getFirst().click();

        waitElementById(driver, 3, "com.hdw.james.rider:id/MAIN_MENU_ID");
        Assert.assertTrue(driver.findElements(AppiumBy.id("com.hdw.james.rider:id/MAIN_MENU_ID")).getFirst().isDisplayed(), "Session correctly started");
    }
}
