package com.mazii.web.tests;

import com.mazii.web.base.BaseTest;
import com.mazii.web.pages.DichPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Visual Regression Tests — Compare UI screenshots with Figma design.
 *
 * Workflow:
 * 1. Run tests để capture screenshots từ app → backstop_data/bitmaps_test/
 * 2. Chạy `backstop test` để compare với reference images
 * 3. Review HTML report tại backstop_data/html_report/index.html
 *
 * Setup:
 * npm install --save-dev backstopjs puppeteer
 * backstop init
 * backstop reference  (tạo baseline từ URLs)
 * mvn test -Dtest=VisualRegressionTest
 * backstop test       (compare test screenshots)
 */
public class VisualRegressionTest extends BaseTest {

    private static final String SCREENSHOTS_DIR = "backstop_data/bitmaps_test";

    // Override setUp nếu cần test guest state, otherwise dùng parent setUp()
    // @Override
    // public void setUp() {
    //     // Leave blank để test guest state
    // }

    // ── Empty State (TC_001) ─────────────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualEmptyState() throws Exception {
        captureScreenshot("01_empty_state");
    }

    // ── Mic Recording State (TC_006) ─────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualMicRecordingState() throws Exception {
        hoiThoaiPage.clickMicJapanese();
        Thread.sleep(500);
        captureScreenshot("02_mic_recording_jp");
    }

    // ── Keyboard Mode (TC_031) ───────────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualKeyboardMode() throws Exception {
        hoiThoaiPage.clickKeyboardToggle();
        Thread.sleep(500);
        captureScreenshot("03_keyboard_mode");
    }

    // ── Delete Modal (TC_025) ────────────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualDeleteModal() throws Exception {
        seedBubbleData();
        hoiThoaiPage.hoverTrashButton();
        hoiThoaiPage.clickTrashButton();
        Thread.sleep(500);
        captureScreenshot("04_delete_modal");
    }

    // ── Bubble with Actions (TC_016) ─────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualBubbleActions() throws Exception {
        seedBubbleData();
        hoiThoaiPage.hoverFirstBubble();
        Thread.sleep(500);
        captureScreenshot("05_bubble_actions");
    }

    // ── Language Dropdown (TC_022) ───────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualLanguageDropdown() throws Exception {
        Thread.sleep(500);
        captureScreenshot("06_language_dropdowns");
    }

    // ── Offline Toast (TC_029) ───────────────────────────────────────
    @Test(groups = {"web", "visual", "regression"})
    public void testVisualOfflineToast() throws Exception {
        com.mazii.web.utils.BrowserUtils.setOffline(driver);
        hoiThoaiPage.clickMicJapanese();
        Thread.sleep(1000);
        captureScreenshot("07_offline_toast");
        com.mazii.web.utils.BrowserUtils.setOnline(driver);
    }

    // ── Helper Methods ───────────────────────────────────────────────

    /**
     * Capture full page screenshot để so sánh với Figma design.
     * Lưu vào backstop_data/bitmaps_test/ để BackstopJS so sánh.
     */
    private void captureScreenshot(String name) throws Exception {
        // Ensure directory exists
        Files.createDirectories(Paths.get(SCREENSHOTS_DIR));

        // Capture screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = SCREENSHOTS_DIR + "/" + name + ".png";
        Files.copy(screenshot.toPath(), Paths.get(filename),
            java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        System.out.println("✅ Screenshot saved: " + filename);
    }
}
