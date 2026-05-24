import { Page } from '@playwright/test';
import { env } from '../utils/env.config';

export async function loginMazii(page: Page): Promise<void> {
  const currentUrl = page.url();

  // Nếu đã ở /translate (trang dịch), thì đã login, skip
  if (currentUrl.includes('/translate') || currentUrl.includes('/dich')) {
    return;
  }

  // Navigate directly to translate page to check auth
  await page.goto(`${env.maziiBaseUrl}/vi-VN/translate`, { waitUntil: 'load' });

  const pageUrl = page.url();

  // If redirected to login, do login flow
  if (pageUrl.includes('/user/login')) {
    // Wait for email input to be visible
    await page.locator('input[placeholder*="email"]').waitFor({ state: 'visible', timeout: 15000 });

    // Fill credentials
    const emailInput = page.locator('input[placeholder*="email"]').first();
    await emailInput.fill(env.maziiEmail);

    const passwordInput = page.locator('input[placeholder*="mật khẩu"], input[placeholder*="password"]').first();
    await passwordInput.fill(env.maziiPassword || 'defaultPassword123');

    // Click login button
    const submitButton = page.locator('button:has-text("Đăng nhập"), button[type="submit"]').first();
    await submitButton.click();

    // Wait for page to load after login
    await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});
    await page.waitForLoadState('load');
  }

  // Navigate to translate page if not there
  const finalUrl = page.url();
  if (!finalUrl.includes('/translate') && !finalUrl.includes('/dich')) {
    await page.goto(`${env.maziiBaseUrl}/vi-VN/translate`, { waitUntil: 'load' });
  }

  // Wait for page to fully render
  await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

  // Wait for translation textarea to be visible
  await page.locator('textarea[placeholder*="Nhập"], textarea[placeholder*="Enter"], textarea:first-of-type').first().waitFor({ state: 'visible', timeout: 15000 });
}
