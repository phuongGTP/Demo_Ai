import { Page, Locator, expect } from '@playwright/test';

export class BasePage {
  constructor(protected readonly page: Page) {}

  async navigate(path: string): Promise<void> {
    await this.page.goto(path);
  }

  async waitForPageLoad(): Promise<void> {
    await this.page.waitForLoadState('networkidle');
  }

  async getFlashMessage(): Promise<string> {
    const flash = this.page.locator('.alert, .toast, [class*="alert"], [class*="flash"]').first();
    await expect(flash).toBeVisible({ timeout: 5000 });
    return flash.innerText();
  }

  async isVisible(locator: Locator): Promise<boolean> {
    return locator.isVisible();
  }
}
