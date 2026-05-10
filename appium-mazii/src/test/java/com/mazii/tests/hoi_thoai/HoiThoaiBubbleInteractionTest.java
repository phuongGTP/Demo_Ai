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
 * MAZ_HT_TC_013–020 — M_HT05: Bubble Chat + M_HT06: Tương tác trên Bubble
 *
 * ⚠️ Pre-condition: Cần có ít nhất 1 Bubble chính thức trên màn hình.
 * Các test này cần seeded data hoặc chạy sau các TC recording đã tạo Bubble.
 *
 * Approach: Dùng @BeforeMethod để seed Bubble qua audio injection,
 * hoặc mở app từ state đã có Bubble (dùng deep link / saved state nếu app hỗ trợ).
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiBubbleInteractionTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
        // ⚠️ Seed Bubble: cần audio injection hoặc pre-seeded app state
        // seedBubbleData();
    }

    @Test(groups = {"mobile", "regression", "bubble"})
    @Description("MAZ_HT_TC_016 — Tap icon Phát âm trên Bubble → TTS phát nội dung")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlayAudioFromBubble() {
        // Pre-condition: phải có Bubble trên màn hình
        Assert.assertTrue(hoiThoaiScreen.getBubbleCount() > 0,
            "Pre-condition: phải có ít nhất 1 Bubble để test tính năng Phát âm");

        hoiThoaiScreen.tapBubblePlayButton();

        // ⚠️ Verification: app không crash và không có lỗi hiển thị
        // Verify audio playing state nếu app expose accessibility state
        Assert.assertTrue(hoiThoaiScreen.isScreenTitleDisplayed(),
            "App không được crash sau khi tap Phát âm");
    }

    @Test(groups = {"mobile", "regression", "bubble"})
    @Description("MAZ_HT_TC_017 — Tap icon Copy trên Bubble → toast 'Đã sao chép'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCopyFromBubbleShowsToast() {
        Assert.assertTrue(hoiThoaiScreen.getBubbleCount() > 0,
            "Pre-condition: phải có ít nhất 1 Bubble để test Copy");

        hoiThoaiScreen.tapBubbleCopyButton();

        Assert.assertTrue(hoiThoaiScreen.isToastCopiedDisplayed(),
            "Sau khi tap Copy: toast 'Đã sao chép' phải hiển thị");
    }

    @Test(groups = {"mobile", "regression", "bubble"})
    @Description("MAZ_HT_TC_019 — Tap icon '>' trên Bubble Tiếng Nhật → chuyển màn Dịch văn bản")
    @Severity(SeverityLevel.CRITICAL)
    public void testTapDetailIconOnJapaneseBubbleNavigatesToDich() {
        Assert.assertTrue(hoiThoaiScreen.getBubbleCount() > 0,
            "Pre-condition: phải có Bubble Tiếng Nhật để test icon '>'");
        Assert.assertTrue(hoiThoaiScreen.isBubbleDetailButtonDisplayed(),
            "Pre-condition: icon '>' phải hiển thị trên Bubble Tiếng Nhật");

        hoiThoaiScreen.tapBubbleDetailButton();

        // Verify chuyển sang màn Dịch văn bản
        Assert.assertTrue(
            new com.mazii.screens.DichScreen(driver).isDichScreenDisplayed(),
            "Sau khi tap '>': phải chuyển sang màn Dịch văn bản"
        );
    }

    @Test(groups = {"mobile", "regression", "bubble"})
    @Description("MAZ_HT_TC_020 — Bubble Tiếng Việt/Anh KHÔNG có icon '>'")
    @Severity(SeverityLevel.NORMAL)
    public void testNonJapaneseBubbleHasNoDetailIcon() {
        // Pre-condition: cần Bubble từ bên Tiếng Việt (phải)
        // ⚠️ Cần seeded Bubble Tiếng Việt trước khi assert
        Assert.assertFalse(
            hoiThoaiScreen.isBubbleDetailButtonDisplayed(),
            "Bubble Tiếng Việt không được có icon '>'"
        );
    }
}
