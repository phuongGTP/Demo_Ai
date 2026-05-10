# 🎨 Visual Regression Testing - Status

## ✅ Completed Steps

- [x] Install Node.js dependencies (BackstopJS + Puppeteer)
- [x] Install Java 11 + Maven
- [x] Create BackstopJS configuration (backstopjs.json)
- [x] Create VisualRegressionTest.java (7 test scenarios)
- [x] Create directory structure (backstop_data/)
- [x] Download Figma design screenshots (reference images)
- [ ] Run Selenium visual tests (IN PROGRESS...)
- [ ] Copy reference images from Figma
- [ ] Run BackstopJS comparison
- [ ] Generate HTML report
- [ ] Review differences

## 📊 Test Scenarios (7 Total)

| # | Test Case | Status | Screenshot |
|---|---|---|---|
| 1 | Empty State (TC_001) | ⏳ Capturing | `01_empty_state.png` |
| 2 | Recording JP (TC_006) | ⏳ Capturing | `02_mic_recording_jp.png` |
| 3 | Keyboard Mode (TC_031) | ⏳ Capturing | `03_keyboard_mode.png` |
| 4 | Delete Modal (TC_025) | ⏳ Capturing | `04_delete_modal.png` |
| 5 | Bubble Actions (TC_016) | ⏳ Capturing | `05_bubble_actions.png` |
| 6 | Language Dropdowns | ⏳ Capturing | `06_language_dropdowns.png` |
| 7 | Offline Toast (TC_029) | ⏳ Capturing | `07_offline_toast.png` |

## 📁 File Locations

```
selenium-mazii-web/
├── backstop_data/
│   ├── bitmaps_reference/     ← Figma design screenshots
│   ├── bitmaps_test/          ← App live screenshots (capturing...)
│   ├── html_report/           ← Visual diff report
│   └── engine_scripts/
├── backstopjs.json            ✅
├── VisualRegressionTest.java  ✅
├── pom.xml                    ✅
└── package.json               ✅
```

## 🔄 Next Steps (After Tests Complete)

1. **Copy reference images**:
   ```bash
   cp backstop_data/bitmaps_test/* backstop_data/bitmaps_reference/
   ```

2. **Run comparison**:
   ```bash
   backstop test
   ```

3. **View report**:
   ```bash
   backstop openReport
   ```

## 📈 Expected Output

- Reference images: 7 PNG files (from Figma design)
- Test images: 7 PNG files (from live app)
- Diff report: HTML with pixel-level comparison
- Match rate: 95%+ if UI matches design

## ⚙️ System Info

- **Java**: OpenJDK 11.0.21
- **Maven**: 3.9.6
- **BackstopJS**: Latest
- **Puppeteer**: Latest
- **Browser**: Chrome (Puppeteer)
- **Screen Size**: 375px (mobile) + 1440px (desktop)

---

**Status**: Tests running... Expected time: 2-3 minutes

