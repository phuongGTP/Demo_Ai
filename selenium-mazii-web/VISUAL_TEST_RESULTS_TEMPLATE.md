# 🎨 Visual Regression Test Results

**Date**: 2026-05-10  
**App**: Mazii Dịch Hội Thoại (Conversation Translation)  
**Browser**: Chrome 127  
**Viewport**: 375px (Mobile) × 1440px (Desktop)  

---

## 📊 Summary

| Metric | Value |
|---|---|
| **Total Scenarios** | 7 |
| **Passed** | ? |
| **Failed** | ? |
| **Match Rate** | ? % |
| **Total Pixels Compared** | ? |
| **Average Diff** | ? % |

---

## 📋 Detailed Results

### ✅ PASS Scenarios (Match Design)

| # | Test Case | Diff % | Status |
|---|---|---|---|
| | | | |

### ❌ FAIL Scenarios (Differ from Design)

| # | Test Case | Diff % | Issue | Action |
|---|---|---|---|---|
| | | | | |

---

## 🔍 Visual Differences Found

### Category: Layout Shifts
- [ ] Button positioning
- [ ] Spacing/padding
- [ ] Element alignment
- [ ] Component sizing

### Category: Colors
- [ ] Button colors
- [ ] Text colors
- [ ] Background colors
- [ ] Border colors

### Category: Typography
- [ ] Font size
- [ ] Font weight
- [ ] Line height
- [ ] Letter spacing

### Category: Missing Elements
- [ ] Icons
- [ ] Buttons
- [ ] Text labels
- [ ] Decorative elements

### Category: Rendering Issues
- [ ] Anti-aliasing
- [ ] Blur/sharpness
- [ ] Animation frames
- [ ] Overflow handling

---

## 📈 Individual Test Case Analysis

### Test 1: Empty State (TC_001)

**Expected**: Mascot, buttons, dropdowns visible  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] UI matches design
- [ ] Minor spacing differences
- [ ] Color inconsistency
- [ ] Missing elements

**Action Items**:
- [ ] Check CSS selector accuracy
- [ ] Verify Figma screenshot clarity
- [ ] Update component if needed

---

### Test 2: Recording State (TC_006)

**Expected**: Mic recording indicator visible  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Recording state correctly indicated
- [ ] Color matches design
- [ ] Animation visible
- [ ] State transition smooth

---

### Test 3: Keyboard Mode (TC_031)

**Expected**: Text input and send button visible  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Input field styled correctly
- [ ] Send button positioned right
- [ ] Keyboard layout correct
- [ ] No overlapping elements

---

### Test 4: Delete Modal (TC_025)

**Expected**: Confirmation dialog with buttons  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Modal backdrop correct
- [ ] Dialog box styled properly
- [ ] Buttons positioned correctly
- [ ] Text content readable

---

### Test 5: Bubble Actions (TC_016)

**Expected**: Play, copy, detail icons visible  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Action icons visible
- [ ] Hover state works
- [ ] Icon colors correct
- [ ] Layout responsive

---

### Test 6: Language Dropdowns (TC_022)

**Expected**: JP and VI language buttons visible  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Both buttons visible
- [ ] Dropdown style correct
- [ ] Options display properly
- [ ] Selection works

---

### Test 7: Offline Toast (TC_029)

**Expected**: Error notification displayed  
**Actual**: [Screenshot comparison]  
**Match%**: [X%]  
**Status**: ✅ / ❌  

**Findings**:
- [ ] Toast positioning correct
- [ ] Message visible
- [ ] Animation smooth
- [ ] Color scheme right

---

## 🔧 Next Steps

### If All Tests PASS ✅
- [ ] Merge design to main branch
- [ ] Update UI documentation
- [ ] Celebrate! 🎉

### If Some Tests FAIL ❌
1. **Review differences**:
   - [ ] Check actual vs expected screenshot
   - [ ] Identify root cause (CSS, selector, etc)

2. **Determine if intentional**:
   - [ ] Design change approved?
   - [ ] Bug in implementation?
   - [ ] Test needs update?

3. **Take action**:
   - [ ] Update CSS/HTML in app
   - [ ] Or: Update Figma reference if design changed
   - [ ] Re-run tests to verify fix

---

## 📸 Screenshot References

**Reference (Figma Design)**:  
Location: `backstop_data/bitmaps_reference/`

**Test (Live App)**:  
Location: `backstop_data/bitmaps_test/`

**Diffs**:  
Location: `backstop_data/html_report/`

---

## 📝 Notes

- Keep this report up-to-date after each test run
- Use BackstopJS HTML report for pixel-level diff visualization
- Screenshot URLs are relative to project root
- Threshold: 0.1% pixel difference tolerance

---

## ✍️ Sign-Off

| Role | Name | Date | Status |
|---|---|---|---|
| QA | TBD | TBD | ⏳ |
| Dev | TBD | TBD | ⏳ |
| PM | TBD | TBD | ⏳ |

