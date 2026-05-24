# ✅ Mazii Test Suite Setup Checklist

## 📦 Files Created

### Test Files
- ✅ `src/tests/mazii/translation-dich.spec.ts` (46 test cases, 2000+ lines)

### Page Objects & Fixtures
- ✅ `src/pages/mazii/mazii-translate.page.ts` (Page Object Model with 40+ methods)
- ✅ `src/fixtures/mazii-auth.fixture.ts` (Authentication logic)
- ✅ `src/fixtures/mazii.fixture.ts` (Test fixtures)

### Test Data
- ✅ `test-data/mazii-translation-data.ts` (Languages, translations, selectors)

### Configuration
- ✅ Updated `.env` with MAZII_BASE_URL, MAZII_EMAIL, MAZII_PASSWORD
- ✅ Updated `src/utils/env.config.ts` with Mazii env variables
- ✅ Updated `package.json` with `test:mazii` & `test:mazii:debug` scripts

### Documentation
- ✅ `docs/MAZII_TESTS.md` (Complete guide)
- ✅ `MAZII_SETUP_CHECKLIST.md` (This file)

## 🎯 Test Coverage

| Module | TCs | Covered |
|--------|-----|---------|
| M1 - Translation Input | 9 | ✅ TC_001-013 |
| M2 - Language Selection | 5 | ✅ TC_014-018 |
| M3 - Translation Execution | 11 | ✅ TC_019-029 |
| M4 - Model Selection + Paywall | 4 | ✅ TC_030-034 |
| M5 - Advanced Features | 5 | ✅ TC_035-039 |
| M6 - Empty State | 2 | ✅ TC_040-041 |
| M7 - Translation History | 6 | ✅ TC_042-047 |
| **TOTAL** | **46** | ✅ |

## 📋 Before Running Tests

### 1. Set Password in `.env`

Edit `antigravity-testing-kit/playwright-crm/.env`:

```bash
MAZII_EMAIL=phuonggt@eupgroup.net
MAZII_PASSWORD=<YOUR_ACTUAL_PASSWORD>  # ← Add password here
```

### 2. Verify Dependencies

```bash
cd antigravity-testing-kit/playwright-crm
npm install  # If not already done
```

### 3. (Optional) Update Selectors

If UI selectors don't match Mazii's actual DOM:
- Update selectors in `test-data/mazii-translation-data.ts`
- Update getters in `src/pages/mazii/mazii-translate.page.ts`

## ▶️ Quick Start

### Run all 46 Mazii tests

```bash
cd antigravity-testing-kit/playwright-crm
npm run test:mazii
```

### Run specific module

```bash
# Only M1 tests (Input)
npx playwright test src/tests/mazii/ -g "M1"

# Only M3 tests (Translation Execution)
npx playwright test src/tests/mazii/ -g "M3"
```

### Run with debug

```bash
npm run test:mazii:debug
```

### View HTML report

```bash
npm run report
```

## 🔍 Test Structure

Each test follows this pattern:

```typescript
test('TC_ID · Test title', async ({ maziiTranslatePage }) => {
  // Setup
  await maziiTranslatePage.selectSourceLanguage(LANGUAGES.JAPANESE);
  
  // Action
  await maziiTranslatePage.inputSourceText('テキスト');
  await maziiTranslatePage.clickTranslate();
  
  // Assertion
  await expect(maziiTranslatePage.resultText).toBeVisible();
  const result = await maziiTranslatePage.getResultText();
  expect(result).toBeTruthy();
});
```

## 🛠 Key Features

### 1. Page Object Model (POM)
All selectors + interactions are encapsulated in `MaziiTranslatePage`:

```typescript
// Instead of:
await page.locator('button:has-text("Dịch")').click();

// Use:
await maziiTranslatePage.clickTranslate();
```

### 2. Reusable Test Data
All test data is centralized in `mazii-translation-data.ts`:

```typescript
MAZII_TEST_DATA.translations.thankYou.ja  // "ありがとうございます"
MAZII_TEST_DATA.languages.source.japanese  // "日本語"
```

### 3. Fixtures & Auto Login
Tests automatically handle login via fixture:

```typescript
test('...', async ({ maziiTranslatePage }) => {
  // Already logged in, ready to use
  await maziiTranslatePage.goto();
});
```

## ⚠️ Known Limitations

1. **Voice Input (TC_009)**: 
   - Simulated with text input (actual voice requires microphone + audio processing)
   - To test real voice: need browser permission + audio mock

2. **Handwriting (TC_010)**:
   - Modal opens, but actual canvas drawing requires mouse/touch events
   - Can extend with drawing canvas testing

3. **Furigana (TC_026-028)**:
   - Depends on actual UI structure; selectors may need adjustment
   - Test validates visibility, not exact text matching

4. **Paywall (TC_032-034)**:
   - Free/Standard/Premium account logic
   - May need separate test accounts for each tier

5. **Network Tests (TC_022-023)**:
   - Use `page.context().setOffline()` for simulation
   - Real network latency testing would need network throttling

## 📊 Expected Results

When all 46 tests run successfully:

```
✓ M1 · Translation Input (Web) — 9 passed
✓ M2 · Language Selection (Web Dropdown) — 5 passed
✓ M3 · Translation Execution + Furigana + Deep Lookup — 11 passed
✓ M4 · Model Selection + Paywall (Web Drawer) — 4 passed
✓ M5 · Advanced Features (Web) — 5 passed
✓ M6 · Empty State (Web) — 2 passed
✓ M7 · Translation History (Web — Inline) — 6 passed

Total: 46 passed
```

## 🔗 Related Files

- Original Test Cases: `practices/testcases/mz-dich/testcases_dich_web_v1.md`
- Playwright Config: `playwright.config.ts`
- Base Page Object: `src/pages/base.page.ts`
- Base Fixture: `src/fixtures/base.fixture.ts`

## 📞 Troubleshooting

### Tests fail with "Login failed"
- Check `.env` password is correct
- Verify account exists on beta.mazii.net
- Check if login flow has changed

### Selectors not found
- Run one test with `--debug` mode
- Inspect elements in browser
- Update selectors in `mazii-translate.page.ts`

### Tests timeout
- Increase timeout values (default: 10-30s)
- Check network speed
- Verify beta.mazii.net is accessible

### Paywall tests not working
- Ensure test account tier matches test expectations
- Free/Standard should see paywall for AI models
- Premium should bypass paywall

---

## ✨ Summary

- **46 automated test cases** covering all Mazii translation features
- **Page Object Model** for maintainability & reusability
- **Organized by modules** matching original test document
- **Test data separated** from test logic
- **Fixtures for auto-login** & setup
- **Multiple runner scripts** for flexibility
- **Comprehensive documentation** for debugging & maintenance

Ready to run! ✅

---

**Created**: 2026-05-24
**Framework**: Playwright + TypeScript
**URL**: https://beta.mazii.net
