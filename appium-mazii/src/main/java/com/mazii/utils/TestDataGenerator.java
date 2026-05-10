package com.mazii.utils;

import java.time.Instant;

public class TestDataGenerator {

    public static String timestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public static String generateEmail(String prefix) {
        return "auto_" + prefix + "_" + timestamp() + "@mazii.test";
    }

    public static String generateTag(String testName) {
        return "auto_" + testName + "_" + timestamp();
    }
}
