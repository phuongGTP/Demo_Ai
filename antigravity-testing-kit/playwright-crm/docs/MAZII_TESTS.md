# Mazii Translation (Dịch) — Automated Test Suite

## 📋 Overview

Complete Playwright test suite cho Mazii Translation feature (beta.mazii.net).

- **Total Test Cases**: 46 TCs
- **Framework**: Playwright + TypeScript
- **Viewport**: 1440px (Laptop)
- **Test Modules**: M1-M7 (Input, Language, Translation, Model, Features, Empty State, History)

## 📁 Project Structure

```
src/
├── fixtures/
│   ├── mazii-auth.fixture.ts      # Authentication logic for Mazii
│   └── mazii.fixture.ts            # Mazii test fixtures
├── pages/
│   └── mazii/
│       └── mazii-translate.page.ts # Page Object Model for Translate page
└── tests/
    └── mazii/
        └── translation-dich.spec.ts # Main test suite (46 TCs)

test-data/
└── mazii-translation-data.ts       # Test data (languages, translations, selectors)

docs/
└── MAZII_TESTS.md                  # This file
```

## 🔧 Setup

### 1. Update `.env` file

```bash
# .env
MAZII_BASE_URL=https://beta.mazii.net
MAZII_EMAIL=phuonggt@eupgroup.net
MAZII_PASSWORD=<YOUR_PASSWORD>
```

⚠️ **Important**: Fill in `MAZII_PASSWORD` before running tests.

### 2. Install Dependencies

```bash
cd antigravity-testing-kit/playwright-crm
npm install
```

## ▶️ Running Tests

### Run all Mazii tests (headed mode)

```bash
npm run test:mazii
```

### Run in debug mode

```bash
npm run test:mazii:debug
```

### Run specific test suite

```bash
# M1 - Translation Input tests
npx playwright test src/tests/mazii/translation-dich.spec.ts -g "M1"

# M3 - Translation Execution tests
npx playwright test src/tests/mazii/translation-dich.spec.ts -g "M3"

# Specific test
npx playwright test src/tests/mazii/translation-dich.spec.ts -g "TC_019"
```

### Run without UI (headless)

```bash
npx playwright test src/tests/mazii/ --headed=false
```

### Generate HTML report

```bash
npm run report
```

## 📊 Test Coverage

| Module | TCs | Risk Level | Status |
|--------|-----|-----------|--------|
| M1 - Translation Input | 9 | HIGH | ✅ |
| M2 - Language Selection | 5 | MEDIUM | ✅ |
| M3 - Translation Execution | 11 | CRITICAL/HIGH | ✅ |
| M4 - Model Selection + Paywall | 4 | CRITICAL/HIGH | ✅ |
| M5 - Advanced Features | 5 | MEDIUM | ✅ |
| M6 - Empty State | 2 | LOW | ✅ |
| M7 - Translation History | 6 | MEDIUM/LOW | ✅ |
| **TOTAL** | **46** | — | ✅ |

## 🎯 Test Scenarios

### M1: Translation Input (9 TCs)
- Input focus & cursor
- Clear button appearance & functionality
- Auto-resize textarea
- Character counter realtime update
- Translate button disabled when empty
- Voice input modal
- Handwriting/Radical modals
- Enable/disable logic for Japanese-only features

### M2: Language Selection (5 TCs)
- Source language dropdown
- Target language dropdown
- Language swap (Swap button)
- Auto-translate on language change

### M3: Translation Execution (11 TCs)
- Successful translation with Result Card
- Skeleton loading
- Manual translation (no auto-translate during typing)
- API timeout (30s) error handling
- Network loss error handling
- Copy to clipboard
- Text-to-speech (pronunciation)
- Furigana display (Japanese only, after translation)
- Deep lookup for Kanji

### M4: Model Selection + Paywall (4 TCs)
- Model selector drawer
- Model switching
- Paywall for Free/Standard accounts
- Premium account bypass

### M5: Advanced Features (5 TCs)
- Grammar checker
- Grammar related (Japanese only)
- Contrast view
- Vocabulary analysis
- Special features per language

### M6: Empty State (2 TCs)
- Empty state on initial load
- Empty state disappears after translation

### M7: Translation History (6 TCs)
- History inline display (web specific)
- Load item from history
- Delete individual history item
- Clear all history
- History ordering (newest first)
- No history on first load

## 🔍 Important Notes

### 1. Selectors & Locators

Selectors are set up with multiple fallbacks to handle different UI variations:

```typescript
get translateButton(): Locator {
  return this.page.locator('button:has-text("Dịch"), button[type="button"]:has-text("Dịch")').first();
}
```

If selectors don't match actual UI, update in:
- `test-data/mazii-translation-data.ts` (UI_ELEMENTS)
- `src/pages/mazii/mazii-translate.page.ts` (getter methods)

### 2. Network & API Tests

Tests for timeout (TC_022) & network loss (TC_023) simulate offline state:

```typescript
await page.context().setOffline(true);  // Simulate network loss
await page.context().setOffline(false); // Restore network
```

### 3. Furigana & Language-Specific Features

- Furigana only displays for **Japanese source** + **after translation**
- Handwriting/Radical buttons only enabled for **Japanese source**
- Grammar features only for **Japanese source**

### 4. Authentication

Login flow is automated in `mazii-auth.fixture.ts`. If login flow changes:

```typescript
// Update login selectors in mazii-auth.fixture.ts
const emailInput = page.locator('input[type="email"]').first();
const passwordInput = page.locator('input[type="password"]').first();
const submitButton = page.locator('button[type="submit"]').first();
```

### 5. Paywall Tests

- TC_032-033: Assumes account is **Free/Standard** → should see paywall
- TC_034: Assumes account is **Premium** → should NOT see paywall

To test all account types, create multiple test accounts or use separate test runs.

## 🐛 Debugging

### Enable verbose logging

```bash
DEBUG=pw:api npx playwright test src/tests/mazii/ --headed
```

### Run in debug mode with inspector

```bash
npx playwright test src/tests/mazii/ --debug
```

### View test traces

Traces are captured on first retry. Check:
- `test-results/` folder
- HTML report via `npm run report`

## 📝 Test Data

All test data is in `test-data/mazii-translation-data.ts`:

- Vietnamese, English, Japanese translations
- Multiline text
- Furigana examples
- Complex sentences
- UI element selectors
- Model names
- Language codes

To add more test data:

```typescript
export const MAZII_TEST_DATA = {
  translations: {
    newTest: {
      ja: '新しいテキスト',
      vi: 'Văn bản mới',
    },
  },
};
```

## ⚙️ Customization

### Update Playwright Config

Edit `playwright.config.ts`:

```typescript
use: {
  baseURL: 'https://beta.mazii.net', // Change URL if needed
  viewport: { width: 1440, height: 900 }, // Adjust viewport
  headless: false, // Run headless (no browser UI)
},
```

### Adjust Timeouts

In test files or page object:

```typescript
const TIMEOUTS = {
  default: 10000,
  api: 30000,
  skeleton: 3000,
};
```

### Filter Tests

```bash
# Run only CRITICAL tests
npx playwright test src/tests/mazii/ -g "HIGH|CRITICAL"

# Run except network tests
npx playwright test src/tests/mazii/ -g "(?!network|offline)"
```

## 🚀 CI/CD Integration

Add to your CI pipeline:

```yaml
- name: Run Mazii Tests
  run: npm run test:mazii
  env:
    MAZII_PASSWORD: ${{ secrets.MAZII_PASSWORD }}

- name: Upload Report
  if: always()
  uses: actions/upload-artifact@v2
  with:
    name: playwright-report
    path: playwright-report/
```

## 📞 Support

For issues with:
- **Selectors not matching**: Update in page object + test data
- **Login failing**: Check credentials in `.env`
- **Timeouts**: Increase timeout values for slow network
- **Paywall logic**: May need test account adjustments

## 📖 Reference

- [Playwright Documentation](https://playwright.dev)
- [Test Case Document](../practices/testcases/mz-dich/testcases_dich_web_v1.md)
- Page Object Model: `src/pages/mazii/mazii-translate.page.ts`

---

**Last Updated**: 2026-05-24
**Total TCs**: 46
**Framework**: Playwright + TypeScript
