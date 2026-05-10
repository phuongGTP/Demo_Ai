package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import com.mazii.web.utils.BrowserUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * WEB_HT_TC_029–030 — M_HT10: Offline / Error Handling
 *
 * Dùng Chrome DevTools Protocol (CDP) để bật/tắt offline mode.
 * Yêu cầu ChromeDriver — không hoạt động với Firefox/Edge.
 */
@Feature("Dịch hội thoại - Web")
public class HoiThoaiOfflineTest extends BaseTest {

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown(ITestResult result) {
        // Luôn khôi phục network trước khi quit driver
        if (driver != null) {
            try {
                BrowserUtils.setOnline(driver);
            } catch (Exception ignored) {}
        }
        super.tearDown(result);
    }

    @Test(groups = {"web", "smoke", "regression", "offline"})
    @Description("WEB_HT_TC_029 — Offline trước khi click Mic → toast lỗi, không thu âm")
    @Severity(SeverityLevel.CRITICAL)
    public void testOfflineBeforeMicClickShowsErrorToast() {
        BrowserUtils.setOffline(driver);

        hoiThoaiPage.clickMicJapanese();

        Assert.assertFalse(
            hoiThoaiPage.isMicJapaneseRecording(),
            "Offline: Mic KHÔNG được chuyển sang trạng thái recording"
        );
        Assert.assertTrue(
            hoiThoaiPage.isNetworkErrorToastVisible(),
            "Offline: phải hiện toast 'Vui lòng kết nối mạng'"
        );
    }

    @Test(groups = {"web", "regression", "offline"})
    @Description("WEB_HT_TC_030 — Mất mạng giữa chừng khi đang thu âm → dừng + thông báo")
    @Severity(SeverityLevel.CRITICAL)
    public void testNetworkDropDuringRecordingStopsAndNotifies() {
        hoiThoaiPage.clickMicJapanese();
        Assert.assertTrue(
            hoiThoaiPage.isMicJapaneseRecording(),
            "Pre-condition: phải đang thu âm trước khi mất mạng"
        );

        // Bật offline trong lúc đang thu
        BrowserUtils.setOffline(driver);

        Assert.assertFalse(
            hoiThoaiPage.isMicJapaneseRecording(),
            "Sau khi mất mạng: thu âm phải dừng lại"
        );
        Assert.assertTrue(
            hoiThoaiPage.isNetworkErrorToastVisible(),
            "Sau khi mất mạng: phải hiện thông báo mất kết nối"
        );
    }
}
