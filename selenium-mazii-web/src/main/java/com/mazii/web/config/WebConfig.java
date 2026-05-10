package com.mazii.web.config;

import io.github.cdimascio.dotenv.Dotenv;

public class WebConfig {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String get(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            value = dotenv.get(key, "");
        }
        return value;
    }

    public static String getBaseUrl() {
        return get("BASE_URL");
    }

    public static String getBrowser() {
        return get("BROWSER").isEmpty() ? "chrome" : get("BROWSER").toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("HEADLESS"));
    }

    public static int getBrowserWidth() {
        String w = get("BROWSER_WIDTH");
        return w.isEmpty() ? 1440 : Integer.parseInt(w);
    }

    public static int getBrowserHeight() {
        String h = get("BROWSER_HEIGHT");
        return h.isEmpty() ? 900 : Integer.parseInt(h);
    }

    public static int getImplicitWait() {
        String val = get("IMPLICIT_WAIT");
        return val.isEmpty() ? 10 : Integer.parseInt(val);
    }

    public static int getExplicitWait() {
        String val = get("EXPLICIT_WAIT");
        return val.isEmpty() ? 15 : Integer.parseInt(val);
    }

    public static int getPageLoadTimeout() {
        String val = get("PAGE_LOAD_TIMEOUT");
        return val.isEmpty() ? 30 : Integer.parseInt(val);
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
