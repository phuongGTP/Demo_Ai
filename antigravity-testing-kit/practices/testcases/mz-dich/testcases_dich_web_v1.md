# [Mazii] - Test Cases: Page Dịch (Web)

**Phiên bản:** v1.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** Figma Web Design (node: 13616:67414) + Logic từ mobile v1  
**Quy trình:** AI-RBT — kế thừa phân tích mobile, điều chỉnh UI web  
**Viewport chuẩn:** Laptop 1440px  
**Tổng số TC:** 44

---

## Điểm khác biệt Web vs Mobile

| Feature | Mobile | Web |
|---------|--------|-----|
| Input area | Bottom Sheet trượt lên khi focus | Textarea thông thường trên page |
| Language selection | Màn hình chọn ngôn ngữ riêng | Dropdown Menu inline |
| Voice Input | Bottom Sheet che Bottom Navigation | Modal overlay centered (750px wide) |
| Vẽ tay / Bộ thủ | Tab trong thanh 5 công cụ | Modal overlay riêng biệt (750px wide) |
| Model selection | Bottom Sheet | Dropdown "Mazii Translator ▼" ở cột kết quả → mở Drawer/Panel (514px) |
| Swap | Tap | Hover → Click |
| Navigation | Bottom Navigation Bar | Top Navigation |
| Empty state | Không có màn riêng | Có Empty state screen riêng |
| **Lịch sử dịch** | Màn hình riêng (popup) | **Hiển thị inline bên dưới 2 textarea trong cùng trang** |
| **Giới hạn ký tự** | Không giới hạn | Không giới hạn (counter "0/5000" chỉ là hiển thị tham khảo) |

## Logic dùng chung với Mobile (không đổi)

- Dịch thủ công: chỉ kích hoạt khi bấm nút "Dịch"
- Skeleton loading bám cấu trúc text
- Furigana chỉ hiện SAU khi bấm Dịch, nguồn Tiếng Nhật
- Furigana KHÔNG hiện khi đang nhập (cursor đang nhấp nháy)
- Kiểm tra ngữ pháp
- Ngữ pháp liên quan: chỉ hiển thị khi nguồn là Tiếng Nhật
- Button Tương phản: hiển thị bản dịch (1 chiều, không toggle)
- Copy → clipboard + toast "Đã sao chép"
- Phát âm → phát audio TTS
- Deep Lookup: tap/click vào Kanji → màn tra cứu chi tiết
- Phân tích từ vựng ✨: Drawer 3 cột
- Paywall: Free/Guest/Thường → Paywall; Premium → apply ngay
- Mất mạng → toast thông báo
- API timeout 30s → toast lỗi
- Bộ thủ/Viết tay disabled nếu nguồn không phải Tiếng Nhật

---

## Risk Summary

| Module | Số TC | Risk Level |
|--------|-------|-----------|
| M1 Translation Input (Web) | 9 | HIGH / MEDIUM |
| M2 Language Selection (Web) | 5 | MEDIUM |
| M3 Translation Execution + Furigana + Deep Lookup | 11 | CRITICAL / HIGH |
| M4 Model Selection + Paywall (Web Drawer) | 4 | CRITICAL / HIGH |
| M5 Advanced Features | 5 | MEDIUM |
| M6 Empty State | 2 | LOW |
| M7 Translation History (Web — inline) | 6 | MEDIUM / LOW |
| **Tổng** | **46** | — |

---

## Assumptions áp dụng

| ID | Nội dung |
|----|---------|
| Q1-WEB | Ô Input Text Area không giới hạn ký tự. Counter "0/5000" trên toolbar chỉ là hiển thị tham khảo |
| Q2 | Nút Dịch disabled khi ô input rỗng |
| Q3 | Timeout API dịch: 30 giây; hiển thị toast lỗi |
| Q5 | Kết quả dịch tự động lưu vào Lịch sử sau mỗi lần thành công |
| Q6 | Chỉ Mazii-AI dùng được model AI; Premium không bao gồm |

---

## PART 1 — M1: Translation Input (Web)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_001 | M1.1 Text Input | HIGH | Click vào textarea — focus và sẵn sàng nhập | Đã đăng nhập, đang ở trang Dịch | 1. Click vào textarea nhập liệu nguồn | 1. Textarea được focus (hiện border highlight)<br>2. Cursor xuất hiện trong textarea<br>3. Không có Bottom Sheet — nhập trực tiếp trên page | Critical | — |
| MAZ_DICH_WEB_TC_002 | M1.1 Text Input | HIGH | Icon Clear xuất hiện khi có text trong textarea | Đang ở trang Dịch, textarea rỗng | 1. Click vào textarea<br>2. Nhập văn bản | 1. Icon "Xóa (Clear)" xuất hiện ở góc phải textarea | High | Nhập: "xin chào" |
| MAZ_DICH_WEB_TC_003 | M1.1 Text Input | HIGH | Clear xóa toàn bộ nội dung textarea | Đã nhập văn bản vào textarea | 1. Click icon Clear | 1. Toàn bộ văn bản bị xóa<br>2. Textarea về trạng thái rỗng<br>3. Icon Clear biến mất | High | Nhập: "今日はいい天気ですね" |
| MAZ_DICH_WEB_TC_004 | M1.1 Text Input | HIGH | Textarea co giãn tự động theo nội dung | Đang ở trang Dịch | 1. Nhập 1 dòng<br>2. Tiếp tục nhập thêm đến 3–5 dòng | 1. Textarea tự mở rộng chiều cao theo nội dung<br>2. Không bị scroll nội bộ sớm | High | Nhập 4 dòng: "Xin chào\nTôi tên là Minh\nTôi đến từ Hà Nội\nRất vui được gặp bạn" |
| MAZ_DICH_WEB_TC_005 | M1.1 Text Input | MEDIUM | Counter ký tự cập nhật realtime khi nhập | Đang ở trang Dịch | 1. Click textarea<br>2. Nhập dần văn bản | 1. Counter "X/5000" cập nhật realtime theo số ký tự đang nhập<br>2. Không bị chặn nhập dù vượt 5000 | Medium | Nhập: "あ" × 100 → counter hiện "100/5000" |
| MAZ_DICH_WEB_TC_007 | M1.1 Text Input | HIGH | Nút Dịch disabled khi textarea rỗng | Đang ở trang Dịch, textarea rỗng | 1. Quan sát nút Dịch khi chưa nhập gì | 1. Nút Dịch ở trạng thái disabled — không thể click | High | — |
| MAZ_DICH_WEB_TC_008 | M1.3 Voice | MEDIUM | Mở modal Voice — overlay centered (lần đầu, chưa cấp quyền mic) | Đăng nhập lần đầu, chưa cấp quyền mic | 1. Click icon "Voice/Ghi âm" trên toolbar | 1. Modal overlay xuất hiện ở trung tâm màn hình (750px wide)<br>2. Background bị mờ (overlay)<br>3. Soft Prompt xin cấp quyền Microphone hiển thị trong modal | High | — |
| MAZ_DICH_WEB_TC_009 | M1.3 Voice | MEDIUM | Ghi âm qua modal — text tự điền vào textarea nguồn | Modal Voice đang mở, đã cấp quyền mic | 1. Click nút ghi âm trong modal<br>2. Đọc câu tiếng Nhật<br>3. Dừng ghi | 1. Hệ thống nhận diện giọng nói<br>2. Modal đóng (hoặc text điền vào ô nguồn)<br>3. Text xuất hiện trong textarea nguồn | High | Đọc: "おはようございます" |
| MAZ_DICH_WEB_TC_010 | M1.4 Handwriting | MEDIUM | Mở modal Vẽ tay — overlay centered | Đang ở trang Dịch, nguồn là Tiếng Nhật | 1. Click icon "Vẽ tay" trên toolbar | 1. Modal overlay Vẽ tay xuất hiện (750px wide)<br>2. Canvas vẽ tay sẵn sàng nhận input chuột/touch | Medium | Nguồn: Tiếng Nhật |
| MAZ_DICH_WEB_TC_011 | M1.4 Bộ thủ | MEDIUM | Mở modal Bộ thủ — overlay centered | Đang ở trang Dịch, nguồn là Tiếng Nhật | 1. Click icon "Bộ thủ" trên toolbar | 1. Modal overlay Bộ thủ xuất hiện (750px wide)<br>2. Bảng bộ thủ Kanji hiển thị để chọn | Medium | Nguồn: Tiếng Nhật |
| MAZ_DICH_WEB_TC_012 | M1.4 Handwriting | LOW | Icon Vẽ tay & Bộ thủ disabled khi nguồn không phải Tiếng Nhật | Nguồn là Tiếng Việt | 1. Quan sát toolbar khi nguồn là Tiếng Việt | 1. Icon Vẽ tay và Bộ thủ ở trạng thái disabled (mờ, không click được) | Medium | Nguồn: Tiếng Việt → Tiếng Nhật |
| MAZ_DICH_WEB_TC_013 | M1.4 Handwriting | LOW | Icon Vẽ tay & Bộ thủ enabled khi nguồn là Tiếng Nhật | Nguồn là Tiếng Nhật | 1. Chọn nguồn: Tiếng Nhật<br>2. Quan sát toolbar | 1. Icon Vẽ tay và Bộ thủ chuyển sang active (có thể click) | Medium | Nguồn: Tiếng Nhật |

---

## PART 2 — M2: Language Selection (Web Dropdown)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_014 | M2.1 Source Lang | MEDIUM | Click dropdown nguồn — mở danh sách ngôn ngữ inline | Đang ở trang Dịch | 1. Click vào ô Ngôn ngữ Nguồn | 1. Dropdown Menu mở ra inline ngay bên dưới ô<br>2. Danh sách ngôn ngữ hiển thị với checkmark trên ngôn ngữ đang chọn | High | — |
| MAZ_DICH_WEB_TC_015 | M2.1 Source Lang | MEDIUM | Đổi ngôn ngữ nguồn khi đang có text — giữ text + dịch lại | Đang có text trong textarea | 1. Click dropdown nguồn<br>2. Chọn ngôn ngữ khác (VD: Tiếng Anh) | 1. Dropdown đóng<br>2. Ngôn ngữ nguồn cập nhật<br>3. Text nguồn giữ nguyên<br>4. Hệ thống tự động dịch lại theo ngôn ngữ nguồn mới | High | Text nguồn: "xin chào" · Nguồn: Việt → đổi sang Anh |
| MAZ_DICH_WEB_TC_016 | M2.2 Target Lang | MEDIUM | Đổi ngôn ngữ đích khi đang có kết quả — dịch lại tức thì | Đang có kết quả dịch | 1. Click dropdown đích<br>2. Chọn ngôn ngữ khác | 1. Dropdown đóng<br>2. Ngôn ngữ đích cập nhật<br>3. Hệ thống tự động kích hoạt lại lệnh dịch<br>4. Kết quả mới hiển thị tức thì | High | Đích ban đầu: Tiếng Nhật → đổi sang Tiếng Hàn |
| MAZ_DICH_WEB_TC_017 | M2.3 Swap | MEDIUM | Hover icon Swap — hiển thị tooltip, click để đổi | Đang có text nguồn và kết quả dịch | 1. Hover chuột vào icon Swap (⇄)<br>2. Click icon Swap | 1. Hover: icon highlight / tooltip hiển thị<br>2. Click: Text đích → thành Text nguồn; Text nguồn cũ → thành Text đích<br>3. Ngôn ngữ nguồn và đích hoán đổi<br>4. Tự động dịch lại | High | Nguồn: "xin chào" (Việt→Nhật) → Sau swap: Nhật→Việt |
| MAZ_DICH_WEB_TC_018 | M2.3 Swap | MEDIUM | Swap khi textarea rỗng — chỉ đổi ngôn ngữ | Textarea rỗng, chưa có kết quả | 1. Click icon Swap (⇄) | 1. Ngôn ngữ nguồn và đích hoán đổi<br>2. Không kích hoạt dịch lại (không có text) | Medium | — |

---

## PART 3 — M3: Translation Execution + Furigana + Deep Lookup (Web)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_019 | M3.1 API | HIGH | Dịch thành công — hiển thị Result Card đầy đủ (layout 2 cột) | Đã đăng nhập, có mạng | 1. Nhập văn bản vào textarea nguồn<br>2. Click nút "Dịch" | 1. Skeleton loading hiển thị ở cột kết quả<br>2. Result Card hiện: phiên âm, dịch nghĩa, Model Badge, Copy, Phát âm, ✨ Phân tích<br>3. Layout 2 cột: nguồn bên trái, kết quả bên phải | Critical | Nhập: "ありがとうございます" · Nhật → Việt |
| MAZ_DICH_WEB_TC_020 | M3.1 API | HIGH | Skeleton loading bám cấu trúc text — cột kết quả | Đang chờ kết quả dịch | 1. Nhập text, click Dịch<br>2. Quan sát cột kết quả trong lúc loading | 1. Skeleton hiển thị đúng vị trí phiên âm và dịch nghĩa<br>2. Không phải spinner đơn thuần | High | Nhập: "日本語を勉強しています" |
| MAZ_DICH_WEB_TC_021 | M3.1 API | HIGH | Dịch thủ công — không tự động dịch khi đang nhập | Đã nhập text, chưa click Dịch | 1. Nhập text vào textarea<br>2. Quan sát cột kết quả | 1. Cột kết quả KHÔNG tự động cập nhật khi đang nhập<br>2. Chỉ dịch khi click nút "Dịch" | High | Nhập: "hello world" |
| MAZ_DICH_WEB_TC_022 | M3.1 API | HIGH | API timeout — toast lỗi sau 30s | Môi trường network throttle | 1. Nhập text, click Dịch<br>2. Chờ >30 giây | 1. Skeleton loading biến mất<br>2. Toast lỗi timeout hiển thị | High | Nhập: "hello" · Môi trường: network throttle |
| MAZ_DICH_WEB_TC_023 | M3.1 API | HIGH | Mất kết nối mạng khi đang dịch — toast lỗi | Đang dịch, sau đó mất mạng | 1. Nhập text, click Dịch<br>2. Tắt mạng trong lúc chờ | 1. Toast thông báo mất kết nối hiển thị<br>2. Không crash trang | Critical | Nhập: "さようなら" |
| MAZ_DICH_WEB_TC_024 | M3.2 Copy | MEDIUM | Copy kết quả dịch vào clipboard | Đang có Result Card | 1. Click icon Copy trên Result Card | 1. Kết quả dịch được copy vào clipboard<br>2. Toast "Đã sao chép" hiển thị | High | — |
| MAZ_DICH_WEB_TC_025 | M3.2 Phát âm | MEDIUM | Click Phát âm — phát audio TTS | Đang có Result Card | 1. Click icon Loa (Phát âm) | 1. Audio TTS phát ra<br>2. Không bị lỗi hoặc im lặng | High | — |
| MAZ_DICH_WEB_TC_026 | M3.3 Furigana | HIGH | KHÔNG hiển thị Furigana khi đang nhập (cursor đang nhấp nháy) — nguồn Nhật | Nguồn là Tiếng Nhật, textarea đang focus | 1. Chọn nguồn: Tiếng Nhật<br>2. Click textarea (focus)<br>3. Nhập "日本語" | 1. Textarea hiển thị văn bản thô "日本語"<br>2. Furigana KHÔNG xuất hiện khi textarea đang được focus/nhập | Critical | Nhập: "日本語" · Nguồn: Tiếng Nhật |
| MAZ_DICH_WEB_TC_027 | M3.3 Furigana | HIGH | Furigana hiển thị đúng SAU khi click Dịch — nguồn Nhật | Nguồn là Tiếng Nhật | 1. Nhập "日本語" vào textarea<br>2. Click nút "Dịch" | 1. Textarea mất focus<br>2. Furigana "にほんご" nổi lên trên chữ "日本語" trong textarea<br>3. Hiển thị đúng phiên âm | Critical | Nhập: "日本語" · Nhật → Việt |
| MAZ_DICH_WEB_TC_028 | M3.3 Furigana | HIGH | Không hiển thị Furigana khi nguồn là Tiếng Việt | Nguồn là Tiếng Việt | 1. Chọn nguồn: Tiếng Việt<br>2. Nhập text, click Dịch | 1. Textarea KHÔNG hiển thị Furigana sau khi dịch | Medium | Nhập: "Xin chào" · Việt → Nhật |
| MAZ_DICH_WEB_TC_029 | M3.4 Deep Lookup | HIGH | Click vào chữ Kanji trong kết quả — mở tra từ sâu | Đang có Result Card chứa Kanji | 1. Quan sát kết quả dịch có Kanji<br>2. Click vào một chữ Kanji cụ thể | 1. Mở trang/modal tra cứu chi tiết chữ Kanji đó<br>2. Hiển thị: nghĩa, cách đọc, ví dụ | High | Kết quả có "東京" → click "東" |

---

## PART 4 — M4: Model Selection + Paywall (Web Drawer)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_030 | M4.1 Model | HIGH | Click dropdown Model — mở Drawer/Panel danh sách model | Đang có Result Card, tài khoản bất kỳ | 1. Click vào dropdown "Mazii Translator ▼" ở góc dưới phải cột kết quả | 1. Drawer/Panel model xuất hiện (514px wide)<br>2. Danh sách models: Mazii Translator (Free), Mazii Base Translator (AI), Mazii NMT (AI)<br>3. Mazii NMT nổi bật với icon Khóa/Premium | High | — |
| MAZ_DICH_WEB_TC_031 | M4.1 Model | HIGH | Đóng Drawer model — giữ nguyên model cũ | Drawer model đang mở | 1. Click ngoài vùng Drawer hoặc nút đóng | 1. Drawer đóng lại<br>2. Model hiện tại không thay đổi<br>3. Dropdown vẫn hiển thị tên model cũ | Medium | — |
| MAZ_DICH_WEB_TC_032 | M4.2 Paywall | HIGH | Tài khoản Free/Guest click model AI → Paywall | Drawer model đang mở, tài khoản Free | 1. Click chọn Mazii NMT | 1. Drawer đóng<br>2. Màn hình Paywall bật lên ngay lập tức | Critical | Tài khoản: Guest/Free |
| MAZ_DICH_WEB_TC_033 | M4.2 Paywall | HIGH | Tài khoản Thường click model AI → Paywall | Drawer model đang mở, tài khoản Thường | 1. Click chọn Mazii Base Translator hoặc Mazii NMT | 1. Màn hình Paywall hiển thị | Critical | Tài khoản: Thường (Standard) |
| MAZ_DICH_WEB_TC_034 | M4.1 Model | HIGH | Tài khoản Premium đổi model — không Paywall, dịch lại ngay | Drawer model đang mở, tài khoản Premium | 1. Click chọn model khác (VD: Mazii NMT) | 1. Không hiện Paywall<br>2. Drawer tự đóng<br>3. Dịch lại ngay theo model mới<br>4. Badge trên Result Card cập nhật tên model mới | Critical | Tài khoản: Premium |

---

## PART 5 — M5: Advanced Features (Web)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_035 | M5.1 Grammar | MEDIUM | Mở Drawer Kiểm tra ngữ pháp | Đang có Result Card với kết quả dịch | 1. Click nút "Kiểm tra ngữ pháp" trên Result Card | 1. Drawer Kiểm tra ngữ pháp mở ra<br>2. Hiển thị phân tích ngữ pháp của câu đang dịch | High | Nhập: "私は毎日日本語を勉強します" |
| MAZ_DICH_WEB_TC_036 | M5.1 Grammar | MEDIUM | Ngữ pháp liên quan hiển thị khi nguồn là Tiếng Nhật | Kết quả dịch từ nguồn Tiếng Nhật | 1. Quan sát Result Card sau khi dịch | 1. Section "Ngữ pháp liên quan" hiển thị trong kết quả | Medium | Nguồn: Tiếng Nhật |
| MAZ_DICH_WEB_TC_037 | M5.1 Grammar | MEDIUM | Ngữ pháp liên quan KHÔNG hiển thị khi nguồn không phải Nhật | Kết quả dịch từ nguồn Tiếng Việt | 1. Dịch text Tiếng Việt → Nhật<br>2. Quan sát Result Card | 1. Section "Ngữ pháp liên quan" KHÔNG xuất hiện | Medium | Nguồn: Tiếng Việt → Nhật |
| MAZ_DICH_WEB_TC_038 | M5.2 Tương phản | MEDIUM | Click button "Tương phản" — hiển thị bản dịch (1 chiều) | Đang có Result Card với kết quả dịch | 1. Click button "Tương phản" trên Result Card | 1. Bản dịch hiển thị ra ngay lập tức<br>2. Nội dung dịch nghĩa xuất hiện đối chiếu với văn bản nguồn | Medium | Nhập: "ありがとうございます" · Nhật → Việt |
| MAZ_DICH_WEB_TC_039 | M5.3 Phân tích | MEDIUM | Click ✨ Phân tích — loading + mở Drawer phân tích từ vựng | Đang có Result Card | 1. Click nút ✨ Phân tích trên Result Card | 1. Loading skeleton hoặc icon xoay hiển thị<br>2. Drawer/section "Kết quả phân tích" mở ra<br>3. Bảng 3 cột: Từ gốc/Kanji — Phiên âm — Ý nghĩa | High | Nhập: "東京は大きい都市です" |

---

## PART 6 — M6: Empty State (Web)

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_040 | M6 Empty | LOW | Hiển thị màn Empty State khi chưa có kết quả dịch | Mở trang Dịch lần đầu hoặc sau khi xóa text | 1. Truy cập trang Dịch khi chưa có kết quả | 1. Cột kết quả hiển thị Empty State (hình minh họa + text hướng dẫn)<br>2. Không hiển thị Result Card trống | Low | — |
| MAZ_DICH_WEB_TC_041 | M6 Empty | LOW | Empty State biến mất sau khi dịch thành công | Đang ở Empty State | 1. Nhập text<br>2. Click Dịch<br>3. Nhận kết quả thành công | 1. Empty State biến mất<br>2. Result Card hiển thị đúng vị trí | Low | Nhập: "こんにちは" |

---

## PART 7 — M7: Translation History (Web — Inline trên Page)

> **Đặc trưng Web:** Lịch sử dịch hiển thị trực tiếp bên dưới 2 textarea trong cùng trang, luôn visible — không cần mở màn riêng như mobile.  
> Mỗi item hiển thị: text nguồn, bản dịch, ngày (VD: 2026-03-07), icon xóa (trash).

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_WEB_TC_042 | M7 History | MEDIUM | Lịch sử dịch hiển thị inline bên dưới sau khi dịch thành công | Chưa có lịch sử, vừa thực hiện 1 lần dịch thành công | 1. Thực hiện dịch thành công<br>2. Quan sát phía dưới 2 textarea | 1. Section "Lịch sử dịch" xuất hiện bên dưới<br>2. Item mới nhất hiển thị: text nguồn, bản dịch, ngày hôm nay, icon trash | Medium | Nhập: "ありがとう" · Nhật → Việt |
| MAZ_DICH_WEB_TC_043 | M7 History | MEDIUM | Click vào item lịch sử — load lại vào 2 textarea | Đang có ít nhất 1 item trong lịch sử | 1. Click vào 1 item trong danh sách lịch sử | 1. Text nguồn của item đó load vào textarea bên trái<br>2. Kết quả dịch tương ứng load vào cột kết quả bên phải | Medium | — |
| MAZ_DICH_WEB_TC_044 | M7 History | MEDIUM | Xóa 1 item lịch sử bằng icon trash | Đang có ít nhất 2 items trong lịch sử | 1. Click icon trash trên 1 item cụ thể | 1. Item đó bị xóa khỏi danh sách ngay lập tức<br>2. Các item còn lại không thay đổi | Medium | — |
| MAZ_DICH_WEB_TC_045 | M7 History | MEDIUM | Click "Xoá toàn bộ" — xóa hết lịch sử | Đang có nhiều items trong lịch sử | 1. Click nút "Xoá toàn bộ" ở góc phải section lịch sử | 1. Toàn bộ lịch sử bị xóa<br>2. Section lịch sử hiển thị empty state hoặc ẩn đi | Medium | — |
| MAZ_DICH_WEB_TC_046 | M7 History | LOW | Lịch sử hiển thị đúng thứ tự — mới nhất ở trên | Đã thực hiện nhiều lần dịch | 1. Quan sát thứ tự danh sách lịch sử | 1. Item mới nhất (theo ngày) hiển thị ở đầu danh sách<br>2. Ngày tháng của từng item hiển thị đúng | Low | Dịch lần lượt: "xin chào", "cảm ơn", "tạm biệt" |
| MAZ_DICH_WEB_TC_047 | M7 History | LOW | Lịch sử không hiển thị khi chưa có phiên dịch nào | Tài khoản mới, chưa dịch lần nào | 1. Mở trang Dịch lần đầu<br>2. Quan sát phía dưới 2 textarea | 1. Section "Lịch sử dịch" không hiển thị hoặc hiển thị empty state | Low | — |

---

## Traceability Matrix

| TC ID | UC/BR/Scenario (Figma) | Logic từ mobile |
|-------|----------------------|----------------|
| WEB_TC_001 | Figma: Nhập liệu nguồn dịch | Mobile TC_001 |
| WEB_TC_002–003 | Figma: Nhập liệu (Icon Clear) | Mobile TC_002–004 |
| WEB_TC_004 | Figma: Co giãn tự động | Mobile TC_005 |
| WEB_TC_005 | Counter realtime "X/5000" (không giới hạn cứng) | — (web only) |
| WEB_TC_007 | UC1: Button Dịch disabled | Mobile TC_008 |
| WEB_TC_008–009 | Figma: Voice modal | Mobile TC_009–011 |
| WEB_TC_010–011 | Figma: Vẽ tay & Bộ thủ modal | Mobile TC_012–013 |
| WEB_TC_012–013 | Figma: Disabled/Enabled Bộ thủ | Mobile TC_012–013 |
| WEB_TC_014–016 | Figma: Chọn Ngôn ngữ Nguồn và Đích | Mobile TC_014–015 |
| WEB_TC_017–018 | Figma: Đổi Nguồn ←→ Đích | Mobile TC_016–017 |
| WEB_TC_019–021 | UC1 Main Flow, Figma: Nhập liệu | Mobile TC_020–022 |
| WEB_TC_022–023 | UC1 E1, BR03 | Mobile TC_023–024 |
| WEB_TC_024–025 | UC1 AF3, AF4 | Mobile TC_025–026 |
| WEB_TC_026–028 | Figma (N6): Furigana | Mobile TC_027–029 |
| WEB_TC_029 | Figma (N4): Deep Lookup | Mobile TC_030 |
| WEB_TC_030–031 | Figma: Đổi Model dịch (Drawer) | Mobile TC_031–032 |
| WEB_TC_032–033 | UC2 E1, BR01: Paywall | Mobile TC_033–034 |
| WEB_TC_034 | UC2 Main Flow: Premium | Mobile TC_035 |
| WEB_TC_035–037 | Figma: Kiểm tra ngữ pháp + Ngữ pháp liên quan | Mobile TC_036–038 |
| WEB_TC_038 | Figma: Tương phản | Mobile TC_039 |
| WEB_TC_039 | Figma: Phân tích từ vựng | Mobile TC_040–041 |
| WEB_TC_040–041 | Figma: Empty State | — (web only) |
| WEB_TC_042–047 | UC5: Lịch sử dịch (inline trên page) | Mobile TC_042–047 (khác: web inline, không có màn riêng) |
