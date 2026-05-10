# [Mazii] - Test Cases: Dịch Hội Thoại (Web)

**Phiên bản:** v2.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** PDF UC3 + Figma Design (node: 13626:136135)  
**Quy trình:** AI-RBT 6 bước (FULL RBT Mode) — chạy đầy đủ  
**Tổng số TC:** 33

---

## Mô tả tính năng

Dịch hội thoại 2 chiều theo thời gian thực trên nền tảng Web (1440px). Người dùng có thể **Thu âm** (giọng nói) hoặc **Nhập từ bàn phím** (text — web-only). Kết quả dịch hiển thị dạng Bubble Chat. Yêu cầu Premium/Mazii-AI.

### Layout UI (từ Figma)

```
┌──────────────────────────────────────────────────────┐
│  ← Dịch    [Dịch văn bản] | [Dịch hội thoại ●]     │
│                                                      │
│              [Mascot / Bubble Area]                  │  ← Vùng chat cuộn
│        "Trò chuyện dễ dàng với Mazii"               │
│                                                      │
│  ┌────────────────────────────────────────────────┐  │
│  │  [Thu âm ●]  |  [Nhập từ bàn phím]            │  │  ← Mode tabs (web-only)
│  │                                                │  │
│  │  [🎤]          ⇄ (Swap)        [🎤]           │  │  ← 2 Mic + Swap
│  │  Tiếng Việt ▼               Tiếng Nhật ▼      │  │  ← Language dropdowns
│  └────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────┘
```

- Nút Mic **idle**: icon mic màu xám
- Nút Mic **đang thu âm**: icon Pause (⏸) viền xanh
- Bubble Tiếng Việt: bên trái · Bubble Tiếng Nhật: bên phải
- Bubble tạm: "..." nhấp nháy
- Bubble chính thức: text gốc + bản dịch + icons
- Bubble Tiếng Nhật: hover để thấy icon ">" → click để chuyển màn Dịch
- **Mode "Nhập từ bàn phím"**: mỗi bên có text input + nút Gửi riêng

---

## Assumptions

| # | Nội dung |
|---|---------|
| A1 | Bubble tạm xuất hiện ngay khi bắt đầu nhận diện giọng nói |
| A2 | Bubble chính thức xuất hiện sau im lặng ~1–1.5s hoặc click Pause |
| A3 | Mic permission là browser-level dialog (không phải OS) |
| A4 | Tính năng yêu cầu Premium hoặc Mazii-AI (BR02) |
| A5 | Offline → không thu âm, hiển thị toast lỗi (BR03) |
| A6 | Auto-scroll khi số Bubble vượt quá vùng nhìn |
| A7 | Icon ">" chỉ xuất hiện trên Bubble Tiếng Nhật; hover để hiện, click để kích hoạt |
| A8 | Tap Mic nhưng không nói → không tạo Bubble, về idle |
| A9 | Không lưu lịch sử liên session — mỗi lần vào là Empty State mới |
| A10 | Danh sách ngôn ngữ hội thoại = danh sách ngôn ngữ Dịch văn bản |
| A11 | STT tự động điều chỉnh theo ngôn ngữ mới sau khi đổi dropdown |
| A12 | Mode "Nhập từ bàn phím": 2 text input song song — trái (Tiếng Việt), phải (Tiếng Nhật), mỗi bên có nút Gửi riêng |
| A13 | Nhấn Enter = Click Gửi trong mode "Nhập từ bàn phím" |

---

## Risk Assessment

| Module | Risk | Lý do |
|--------|------|-------|
| M_HT02 Access Control / Paywall | HIGH | Business rule BR02, blocking |
| M_HT03 Mic Permission (Browser) | HIGH | Blocking nếu sai |
| M_HT04 Thu âm & STT | HIGH | Core flow, phụ thuộc browser API + network |
| M_HT05 Bubble Chat | HIGH | Core UX |
| M_HT10 Offline / Error Handling | HIGH | BR03, critical error path |
| M_HT11 Nhập từ bàn phím | HIGH | Tính năng web-only, nhiều edge cases |
| M_HT06 Tương tác Bubble | MEDIUM | Hover+Click thay vì Tap |
| M_HT07 Language Selection + Swap | MEDIUM | State management, Q1 confirmed |
| M_HT08 Xóa lịch sử | MEDIUM | Destructive action |
| M_HT09 Auto-scroll | MEDIUM | UX |
| M_HT01 Empty State | LOW | Trạng thái đơn giản |

---

## Business Rules

| BR | Mô tả |
|----|------|
| BR02 | Dịch hội thoại yêu cầu Premium hoặc Mazii-AI. Guest/Thường → Paywall |
| BR03 | Mất mạng → không cho thu âm/gửi text, hiển thị toast lỗi |

---

## PART 1 — M_HT01: Empty State

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_001 | M_HT01 Empty State | LOW | Empty State web hiển thị đúng khi chưa có Bubble | Tài khoản Premium, vào tab "Dịch hội thoại" lần đầu | 1. Click tab "Dịch hội thoại" | 1. Mascot + text "Trò chuyện dễ dàng với Mazii"<br>2. 3 dòng hướng dẫn sử dụng<br>3. Bottom panel: tab "Thu âm" (active) + tab "Nhập từ bàn phím"<br>4. 2 nút Mic idle + Swap icon giữa<br>5. Dropdown: Tiếng Việt (trái), Tiếng Nhật (phải) | Low | Tài khoản: Premium |

---

## PART 2 — M_HT02: Access Control / Paywall

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_002 | M_HT02 Paywall | HIGH | Guest click tab Dịch hội thoại → Paywall ngay lập tức | Chưa đăng nhập (Guest) | 1. Click tab "Dịch hội thoại" | 1. Màn hình Paywall hiển thị ngay lập tức<br>2. Không vào được màn hội thoại | Critical | Tài khoản: Guest (chưa đăng nhập) |
| WEB_HT_TC_003 | M_HT02 Paywall | HIGH | Tài khoản Thường click Mic → Paywall | Đăng nhập tài khoản Thường (Standard), đang ở màn Dịch hội thoại | 1. Click nút Mic bất kỳ | 1. Màn hình Paywall xuất hiện yêu cầu nâng cấp Premium/Mazii-AI<br>2. Không bắt đầu thu âm | Critical | Tài khoản: Thường (Standard) |

---

## PART 3 — M_HT03: Mic Permission (Browser-level)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_004 | M_HT03 Mic Permission | HIGH | Lần đầu click Mic → trình duyệt xin quyền Microphone (browser dialog) | Tài khoản Premium, chưa cấp quyền mic cho site này | 1. Click nút Mic bất kỳ | 1. Browser dialog xin quyền Microphone xuất hiện (không phải OS dialog)<br>2. Chưa thu âm ngay | High | Tài khoản: Premium, browser mic permission = chưa cấp |
| WEB_HT_TC_005 | M_HT03 Mic Permission | HIGH | Từ chối quyền Mic (browser) → hướng dẫn cài đặt trình duyệt | Tài khoản Premium, đã block mic permission trên trình duyệt | 1. Click nút Mic | 1. Hướng dẫn cấp lại quyền mic từ cài đặt trình duyệt<br>2. Không thu âm | High | Tài khoản: Premium, browser mic = Blocked |

---

## PART 4 — M_HT04: Thu âm & STT

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_006 | M_HT04 Thu âm | HIGH | Click Mic → nút chuyển sang Pause icon, hệ thống lắng nghe | Tài khoản Premium, đã cấp quyền mic, mode "Thu âm" active | 1. Click nút Mic bên Tiếng Việt (trái) | 1. Nút Mic bên trái chuyển thành Pause (⏸) viền xanh<br>2. Nút Mic bên phải vẫn idle (xám)<br>3. Hệ thống bắt đầu lắng nghe | Critical | Tài khoản: Premium, ngôn ngữ: Việt–Nhật |
| WEB_HT_TC_007 | M_HT04 Thu âm | HIGH | Chỉ 1 bên thu âm tại 1 thời điểm | Đang thu âm bên Tiếng Việt (Pause icon hiển thị) | 1. Click Mic bên Tiếng Nhật trong khi bên Tiếng Việt đang thu | 1. Bên Tiếng Nhật không bắt đầu thu đồng thời<br>2. Hoặc: bên Tiếng Việt dừng → bên Tiếng Nhật bắt đầu<br>(xác nhận behavior với dev) | High | — |
| WEB_HT_TC_008 | M_HT04 Thu âm | HIGH | Đang nói → Bubble tạm "..." xuất hiện ngay lập tức | Đang thu âm (Pause icon hiển thị) | 1. Bắt đầu nói vào micro | 1. Bubble tạm xuất hiện ngay tại vùng chat phía người nói<br>2. Bên trong Bubble hiển thị "..." nhấp nháy | Critical | Đọc: "おはようございます" |
| WEB_HT_TC_009 | M_HT04 Thu âm | HIGH | Im lặng ~1–1.5s → Bubble chính thức tự động | Đang có Bubble tạm "..." | 1. Nói xong, im lặng khoảng 1–1.5 giây | 1. Nút Mic về idle<br>2. Bubble tạm biến mất<br>3. Bubble chính thức: text gốc + bản dịch | Critical | Đọc: "おはようございます" (Nhật→Việt), im lặng 1.5s |
| WEB_HT_TC_010 | M_HT04 Thu âm | HIGH | Click Pause thủ công → Bubble chính thức | Đang thu âm (Pause icon hiển thị) | 1. Click nút Pause (⏸) để dừng | 1. Thu âm dừng ngay<br>2. Nút về idle (mic xám)<br>3. Bubble chính thức xuất hiện với kết quả dịch | High | — |
| WEB_HT_TC_011 | M_HT04 Thu âm | HIGH | Click Mic nhưng không nói (im lặng hoàn toàn) → không tạo Bubble | Đang thu âm (Pause icon hiển thị) | 1. Không nói gì<br>2. Click Pause hoặc chờ silence detection | 1. Không tạo Bubble<br>2. Nút Mic về idle<br>3. Màn hình giữ nguyên | High | Im lặng hoàn toàn ≥ 3 giây |
| WEB_HT_TC_012 | M_HT04 Thu âm | HIGH | Đổi ngôn ngữ → STT lần click Mic tiếp theo dùng ngôn ngữ mới | Đang dùng cặp Việt–Nhật, có quyền mic | 1. Đổi dropdown bên phải từ "Tiếng Nhật" sang "Tiếng Anh"<br>2. Click Mic bên vừa đổi<br>3. Nói một câu Tiếng Anh | 1. STT nhận diện đúng Tiếng Anh<br>2. Bubble chính thức: text Tiếng Anh + bản dịch Tiếng Việt | High | Đổi sang Tiếng Anh, đọc: "Good morning" |

---

## PART 5 — M_HT05: Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_013 | M_HT05 Bubble | HIGH | Bubble xuất hiện đúng phía người nói | Đã có Bubble từ cả 2 bên | 1. Bên Tiếng Việt nói → quan sát vị trí<br>2. Bên Tiếng Nhật nói → quan sát vị trí | 1. Bubble Tiếng Việt bên trái<br>2. Bubble Tiếng Nhật bên phải<br>3. Không bị lẫn vị trí | Critical | Nói 1 câu mỗi bên |
| WEB_HT_TC_014 | M_HT05 Bubble | HIGH | Cấu trúc Bubble chính thức đủ thành phần | Đang có Bubble chính thức trên màn hình | 1. Quan sát nội dung bên trong 1 Bubble | 1. Text gốc (ngôn ngữ người nói)<br>2. Bản dịch tương ứng<br>3. Icon Phát âm<br>4. Icon Copy<br>5. (Bubble Tiếng Nhật) hover → icon ">" hiện ra | High | — |
| WEB_HT_TC_015 | M_HT05 Bubble | HIGH | Bubble tạm "..." hiển thị đúng trong khi xử lý | Đang thu âm và đang nói | 1. Quan sát Bubble ngay khi bắt đầu nhận diện | 1. Bubble tạm chứa "..." nhấp nháy<br>2. Chưa có text thực<br>3. Bubble đúng phía người nói | High | Đọc: "Xin chào" |

---

## PART 6 — M_HT06: Tương tác trên Bubble

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_016 | M_HT06 Tương tác | MEDIUM | Click icon Phát âm trên Bubble | Đang có Bubble chính thức | 1. Click icon Phát âm trên Bubble | 1. Audio TTS phát nội dung bản dịch<br>2. Không bị lỗi hoặc im lặng | High | — |
| WEB_HT_TC_017 | M_HT06 Tương tác | MEDIUM | Click icon Copy trên Bubble → toast xác nhận | Đang có Bubble chính thức | 1. Click icon Copy trên Bubble | 1. Nội dung Bubble copy vào clipboard<br>2. Toast "Đã sao chép" hiển thị | High | — |
| WEB_HT_TC_018 | M_HT06 Deep Lookup | MEDIUM | Click vào từ trong Bubble → popup tra từ sâu | Đang có Bubble chứa chữ Kanji | 1. Click vào từ "東" trong Bubble Tiếng Nhật | 1. Popup chi tiết từ mở ra<br>2. Hiển thị: nghĩa, phiên âm (hiragana/romaji), ví dụ | High | Bubble chứa: "東京" → click "東" |
| WEB_HT_TC_019 | M_HT06 Japanese ">" | MEDIUM | Hover Bubble Tiếng Nhật → icon ">" hiện ra | Đang có Bubble Tiếng Nhật | 1. Di chuột (hover) vào Bubble Tiếng Nhật | 1. Icon ">" xuất hiện khi hover<br>2. Trước khi hover: icon không hiển thị | High | Bubble: "おはようございます" |
| WEB_HT_TC_020 | M_HT06 Japanese ">" | MEDIUM | Click icon ">" trên Bubble Tiếng Nhật → chuyển màn Dịch văn bản | Đang hover vào Bubble Tiếng Nhật, icon ">" đang hiển thị | 1. Click icon ">" | 1. Chuyển sang màn hình Dịch văn bản<br>2. Nội dung Bubble tự động điền vào ô nguồn | High | Bubble: "おはようございます" |
| WEB_HT_TC_021 | M_HT06 Japanese ">" | MEDIUM | Hover Bubble Tiếng Việt/Anh/Hàn → icon ">" KHÔNG hiện ra | Đang có Bubble từ bên không phải Tiếng Nhật | 1. Hover vào Bubble Tiếng Việt | 1. Icon ">" không xuất hiện | Medium | Bubble ngôn ngữ: Tiếng Việt |

---

## PART 7 — M_HT07: Language Selection + Swap

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_022 | M_HT07 Language | MEDIUM | Đổi ngôn ngữ → Bubble cũ giữ nguyên, Bubble mới theo ngôn ngữ mới | Đang có Bubble trên màn hình (cặp Việt–Nhật) | 1. Click dropdown ngôn ngữ một bên<br>2. Chọn "Tiếng Hàn"<br>3. Thu âm/gửi text tiếp | 1. Bubble cũ giữ nguyên nội dung và ngôn ngữ ban đầu<br>2. Bubble mới dịch theo cặp ngôn ngữ đã đổi | Medium | Đổi từ Tiếng Nhật → Tiếng Hàn, đọc: "안녕하세요" |
| WEB_HT_TC_023 | M_HT07 Language | MEDIUM | Test cặp ngôn ngữ thứ 3: Anh–Việt | Tài khoản Premium, cặp hiện tại: Việt–Nhật | 1. Đổi 1 bên sang "Tiếng Anh"<br>2. Thu âm/nhập: "Good morning, how are you?"<br>3. Quan sát Bubble chính thức | 1. STT/nhập liệu nhận diện đúng Tiếng Anh<br>2. Bubble: text Anh + bản dịch Tiếng Việt chính xác | Medium | Cặp: Anh–Việt, nhập: "Good morning, how are you?" |
| WEB_HT_TC_024 | M_HT07 Swap | MEDIUM | Click Swap icon → 2 ngôn ngữ hoán đổi vị trí | Đang ở màn hội thoại web, cặp: Tiếng Việt (trái) – Tiếng Nhật (phải) | 1. Click icon Swap (⇄) giữa 2 dropdown | 1. Dropdown trái → Tiếng Nhật<br>2. Dropdown phải → Tiếng Việt<br>3. Bubble cũ (nếu có) giữ nguyên | Medium | Cặp ban đầu: Việt–Nhật |

---

## PART 8 — M_HT08: Xóa lịch sử

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_025 | M_HT08 Xóa | MEDIUM | Hover + Click Trash icon → Modal xác nhận "không thể hoàn tác" | Đang có Bubble trên màn hình | 1. Hover vào icon Trash (🗑) trên header<br>2. Click icon Trash | 1. Modal xuất hiện với cảnh báo: toàn bộ tin nhắn sẽ bị xóa, không thể hoàn tác<br>2. Có 2 button: "Hủy" và "Xác nhận" | Medium | — |
| WEB_HT_TC_026 | M_HT08 Xóa | MEDIUM | Xác nhận xóa → về Empty State + Toast | Modal xác nhận đang hiển thị | 1. Click nút "Xác nhận" | 1. Toàn bộ Bubble bị xóa<br>2. Màn hình về Empty State: "Trò chuyện dễ dàng với Mazii"<br>3. Toast xác nhận xóa thành công | Medium | — |
| WEB_HT_TC_027 | M_HT08 Xóa | LOW | Hủy xóa → Bubble giữ nguyên | Modal xác nhận đang hiển thị | 1. Click nút "Hủy" | 1. Modal đóng lại<br>2. Toàn bộ Bubble Chat vẫn còn nguyên | Low | — |

---

## PART 9 — M_HT09: Auto-scroll

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_028 | M_HT09 Auto-scroll | MEDIUM | Bubble mới → màn tự cuộn, Bubble mới nhất luôn hiển thị đầy đủ | Đã có hơn 4–5 Bubble lấp đầy màn hình | 1. Tạo thêm Bubble mới | 1. Màn hình tự cuộn lên<br>2. Bubble mới nhất hiển thị đầy đủ trong vùng nhìn<br>3. Không cần cuộn thủ công | Medium | Gửi liên tục ≥ 6 lần |

---

## PART 10 — M_HT10: Offline / Error Handling

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_029 | M_HT10 Offline | HIGH | Offline trước khi click Mic → toast lỗi, không thu âm | Tài khoản Premium, browser offline, mode "Thu âm" | 1. Bật browser offline (DevTools → Network → Offline)<br>2. Click nút Mic | 1. Không bắt đầu thu âm<br>2. Toast/Banner: "Vui lòng kết nối mạng để sử dụng Dịch hội thoại"<br>3. Nút Mic không chuyển sang Pause | Critical | Tài khoản: Premium, browser: Offline mode |
| WEB_HT_TC_030 | M_HT10 Offline | HIGH | Mất mạng giữa chừng khi đang thu âm → dừng + thông báo | Đang trong phiên thu âm có mạng | 1. Click Mic, bắt đầu nói<br>2. Bật Offline trong lúc đang nói | 1. Thu âm dừng lại<br>2. Bubble tạm biến mất hoặc chuyển trạng thái lỗi<br>3. Toast thông báo mất kết nối | Critical | Tài khoản: Premium |

---

## PART 11 — M_HT11: Nhập từ bàn phím (Web-only)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_031 | M_HT11 Keyboard | HIGH | Click tab "Nhập từ bàn phím" → UI chuyển sang text input, Mic ẩn | Đang ở mode "Thu âm" (default) | 1. Click tab "Nhập từ bàn phím" trong bottom panel | 1. Nút Mic bị ẩn<br>2. Mỗi bên hiển thị text input + nút Gửi<br>3. Tab "Nhập từ bàn phím" highlight active | High | — |
| WEB_HT_TC_032 | M_HT11 Keyboard | HIGH | Nhập text + Click Gửi → Bubble tạm → Bubble chính thức | Mode "Nhập từ bàn phím" đang active | 1. Nhập text vào ô bên Tiếng Việt (trái)<br>2. Click nút Gửi | 1. Bubble tạm "..." xuất hiện<br>2. Sau xử lý: Bubble chính thức với text gốc + bản dịch Tiếng Nhật | Critical | Nhập: "Xin chào, bạn khỏe không?" |
| WEB_HT_TC_033 | M_HT11 Keyboard | HIGH | Nhấn Enter → hành vi giống nút Gửi | Mode "Nhập từ bàn phím", đã nhập text | 1. Nhập text vào ô<br>2. Nhấn phím Enter | 1. Bubble được tạo (giống click Gửi)<br>2. Ô text trống lại sau khi gửi | High | Nhập: "Hello" vào ô bên Tiếng Việt |
| WEB_HT_TC_034 | M_HT11 Keyboard | HIGH | Gửi text rỗng → không tạo Bubble | Mode "Nhập từ bàn phím", ô text đang trống | 1. Để trống ô<br>2. Click Gửi hoặc nhấn Enter | 1. Không tạo Bubble<br>2. Nút Gửi disabled hoặc không phản hồi | High | Input: "" (rỗng) |
| WEB_HT_TC_035 | M_HT11 Keyboard | MEDIUM | Chuyển lại "Thu âm" → UI về mic, Bubble cũ giữ nguyên | Đang ở mode "Nhập từ bàn phím", có Bubble trên màn hình | 1. Click tab "Thu âm" | 1. Text input bị ẩn<br>2. 2 nút Mic idle xuất hiện trở lại<br>3. Tab "Thu âm" highlight active<br>4. Bubble Chat cũ vẫn giữ nguyên | Medium | — |

---

## Traceability Matrix

| TC ID | Module | UC/BR | Scenario / Nguồn |
|-------|--------|-------|----------------|
| WEB_HT_TC_001 | M_HT01 | UC3 Main Flow | Empty State web |
| WEB_HT_TC_002 | M_HT02 | UC3 E1, BR02 | Paywall — Guest |
| WEB_HT_TC_003 | M_HT02 | UC3 E1, BR02 | Paywall — Thường |
| WEB_HT_TC_004 | M_HT03 | UC3 E2 | Xin quyền mic lần đầu (Browser) |
| WEB_HT_TC_005 | M_HT03 | UC3 E2 | Từ chối quyền mic (Browser) |
| WEB_HT_TC_006 | M_HT04 | UC3 Main Flow | Click Mic → Pause icon |
| WEB_HT_TC_007 | M_HT04 | UC3 Main Flow | 1 bên thu âm tại 1 thời điểm |
| WEB_HT_TC_008 | M_HT04 | UC3 Main Flow | Bubble tạm "..." |
| WEB_HT_TC_009 | M_HT04 | UC3 Main Flow | Im lặng → Bubble chính thức (auto) |
| WEB_HT_TC_010 | M_HT04 | UC3 AF1 | Pause thủ công → Bubble chính thức |
| WEB_HT_TC_011 | M_HT04 | Gap G2 | Không nói → không tạo Bubble |
| WEB_HT_TC_012 | M_HT04, M_HT07 | UC3 Main Flow, Q1 | STT điều chỉnh theo ngôn ngữ mới |
| WEB_HT_TC_013 | M_HT05 | UC3 Main Flow | Bubble đúng phía người nói |
| WEB_HT_TC_014 | M_HT05 | UC3 Main Flow | Cấu trúc Bubble chính thức |
| WEB_HT_TC_015 | M_HT05 | UC3 Main Flow | Bubble tạm "..." |
| WEB_HT_TC_016 | M_HT06 | Figma: Phát âm | Phát âm từ Bubble |
| WEB_HT_TC_017 | M_HT06 | Figma: Copy | Copy từ Bubble |
| WEB_HT_TC_018 | M_HT06 | Figma: Deep Lookup | Deep Lookup từ Bubble |
| WEB_HT_TC_019 | M_HT06 | Figma: ">" Nhật | Hover → icon ">" hiện ra |
| WEB_HT_TC_020 | M_HT06 | Figma: ">" Nhật | Click ">" → chuyển màn Dịch |
| WEB_HT_TC_021 | M_HT06 | Figma: ">" Nhật | Không phải Nhật → ">" không hiện |
| WEB_HT_TC_022 | M_HT07 | UC3 Main Flow | Đổi ngôn ngữ — giữ Bubble cũ |
| WEB_HT_TC_023 | M_HT07 | Q1 confirmed, Gap G1 | Test cặp Anh–Việt |
| WEB_HT_TC_024 | M_HT07 | Figma: Swap | Swap hoán đổi 2 ngôn ngữ |
| WEB_HT_TC_025 | M_HT08 | UC3 AF2, Figma | Hover+Click Trash → Modal |
| WEB_HT_TC_026 | M_HT08 | UC3 AF2 | Xác nhận → Empty State |
| WEB_HT_TC_027 | M_HT08 | Figma | Hủy → giữ nguyên |
| WEB_HT_TC_028 | M_HT09 | UC3 Main Flow | Auto-scroll |
| WEB_HT_TC_029 | M_HT10 | UC3 E3, BR03 | Offline — trước khi click Mic |
| WEB_HT_TC_030 | M_HT10 | BR03 | Offline — mất mạng giữa chừng |
| WEB_HT_TC_031 | M_HT11 | Figma web-only | Tab "Nhập từ bàn phím" → text input |
| WEB_HT_TC_032 | M_HT11 | Figma web-only | Nhập text + Gửi → Bubble |
| WEB_HT_TC_033 | M_HT11 | Figma web-only | Enter = Gửi |
| WEB_HT_TC_034 | M_HT11 | Figma web-only | Gửi rỗng → không tạo Bubble |
| WEB_HT_TC_035 | M_HT11 | Figma web-only | Chuyển lại "Thu âm" → mic trở lại |

---

## So sánh Web vs Mobile (Key Differences)

| Điểm khác biệt | Mobile | Web |
|----------------|--------|-----|
| Mode nhập liệu | Thu âm duy nhất | Thu âm + Nhập từ bàn phím |
| Mic Permission | OS-level dialog | Browser-level dialog |
| Empty state text | "Nhấn vào micro để bắt đầu giao tiếp" | "Trò chuyện dễ dàng với Mazii" |
| Icon ">" trên Bubble Nhật | Luôn hiển thị, Tap để navigate | Hover để hiện, Click để navigate |
| Xóa lịch sử | Tap Trash | Hover + Click Trash |
| Swap ngôn ngữ | Không có (từ Figma mobile) | Swap icon (⇄) giữa 2 dropdown |
| Layout Bubble | Nhật trái / Việt phải | Việt trái / Nhật phải |
