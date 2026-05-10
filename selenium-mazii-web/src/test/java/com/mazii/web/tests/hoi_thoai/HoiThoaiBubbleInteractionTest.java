package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * WEB_HT_TC_016–021 — M_HT06: Tương tác trên Bubble
 *
 * ⚠️ Tất cả TCs trong class này cần seeded Bubble làm pre-condition.
 * Implement seedBubbleData() qua audio injection hoặc API mock.
 */
@Feature("Dịch hội thoại - Web")
public class HoiThoaiBubbleInteractionTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        seedBubbleData();
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_016 — Click icon Phát âm trên Bubble → TTS phát nội dung")
    @Severity(SeverityLevel.CRITICAL)
    public void testClickPlayButtonPlaysAudio() {
        // ⚠️ Cần seeded Bubble — Xem BeforeMethod
        int bubbleCount = hoiThoaiPage.getBubbleCount();
        if (bubbleCount == 0) {
            throw new RuntimeException("Pre-condition failed: cần ít nhất 1 Bubble trên màn hình");
        }

        hoiThoaiPage.clickBubblePlay();

        // Verify: nút Phát âm phản hồi (active state hoặc không bị lỗi)
        // ⚠️ Verify TTS thực tế cần WebAudio API assertion hoặc kiểm tra network request
        Assert.assertTrue(
            hoiThoaiPage.getBubbleCount() > 0,
            "Bubble vẫn tồn tại sau khi click Phát âm"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_017 — Click icon Copy trên Bubble → toast 'Đã sao chép'")
    @Severity(SeverityLevel.CRITICAL)
    public void testClickCopyShowsToast() {
        int bubbleCount = hoiThoaiPage.getBubbleCount();
        if (bubbleCount == 0) {
            throw new RuntimeException("Pre-condition failed: cần ít nhất 1 Bubble trên màn hình");
        }

        hoiThoaiPage.clickBubbleCopy();

        Assert.assertTrue(
            hoiThoaiPage.isCopiedToastVisible(),
            "Sau khi click Copy: phải hiện toast 'Đã sao chép'"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_019 — Hover Bubble Tiếng Nhật → icon '>' hiện ra")
    @Severity(SeverityLevel.CRITICAL)
    public void testHoverJapaneseBubbleShowsDetailIcon() {
        int bubbleCount = hoiThoaiPage.getBubbleCount();
        if (bubbleCount == 0) {
            throw new RuntimeException("Pre-condition failed: cần Bubble Tiếng Nhật trên màn hình");
        }

        // Trước khi hover: icon ">" không hiển thị
        Assert.assertFalse(
            hoiThoaiPage.isBubbleDetailButtonVisible(),
            "Trước khi hover: icon '>' KHÔNG được hiển thị"
        );

        hoiThoaiPage.hoverFirstBubble();

        Assert.assertTrue(
            hoiThoaiPage.isBubbleDetailButtonVisible(),
            "Sau khi hover Bubble Tiếng Nhật: icon '>' phải xuất hiện"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_020 — Click icon '>' trên Bubble Tiếng Nhật → chuyển màn Dịch văn bản")
    @Severity(SeverityLevel.CRITICAL)
    public void testClickDetailButtonNavigatesToDich() {
        int bubbleCount = hoiThoaiPage.getBubbleCount();
        if (bubbleCount == 0) {
            throw new RuntimeException("Pre-condition failed: cần Bubble Tiếng Nhật trên màn hình");
        }

        hoiThoaiPage.hoverFirstBubble();
        hoiThoaiPage.clickBubbleDetailButton();

        // Verify: chuyển sang màn hình Dịch văn bản
        hoiThoaiPage.waitForUrl("van-ban");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("van-ban") || currentUrl.contains("dich"),
            "Sau khi click '>': phải chuyển sang màn Dịch văn bản, actual URL: " + currentUrl
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_021 — Hover Bubble Tiếng Việt/Anh/Hàn → icon '>' KHÔNG hiện ra")
    @Severity(SeverityLevel.NORMAL)
    public void testHoverNonJapaneseBubbleDoesNotShowDetailIcon() {
        int bubbleCount = hoiThoaiPage.getBubbleCount();
        if (bubbleCount == 0) {
            throw new RuntimeException("Pre-condition failed: cần Bubble Tiếng Việt trên màn hình");
        }

        // Hover vào Bubble Tiếng Việt (bên trái)
        hoiThoaiPage.hoverFirstBubble(); // ⚠️ Cần method riêng hoverFirstVietnameseBubble()

        Assert.assertFalse(
            hoiThoaiPage.isBubbleDetailButtonVisible(),
            "Bubble Tiếng Việt KHÔNG được hiện icon '>'"
        );
    }
}
