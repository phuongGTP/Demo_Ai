package com.mazii.drivers;

import com.mazii.config.AppConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumDriverFactory {

    public static AppiumDriver createDriver() {
        return createDriver(false);
    }

    public static AppiumDriver createDriver(boolean noAutoPermission) {
        try {
            URL serverUrl = new URL(AppConfig.getAppiumServerUrl());
            AppiumDriver driver;

            if (AppConfig.isAndroid()) {
                driver = new AndroidDriver(
                    serverUrl,
                    noAutoPermission
                        ? CapabilitiesManager.getAndroidCapabilitiesNoAutoPermission()
                        : CapabilitiesManager.getAndroidCapabilities()
                );
            } else {
                driver = new IOSDriver(serverUrl, CapabilitiesManager.getIOSCapabilities());
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppConfig.getImplicitWait()));
            return driver;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Appium server URL không hợp lệ: " + AppConfig.getAppiumServerUrl(), e);
        }
    }
}
