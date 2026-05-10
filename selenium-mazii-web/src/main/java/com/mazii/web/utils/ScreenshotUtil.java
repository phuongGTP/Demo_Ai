package com.mazii.web.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "target/screenshots/";

    public static String capture(WebDriver driver, String testName) {
        String fileName = testName + "_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                + ".png";
        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);
            Path src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Path dest = dir.resolve(fileName);
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            return dest.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
