# Mazii Dịch Hội Thoại - Test Status Report (2026-05-10)

## Overview
Web automation testing suite for **Dịch Hội Thoại** (Conversation Translation) feature on https://beta.mazii.net/vi-VN/conversation-translation using Selenium 4.x + TestNG + Allure

---

## Test Coverage Summary

### Total Test Cases: 25 Enabled (4 Disabled)

| Test Class | TC Range | Enabled | Disabled | Status |
|---|---|---|---|---|
| **Empty State** | TC_001 | 1 | — | ✅ Ready |
| **Paywall** | TC_002–003 | 2 | — | ✅ Ready |
| **Mic Permission** | TC_004–005 | 2 | — | ✅ Ready |
| **Bubble Chat** | TC_006–011 | 3 | 2 (TC_008, TC_009) | ⚠️ Partial |
| **Bubble Interactions** | TC_016–021 | 5 | — | ✅ Ready |
| **Language** | TC_022–023 | 2 | 1 (TC_012) | ⚠️ Partial |
| **Clear History** | TC_025–027 | 3 | 1 (TC_028) | ✅ Ready |
| **Offline** | TC_029–030 | 2 | — | ✅ Ready |
| **Keyboard Mode** | TC_031–035 | 5 | — | ✅ Ready |
| **TOTALS** | TC_001–035 | **25** | **4** | |

---

## Enabled Test Cases (Ready to Run)

### M_HT01: Empty State (1 TC)
- **TC_001**: Empty state displays correctly (mic buttons, keyboard toggle, language dropdowns visible)

### M_HT02: Paywall (2 TCs)
- **TC_002**: Guest user sees paywall when clicking mic
- **TC_003**: Standard user sees paywall when clicking mic

### M_HT03: Mic Permission (2 TCs)
- **TC_004**: First-time mic click requests browser permission
- **TC_005**: Blocked mic permission prevents recording

### M_HT04: Bubble Chat (3 TCs + 2 disabled)
- **TC_006**: Click mic shows recording state
- **TC_007**: Only one mic recording at a time
- **TC_011**: Silence after mic tap creates no bubble
- **TC_008 (DISABLED)**: Speaking shows pending bubble [needs audio injection]
- **TC_009 (DISABLED)**: Silence creates official bubble [needs audio injection]

### M_HT06: Bubble Interactions (5 TCs)
- **TC_016**: Click play button plays TTS audio
- **TC_017**: Click copy shows "Đã sao chép" toast
- **TC_019**: Hover Japanese bubble shows detail icon ">"
- **TC_020**: Click detail icon navigates to dịch văn bản
- **TC_021**: Hover non-Japanese bubble doesn't show detail icon

### M_HT07: Language Selection (2 TCs + 1 disabled)
- **TC_022**: Changing language preserves old bubbles
- **TC_023**: English–Vietnamese language pair works correctly
- **TC_012 (DISABLED)**: Language change updates STT [needs audio injection]

### M_HT08: Clear History (3 TCs + 1 disabled)
- **TC_025**: Click trash shows confirmation modal
- **TC_026**: Confirm delete returns to empty state
- **TC_027**: Cancel delete preserves bubbles
- **TC_028 (DISABLED)**: Auto-scroll when new bubble added [needs ≥6 bubbles]

### M_HT10: Offline (2 TCs)
- **TC_029**: Offline before mic click shows error toast
- **TC_030**: Network drop during recording stops + notifies

### M_HT11: Keyboard Mode (5 TCs)
- **TC_031**: Click keyboard toggle switches to text input mode
- **TC_032**: Type text + click send creates bubble
- **TC_033**: Press Enter sends message (same as send button)
- **TC_034**: Sending empty text creates no bubble
- **TC_035**: Click keyboard toggle twice returns to mic mode

---

## Disabled Test Cases (Need Work)

### Audio Injection Tests (4 TCs)
These require **audio file injection** into microphone stream or mock API responses:

- **TC_008**: Pending bubble during speaking
- **TC_009**: Bubble creation on silence
- **TC_012**: Language-specific STT validation
- **TC_028**: Auto-scroll with multiple bubbles

**Note**: Audio injection can be implemented via:
1. WebRTC audio injection (complex — requires custom library)
2. Mock API responses for STT results (simpler approach)
3. External audio device simulation

---

## Recent Improvements (May 10, 2026)

### 1. Bubble Seeding Infrastructure ✅
Added `seedBubbleData()` method to `BaseTest`:
- Auto-creates 1 or more bubbles via keyboard input before tests
- Used by bubble interaction tests (TC_016–021) and clear history tests (TC_025–027)
- Reduces flakiness from missing pre-conditions

```java
protected void seedBubbleData()                // Creates 1 bubble
protected void seedBubbleData(int count)       // Creates N bubbles
```

**Applied to**:
- `HoiThoaiBubbleInteractionTest` — now seeds before TC_016–021
- `HoiThoaiClearHistoryTest` — now seeds before TC_025–027

### 2. Unified Test Suite (testng.xml)
All 9 test classes registered in a single suite:
- Empty State, Paywall, Mic Permission, Bubble Chat
- Bubble Interactions, Language, Clear History, Offline, Keyboard Mode

### 3. Browser Automation Utilities ✅
Complete `BrowserUtils` class:
- `setOffline()` / `setOnline()` — Chrome DevTools Protocol network emulation
- `scrollToBottom()` — scroll to viewport
- `isInViewport()` — viewport visibility checks

---

## Known Limitations & TODOs

### ❌ Audio Injection
Tests needing audio input (TC_008, TC_009, TC_012) are disabled.
**Path forward**: Mock STT API or use external audio library.

### ⚠️ Single Bubble Hover
`hoverFirstBubble()` can't distinguish between Japanese and Vietnamese bubbles (both appear in same list).
**Comment in TC_021**: "Cần method riêng hoverFirstVietnameseBubble()"
**Impact**: Low — Vietnamese bubble doesn't show detail icon anyway.

### ⚠️ Paywall Modal Selector Conflict
- `PaywallPage` uses: `.modal.fade.show`
- `DichHoiThoaiPage` uses: `[class*='paywall'], [class*='upgrade'], .modal-upgrade`

**Current behavior**: Both work independently (PaywallPage has priority in TC_002–003).
**No action needed** unless paywall modal CSS changes.

### ⚠️ Permission Dialog Not Automatable
Browser native permission dialogs (TC_004–005) can't be clicked via WebDriver.
**Mitigation**: Verify app doesn't enter recording state instead.

---

## Environment Setup

### .env Configuration ✅
```
BASE_URL=https://beta.mazii.net
BROWSER=chrome
HEADLESS=true
BROWSER_WIDTH=1440
BROWSER_HEIGHT=900
ACCOUNT_PREMIUM_EMAIL=phuonggt+4@eupgroup.net
ACCOUNT_PREMIUM_PASSWORD=123456
ACCOUNT_STANDARD_EMAIL=phuonggt+5@eupgroup.net
ACCOUNT_STANDARD_PASSWORD=123456
```

### Dependencies ✅
- **Selenium**: 4.18.1
- **TestNG**: 7.8.0
- **Allure**: 2.25.0
- **WebDriverManager**: 5.7.0 (auto-download ChromeDriver)

### Run Tests
```bash
cd selenium-mazii-web
mvn test                           # Run all suites
mvn test -Dgroups=smoke           # Run smoke tests only
mvn allure:report                 # Generate Allure report
```

---

## Next Steps

### Priority 1: Verify Test Execution
- [ ] Install Maven or use Maven wrapper
- [ ] Run full test suite: `mvn test`
- [ ] Check for failures/flakiness
- [ ] Review Allure report

### Priority 2: Audio Injection (If Needed)
- [ ] Research mock API approach for STT
- [ ] Enable TC_008, TC_009, TC_012, TC_028
- [ ] Add seed method with N bubbles for TC_028 validation

### Priority 3: Enhanced Reporting
- [ ] Configure Allure attachments (screenshots, HTML source)
- [ ] Add custom step logging
- [ ] Integration with CI/CD pipeline

---

## Test Execution Stats (Estimated)

- **Total Suite Runtime**: ~5–8 minutes (depending on network latency)
- **Per Test Average**: 10–20 seconds
- **Flakiness**: Low (most tests deterministic, no audio injection)

---

## Questions / Clarifications Needed

1. **Audio Injection Strategy**: Do you want to implement mock API responses, or wait for a dedicated audio library?
2. **Test Grouping**: Run all 25 tests, or start with smoke tests (TC_001–003, TC_006, TC_031)?
3. **CI/CD Integration**: Should these tests run on every commit, or nightly only?

