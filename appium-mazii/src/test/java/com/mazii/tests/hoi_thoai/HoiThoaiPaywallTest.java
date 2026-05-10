package com.mazii.tests.hoi_thoai;

import com.mazii.base.BaseTest;
import com.mazii.screens.DichHoiThoaiScreen;
import com.mazii.screens.PaywallScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MAZ_HT_TC_002, MAZ_HT_TC_003 — M_HT02: Paywall / Access Control
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiPaywallTest extends BaseTest {

    private PaywallScreen paywallScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        paywallScreen = new PaywallScreen(driver);
    }

    @Test(groups = {"mobile", "regression", "paywall"})
    @Description("MAZ_HT_TC_002 — Guest truy cập tab Dịch hội thoại → Paywall ngay lập tức")
    @Severity(SeverityLevel.BLOCKER)
    public void testGuestSeesPaywallOnTabAccess() {
        // Guest: không đăng nhập hoặc token Guest
        // ⚠️ Đảm bảo driver khởi động với tài khoản Guest (xóa session / dùng noReset=false)
        dichScreen.goToDichHoiThoai();

        Assert.assertTrue(
            paywallScreen.isDisplayed(),
            "Guest phải thấy màn hình Paywall ngay khi mở tab Dịch hội thoại"
        );
    }

    @Test(groups = {"mobile", "regression", "paywall"})
    @Description("MAZ_HT_TC_003 — Tài khoản Thường tap Mic → Paywall")
    @Severity(SeverityLevel.BLOCKER)
    public void testStandardAccountSeesPaywallOnMicTap() {
        loginAsStandard();
        DichHoiThoaiScreen hoiThoaiScreen = dichScreen.goToDichHoiThoai();
        hoiThoaiScreen.tapMicRight();

        Assert.assertTrue(
            paywallScreen.isDisplayed(),
            "Tài khoản Thường tap Mic phải thấy Paywall — không được bắt đầu thu âm"
        );
    }
}
