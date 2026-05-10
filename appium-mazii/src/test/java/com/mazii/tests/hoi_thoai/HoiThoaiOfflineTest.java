package com.mazii.tests.hoi_thoai;

import com.mazii.base.BaseTest;
import com.mazii.screens.DichHoiThoaiScreen;
import com.mazii.utils.NetworkUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MAZ_HT_TC_027–028 — M_HT10: Offline / Error Handling (BR03)
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiOfflineTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    @AfterMethod(alwaysRun = true)
    public void restoreNetwork() {
        // Đảm bảo bật lại network sau mỗi test offline
        try {
            NetworkUtils.enableNetwork(driver);
        } catch (Exception ignored) {}
    }

    @Test(groups = {"mobile", "regression", "offline"})
    @Description("MAZ_HT_TC_027 — Offline trước khi tap Mic → toast lỗi, không thu âm")
    @Severity(SeverityLevel.BLOCKER)
    public void testOfflineBeforeMicTapShowsError() {
        NetworkUtils.disableNetwork(driver);

        hoiThoaiScreen.tapMicRight();

        Assert.assertFalse(
            hoiThoaiScreen.isMicRightRecording(),
            "Khi offline: tap Mic KHÔNG được bắt đầu thu âm"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isToastNetworkErrorDisplayed(),
            "Khi offline: phải hiển thị toast 'Vui lòng kết nối mạng để sử dụng Dịch hội thoại'"
        );
    }

    @Test(groups = {"mobile", "regression", "offline"})
    @Description("MAZ_HT_TC_028 — Mất mạng giữa chừng khi đang thu âm → dừng + thông báo")
    @Severity(SeverityLevel.BLOCKER)
    public void testNetworkDropDuringRecordingStopsAndShowsError() {
        hoiThoaiScreen.tapMicRight();
        Assert.assertTrue(hoiThoaiScreen.isMicRightRecording(),
            "Pre-condition: phải đang thu âm trước khi tắt mạng");

        NetworkUtils.disableNetwork(driver);

        Assert.assertFalse(
            hoiThoaiScreen.isMicRightRecording(),
            "Sau khi mất mạng: thu âm phải dừng lại"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isToastNetworkErrorDisplayed(),
            "Sau khi mất mạng giữa chừng: phải hiển thị toast thông báo lỗi mạng"
        );
    }
}
