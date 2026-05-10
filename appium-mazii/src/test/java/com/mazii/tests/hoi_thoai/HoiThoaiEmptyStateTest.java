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
 * MAZ_HT_TC_001 — M_HT01: Empty State
 */
@Feature("Dịch hội thoại - Mobile")
public class HoiThoaiEmptyStateTest extends BaseTest {

    private DichHoiThoaiScreen hoiThoaiScreen;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        loginAsPremium();
        hoiThoaiScreen = dichScreen.goToDichHoiThoai();
    }

    @Test(groups = {"mobile", "regression", "empty_state"})
    @Description("MAZ_HT_TC_001 — Empty State hiển thị đúng khi chưa có Bubble Chat")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyStateDisplayedOnFirstOpen() {
        Assert.assertTrue(
            hoiThoaiScreen.isEmptyStateDisplayed(),
            "Empty state phải hiển thị text 'Nhấn vào micro để bắt đầu giao tiếp'"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isScreenTitleDisplayed(),
            "Header phải hiển thị tiêu đề 'Dịch hội thoại'"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isTrashButtonDisplayed(),
            "Trash icon phải hiển thị ở góc trên phải header"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isMicLeftIdle(),
            "Nút Mic trái phải ở trạng thái idle khi chưa thu âm"
        );
        Assert.assertTrue(
            hoiThoaiScreen.isMicRightIdle(),
            "Nút Mic phải phải ở trạng thái idle khi chưa thu âm"
        );
    }
}
