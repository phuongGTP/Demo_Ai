package com.mazii.utils;

import com.mazii.config.AppConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.AppiumDriver;

public class NetworkUtils {

    // Tắt WiFi + Mobile Data (Android)
    public static void disableNetwork(AppiumDriver driver) {
        if (AppConfig.isAndroid()) {
            ((AndroidDriver) driver).setConnection(
                new ConnectionState(ConnectionState.AIRPLANE_MODE_MASK)
            );
        }
        // iOS: dùng airplane mode qua Settings app hoặc proxy configuration
    }

    // Bật lại network (Android)
    public static void enableNetwork(AppiumDriver driver) {
        if (AppConfig.isAndroid()) {
            ((AndroidDriver) driver).setConnection(
                new ConnectionState(ConnectionState.WIFI_MASK | ConnectionState.DATA_MASK)
            );
        }
    }

    public static boolean isNetworkEnabled(AppiumDriver driver) {
        if (AppConfig.isAndroid()) {
            ConnectionState state = ((AndroidDriver) driver).getConnection();
            return state.isWiFiEnabled() || state.isDataEnabled();
        }
        return true;
    }
}
