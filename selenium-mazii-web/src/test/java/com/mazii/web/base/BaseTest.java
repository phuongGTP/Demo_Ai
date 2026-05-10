package com.mazii.web.base;

import com.mazii.web.config.WebConfig;
import com.mazii.web.drivers.WebDriverFactory;
import com.mazii.web.pages.DichPage;
import com.mazii.web.pages.DichHoiThoaiPage;
import com.mazii.web.utils.ScreenshotUtil;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected DichPage dichPage;
    protected DichHoiThoaiPage hoiThoaiPage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        loginAsPremium();
        navigateToHoiThoai();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateToHoiThoai() {
        dichPage = new DichPage(driver);
        hoiThoaiPage = dichPage.goToDichHoiThoai();
        dismissModalIfPresent();
    }

    /**
     * Dismiss tất cả popup/modal đang hiện (notification, cookie, intro) bằng JS.
     * Gọi sau mỗi lần navigate để đảm bảo không có modal che phủ button.
     */
    protected void dismissModalIfPresent() {
        try {
            // Chờ modal xuất hiện (tối đa 4s)
            new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal.show")));

            // Xóa modal + block Notification API để Angular không mở lại
            ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('.modal.show, .modal-backdrop').forEach(el => el.remove());" +
                "document.body.classList.remove('modal-open');" +
                "document.body.style.overflow = '';" +
                "document.body.style.paddingRight = '';" +
                "if (window.Notification) { Notification.requestPermission = function() { return Promise.resolve('denied'); }; }");

            // Chờ DOM cập nhật
            new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".modal.show")));
        } catch (Exception ignored) {
            // Không có modal — tiếp tục bình thường
        }
    }

    /**
     * Login với tài khoản Premium.
     * Flow: /vi-VN → click "Đăng nhập" → fill email/password → submit → wait home.
     */
    protected void loginAsPremium() {
        login(WebConfig.getPremiumEmail(), WebConfig.getPremiumPassword());
    }

    protected void loginAsStandard() {
        login(WebConfig.getStandardEmail(), WebConfig.getStandardPassword());
    }

    private void login(String email, String password) {
        WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(WebConfig.getBaseUrl() + "/vi-VN");

        // Click nút Đăng nhập
        loginWait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-primary"))).click();

        // Điền email
        loginWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email"))).sendKeys(email);

        // Điền password
        driver.findElement(By.id("password")).sendKeys(password);

        // Click nút Đăng nhập trong form
        driver.findElement(By.cssSelector("button.btn-login.btn-primary")).click();

        // Chờ login xong (URL về home hoặc không còn login page)
        loginWait.until(d -> !d.getCurrentUrl().contains("/user/login"));
    }

    protected void logout() {
        driver.get(WebConfig.getBaseUrl() + "/vi-VN");
        // Implement logout nếu cần cho specific test
    }

    /**
     * Seed bubbles qua keyboard mode để dùng cho bubble interaction tests.
     * Creates 1 bubble với Japanese text.
     */
    protected void seedBubbleData() {
        seedBubbleData(1);
    }

    /**
     * Seed multiple bubbles qua keyboard mode.
     * @param count số lượng bubble cần tạo
     */
    protected void seedBubbleData(int count) {
        if (hoiThoaiPage.getBubbleCount() > 0) return; // Already has bubbles

        hoiThoaiPage.clickKeyboardToggle();

        for (int i = 0; i < count; i++) {
            int countBefore = hoiThoaiPage.getBubbleCount();
            String text = i == 0 ? "おはようございます" : "Bubble " + (i + 1);
            hoiThoaiPage.typeInJapaneseInput(text);
            hoiThoaiPage.clickSendJapanese();
            hoiThoaiPage.waitForNewBubble(countBefore);
        }

        // Switch back to mic mode
        hoiThoaiPage.clickKeyboardToggle();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot(String testName) {
        ScreenshotUtil.capture(driver, testName);
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
