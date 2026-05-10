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
 * WEB_HT_TC_025–027 — M_HT08: Xóa lịch sử
 * WEB_HT_TC_028 — M_HT09: Auto-scroll
 *
 * ⚠️ TC_025–027 cần seeded Bubble làm pre-condition.
 * ⚠️ TC_028 cần ≥6 Bubble (enabled=false).
 *
 * Web behavior: Hover + Click Trash (khác mobile chỉ cần Tap).
 */
@Feature("Dịch hội thoại - Web")
public class HoiThoaiClearHistoryTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        seedBubbleData();
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_025 — Hover + Click Trash icon → Modal xác nhận 'không thể hoàn tác'")
    @Severity(SeverityLevel.NORMAL)
    public void testClickTrashShowsConfirmModal() {
        hoiThoaiPage.hoverTrashButton();
        hoiThoaiPage.clickTrashButton();

        Assert.assertTrue(
            hoiThoaiPage.isDeleteModalVisible(),
            "Sau khi click Trash: Modal xác nhận phải xuất hiện với cảnh báo 'không thể hoàn tác'"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_026 — Xác nhận xóa → về Empty State")
    @Severity(SeverityLevel.NORMAL)
    public void testConfirmDeleteReturnsToEmptyState() {
        hoiThoaiPage.hoverTrashButton();
        hoiThoaiPage.clickTrashButton();

        Assert.assertTrue(hoiThoaiPage.isDeleteModalVisible(), "Pre-condition: Modal phải mở");

        hoiThoaiPage.confirmDelete();

        Assert.assertTrue(
            hoiThoaiPage.isEmptyState(),
            "Sau khi xác nhận xóa: màn hình phải về Empty State"
        );
        Assert.assertEquals(
            hoiThoaiPage.getBubbleCount(),
            0,
            "Sau khi xóa: KHÔNG còn Bubble nào"
        );
    }

    @Test(groups = {"web", "regression"})
    @Description("WEB_HT_TC_027 — Hủy xóa → Bubble giữ nguyên")
    @Severity(SeverityLevel.NORMAL)
    public void testCancelDeletePreservesBubbles() {
        int countBefore = hoiThoaiPage.getBubbleCount();

        hoiThoaiPage.hoverTrashButton();
        hoiThoaiPage.clickTrashButton();
        Assert.assertTrue(hoiThoaiPage.isDeleteModalVisible(), "Pre-condition: Modal phải mở");

        hoiThoaiPage.cancelDelete();

        Assert.assertFalse(
            hoiThoaiPage.isDeleteModalVisible(),
            "Sau khi Hủy: Modal phải đóng lại"
        );
        Assert.assertEquals(
            hoiThoaiPage.getBubbleCount(),
            countBefore,
            "Sau khi Hủy: tất cả Bubble phải còn nguyên"
        );
    }

    @Test(enabled = false,
          groups = {"web", "regression"},
          description = "WEB_HT_TC_028 — Auto-scroll: Bubble mới → tự cuộn xuống cuối [cần ≥6 Bubble]")
    @Severity(SeverityLevel.NORMAL)
    public void testAutoScrollWhenNewBubbleAdded() {
        // ⚠️ Cần ≥6 Bubble để overflow scroll area, rồi thêm Bubble mới
        // Verify: Bubble mới nhất visible trong viewport mà không cần scroll thủ công
        // BrowserUtils.isInViewport(driver, lastBubbleElement)
        Assert.assertTrue(true, "Placeholder — cần seeded ≥6 Bubble");
    }
}
