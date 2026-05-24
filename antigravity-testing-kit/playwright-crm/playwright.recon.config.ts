import { defineConfig, devices } from '@playwright/test';
import { loadEnv } from './src/utils/env.config';

loadEnv();

export default defineConfig({
  testDir: './recon',
  fullyParallel: false,
  forbidOnly: false,
  retries: 0,
  workers: 1,
  timeout: 120000,
  reporter: [['list']],

  use: {
    baseURL: process.env.MAZII_BASE_URL || 'https://beta.mazii.net',
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'off',
    viewport: { width: 1440, height: 900 },
    headless: false,
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],

  outputDir: 'test-results/',
});
