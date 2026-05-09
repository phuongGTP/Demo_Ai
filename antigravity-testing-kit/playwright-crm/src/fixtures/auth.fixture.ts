import { Page } from '@playwright/test';
import { LoginPage } from '../pages/login.page';
import { env } from '../utils/env.config';

export async function loginAsAdmin(page: Page): Promise<void> {
  const url = page.url();
  if (url.includes('/admin') && !url.includes('/admin/authentication')) {
    return;
  }
  const loginPage = new LoginPage(page);
  await loginPage.goto();
  await loginPage.login(env.adminEmail, env.adminPassword);
}
