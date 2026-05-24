/**
 * Mazii Login Page Object
 * Handles all login form interactions including email/password fields,
 * error messages, password visibility, and navigation links.
 */

import { Page, Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';
import { env } from '../../utils/env.config';

export class MaziiLoginPage extends BasePage {
  // Form inputs
  private readonly emailInput: Locator = this.page.locator('#email');
  private readonly passwordInput: Locator = this.page.locator('#password');

  // Buttons & Links
  private readonly loginButton: Locator = this.page.locator('button[type="submit"].btn-login');
  private readonly eyeToggleButton: Locator = this.page.locator('img[alt="icon eye"]').locator('xpath=ancestor::button').first();
  private readonly forgotPasswordLink: Locator = this.page.getByRole('link', { name: /quên mật khẩu/i });
  private readonly signupLink: Locator = this.page.locator('a:has-text("Đăng ký ngay")');
  private readonly googleButton: Locator = this.page.locator('button', { hasText: /google/i });
  private readonly appleButton: Locator = this.page.locator('button', { hasText: /apple/i });

  // Error messages - using more robust selectors
  private readonly emailErrorMessage: Locator = this.page.locator('input#email').locator('xpath=ancestor::div[contains(@class, "form-group")]//div[contains(@class, "invalid-feedback")]').first();
  private readonly passwordErrorMessage: Locator = this.page.locator('input#password').locator('xpath=ancestor::div[contains(@class, "form-group")]//div[contains(@class, "invalid-feedback")]').first();
  private readonly generalErrorMessage: Locator = this.page.locator('p:has-text("mật khẩu"), p:has-text("tài khoản"), [role="alert"]').first();

  // Validation feedback
  private readonly reCaptchaWidget: Locator = this.page.frameLocator('iframe[name*="captcha"]').first();

  constructor(protected readonly page: Page) {
    super(page);
  }

  /**
   * Navigate to login page
   */
  async goto(): Promise<void> {
    await this.page.goto(`${env.maziiBaseUrl}/vi-VN/user/login`);
    await this.waitForPageLoad();
  }

  /**
   * Fill email input field
   */
  async fillEmail(email: string): Promise<void> {
    await this.emailInput.clear();
    await this.emailInput.fill(email);
  }

  /**
   * Fill password input field
   */
  async fillPassword(password: string): Promise<void> {
    await this.passwordInput.clear();
    await this.passwordInput.fill(password);
  }

  /**
   * Perform complete login action
   */
  async login(email: string, password: string): Promise<void> {
    await this.fillEmail(email);
    await this.fillPassword(password);
    await this.clickLoginButton();
  }

  /**
   * Click login button
   */
  async clickLoginButton(): Promise<void> {
    await this.loginButton.click();
  }

  /**
   * Get email field validation error message
   */
  async getEmailError(): Promise<string> {
    try {
      await this.emailErrorMessage.waitFor({ state: 'visible', timeout: 3000 });
      return await this.emailErrorMessage.innerText();
    } catch {
      return '';
    }
  }

  /**
   * Get password field validation error message
   */
  async getPasswordError(): Promise<string> {
    try {
      await this.passwordErrorMessage.waitFor({ state: 'visible', timeout: 3000 });
      return await this.passwordErrorMessage.innerText();
    } catch {
      return '';
    }
  }

  /**
   * Get general form error message (login failed, etc.)
   */
  async getGeneralError(): Promise<string> {
    try {
      await this.generalErrorMessage.waitFor({ state: 'visible', timeout: 3000 });
      return await this.generalErrorMessage.innerText();
    } catch {
      return '';
    }
  }

  /**
   * Toggle password visibility (eye icon)
   */
  async togglePasswordVisibility(): Promise<void> {
    await this.eyeToggleButton.click();
  }

  /**
   * Check if password input is showing plain text or masked
   */
  async isPasswordVisible(): Promise<boolean> {
    const inputType = await this.passwordInput.getAttribute('type');
    return inputType === 'text';
  }

  /**
   * Check if login button is enabled
   */
  async isLoginButtonEnabled(): Promise<boolean> {
    const isDisabled = await this.loginButton.isDisabled();
    return !isDisabled;
  }

  /**
   * Click forgot password link
   */
  async clickForgotPasswordLink(): Promise<void> {
    await this.forgotPasswordLink.click();
    await this.page.waitForTimeout(1000);
  }

  /**
   * Click signup link
   */
  async clickSignupLink(): Promise<void> {
    await this.signupLink.click();
    await this.page.waitForTimeout(1000);
  }

  /**
   * Click Google OAuth button
   */
  async clickGoogleButton(): Promise<void> {
    await this.googleButton.click();
  }

  /**
   * Click Apple OAuth button
   */
  async clickAppleButton(): Promise<void> {
    await this.appleButton.click();
  }

  /**
   * Check if reCAPTCHA blocks login
   */
  async isRecaptchaBlocking(): Promise<boolean> {
    const hasRecaptcha = await this.waitForRecaptcha(2000);
    if (!hasRecaptcha) return false;

    // If reCAPTCHA present, check if login button is disabled
    const isDisabled = await this.loginButton.isDisabled().catch(() => false);
    return isDisabled;
  }

  /**
   * Wait for login success (redirect from login page)
   */
  async waitForLoginSuccess(timeout: number = 10000): Promise<void> {
    // Check if reCAPTCHA is blocking
    const blocked = await this.isRecaptchaBlocking();
    if (blocked) {
      throw new Error('Login blocked by reCAPTCHA - requires manual verification');
    }

    await expect(this.page).not.toHaveURL(/user\/login/, { timeout });
  }

  /**
   * Verify email field has validation error styling (red border/highlight)
   */
  async hasEmailError(): Promise<boolean> {
    try {
      // Check if input has error class or is marked invalid
      const hasError = await this.emailInput.evaluate(el => {
        const classList = el.className;
        return classList.includes('error') || classList.includes('is-invalid') || classList.includes('ng-invalid');
      });
      return hasError;
    } catch {
      return false;
    }
  }

  /**
   * Verify password field has validation error styling (red border/highlight)
   */
  async hasPasswordError(): Promise<boolean> {
    try {
      // Check if input has error class or is marked invalid
      const hasError = await this.passwordInput.evaluate(el => {
        const classList = el.className;
        return classList.includes('error') || classList.includes('is-invalid') || classList.includes('ng-invalid');
      });
      return hasError;
    } catch {
      return false;
    }
  }

  /**
   * Clear email field
   */
  async clearEmail(): Promise<void> {
    await this.emailInput.clear();
  }

  /**
   * Clear password field
   */
  async clearPassword(): Promise<void> {
    await this.passwordInput.clear();
  }

  /**
   * Get email input value
   */
  async getEmailValue(): Promise<string> {
    return await this.emailInput.inputValue();
  }

  /**
   * Get password input value
   */
  async getPasswordValue(): Promise<string> {
    return await this.passwordInput.inputValue();
  }

  /**
   * Wait for reCAPTCHA to be visible
   */
  async waitForRecaptcha(timeout: number = 5000): Promise<boolean> {
    try {
      // Check for reCAPTCHA iframe or badge
      const recaptchaIframe = this.page.frameLocator('iframe[title*="captcha"]').first();
      await recaptchaIframe.locator('body').waitFor({ state: 'visible', timeout });
      return true;
    } catch {
      // Alternative check: look for reCAPTCHA badge
      const badge = this.page.locator('[aria-label*="recaptcha"], .g-recaptcha').first();
      return await badge.isVisible({ timeout: 2000 }).catch(() => false);
    }
  }
}
