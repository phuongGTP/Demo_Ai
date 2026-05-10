# 🎨 Visual Regression Testing Guide — Mazii Dịch Hội Thoại

**Mục đích**: So sánh UI từ app thực tế với Figma design tự động bằng pixel comparison.

**File Design Figma**: https://www.figma.com/design/Nk5L22h5xSC09Or0GeNXDw

---

## 🚀 Quick Start (5 phút)

### 1️⃣ Install Tools
```bash
cd ~/Documents/Phuonggtest/DemoAI/selenium-mazii-web

# Install Node.js dependencies
npm init -y
npm install --save-dev backstopjs puppeteer
```

### 2️⃣ Setup BackstopJS
```bash
# File cấu hình đã có sẵn: backstopjs.json
# Tạo thư mục cho reference images
mkdir -p backstop_data/bitmaps_reference
mkdir -p backstop_data/bitmaps_test
```

### 3️⃣ Capture Reference Images (từ Figma)
**Option A: Tự động từ Figma URLs** (nếu app = live Figma)
```bash
backstop reference
# → Puppeteer sẽ truy cập URLs và capture screenshots
```

**Option B: Manual Download (Recommended)**
- Mở mỗi Figma node scenario
- Screenshot → lưu vào `backstop_data/bitmaps_reference/`
- Tên file theo scenario (e.g., `01_empty_state.png`)

### 4️⃣ Capture Test Screenshots (từ App)
```bash
# Run Selenium visual tests
mvn test -Dtest=VisualRegressionTest

# Hoặc từng test cụ thể:
mvn test -Dtest=VisualRegressionTest#testVisualEmptyState
```

Screenshots sẽ lưu vào: `backstop_data/bitmaps_test/`

### 5️⃣ Compare & Generate Report
```bash
backstop test
```

### 6️⃣ View Report
```bash
backstop openReport

# Hoặc mở file:
open backstop_data/html_report/index.html
```

---

## 📊 Visual Test Scenarios

| # | Test Case | Screenshot | Component | Expected |
|---|---|---|---|---|
| 01 | Empty State (TC_001) | Mascot, buttons, dropdowns | `app-conversation-translation` | ✅ UI matches design |
| 02 | Mic Recording (TC_006) | Mic JP active state | `button.rounded-circle.active` | ✅ Recording indicator visible |
| 03 | Keyboard Mode (TC_031) | Text input layout | `.input-container` | ✅ Keyboard visible |
| 04 | Delete Modal (TC_025) | Modal dialog | `.modal.show` | ✅ Buttons positioned correctly |
| 05 | Bubble Actions (TC_016) | Play, copy buttons | `button.btn-audio, .btn.p-0` | ✅ Action icons visible |
| 06 | Language Dropdowns | JP/VI language buttons | `button.txt-lang` | ✅ Both dropdowns visible |
| 07 | Offline Toast (TC_029) | Error notification | `[role='alert']` | ✅ Toast style correct |

---

## 🔍 Understanding Visual Regression Report

### Report Features:

**✅ PASS** (Green)
```
Screenshot matches reference within 0.1% (misMatchThreshold)
```

**❌ FAIL** (Red)
```
Screenshot differs from reference by >0.1%
→ Hover to see highlighted differences
→ Review "Diff" image to locate changes
```

### Common Differences:

| Issue | Cause | Action |
|---|---|---|
| Text rendering | Font anti-aliasing | Increase `misMatchThreshold` |
| Colors different | CSS changes | Check CSS override |
| Layout shifted | Spacing change | Verify design change is intentional |
| Elements missing | Selector outdated | Update `selectors` in backstopjs.json |
| Animation frame | Timing issue | Increase `postInteractionWait` |

---

## 🔧 Configuration Details

### backstopjs.json (đã tạo sẵn)

```json
{
  "viewports": [
    { "label": "Mobile 375px", "width": 375, "height": 812 },
    { "label": "Desktop 1440px", "width": 1440, "height": 900 }
  ],
  "scenarios": [
    {
      "label": "TC_001 | Empty State",
      "url": "https://beta.mazii.net/vi-VN/conversation-translation",
      "readySelector": "div.intro-container",
      "selectors": ["app-conversation-translation", "div.intro-container"],
      "misMatchThreshold": 0.1  // 10% tolerance
    }
  ]
}
```

### Key Parameters:

- **`readySelector`**: Chờ element này xuất hiện trước capture
- **`clickSelector`**: Click element này trước capture (e.g., mic button)
- **`hoverSelector`**: Hover element này trước capture (e.g., bubble)
- **`postInteractionWait`**: Chờ (ms) sau interaction trước capture
- **`misMatchThreshold`**: % tolerance cho pixel differences
  - `0.1` = 0.1% tolerance (strict)
  - `1.0` = 1% tolerance (loose)

---

## 📸 Figma to Screenshot Mapping

### How to Download Figma References:

1. **Open Figma Design**:
   https://www.figma.com/design/Nk5L22h5xSC09Or0GeNXDw

2. **Navigate to Scenario**:
   - "Scenario: Trạng thái trống" (Empty State)
   - "Scenario: Trạng thái Đang thu âm" (Recording)
   - etc.

3. **Export Instance**:
   - Right-click "Dịch hội thoại | Mobile 375px"
   - Export → PNG
   - Save to: `backstop_data/bitmaps_reference/01_empty_state.png`

4. **Naming Convention**:
   ```
   01_empty_state.png           ← TC_001
   02_mic_recording_jp.png      ← TC_006
   03_keyboard_mode.png         ← TC_031
   04_delete_modal.png          ← TC_025
   05_bubble_actions.png        ← TC_016
   06_language_dropdowns.png    ← TC_022
   07_offline_toast.png         ← TC_029
   ```

---

## 🐛 Troubleshooting

### Q: Screenshots appear blank or truncated
**A**: Increase `delay` or `postInteractionWait` in scenario config
```json
"delay": 2000,                    // Wait 2s after page load
"postInteractionWait": 1000       // Wait 1s after click/hover
```

### Q: "Element not found" errors
**A**: Update `readySelector` to match current DOM
```bash
# Open dev tools, find the correct selector:
document.querySelector("div.intro-container")  // Should exist

# Update backstopjs.json:
"readySelector": "div.intro-container"
```

### Q: Too many false positives (anti-alias, rendering)
**A**: Increase `misMatchThreshold`
```json
"misMatchThreshold": 0.5    // 0.5% tolerance instead of 0.1%
```

### Q: Tests pass locally but fail in CI
**A**: Ensure same resolution in CI
```bash
# Set screen resolution explicitly:
export BROWSER_WIDTH=1440
export BROWSER_HEIGHT=900
mvn test -Dtest=VisualRegressionTest
```

---

## 📋 Workflow Summary

```
Step 1: Capture reference images from Figma design
        ↓
Step 2: Download/export Figma screenshots
        ↓
Step 3: Place in backstop_data/bitmaps_reference/
        ↓
Step 4: Run Selenium visual tests
        mvn test -Dtest=VisualRegressionTest
        ↓
Step 5: Capture test screenshots (saved to bitmaps_test/)
        ↓
Step 6: Compare with BackstopJS
        backstop test
        ↓
Step 7: Review HTML report
        open backstop_data/html_report/index.html
        ↓
Step 8: If differences are intentional:
        backstop approve
        (Updates reference images)
```

---

## 🎯 Best Practices

### ✅ DO

- Capture on **consistent viewport** (375px mobile)
- Wait for **async content** (spinners, api calls)
- **Isolate changes** — test one scenario at a time
- **Review report** carefully before approving
- **Version control** reference images in git

### ❌ DON'T

- Compare **desktop vs mobile** (different viewports)
- Use **too strict** misMatchThreshold (anti-alias issues)
- **Approve diffs** without checking design
- Compare **with animations running**
- Take screenshots of **overlays/modals in wrong state**

---

## 🔗 Integration with CI/CD

### GitHub Actions Example

```yaml
name: Visual Regression Tests

on: [pull_request]

jobs:
  visual-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-node@v2
        with:
          node-version: '16'
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      
      - run: npm ci
      - run: mvn test -Dtest=VisualRegressionTest
      - run: npx backstop test
      
      - name: Upload Report
        if: always()
        uses: actions/upload-artifact@v2
        with:
          name: visual-regression-report
          path: backstop_data/html_report
```

---

## 📚 Resources

- **BackstopJS Docs**: https://garris.github.io/BackstopJS/
- **Puppeteer API**: https://pptr.dev/
- **Figma Design File**: https://www.figma.com/design/Nk5L22h5xSC09Or0GeNXDw
- **Visual Testing Guide**: https://css-tricks.com/visual-regression-testing/

---

## ✅ Next Steps

- [ ] Install Node.js + npm
- [ ] Run `npm install --save-dev backstopjs puppeteer`
- [ ] Download Figma screenshots → `backstop_data/bitmaps_reference/`
- [ ] Run `mvn test -Dtest=VisualRegressionTest`
- [ ] Run `backstop test`
- [ ] Review `html_report/index.html`
- [ ] Approve changes if correct: `backstop approve`
- [ ] Commit reference images to git

**Questions?** Check `visual-regression-setup.md` for detailed configuration.

