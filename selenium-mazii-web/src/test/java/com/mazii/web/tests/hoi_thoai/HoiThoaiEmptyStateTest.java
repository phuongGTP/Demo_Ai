package com.mazii.web.tests.hoi_thoai;

import com.mazii.web.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Dịch hội thoại - Web")
public class HoiThoaiEmptyStateTest extends BaseTest {

    @Test(groups = {"web", "smoke", "regression"})
    @Description("WEB_HT_TC_001 — Empty State hiển thị đúng: mascot text, keyboard toggle, 2 mic, 2 dropdown")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyStateDisplaysCorrectly() {
        Assert.assertTrue(
            hoiThoaiPage.isEmptyState(),
            "Phải hiện Empty State khi chưa có Bubble Chat"
        );

        String emptyText = hoiThoaiPage.getEmptyStateText();
        Assert.assertTrue(
            emptyText.contains("Trò chuyện dễ dàng với Mazii"),
            "Empty State text sai, actual: " + emptyText
        );

        // Mic buttons visible
        Assert.assertTrue(hoiThoaiPage.isMicJapaneseIdle(), "Mic Japanese phải idle");
        Assert.assertTrue(hoiThoaiPage.isMicVietnameseIdle(), "Mic Vietnamese phải idle");

        // Keyboard toggle button visible
        Assert.assertTrue(hoiThoaiPage.isKeyboardToggleVisible(), "Keyboard toggle button phải hiển thị");

        // Language dropdowns
        String langJP = hoiThoaiPage.getLanguageJapanese();
        String langVI = hoiThoaiPage.getLanguageVietnamese();
        Assert.assertTrue(langJP.contains("Japanese"), "Dropdown JP sai, actual: " + langJP);
        Assert.assertTrue(langVI.contains("Vietnamese"), "Dropdown VI sai, actual: " + langVI);
    }
}
