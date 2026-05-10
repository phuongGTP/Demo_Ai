package com.mazii.screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Màn hình Dịch chính — chứa 2 tabs: "Dịch văn bản" và "Dịch hội thoại".
 *
 * ⚠️ Locator cần verify trên app thực tế bằng Appium Inspector.
 * Các accessibility ID dưới đây theo convention Mazii — cập nhật nếu khác.
 */
public class DichScreen extends BaseScreen {

    // ===================== LOCATORS =====================
    // ⚠️ Cần verify với Appium Inspector
    private final By tabDichVanBan       = byAccessibilityId("tab_dich_van_ban");
    private final By tabDichHoiThoai     = byAccessibilityId("tab_dich_hoi_thoai");

    public DichScreen(AppiumDriver driver) {
        super(driver);
    }

    public boolean isDichScreenDisplayed() {
        return isDisplayed(tabDichVanBan) || isDisplayed(tabDichHoiThoai);
    }

    public DichHoiThoaiScreen goToDichHoiThoai() {
        tap(tabDichHoiThoai);
        return new DichHoiThoaiScreen(driver);
    }
}
