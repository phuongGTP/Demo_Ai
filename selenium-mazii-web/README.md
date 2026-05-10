# Mazii Selenium Web Test Automation

Automation test scripts cho tính năng **Dịch hội thoại** trên nền tảng Web (mazii.net).

**Stack:** Selenium 4.18 · Java 11 · TestNG 7.8 · Allure 2.25 · WebDriverManager 5.7  
**Browser:** Chrome (default) · Firefox · Edge  
**Test cases nguồn:** `antigravity-testing-kit/practices/testcases/mz-dich/testcases_hoi_thoai_web_v2.md`

---

## Yêu cầu môi trường

| Công cụ | Phiên bản tối thiểu |
|---------|---------------------|
| Java JDK | 11 |
| Maven | 3.8+ |
| Chrome | 120+ (CDP offline test) |
| ChromeDriver | Tự động qua WebDriverManager |

> WebDriverManager tự động download driver phù hợp với Chrome version hiện tại — không cần cài thủ công.

---

## Cài đặt

```bash
# Copy file cấu hình mẫu
cp .env.example .env

# Điền thông tin thực vào .env
nano .env
```

Các trường quan trọng trong `.env`:

```
BASE_URL=https://mazii.net
BROWSER=chrome        # chrome | firefox | edge
HEADLESS=false

ACCOUNT_PREMIUM_EMAIL=...
ACCOUNT_PREMIUM_PASSWORD=...
ACCOUNT_STANDARD_EMAIL=...
ACCOUNT_STANDARD_PASSWORD=...
```

---

## Chạy test

### Chạy toàn bộ suite

```bash
mvn clean test
```

### Chạy một test class

```bash
mvn test -Dtest=HoiThoaiKeyboardModeTest
mvn test -Dtest=HoiThoaiOfflineTest
```

### Chạy theo group

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups="keyboard,offline"
```

### Headless mode

```bash
HEADLESS=true mvn clean test
# Hoặc:
mvn test -DHEADLESS=true
```

### Xem Allure report

```bash
mvn allure:serve
```

---

## Cấu trúc project

```
selenium-mazii-web/
├── .env.example                     # Template cấu hình
├── pom.xml                          # Maven dependencies
├── testng.xml                       # TestNG suite config
├── task.md                          # Progress tracker & locator collection
├── test-data/
│   └── accounts.json                # Account config (giá trị từ .env)
└── src/
    ├── main/java/com/mazii/web/
    │   ├── config/
    │   │   └── WebConfig.java       # Đọc config từ .env
    │   ├── drivers/
    │   │   └── WebDriverFactory.java  # Tạo Chrome/Firefox/Edge với mic prefs
    │   ├── pages/
    │   │   ├── BasePage.java        # Base: wait helpers, hover, Actions
    │   │   ├── DichPage.java        # Tab navigation
    │   │   ├── DichHoiThoaiPage.java  # Core page — 30+ locators + actions
    │   │   └── PaywallPage.java
    │   └── utils/
    │       ├── BrowserUtils.java    # CDP offline toggle, viewport check
    │       └── ScreenshotUtil.java
    └── test/java/com/mazii/web/
        ├── base/
        │   └── BaseTest.java        # Setup/teardown, screenshot on failure
        └── tests/hoi_thoai/
            ├── HoiThoaiEmptyStateTest.java       # TC_001
            ├── HoiThoaiPaywallTest.java          # TC_002, 003
            ├── HoiThoaiMicPermissionTest.java    # TC_004, 005
            ├── HoiThoaiBubbleChatTest.java       # TC_006–015
            ├── HoiThoaiBubbleInteractionTest.java # TC_016–021
            ├── HoiThoaiLanguageTest.java         # TC_022–024
            ├── HoiThoaiClearHistoryTest.java     # TC_025–028
            ├── HoiThoaiOfflineTest.java          # TC_029, 030
            └── HoiThoaiKeyboardModeTest.java     # TC_031–035
```

---

## Test Cases Coverage

| TC ID | Module | Mô tả | Status |
|-------|--------|-------|--------|
| TC_001 | Empty State | Mascot, mode tabs, mic, swap, dropdown | ✅ |
| TC_002 | Paywall | Guest mở tab → Paywall | ✅ |
| TC_003 | Paywall | Tài khoản Thường click Mic → Paywall | ✅ |
| TC_004 | Mic Permission | Lần đầu click Mic → browser dialog | ✅ |
| TC_005 | Mic Permission | Mic blocked → hướng dẫn | ✅ |
| TC_006 | Thu âm | Click Mic → Pause icon | ✅ |
| TC_007 | Thu âm | Chỉ 1 mic hoạt động cùng lúc | ✅ |
| TC_008 | Thu âm | Đang nói → Bubble tạm "..." | ⏸ (audio injection) |
| TC_009 | Thu âm | Im lặng → Bubble chính thức (auto) | ⏸ (audio injection) |
| TC_010 | Thu âm | Click Pause thủ công → Bubble | ⏸ (audio injection) |
| TC_011 | Thu âm | Không nói → không tạo Bubble | ✅ |
| TC_012 | Language | STT điều chỉnh theo ngôn ngữ mới | ⏸ (audio injection) |
| TC_013–015 | Bubble | Layout, cấu trúc, Bubble tạm | ⏸ (seeded / audio) |
| TC_016 | Bubble Action | Phát âm | ✅ |
| TC_017 | Bubble Action | Copy → toast | ✅ |
| TC_018 | Bubble Action | Deep Lookup Kanji | ⏸ (chưa implement) |
| TC_019 | Bubble Action | Hover Nhật → icon ">" hiện | ✅ |
| TC_020 | Bubble Action | Click ">" → chuyển màn Dịch | ✅ |
| TC_021 | Bubble Action | Hover Việt → không có ">" | ✅ |
| TC_022 | Language | Đổi ngôn ngữ → Bubble cũ giữ nguyên | ✅ |
| TC_023 | Language | Cặp Anh–Việt (Gap G1) | ✅ |
| TC_024 | Swap | Swap ⇄ hoán đổi 2 ngôn ngữ | ✅ |
| TC_025 | Clear History | Hover+Click Trash → Modal | ✅ |
| TC_026 | Clear History | Xác nhận xóa → Empty State | ✅ |
| TC_027 | Clear History | Hủy → giữ Bubble | ✅ |
| TC_028 | Auto-scroll | Bubble mới → tự cuộn | ⏸ (≥6 Bubble) |
| TC_029 | Offline | Offline → toast lỗi | ✅ |
| TC_030 | Offline | Mất mạng giữa chừng → dừng | ✅ |
| TC_031 | Keyboard | Tab "Nhập bàn phím" → UI đổi | ✅ |
| TC_032 | Keyboard | Nhập text + Gửi → Bubble | ✅ |
| TC_033 | Keyboard | Enter = Gửi | ✅ |
| TC_034 | Keyboard | Gửi rỗng → không tạo Bubble | ✅ |
| TC_035 | Keyboard | Chuyển lại "Thu âm" → mic trở lại | ✅ |

**Tổng:** 24 TCs ✅ · 8 TCs ⏸ `@Test(enabled=false)` hoặc chưa implement

---

## Web vs Mobile — Điểm khác biệt kỹ thuật

| Khía cạnh | Mobile (Appium) | Web (Selenium) |
|-----------|----------------|----------------|
| Driver | `AppiumDriver` | `WebDriver` |
| Page pattern | `Screen` (suffix) | `Page` (suffix) |
| Locator ưu tiên | `accessibilityId` → `resource-id` | CSS selector → XPath |
| Hover | `Actions.moveToElement()` (mobile: không dùng) | `Actions.moveToElement()` |
| Offline | `ConnectionState` (Android ADB) | Chrome DevTools Protocol (CDP) |
| Mic permission | OS-level (app capabilities) | Browser-level (Chrome prefs) |
| Layout Bubble | Nhật trái · Việt phải | Việt trái · Nhật phải |
| Xóa lịch sử | Tap Trash | Hover + Click Trash |
| Keyboard mode | Không có | ✅ Tab "Nhập từ bàn phím" |
| Swap icon | Không có | ✅ Swap (⇄) giữa 2 dropdown |

---

## Locator Verification

> ⚠️ Tất cả locators dùng `data-testid` convention — cần verify DevTools trước khi chạy thật.

```bash
# 1. Mở mazii.net → F12 → Elements
# 2. Ctrl+Shift+C → click từng element → xem html attributes
# 3. Nếu có data-testid: dùng luôn. Nếu không: tìm id/class/aria-label
# 4. Cập nhật DichHoiThoaiPage.java với locator thực tế
```

Xem đầy đủ bảng locators tại [task.md](task.md).

---

## Các bước cần thêm để enable TCs disabled

### TC_016–021, 025–027 — Seeded Bubble
Implement `seedBubbleData()` trong `@BeforeMethod` bằng keyboard mode (không cần audio):

```java
@BeforeMethod
@Override
public void setUp() {
    super.setUp();
    // Seed Bubble qua keyboard mode
    hoiThoaiPage.switchToKeyboardMode();
    hoiThoaiPage.typeInRightInput("おはようございます");
    hoiThoaiPage.clickSendRight();
    hoiThoaiPage.waitForNewBubble(0);
    hoiThoaiPage.switchToMicMode();
}
```

### TC_029–030 — CDP Offline
Chỉ hoạt động với `ChromeDriver`. Đảm bảo `BROWSER=chrome` trong `.env`.  
Nếu Chrome version ≠ 120, đổi import trong `BrowserUtils.java`:
```java
// Thay v120 → version khớp Chrome thực tế (e.g., v121, v122)
import org.openqa.selenium.devtools.v121.network.Network;
```

### Known Issues

| # | Vấn đề | Giải pháp |
|---|--------|-----------|
| 1 | Login flow chưa implement | Implement `loginAsPremium()` theo flow thực của mazii.net |
| 2 | CDP offline dùng v120 | Đổi version trong BrowserUtils khớp Chrome thực tế |
| 3 | TC_018 Deep Lookup chưa có | Cần biết locator popup sau khi click Kanji |
| 4 | Bubble seeded cần audio hoặc keyboard hack | Dùng keyboard mode trong @BeforeMethod |
