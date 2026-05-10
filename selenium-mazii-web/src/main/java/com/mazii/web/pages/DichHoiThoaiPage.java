package com.mazii.web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

/**
 * Page object cho màn hình Dịch hội thoại — beta.mazii.net/vi-VN/conversation-translation
 *
 * Layout thực tế (verified 2026-05-10):
 *   [🎤 Japanese btn-danger]  [⌨ btn-action toggle]  [🎤 Vietnamese btn-primary]
 *   [Japanese dropdown ▼]                              [Vietnamese dropdown ▼]
 *
 * App: Angular 18 — không có data-testid, dùng CSS class selectors.
 */
public class DichHoiThoaiPage extends BasePage {

    // ── Empty state ────────────────────────────────────────────────
    private static final By EMPTY_STATE       = By.cssSelector("div.intro-container");
    private static final By EMPTY_STATE_TEXT  = By.cssSelector("div.intro-container p.h4");

    // ── Mic buttons ──────────────────────────────────────────────
    // Japanese mic (left, red) / Vietnamese mic (right, blue)
    private static final By MIC_JAPANESE   = By.cssSelector("app-conversation-translation .box-language:first-of-type button.rounded-circle");
    private static final By MIC_VIETNAMESE = By.cssSelector("app-conversation-translation .box-language:last-of-type button.rounded-circle");

    // Pause/stop state — mic button switches class when recording
    // Fallback: detect if mic icon changes to pause icon
    private static final By MIC_JAPANESE_RECORDING   = By.cssSelector("app-conversation-translation .box-language:first-of-type button.rounded-circle.active, app-conversation-translation .box-language:first-of-type button[class*='pause'], app-conversation-translation .box-language:first-of-type button.recording");
    private static final By MIC_VIETNAMESE_RECORDING = By.cssSelector("app-conversation-translation .box-language:last-of-type button.rounded-circle.active, app-conversation-translation .box-language:last-of-type button[class*='pause'], app-conversation-translation .box-language:last-of-type button.recording");

    // ── Keyboard toggle ───────────────────────────────────────────
    // In mic mode: button.btn-action (keyboard icon, center position)
    private static final By KEYBOARD_TOGGLE = By.cssSelector("button.btn-action");
    // In keyboard mode: large mic button (p-2) in .mb-2 div → switches BACK to mic mode
    // Color varies (btn-danger for JP, btn-primary for VI) → match by size class only
    private static final By MIC_MODE_TOGGLE = By.cssSelector("app-conversation-translation .mb-2 > button.rounded-circle");

    // ── Keyboard mode — single shared input + send (both langs use same field)
    // Appears inside .input-container when keyboard mode is active
    private static final By KEYBOARD_CONTAINER        = By.cssSelector("app-conversation-translation .input-container");
    private static final By KEYBOARD_INPUT            = By.cssSelector("app-conversation-translation .input-container input.form-control");
    // Send button is DIRECT child of .input-container (not inside .swap-language), has plane icon, p-2
    private static final By KEYBOARD_SEND             = By.cssSelector("app-conversation-translation .input-container > button.btn-primary.rounded-circle");
    // Keep aliases so test method names still compile
    private static final By KEYBOARD_INPUT_JAPANESE   = KEYBOARD_INPUT;
    private static final By KEYBOARD_INPUT_VIETNAMESE = KEYBOARD_INPUT;
    private static final By KEYBOARD_SEND_JAPANESE    = KEYBOARD_SEND;
    private static final By KEYBOARD_SEND_VIETNAMESE  = KEYBOARD_SEND;

    // ── Language dropdowns ────────────────────────────────────────
    private static final By LANG_BTN_JAPANESE    = By.cssSelector("app-conversation-translation .box-language:first-of-type button.txt-lang");
    private static final By LANG_BTN_VIETNAMESE  = By.cssSelector("app-conversation-translation .box-language:last-of-type button.txt-lang");
    private static final By LANG_DROPDOWN_ITEM   = By.cssSelector("app-conversation-translation .dropdown-menu .dropdown-item");

    // ── Bubble area ───────────────────────────────────────────────
    // Real class (verified via diagnostic 2026-05-10): div.chat-message.user-message|bot-message
    private static final By CHAT_SCROLL    = By.cssSelector("div.chat-scroll");
    private static final By BUBBLE_ITEM    = By.cssSelector("div.chat-scroll div.chat-message");
    private static final By BUBBLE_PENDING = By.cssSelector("[class*='pending'], [class*='loading'], [class*='typing']");

    // ── Bubble action buttons ─────────────────────────────────────
    // Real selectors (verified via diagnostic 2026-05-10):
    // Audio button: button.btn-audio (with ic_volume.svg)
    // Copy button: button.btn.p-0 (with ic_copy.svg) - multiple per bubble
    // Detail/translate button: not yet confirmed; guessed btn-go or btn-detail
    private static final By BUBBLE_PLAY   = By.cssSelector("div.chat-scroll button.btn-audio");
    private static final By BUBBLE_COPY   = By.cssSelector("div.chat-scroll div.d-flex.gap-2:first-child button.btn.p-0");
    private static final By BUBBLE_DETAIL = By.cssSelector("div.chat-scroll [class*='btn-detail'], div.chat-scroll button.btn-go, div.chat-scroll button[class*='translate']");

    // ── Trash / Delete ────────────────────────────────────────────
    // Trash button appears when bubbles exist (not in empty state)
    private static final By TRASH_BUTTON  = By.cssSelector("button.btn-trash, button[class*='trash'], button[class*='delete-all']");
    private static final By DELETE_MODAL  = By.cssSelector(".modal.show, [class*='confirm-modal'], dialog[open]");
    private static final By DELETE_CONFIRM = By.cssSelector(".modal.show .btn-submit, .modal.show .btn-primary, [class*='confirm-delete']");
    private static final By DELETE_CANCEL  = By.cssSelector(".modal.show .btn-cancel, .modal.show .btn-secondary, [class*='cancel-delete']");

    // ── Toast / Alert ─────────────────────────────────────────────
    private static final By TOAST = By.cssSelector("[class*='toast'], [class*='alert-msg'], [role='alert']");

    // ── Paywall ───────────────────────────────────────────────────
    private static final By PAYWALL = By.cssSelector("[class*='paywall'], [class*='upgrade'], .modal-upgrade");

    public DichHoiThoaiPage(WebDriver driver) {
        super(driver);
    }

    // ── Empty state ──────────────────────────────────────────────
    public boolean isEmptyState() {
        return isDisplayed(EMPTY_STATE, 5);
    }

    public String getEmptyStateText() {
        return getText(EMPTY_STATE_TEXT);
    }

    // ── Mic ──────────────────────────────────────────────────────
    public void clickMicJapanese() {
        click(MIC_JAPANESE);
    }

    public void clickMicVietnamese() {
        click(MIC_VIETNAMESE);
    }

    /**
     * Detect recording state: checks if the mic button's class has changed
     * compared to idle state (btn-danger for JP, btn-primary for VI).
     */
    public boolean isMicJapaneseRecording() {
        // When recording, class changes or a recording indicator appears
        if (isDisplayed(MIC_JAPANESE_RECORDING, 2)) return true;
        // Fallback: check if img src has changed to a pause icon
        try {
            WebElement btn = driver.findElement(MIC_JAPANESE);
            WebElement img = btn.findElement(By.tagName("img"));
            String src = img.getAttribute("src");
            return src != null && (src.contains("pause") || src.contains("stop") || src.contains("record"));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isMicVietnameseRecording() {
        if (isDisplayed(MIC_VIETNAMESE_RECORDING, 2)) return true;
        try {
            WebElement btn = driver.findElement(MIC_VIETNAMESE);
            WebElement img = btn.findElement(By.tagName("img"));
            String src = img.getAttribute("src");
            return src != null && (src.contains("pause") || src.contains("stop") || src.contains("record"));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isMicJapaneseIdle() {
        return isDisplayed(MIC_JAPANESE, 3) && !isMicJapaneseRecording();
    }

    public boolean isMicVietnameseIdle() {
        return isDisplayed(MIC_VIETNAMESE, 3) && !isMicVietnameseRecording();
    }

    // ── Keyboard toggle ───────────────────────────────────────────
    public void clickKeyboardToggle() {
        if (isDisplayed(KEYBOARD_TOGGLE, 2)) {
            // Mic mode → switch to keyboard mode (btn-action)
            click(KEYBOARD_TOGGLE);
        } else {
            // Keyboard mode → switch back to mic mode via large mic-icon button in .mb-2
            // Use waitVisible (not waitClickable) + jsClick to avoid flaky interactability check
            waitVisible(MIC_MODE_TOGGLE);
            jsClick(MIC_MODE_TOGGLE);
        }
    }

    public boolean isKeyboardToggleVisible() {
        return isDisplayed(KEYBOARD_TOGGLE, 3);
    }

    public boolean isInKeyboardMode() {
        return isDisplayed(KEYBOARD_CONTAINER, 5);
    }

    public boolean isInMicMode() {
        return isDisplayed(KEYBOARD_TOGGLE, 5);
    }

    // Language selector buttons inside input-container's swap-language row
    // [0] = JP mic (btn-danger p-1), [1] = swap (btn p-0), [2] = VI mic (btn-primary p-1)
    private static final By SWAP_LANG_JP = By.cssSelector("app-conversation-translation .swap-language > button.btn-danger.rounded-circle");
    private static final By SWAP_LANG_VI = By.cssSelector("app-conversation-translation .swap-language > button.btn-primary.rounded-circle");

    // ── Keyboard input ────────────────────────────────────────────
    public void typeInJapaneseInput(String text) {
        // Click JP lang button to activate Japanese input mode
        if (isDisplayed(SWAP_LANG_JP, 2)) jsClick(SWAP_LANG_JP);
        type(KEYBOARD_INPUT_JAPANESE, text);
    }

    public void typeInVietnameseInput(String text) {
        // Click VI lang button to activate Vietnamese input mode
        if (isDisplayed(SWAP_LANG_VI, 2)) jsClick(SWAP_LANG_VI);
        type(KEYBOARD_INPUT_VIETNAMESE, text);
    }

    public void clickSendJapanese() {
        click(KEYBOARD_SEND_JAPANESE);
    }

    public void clickSendVietnamese() {
        click(KEYBOARD_SEND_VIETNAMESE);
    }

    public void pressEnterInJapaneseInput() {
        if (isDisplayed(SWAP_LANG_JP, 2)) jsClick(SWAP_LANG_JP);
        waitVisible(KEYBOARD_INPUT_JAPANESE).sendKeys(Keys.RETURN);
    }

    public void pressEnterInVietnameseInput() {
        if (isDisplayed(SWAP_LANG_VI, 2)) jsClick(SWAP_LANG_VI);
        waitVisible(KEYBOARD_INPUT_VIETNAMESE).sendKeys(Keys.RETURN);
    }

    public String getJapaneseInputValue() {
        return waitVisible(KEYBOARD_INPUT).getAttribute("value");
    }

    public boolean isSendJapaneseDisabled() {
        WebElement btn = waitVisible(KEYBOARD_SEND);
        return btn.getAttribute("disabled") != null;
    }

    // ── Language dropdowns ────────────────────────────────────────
    public String getLanguageJapanese() {
        return getText(LANG_BTN_JAPANESE);
    }

    public String getLanguageVietnamese() {
        return getText(LANG_BTN_VIETNAMESE);
    }

    public void selectLanguageJapanese(String language) {
        click(LANG_BTN_JAPANESE);
        selectDropdownItem(language);
    }

    public void selectLanguageVietnamese(String language) {
        click(LANG_BTN_VIETNAMESE);
        selectDropdownItem(language);
    }

    private void selectDropdownItem(String language) {
        // Dropdown items are always in DOM (no *ngIf) but CSS-hidden when closed.
        // Use presenceOf (not visibilityOf) + JS click to select regardless of CSS state.
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(LANG_DROPDOWN_ITEM));
        List<WebElement> items = driver.findElements(LANG_DROPDOWN_ITEM);
        for (WebElement item : items) {
            String text = item.getText().trim();
            if (text.equals(language) || text.contains(language)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
                return;
            }
        }
        throw new NoSuchElementException("Language option not found: " + language);
    }

    // ── Bubbles ───────────────────────────────────────────────────
    public int getBubbleCount() {
        return findAll(BUBBLE_ITEM).size();
    }

    public boolean hasPendingBubble() {
        return isDisplayed(BUBBLE_PENDING, 5);
    }

    public boolean waitForNewBubble(int initialCount) {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> findAll(BUBBLE_ITEM).size() > initialCount);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ── Trash / Delete ────────────────────────────────────────────
    public void hoverTrashButton() {
        hover(TRASH_BUTTON);
    }

    public void clickTrashButton() {
        click(TRASH_BUTTON);
    }

    public boolean isTrashButtonVisible() {
        return isDisplayed(TRASH_BUTTON, 3);
    }

    public boolean isDeleteModalVisible() {
        return isDisplayed(DELETE_MODAL, 5);
    }

    public void confirmDelete() {
        click(DELETE_CONFIRM);
    }

    public void cancelDelete() {
        click(DELETE_CANCEL);
    }

    // ── Toast ─────────────────────────────────────────────────────
    public boolean isToastVisible(String containsText) {
        try {
            wait.until(d -> {
                List<WebElement> toasts = d.findElements(TOAST);
                return toasts.stream().anyMatch(t -> t.getText().contains(containsText));
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isNetworkErrorToastVisible() {
        return isToastVisible("kết nối mạng") || isToastVisible("mạng");
    }

    public boolean isCopiedToastVisible() {
        return isToastVisible("sao chép") || isToastVisible("copied");
    }

    // ── Paywall ───────────────────────────────────────────────────
    public boolean isPaywallDisplayed() {
        return isDisplayed(PAYWALL, 5);
    }

    // ── Bubble actions ────────────────────────────────────────────
    public void hoverFirstBubble() {
        List<WebElement> bubbles = findAll(BUBBLE_ITEM);
        if (bubbles.isEmpty()) throw new NoSuchElementException("No bubbles on screen");
        hover(bubbles.get(0));
    }

    public boolean isBubbleDetailButtonVisible() {
        return isDisplayed(BUBBLE_DETAIL, 3);
    }

    public void clickBubbleDetailButton() {
        click(BUBBLE_DETAIL);
    }

    public void clickBubblePlay() {
        click(BUBBLE_PLAY);
    }

    public void clickBubbleCopy() {
        click(BUBBLE_COPY);
    }
}
