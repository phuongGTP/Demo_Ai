package com.mazii.base;

import com.mazii.config.AppConfig;
import com.mazii.drivers.AppiumDriverFactory;
import com.mazii.screens.DichScreen;
import com.mazii.utils.ScreenshotUtil;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected AppiumDriver driver;
    protected DichScreen dichScreen;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = AppiumDriverFactory.createDriver();
        dichScreen = new DichScreen(driver);
        navigateToDichScreen();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    // Override trong test cụ thể nếu cần navigate khác
    protected void navigateToDichScreen() {
        // Mazii app mở ở màn chính → navigate đến tab Dịch
        // ⚠️ Điều chỉnh navigation flow theo app thực tế
    }

    // BaseTest với login Premium
    protected void loginAsPremium() {
        // ⚠️ Implement login flow nếu app yêu cầu
        // Ví dụ: LoginScreen loginScreen = new LoginScreen(driver);
        // loginScreen.login(AppConfig.getPremiumEmail(), AppConfig.getPremiumPassword());
    }

    protected void loginAsStandard() {
        // ⚠️ Implement login flow nếu app yêu cầu
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] captureScreenshot(String testName) {
        ScreenshotUtil.capture(driver, testName);
        return ((org.openqa.selenium.TakesScreenshot) driver)
            .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
    }
}
