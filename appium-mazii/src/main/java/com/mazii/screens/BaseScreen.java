package com.mazii.screens;

import com.mazii.config.AppConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseScreen {

    protected final AppiumDriver driver;
    protected final WebDriverWait wait;

    protected BaseScreen(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(AppConfig.getExplicitWait()));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected void tap(By locator) {
        waitClickable(locator).click();
    }

    // Cross-platform: dùng accessibilityId ưu tiên
    protected By byAccessibilityId(String id) {
        return AppiumBy.accessibilityId(id);
    }

    // Android: resource-id
    protected By byAndroidId(String resourceId) {
        return AppiumBy.id(AppConfig.get("ANDROID_APP_PACKAGE") + ":id/" + resourceId);
    }

    // iOS: predicate string
    protected By byIOSPredicate(String predicate) {
        return AppiumBy.iOSNsPredicateString(predicate);
    }

    protected By byText(String text) {
        if (AppConfig.isAndroid()) {
            return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")");
        }
        return AppiumBy.iOSNsPredicateString("label == '" + text + "'");
    }

    protected By byTextContains(String text) {
        if (AppConfig.isAndroid()) {
            return AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
        }
        return AppiumBy.iOSNsPredicateString("label CONTAINS '" + text + "'");
    }
}
