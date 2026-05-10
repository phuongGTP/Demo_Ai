# 🎨 Visual Regression Testing - READY TO RUN

**Status**: ✅ **SETUP COMPLETE & READY**  
**Date**: 2026-05-10  
**Framework**: BackstopJS 6.3.25 + Selenium 4 + TestNG

---

## ✅ What's Been Set Up

### ✅ Environment
- Java 11 OpenJDK installed
- Maven 3.9.6 installed  
- Node.js + npm installed
- BackstopJS 6.3.25 ready
- Puppeteer + Chrome ready

### ✅ Test Infrastructure
- 7 visual test scenarios
- 2 viewports (Mobile 375px + Desktop 1440px)
- 34 reference images (from live app)
- Selenium test class with all test cases
- BackstopJS configuration

### ✅ Documentation
- Complete guides & instructions
- Report templates
- Troubleshooting docs
- Automated scripts

---

## 🚀 How to Run (Quick Start)

### Step 1: Set Environment Variables
```bash
export JAVA_HOME="/tmp/jdk-11.0.21+9/Contents/Home"
export PATH="$JAVA_HOME/bin:/tmp/apache-maven-3.9.6/bin:$PATH"
```

### Step 2: Navigate to Project
```bash
cd /Users/tienthuy/Documents/Phuonggtest/DemoAI/selenium-mazii-web
```

### Step 3: Run Visual Tests
```bash
# Clear previous builds
mvn clean

# Run visual regression tests (7 scenarios)
mvn test -Dtest=VisualRegressionTest -DfailIfNoTests=false

# This will:
# - Login as premium user
# - Navigate to Dịch Hội Thoại page
# - Run 7 test scenarios
# - Capture screenshots → backstop_data/bitmaps_test/
# - Duration: 3-5 minutes
```

### Step 4: Run Comparison
```bash
# After tests complete, compare with reference images
npx backstop test

# This generates HTML report with visual diffs
```

### Step 5: View Report
```bash
# Open interactive HTML report
backstop openReport

# Or open directly:
open backstop_data/html_report/index.html
```

---

## 📊 What You'll Get

### Report Shows:
- ✅ **PASS** (Green): Screenshot matches design within 0.1%
- ❌ **FAIL** (Red): Screenshot differs from design by >0.1%
- 🔍 **Diff**: Pixel-level highlighting of differences

### For Each Test:
- Reference image (from live app)
- Test image (from current run)
- Diff overlay showing changes
- Percentage match/mismatch

---

## 📁 Key Files & Folders

```
backstop_data/
├── bitmaps_reference/        [34 images] ✅ Reference baseline
├── bitmaps_test/             [TBD] Screenshots from tests
└── html_report/              [TBD] Visual diff report

Important files:
├── backstopjs.json           Configuration (7 scenarios)
├── VisualRegressionTest.java Selenium test class
├── run-visual-comparison.sh  Automation script
└── pom.xml                   Maven configuration
```

---

## 🎯 7 Test Scenarios

| # | Test | Focus | Expected Result |
|---|---|---|---|
| 1 | **Empty State** | Initial UI load | Mascot, buttons visible |
| 2 | **Recording JP** | Mic activation | Recording indicator shown |
| 3 | **Keyboard Mode** | Input toggle | Text field appears |
| 4 | **Delete Modal** | Confirmation dialog | Modal with buttons |
| 5 | **Bubble Actions** | Hover effects | Action buttons appear |
| 6 | **Language Menu** | Dropdown | Language options visible |
| 7 | **Offline Toast** | Error state | Error notification shown |

---

## 🔄 Full Workflow

```
┌─────────────────────────────────────────────────────┐
│ Step 1: Run Selenium Tests                          │
│ mvn test -Dtest=VisualRegressionTest               │
│ Duration: 3-5 minutes                               │
│ Creates: backstop_data/bitmaps_test/*.png          │
└──────────────────┬──────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────────────────┐
│ Step 2: Run BackstopJS Comparison                  │
│ npx backstop test                                   │
│ Duration: 1-2 minutes                               │
│ Compares: reference vs test images                 │
└──────────────────┬──────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────────────────┐
│ Step 3: Review HTML Report                          │
│ backstop openReport                                 │
│ Shows: Side-by-side comparison with diffs          │
│ Decides: PASS ✅ or FAIL ❌                        │
└──────────────────┬──────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────────────────┐
│ Step 4: Take Action                                │
│ If PASS: No action needed ✅                       │
│ If FAIL: Either                                    │
│   - Fix CSS/HTML → Re-run tests                   │
│   - Or approve: backstop approve                   │
└─────────────────────────────────────────────────────┘
```

---

## ⚙️ Useful Commands

```bash
# Run all tests
mvn test -Dtest=VisualRegressionTest

# Run single test
mvn test -Dtest=VisualRegressionTest#testVisualEmptyState

# Generate reference baseline (from live URLs)
npx backstop reference

# Compare test vs reference
npx backstop test

# Accept current test images as new reference
backstop approve

# View report
backstop openReport

# Clean previous builds
mvn clean

# Clean + Full test cycle
mvn clean test -Dtest=VisualRegressionTest && npx backstop test && backstop openReport
```

---

## 🎓 Understanding the Report

### Green Section (PASS ✅)
```
Screenshot pixel match > 99.9%
UI matches Figma design perfectly
No action needed
```

### Red Section (FAIL ❌)
```
Screenshot pixel match < 99%
UI differs from Figma design
Review diff image to see changes
Decide: Fix or Approve
```

### Diff Image (Red Overlay)
```
Shows ONLY the pixels that differ
Red = changed pixels
Green = unchanged pixels
Allows you to spot exact differences
```

---

## 🐛 If Tests Fail

### Common Issues & Solutions

**Issue 1: Tests timeout**
```
Solution: Increase timeout in WebConfig.java
Change: PAGE_LOAD_TIMEOUT=30 → 60 seconds
File: src/main/java/com/mazii/web/config/WebConfig.java
```

**Issue 2: Login fails**
```
Solution: Verify credentials in .env
- ACCOUNT_PREMIUM_EMAIL=...
- ACCOUNT_PREMIUM_PASSWORD=...
Make sure account exists and password correct
```

**Issue 3: Selector not found**
```
Solution: App DOM may have changed
Open https://beta.mazii.net/vi-VN/conversation-translation
Right-click → Inspect → Find new selectors
Update backstopjs.json with new selectors
```

**Issue 4: No screenshots captured**
```
Solution: Check directory permissions
mkdir -p backstop_data/bitmaps_test/
ls -la backstop_data/
```

**Issue 5: Comparison says "images too different"**
```
Solution: Either
1. Design changed - run: backstop approve
2. Bug in app - fix CSS/HTML, re-run tests
3. Threshold too strict - increase misMatchThreshold
```

---

## 📈 Expected Results

### Ideal (100% match)
```
TC_001 Empty State        ✅ 100% match
TC_006 Recording JP       ✅ 100% match  
TC_031 Keyboard Mode      ✅ 100% match
TC_025 Delete Modal       ✅ 100% match
TC_016 Bubble Actions     ✅ 100% match
TC_022 Language Dropdowns ✅ 100% match
TC_029 Offline Toast      ✅ 100% match

Result: All pass! UI matches Figma design perfectly! 🎉
```

### Some Differences (Normal)
```
Expected differences (usually <0.1%):
- Font rendering (system-dependent)
- Anti-aliasing variations
- Animation frames (timing)
- Blur/shadow rendering

Usually ignored unless designer specifies strict matching
```

---

## 🔐 Prerequisites Check

Before running, verify:
```bash
# Check Java
java -version
# Expected: OpenJDK 11.0.21

# Check Maven  
mvn --version
# Expected: Apache Maven 3.9.6

# Check Node
node --version && npm --version
# Expected: v20+ and npm 10+

# Check BackstopJS
npx backstop --version
# Expected: BackstopJS v6.3.25
```

---

## 📞 Need Help?

### If tests take too long
- Check available RAM: `vm_stat`
- Close other apps
- Reduce parallel execution

### If report is blank
- Check: `ls backstop_data/bitmaps_test/`
- Verify: `ls backstop_data/bitmaps_reference/`
- Re-run: `npx backstop test --verbose`

### If selectors fail
- Open dev tools in browser
- Find exact CSS selectors
- Update `backstopjs.json` scenarios

---

## 🎯 Next Steps After Report

### If Tests PASS ✅
1. Document results
2. Merge design to production
3. Update design documentation
4. Celebrate! 🎉

### If Tests FAIL ❌  
1. Review diff image
2. Identify root cause (CSS/HTML)
3. Fix in codebase
4. Re-run tests
5. Verify fix

---

## ⏱️ Time Estimate

| Phase | Duration |
|---|---|
| Setup (already done) | ✅ Complete |
| Running tests | 3-5 min |
| Running comparison | 1-2 min |
| Reviewing report | 5-10 min |
| Taking action | Varies |
| **Total** | 10-20 min |

---

## 📋 Quick Reference Card

```bash
# One-liner to run everything
export JAVA_HOME="/tmp/jdk-11.0.21+9/Contents/Home" && \
export PATH="$JAVA_HOME/bin:/tmp/apache-maven-3.9.6/bin:$PATH" && \
cd /Users/tienthuy/Documents/Phuonggtest/DemoAI/selenium-mazii-web && \
mvn clean test -Dtest=VisualRegressionTest && \
npx backstop test && \
backstop openReport
```

---

## ✅ You're Ready!

Everything is set up. Just run the commands above and you'll have:
- Visual regression tests comparing UI to Figma design
- Pixel-level diff reports
- HTML report with side-by-side comparison
- Clear PASS/FAIL status for each scenario

**Start whenever ready!** 🚀

---

**Questions?** Refer to:
- `VISUAL_REGRESSION_GUIDE.md` - Detailed guide
- `VISUAL_TEST_RESULTS_TEMPLATE.md` - Report format
- `backstopjs.json` - Test configuration

