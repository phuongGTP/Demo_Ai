package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Dịch hội thoại - Web")
public class HoiThoaiKeyboardModeTest extends BaseTest {

    @Test(groups = {"web", "smoke", "regression", "keyboard"})
    @Description("WEB_HT_TC_031 — Click keyboard toggle → UI chuyển sang text input, mic ẩn hoặc thay đổi")
    @Severity(SeverityLevel.CRITICAL)
    public void testSwitchToKeyboardModeShowsInput() {
        Assert.assertTrue(hoiThoaiPage.isInMicMode(), "Default phải là mic mode");

        hoiThoaiPage.clickKeyboardToggle();

        Assert.assertTrue(
            hoiThoaiPage.isInKeyboardMode(),
            "Sau khi click keyboard toggle: text input phải xuất hiện"
        );
    }

    @Test(groups = {"web", "smoke", "regression", "keyboard"})
    @Description("WEB_HT_TC_032 — Nhập text + Click Gửi → Bubble xuất hiện")
    @Severity(SeverityLevel.CRITICAL)
    public void testTypeTextAndSendCreatesBubble() {
        hoiThoaiPage.clickKeyboardToggle();
        Assert.assertTrue(hoiThoaiPage.isInKeyboardMode(), "Phải ở keyboard mode");

        int countBefore = hoiThoaiPage.getBubbleCount();
        hoiThoaiPage.typeInVietnameseInput("Xin chào, bạn khỏe không?");
        hoiThoaiPage.clickSendVietnamese();

        Assert.assertTrue(
            hoiThoaiPage.waitForNewBubble(countBefore),
            "Nhập text + Gửi phải tạo Bubble"
        );
    }

    @Test(groups = {"web", "regression", "keyboard"})
    @Description("WEB_HT_TC_033 — Nhấn Enter = hành vi giống nút Gửi")
    @Severity(SeverityLevel.CRITICAL)
    public void testPressEnterSendsMessage() {
        hoiThoaiPage.clickKeyboardToggle();

        int countBefore = hoiThoaiPage.getBubbleCount();
        hoiThoaiPage.typeInVietnameseInput("Hello");
        hoiThoaiPage.pressEnterInVietnameseInput();

        Assert.assertTrue(
            hoiThoaiPage.waitForNewBubble(countBefore),
            "Nhấn Enter phải tạo Bubble như click Gửi"
        );

        String inputVal = hoiThoaiPage.getJapaneseInputValue();
        Assert.assertTrue(inputVal == null || inputVal.isEmpty(),
            "Input phải trống lại sau khi gửi, actual: '" + inputVal + "'");
    }

    @Test(groups = {"web", "regression", "keyboard"})
    @Description("WEB_HT_TC_034 — Gửi text rỗng → không tạo Bubble, nút Gửi disabled")
    @Severity(SeverityLevel.CRITICAL)
    public void testSendEmptyTextCreatesNoBubble() {
        hoiThoaiPage.clickKeyboardToggle();

        int countBefore = hoiThoaiPage.getBubbleCount();

        // Send button phải disabled khi input rỗng
        Assert.assertTrue(
            hoiThoaiPage.isSendJapaneseDisabled(),
            "Nút Gửi phải disabled khi input rỗng"
        );
        Assert.assertEquals(hoiThoaiPage.getBubbleCount(), countBefore,
            "Gửi rỗng KHÔNG được tạo Bubble");
    }

    @Test(groups = {"web", "regression", "keyboard"})
    @Description("WEB_HT_TC_035 — Click keyboard toggle lần 2 → quay về mic mode, Bubble cũ giữ nguyên")
    @Severity(SeverityLevel.NORMAL)
    public void testSwitchBackToMicModeRestoresMic() {
        hoiThoaiPage.clickKeyboardToggle();

        // Gửi 1 Bubble
        int countBefore = hoiThoaiPage.getBubbleCount();
        hoiThoaiPage.typeInVietnameseInput("Xin chào");
        hoiThoaiPage.clickSendVietnamese();
        hoiThoaiPage.waitForNewBubble(countBefore);
        int countWithBubble = hoiThoaiPage.getBubbleCount();

        // Chuyển lại mic mode
        hoiThoaiPage.clickKeyboardToggle();

        Assert.assertTrue(hoiThoaiPage.isInMicMode(), "Phải quay về mic mode");
        Assert.assertEquals(hoiThoaiPage.getBubbleCount(), countWithBubble,
            "Bubble cũ phải giữ nguyên sau khi đổi mode");
    }
}
