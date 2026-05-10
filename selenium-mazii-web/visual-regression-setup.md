# Visual Regression Testing - Setup Guide

## 📋 Overview

**Tool**: BackstopJS  
**Framework**: Selenium + BackstopJS  
**Purpose**: Compare UI screenshots (Figma Design vs Live App)

---

## 🛠 Installation

### 1. Install BackstopJS globally
```bash
npm install -g backstopjs
```

### 2. Install project dependencies
```bash
cd /Users/tienthuy/Documents/Phuonggtest/DemoAI/selenium-mazii-web

npm init -y
npm install --save-dev backstopjs
npm install --save-dev puppeteer  # For screenshot capture
```

### 3. Initialize BackstopJS
```bash
backstop init
```

This creates:
- `backstop_data/bitmaps_reference/` (reference images từ design)
- `backstop_data/bitmaps_test/` (current screenshots từ app)
- `backstopjs.json` (configuration)

---

## 📸 Scenario Configuration

### Visual Test Scenarios (từ Figma Design)

| Scenario | Node ID | Screenshot | Description |
|---|---|---|---|
| **Empty State** | 14263:471103 | Mascot, buttons, dropdowns | TC_001 |
| **Recording JP** | 14345:84206 | Mic recording state | TC_006 |
| **Pending Bubble** | 14345:84504 | "..." loading state | TC_008 |
| **Bubble Complete** | 14345:89442 | Chat message displayed | TC_011 |
| **Auto-scroll** | 14345:91329 | Multiple bubbles | TC_028 |
| **Detail Navigation** | 14345:93522 | Detail icon visible | TC_020 |
| **Delete Confirm** | 14353:100355 | Modal xác nhận | TC_025 |
| **Offline Toast** | 14353:201354 | Error notification | TC_029 |

---

## 🎯 Workflow

### Step 1: Reference Images (từ Figma)
```bash
# Manually download từ Figma screenshots
# → backstop_data/bitmaps_reference/

curl -o reference_empty_state.png "https://www.figma.com/..."
curl -o reference_recording.png "https://www.figma.com/..."
```

### Step 2: Selenium Capture Script
```bash
# Tạo Java test để capture từ app
mvn test -Dtest=VisualRegressionTest
```

### Step 3: Compare
```bash
backstop test
```

### Step 4: Review Report
```bash
backstop openReport
```

---

## 🔧 BackstopJS Configuration (backstopjs.json)

```json
{
  "id": "mazii_dich_hoi_thoai",
  "viewports": [
    {
      "label": "Mobile 375px",
      "width": 375,
      "height": 812
    }
  ],
  "scenarios": [
    {
      "label": "Empty State (TC_001)",
      "url": "https://beta.mazii.net/vi-VN/conversation-translation",
      "referenceUrl": "",
      "readyEvent": "",
      "readySelector": "div.intro-container",
      "delay": 1000,
      "hideSelectors": [],
      "removeSelectors": [],
      "hoverSelector": "",
      "clickSelector": "",
      "postInteractionWait": 0,
      "selectors": [
        "div.intro-container",
        "button.rounded-circle",
        "button.btn-action"
      ],
      "selectorExpansion": true,
      "expect": 0,
      "misMatchThreshold": 0.1,
      "requireSameDimensions": true
    },
    {
      "label": "Recording State (TC_006)",
      "url": "https://beta.mazii.net/vi-VN/conversation-translation",
      "readySelector": "button.btn-danger.active",
      "clickSelector": "button.btn-danger",
      "postInteractionWait": 500,
      "selectors": [
        "app-conversation-translation",
        "button.rounded-circle.active"
      ],
      "misMatchThreshold": 0.1
    },
    {
      "label": "Delete Confirm Modal (TC_025)",
      "url": "https://beta.mazii.net/vi-VN/conversation-translation",
      "readySelector": ".modal.show",
      "selectors": [
        ".modal.show",
        ".modal.fade",
        "button.btn-submit",
        "button.btn-cancel"
      ],
      "misMatchThreshold": 0.1
    }
  ],
  "paths": {
    "bitmaps_reference": "backstop_data/bitmaps_reference",
    "bitmaps_test": "backstop_data/bitmaps_test",
    "engine_scripts": "backstop_data/engine_scripts",
    "html_report": "backstop_data/html_report",
    "ci_report": "backstop_data/ci_report"
  },
  "report": ["browser", "CI"],
  "engine": "puppeteer",
  "engineOptions": {
    "args": ["--no-sandbox"]
  },
  "asyncCaptureLimit": 5,
  "asyncCompareLimit": 50,
  "strict": false,
  "debug": false,
  "debugWindow": false
}
```

---

## 🚀 Running Tests

### Capture Reference (chạy 1 lần)
```bash
backstop reference
# → Tạo reference images từ URLs
```

### Compare (sau mỗi lần thay đổi app)
```bash
backstop test
# → Compare test vs reference
# → Generate HTML report với diff highlighting
```

### Approve Changes (nếu deliberate)
```bash
backstop approve
# → Update reference images với test screenshots
```

### View Report
```bash
backstop openReport
```

---

## 📊 Integration with Selenium Tests

### Option 1: Selenium Hooks
Chèn vào `BaseTest.tearDown()`:

```java
@AfterMethod
public void captureScreenshot() {
    String filename = "screenshot_" + System.currentTimeMillis() + ".png";
    File screenshot = ((TakesScreenshot) driver)
        .getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screenshot, 
        new File("backstop_data/bitmaps_test/" + filename));
}
```

### Option 2: Separate Visual Test Suite
```bash
mvn test -Dtest=VisualRegressionTest
backstop test
```

---

## 📋 Checklist

- [ ] Install Node.js + npm
- [ ] Run `npm init -y` trong selenium-mazii-web/
- [ ] Run `npm install --save-dev backstopjs puppeteer`
- [ ] Tạo `backstopjs.json` config
- [ ] Download Figma screenshots → reference folder
- [ ] Run `backstop init`
- [ ] Run `backstop reference` (tạo baseline)
- [ ] Update Selenium tests để capture screenshots
- [ ] Run `backstop test` (compare)
- [ ] Review `backstop_data/html_report/index.html`

---

## 🎨 Figma Design URLs (Node IDs)

Download reference images từ các Figma node này:

```
Empty State:        14263:471103
Recording State:    14345:84206
Pending Bubble:     14345:84504
Bubble Complete:    14345:89442
Auto-scroll:        14345:91329
Detail Navigation:  14345:93522
Delete Modal:       14353:100355
Offline Toast:      14353:201354
```

---

## ❓ Troubleshooting

**Issue**: Puppeteer timeout  
**Fix**: Increase `viewportWait` in backstopjs.json

**Issue**: Screenshots too large  
**Fix**: Adjust `width`/`height` in viewports

**Issue**: False positives (anti-alias differences)  
**Fix**: Increase `misMatchThreshold` (0.1 = 10% tolerance)

---

## 📚 Resources

- [BackstopJS Docs](https://garris.github.io/BackstopJS/)
- [Puppeteer API](https://pptr.dev/)
- [Visual Regression Testing Best Practices](https://css-tricks.com/visual-regression-testing/)

