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
 * MAZ_HT_TC_021, MAZ_HT_TC_022 — M_HT07: Language Selection
 * MAZ_HT_TC_012 — STT điều chỉnh theo ngôn ngữ mới
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiLanguageTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    @Test(groups = {"mobile", "regression", "language"})
    @Description("MAZ_HT_TC_021 — Đổi ngôn ngữ → Bubble cũ giữ nguyên, Bubble mới theo cặp mới")
    @Severity(SeverityLevel.NORMAL)
    public void testChangeLanguageKeepsOldBubbles() {
        int bubblesBefore = hoiThoaiScreen.getBubbleCount();

        hoiThoaiScreen.selectLanguageLeft("Tiếng Hàn");

        Assert.assertEquals(
            hoiThoaiScreen.getBubbleCount(), bubblesBefore,
            "Bubble cũ phải giữ nguyên sau khi đổi ngôn ngữ"
        );
        Assert.assertTrue(
            hoiThoaiScreen.getLanguageLeftText().contains("Hàn"),
            "Dropdown trái phải hiển thị ngôn ngữ mới 'Tiếng Hàn'"
        );
    }

    @Test(groups = {"mobile", "regression", "language"})
    @Description("MAZ_HT_TC_022 — Test cặp ngôn ngữ Anh–Việt (Gap G1 — Q1 confirmed)")
    @Severity(SeverityLevel.NORMAL)
    public void testEnglishVietnameseLanguagePair() {
        hoiThoaiScreen.selectLanguageLeft("Tiếng Anh");

        Assert.assertTrue(
            hoiThoaiScreen.getLanguageLeftText().contains("Anh"),
            "Dropdown trái phải hiển thị 'Tiếng Anh' sau khi chọn"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isMicLeftIdle(),
            "Mic trái phải vẫn idle sau khi đổi ngôn ngữ"
        );
        // ⚠️ Verification STT nhận diện Tiếng Anh cần audio injection (TC_012)
        // Tiến hành tap Mic và speak "Good morning" để verify đầy đủ
    }

    @Test(enabled = false, groups = {"mobile", "manual_required", "language"})
    @Description("MAZ_HT_TC_012 — Đổi ngôn ngữ → STT dùng ngôn ngữ mới | YÊU CẦU GIỌNG NÓI")
    @Severity(SeverityLevel.CRITICAL)
    public void testSTTAdjustsToNewLanguage() {
        // ⚠️ Cần audio injection
        // 1. Đổi dropdown trái sang "Tiếng Anh"
        // 2. Tap Mic trái
        // 3. Inject audio: "Good morning"
        // 4. Verify Bubble chính thức có text Tiếng Anh + bản dịch Tiếng Việt
        hoiThoaiScreen.selectLanguageLeft("Tiếng Anh");
        hoiThoaiScreen.tapMicLeft();

        int initialCount = hoiThoaiScreen.getBubbleCount();
        Assert.assertTrue(hoiThoaiScreen.waitForOfficialBubble(initialCount),
            "STT phải nhận diện đúng Tiếng Anh và tạo Bubble sau khi đổi ngôn ngữ");
    }
}
