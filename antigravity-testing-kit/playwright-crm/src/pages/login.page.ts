import { Page, expect } from '@playwright/test';
import { BasePage } from './base.page';

export class LoginPage extends BasePage {
  // RECON: verify all locators below against real DOM
  private readonly emailInput = this.page.locator('input[name="email"]');
  private readonly passwordInput = this.page.locator('input[name="password"]');
  private readonly loginButton = this.page.getByRole('button', { name: /login/i });

  async goto(): Promise<void> {
    await this.navigate('/admin/authentication');
  }

  async login(email: string, password: string): Promise<void> {
    await this.emailInput.fill(email);
    await this.passwordInput.fill(password);
    await this.loginButton.click();
    await expect(this.page).toHaveURL(/admin(?!\/authentication)/, { timeout: 10000 });
  }
}
