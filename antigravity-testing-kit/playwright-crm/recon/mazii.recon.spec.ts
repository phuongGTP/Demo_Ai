/**
 * LOCATOR RECON SCRIPT — Mazii Translation Page
 * Thu thập locators từ DOM thực, lưu vào recon/mazii-output.json
 */

import { test } from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

const MAZII_URL = process.env.MAZII_BASE_URL ?? 'https://beta.mazii.net/vi-VN/translate';
const MAZII_EMAIL = process.env.MAZII_EMAIL ?? 'testuser@example.com';
const MAZII_PASSWORD = process.env.MAZII_PASSWORD ?? 'password123';

interface ElementInfo extends Record<string, unknown> {
  tag: string;
}

async function extractElements(page: import('@playwright/test').Page, label: string, output: Record<string, unknown>) {
  const elements: ElementInfo[] = await page.evaluate(() => {
    const results: ElementInfo[] = [];
    const selectors = [
      'button', 'input', 'textarea', 'select', 'a[href]',
      '[data-testid]', '[aria-label]', '[role]',
      '[class*="model"]', '[class*="dropdown"]', '[class*="selector"]',
    ];

    const seen = new Set<string>();

    for (const sel of selectors) {
      document.querySelectorAll<HTMLElement>(sel).forEach((el) => {
        // Avoid duplicates
        const key = `${el.tagName}:${el.className}:${el.getAttribute('data-testid')}`;
        if (seen.has(key)) return;
        seen.add(key);

        const entry: ElementInfo = {
          tag: el.tagName.toLowerCase(),
          visible: el.offsetHeight > 0 && el.offsetWidth > 0,
        };

        if (el.id) entry.id = el.id;
        if ((el as HTMLInputElement).name) entry.name = (el as HTMLInputElement).name;
        if ((el as HTMLInputElement).type) entry.type = (el as HTMLInputElement).type;
        if ((el as HTMLInputElement).placeholder) entry.placeholder = (el as HTMLInputElement).placeholder;
        if (el.getAttribute('aria-label')) entry.ariaLabel = el.getAttribute('aria-label');
        if (el.getAttribute('data-testid')) entry.testId = el.getAttribute('data-testid');
        if (el.getAttribute('aria-label*="model"')) entry.ariaLabelModel = true;
        if (el.getAttribute('href')) entry.href = el.getAttribute('href');

        const text = el.innerText?.trim().slice(0, 80);
        if (text) entry.text = text;

        const classes = el.className?.toString().trim().slice(0, 150);
        if (classes) entry.classes = classes;

        if (Object.keys(entry).length > 1) results.push(entry);
      });
    }

    return results.sort((a, b) => (b.visible ? 1 : -1) - (a.visible ? 1 : -1));
  });

  output[label] = elements;
  console.log(`✅ [${label}] — Collected ${elements.length} elements`);
}

function saveOutput(output: Record<string, unknown>) {
  const outputDir = path.resolve('recon');
  if (!fs.existsSync(outputDir)) fs.mkdirSync(outputDir, { recursive: true });

  const screenshotDir = path.resolve('recon/screenshots');
  if (!fs.existsSync(screenshotDir)) fs.mkdirSync(screenshotDir, { recursive: true });

  const outputPath = path.resolve('recon/mazii-output.json');
  fs.writeFileSync(outputPath, JSON.stringify(output, null, 2), 'utf-8');
  console.log(`\n📄 Recon output saved to: ${outputPath}`);
}

test('RECON — Mazii Translation Page (Elements Mapping)', async ({ page }) => {
  const output: Record<string, unknown> = {};

  try {
    // ── Step 1: Navigate to translate page ────────────────────────────────
    console.log('📍 Navigating to Mazii translate page...');
    await page.goto(MAZII_URL, { waitUntil: 'load' });
    await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

    await extractElements(page, 'translate_page_initial', output);
    await page.screenshot({
      path: 'recon/screenshots/01_mazii_translate_initial.png',
      fullPage: true
    });

    // ── Step 2: Input some text and translate ──────────────────────────────
    console.log('📍 Inputting text...');
    const textarea = page.locator('textarea[placeholder*="Nhập"], textarea:first-of-type').first();

    if (await textarea.isVisible({ timeout: 5000 }).catch(() => false)) {
      await textarea.fill('ありがとう');
      await page.waitForTimeout(500);

      await extractElements(page, 'translate_page_with_input', output);
      await page.screenshot({
        path: 'recon/screenshots/02_mazii_with_input.png',
        fullPage: true
      });

      // Click translate button
      const translateBtn = page.locator('button:has-text("Dịch")').first();
      if (await translateBtn.isVisible({ timeout: 3000 }).catch(() => false)) {
        await translateBtn.click();
        await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

        await extractElements(page, 'translate_page_after_translate', output);
        await page.screenshot({
          path: 'recon/screenshots/03_mazii_after_translate.png',
          fullPage: true
        });

        // ── Step 3: Try to find and locate model dropdown ────────────────────
        console.log('📍 Locating model dropdown button...');
        const modelButtons = await page.locator('button').all();
        const modelInfo: Record<string, unknown>[] = [];

        for (const btn of modelButtons) {
          const text = await btn.innerText().catch(() => '');
          const ariaLabel = await btn.getAttribute('aria-label');
          const testId = await btn.getAttribute('data-testid');
          const classes = await btn.getAttribute('class');

          if (text.toLowerCase().includes('mazii') ||
              text.toLowerCase().includes('translator') ||
              text.toLowerCase().includes('model') ||
              ariaLabel?.toLowerCase().includes('model')) {
            modelInfo.push({
              text: text.slice(0, 100),
              ariaLabel,
              testId,
              classes: classes?.slice(0, 150),
              isVisible: await btn.isVisible({ timeout: 1000 }).catch(() => false),
            });
          }
        }

        output['model_dropdown_candidates'] = modelInfo;
        console.log(`📍 Found ${modelInfo.length} potential model dropdown buttons`);

        // Try clicking first model button found
        if (modelInfo.length > 0) {
          const firstModelBtn = page.locator('button:has-text("Mazii")').first();
          if (await firstModelBtn.isVisible({ timeout: 3000 }).catch(() => false)) {
            console.log('📍 Found model button, attempting click...');
            await firstModelBtn.click().catch(() => {
              console.log('⚠️  Model button click failed');
            });
            await page.waitForTimeout(1000);

            await extractElements(page, 'translate_page_after_model_click', output);
            await page.screenshot({
              path: 'recon/screenshots/04_mazii_model_drawer.png',
              fullPage: true
            });
          }
        }
      }
    } else {
      console.log('⚠️  Textarea not found');
    }

    // ── Save all output ───────────────────────────────────────────────────
    saveOutput(output);
    console.log('\n✅ RECON COMPLETE');
    console.log('📄 Check recon/mazii-output.json for element mapping');
    console.log('📸 Screenshots saved in recon/screenshots/');

  } catch (error) {
    console.error('❌ Recon failed:', error);
    saveOutput(output);
    throw error;
  }
});
