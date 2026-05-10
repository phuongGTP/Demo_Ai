package com.mazii.tests.hoi_thoai;

import com.mazii.base.BaseTest;
import com.mazii.screens.DichHoiThoaiScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * MAZ_HT_TC_006–015 — M_HT04: Thu âm & M_HT05: Bubble Chat
 *
 * ⚠️ Lưu ý quan trọng về giới hạn tự động hóa:
 *
 * Các TC liên quan đến việc NÓI VÀO MIC (TC_008–012) không thể tự động hóa
 * hoàn toàn vì yêu cầu giọng nói thực tế. Các options:
 *
 *  Option A: Dùng mock audio file (inject qua virtual mic device)
 *            → Cần setup emulator với virtual mic + audio injection
 *
 *  Option B: Dùng Appium execScript để simulate STT result (nếu app hỗ trợ)
 *
 *  Option C: Đánh dấu @Test(enabled=false) và chạy manual cho các TC này
 *
 * TC_006, TC_007, TC_013–015 có thể automate (không cần giọng nói).
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiBubbleChatTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    // ===== AUTOMATABLE: Mic button state =====

    @Test(groups = {"mobile", "regression", "recording"})
    @Description("MAZ_HT_TC_006 — Tap Mic → nút chuyển sang Pause icon, hệ thống lắng nghe")
    @Severity(SeverityLevel.BLOCKER)
    public void testTapMicRightChangesToPauseState() {
        Assert.assertTrue(hoiThoaiScreen.isMicRightIdle(),
            "Nút Mic phải phải ở trạng thái idle trước khi tap");

        hoiThoaiScreen.tapMicRight();

        Assert.assertTrue(hoiThoaiScreen.isMicRightRecording(),
            "Sau khi tap Mic phải: nút phải chuyển thành Pause icon (⏸) viền xanh");
        Assert.assertTrue(hoiThoaiScreen.isMicLeftIdle(),
            "Nút Mic trái phải vẫn idle khi bên phải đang thu âm");
    }

    @Test(groups = {"mobile", "regression", "recording"})
    @Description("MAZ_HT_TC_007 — Chỉ 1 bên thu âm tại 1 thời điểm")
    @Severity(SeverityLevel.CRITICAL)
    public void testOnlyOneSideRecordsAtATime() {
        hoiThoaiScreen.tapMicRight();
        Assert.assertTrue(hoiThoaiScreen.isMicRightRecording(),
            "Bên phải phải vào trạng thái recording sau khi tap");

        hoiThoaiScreen.tapMicLeft();

        // Behavior: bên trái không start đồng thời HOẶC bên phải dừng lại
        boolean rightStillRecording = hoiThoaiScreen.isMicRightRecording();
        boolean leftStartedRecording = hoiThoaiScreen.isMicLeftRecording();

        boolean onlyOneIsRecording = (leftStartedRecording && !rightStillRecording)
            || (!leftStartedRecording && rightStillRecording);

        Assert.assertTrue(onlyOneIsRecording,
            "Chỉ 1 bên được thu âm tại 1 thời điểm — không được cả 2 cùng lúc");
    }

    // ===== REQUIRES VOICE INPUT — DISABLED pending audio injection setup =====

    @Test(enabled = false, groups = {"mobile", "manual_required", "recording"})
    @Description("MAZ_HT_TC_008 — Đang nói → Bubble tạm '...' xuất hiện ngay | YÊU CẦU GIỌNG NÓI THỰC TẾ")
    @Severity(SeverityLevel.BLOCKER)
    public void testSpeakingShowsPendingBubble() {
        // Pre-condition: đang thu âm, người dùng nói vào mic
        // ⚠️ Cần audio injection hoặc virtual mic để automate
        // Hướng dẫn manual:
        // 1. Tap Mic phải
        // 2. Đọc: "おはようございます"
        // 3. Verify: Bubble tạm "..." xuất hiện ngay tại nửa màn hình phải
        hoiThoaiScreen.tapMicRight();
        Assert.assertTrue(hoiThoaiScreen.isBubblePendingDisplayed(),
            "Bubble tạm '...' phải xuất hiện ngay khi đang nói");
    }

    @Test(enabled = false, groups = {"mobile", "manual_required", "recording"})
    @Description("MAZ_HT_TC_009 — Im lặng 1–1.5s → Bubble chính thức tự động | YÊU CẦU GIỌNG NÓI")
    @Severity(SeverityLevel.BLOCKER)
    public void testSilenceTriggersOfficialBubble() {
        // ⚠️ Cần audio injection
        // Hướng dẫn manual:
        // 1. Tap Mic, nói "おはようございます"
        // 2. Im lặng 1.5 giây
        // 3. Verify: nút về idle, Bubble chính thức có text gốc + bản dịch
        int initialCount = hoiThoaiScreen.getBubbleCount();
        hoiThoaiScreen.tapMicRight();

        Assert.assertTrue(hoiThoaiScreen.waitForOfficialBubble(initialCount),
            "Bubble chính thức phải xuất hiện sau khi im lặng ~1.5s");
        Assert.assertTrue(hoiThoaiScreen.isMicRightIdle(),
            "Nút Mic phải về idle sau khi kết thúc thu âm tự động");
    }

    @Test(groups = {"mobile", "regression", "recording"})
    @Description("MAZ_HT_TC_010 — Tap Pause thủ công → dừng thu âm, nút về idle")
    @Severity(SeverityLevel.CRITICAL)
    public void testManualPauseStopsRecording() {
        hoiThoaiScreen.tapMicRight();
        Assert.assertTrue(hoiThoaiScreen.isMicRightRecording(),
            "Mic phải phải đang ở trạng thái recording trước khi Pause");

        hoiThoaiScreen.tapPauseRight();

        Assert.assertTrue(hoiThoaiScreen.isMicRightIdle(),
            "Sau khi tap Pause: nút Mic phải về idle (mic xám)");
    }

    @Test(groups = {"mobile", "regression", "recording"})
    @Description("MAZ_HT_TC_011 — Tap Mic nhưng không nói → không tạo Bubble, về idle (Gap G2)")
    @Severity(SeverityLevel.CRITICAL)
    public void testNoSpeechNoBubbleCreated() {
        int initialCount = hoiThoaiScreen.getBubbleCount();

        hoiThoaiScreen.tapMicRight();
        // Im lặng hoàn toàn — chờ silence detection (~3s)
        hoiThoaiScreen.tapPauseRight();

        Assert.assertTrue(hoiThoaiScreen.isMicRightIdle(),
            "Nút Mic phải về idle sau khi không nói gì");
        Assert.assertEquals(hoiThoaiScreen.getBubbleCount(), initialCount,
            "Không nói gì → không được tạo Bubble mới");
    }
}
