package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Dịch hội thoại - Web")
public class HoiThoaiLanguageTest extends BaseTest {

    @Test(enabled = false, groups = {"web", "regression"},
          description = "WEB_HT_TC_012 — STT điều chỉnh theo ngôn ngữ mới [cần audio injection]")
    @Severity(SeverityLevel.CRITICAL)
    public void testChangeLanguageUpdatesSTT() {
        hoiThoaiPage.selectLanguageJapanese("English");
        hoiThoaiPage.clickMicJapanese();
        // inject audio "Good morning" ...
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_022 — Đổi ngôn ngữ → Bubble cũ giữ nguyên")
    @Severity(SeverityLevel.NORMAL)
    public void testChangingLanguagePreservesOldBubbles() {
        int countBefore = hoiThoaiPage.getBubbleCount();
        hoiThoaiPage.selectLanguageJapanese("English");

        String lang = hoiThoaiPage.getLanguageJapanese();
        Assert.assertTrue(lang.contains("English"), "Dropdown JP phải đổi sang English, actual: " + lang);
        Assert.assertEquals(hoiThoaiPage.getBubbleCount(), countBefore,
            "Đổi ngôn ngữ KHÔNG được xóa Bubble cũ");
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_023 — Test cặp Anh–Việt (Gap G1)")
    @Severity(SeverityLevel.NORMAL)
    public void testEnglishVietnamesePair() {
        hoiThoaiPage.selectLanguageJapanese("English");

        Assert.assertTrue(hoiThoaiPage.getLanguageJapanese().contains("English"),
            "Dropdown JP phải là English");
        Assert.assertTrue(hoiThoaiPage.getLanguageVietnamese().contains("Vietnamese"),
            "Dropdown VI phải là Vietnamese");
    }
}
