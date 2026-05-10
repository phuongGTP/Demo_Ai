package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Dịch hội thoại - Web")
public class HoiThoaiBubbleChatTest extends BaseTest {

    @Test(groups = {"web", "smoke", "regression"})
    @Description("WEB_HT_TC_006 — Click Mic Japanese → trạng thái recording, mic Vietnamese vẫn idle")
    @Severity(SeverityLevel.CRITICAL)
    public void testClickMicShowsRecordingState() {
        hoiThoaiPage.clickMicJapanese();

        Assert.assertTrue(
            hoiThoaiPage.isMicJapaneseRecording(),
            "Sau khi click Mic JP: phải ở trạng thái recording"
        );
        Assert.assertFalse(
            hoiThoaiPage.isMicVietnameseRecording(),
            "Mic VI phải vẫn idle khi Mic JP đang thu"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_007 — Chỉ 1 mic thu âm tại 1 thời điểm")
    @Severity(SeverityLevel.CRITICAL)
    public void testOnlyOneMicRecordingAtATime() {
        hoiThoaiPage.clickMicJapanese();
        Assert.assertTrue(hoiThoaiPage.isMicJapaneseRecording(), "Mic JP phải đang thu");

        hoiThoaiPage.clickMicVietnamese();

        boolean jpRecording = hoiThoaiPage.isMicJapaneseRecording();
        boolean viRecording = hoiThoaiPage.isMicVietnameseRecording();

        Assert.assertFalse(jpRecording && viRecording, "2 mic không được cùng thu âm");
    }

    @Test(enabled = false, groups = {"web", "regression"},
          description = "WEB_HT_TC_008 — Đang nói → Bubble tạm '...' [cần audio injection]")
    @Severity(SeverityLevel.CRITICAL)
    public void testSpeakingShowsPendingBubble() {
        hoiThoaiPage.clickMicJapanese();
        Assert.assertTrue(hoiThoaiPage.hasPendingBubble(), "Phải có Bubble tạm '...'");
    }

    @Test(enabled = false, groups = {"web", "regression"},
          description = "WEB_HT_TC_009 — Im lặng → Bubble chính thức tự động [cần audio injection]")
    @Severity(SeverityLevel.CRITICAL)
    public void testSilenceCreatesOfficialBubble() {
        int countBefore = hoiThoaiPage.getBubbleCount();
        Assert.assertTrue(hoiThoaiPage.waitForNewBubble(countBefore), "Phải xuất hiện Bubble chính thức");
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_011 — Click Mic nhưng không nói → không tạo Bubble, về idle")
    @Severity(SeverityLevel.CRITICAL)
    public void testSilenceAfterMicTapCreatesNoBubble() {
        int countBefore = hoiThoaiPage.getBubbleCount();

        hoiThoaiPage.clickMicJapanese();
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        // Click lại để dừng nếu đang recording
        if (hoiThoaiPage.isMicJapaneseRecording()) {
            hoiThoaiPage.clickMicJapanese();
        }

        Assert.assertEquals(hoiThoaiPage.getBubbleCount(), countBefore,
            "Im lặng hoàn toàn → KHÔNG được tạo Bubble");
    }
}
