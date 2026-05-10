package com.mazii.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaywallPage extends BasePage {

    // Verified 2026-05-10: paywall = Bootstrap modal.fade.show with "Nâng cấp" button
    // Triggered when guest/standard user clicks mic → "Bạn cần nâng cấp lên bản premium..."
    private static final By PAYWALL_MODAL  = By.cssSelector(".modal.fade.show");
    private static final By UPGRADE_BUTTON = By.xpath("//button[normalize-space()='Nâng cấp']");

    public PaywallPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPaywallDisplayed() {
        return isDisplayed(PAYWALL_MODAL, 5) && isDisplayed(UPGRADE_BUTTON, 3);
    }

    public boolean isUpgradeButtonVisible() {
        return isDisplayed(UPGRADE_BUTTON, 3);
    }
}
