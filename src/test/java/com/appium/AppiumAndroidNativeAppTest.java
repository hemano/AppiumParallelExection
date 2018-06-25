package com.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppiumAndroidNativeAppTest {

    private AppiumDriver driver;
    private static AppiumServer server;



    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "systemPort"})
    public void setUp(String platform, String udid, String systemPort) throws Exception {


        String platformName = platform.split("-")[0];
        String platformVersion = platform.split("-")[1];

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);


        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.valueOf(systemPort));
        capabilities.setCapability(MobileCapabilityType.APP, "/Users/hemantojha/JavaProjects/appium_projects/kobitonpoc/apks/wordpress.apk");
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.wordpress.android");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.wordpress.android.ui.accounts.SignInActivity");


        driver = new AppiumDriver(new URL(server.getAppiumServerURL()), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testcase_001() throws Exception {
        driver.findElementById("nux_username").sendKeys("invalid username");
        driver.findElementById("nux_sign_in_button").click();

        waitUntilClickable(By.id("nux_password")).
                sendKeys("password");

        driver.hideKeyboard();

        waitUntilClickable(By.id("nux_sign_in_button")).click();

        Thread.sleep(5000);
    }


    @AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeSuite
    public void startServer() throws InterruptedException {
        server = new AppiumServer();
        server.startAppiumServer();
    }

    @AfterSuite
    public void stopServer() {
        server.destroyAppiumNode();
    }

    public WebElement waitUntilClickable(By element) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

}
