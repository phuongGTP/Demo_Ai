import { Page, Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';

export class MaziiTranslatePage extends BasePage {
  // Language selection
  get sourceLangButton(): Locator {
    // Source language button - left dropdown button in translation area
    return this.page.locator('button').filter({ hasText: /^(Japanese|English|Chinese|Vietnamese|Spanish|French|German|Korean|Thai|Arabic|Russian|Portuguese|Italian)/ }).first();
  }

  get targetLangButton(): Locator {
    // Target language button - second dropdown button in translation area
    return this.page.locator('button').filter({ hasText: /^(Japanese|English|Chinese|Vietnamese|Spanish|French|German|Korean|Thai|Arabic|Russian|Portuguese|Italian)/ }).nth(1);
  }

  get swapButton(): Locator {
    return this.page.locator('button:has-text("⇄"), button[aria-label*="swap"], [data-testid="swap"]').first();
  }

  // Input
  get sourceTextarea(): Locator {
    return this.page.locator('textarea[placeholder*="Nhập"], textarea:first-of-type').first();
  }

  get clearButton(): Locator {
    return this.sourceTextarea.locator('+ button, ~ button:has-text("✕")').first();
  }

  get charCounterDisplay(): Locator {
    return this.page.locator('[data-testid="char-count"], text=/\\d+\\/5000/').first();
  }

  // Toolbar buttons
  get voiceButton(): Locator {
    return this.page.locator('button[aria-label*="voice"], button[aria-label*="ghi âm"], svg:has-text("🎤")').first();
  }

  get handwritingButton(): Locator {
    return this.page.locator('button[aria-label*="handwriting"], button[aria-label*="vẽ tay"]').first();
  }

  get radicalButton(): Locator {
    return this.page.locator('button[aria-label*="radical"], button[aria-label*="bộ thủ"]').first();
  }

  // Main translate button
  get translateButton(): Locator {
    return this.page.locator('button:has-text("Dịch"), button[type="button"]:has-text("Dịch")').first();
  }

  // Result area
  get resultColumn(): Locator {
    return this.page.locator('[data-testid="result-column"]').first();
  }

  get resultText(): Locator {
    // Result text appears in the result column, typically in a div or paragraph next to the target language button
    // Look for elements in the result area (right side) that contain the translation
    return this.page.locator('div:has(button:has-text("Vietnamese"), button:has-text("English"), button:has-text("Japanese"))').locator('p, div').filter({ hasText: /\S/ }).nth(1);
  }

  get emptyState(): Locator {
    return this.page.locator('[data-testid="empty-state"], text=/Nhập văn bản|Enter text/').first();
  }

  get skeletonLoader(): Locator {
    return this.resultColumn.locator('[data-testid="skeleton"], div[class*="skeleton"], div[class*="loading"]').first();
  }

  // Result card actions
  get copyButton(): Locator {
    return this.resultColumn.locator('button[aria-label*="copy"], button[title*="sao chép"]').first();
  }

  get pronunciationButton(): Locator {
    return this.resultColumn.locator('button[aria-label*="pronunciation"], button[title*="phát âm"]').first();
  }

  get analyzeButton(): Locator {
    return this.resultColumn.locator('button:has-text("Phân tích")').first();
  }

  get grammarButton(): Locator {
    return this.resultColumn.locator('button:has-text("Kiểm tra ngữ pháp")').first();
  }

  get contrastButton(): Locator {
    return this.resultColumn.locator('button:has-text("Tương phản")').first();
  }

  get modelDropdown(): Locator {
    return this.page.locator(
      // Priority 1: data-testid (most reliable)
      '[data-testid="model-selector"],' +
      '[data-testid="model-dropdown"],' +
      // Priority 2: aria-label (accessibility)
      'button[aria-label*="model" i],' +
      'button[aria-label*="translator" i],' +
      // Priority 3: class-based
      'button[class*="model"],' +
      'button[class*="ModelSelector"],' +
      // Priority 4: has structure (nested pattern)
      'button:has(generic:has-text("Mazii Translator")),' +
      'button:has-text("Mazii Translator"),' +
      'button:has-text("Mazii")'
    ).first();
  }

  // Modals & Drawers
  get voiceModal(): Locator {
    return this.page.locator('[data-testid="voice-modal"], div[role="dialog"]:has-text("ghi âm")').first();
  }

  get handwritingModal(): Locator {
    return this.page.locator('[data-testid="handwriting-modal"], div[role="dialog"]:has-text("vẽ tay")').first();
  }

  get radicalModal(): Locator {
    return this.page.locator('[data-testid="radical-modal"], div[role="dialog"]:has-text("bộ thủ")').first();
  }

  get modelDrawer(): Locator {
    return this.page.locator(
      // Priority 1: data-testid
      '[data-testid="model-drawer"],' +
      '[data-testid="model-modal"],' +
      '[data-testid="model-selector-drawer"],' +
      // Priority 2: find dialog that has model-related content (exclude nav)
      'div[role="dialog"]:not(:has(a[href*="/translate"], a[href*="/search"])):has(button:has-text("AI")),' +
      'div[role="dialog"]:not(:has(a[href*="/translate"], a[href*="/search"])):has-text("Base Translator"),' +
      // Priority 3: class-based (specific model drawer classes)
      'div[class*="ModelDrawer"],' +
      'div[class*="model-drawer"],' +
      'div[class*="SelectModelDrawer"],' +
      // Priority 4: fallback (any dialog with model options)
      'div[role="dialog"]:has(button:has-text("AI Base")),' +
      'div[role="dialog"]:has(button:has-text("NMT"))'
    ).first();
  }

  get paywallScreen(): Locator {
    return this.page.locator('[data-testid="paywall"], div:has-text("nâng cấp"), div:has-text("upgrade")').first();
  }

  // History section
  get historySection(): Locator {
    return this.page.locator('[data-testid="history-section"], div:has-text("Lịch sử")').first();
  }

  get historyItems(): Locator {
    return this.page.locator('[data-testid*="history-item"], ul li, div[data-history]');
  }

  get clearHistoryButton(): Locator {
    return this.historySection.locator('button:has-text("Xoá")').first();
  }

  // Toast notification
  get toastMessage(): Locator {
    return this.page.locator('[role="alert"], div[class*="toast"], div[class*="notification"]').first();
  }

  // Methods
  async goto(): Promise<void> {
    await this.page.goto('https://beta.mazii.net/vi-VN/translate', { waitUntil: 'load' });

    // Wait for page to fully load
    await this.page.waitForLoadState('networkidle', { timeout: 20000 }).catch(() => {});

    // Wait for textarea with multiple fallback selectors
    const textareaLocator = this.page.locator(
      'textarea[placeholder*="Nhập"],' +
      'textarea[placeholder*="Enter"],' +
      'textarea:first-of-type'
    );

    await textareaLocator.first().waitFor({ state: 'visible', timeout: 15000 }).catch(() => {
      console.warn('Textarea did not appear within 15s');
    });
  }

  async inputSourceText(text: string): Promise<void> {
    await this.sourceTextarea.click();
    await this.sourceTextarea.fill(text);
  }

  async clearSourceText(): Promise<void> {
    await this.clearButton.click();
  }

  async isTranslateButtonEnabled(): Promise<boolean> {
    return !this.translateButton.isDisabled();
  }

  async clickTranslate(): Promise<void> {
    await this.translateButton.waitFor({ state: 'visible', timeout: 10000 });
    await this.translateButton.click({ timeout: 5000 });
  }

  async selectSourceLanguage(language: string): Promise<void> {
    await this.sourceLangButton.waitFor({ state: 'visible', timeout: 10000 });
    await this.sourceLangButton.click();

    // Wait for dropdown to open
    await this.page.waitForTimeout(500);

    // Click language option with fallback selectors
    const langSelector = this.page.locator(
      `button:has-text("${language}")` +
      `, div:has-text("${language}")` +
      `, li:has-text("${language}")` +
      `, [role="option"]:has-text("${language}")`
    );

    await langSelector.first().waitFor({ state: 'visible', timeout: 10000 });
    await langSelector.first().click();
  }

  async selectTargetLanguage(language: string): Promise<void> {
    await this.targetLangButton.waitFor({ state: 'visible', timeout: 10000 });
    await this.targetLangButton.click();

    // Wait for dropdown to open
    await this.page.waitForTimeout(500);

    // Click language option with fallback selectors
    const langSelector = this.page.locator(
      `button:has-text("${language}")` +
      `, div:has-text("${language}")` +
      `, li:has-text("${language}")` +
      `, [role="option"]:has-text("${language}")`
    );

    await langSelector.first().waitFor({ state: 'visible', timeout: 10000 });
    await langSelector.first().click();
  }

  async clickSwap(): Promise<void> {
    await this.swapButton.click();
  }

  async openVoiceModal(): Promise<void> {
    await this.voiceButton.click();
    await this.voiceModal.waitFor({ state: 'visible', timeout: 5000 });
  }

  async openHandwritingModal(): Promise<void> {
    await this.handwritingButton.click();
    await this.handwritingModal.waitFor({ state: 'visible', timeout: 5000 });
  }

  async openRadicalModal(): Promise<void> {
    await this.radicalButton.click();
    await this.radicalModal.waitFor({ state: 'visible', timeout: 5000 });
  }

  async closeModal(): Promise<void> {
    const closeButton = this.page.locator('button[aria-label="close"], button:has-text("✕"), [data-testid="close"]').first();
    if (await closeButton.isVisible({ timeout: 2000 }).catch(() => false)) {
      await closeButton.click();
    }
    await this.page.keyboard.press('Escape');
  }

  async waitForResult(): Promise<void> {
    // Wait for page to stabilize
    await this.page.waitForLoadState('networkidle', { timeout: 30000 }).catch(() => {});

    // Wait for skeleton to disappear
    await this.skeletonLoader.waitFor({ state: 'hidden', timeout: 30000 }).catch(() => {
      console.warn('Skeleton loader did not hide within 30s');
    });

    // Wait for result text to appear
    await this.resultText.waitFor({ state: 'visible', timeout: 15000 }).catch(() => {
      console.warn('Result text did not appear within 15s');
    });

    // Give a moment for any animations to complete
    await this.page.waitForTimeout(800);
  }

  async getResultText(): Promise<string> {
    await this.resultText.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
    return this.resultText.innerText();
  }

  async isEmptyStateVisible(): Promise<boolean> {
    return this.emptyState.isVisible({ timeout: 2000 }).catch(() => false);
  }

  async copyResult(): Promise<void> {
    await this.copyButton.click();
  }

  async playPronunciation(): Promise<void> {
    await this.pronunciationButton.click();
  }

  async openGrammarChecker(): Promise<void> {
    await this.grammarButton.click();
  }

  async openAnalyze(): Promise<void> {
    await this.analyzeButton.click();
  }

  async openContrast(): Promise<void> {
    await this.contrastButton.click();
  }

  async openModelSelector(): Promise<void> {
    // Step 1: Ensure result column is visible
    try {
      await this.resultColumn.waitFor({ state: 'visible', timeout: 10000 });
    } catch {
      // Result column might not have data-testid, try to wait for any result
      await this.page.waitForSelector('button:has-text("Mazii")', { timeout: 10000 }).catch(() => {});
    }

    // Step 2: Wait for model dropdown to be visible
    await this.modelDropdown.waitFor({ state: 'visible', timeout: 10000 });

    // Step 3: Verify button is enabled
    const isEnabled = await this.modelDropdown.isEnabled().catch(() => false);
    if (!isEnabled) {
      throw new Error('Model dropdown button is disabled');
    }

    // Step 4: Click with error handling
    let lastError: Error | null = null;
    for (let attempt = 0; attempt < 3; attempt++) {
      try {
        console.log(`[openModelSelector] Attempt ${attempt + 1}/3: Clicking model dropdown button`);
        await this.modelDropdown.click({ timeout: 5000 });
        console.log(`[openModelSelector] Click successful, waiting for drawer`);
        break;
      } catch (error) {
        lastError = error as Error;
        console.warn(`[Attempt ${attempt + 1}/3] Model dropdown click failed:`, lastError.message);

        if (attempt < 2) {
          // Wait for page to stabilize and retry
          await this.page.waitForLoadState('networkidle', { timeout: 5000 }).catch(() => {});
          await this.page.waitForTimeout(500);
        }
      }
    }

    if (lastError) {
      throw lastError;
    }

    // Step 5: Wait for model drawer to open
    console.log('[openModelSelector] Waiting for model drawer...');
    await this.page.waitForTimeout(1000);

    try {
      await this.modelDrawer.waitFor({ state: 'visible', timeout: 10000 });
      console.log('[openModelSelector] Model drawer opened successfully');
    } catch (error) {
      console.error('[openModelSelector] Model drawer did not open', error);
      // Save screenshot for debugging
      await this.page.screenshot({ path: 'test-results/model-drawer-failed.png' }).catch(() => {});
      throw error;
    }
  }

  async selectModel(modelName: string): Promise<void> {
    console.log(`[selectModel] Opening model selector for model: ${modelName}`);
    await this.openModelSelector();

    console.log(`[selectModel] Searching for model option: ${modelName}`);
    await this.page.waitForTimeout(1000);

    // Search for model option from page (drawer may be hard to locate)
    // Try to find by exact text first, then by partial
    const modelOption = this.page.locator(
      `button:has-text("${modelName}"),` +
      `li:has-text("${modelName}"),` +
      `div[class*="model"]:has-text("${modelName}"),` +
      `div[class*="option"]:has-text("${modelName}")`
    );

    try {
      await modelOption.first().waitFor({ state: 'visible', timeout: 10000 });
      console.log(`[selectModel] Found model option, clicking it`);
      await modelOption.first().click({ timeout: 5000 });
      console.log(`[selectModel] Model option clicked`);
    } catch (error) {
      console.error(`[selectModel] Failed to find model option "${modelName}"`);

      // List all buttons with text visible on page
      const allTexts = await this.page.locator('button, li, div[class*="option"]').allInnerTexts();
      console.log(`[selectModel] All visible text elements: ${allTexts.filter(t => t.trim()).length}`);
      for (const text of allTexts.filter(t => t.includes('AI') || t.includes('Model') || t.includes('Translator')).slice(0, 15)) {
        console.log(`  - "${text.trim().slice(0, 80)}"`);
      }

      // Save screenshot for debugging
      await this.page.screenshot({ path: 'test-results/model-options-failed.png' }).catch(() => {});

      throw error;
    }
  }

  async isPaywallVisible(): Promise<boolean> {
    return this.paywallScreen.isVisible({ timeout: 2000 }).catch(() => false);
  }

  async getToastMessage(): Promise<string> {
    return this.toastMessage.innerText();
  }

  async getCharCount(): Promise<string> {
    return this.charCounterDisplay.innerText();
  }

  async isHandwritingButtonDisabled(): Promise<boolean> {
    return this.handwritingButton.isDisabled();
  }

  async isRadicalButtonDisabled(): Promise<boolean> {
    return this.radicalButton.isDisabled();
  }

  async clickHistoryItem(index: number): Promise<void> {
    const item = this.historyItems.nth(index);
    await item.click();
  }

  async deleteHistoryItem(index: number): Promise<void> {
    const item = this.historyItems.nth(index);
    const deleteBtn = item.locator('button[aria-label*="delete"], button[aria-label*="xoá"]').first();
    await deleteBtn.click();
  }

  async clearAllHistory(): Promise<void> {
    await this.clearHistoryButton.click();
    // Confirmation dialog nếu có
    const confirmBtn = this.page.locator('button:has-text("Xác nhận"), button:has-text("Confirm")').first();
    if (await confirmBtn.isVisible({ timeout: 2000 }).catch(() => false)) {
      await confirmBtn.click();
    }
  }

  async waitForApiTimeout(timeout: number = 30000): Promise<void> {
    await this.page.waitForTimeout(timeout);
  }

  async isSkeletonVisible(): Promise<boolean> {
    return this.skeletonLoader.isVisible({ timeout: 2000 }).catch(() => false);
  }

  async getFuriganaText(): Promise<string | null> {
    const furigana = this.sourceTextarea.locator('ruby rt, small, [data-testid*="furigana"]').first();
    return (await furigana.isVisible({ timeout: 2000 }).catch(() => false)) ? furigana.innerText() : null;
  }

  async clickKanji(kanji: string): Promise<void> {
    const kanjiElement = this.resultColumn.locator(`text="${kanji}"`).first();
    await kanjiElement.click();
  }
}
