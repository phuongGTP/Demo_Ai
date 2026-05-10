package com.mazii.config;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String get(String key) {
        String value = System.getenv(key);
        if (value != null) return value;
        return dotenv.get(key, "");
    }

    public static String getPlatform() {
        return get("PLATFORM").toLowerCase();
    }

    public static boolean isAndroid() {
        return "android".equals(getPlatform());
    }

    public static boolean isIOS() {
        return "ios".equals(getPlatform());
    }

    public static String getAppiumServerUrl() {
        return get("APPIUM_SERVER_URL");
    }

    public static int getExplicitWait() {
        String val = get("EXPLICIT_WAIT");
        return val.isEmpty() ? 15 : Integer.parseInt(val);
    }

    public static int getImplicitWait() {
        String val = get("IMPLICIT_WAIT");
        return val.isEmpty() ? 10 : Integer.parseInt(val);
    }

    public static String getPremiumEmail() {
        return get("ACCOUNT_PREMIUM_EMAIL");
    }

    public static String getPremiumPassword() {
        return get("ACCOUNT_PREMIUM_PASSWORD");
    }

    public static String getStandardEmail() {
        return get("ACCOUNT_STANDARD_EMAIL");
    }

    public static String getStandardPassword() {
        return get("ACCOUNT_STANDARD_PASSWORD");
    }
}
