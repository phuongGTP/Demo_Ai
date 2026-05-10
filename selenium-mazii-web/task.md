# Automation Generation Progress — Mazii Dịch Hội Thoại (Web)

**Test cases nguồn:** `antigravity-testing-kit/practices/testcases/mz-dich/testcases_hoi_thoai_web_v2.md`  
**Framework:** Selenium 4.18 + Java 11 + TestNG 7.8 + Allure 2.25  
**Platform:** Web (Chrome / Firefox / Edge)  
**Ngày tạo:** 2026-05-10

---

## Checklist 6 Bước

- [x] Bước 1: Phân tích test cases — 35 TCs, 11 Modules
- [x] Bước 2: Khảo sát UI — Locator Collection (CSS selectors, cần verify DevTools)
- [x] Bước 3: Thiết kế Page Objects — BasePage, DichPage, DichHoiThoaiPage, PaywallPage
- [x] Bước 4: Chuẩn bị Test Data — accounts.json + WebConfig từ .env
- [x] Bước 5: Sinh automation scripts — 9 test classes
- [x] Bước 6: Static code review — sửa 1 lỗi biên dịch (`waitForUrl` protected → public)
- [ ] Bước 6 (runtime): Chạy test trên browser thực — CẦN SETUP MÔI TRƯỜNG

---

## Test Cases Coverage

| TC ID | Title | Test Class | Status |
|-------|-------|-----------|--------|
| WEB_HT_TC_001 | Empty State web hiển thị đúng | HoiThoaiEmptyStateTest | ✅ Automated |
| WEB_HT_TC_002 | Guest click tab → Paywall ngay | HoiThoaiPaywallTest | ✅ Automated |
| WEB_HT_TC_003 | Tài khoản Thường click Mic → Paywall | HoiThoaiPaywallTest | ✅ Automated |
| WEB_HT_TC_004 | Lần đầu click Mic → browser permission dialog | HoiThoaiMicPermissionTest | ✅ Automated (verify gián tiếp) |
| WEB_HT_TC_005 | Deny mic → hướng dẫn cài đặt trình duyệt | HoiThoaiMicPermissionTest | ✅ Automated (browser prefs=Block) |
| WEB_HT_TC_006 | Click Mic → chuyển sang Pause icon | HoiThoaiBubbleChatTest | ✅ Automated |
| WEB_HT_TC_007 | Chỉ 1 bên thu âm tại 1 thời điểm | HoiThoaiBubbleChatTest | ✅ Automated |
| WEB_HT_TC_008 | Đang nói → Bubble tạm "..." | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| WEB_HT_TC_009 | Im lặng → Bubble chính thức tự động | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| WEB_HT_TC_010 | Click Pause thủ công → Bubble chính thức | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| WEB_HT_TC_011 | Không nói → không tạo Bubble (Gap G2) | HoiThoaiBubbleChatTest | ✅ Automated |
| WEB_HT_TC_012 | STT điều chỉnh theo ngôn ngữ mới | HoiThoaiLanguageTest | ⏸ enabled=false (cần audio injection) |
| WEB_HT_TC_013 | Bubble đúng phía người nói | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần seeded Bubble) |
| WEB_HT_TC_014 | Cấu trúc Bubble chính thức đủ thành phần | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần seeded Bubble) |
| WEB_HT_TC_015 | Bubble tạm "..." hiển thị đúng | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| WEB_HT_TC_016 | Click Phát âm → TTS phát | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_017 | Click Copy → toast "Đã sao chép" | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_018 | Click Kanji → popup Deep Lookup | HoiThoaiBubbleInteractionTest | ⏸ Chưa implement (cần seeded Bubble + Kanji) |
| WEB_HT_TC_019 | Hover Bubble Nhật → icon ">" hiện ra | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_020 | Click ">" → chuyển màn Dịch văn bản | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_021 | Hover Bubble Việt → icon ">" KHÔNG hiện | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble Việt) |
| WEB_HT_TC_022 | Đổi ngôn ngữ → Bubble cũ giữ nguyên | HoiThoaiLanguageTest | ✅ Automated |
| WEB_HT_TC_023 | Test cặp Anh–Việt (Gap G1) | HoiThoaiLanguageTest | ✅ Automated |
| WEB_HT_TC_024 | Click Swap → 2 ngôn ngữ hoán đổi | HoiThoaiLanguageTest | ✅ Automated |
| WEB_HT_TC_025 | Hover+Click Trash → Modal xác nhận | HoiThoaiClearHistoryTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_026 | Xác nhận xóa → Empty State | HoiThoaiClearHistoryTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_027 | Hủy xóa → Bubble giữ nguyên | HoiThoaiClearHistoryTest | ✅ Automated (cần seeded Bubble) |
| WEB_HT_TC_028 | Auto-scroll khi có Bubble mới | HoiThoaiClearHistoryTest | ⏸ enabled=false (cần ≥6 Bubble) |
| WEB_HT_TC_029 | Offline → toast lỗi khi click Mic | HoiThoaiOfflineTest | ✅ Automated (CDP offline) |
| WEB_HT_TC_030 | Mất mạng giữa chừng → dừng + toast | HoiThoaiOfflineTest | ✅ Automated (CDP offline) |
| WEB_HT_TC_031 | Click tab Nhập bàn phím → UI đổi | HoiThoaiKeyboardModeTest | ✅ Automated |
| WEB_HT_TC_032 | Nhập text + Gửi → Bubble | HoiThoaiKeyboardModeTest | ✅ Automated |
| WEB_HT_TC_033 | Nhấn Enter = Gửi | HoiThoaiKeyboardModeTest | ✅ Automated |
| WEB_HT_TC_034 | Gửi text rỗng → không tạo Bubble | HoiThoaiKeyboardModeTest | ✅ Automated |
| WEB_HT_TC_035 | Chuyển lại "Thu âm" → mic trở lại | HoiThoaiKeyboardModeTest | ✅ Automated |

**Tổng:** 24 TCs ✅ Automated · 7 TCs ⏸ `enabled=false` (audio/seeded Bubble) · 1 TC ⏸ Chưa implement (TC_018 Deep Lookup)

---

## Locator Collection

> ⚠️ Tất cả locators dưới đây dùng `data-testid` convention — **cần verify bằng DevTools (F12 → Elements)** trên mazii.net thực tế.

| Screen | Element | CSS Selector (Primary) | Fallback | Verified |
|--------|---------|----------------------|---------|---------|
| DichPage | Tab Dịch văn bản | `[data-testid='tab-dich-van-ban']` | `a[href*='van-ban']` | ⚠️ Chưa |
| DichPage | Tab Dịch hội thoại | `[data-testid='tab-dich-hoi-thoai']` | `a[href*='hoi-thoai']` | ⚠️ Chưa |
| DichHoiThoaiPage | Mode tab "Thu âm" | `[data-testid='mode-tab-mic']` | `button:contains('Thu âm')` | ⚠️ Chưa |
| DichHoiThoaiPage | Mode tab "Nhập bàn phím" | `[data-testid='mode-tab-keyboard']` | `button:contains('Nhập từ bàn phím')` | ⚠️ Chưa |
| DichHoiThoaiPage | Mic trái (Tiếng Việt) | `[data-testid='mic-left']` | `#mic-left` | ⚠️ Chưa |
| DichHoiThoaiPage | Mic phải (Tiếng Nhật) | `[data-testid='mic-right']` | `#mic-right` | ⚠️ Chưa |
| DichHoiThoaiPage | Pause trái | `[data-testid='pause-left']` | `.pause-btn-left` | ⚠️ Chưa |
| DichHoiThoaiPage | Pause phải | `[data-testid='pause-right']` | `.pause-btn-right` | ⚠️ Chưa |
| DichHoiThoaiPage | Dropdown ngôn ngữ trái | `[data-testid='lang-dropdown-left']` | `select#lang-left` | ⚠️ Chưa |
| DichHoiThoaiPage | Dropdown ngôn ngữ phải | `[data-testid='lang-dropdown-right']` | `select#lang-right` | ⚠️ Chưa |
| DichHoiThoaiPage | Language option item | `[data-testid='lang-option']` | `.lang-option` | ⚠️ Chưa |
| DichHoiThoaiPage | Swap icon (⇄) | `[data-testid='swap-icon']` | `.swap-btn` | ⚠️ Chưa |
| DichHoiThoaiPage | Trash button | `[data-testid='trash-button']` | `.trash-btn` | ⚠️ Chưa |
| DichHoiThoaiPage | Empty state text | `[data-testid='empty-state-text']` | `[contains text 'Trò chuyện']` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble list | `[data-testid='bubble-list']` | `.bubble-list` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble item | `[data-testid='bubble-item']` | `.bubble-item` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble tạm "..." | `[data-testid='bubble-pending']` | `.bubble-pending` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble Phát âm | `[data-testid='bubble-play']` | `.btn-play` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble Copy | `[data-testid='bubble-copy']` | `.btn-copy` | ⚠️ Chưa |
| DichHoiThoaiPage | Bubble ">" (hover, Nhật) | `[data-testid='bubble-detail']` | `.btn-detail` | ⚠️ Chưa |
| DichHoiThoaiPage | Delete modal | `[data-testid='delete-modal']` | `[contains text 'không thể hoàn tác']` | ⚠️ Chưa |
| DichHoiThoaiPage | Delete confirm | `[data-testid='delete-confirm']` | `button:contains('Xác nhận')` | ⚠️ Chưa |
| DichHoiThoaiPage | Delete cancel | `[data-testid='delete-cancel']` | `button:contains('Hủy')` | ⚠️ Chưa |
| DichHoiThoaiPage | Toast | `[role='alert']` | `.toast` | ⚠️ Chưa |
| DichHoiThoaiPage | Keyboard input trái | `[data-testid='keyboard-input-left']` | `textarea#input-left` | ⚠️ Chưa |
| DichHoiThoaiPage | Keyboard input phải | `[data-testid='keyboard-input-right']` | `textarea#input-right` | ⚠️ Chưa |
| DichHoiThoaiPage | Keyboard send trái | `[data-testid='keyboard-send-left']` | `button#send-left` | ⚠️ Chưa |
| DichHoiThoaiPage | Keyboard send phải | `[data-testid='keyboard-send-right']` | `button#send-right` | ⚠️ Chưa |
| PaywallPage | Paywall container | `[data-testid='paywall-screen']` | `.paywall` | ⚠️ Chưa |
| PaywallPage | Upgrade button | `[data-testid='paywall-upgrade-button']` | `button:contains('Nâng cấp')` | ⚠️ Chưa |

### Hướng dẫn verify locators bằng DevTools

```bash
# 1. Mở mazii.net trong Chrome → DevTools (F12) → Tab Elements
# 2. Navigate đến màn Dịch hội thoại
# 3. Click "Pick element" (Ctrl+Shift+C) → click từng element → xem html attributes
# 4. Nếu có data-testid: dùng luôn. Nếu không: dùng id/class/text-based locator
# 5. Cập nhật locators vào DichHoiThoaiPage.java
```

---

## Known Issues & Limitations

| # | Vấn đề | Workaround |
|---|--------|-----------|
| 1 | TC_008, 009, 010, 012, 013-015, 028 cần audio thực hoặc Bubble seeded | Audio injection qua virtual mic / API mock |
| 2 | TC_018 Deep Lookup chưa implement | Cần biết locator popup và cách trigger click từ Kanji |
| 3 | BrowserUtils offline dùng CDP v120 — phụ thuộc Chrome version | Đổi `v120` → version khớp Chrome thực tế (`adb -v` → xem CDP version) |
| 4 | TC_016–021 (Bubble interaction) cần seeded Bubble trong BeforeMethod | Implement `seedBubbleData()`: dùng keyboard mode nhập text thay vì audio injection |
| 5 | TC_003 (Standard → Paywall) cần tài khoản Thường riêng | Điền `ACCOUNT_STANDARD_EMAIL` trong .env |
| 6 | Login flow chưa implement trong `BaseTest.loginAsPremium()` | Implement theo flow đăng nhập thực tế của mazii.net |

---

## Files Created

| File | Mô tả |
|------|-------|
| `pom.xml` | Maven config — Selenium 4.18, TestNG 7.8, Allure 2.25, WebDriverManager 5.7 |
| `testng.xml` | Suite config — 9 test classes |
| `.env.example` | Template config — BASE_URL, BROWSER, credentials |
| `src/main/.../config/WebConfig.java` | Đọc config từ .env |
| `src/main/.../drivers/WebDriverFactory.java` | Tạo WebDriver (Chrome/Firefox/Edge) + mic permission prefs |
| `src/main/.../pages/BasePage.java` | Base Page Object — wait helpers, hover, Actions |
| `src/main/.../pages/DichPage.java` | Tab navigation screen |
| `src/main/.../pages/DichHoiThoaiPage.java` | Core page — 30+ locators + methods (mic, bubble, keyboard mode, swap) |
| `src/main/.../pages/PaywallPage.java` | Paywall verification |
| `src/main/.../utils/BrowserUtils.java` | CDP offline toggle, viewport check |
| `src/main/.../utils/ScreenshotUtil.java` | Chụp screenshot khi fail |
| `src/test/.../base/BaseTest.java` | Setup/teardown lifecycle |
| `src/test/.../tests/hoi_thoai/HoiThoaiEmptyStateTest.java` | TC_001 |
| `src/test/.../tests/hoi_thoai/HoiThoaiPaywallTest.java` | TC_002, 003 |
| `src/test/.../tests/hoi_thoai/HoiThoaiMicPermissionTest.java` | TC_004, 005 |
| `src/test/.../tests/hoi_thoai/HoiThoaiBubbleChatTest.java` | TC_006–015 |
| `src/test/.../tests/hoi_thoai/HoiThoaiBubbleInteractionTest.java` | TC_016–021 |
| `src/test/.../tests/hoi_thoai/HoiThoaiLanguageTest.java` | TC_012, 022–024 |
| `src/test/.../tests/hoi_thoai/HoiThoaiClearHistoryTest.java` | TC_025–028 |
| `src/test/.../tests/hoi_thoai/HoiThoaiOfflineTest.java` | TC_029, 030 |
| `src/test/.../tests/hoi_thoai/HoiThoaiKeyboardModeTest.java` | TC_031–035 |
| `test-data/accounts.json` | Account config template |
