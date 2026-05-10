package com.mazii.screens;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Màn hình Dịch hội thoại — core screen cho tính năng hội thoại 2 chiều.
 *
 * Layout:
 *   [Header: ← Dịch | Dịch hội thoại | 🗑]
 *   [Bubble Chat Area (scrollable)]
 *   [Mic Trái | Mic Phải]
 *   [Language Left ▼ | Language Right ▼]
 *
 * ⚠️ TẤT CẢ locators dưới đây cần được verify bằng Appium Inspector
 *    trên app Mazii thực tế. Đây là convention naming dựa trên Figma design.
 */
public class DichHoiThoaiScreen extends BaseScreen {

    // ===================== LOCATORS =====================
    // Empty state
    private final By emptyStateText          = byTextContains("Nhấn vào micro để bắt đầu");
    private final By emptyStateMascot        = byAccessibilityId("hoi_thoai_empty_mascot");

    // Header
    private final By trashButton             = byAccessibilityId("hoi_thoai_trash_button");
    private final By screenTitle             = byTextContains("Dịch hội thoại");

    // Mic buttons — Figma: Tiếng Nhật trái, Tiếng Việt phải
    private final By micButtonLeft           = byAccessibilityId("hoi_thoai_mic_left");
    private final By micButtonRight          = byAccessibilityId("hoi_thoai_mic_right");

    // Mic state
    private final By pauseIconLeft           = byAccessibilityId("hoi_thoai_pause_left");
    private final By pauseIconRight          = byAccessibilityId("hoi_thoai_pause_right");

    // Language dropdowns
    private final By languageDropdownLeft    = byAccessibilityId("hoi_thoai_lang_left");
    private final By languageDropdownRight   = byAccessibilityId("hoi_thoai_lang_right");

    // Bubble area
    private final By bubbleList              = byAccessibilityId("hoi_thoai_bubble_list");
    private final By bubbleItems             = byAccessibilityId("hoi_thoai_bubble_item");

    // Bubble tạm
    private final By bubblePending           = byTextContains("...");

    // Delete confirmation modal
    private final By deleteModal             = byAccessibilityId("hoi_thoai_delete_modal");
    private final By deleteConfirmButton     = byAccessibilityId("hoi_thoai_delete_confirm");
    private final By deleteCancelButton      = byAccessibilityId("hoi_thoai_delete_cancel");
    private final By deleteModalText         = byTextContains("không thể hoàn tác");

    // Toast messages
    private final By toastNetworkError       = byTextContains("Vui lòng kết nối mạng");
    private final By toastCopied             = byTextContains("Đã sao chép");

    // Bubble action buttons (trên mỗi bubble)
    private final By bubblePlayButton        = byAccessibilityId("bubble_play_button");
    private final By bubbleCopyButton        = byAccessibilityId("bubble_copy_button");
    private final By bubbleDetailButton      = byAccessibilityId("bubble_detail_button");

    public DichHoiThoaiScreen(AppiumDriver driver) {
        super(driver);
    }

    // ===================== VERIFICATIONS =====================

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(emptyStateText, 5);
    }

    public boolean isScreenTitleDisplayed() {
        return isDisplayed(screenTitle, 5);
    }

    public boolean isTrashButtonDisplayed() {
        return isDisplayed(trashButton);
    }

    public boolean isMicLeftIdle() {
        return isDisplayed(micButtonLeft) && !isDisplayed(pauseIconLeft, 1);
    }

    public boolean isMicRightIdle() {
        return isDisplayed(micButtonRight) && !isDisplayed(pauseIconRight, 1);
    }

    public boolean isMicLeftRecording() {
        return isDisplayed(pauseIconLeft, 5);
    }

    public boolean isMicRightRecording() {
        return isDisplayed(pauseIconRight, 5);
    }

    public boolean isBubblePendingDisplayed() {
        return isDisplayed(bubblePending, 5);
    }

    public int getBubbleCount() {
        try {
            List<WebElement> bubbles = driver.findElements(bubbleItems);
            return bubbles.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isDeleteModalDisplayed() {
        return isDisplayed(deleteModal, 5) || isDisplayed(deleteModalText, 5);
    }

    public boolean isToastNetworkErrorDisplayed() {
        return isDisplayed(toastNetworkError, 5);
    }

    public boolean isToastCopiedDisplayed() {
        return isDisplayed(toastCopied, 3);
    }

    public boolean isBubbleDetailButtonDisplayed() {
        return isDisplayed(bubbleDetailButton);
    }

    // ===================== ACTIONS =====================

    public void tapMicLeft() {
        tap(micButtonLeft);
    }

    public void tapMicRight() {
        tap(micButtonRight);
    }

    public void tapPauseLeft() {
        tap(pauseIconLeft);
    }

    public void tapPauseRight() {
        tap(pauseIconRight);
    }

    public void tapTrashButton() {
        tap(trashButton);
    }

    public void confirmDelete() {
        tap(deleteConfirmButton);
    }

    public void cancelDelete() {
        tap(deleteCancelButton);
    }

    public void tapBubblePlayButton() {
        tap(bubblePlayButton);
    }

    public void tapBubbleCopyButton() {
        tap(bubbleCopyButton);
    }

    public void tapBubbleDetailButton() {
        tap(bubbleDetailButton);
    }

    public void selectLanguageLeft(String languageName) {
        tap(languageDropdownLeft);
        tap(byText(languageName));
    }

    public void selectLanguageRight(String languageName) {
        tap(languageDropdownRight);
        tap(byText(languageName));
    }

    public String getLanguageLeftText() {
        return getText(languageDropdownLeft);
    }

    public String getLanguageRightText() {
        return getText(languageDropdownRight);
    }

    // Chờ bubble chính thức xuất hiện (sau bubble tạm "...")
    public boolean waitForOfficialBubble(int initialCount) {
        try {
            wait.until(d -> getBubbleCount() > initialCount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
