package com.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import java.net.URL;

public class AppiumAndroidBrowserTest {

    private AppiumDriver driver;
    private static AppiumServer server;

    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "chromeDriverPort", "chromeDriverExecPath"})
    public void setUp(String platform, String udid,  String chromeDriverPort, @Optional String chromeDriverExecPath) throws Exception {

        String platformName = platform.split("-")[0];
        String platformVersion = platform.split("-")[1];

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability("chromeDriverPort", Integer.valueOf(chromeDriverPort));

        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverExecPath);
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");

        driver = new AppiumDriver(new URL(server.getAppiumServerURL()), capabilities);
    }

    @Test
    public void testcase_001() throws Exception {
        driver.get("http://www.google.com");

        WebElement keyword = driver.findElementByName("q");
        keyword.sendKeys("appium");
        driver.findElement(By.xpath("//button[@type='button' and @aria-label='Google Search']")).click();
        Thread.sleep(5000);
    }


    @AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        if(driver != null){
            driver.quit();
        }
    }

    @BeforeSuite
    public synchronized void startServer() throws InterruptedException {
        server = new AppiumServer();
        server.startAppiumServer();
    }

    @AfterSuite
    public void stopServer(){
        server.destroyAppiumNode();
        System.out.println("Appium server has stopped");
    }
}