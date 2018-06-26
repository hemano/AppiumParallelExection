package com.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;

public class AppiumServer {

    private static AppiumDriverLocalService appiumDriverLocalService;

    private static AppiumDriverLocalService getAppiumDriverLocalService() {
        return appiumDriverLocalService;
    }


    /**
     * Start an Appium Server at a random port
     */
    public void startAppiumServer(){
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server on Localhost......");
        System.out.println(
                "**************************************************************************\n");

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();


        AppiumServiceBuilder builder =
                appiumServiceBuilder
                        .withLogFile(new File(
                                System.getProperty("user.dir")
                                        + "/appium_logs.txt"))
                        .usingAnyFreePort();
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();

        System.out.println(
                "**************************************************************************\n");
        System.out.println("Appium Server Started at......"
                + appiumDriverLocalService.getUrl());
        System.out.println(
                "**************************************************************************\n");

    }

    /**
     * Destroy running Appium server
     */
    public void destroyAppiumNode(){
        getAppiumDriverLocalService().stop();
        if (getAppiumDriverLocalService().isRunning()) {
            System.out.println("AppiumServer didn't shut... Trying to quit again....");
            getAppiumDriverLocalService().stop();
        }
    }

    public boolean isAppiumServerRunning(){
        return getAppiumDriverLocalService().isRunning();
    }

    public String getAppiumServerURL(){
        return getAppiumDriverLocalService().getUrl().toString();
    }
}
