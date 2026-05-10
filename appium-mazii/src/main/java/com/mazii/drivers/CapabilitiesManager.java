package com.mazii.drivers;

import com.mazii.config.AppConfig;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.Capabilities;

public class CapabilitiesManager {

    public static Capabilities getAndroidCapabilities() {
        UiAutomator2Options options = buildAndroidOptions();
        options.setCapability("autoGrantPermissions", true);
        options.setCapability("noReset", true);
        return options;
    }

    public static Capabilities getIOSCapabilities() {
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(AppConfig.get("IOS_DEVICE_NAME"));
        options.setPlatformVersion(AppConfig.get("IOS_PLATFORM_VERSION"));
        options.setBundleId(AppConfig.get("IOS_BUNDLE_ID"));

        String udid = AppConfig.get("IOS_UDID");
        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }

        String appPath = AppConfig.get("IOS_APP_PATH");
        if (!appPath.isEmpty()) {
            options.setApp(System.getProperty("user.dir") + "/" + appPath);
        }

        options.setCapability("noReset", true);
        options.setCapability("newCommandTimeout", 120);
        return options;
    }

    // Capabilities với autoGrantPermissions=false để test permission dialog
    public static Capabilities getAndroidCapabilitiesNoAutoPermission() {
        UiAutomator2Options options = buildAndroidOptions();
        options.setCapability("autoGrantPermissions", false);
        options.setCapability("noReset", false);
        return options;
    }

    private static UiAutomator2Options buildAndroidOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(AppConfig.get("ANDROID_DEVICE_NAME"));
        options.setPlatformVersion(AppConfig.get("ANDROID_PLATFORM_VERSION"));
        options.setAppPackage(AppConfig.get("ANDROID_APP_PACKAGE"));
        options.setAppActivity(AppConfig.get("ANDROID_APP_ACTIVITY"));
        String appPath = AppConfig.get("ANDROID_APP_PATH");
        if (!appPath.isEmpty()) {
            options.setApp(System.getProperty("user.dir") + "/" + appPath);
        }
        options.setCapability("newCommandTimeout", 120);
        return options;
    }
}
