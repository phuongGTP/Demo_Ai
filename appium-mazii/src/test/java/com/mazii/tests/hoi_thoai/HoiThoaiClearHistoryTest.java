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
 * MAZ_HT_TC_023–025 — M_HT08: Xóa lịch sử hội thoại
 * MAZ_HT_TC_026 — M_HT09: Auto-scroll
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiClearHistoryTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    @Test(groups = {"mobile", "regression", "clear_history"})
    @Description("MAZ_HT_TC_023 — Tap Trash icon → Modal xác nhận 'không thể hoàn tác' hiển thị")
    @Severity(SeverityLevel.NORMAL)
    public void testTapTrashShowsConfirmationModal() {
        hoiThoaiScreen.tapTrashButton();

        Assert.assertTrue(
            hoiThoaiScreen.isDeleteModalDisplayed(),
            "Sau khi tap Trash: Modal xác nhận phải hiển thị với cảnh báo 'không thể hoàn tác'"
        );
    }

    @Test(groups = {"mobile", "regression", "clear_history"})
    @Description("MAZ_HT_TC_024 — Xác nhận xóa → màn về Empty State + Toast")
    @Severity(SeverityLevel.NORMAL)
    public void testConfirmDeleteReturnsToEmptyState() {
        hoiThoaiScreen.tapTrashButton();
        Assert.assertTrue(hoiThoaiScreen.isDeleteModalDisplayed(),
            "Pre-condition: Modal xác nhận phải hiển thị");

        hoiThoaiScreen.confirmDelete();

        Assert.assertTrue(
            hoiThoaiScreen.isEmptyStateDisplayed(),
            "Sau khi xác nhận xóa: màn hình phải về Empty State"
        );
        Assert.assertEquals(
            hoiThoaiScreen.getBubbleCount(), 0,
            "Sau khi xóa: không còn Bubble nào trên màn hình"
        );
    }

    @Test(groups = {"mobile", "regression", "clear_history"})
    @Description("MAZ_HT_TC_025 — Hủy xóa → Bubble giữ nguyên")
    @Severity(SeverityLevel.MINOR)
    public void testCancelDeleteKeepsBubbles() {
        int bubblesBefore = hoiThoaiScreen.getBubbleCount();

        hoiThoaiScreen.tapTrashButton();
        Assert.assertTrue(hoiThoaiScreen.isDeleteModalDisplayed(),
            "Pre-condition: Modal xác nhận phải hiển thị");

        hoiThoaiScreen.cancelDelete();

        Assert.assertFalse(
            hoiThoaiScreen.isDeleteModalDisplayed(),
            "Sau khi Hủy: Modal phải đóng lại"
        );
        Assert.assertEquals(
            hoiThoaiScreen.getBubbleCount(), bubblesBefore,
            "Sau khi Hủy: số lượng Bubble phải giữ nguyên"
        );
    }

    @Test(enabled = false, groups = {"mobile", "manual_required", "auto_scroll"})
    @Description("MAZ_HT_TC_026 — Auto-scroll: Bubble mới → màn tự cuộn | YÊU CẦU NHIỀU BUBBLE")
    @Severity(SeverityLevel.NORMAL)
    public void testAutoScrollOnNewBubble() {
        // ⚠️ Cần ≥6 Bubble để verify auto-scroll (cần audio injection)
        // Hướng dẫn manual:
        // 1. Tạo ≥6 Bubble bằng cách nói xen kẽ 2 bên
        // 2. Thêm Bubble mới
        // 3. Verify: Bubble mới nhất hiển thị đầy đủ mà không cần scroll thủ công
        Assert.assertTrue(hoiThoaiScreen.getBubbleCount() >= 6,
            "Pre-condition: cần ≥6 Bubble để test auto-scroll");
    }
}
