/**
 * Mazii Login Fixture
 * Provides pre-configured MaziiLoginPage for test specs
 * Note: No pre-login required since we're testing the login form itself
 */

import { test as base } from '@playwright/test';
import { MaziiLoginPage } from '../pages/mazii/mazii-login.page';

type MaziiLoginFixtures = {
  maziiLoginPage: MaziiLoginPage;
};

/**
 * Extended test with MaziiLoginPage fixture
 * Fixture initializes login page and navigates to /vi-VN/user/login
 */
export const test = base.extend<MaziiLoginFixtures>({
  maziiLoginPage: async ({ page }, use) => {
    const loginPage = new MaziiLoginPage(page);
    await loginPage.goto();
    await use(loginPage);
  },
});

export { expect } from '@playwright/test';
