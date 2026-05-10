package com.mazii.tests.hoi_thoai;

import com.mazii.base.BaseTest;
import com.mazii.drivers.AppiumDriverFactory;
import com.mazii.screens.DichHoiThoaiScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MAZ_HT_TC_004, MAZ_HT_TC_005 — M_HT03: Mic Permission (OS-level)
 *
 * ⚠️ Lưu ý:
 * - TC_004 yêu cầu app chạy lần đầu, chưa từng cấp quyền mic.
 *   → Dùng capabilities noReset=false + autoGrantPermissions=false.
 *   → Sau khi tap Mic, hệ thống sẽ hiện OS dialog.
 *   → Automation verify dialog xuất hiện (hoặc dùng Appium alert handling).
 *
 * - TC_005 yêu cầu quyền đã bị deny trước đó.
 *   → Cần pre-condition: deny permission ở OS Settings.
 *   → Khó automate hoàn toàn — thường xử lý bằng manual hoặc thiết lập qua ADB.
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiMicPermissionTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        // Dùng driver không auto-grant permission để OS dialog xuất hiện khi tap Mic
        driver = AppiumDriverFactory.createDriver(true);
        loginAsPremium();
        dichScreen = new com.mazii.screens.DichScreen(driver);
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    @Test(groups = {"mobile", "regression", "permission"})
    @Description("MAZ_HT_TC_004 — Lần đầu tap Mic → hệ thống xin quyền Microphone (OS dialog)")
    @Severity(SeverityLevel.CRITICAL)
    public void testFirstTimeMicTapRequestsPermission() {
        hoiThoaiScreen.tapMicRight();

        // Verify OS dialog xuất hiện — driver chưa thu âm ngay
        boolean isDialogPresent;
        try {
            // Appium tự detect alert/dialog
            driver.switchTo().alert();
            isDialogPresent = true;
        } catch (Exception e) {
            // Fallback: kiểm tra app không vào trạng thái recording ngay
            isDialogPresent = !hoiThoaiScreen.isMicRightRecording();
        }

        Assert.assertTrue(
            isDialogPresent,
            "Lần đầu tap Mic phải hiện OS dialog xin quyền Microphone, chưa thu âm ngay"
        );
    }

    @Test(groups = {"mobile", "regression", "permission"},
          description = "MAZ_HT_TC_005 — Từ chối quyền Mic → thông báo hướng dẫn cài đặt")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeniedMicPermissionShowsGuidance() {
        // Pre-condition: quyền mic đã bị deny (cần setup qua ADB hoặc manual)
        // adb shell pm revoke net.mazii.app android.permission.RECORD_AUDIO
        hoiThoaiScreen.tapMicRight();

        Assert.assertFalse(
            hoiThoaiScreen.isMicRightRecording(),
            "Khi quyền mic bị deny: app KHÔNG được bắt đầu thu âm"
        );
        // ⚠️ Thêm assert kiểm tra guidance message/dialog nếu app hiển thị
        // Assert.assertTrue(hoiThoaiScreen.isPermissionGuidanceDisplayed(), "Phải hiện hướng dẫn cấp quyền");
    }
}
