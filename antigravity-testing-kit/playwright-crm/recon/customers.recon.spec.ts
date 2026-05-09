/**
 * LOCATOR RECON SCRIPT — Customers Module
 * Thu thập locators từ DOM thực, lưu vào recon/recon-output.json
 */

import { test } from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

const BASE_URL = process.env.BASE_URL ?? 'https://crm.anhtester.com';
const EMAIL = process.env.ADMIN_EMAIL ?? 'admin@example.com';
const PASSWORD = process.env.ADMIN_PASSWORD ?? '123456';

type ElementInfo = Record<string, string>;

async function extractElements(page: import('@playwright/test').Page, label: string, output: Record<string, unknown>) {
  const elements: ElementInfo[] = await page.evaluate(() => {
    const selectors = [
      'input', 'textarea', 'select', 'button', 'a[href]',
      '[data-testid]', '[aria-label]', '[role="tab"]',
      '.nav-link', '.btn', 'label',
    ];
    const results: ElementInfo[] = [];
    for (const sel of selectors) {
      document.querySelectorAll<HTMLElement>(sel).forEach((el) => {
        const entry: ElementInfo = { tag: el.tagName.toLowerCase() };
        if (el.id) entry.id = el.id;
        if ((el as HTMLInputElement).name) entry.name = (el as HTMLInputElement).name;
        if ((el as HTMLInputElement).type) entry.type = (el as HTMLInputElement).type;
        if (el.getAttribute('placeholder')) entry.placeholder = el.getAttribute('placeholder')!;
        if (el.getAttribute('aria-label')) entry.ariaLabel = el.getAttribute('aria-label')!;
        if (el.getAttribute('data-testid')) entry.testId = el.getAttribute('data-testid')!;
        if (el.getAttribute('href')) entry.href = el.getAttribute('href')!;
        const text = el.innerText?.trim().slice(0, 60);
        if (text) entry.text = text;
        const classes = el.className?.toString().trim().slice(0, 80);
        if (classes) entry.classes = classes;
        if (Object.keys(entry).length > 1) results.push(entry);
      });
    }
    return results;
  });
  output[label] = elements;
  console.log(`✅ [${label}] — Collected ${elements.length} elements`);
}

async function login(page: import('@playwright/test').Page) {
  await page.goto('/admin/authentication');
  await page.locator('input[name="email"]').fill(EMAIL);
  await page.locator('input[name="password"]').fill(PASSWORD);
  await page.getByRole('button', { name: /login/i }).click();
  await page.waitForURL(/admin(?!\/authentication)/, { timeout: 15000 });
}

function saveOutput(output: Record<string, unknown>) {
  const outputDir = path.resolve('recon');
  if (!fs.existsSync(outputDir)) fs.mkdirSync(outputDir, { recursive: true });
  const screenshotDir = path.resolve('recon/screenshots');
  if (!fs.existsSync(screenshotDir)) fs.mkdirSync(screenshotDir, { recursive: true });
  const outputPath = path.resolve('recon/recon-output.json');
  fs.writeFileSync(outputPath, JSON.stringify(output, null, 2), 'utf-8');
  console.log(`\n📄 Recon output saved to: ${outputPath}`);
}

test('RECON — Customers Module (all pages)', async ({ page }) => {
  const output: Record<string, unknown> = {};

  // ── Step 1: Login ─────────────────────────────────────────────────────────
  await page.goto(`${BASE_URL}/admin/authentication`);
  await extractElements(page, 'login_page', output);
  await page.screenshot({ path: 'recon/screenshots/01_login.png', fullPage: true });

  await login(page);

  // ── Step 2: Customer List Page ────────────────────────────────────────────
  await page.goto(`${BASE_URL}/admin/clients`);
  await page.waitForLoadState('networkidle');
  await extractElements(page, 'customer_list_page', output);
  await page.screenshot({ path: 'recon/screenshots/02_customer_list.png', fullPage: true });

  // ── Step 3: Customer Create Form ──────────────────────────────────────────
  await page.goto(`${BASE_URL}/admin/clients/client`);
  await page.waitForLoadState('networkidle');
  await extractElements(page, 'customer_form_details_tab', output);
  await page.screenshot({ path: 'recon/screenshots/03a_customer_form_details.png', fullPage: true });

  const billingTab = page.getByRole('link', { name: /billing/i });
  if (await billingTab.isVisible()) {
    await billingTab.click();
    await page.waitForTimeout(500);
    await extractElements(page, 'customer_form_billing_tab', output);
    await page.screenshot({ path: 'recon/screenshots/03b_customer_form_billing.png', fullPage: true });
  }

  // ── Step 4: Customer Detail Page ──────────────────────────────────────────
  await page.goto(`${BASE_URL}/admin/clients`);
  await page.waitForLoadState('networkidle');
  const firstCompanyLink = page.locator('table tbody tr:first-child td a').first();
  if (await firstCompanyLink.isVisible()) {
    await firstCompanyLink.click();
    await page.waitForLoadState('networkidle');
    output['customer_detail_url'] = page.url();
    await extractElements(page, 'customer_detail_page', output);
    await page.screenshot({ path: 'recon/screenshots/04_customer_detail.png', fullPage: true });

    const sidebarLinks = await page.locator('.client-profile-menu a, .nav-tabs a, [class*="sidebar"] a').allInnerTexts();
    output['sidebar_tab_texts'] = sidebarLinks;
  }

  // ── Step 5: Import Page ───────────────────────────────────────────────────
  await page.goto(`${BASE_URL}/admin/clients/import`);
  await page.waitForLoadState('networkidle');
  await extractElements(page, 'customer_import_page', output);
  await page.screenshot({ path: 'recon/screenshots/05_customer_import.png', fullPage: true });

  // ── Save all output ───────────────────────────────────────────────────────
  saveOutput(output);
  console.log('\n⏭️  Next step: Review recon-output.json and update POM locators.');
});
