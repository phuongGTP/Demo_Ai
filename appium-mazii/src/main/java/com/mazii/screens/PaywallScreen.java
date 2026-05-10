package com.mazii.screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Màn hình Paywall — hiển thị khi tài khoản Guest/Thường truy cập tính năng Premium.
 *
 * ⚠️ Locator cần verify với Appium Inspector.
 */
public class PaywallScreen extends BaseScreen {

    // ===================== LOCATORS =====================
    private final By paywallContainer    = byAccessibilityId("paywall_screen");
    private final By upgradeButton       = byAccessibilityId("paywall_upgrade_button");
    private final By paywallTitle        = byTextContains("Nâng cấp");

    public PaywallScreen(AppiumDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(paywallContainer, 5)
            || isDisplayed(upgradeButton, 3)
            || isDisplayed(paywallTitle, 3);
    }

    public boolean isUpgradeButtonVisible() {
        return isDisplayed(upgradeButton);
    }
}
