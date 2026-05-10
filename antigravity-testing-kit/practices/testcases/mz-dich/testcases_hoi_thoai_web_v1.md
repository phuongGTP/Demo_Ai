# [Mazii] - Test Cases: Dịch Hội Thoại (Web)

**Phiên bản:** v1.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** PDF UC3 + Figma Design (node: 13626:136135)  
**Quy trình:** AI-RBT 6 bước  
**Tổng số TC:** 30

---

## Mô tả tính năng

Dịch hội thoại 2 chiều theo thời gian thực trên nền tảng Web (1440px). Người dùng có thể **Thu âm** (giọng nói) hoặc **Nhập từ bàn phím** (text). Kết quả dịch hiển thị dạng Bubble Chat. Đây là tính năng yêu cầu Premium/Mazii-AI.

### Layout UI (từ Figma)

```
┌──────────────────────────────────────────────────────┐
│  ← Dịch          Dịch hội thoại                     │  ← Tabs: [Dịch văn bản] | [Dịch hội thoại ●]
│                                                      │
│              [Mascot / Bubble Area]                  │  ← Vùng chat cuộn
│        "Trò chuyện dễ dàng với Mazii"               │
│                                                      │
│  ┌────────────────────────────────────────────────┐  │
│  │  [Thu âm]  |  [Nhập từ bàn phím]              │  │  ← Mode tabs (web-only)
│  │                                                │  │
│  │  [🎤]         ⇄ (Swap)         [🎤]           │  │  ← 2 Mic buttons + Swap
│  │  Tiếng Việt ▼               Tiếng Nhật ▼      │  │  ← Language dropdowns
│  └────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────┘
```

**Trạng thái nút Mic:**
- **Idle**: icon mic màu xám
- **Đang thu âm**: icon Pause (⏸) viền xanh lam

**Trạng thái Bubble Chat:**
- **Bubble tạm**: "..." nhấp nháy trong khi xử lý
- **Bubble chính thức**: text gốc + bản dịch + icons (Phát âm, Copy, Deep Lookup)
- **Bubble Tiếng Nhật**: hover để thấy icon ">" → click để chuyển màn Dịch

**Mode "Nhập từ bàn phím" (web-only):**
- Mic được ẩn đi
- Mỗi bên hiển thị text input + nút Gửi
- Bubble được tạo khi nhấn Gửi (hoặc Enter)

---

## Assumptions

| # | Nội dung |
|---|---------|
| A1 | Bubble tạm xuất hiện ngay khi bắt đầu nhận diện giọng nói |
| A2 | Bubble chính thức xuất hiện sau khi im lặng ~1–1.5s hoặc click Pause |
| A3 | Browser mic permission là dialog của trình duyệt (không phải OS) |
| A4 | Tính năng yêu cầu Premium hoặc Mazii-AI (BR02) |
| A5 | Offline → không thu âm, hiển thị thông báo lỗi (BR03) |
| A6 | Auto-scroll khi số Bubble vượt quá vùng nhìn |
| A7 | Interaction: Hover để reveal icon ">", Click để kích hoạt (không phải Tap) |
| A8 | Mode "Nhập từ bàn phím": nhấn Gửi hoặc Enter → tạo Bubble |
| A9 | Không giới hạn ký tự khi nhập từ bàn phím |

---

## Risk Assessment

| Module | Risk | Lý do |
|--------|------|-------|
| M_HWT3 Thu âm & Voice Recognition | HIGH | Core flow, phụ thuộc browser API |
| M_HWT4 Mode Nhập từ bàn phím | HIGH | Tính năng web-only, không có trên mobile |
| M_HWT5 Bubble Chat | HIGH | Core UX |
| M_HWT9 Paywall / Access Control | HIGH | Business rule BR02 |
| M_HWT8 Offline | HIGH | Error handling |
| M_HWT2 Browser Mic Permission | HIGH | Blocking nếu sai |
| M_HWT6 Actions trên Bubble | MEDIUM | Hover/Click khác Tap |
| M_HWT7 Language / Clear History | MEDIUM | State management |
| M_HWT1 Empty State | LOW | Trạng thái đơn giản |

---

## Business Rules áp dụng

| BR | Mô tả |
|----|------|
| BR02 | Dịch hội thoại yêu cầu Premium hoặc Mazii-AI. Guest/Thường → Paywall |
| BR03 | Mất mạng → không cho thu âm, hiển thị toast/banner lỗi |

---

## PART 1 — M_HWT1: Empty State + M_HWT9: Paywall / Access

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_001 | M_HWT1 Empty | LOW | Empty State hiển thị đúng khi chưa có Bubble Chat | Đăng nhập tài khoản Premium, vào tab "Dịch hội thoại" lần đầu (chưa có lịch sử) | 1. Mở tab "Dịch hội thoại" trên màn Dịch web | 1. Màn hình hiển thị mascot + text "Trò chuyện dễ dàng với Mazii"<br>2. Có 3 dòng hướng dẫn sử dụng<br>3. Bottom panel hiển thị 2 mode tabs: "Thu âm" (active) và "Nhập từ bàn phím"<br>4. Mode "Thu âm": 2 nút Mic idle + language dropdowns (Tiếng Việt ▼ trái, Tiếng Nhật ▼ phải) + Swap icon giữa | Low | Tài khoản: Premium |
| WEB_HT_TC_002 | M_HWT9 Paywall | HIGH | Tài khoản Guest click tab "Dịch hội thoại" → Paywall | Chưa đăng nhập hoặc tài khoản Guest | 1. Click tab "Dịch hội thoại" | 1. Màn hình Paywall hiển thị ngay lập tức<br>2. Không vào được màn hội thoại | Critical | Tài khoản: Guest |
| WEB_HT_TC_003 | M_HWT9 Paywall | HIGH | Tài khoản Thường click nút Mic → Paywall | Đang ở màn Dịch hội thoại web, tài khoản Thường | 1. Click nút Mic bất kỳ | 1. Màn hình Paywall hiển thị yêu cầu nâng cấp Premium/Mazii-AI<br>2. Không bắt đầu thu âm | Critical | Tài khoản: Thường (Standard) |

---

## PART 2 — M_HWT2: Browser Mic Permission

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_004 | M_HWT2 Permission | HIGH | Lần đầu click Mic — trình duyệt xin quyền Microphone | Tài khoản Premium, chưa từng cấp quyền mic cho site này trên trình duyệt | 1. Click nút Mic bất kỳ | 1. Trình duyệt hiển thị dialog xin quyền Microphone (browser-level, không phải OS)<br>2. Chưa thu âm ngay | High | Tài khoản: Premium, trình duyệt chưa cấp quyền mic cho site |
| WEB_HT_TC_005 | M_HWT2 Permission | HIGH | Từ chối quyền Mic trên trình duyệt → thông báo hướng dẫn | Đã từ chối quyền mic trên trình duyệt trước đó (hoặc block trong dialog) | 1. Click nút Mic | 1. Thông báo hướng dẫn cấp lại quyền mic từ cài đặt trình duyệt<br>2. Không thu âm | High | Tài khoản: Premium, trình duyệt đã deny mic permission |

---

## PART 3 — M_HWT3: Thu âm & Voice Recognition

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_006 | M_HWT3 Recording | HIGH | Click Mic → nút chuyển sang trạng thái "Đang nghe" | Tài khoản Premium, đã cấp quyền mic, mode "Thu âm" đang active | 1. Click nút Mic bên Tiếng Việt (trái) | 1. Nút Mic bên trái chuyển thành icon Pause (⏸) viền xanh<br>2. Nút Mic bên phải vẫn idle (xám)<br>3. Hệ thống bắt đầu lắng nghe | Critical | Tài khoản: Premium |
| WEB_HT_TC_007 | M_HWT3 Recording | HIGH | Chỉ 1 bên thu âm tại 1 thời điểm | Đang thu âm bên Tiếng Việt (nút Pause đang hiển thị) | 1. Click nút Mic bên Tiếng Nhật trong khi bên Tiếng Việt đang thu | 1. Bên Tiếng Nhật không bắt đầu thu đồng thời<br>2. Hoặc: bên Tiếng Việt dừng lại và bên Tiếng Nhật bắt đầu<br>(xác nhận behavior với dev) | High | — |
| WEB_HT_TC_008 | M_HWT3 Recording | HIGH | Đang nói → Bubble tạm "..." xuất hiện ngay lập tức | Mode "Thu âm" đang active, đang thu âm (Pause icon hiển thị) | 1. Nói vào micro | 1. Bubble tạm xuất hiện ngay tại vùng chat phía người nói<br>2. Bên trong Bubble hiển thị "..." nhấp nháy | Critical | Đọc: "おはようございます" |
| WEB_HT_TC_009 | M_HWT3 Recording | HIGH | Im lặng ~1–1.5s → Bubble chính thức tự động hiển thị | Đang có Bubble tạm "..." | 1. Nói xong, im lặng khoảng 1–1.5 giây | 1. Nút Mic trở về idle<br>2. Bubble tạm "..." thay bằng Bubble chính thức: text gốc + bản dịch | Critical | Đọc: "おはようございます" (Nhật → Việt) |
| WEB_HT_TC_010 | M_HWT3 Recording | HIGH | Click Pause để ngắt thu âm thủ công → Bubble chính thức | Đang thu âm (Pause icon đang hiển thị) | 1. Click nút Pause (⏸) để dừng | 1. Thu âm dừng ngay lập tức<br>2. Nút trở về idle (mic xám)<br>3. Bubble chính thức xuất hiện với kết quả dịch | High | — |

---

## PART 4 — M_HWT4: Mode "Nhập từ bàn phím" (Web-only)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_011 | M_HWT4 Keyboard | HIGH | Click tab "Nhập từ bàn phím" → UI chuyển sang text input | Đang ở mode "Thu âm" (default) | 1. Click tab "Nhập từ bàn phím" trong bottom panel | 1. Nút Mic bị ẩn đi<br>2. Mỗi bên hiển thị ô nhập text + nút Gửi (hoặc icon send)<br>3. Tab "Nhập từ bàn phím" được highlight active | High | — |
| WEB_HT_TC_012 | M_HWT4 Keyboard | HIGH | Nhập text bên Tiếng Việt → click Gửi → Bubble tạm → Bubble chính thức | Mode "Nhập từ bàn phím" đang active | 1. Nhập text vào ô bên Tiếng Việt<br>2. Click nút Gửi (hoặc nhấn Enter) | 1. Bubble tạm "..." xuất hiện tại vùng chat bên Tiếng Việt<br>2. Sau khi xử lý xong: Bubble chính thức hiển thị text gốc + bản dịch Tiếng Nhật | Critical | Nhập: "Xin chào, bạn khỏe không?" |
| WEB_HT_TC_013 | M_HWT4 Keyboard | HIGH | Nhấn Enter để gửi → hành vi giống nút Gửi | Mode "Nhập từ bàn phím", đã nhập text | 1. Nhập text vào ô bất kỳ<br>2. Nhấn phím Enter | 1. Bubble được tạo (hành vi giống click Gửi)<br>2. Ô text trống lại sau khi gửi | High | Nhập: "Hello" bên Tiếng Việt |
| WEB_HT_TC_014 | M_HWT4 Keyboard | HIGH | Gửi text rỗng → không tạo Bubble | Mode "Nhập từ bàn phím", ô text trống | 1. Để trống ô text<br>2. Click nút Gửi hoặc nhấn Enter | 1. Không tạo Bubble<br>2. Nút Gửi bị disabled hoặc không có phản hồi | High | Input: "" (rỗng) |
| WEB_HT_TC_015 | M_HWT4 Keyboard | HIGH | Chuyển từ "Nhập từ bàn phím" → "Thu âm" → UI trở về mic | Đang ở mode "Nhập từ bàn phím" | 1. Click tab "Thu âm" | 1. Ô text input bị ẩn<br>2. 2 nút Mic idle xuất hiện trở lại<br>3. Tab "Thu âm" được highlight active<br>4. Bubble Chat cũ (nếu có) vẫn giữ nguyên | Medium | — |

---

## PART 5 — M_HWT5: Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_016 | M_HWT5 Bubble | HIGH | Bubble xuất hiện đúng phía người nói | Đã có ít nhất 1 Bubble từ mỗi bên | 1. Bên Tiếng Việt (trái) gửi → quan sát vị trí Bubble<br>2. Bên Tiếng Nhật (phải) gửi → quan sát vị trí Bubble | 1. Bubble của Tiếng Việt xuất hiện bên trái (hoặc align theo bên tương ứng)<br>2. Bubble của Tiếng Nhật xuất hiện bên phải<br>3. Không bị lẫn vị trí | Critical | — |
| WEB_HT_TC_017 | M_HWT5 Bubble | HIGH | Cấu trúc Bubble chính thức đầy đủ các thành phần | Đang có Bubble chính thức trên màn hình | 1. Quan sát nội dung 1 Bubble Chat | 1. Bubble hiển thị: text gốc (ngôn ngữ người nói)<br>2. Bản dịch tương ứng<br>3. Icon Phát âm<br>4. Icon Copy<br>5. (Nếu là Tiếng Nhật) Icon mũi tên ">" hiện khi hover | High | — |
| WEB_HT_TC_018 | M_HWT5 AutoScroll | MEDIUM | Auto-scroll đẩy Bubble cũ lên khi có Bubble mới | Đã có hơn 4–5 Bubble trên màn hình | 1. Tạo thêm Bubble mới | 1. Màn hình tự động cuộn lên<br>2. Bubble mới nhất hiển thị đầy đủ tại vùng nhìn<br>3. Không cần user scroll thủ công | Medium | Gửi liên tục ≥5 lần |

---

## PART 6 — M_HWT6: Tương tác trên Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_019 | M_HWT6 Actions | MEDIUM | Click icon Phát âm trên Bubble | Đang có Bubble chính thức | 1. Click icon Phát âm trên Bubble | 1. Audio TTS phát nội dung bản dịch trong Bubble<br>2. Không bị lỗi hoặc im lặng | High | — |
| WEB_HT_TC_020 | M_HWT6 Actions | MEDIUM | Click icon Copy trên Bubble → toast xác nhận | Đang có Bubble chính thức | 1. Click icon Copy trên Bubble | 1. Nội dung Bubble được copy vào clipboard<br>2. Toast "Đã sao chép" hoặc tương đương hiển thị | High | — |
| WEB_HT_TC_021 | M_HWT6 Deep Lookup | MEDIUM | Click vào từ trong Bubble → mở popup tra từ sâu | Đang có Bubble chứa chữ Kanji | 1. Click vào một từ/Kanji cụ thể trong Bubble | 1. Popup chi tiết từ mở ra<br>2. Hiển thị: nghĩa, phiên âm, ví dụ | High | Bubble chứa: "東京" → click "東" |
| WEB_HT_TC_022 | M_HWT6 Japanese | MEDIUM | Hover vào Bubble Tiếng Nhật → icon ">" hiện ra | Đang có Bubble Tiếng Nhật | 1. Di chuột (hover) vào Bubble Tiếng Nhật | 1. Icon mũi tên ">" xuất hiện trên Bubble<br>2. Trước khi hover: icon này không hiển thị | High | Bubble ngôn ngữ: Tiếng Nhật |
| WEB_HT_TC_023 | M_HWT6 Japanese | MEDIUM | Click icon ">" trên Bubble Tiếng Nhật → chuyển sang màn Dịch | Đang hover vào Bubble Tiếng Nhật, icon ">" hiển thị | 1. Click icon mũi tên ">" | 1. Chuyển sang màn hình Dịch văn bản<br>2. Nội dung Bubble tự động điền vào ô nguồn | High | Bubble ngôn ngữ: Tiếng Nhật |
| WEB_HT_TC_024 | M_HWT6 Japanese | MEDIUM | Bubble không phải Tiếng Nhật KHÔNG hiện icon ">" dù hover | Đang có Bubble từ bên Tiếng Việt | 1. Hover vào Bubble Tiếng Việt | 1. Icon ">" không xuất hiện trên Bubble Tiếng Việt | Medium | Bubble ngôn ngữ: Tiếng Việt |

---

## PART 7 — M_HWT7: Đổi ngôn ngữ & Xóa lịch sử

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_025 | M_HWT7 Language | MEDIUM | Đổi ngôn ngữ qua dropdown — Bubble cũ giữ nguyên, Bubble mới theo cặp ngôn ngữ mới | Đang có Bubble trên màn hình | 1. Click dropdown ngôn ngữ một bên<br>2. Chọn ngôn ngữ khác từ danh sách<br>3. Gửi Bubble mới | 1. Dropdown đóng lại, hiển thị ngôn ngữ mới<br>2. Các Bubble cũ giữ nguyên nội dung và ngôn ngữ ban đầu<br>3. Bubble mới được dịch theo cặp ngôn ngữ đã đổi | Medium | Đổi từ Tiếng Nhật sang Tiếng Hàn |
| WEB_HT_TC_026 | M_HWT7 Language | MEDIUM | Click Swap icon — 2 ngôn ngữ hoán đổi vị trí | Đang ở màn hội thoại web | 1. Click icon Swap (⇄) ở giữa 2 dropdown | 1. Ngôn ngữ trái và phải được hoán đổi cho nhau<br>2. Bubble cũ (nếu có) vẫn giữ nguyên | Medium | Từ: Tiếng Việt ↔ Tiếng Nhật |
| WEB_HT_TC_027 | M_HWT7 Clear | MEDIUM | Hover + Click Trash icon → Modal xác nhận "không thể hoàn tác" | Đang có Bubble trên màn hình | 1. Hover vào icon Trash (🗑) trên header<br>2. Click icon Trash | 1. Modal xuất hiện với nội dung cảnh báo (tương đương: "Toàn bộ tin nhắn sẽ bị xóa. Bạn không thể hoàn tác.")<br>2. Có 2 button: Hủy và Xác nhận | Medium | — |
| WEB_HT_TC_028 | M_HWT7 Clear | MEDIUM | Xác nhận xóa lịch sử → về Empty State + Toast | Modal xác nhận đang hiển thị | 1. Click nút Xác nhận | 1. Toàn bộ Bubble Chat bị xóa<br>2. Màn hình trở về Empty State (mascot + "Trò chuyện dễ dàng với Mazii")<br>3. Toast xác nhận xóa thành công hiển thị | Medium | — |
| WEB_HT_TC_029 | M_HWT7 Clear | LOW | Hủy xóa lịch sử → giữ nguyên Bubble | Modal xác nhận đang hiển thị | 1. Click nút Hủy trong Modal | 1. Modal đóng lại<br>2. Toàn bộ Bubble Chat vẫn còn nguyên | Low | — |

---

## PART 8 — M_HWT8: Error Handling

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| WEB_HT_TC_030 | M_HWT8 Offline | HIGH | Click Mic khi mất mạng → không thu âm + thông báo lỗi | Tài khoản Premium, đã cắt kết nối internet, mode "Thu âm" | 1. Ngắt kết nối internet (bật chế độ Offline hoặc tắt WiFi)<br>2. Click nút Mic bất kỳ | 1. Hệ thống KHÔNG bắt đầu thu âm<br>2. Toast/Banner hiển thị thông báo lỗi mạng (ví dụ: "Vui lòng kết nối mạng để sử dụng Dịch hội thoại")<br>3. Nút Mic không chuyển sang trạng thái "Đang nghe" | Critical | Tài khoản: Premium, browser offline |
| WEB_HT_TC_031 | M_HWT8 Offline | HIGH | Đang thu âm → mất mạng giữa chừng → dừng + thông báo | Đang trong phiên thu âm có mạng | 1. Click Mic, bắt đầu nói bình thường<br>2. Ngắt kết nối internet trong lúc đang nói | 1. Thu âm dừng lại<br>2. Bubble tạm "..." biến mất hoặc chuyển sang trạng thái lỗi<br>3. Toast/Banner thông báo mất kết nối | Critical | Tài khoản: Premium |

---

## Traceability Matrix

| TC ID | UC/BR | Scenario Figma / Nguồn |
|-------|-------|----------------------|
| WEB_HT_TC_001 | UC3: Tab Hội thoại | Empty State web — mascot + hướng dẫn |
| WEB_HT_TC_002 | UC3 E1, BR02 | Paywall Guest |
| WEB_HT_TC_003 | UC3 E1, BR02 | Paywall Thường |
| WEB_HT_TC_004 | UC3 E2 | Xin quyền Microphone lần đầu (browser-level) |
| WEB_HT_TC_005 | UC3 E2 | Từ chối quyền Mic trên browser |
| WEB_HT_TC_006 | UC3 Main Flow | Trạng thái Đang thu âm |
| WEB_HT_TC_007 | UC3 Main Flow | Chỉ 1 bên thu âm tại 1 thời điểm |
| WEB_HT_TC_008 | UC3 Main Flow | Nhận diện giọng nói & Bubble tạm |
| WEB_HT_TC_009 | UC3 Main Flow | Kết thúc thu âm & Xử lý dịch (auto stop) |
| WEB_HT_TC_010 | UC3 AF1 | Dừng thu âm thủ công (click Pause) |
| WEB_HT_TC_011 | Figma web-only | Mode toggle Thu âm → Nhập từ bàn phím |
| WEB_HT_TC_012 | Figma web-only | Nhập text → Gửi → Bubble chính thức |
| WEB_HT_TC_013 | Figma web-only | Enter để gửi |
| WEB_HT_TC_014 | Figma web-only | Gửi text rỗng → không tạo Bubble |
| WEB_HT_TC_015 | Figma web-only | Mode toggle Nhập từ bàn phím → Thu âm |
| WEB_HT_TC_016 | UC3 Main Flow | Bubble đúng phía người nói |
| WEB_HT_TC_017 | UC3 Main Flow | Cấu trúc Bubble chính thức |
| WEB_HT_TC_018 | UC3 Main Flow | Auto-scroll |
| WEB_HT_TC_019 | Figma: Phát âm | Phát âm từ Bubble |
| WEB_HT_TC_020 | Figma: Sao chép | Copy từ Bubble |
| WEB_HT_TC_021 | Figma: Tra từ sâu | Deep Lookup từ Bubble |
| WEB_HT_TC_022 | Figma: Xem chi tiết Bubble Nhật | Hover → icon ">" hiện ra |
| WEB_HT_TC_023 | Figma: Xem chi tiết Bubble Nhật | Click ">" → chuyển màn Dịch |
| WEB_HT_TC_024 | Figma: Xem chi tiết Bubble Nhật | Bubble không phải Nhật không có ">" |
| WEB_HT_TC_025 | UC3 Main Flow | Đổi ngôn ngữ — giữ Bubble cũ |
| WEB_HT_TC_026 | Figma | Swap ngôn ngữ |
| WEB_HT_TC_027 | UC3 AF2, Figma | Xóa lịch sử — Hover + Click Trash → Modal |
| WEB_HT_TC_028 | UC3 AF2, Figma | Xác nhận xóa → Empty State + Toast |
| WEB_HT_TC_029 | Figma | Hủy xóa → giữ nguyên Bubble |
| WEB_HT_TC_030 | UC3 E1, BR03 | Offline — Click Mic |
| WEB_HT_TC_031 | BR03 | Offline — Mất mạng giữa chừng |

---

## So sánh Web vs Mobile (Key Differences)

| Điểm khác biệt | Mobile | Web |
|----------------|--------|-----|
| Mode nhập liệu | Thu âm duy nhất | "Thu âm" + "Nhập từ bàn phím" (web-only) |
| Mic Permission | OS-level dialog | Browser-level dialog |
| Empty State text | "Nhấn vào micro để bắt đầu giao tiếp" | "Trò chuyện dễ dàng với Mazii" |
| Icon ">" trên Bubble Nhật | Tap để xem | Hover để hiện, Click để navigate |
| Xóa lịch sử | Tap Trash | Hover + Click Trash |
| Swap ngôn ngữ | Không rõ trong Figma mobile | Swap icon (⇄) giữa 2 dropdown |
| Layout dropdown ngôn ngữ | Tiếng Nhật trái / Tiếng Việt phải | Tiếng Việt trái / Tiếng Nhật phải |
