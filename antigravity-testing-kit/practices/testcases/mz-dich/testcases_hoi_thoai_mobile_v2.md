# [Mazii] - Test Cases: Dịch Hội Thoại (Mobile)

**Phiên bản:** v2.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** PDF UC3 + Figma Design (node: 14263:471101)  
**Quy trình:** AI-RBT 6 bước (FULL RBT Mode) — chạy đầy đủ  
**Tổng số TC:** 28

---

## Mô tả tính năng

Dịch hội thoại 2 chiều theo thời gian thực — 2 người dùng cùng 1 thiết bị. Mỗi bên có nút Mic độc lập, kết quả dịch hiển thị dạng Bubble Chat theo từng nửa màn hình.

### Layout UI (từ Figma)

```
┌────────────────────────────────────────┐
│  ← Dịch    Dịch hội thoại         🗑  │  ← Header + Trash icon
│                                        │
│         [Empty State / Bubbles]        │  ← Vùng chat cuộn
│                                        │
│  [🎤 / ⏸]        [🎤 / ⏸]           │  ← 2 nút Mic độc lập
│  Tiếng Nhật ▼    Tiếng Việt ▼        │  ← Language dropdown mỗi bên
└────────────────────────────────────────┘
```

- Nút Mic **idle**: icon mic màu xám
- Nút Mic **đang thu âm**: icon Pause (⏸) viền xanh
- Bubble Chat xuất hiện **đúng nửa phía người nói** (Nhật: trái, Việt: phải)
- Bubble tạm: "..." nhấp nháy trong khi xử lý
- Bubble chính thức: text gốc + bản dịch + icons (Phát âm, Copy)
- Bubble Tiếng Nhật: có thêm icon ">" → tap để chuyển màn Dịch văn bản

---

## Assumptions

| # | Nội dung |
|---|---------|
| A1 | Bubble tạm xuất hiện ngay khi bắt đầu nhận diện giọng nói |
| A2 | Bubble chính thức xuất hiện sau im lặng ~1–1.5s hoặc tap Pause |
| A3 | Mic permission là OS-level dialog |
| A4 | Tính năng yêu cầu Premium hoặc Mazii-AI (BR02) |
| A5 | Offline → không thu âm, hiển thị toast lỗi (BR03) |
| A6 | Auto-scroll khi số Bubble vượt quá vùng nhìn |
| A7 | Icon ">" chỉ xuất hiện trên Bubble Tiếng Nhật |
| A8 | Tap Mic nhưng không nói → không tạo Bubble, về idle |
| A9 | Không lưu lịch sử liên session — mỗi lần vào là Empty State mới |
| A10 | Danh sách ngôn ngữ hội thoại = danh sách ngôn ngữ Dịch văn bản |
| A11 | STT tự động điều chỉnh theo ngôn ngữ mới sau khi đổi dropdown |

---

## Risk Assessment

| Module | Risk | Lý do |
|--------|------|-------|
| M_HT02 Access Control / Paywall | HIGH | Business rule BR02, blocking |
| M_HT03 Mic Permission (OS) | HIGH | Blocking nếu sai |
| M_HT04 Thu âm & STT | HIGH | Core flow, phụ thuộc hardware + network |
| M_HT05 Bubble Chat | HIGH | Core UX |
| M_HT10 Offline / Error Handling | HIGH | BR03, critical error path |
| M_HT06 Tương tác Bubble | MEDIUM | Phát âm, Copy, Deep Lookup, ">" |
| M_HT07 Language Selection | MEDIUM | State management, Q1 confirmed |
| M_HT08 Xóa lịch sử | MEDIUM | Destructive action |
| M_HT09 Auto-scroll | MEDIUM | UX |
| M_HT01 Empty State | LOW | Trạng thái đơn giản |

---

## Business Rules

| BR | Mô tả |
|----|------|
| BR02 | Dịch hội thoại yêu cầu Premium hoặc Mazii-AI. Guest/Thường → Paywall |
| BR03 | Mất mạng → không cho thu âm, hiển thị toast lỗi |

---

## PART 1 — M_HT01: Empty State

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_001 | M_HT01 Empty State | LOW | Empty State hiển thị đúng khi chưa có Bubble | Tài khoản Premium, vào tab "Dịch hội thoại" lần đầu (chưa có lịch sử) | 1. Mở tab "Dịch hội thoại" | 1. Mascot + text "Nhấn vào micro để bắt đầu giao tiếp"<br>2. 2 nút Mic idle (màu xám)<br>3. Dropdown ngôn ngữ: Tiếng Nhật (trái), Tiếng Việt (phải)<br>4. Trash icon ở góc trên phải header | Low | Tài khoản: Premium |

---

## PART 2 — M_HT02: Access Control / Paywall

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_002 | M_HT02 Paywall | HIGH | Guest truy cập tab Dịch hội thoại → Paywall ngay lập tức | Chưa đăng nhập (Guest) | 1. Mở tab "Dịch hội thoại" | 1. Màn hình Paywall hiển thị ngay lập tức<br>2. Không vào được màn hội thoại | Critical | Tài khoản: Guest (chưa đăng nhập) |
| MAZ_HT_TC_003 | M_HT02 Paywall | HIGH | Tài khoản Thường tap Mic → Paywall | Đăng nhập tài khoản Thường (Standard), đang ở màn Dịch hội thoại | 1. Tap nút Mic bất kỳ | 1. Màn hình Paywall xuất hiện yêu cầu nâng cấp Premium/Mazii-AI<br>2. Không bắt đầu thu âm | Critical | Tài khoản: Thường (Standard) |

---

## PART 3 — M_HT03: Mic Permission (OS-level)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_004 | M_HT03 Mic Permission | HIGH | Lần đầu tap Mic → hệ thống xin quyền Microphone (OS dialog) | Tài khoản Premium, app cài mới, chưa cấp quyền mic cho app | 1. Tap nút Mic bất kỳ | 1. Dialog OS xin quyền Microphone xuất hiện<br>2. Chưa thu âm ngay | High | Tài khoản: Premium, thiết bị chưa cấp quyền mic |
| MAZ_HT_TC_005 | M_HT03 Mic Permission | HIGH | Từ chối quyền Mic (OS) → hướng dẫn vào Cài đặt | Tài khoản Premium, đã deny quyền mic ở OS | 1. Tap nút Mic | 1. Thông báo hướng dẫn cấp lại quyền mic trong Cài đặt thiết bị<br>2. Không thu âm | High | Tài khoản: Premium, OS mic permission = Denied |

---

## PART 4 — M_HT04: Thu âm & STT

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_006 | M_HT04 Thu âm | HIGH | Tap Mic → nút chuyển sang Pause icon, hệ thống lắng nghe | Tài khoản Premium, đã cấp quyền mic, cặp ngôn ngữ Nhật–Việt | 1. Tap nút Mic bên Tiếng Việt (phải) | 1. Nút Mic bên phải chuyển thành Pause (⏸) viền xanh<br>2. Nút Mic bên trái vẫn idle (xám)<br>3. Hệ thống bắt đầu lắng nghe | Critical | Tài khoản: Premium, ngôn ngữ: Nhật–Việt |
| MAZ_HT_TC_007 | M_HT04 Thu âm | HIGH | Chỉ 1 bên thu âm tại 1 thời điểm | Đang thu âm bên Tiếng Việt (Pause icon hiển thị) | 1. Tap Mic bên Tiếng Nhật trong khi bên Tiếng Việt đang thu | 1. Bên Tiếng Nhật không bắt đầu thu đồng thời<br>2. Hoặc: bên Tiếng Việt dừng lại → bên Tiếng Nhật bắt đầu<br>(xác nhận behavior với dev) | High | — |
| MAZ_HT_TC_008 | M_HT04 Thu âm | HIGH | Đang nói → Bubble tạm "..." xuất hiện ngay lập tức | Đang thu âm (Pause icon hiển thị) | 1. Bắt đầu nói vào micro | 1. Bubble tạm xuất hiện ngay tại nửa màn hình phía người nói<br>2. Bên trong Bubble hiển thị "..." nhấp nháy | Critical | Đọc: "おはようございます" |
| MAZ_HT_TC_009 | M_HT04 Thu âm | HIGH | Im lặng ~1–1.5s → Bubble chính thức tự động | Đang có Bubble tạm "..." | 1. Nói xong, im lặng khoảng 1–1.5 giây | 1. Nút Mic về idle<br>2. Bubble tạm biến mất<br>3. Bubble chính thức: text gốc Tiếng Nhật + bản dịch Tiếng Việt | Critical | Đọc: "おはようございます" (Nhật→Việt), im lặng 1.5s |
| MAZ_HT_TC_010 | M_HT04 Thu âm | HIGH | Tap Pause thủ công → Bubble chính thức | Đang thu âm (Pause icon hiển thị) | 1. Tap nút Pause (⏸) để dừng thủ công | 1. Thu âm dừng ngay lập tức<br>2. Nút về idle (mic xám)<br>3. Bubble chính thức xuất hiện với kết quả dịch | High | — |
| MAZ_HT_TC_011 | M_HT04 Thu âm | HIGH | Tap Mic nhưng không nói (im lặng hoàn toàn) → không tạo Bubble | Đang thu âm (Pause icon hiển thị) | 1. Không nói gì<br>2. Tap Pause hoặc chờ silence detection | 1. Không tạo Bubble<br>2. Nút Mic về idle<br>3. Màn hình giữ nguyên trạng thái trước | High | Im lặng hoàn toàn ≥ 3 giây |
| MAZ_HT_TC_012 | M_HT04 Thu âm | HIGH | Đổi ngôn ngữ → STT lần thu âm tiếp theo dùng ngôn ngữ mới | Đang dùng cặp Nhật–Việt, có quyền mic | 1. Đổi dropdown bên trái từ "Tiếng Nhật" sang "Tiếng Anh"<br>2. Tap Mic bên vừa đổi<br>3. Nói một câu Tiếng Anh | 1. STT nhận diện đúng Tiếng Anh<br>2. Bubble chính thức: text Tiếng Anh + bản dịch Tiếng Việt | High | Đổi sang Tiếng Anh, đọc: "Good morning" |

---

## PART 5 — M_HT05: Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_013 | M_HT05 Bubble | HIGH | Bubble xuất hiện đúng nửa màn hình của người nói | Đã có Bubble từ cả 2 bên | 1. Bên Tiếng Việt nói → quan sát vị trí Bubble<br>2. Bên Tiếng Nhật nói → quan sát vị trí Bubble | 1. Bubble Tiếng Nhật xuất hiện bên trái màn hình<br>2. Bubble Tiếng Việt xuất hiện bên phải màn hình<br>3. Không bị lẫn vị trí | Critical | Nói 1 câu mỗi bên |
| MAZ_HT_TC_014 | M_HT05 Bubble | HIGH | Cấu trúc Bubble chính thức đủ thành phần | Đang có Bubble chính thức trên màn hình | 1. Quan sát nội dung bên trong 1 Bubble | 1. Text gốc (ngôn ngữ người nói)<br>2. Bản dịch tương ứng<br>3. Icon Phát âm<br>4. Icon Copy<br>5. (Bubble Tiếng Nhật) Icon ">" | High | — |
| MAZ_HT_TC_015 | M_HT05 Bubble | HIGH | Bubble tạm "..." hiển thị đúng trong khi xử lý | Đang thu âm và đang nói | 1. Quan sát Bubble ngay khi bắt đầu nhận diện | 1. Bubble tạm chứa "..." nhấp nháy<br>2. Chưa có text thực<br>3. Bubble đúng nửa màn hình người nói | High | Đọc: "Xin chào" |

---

## PART 6 — M_HT06: Tương tác trên Bubble

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_016 | M_HT06 Tương tác | MEDIUM | Tap icon Phát âm trên Bubble | Đang có Bubble chính thức | 1. Tap icon Phát âm trên Bubble | 1. Audio TTS phát nội dung bản dịch<br>2. Không bị lỗi hoặc im lặng | High | — |
| MAZ_HT_TC_017 | M_HT06 Tương tác | MEDIUM | Tap icon Copy trên Bubble → toast xác nhận | Đang có Bubble chính thức | 1. Tap icon Copy trên Bubble | 1. Nội dung Bubble copy vào clipboard<br>2. Toast "Đã sao chép" hiển thị | High | — |
| MAZ_HT_TC_018 | M_HT06 Deep Lookup | MEDIUM | Tap vào từ trong Bubble → popup tra từ sâu | Đang có Bubble chứa chữ Kanji | 1. Tap vào từ "東" trong Bubble Tiếng Nhật | 1. Popup chi tiết từ mở ra<br>2. Hiển thị: nghĩa, phiên âm (hiragana/romaji), ví dụ | High | Bubble chứa: "東京" → tap "東" |
| MAZ_HT_TC_019 | M_HT06 Japanese ">" | MEDIUM | Tap icon ">" trên Bubble Tiếng Nhật → chuyển màn Dịch văn bản | Đang có Bubble Tiếng Nhật (icon ">" hiển thị) | 1. Tap icon ">" trên Bubble Tiếng Nhật | 1. Chuyển sang màn hình Dịch văn bản<br>2. Nội dung Bubble tự động điền vào ô nguồn | High | Bubble: "おはようございます" |
| MAZ_HT_TC_020 | M_HT06 Japanese ">" | MEDIUM | Bubble Tiếng Việt/Anh/Hàn KHÔNG có icon ">" | Đang có Bubble từ bên không phải Tiếng Nhật | 1. Quan sát Bubble Tiếng Việt | 1. Không có icon ">" trên Bubble không phải Tiếng Nhật | Medium | Bubble ngôn ngữ: Tiếng Việt |

---

## PART 7 — M_HT07: Language Selection

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_021 | M_HT07 Language | MEDIUM | Đổi ngôn ngữ → Bubble cũ giữ nguyên, Bubble mới theo ngôn ngữ mới | Đang có Bubble trên màn hình (cặp Nhật–Việt) | 1. Tap dropdown ngôn ngữ một bên<br>2. Chọn "Tiếng Hàn"<br>3. Thu âm tiếp | 1. Bubble cũ giữ nguyên nội dung và ngôn ngữ ban đầu<br>2. Bubble mới dịch theo cặp ngôn ngữ đã đổi (Hàn–Việt) | Medium | Đổi từ Tiếng Nhật → Tiếng Hàn, đọc: "안녕하세요" |
| MAZ_HT_TC_022 | M_HT07 Language | MEDIUM | Test cặp ngôn ngữ thứ 3: Anh–Việt | Tài khoản Premium, cặp hiện tại: Nhật–Việt | 1. Đổi 1 bên sang "Tiếng Anh"<br>2. Thu âm: "Good morning, how are you?"<br>3. Quan sát Bubble chính thức | 1. STT nhận diện đúng Tiếng Anh<br>2. Bubble: text Anh + bản dịch Tiếng Việt chính xác | Medium | Cặp: Anh–Việt, đọc: "Good morning, how are you?" |

---

## PART 8 — M_HT08: Xóa lịch sử

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_023 | M_HT08 Xóa | MEDIUM | Tap Trash icon → Modal xác nhận "không thể hoàn tác" hiển thị | Đang có Bubble trên màn hình | 1. Tap icon Trash (🗑) góc trên phải | 1. Modal xuất hiện với cảnh báo: toàn bộ tin nhắn sẽ bị xóa, không thể hoàn tác<br>2. Có 2 button: "Hủy" và "Xác nhận" | Medium | — |
| MAZ_HT_TC_024 | M_HT08 Xóa | MEDIUM | Xác nhận xóa → về Empty State + Toast | Modal xác nhận đang hiển thị | 1. Tap nút "Xác nhận" | 1. Toàn bộ Bubble bị xóa<br>2. Màn hình về Empty State: "Nhấn vào micro để bắt đầu giao tiếp"<br>3. Toast xác nhận xóa thành công | Medium | — |
| MAZ_HT_TC_025 | M_HT08 Xóa | LOW | Hủy xóa → Bubble giữ nguyên | Modal xác nhận đang hiển thị | 1. Tap nút "Hủy" | 1. Modal đóng lại<br>2. Toàn bộ Bubble Chat vẫn còn nguyên | Low | — |

---

## PART 9 — M_HT09: Auto-scroll

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_026 | M_HT09 Auto-scroll | MEDIUM | Bubble mới → màn tự cuộn, Bubble mới nhất luôn hiển thị đầy đủ | Đã có hơn 4–5 Bubble lấp đầy màn hình | 1. Tạo thêm Bubble mới bằng cách nói tiếp | 1. Màn hình tự cuộn lên<br>2. Bubble mới nhất hiển thị đầy đủ trong vùng nhìn<br>3. Không cần cuộn thủ công | Medium | Thu âm liên tục ≥ 6 lần |

---

## PART 10 — M_HT10: Offline / Error Handling

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_027 | M_HT10 Offline | HIGH | Offline trước khi tap Mic → toast lỗi, không thu âm | Tài khoản Premium, đã tắt mạng | 1. Tắt WiFi/4G<br>2. Vào màn Dịch hội thoại<br>3. Tap nút Mic | 1. Không bắt đầu thu âm<br>2. Toast/Banner: "Vui lòng kết nối mạng để sử dụng Dịch hội thoại"<br>3. Nút Mic không chuyển sang Pause | Critical | Tài khoản: Premium, WiFi/4G: Tắt |
| MAZ_HT_TC_028 | M_HT10 Offline | HIGH | Mất mạng giữa chừng khi đang thu âm → dừng + thông báo | Đang trong phiên thu âm có mạng | 1. Tap Mic, bắt đầu nói<br>2. Tắt mạng trong lúc đang nói | 1. Thu âm dừng lại<br>2. Bubble tạm biến mất hoặc chuyển trạng thái lỗi<br>3. Toast thông báo mất kết nối | Critical | Tài khoản: Premium |

---

## Traceability Matrix

| TC ID | Module | UC/BR | Scenario / Nguồn |
|-------|--------|-------|----------------|
| MAZ_HT_TC_001 | M_HT01 | UC3 Main Flow | Empty State mobile |
| MAZ_HT_TC_002 | M_HT02 | UC3 E1, BR02 | Paywall — Guest |
| MAZ_HT_TC_003 | M_HT02 | UC3 E1, BR02 | Paywall — Thường |
| MAZ_HT_TC_004 | M_HT03 | UC3 E2 | Xin quyền mic lần đầu (OS) |
| MAZ_HT_TC_005 | M_HT03 | UC3 E2 | Từ chối quyền mic (OS) |
| MAZ_HT_TC_006 | M_HT04 | UC3 Main Flow | Tap Mic → Pause icon |
| MAZ_HT_TC_007 | M_HT04 | UC3 Main Flow | 1 bên thu âm tại 1 thời điểm |
| MAZ_HT_TC_008 | M_HT04 | UC3 Main Flow | Bubble tạm "..." |
| MAZ_HT_TC_009 | M_HT04 | UC3 Main Flow | Im lặng → Bubble chính thức (auto) |
| MAZ_HT_TC_010 | M_HT04 | UC3 AF1 | Pause thủ công → Bubble chính thức |
| MAZ_HT_TC_011 | M_HT04 | Gap G2 | Không nói → không tạo Bubble |
| MAZ_HT_TC_012 | M_HT04, M_HT07 | UC3 Main Flow, Q1 | STT điều chỉnh theo ngôn ngữ mới |
| MAZ_HT_TC_013 | M_HT05 | UC3 Main Flow | Bubble đúng nửa màn hình |
| MAZ_HT_TC_014 | M_HT05 | UC3 Main Flow | Cấu trúc Bubble chính thức |
| MAZ_HT_TC_015 | M_HT05 | UC3 Main Flow | Bubble tạm "..." |
| MAZ_HT_TC_016 | M_HT06 | Figma: Phát âm | Phát âm từ Bubble |
| MAZ_HT_TC_017 | M_HT06 | Figma: Copy | Copy từ Bubble |
| MAZ_HT_TC_018 | M_HT06 | Figma: Deep Lookup | Deep Lookup từ Bubble |
| MAZ_HT_TC_019 | M_HT06 | Figma: ">" Nhật | Tap ">" → chuyển màn Dịch |
| MAZ_HT_TC_020 | M_HT06 | Figma: ">" Nhật | Không phải Nhật → không có ">" |
| MAZ_HT_TC_021 | M_HT07 | UC3 Main Flow | Đổi ngôn ngữ — giữ Bubble cũ |
| MAZ_HT_TC_022 | M_HT07 | Q1 confirmed, Gap G1 | Test cặp Anh–Việt |
| MAZ_HT_TC_023 | M_HT08 | UC3 AF2, Figma | Trash → Modal xác nhận |
| MAZ_HT_TC_024 | M_HT08 | UC3 AF2 | Xác nhận → Empty State |
| MAZ_HT_TC_025 | M_HT08 | Figma | Hủy → giữ nguyên |
| MAZ_HT_TC_026 | M_HT09 | UC3 Main Flow | Auto-scroll |
| MAZ_HT_TC_027 | M_HT10 | UC3 E3, BR03 | Offline — trước khi tap Mic |
| MAZ_HT_TC_028 | M_HT10 | BR03 | Offline — mất mạng giữa chừng |
