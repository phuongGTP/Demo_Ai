package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import com.mazii.web.drivers.WebDriverFactory;
import com.mazii.web.pages.DichPage;
import com.mazii.web.pages.PaywallPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Dịch hội thoại - Web")
public class HoiThoaiPaywallTest extends BaseTest {

    @Test(groups = {"web", "smoke", "regression", "paywall"})
    @Description("WEB_HT_TC_002 — Guest click Mic → Paywall xuất hiện (guest thấy UI nhưng không dùng được)")
    @Severity(SeverityLevel.CRITICAL)
    public void testGuestOpenTabShowsPaywall() {
        // Tạo driver mới — không đăng nhập (Guest)
        driver.quit();
        driver = WebDriverFactory.createDriver();

        DichPage guestDichPage = new DichPage(driver);
        hoiThoaiPage = guestDichPage.goToDichHoiThoai();

        // Guest thấy UI, paywall chỉ xuất hiện khi thử dùng Mic
        hoiThoaiPage.clickMicJapanese();

        PaywallPage paywall = new PaywallPage(driver);
        Assert.assertTrue(
            paywall.isPaywallDisplayed(),
            "Guest click Mic phải thấy Paywall 'Bạn cần nâng cấp'"
        );
    }

    @Test(groups = {"web", "regression", "paywall"})
    @Description("WEB_HT_TC_003 — Tài khoản Thường click Mic → Paywall xuất hiện")
    @Severity(SeverityLevel.CRITICAL)
    public void testStandardUserMicTapShowsPaywall() {
        // Quit session Premium, tạo driver mới và login Standard
        driver.quit();
        driver = WebDriverFactory.createDriver();
        loginAsStandard();
        navigateToHoiThoai();

        hoiThoaiPage.clickMicJapanese();

        PaywallPage paywall = new PaywallPage(driver);
        Assert.assertTrue(
            paywall.isPaywallDisplayed(),
            "Tài khoản Thường click Mic phải thấy Paywall, không được bắt đầu thu âm"
        );
        Assert.assertFalse(
            hoiThoaiPage.isMicJapaneseRecording(),
            "Mic KHÔNG được ở trạng thái recording sau khi thấy Paywall"
        );
    }
}
