# Mazii Appium Mobile Test Automation

Automation test scripts cho tính năng **Dịch hội thoại** trên app Mazii Mobile.

**Stack:** Appium 8.6 · Java 11 · TestNG 7.8 · Allure 2.25  
**Platform:** Android + iOS  
**Test cases nguồn:** `antigravity-testing-kit/practices/testcases/mz-dich/testcases_hoi_thoai_mobile_v2.md`

---

## Yêu cầu môi trường

| Công cụ | Phiên bản tối thiểu |
|---------|---------------------|
| Java JDK | 11 |
| Maven | 3.8+ |
| Appium Server | 2.x |
| Appium UiAutomator2 driver | 2.x (Android) |
| Appium XCUITest driver | 4.x (iOS) |
| Android SDK / ADB | API 28+ |
| Xcode | 14+ (iOS only) |

---

## Cài đặt

### 1. Cài Appium Server

```bash
npm install -g appium
appium driver install uiautomator2   # Android
appium driver install xcuitest       # iOS
```

### 2. Clone và cấu hình project

```bash
# Copy file cấu hình mẫu
cp .env.example .env

# Chỉnh sửa .env với thông tin thiết bị và tài khoản thực
nano .env
```

Các trường bắt buộc trong `.env`:

```
PLATFORM=android                          # android | ios
ANDROID_DEVICE_NAME=emulator-5554         # Tên thiết bị (adb devices)
ANDROID_PLATFORM_VERSION=13.0
ANDROID_APP_PACKAGE=net.mazii.app
ANDROID_APP_ACTIVITY=com.mazii.app.MainActivity
ANDROID_APP_PATH=apps/mazii.apk           # Đường dẫn tới APK (bỏ trống nếu app đã cài)

ACCOUNT_PREMIUM_EMAIL=...
ACCOUNT_PREMIUM_PASSWORD=...
ACCOUNT_STANDARD_EMAIL=...
ACCOUNT_STANDARD_PASSWORD=...
```

### 3. Đặt app file (nếu cần cài mới)

```bash
# Android
cp /path/to/mazii.apk apps/mazii.apk

# iOS
cp /path/to/mazii.ipa apps/mazii.ipa
```

> Nếu app đã được cài sẵn trên thiết bị, để trống `ANDROID_APP_PATH` / `IOS_APP_PATH`.

---

## Chạy test

### Khởi động Appium Server

```bash
appium --port 4723
```

### Chạy toàn bộ suite

```bash
mvn clean test
```

### Chạy một test class cụ thể

```bash
mvn test -Dtest=HoiThoaiEmptyStateTest
mvn test -Dtest=HoiThoaiBubbleChatTest
```

### Chạy theo group

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
```

### Xem Allure report

```bash
mvn allure:serve
```

---

## Cấu trúc project

```
appium-mazii/
├── .env.example                    # Template cấu hình — copy thành .env
├── pom.xml                         # Maven dependencies
├── testng.xml                      # TestNG suite config
├── task.md                         # Progress tracker & locator collection
├── apps/                           # APK / IPA files (gitignored)
├── test-data/
│   └── accounts.json               # Account config (giá trị từ .env)
└── src/
    ├── main/java/com/mazii/
    │   ├── config/
    │   │   └── AppConfig.java      # Đọc config từ .env
    │   ├── drivers/
    │   │   ├── AppiumDriverFactory.java
    │   │   └── CapabilitiesManager.java
    │   ├── screens/
    │   │   ├── BaseScreen.java     # Base với cross-platform locator helpers
    │   │   ├── DichScreen.java     # Tab navigation (Dịch văn bản / Dịch hội thoại)
    │   │   ├── DichHoiThoaiScreen.java  # Core screen — 25+ locators + actions
    │   │   └── PaywallScreen.java
    │   └── utils/
    │       ├── MobileGestures.java
    │       ├── NetworkUtils.java   # Bật/tắt network Android (offline tests)
    │       ├── ScreenshotUtil.java
    │       └── TestDataGenerator.java
    └── test/java/com/mazii/
        ├── base/
        │   └── BaseTest.java       # Setup/teardown, screenshot on failure
        └── tests/hoi_thoai/
            ├── HoiThoaiEmptyStateTest.java       # TC_001
            ├── HoiThoaiPaywallTest.java          # TC_002, 003
            ├── HoiThoaiMicPermissionTest.java    # TC_004, 005
            ├── HoiThoaiBubbleChatTest.java       # TC_006–011
            ├── HoiThoaiBubbleInteractionTest.java # TC_013–020
            ├── HoiThoaiLanguageTest.java         # TC_021, 022
            ├── HoiThoaiClearHistoryTest.java     # TC_023–026
            └── HoiThoaiOfflineTest.java          # TC_027, 028
```

---

## Test Cases Coverage

| TC ID | Module | Mô tả | Status |
|-------|--------|-------|--------|
| TC_001 | Empty State | Hiển thị đúng khi chưa có Bubble | ✅ |
| TC_002 | Paywall | Guest mở tab → Paywall | ✅ |
| TC_003 | Paywall | Tài khoản Thường tap Mic → Paywall | ✅ |
| TC_004 | Mic Permission | Lần đầu tap Mic → OS dialog xin quyền | ✅ |
| TC_005 | Mic Permission | Deny quyền → hướng dẫn vào Settings | ✅ |
| TC_006 | Bubble Chat | Tap Mic → icon chuyển sang Pause | ✅ |
| TC_007 | Bubble Chat | Chỉ 1 mic hoạt động cùng lúc | ✅ |
| TC_008 | Bubble Chat | Đang nói → Bubble tạm "..." xuất hiện | ⏸ (cần audio injection) |
| TC_009 | Bubble Chat | Im lặng → Bubble chính thức tự động | ⏸ (cần audio injection) |
| TC_010 | Bubble Chat | Tap Pause thủ công → Bubble chính thức | ✅ |
| TC_011 | Bubble Chat | Không nói → không tạo Bubble | ✅ |
| TC_012 | Language | STT điều chỉnh theo ngôn ngữ mới | ⏸ (cần audio injection) |
| TC_013–015 | Bubble Layout | Kiểm tra vị trí và cấu trúc Bubble | ✅ (cần seeded Bubble) |
| TC_016 | Bubble Action | Tap Phát âm → TTS phát | ✅ |
| TC_017 | Bubble Action | Tap Copy → toast "Đã sao chép" | ✅ |
| TC_018 | Bubble Action | Deep Lookup từ Kanji trong Bubble | ✅ |
| TC_019 | Bubble Action | Tap ">" Bubble Nhật → màn Dịch | ✅ |
| TC_020 | Bubble Action | Bubble Việt KHÔNG có icon ">" | ✅ |
| TC_021 | Language | Đổi ngôn ngữ → Bubble cũ giữ nguyên | ✅ |
| TC_022 | Language | Cặp Anh–Việt hoạt động đúng (Gap G1) | ✅ |
| TC_023 | Clear History | Tap Trash → Modal xác nhận xuất hiện | ✅ |
| TC_024 | Clear History | Xác nhận xóa → Empty State | ✅ |
| TC_025 | Clear History | Hủy xóa → Bubble giữ nguyên | ✅ |
| TC_026 | Clear History | Auto-scroll khi có Bubble mới | ⏸ (cần ≥6 Bubble) |
| TC_027 | Offline | Offline → toast lỗi khi tap Mic | ✅ |
| TC_028 | Offline | Mất mạng giữa chừng → dừng + toast | ✅ |

**Tổng:** 22 TCs ✅ · 4 TCs ⏸ `@Test(enabled=false)`

---

## Locator Verification

> ⚠️ Tất cả locators trong `DichHoiThoaiScreen.java` dựa trên convention Figma — **cần verify bằng Appium Inspector** trước khi chạy thật.

```bash
# 1. Mở Appium Inspector (https://inspector.appiumpro.com hoặc desktop app)
# 2. Điền capabilities từ .env
# 3. Start Session → navigate đến màn Dịch hội thoại
# 4. Tap từng element → xem Accessibility ID và resource-id
# 5. Cập nhật DichHoiThoaiScreen.java nếu khác với convention
```

Xem đầy đủ bảng locators tại [task.md](task.md).

---

## Các TCs cần bước chuẩn bị thêm

### TC_004, 005 — Mic Permission
TC_004 yêu cầu app chưa từng cấp quyền mic (fresh install):
```bash
adb uninstall net.mazii.app
# Hoặc chỉ reset permission:
adb shell pm revoke net.mazii.app android.permission.RECORD_AUDIO
```

### TC_008, 009, 012, 026 — Audio Injection
Cần virtual mic trên emulator để inject audio:
```bash
# Cài SoX
brew install sox

# Inject file âm thanh vào emulator
# (Cần config theo từng emulator — xem AOSP audio injection docs)
```

### TC_013–020 — Seeded Bubble
Các TC này cần Bubble Chat có sẵn. Implement `seedBubbleData()` trong `@BeforeMethod` bằng một trong hai cách:
- Audio injection trong setup
- API mock để tạo Bubble data

---

## Known Issues

| # | Vấn đề | Giải pháp |
|---|--------|-----------|
| 1 | TC_008/009/012/026 cần giọng nói thực | Setup virtual mic + audio injection (SoX/ffmpeg) |
| 2 | TC_013–020 cần Bubble pre-condition | Implement `seedBubbleData()` hoặc API mock |
| 3 | iOS offline test chưa implement | Dùng Network Link Conditioner hoặc proxy |
| 4 | TC_005 cần deny mic permission trước | `adb shell pm revoke net.mazii.app android.permission.RECORD_AUDIO` |
