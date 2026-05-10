# [Mazii] - Test Cases: Dịch Hội Thoại (Mobile)

**Phiên bản:** v1.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** PDF UC3 + Figma Design (node: 14263:471101)  
**Quy trình:** AI-RBT 6 bước  
**Tổng số TC:** 21

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
│  [🎤]            [🎤 / ⏸]            │  ← 2 nút Mic độc lập
│  Tiếng Nhật ▼    Tiếng Việt ▼        │  ← Language selector mỗi bên
└────────────────────────────────────────┘
```

- Nút Mic **idle**: icon mic màu xám
- Nút Mic **đang thu âm**: icon Pause (⏸) viền xanh
- Bubble Chat xuất hiện **đúng nửa phía người nói**
- Bubble tạm: hiển thị "..." nhấp nháy trong khi xử lý
- Bubble chính thức: hiển thị text gốc + bản dịch

---

## Risk Assessment

| Module | Risk | Lý do |
|--------|------|-------|
| M_HT3 Thu âm & Voice Recognition | HIGH | Core flow, phụ thuộc hardware |
| M_HT4 Bubble Chat | HIGH | Core UX, dễ bug về layout |
| M_HT9 Paywall / Access Control | HIGH | Business rule BR02 |
| M_HT8 Offline | HIGH | Error handling quan trọng |
| M_HT2 Mic Permission | HIGH | Blocking nếu sai |
| M_HT5 Actions trên Bubble | MEDIUM | Phát âm, Copy, Deep Lookup |
| M_HT7 Language / Clear History | MEDIUM | State management |
| M_HT6 Auto-scroll | MEDIUM | UX |
| M_HT1 Empty State | LOW | Trạng thái đơn giản |

---

## Business Rules áp dụng

| BR | Mô tả |
|----|------|
| BR02 | Dịch hội thoại yêu cầu Premium hoặc Mazii-AI. Guest/Thường → Paywall |
| BR03 | Mất mạng → không cho thu âm, hiển thị toast lỗi |

---

## PART 1 — M_HT1: Empty State + M_HT9: Paywall / Access

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_001 | M_HT1 Empty | LOW | Empty State hiển thị khi chưa có Bubble Chat | Đăng nhập tài khoản Premium, vào tab "Dịch hội thoại" lần đầu | 1. Mở tab Dịch hội thoại | 1. Màn hình hiển thị mascot + text "Nhấn vào micro để bắt đầu giao tiếp"<br>2. 2 nút Mic idle ở phía dưới<br>3. Language selector: Tiếng Nhật (trái) / Tiếng Việt (phải)<br>4. Trash icon hiển thị ở góc trên phải header | Low | Tài khoản: Premium |
| MAZ_HT_TC_002 | M_HT9 Paywall | HIGH | Tài khoản Guest truy cập Dịch hội thoại → Paywall | Chưa đăng nhập hoặc tài khoản Guest | 1. Tap tab "Dịch hội thoại" trên màn Dịch | 1. Màn hình Paywall hiển thị ngay lập tức<br>2. Không vào được màn hội thoại | Critical | Tài khoản: Guest |
| MAZ_HT_TC_003 | M_HT9 Paywall | HIGH | Tài khoản Thường tap nút Mic → Paywall | Đang ở màn Dịch hội thoại, tài khoản Thường | 1. Tap nút Mic bất kỳ | 1. Màn hình Paywall hiển thị yêu cầu nâng cấp Premium/Mazii-AI | Critical | Tài khoản: Thường (Standard) |

---

## PART 2 — M_HT2: Mic Permission

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_004 | M_HT2 Permission | HIGH | Lần đầu tap Mic — hệ thống xin quyền Microphone | App cài mới, chưa từng cấp quyền mic, tài khoản Premium | 1. Tap nút Mic bất kỳ | 1. Hệ thống hiển thị Soft Prompt xin quyền Microphone<br>2. Chưa thu âm ngay | High | Tài khoản: Premium, thiết bị chưa cấp quyền mic cho app |
| MAZ_HT_TC_005 | M_HT2 Permission | HIGH | Từ chối quyền Mic → thông báo hướng dẫn cài đặt | Đã từ chối quyền mic trước đó | 1. Tap nút Mic | 1. Hệ thống hiển thị thông báo yêu cầu cấp quyền mic trong Cài đặt thiết bị<br>2. Không thu âm | High | Tài khoản: Premium, đã deny mic permission |

---

## PART 3 — M_HT3: Thu âm & Voice Recognition

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_006 | M_HT3 Recording | HIGH | Tap Mic → nút chuyển sang trạng thái "Đang nghe" | Tài khoản Premium, đã cấp quyền mic, màn Dịch hội thoại | 1. Tap nút Mic bên Tiếng Việt (phải) | 1. Nút Mic bên phải chuyển thành icon Pause (⏸) viền xanh<br>2. Nút Mic bên trái vẫn idle (xám)<br>3. Hệ thống bắt đầu lắng nghe | Critical | Tài khoản: Premium |
| MAZ_HT_TC_007 | M_HT3 Recording | HIGH | Chỉ 1 bên thu âm tại 1 thời điểm — bên còn lại không hoạt động | Đang thu âm bên Tiếng Việt | 1. Tap nút Mic bên Tiếng Nhật trong khi bên Tiếng Việt đang thu | 1. Bên Tiếng Nhật không bắt đầu thu âm<br>2. Hoặc: bên Tiếng Việt dừng lại và bên Tiếng Nhật bắt đầu<br>(xác nhận behavior với dev) | High | — |
| MAZ_HT_TC_008 | M_HT3 Recording | HIGH | Đang nói → Bubble tạm xuất hiện với "..." nhấp nháy | Đang thu âm (nút Pause đang hiển thị) | 1. Bắt đầu nói vào mic | 1. Bubble Chat tạm thời xuất hiện ngay lập tức tại nửa màn hình của người nói<br>2. Bên trong Bubble hiển thị "..." (ba chấm nhấp nháy) | Critical | Đọc: "おはようございます" |
| MAZ_HT_TC_009 | M_HT3 Recording | HIGH | Dừng nói tự động (im lặng 1–1.5s) → Bubble chính thức | Đang có Bubble tạm "..." | 1. Nói xong, im lặng khoảng 1–1.5 giây | 1. Nút Mic trở về trạng thái idle (mic xám)<br>2. Bubble tạm "..." biến mất<br>3. Bubble chính thức xuất hiện chứa: text gốc + bản dịch | Critical | Đọc: "おはようございます" (Nhật → Việt) |
| MAZ_HT_TC_010 | M_HT3 Recording | HIGH | Tap Pause để ngắt thu âm thủ công → Bubble chính thức | Đang thu âm (nút Pause đang hiển thị) | 1. Tap nút Pause (⏸) để dừng | 1. Thu âm dừng ngay lập tức<br>2. Nút trở về idle<br>3. Bubble chính thức xuất hiện với kết quả dịch | High | — |

---

## PART 4 — M_HT4: Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_011 | M_HT4 Bubble | HIGH | Bubble xuất hiện đúng nửa màn hình của người nói | Đã có ít nhất 1 Bubble từ mỗi bên | 1. Bên Tiếng Việt nói → quan sát vị trí Bubble<br>2. Bên Tiếng Nhật nói → quan sát vị trí Bubble | 1. Bubble của bên Tiếng Việt xuất hiện bên phải màn hình<br>2. Bubble của bên Tiếng Nhật xuất hiện bên trái màn hình<br>3. Không bị lẫn sang nửa kia | Critical | — |
| MAZ_HT_TC_012 | M_HT4 Bubble | HIGH | Cấu trúc Bubble chính thức đầy đủ các thành phần | Đang có Bubble chính thức trên màn hình | 1. Quan sát nội dung bên trong 1 Bubble Chat | 1. Bubble hiển thị: text gốc (ngôn ngữ người nói)<br>2. Bản dịch tương ứng<br>3. Icon Phát âm<br>4. Icon Copy<br>5. (Nếu là Tiếng Nhật) Icon mũi tên ">" | High | — |
| MAZ_HT_TC_013 | M_HT6 AutoScroll | MEDIUM | Auto-scroll đẩy Bubble cũ lên khi có Bubble mới | Đã có hơn 4–5 Bubble trên màn hình | 1. Tạo thêm Bubble mới bằng cách nói tiếp | 1. Màn hình tự động cuộn lên<br>2. Bubble mới nhất luôn hiển thị đầy đủ tại vùng nhìn<br>3. Không cần user scroll thủ công | Medium | Thu âm liên tục ≥5 lần |

---

## PART 5 — M_HT5: Tương tác trên Bubble Chat

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_014 | M_HT5 Actions | MEDIUM | Phát âm từ Bubble Chat | Đang có Bubble chính thức | 1. Tap icon Phát âm trên Bubble | 1. Audio TTS phát ra nội dung bản dịch trong Bubble<br>2. Không bị lỗi hoặc im lặng | High | — |
| MAZ_HT_TC_015 | M_HT5 Actions | MEDIUM | Copy văn bản từ Bubble Chat → toast xác nhận | Đang có Bubble chính thức | 1. Tap icon Copy trên Bubble | 1. Nội dung Bubble được copy vào clipboard<br>2. Toast "Đã sao chép" hiển thị | High | — |
| MAZ_HT_TC_016 | M_HT5 Deep Lookup | MEDIUM | Tap vào một từ trong Bubble → mở popup tra từ sâu | Đang có Bubble chính thức có chứa chữ Kanji | 1. Tap vào một từ/Kanji cụ thể trong Bubble | 1. Popup chi tiết từ mở ra<br>2. Hiển thị: nghĩa, phiên âm, ví dụ | High | Bubble chứa: "東京" → tap "東" |
| MAZ_HT_TC_017 | M_HT5 Japanese | MEDIUM | Tap icon ">" trên Bubble Tiếng Nhật → chuyển sang màn Dịch | Đang có Bubble Tiếng Nhật (có icon ">") | 1. Tap icon mũi tên ">" trên Bubble Tiếng Nhật | 1. Chuyển sang màn hình Dịch văn bản<br>2. Nội dung Bubble tự động điền vào ô nguồn | High | Bubble ngôn ngữ: Tiếng Nhật |
| MAZ_HT_TC_018 | M_HT5 Japanese | MEDIUM | Bubble Tiếng Việt/Anh KHÔNG hiển thị icon ">" | Đang có Bubble từ bên Tiếng Việt | 1. Quan sát Bubble Chat của bên Tiếng Việt | 1. Không có icon ">" trên Bubble không phải Tiếng Nhật | Medium | Bubble ngôn ngữ: Tiếng Việt |

---

## PART 6 — M_HT7: Đổi ngôn ngữ & Xóa lịch sử

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_019 | M_HT7 Language | MEDIUM | Đổi ngôn ngữ — Bubble cũ giữ nguyên, Bubble mới theo ngôn ngữ mới | Đang có Bubble trên màn hình | 1. Tap dropdown ngôn ngữ một bên<br>2. Chọn ngôn ngữ khác<br>3. Thu âm tiếp | 1. Các Bubble cũ giữ nguyên nội dung và ngôn ngữ ban đầu<br>2. Bubble mới được dịch theo cặp ngôn ngữ mới | Medium | Đổi từ Tiếng Nhật sang Tiếng Hàn |
| MAZ_HT_TC_020 | M_HT7 Clear | MEDIUM | Tap Trash icon → Modal xác nhận "không thể hoàn tác" | Đang có Bubble trên màn hình | 1. Tap icon Trash (🗑) góc trên phải | 1. Modal xuất hiện với nội dung: "Toàn bộ tin nhắn trên màn hình này sẽ bị xóa. Bạn không thể hoàn tác."<br>2. Có 2 button: Hủy và Xác nhận | Medium | — |
| MAZ_HT_TC_021 | M_HT7 Clear | MEDIUM | Xác nhận xóa lịch sử → màn hình về Empty State | Modal xác nhận đang hiển thị | 1. Tap nút Xác nhận trong Modal | 1. Toàn bộ Bubble Chat bị xóa<br>2. Màn hình trở về Empty State (mascot + "Nhấn vào micro để bắt đầu giao tiếp")<br>3. Toast xác nhận đã xóa hiển thị (nếu có) | Medium | — |
| MAZ_HT_TC_022 | M_HT7 Clear | LOW | Hủy xóa lịch sử → giữ nguyên Bubble | Modal xác nhận đang hiển thị | 1. Tap nút Hủy trong Modal | 1. Modal đóng lại<br>2. Toàn bộ Bubble Chat vẫn còn nguyên | Low | — |

---

## PART 7 — M_HT8: Error Handling

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_HT_TC_023 | M_HT8 Offline | HIGH | Tap Mic khi mất mạng → không thu âm + toast lỗi | Tài khoản Premium, mất kết nối mạng | 1. Tắt mạng thiết bị<br>2. Vào màn Dịch hội thoại<br>3. Tap nút Mic | 1. Hệ thống KHÔNG bắt đầu thu âm<br>2. Toast/Banner hiển thị: "Vui lòng kết nối mạng để sử dụng Dịch hội thoại"<br>3. Nút Mic không chuyển sang trạng thái "Đang nghe" | Critical | Tài khoản: Premium, WiFi/4G tắt |
| MAZ_HT_TC_024 | M_HT8 Offline | HIGH | Đang thu âm giữa chừng → mất mạng | Đang trong phiên thu âm có mạng | 1. Bắt đầu thu âm bình thường<br>2. Tắt mạng trong lúc đang nói | 1. Hệ thống dừng thu âm<br>2. Bubble tạm "..." biến mất hoặc hiển thị trạng thái lỗi<br>3. Toast thông báo mất kết nối | Critical | Tài khoản: Premium |

---

## Traceability Matrix

| TC ID | UC/BR | Scenario Figma |
|-------|-------|---------------|
| HT_TC_001 | UC3: Tab Hội thoại | Trạng thái trống |
| HT_TC_002 | UC3 E1, BR02 | Paywall Guest |
| HT_TC_003 | UC3 E1, BR02 | Paywall Thường |
| HT_TC_004 | UC3 E2 | Xin quyền Microphone lần đầu |
| HT_TC_005 | UC3 E2 | Từ chối quyền Mic |
| HT_TC_006 | UC3 Main Flow | Trạng thái Đang thu âm |
| HT_TC_007 | UC3 Main Flow | Chỉ 1 bên thu âm |
| HT_TC_008 | UC3 Main Flow | Nhận diện giọng nói & Bubble Chat tạm |
| HT_TC_009 | UC3 Main Flow | Kết thúc thu âm & Xử lý dịch (auto stop) |
| HT_TC_010 | UC3 AF1 | Kết thúc thu âm thủ công (Pause) |
| HT_TC_011 | UC3 Main Flow | Bubble đúng nửa màn hình |
| HT_TC_012 | UC3 Main Flow | Trạng thái hiển thị Bubble Chat |
| HT_TC_013 | UC3 Main Flow | Tự động cuộn (Auto-scroll) |
| HT_TC_014 | Figma: Phát âm & Sao chép | Phát âm từ Bubble |
| HT_TC_015 | Figma: Phát âm & Sao chép | Copy từ Bubble |
| HT_TC_016 | Figma: Tra từ sâu | Deep Lookup từ Bubble |
| HT_TC_017 | Figma: Xem chi tiết Bubble Nhật | Icon ">" → màn Dịch |
| HT_TC_018 | Figma: Xem chi tiết Bubble Nhật | Bubble không phải Nhật không có ">" |
| HT_TC_019 | UC3 Main Flow, Figma | Đổi ngôn ngữ — giữ Bubble cũ |
| HT_TC_020 | UC3 AF2, Figma | Xóa lịch sử — Modal confirmation |
| HT_TC_021 | UC3 AF2, Figma | Xác nhận xóa → Empty State |
| HT_TC_022 | Figma | Hủy xóa → giữ nguyên |
| HT_TC_023 | UC3 E1, BR03 | Mất kết nối mạng (Offline) |
| HT_TC_024 | BR03 | Mất mạng giữa chừng |
