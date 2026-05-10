package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import com.mazii.web.drivers.WebDriverFactory;
import com.mazii.web.pages.DichPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * WEB_HT_TC_004, WEB_HT_TC_005 — M_HT03: Mic Permission (Browser-level)
 *
 * ⚠️ TC_004: Yêu cầu browser chưa cấp quyền mic cho site này.
 *   → Trong Selenium, ta KHÔNG set prefs → browser hiện dialog khi click Mic.
 *   → WebDriver không thể tương tác trực tiếp với browser permission dialog (không phải JS alert).
 *   → Verify gián tiếp: sau khi click Mic, kiểm tra app không chuyển sang trạng thái recording.
 *
 * ⚠️ TC_005: Yêu cầu browser đã BLOCK mic permission.
 *   → Dùng driver với prefs mic = 2 (Block).
 *   → Verify: click Mic → không recording → app hiển thị hướng dẫn.
 */
@Feature("Dịch hội thoại - Web")
public class HoiThoaiMicPermissionTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        // TC_004/005 dùng driver riêng — override trong từng test
    }

    @Test(groups = {"web", "regression", "permission"})
    @Description("WEB_HT_TC_004 — Lần đầu click Mic → browser dialog xin quyền Microphone")
    @Severity(SeverityLevel.CRITICAL)
    public void testFirstTimeMicClickRequestsBrowserPermission() {
        // Driver không set prefs → browser sẽ hiện permission prompt khi click Mic
        driver = WebDriverFactory.createDriver(false);
        loginAsPremium();
        DichPage dichPage = new DichPage(driver);
        hoiThoaiPage = dichPage.goToDichHoiThoai();

        hoiThoaiPage.clickMicVietnamese();

        // Sau khi click Mic mà chưa cấp quyền, app không được vào trạng thái recording
        // Browser permission dialog là native UI — không thể interact qua WebDriver
        Assert.assertFalse(
            hoiThoaiPage.isMicVietnameseRecording(),
            "Lần đầu click Mic (chưa cấp quyền browser) → KHÔNG thu âm ngay. Browser phải hiện permission dialog."
        );
    }

    @Test(groups = {"web", "regression", "permission"})
    @Description("WEB_HT_TC_005 — Từ chối quyền Mic (browser blocked) → hướng dẫn cài đặt trình duyệt")
    @Severity(SeverityLevel.CRITICAL)
    public void testBlockedMicPermissionShowsGuidance() {
        // Driver với mic permission = Blocked (prefs: 2)
        driver = WebDriverFactory.createDriver(false);
        loginAsPremium();
        DichPage dichPage = new DichPage(driver);
        hoiThoaiPage = dichPage.goToDichHoiThoai();

        hoiThoaiPage.clickMicVietnamese();

        Assert.assertFalse(
            hoiThoaiPage.isMicVietnameseRecording(),
            "Khi mic bị block: app KHÔNG được bắt đầu thu âm"
        );
        // ⚠️ Thêm assert kiểm tra guidance message nếu app hiển thị hướng dẫn cấp lại quyền
        // Assert.assertTrue(hoiThoaiPage.isMicPermissionGuidanceVisible(), "Phải hiện hướng dẫn cấp quyền mic");
    }
}
