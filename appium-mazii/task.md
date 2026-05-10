# Automation Generation Progress — Mazii Dịch Hội Thoại (Mobile)

**Test cases nguồn:** `antigravity-testing-kit/practices/testcases/mz-dich/testcases_hoi_thoai_mobile_v2.md`  
**Framework:** Appium + Java 11 + TestNG + Allure  
**Platform:** Android + iOS  
**Ngày tạo:** 2026-05-10

---

## Checklist 6 Bước

- [x] Bước 1: Phân tích test cases — 28 TCs, 10 Modules
- [x] Bước 2: Khảo sát UI — Locator Collection dựa trên Figma design (cần verify với Appium Inspector)
- [x] Bước 3: Thiết kế Screen Objects — BaseScreen, DichScreen, DichHoiThoaiScreen, PaywallScreen
- [x] Bước 4: Chuẩn bị Test Data — accounts.json + AppConfig từ .env
- [x] Bước 5: Sinh automation scripts — 7 test classes
- [x] Bước 6: Static code review — sửa 3 lỗi biên dịch tiềm ẩn
- [ ] Bước 6 (runtime): Chạy test trên thiết bị thực / emulator — CẦN SETUP MÔI TRƯỜNG

---

## Test Cases Coverage

| TC ID | Title | Test Class | Status |
|-------|-------|-----------|--------|
| MAZ_HT_TC_001 | Empty State hiển thị đúng | HoiThoaiEmptyStateTest | ✅ Automated |
| MAZ_HT_TC_002 | Guest → Paywall khi mở tab | HoiThoaiPaywallTest | ✅ Automated |
| MAZ_HT_TC_003 | Thường tap Mic → Paywall | HoiThoaiPaywallTest | ✅ Automated |
| MAZ_HT_TC_004 | Lần đầu tap Mic → OS permission dialog | HoiThoaiMicPermissionTest | ✅ Automated |
| MAZ_HT_TC_005 | Deny permission → hướng dẫn cài đặt | HoiThoaiMicPermissionTest | ✅ Automated (cần ADB pre-condition) |
| MAZ_HT_TC_006 | Tap Mic → chuyển sang Pause icon | HoiThoaiBubbleChatTest | ✅ Automated |
| MAZ_HT_TC_007 | Chỉ 1 bên thu âm tại 1 thời điểm | HoiThoaiBubbleChatTest | ✅ Automated |
| MAZ_HT_TC_008 | Đang nói → Bubble tạm "..." | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| MAZ_HT_TC_009 | Im lặng → Bubble chính thức tự động | HoiThoaiBubbleChatTest | ⏸ enabled=false (cần audio injection) |
| MAZ_HT_TC_010 | Tap Pause thủ công → Bubble chính thức | HoiThoaiBubbleChatTest | ✅ Automated |
| MAZ_HT_TC_011 | Không nói → không tạo Bubble (Gap G2) | HoiThoaiBubbleChatTest | ✅ Automated |
| MAZ_HT_TC_012 | STT điều chỉnh theo ngôn ngữ mới | HoiThoaiLanguageTest | ⏸ enabled=false (cần audio injection) |
| MAZ_HT_TC_013 | Bubble đúng nửa màn hình người nói | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_014 | Cấu trúc Bubble chính thức đủ thành phần | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_015 | Bubble tạm "..." hiển thị đúng | HoiThoaiBubbleChatTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_016 | Tap Phát âm → TTS phát | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_017 | Tap Copy → toast "Đã sao chép" | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_018 | Deep Lookup từ Bubble Kanji | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble) |
| MAZ_HT_TC_019 | Tap ">" trên Bubble Nhật → màn Dịch | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble Nhật) |
| MAZ_HT_TC_020 | Bubble Việt KHÔNG có icon ">" | HoiThoaiBubbleInteractionTest | ✅ Automated (cần seeded Bubble Việt) |
| MAZ_HT_TC_021 | Đổi ngôn ngữ → Bubble cũ giữ nguyên | HoiThoaiLanguageTest | ✅ Automated |
| MAZ_HT_TC_022 | Test cặp Anh–Việt (Gap G1) | HoiThoaiLanguageTest | ✅ Automated |
| MAZ_HT_TC_023 | Tap Trash → Modal xác nhận | HoiThoaiClearHistoryTest | ✅ Automated |
| MAZ_HT_TC_024 | Xác nhận xóa → Empty State | HoiThoaiClearHistoryTest | ✅ Automated |
| MAZ_HT_TC_025 | Hủy xóa → Bubble giữ nguyên | HoiThoaiClearHistoryTest | ✅ Automated |
| MAZ_HT_TC_026 | Auto-scroll khi có Bubble mới | HoiThoaiClearHistoryTest | ⏸ enabled=false (cần ≥6 Bubble) |
| MAZ_HT_TC_027 | Offline → toast lỗi khi tap Mic | HoiThoaiOfflineTest | ✅ Automated |
| MAZ_HT_TC_028 | Mất mạng giữa chừng → dừng + toast | HoiThoaiOfflineTest | ✅ Automated |

**Tổng:** 22 TCs ✅ Automated · 4 TCs ⏸ `enabled=false` (cần audio injection)

---

## Locator Collection

> ⚠️ Tất cả locators dưới đây **cần verify** bằng Appium Inspector trên app Mazii thực tế.
> Đây là convention naming dựa trên Figma design — có thể khác với tên thật trong app.

| Screen | Element | Action | Primary Locator | Fallback | Verified |
|--------|---------|--------|----------------|---------|---------|
| DichScreen | Tab Dịch văn bản | Tap | `accessibilityId("tab_dich_van_ban")` | `text("Dịch văn bản")` | ⚠️ Chưa |
| DichScreen | Tab Dịch hội thoại | Tap | `accessibilityId("tab_dich_hoi_thoai")` | `text("Dịch hội thoại")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Empty state text | Assert | `textContains("Nhấn vào micro")` | `accessibilityId("hoi_thoai_empty_text")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Header title | Assert | `textContains("Dịch hội thoại")` | — | ⚠️ Chưa |
| DichHoiThoaiScreen | Trash icon | Tap | `accessibilityId("hoi_thoai_trash_button")` | `Android: id/btn_delete` | ⚠️ Chưa |
| DichHoiThoaiScreen | Mic button trái (Nhật) | Tap | `accessibilityId("hoi_thoai_mic_left")` | `Android: id/btn_mic_left` | ⚠️ Chưa |
| DichHoiThoaiScreen | Mic button phải (Việt) | Tap | `accessibilityId("hoi_thoai_mic_right")` | `Android: id/btn_mic_right` | ⚠️ Chưa |
| DichHoiThoaiScreen | Pause icon trái | Assert | `accessibilityId("hoi_thoai_pause_left")` | — | ⚠️ Chưa |
| DichHoiThoaiScreen | Pause icon phải | Assert | `accessibilityId("hoi_thoai_pause_right")` | — | ⚠️ Chưa |
| DichHoiThoaiScreen | Language dropdown trái | Tap | `accessibilityId("hoi_thoai_lang_left")` | `Android: id/dropdown_lang_left` | ⚠️ Chưa |
| DichHoiThoaiScreen | Language dropdown phải | Tap | `accessibilityId("hoi_thoai_lang_right")` | `Android: id/dropdown_lang_right` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble tạm "..." | Assert | `textContains("...")` | `accessibilityId("bubble_pending")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble list | Count | `accessibilityId("hoi_thoai_bubble_list")` | `Android: id/recycler_bubble` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble item | Count | `accessibilityId("hoi_thoai_bubble_item")` | `Android: id/bubble_item` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble Phát âm | Tap | `accessibilityId("bubble_play_button")` | `Android: id/btn_play` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble Copy | Tap | `accessibilityId("bubble_copy_button")` | `Android: id/btn_copy` | ⚠️ Chưa |
| DichHoiThoaiScreen | Bubble ">" Nhật | Tap | `accessibilityId("bubble_detail_button")` | `Android: id/btn_detail` | ⚠️ Chưa |
| DichHoiThoaiScreen | Delete modal | Assert | `accessibilityId("hoi_thoai_delete_modal")` | `textContains("không thể hoàn tác")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Delete Confirm button | Tap | `accessibilityId("hoi_thoai_delete_confirm")` | `text("Xác nhận")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Delete Cancel button | Tap | `accessibilityId("hoi_thoai_delete_cancel")` | `text("Hủy")` | ⚠️ Chưa |
| DichHoiThoaiScreen | Toast network error | Assert | `textContains("Vui lòng kết nối mạng")` | — | ⚠️ Chưa |
| DichHoiThoaiScreen | Toast copied | Assert | `textContains("Đã sao chép")` | — | ⚠️ Chưa |
| PaywallScreen | Paywall container | Assert | `accessibilityId("paywall_screen")` | `textContains("Nâng cấp")` | ⚠️ Chưa |
| PaywallScreen | Upgrade button | Assert | `accessibilityId("paywall_upgrade_button")` | `text("Nâng cấp")` | ⚠️ Chưa |

### Hướng dẫn verify locators bằng Appium Inspector

```bash
# 1. Khởi Appium Inspector (desktop app hoặc web)
# 2. Điền capabilities từ .env
# 3. Start Session → app mở trên thiết bị
# 4. Navigate đến màn Dịch hội thoại
# 5. Tap element → xem "Accessibility ID" và "resource-id" trong Inspector
# 6. Cập nhật locators vào DichHoiThoaiScreen.java nếu khác với convention
```

---

## Known Issues & Limitations

| # | Vấn đề | Workaround |
|---|--------|-----------|
| 1 | TC_008, 009, 012, 026 cần giọng nói thực — `enabled=false` | Setup emulator virtual mic + audio injection (SoX/ffmpeg) |
| 2 | TC_013–020 cần seeded Bubble làm pre-condition | Implement audio injection trong `@BeforeMethod` hoặc dùng API mock |
| 3 | iOS offline test chưa implement (chỉ Android `ConnectionState`) | Dùng iOS Network Link Conditioner hoặc proxy-based approach |
| 4 | TC_005 cần deny mic permission trước khi test | Thêm ADB command trong `@BeforeMethod`: `adb shell pm revoke net.mazii.app android.permission.RECORD_AUDIO` |

---

## Files Created

| File | Mô tả |
|------|-------|
| `pom.xml` | Maven config — Appium 8.6, TestNG 7.8, Allure 2.25 |
| `testng.xml` | Suite config — 8 test classes |
| `.env.example` | Template config — điền App ID, device info |
| `src/main/.../config/AppConfig.java` | Đọc config từ .env |
| `src/main/.../drivers/AppiumDriverFactory.java` | Tạo Appium driver |
| `src/main/.../drivers/CapabilitiesManager.java` | Android + iOS capabilities |
| `src/main/.../screens/BaseScreen.java` | Base Screen với cross-platform locator helpers |
| `src/main/.../screens/DichScreen.java` | Tab navigation screen |
| `src/main/.../screens/DichHoiThoaiScreen.java` | Core screen — 25+ locators + methods |
| `src/main/.../screens/PaywallScreen.java` | Paywall verification |
| `src/main/.../utils/MobileGestures.java` | Swipe, scroll, tap utils |
| `src/main/.../utils/NetworkUtils.java` | Bật/tắt network (Android) |
| `src/main/.../utils/ScreenshotUtil.java` | Chụp screenshot khi fail |
| `src/main/.../utils/TestDataGenerator.java` | Sinh test data unique |
| `src/test/.../base/BaseTest.java` | Setup/teardown lifecycle |
| `src/test/.../tests/hoi_thoai/HoiThoaiEmptyStateTest.java` | TC_001 |
| `src/test/.../tests/hoi_thoai/HoiThoaiPaywallTest.java` | TC_002, 003 |
| `src/test/.../tests/hoi_thoai/HoiThoaiMicPermissionTest.java` | TC_004, 005 |
| `src/test/.../tests/hoi_thoai/HoiThoaiBubbleChatTest.java` | TC_006–011 |
| `src/test/.../tests/hoi_thoai/HoiThoaiBubbleInteractionTest.java` | TC_016–020 |
| `src/test/.../tests/hoi_thoai/HoiThoaiLanguageTest.java` | TC_021, 022 |
| `src/test/.../tests/hoi_thoai/HoiThoaiClearHistoryTest.java` | TC_023–026 |
| `src/test/.../tests/hoi_thoai/HoiThoaiOfflineTest.java` | TC_027, 028 |
| `test-data/accounts.json` | Account config template |
