import { test as base } from '@playwright/test';
import { loginMazii } from './mazii-auth.fixture';
import { MaziiTranslatePage } from '../pages/mazii/mazii-translate.page';

type MaziiFixtures = {
  maziiTranslatePage: MaziiTranslatePage;
};

export const test = base.extend<MaziiFixtures>({
  maziiTranslatePage: async ({ page }, use) => {
    await loginMazii(page);
    const translatePage = new MaziiTranslatePage(page);
    await use(translatePage);
  },
});

export { expect } from '@playwright/test';
