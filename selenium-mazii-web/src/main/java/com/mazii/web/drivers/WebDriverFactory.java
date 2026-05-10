package com.mazii.web.drivers;

import com.mazii.web.config.WebConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    /**
     * Tạo WebDriver với mic permission được cấp tự động.
     * Dùng cho hầu hết test cases.
     */
    public static WebDriver createDriver() {
        return createDriver(true);
    }

    /**
     * @param grantMicPermission true = mic auto-allowed, false = mic blocked (dùng cho TC_005)
     */
    public static WebDriver createDriver(boolean grantMicPermission) {
        String browser = WebConfig.getBrowser();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                driver = createFirefoxDriver(grantMicPermission);
                break;
            case "edge":
                driver = createEdgeDriver(grantMicPermission);
                break;
            default:
                driver = createChromeDriver(grantMicPermission);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WebConfig.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WebConfig.getPageLoadTimeout()));
        driver.manage().window().setSize(new Dimension(WebConfig.getBrowserWidth(), WebConfig.getBrowserHeight()));
        return driver;
    }

    private static ChromeDriver createChromeDriver(boolean grantMicPermission) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        // 1 = Allow, 2 = Block
        prefs.put("profile.default_content_setting_values.media_stream_mic",
                grantMicPermission ? 1 : 2);
        // Block push notification prompt (prevents "Bật thông báo" modal)
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        if (WebConfig.isHeadless()) {
            options.addArguments("--headless=new");
        }
        // Disable browser-level notification permission UI entirely
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                "--disable-notifications");

        return new ChromeDriver(options);
    }

    private static FirefoxDriver createFirefoxDriver(boolean grantMicPermission) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (grantMicPermission) {
            options.addPreference("permissions.default.microphone", 1);
        } else {
            options.addPreference("permissions.default.microphone", 2);
        }

        if (WebConfig.isHeadless()) {
            options.addArguments("--headless");
        }
        return new FirefoxDriver(options);
    }

    private static EdgeDriver createEdgeDriver(boolean grantMicPermission) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_mic",
                grantMicPermission ? 1 : 2);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");

        if (WebConfig.isHeadless()) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }
}
