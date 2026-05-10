# [Mazii] - Test Cases: Page Dịch (Mobile)

**Phiên bản:** v1.0  
**Ngày tạo:** 2026-05-10  
**Tài liệu tham chiếu:** PDF Use Cases UC1–UC5 + Figma Design (node: 14230:67321)  
**Quy trình:** AI-RBT 6 bước  
**Tổng số TC:** 45

---

## Risk Summary

| Module | Số TC | Risk Level |
|--------|-------|-----------|
| M1 Translation Input | 8 | HIGH / MEDIUM / LOW |
| M2 Language Selection | 6 | MEDIUM |
| M3 Translation Execution + Furigana + Deep Lookup | 12 | CRITICAL / HIGH |
| M4 Model Selection + Paywall | 5 | CRITICAL / HIGH |
| M5 Advanced Features (Tương phản, Phân tích, Ngữ pháp) | 6 | MEDIUM |
| M7 Translation History | 6 | LOW |
| **Tổng** | **47** | — |

---

## Assumptions đã áp dụng

| ID | Nội dung |
|----|---------|
| Q1 | Ô Input Text Area không giới hạn ký tự |
| Q2 | Button Dịch disabled khi ô input rỗng |
| Q3 | Timeout API dịch: 30 giây; hiển thị toast lỗi khi timeout |
| Q5 | Kết quả dịch tự động lưu vào Lịch sử sau mỗi lần dịch thành công |
| Q6 | Chỉ Mazii-AI mới dùng được model AI; Premium không bao gồm |
| Q7 | Loading dùng skeleton bám cấu trúc text |
| Q8 | Dịch hội thoại: 2 bên cùng 1 thiết bị |
| Q10 | Lưu tối đa 50 phiên lịch sử, local only |

---

## Business Rules

| BR Code | Mô tả |
|---------|------|
| BR01 | Công cụ dịch AI (Mazii Base, Mazii NMT) → bắt buộc Mazii-AI. Nếu không → Paywall |
| BR02 | Dịch hội thoại → bắt buộc Premium/Mazii-AI. Guest/Thường → Paywall |
| BR03 | Offline + chưa có gói ngôn ngữ → hiển thị thông báo lỗi kết nối |

---

## PART 1 — M1: Translation Input + M2: Language Selection

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_TC_001 | M1.1 Text Input | HIGH | Focus ô nhập liệu — bàn phím bật và Bottom Sheet trượt lên | Đã đăng nhập, đang ở màn Dịch tab Văn bản | 1. Tap vào ô nhập liệu văn bản | 1. Bàn phím hệ thống bật lên<br>2. Bottom Sheet trượt lên đẩy khít mép trên bàn phím | Critical | — |
| MAZ_DICH_TC_002 | M1.1 Text Input | HIGH | Icon Clear xuất hiện khi có text | Đã đăng nhập, ô nhập liệu đang rỗng | 1. Tap ô nhập liệu<br>2. Nhập văn bản | 1. Icon "Xóa (Clear)" xuất hiện ở góc phải ô nhập liệu | High | Nhập: "xin chào" |
| MAZ_DICH_TC_003 | M1.1 Text Input | HIGH | Icon Clear không hiển thị khi ô rỗng | Đã đăng nhập, ô nhập liệu rỗng | 1. Quan sát ô nhập liệu khi chưa nhập gì | 1. Icon Clear không hiển thị | Medium | — |
| MAZ_DICH_TC_004 | M1.1 Text Input | HIGH | Xóa nhanh toàn bộ văn bản bằng Clear | Đã nhập văn bản vào ô nhập liệu | 1. Nhập văn bản<br>2. Tap icon Clear | 1. Toàn bộ văn bản bị xóa<br>2. Ô nhập liệu về trạng thái rỗng<br>3. Icon Clear biến mất | High | Nhập: "今日はいい天気ですね" |
| MAZ_DICH_TC_005 | M1.1 Text Input | HIGH | Ô nhập liệu co giãn tự động theo nội dung | Đang ở màn Dịch, bàn phím đang bật | 1. Nhập 1 dòng văn bản<br>2. Tiếp tục nhập thêm đến dòng 3–5 | 1. Ô nhập liệu tự mở rộng theo chiều cao nội dung<br>2. Không xuất hiện scrollbar dọc sớm | High | Nhập text 3–5 dòng: "Xin chào\nTôi tên là Minh\nTôi đến từ Hà Nội\nRất vui được gặp bạn" |
| MAZ_DICH_TC_008 | M1.1 Text Input | HIGH | Nhấn Dịch khi ô nhập liệu rỗng | Đang ở màn Dịch, ô input rỗng | 1. Không nhập gì<br>2. Quan sát nút Dịch | 1. Nút Dịch ở trạng thái disabled — không thể tap | High | — |
| MAZ_DICH_TC_009 | M1.3 Voice | MEDIUM | Mở Bottom Sheet Ghi âm lần đầu (chưa cấp quyền mic) | Đăng nhập lần đầu, chưa cấp quyền mic | 1. Tap icon "Ghi âm" trên thanh công cụ | 1. Bottom Sheet trượt lên che Bottom Navigation<br>2. Hiển thị Soft Prompt xin cấp quyền Microphone | High | — |
| MAZ_DICH_TC_010 | M1.3 Voice | MEDIUM | Mở Bottom Sheet Ghi âm khi đã cấp quyền mic | Đã cấp quyền mic trước đó | 1. Tap icon "Ghi âm" | 1. Bottom Sheet trượt lên<br>2. Không hiện prompt xin quyền<br>3. Nút ghi âm sẵn sàng | High | — |
| MAZ_DICH_TC_011 | M1.3 Voice | MEDIUM | Ghi âm thành công — text tự điền vào ô nguồn | Bottom Sheet Ghi âm đang mở, đã cấp quyền mic | 1. Tap nút ghi âm<br>2. Đọc câu tiếng Nhật<br>3. Dừng ghi | 1. Hệ thống nhận diện giọng nói<br>2. Text tự động điền vào ô dữ liệu nguồn | High | Đọc: "おはようございます" |
| MAZ_DICH_TC_012 | M1.4 Handwriting | LOW | Icon Bộ thủ & Viết tay disabled khi nguồn không phải Tiếng Nhật | Đang ở màn Dịch, ngôn ngữ nguồn là Tiếng Việt | 1. Quan sát thanh 5 công cụ nhập liệu | 1. Icon "Bộ thủ" và "Viết tay" hiển thị trạng thái disabled (mờ, không tap được) | Medium | Nguồn: Tiếng Việt → Tiếng Nhật |
| MAZ_DICH_TC_013 | M1.4 Handwriting | LOW | Icon Bộ thủ & Viết tay enabled khi nguồn là Tiếng Nhật | Đang ở màn Dịch, ngôn ngữ nguồn là Tiếng Nhật | 1. Chọn ngôn ngữ nguồn là Tiếng Nhật<br>2. Quan sát thanh công cụ | 1. Icon "Bộ thủ" và "Viết tay" chuyển sang trạng thái active (có thể tap) | Medium | Nguồn: Tiếng Nhật |
| MAZ_DICH_TC_014 | M2.1 Source Lang | MEDIUM | Đổi ngôn ngữ nguồn khi đang có text — giữ text + dịch lại | Đang có text trong ô nhập liệu, đã có kết quả dịch | 1. Tap ô ngôn ngữ Nguồn<br>2. Chọn ngôn ngữ khác (VD: Tiếng Anh)<br>3. Xác nhận | 1. Text nguồn giữ nguyên không bị xóa<br>2. Ngôn ngữ nguồn cập nhật lên thanh<br>3. Hệ thống tự động dịch lại text theo ngôn ngữ nguồn mới | High | Text nguồn: "xin chào" · Nguồn ban đầu: Tiếng Việt → đổi sang Tiếng Anh |
| MAZ_DICH_TC_015 | M2.2 Target Lang | MEDIUM | Đổi ngôn ngữ đích khi đang có kết quả — dịch lại tức thì | Đang có kết quả dịch trên màn hình | 1. Tap ô ngôn ngữ Đích<br>2. Chọn ngôn ngữ khác (VD: Nhật → Hàn) | 1. Thanh ngôn ngữ cập nhật ngôn ngữ đích mới<br>2. Hệ thống tự động kích hoạt lại lệnh dịch<br>3. Kết quả mới hiển thị tức thì | High | Đích ban đầu: Tiếng Nhật → đổi sang Tiếng Hàn |
| MAZ_DICH_TC_016 | M2.3 Swap | MEDIUM | Swap ngôn ngữ khi đang có text và kết quả | Đang có text nguồn "xin chào" và kết quả dịch | 1. Tap icon Swap (⇄) | 1. Text đích → trở thành Text nguồn<br>2. Text nguồn cũ → xuống thành Text đích<br>3. Ngôn ngữ nguồn và đích hoán đổi nhau<br>4. Hệ thống tự động dịch lại | High | Nguồn: "xin chào" (Việt→Nhật) → Sau swap: Nhật→Việt |
| MAZ_DICH_TC_017 | M2.3 Swap | MEDIUM | Swap khi ô nhập liệu rỗng | Ô nhập liệu rỗng, chưa có kết quả dịch | 1. Tap icon Swap (⇄) | 1. Ngôn ngữ nguồn và đích hoán đổi nhau<br>2. Không có hiệu ứng dịch lại (không có text) | Medium | — |
| MAZ_DICH_TC_018 | M2.1 Source Lang | MEDIUM | Tìm kiếm ngôn ngữ không tồn tại | Màn hình chọn ngôn ngữ đang mở | 1. Nhập từ khóa không tồn tại vào ô tìm kiếm | 1. Hiển thị trạng thái "Không tìm thấy ngôn ngữ" | Low | Từ khóa: "Klingon123" |
| MAZ_DICH_TC_019 | M2.1 Source Lang | MEDIUM | Hủy chọn ngôn ngữ — giữ nguyên ngôn ngữ cũ | Màn hình chọn ngôn ngữ đang mở | 1. Mở màn chọn ngôn ngữ<br>2. Tap Back hoặc nút Hủy | 1. Màn chọn ngôn ngữ đóng lại<br>2. Ngôn ngữ nguồn giữ nguyên như trước | Low | — |

---

## PART 2 — M3: Translation Execution + M4: Model Selection

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_TC_020 | M3.1 API | HIGH | Dịch thành công — hiển thị Result Card đầy đủ | Đã đăng nhập, có kết nối mạng | 1. Nhập văn bản vào ô nguồn<br>2. Tap nút "Dịch" | 1. Skeleton loading hiển thị<br>2. Result Card hiện: phiên âm, dịch nghĩa, Model Badge, nút Copy, nút Phát âm, nút ✨ Phân tích | Critical | Nhập: "ありがとうございます" · Nguồn: Nhật → Việt |
| MAZ_DICH_TC_021 | M3.1 API | HIGH | Skeleton loading bám sát cấu trúc text | Đang chờ kết quả dịch | 1. Nhập text<br>2. Tap Dịch<br>3. Quan sát trạng thái loading | 1. Skeleton hiển thị đúng vị trí phiên âm và dịch nghĩa<br>2. Không phải spinner đơn thuần | High | Nhập: "日本語を勉強しています" |
| MAZ_DICH_TC_022 | M3.1 API | HIGH | Dịch thủ công — kích hoạt chỉ khi bấm nút Dịch | Đã nhập text, chưa tap Dịch | 1. Nhập text vào ô nguồn<br>2. Quan sát màn hình (không tap Dịch) | 1. Hệ thống KHÔNG tự động dịch khi đang nhập<br>2. Chỉ dịch khi tap nút "Dịch" | High | Nhập: "konnichiwa" |
| MAZ_DICH_TC_023 | M3.1 API | HIGH | API timeout — hiển thị thông báo lỗi | Có kết nối mạng yếu (simulate timeout >30s) | 1. Nhập text<br>2. Tap Dịch<br>3. Chờ >30 giây | 1. Sau 30s, skeleton loading biến mất<br>2. Hiển thị toast/snackbar thông báo lỗi timeout | High | Nhập: "hello" · Môi trường: network throttle |
| MAZ_DICH_TC_024 | M3.1 API | HIGH | Mất kết nối mạng khi đang dịch | Đang sử dụng tính năng dịch, sau đó mất mạng | 1. Nhập text, tap Dịch<br>2. Tắt mạng trong lúc chờ kết quả | 1. Hệ thống hiển thị toast thông báo mất kết nối<br>2. Không crash app | Critical | Nhập: "さようなら" |
| MAZ_DICH_TC_025 | M3.2 Copy | MEDIUM | Copy kết quả dịch vào clipboard | Đang có Result Card với kết quả dịch | 1. Tap icon Copy trên Result Card | 1. Kết quả dịch được copy vào clipboard<br>2. Bottom Toast hiện "Đã sao chép" | High | — |
| MAZ_DICH_TC_026 | M3.2 Phát âm | MEDIUM | Phát âm kết quả dịch | Đang có Result Card với kết quả dịch | 1. Tap icon Loa (Phát âm) trên Result Card | 1. Audio TTS của kết quả dịch phát ra<br>2. Không bị lỗi hoặc im lặng | High | — |
| MAZ_DICH_TC_027 | M3.3 Furigana | HIGH | KHÔNG hiển thị Furigana khi bàn phím đang bật (nguồn Nhật) | Ngôn ngữ nguồn là Tiếng Nhật, bàn phím đang mở | 1. Chọn nguồn: Tiếng Nhật<br>2. Tap ô nhập liệu (bàn phím bật)<br>3. Nhập "日本語" | 1. Ô nhập liệu hiển thị văn bản thô "日本語"<br>2. Furigana KHÔNG xuất hiện trên đầu Kanji khi bàn phím còn bật | Critical | Nhập: "日本語" · Nguồn: Tiếng Nhật |
| MAZ_DICH_TC_028 | M3.3 Furigana | HIGH | Furigana hiển thị đúng SAU khi bấm Dịch (nguồn Nhật) | Ngôn ngữ nguồn là Tiếng Nhật | 1. Nhập "日本語" vào ô nguồn<br>2. Tap nút "Dịch" (bàn phím đóng lại) | 1. Bàn phím đóng<br>2. Trên ô nhập liệu: "にほんご" nổi lên trên chữ "日本語"<br>3. Furigana hiển thị đúng | Critical | Nhập: "日本語" · Nguồn: Nhật → Việt |
| MAZ_DICH_TC_029 | M3.3 Furigana | HIGH | Không hiển thị Furigana khi nguồn là Tiếng Việt | Ngôn ngữ nguồn là Tiếng Việt | 1. Chọn nguồn: Tiếng Việt<br>2. Nhập text và tap Dịch | 1. Ô nhập liệu không hiển thị Furigana sau khi dịch | Medium | Nhập: "Xin chào" · Nguồn: Việt → Nhật |
| MAZ_DICH_TC_030 | M3.4 Deep Lookup | HIGH | Tap vào chữ Kanji cụ thể trong kết quả — mở tra từ sâu | Đang có Result Card với kết quả dịch chứa Kanji | 1. Quan sát kết quả dịch có chứa Kanji<br>2. Tap vào một chữ Kanji cụ thể | 1. Mở màn hình chi tiết tra cứu chữ Kanji đó<br>2. Hiển thị thông tin: nghĩa, cách đọc, ví dụ | High | Kết quả có chứa: "東京" → tap "東" |
| MAZ_DICH_TC_031 | M4.1 Model | HIGH | Mở Bottom Sheet chọn model qua Badge | Đang có Result Card, tài khoản bất kỳ | 1. Tap vào Badge tên Model trên Result Card | 1. Bottom Sheet danh sách AI mở ra<br>2. Hiển thị: Mazii Translator (Free), Mazii Base Translator (AI), Mazii NMT (AI)<br>3. Mazii NMT nổi bật với icon Khóa/Premium | High | — |
| MAZ_DICH_TC_032 | M4.1 Model | HIGH | Đóng Bottom Sheet chọn model — giữ nguyên model cũ | Bottom Sheet model đang mở | 1. Tap ngoài vùng Bottom Sheet hoặc kéo xuống đóng | 1. Bottom Sheet đóng lại<br>2. Model hiện tại không thay đổi | Medium | — |
| MAZ_DICH_TC_033 | M4.2 Paywall | HIGH | Tài khoản Free/Guest chọn model Premium (Mazii NMT) → Paywall | Bottom Sheet model đang mở, tài khoản Free/Guest | 1. Tap chọn Mazii NMT | 1. Bottom Sheet đóng ngay lập tức<br>2. Màn hình Paywall chào bán gói Premium bật lên | Critical | Tài khoản: Guest |
| MAZ_DICH_TC_034 | M4.2 Paywall | HIGH | Tài khoản Thường chọn model AI → Paywall | Bottom Sheet model đang mở, tài khoản Thường | 1. Tap chọn Mazii Base Translator hoặc Mazii NMT | 1. Màn hình Paywall hiển thị | Critical | Tài khoản: Thường (Standard) |
| MAZ_DICH_TC_035 | M4.1 Model | HIGH | Tài khoản Premium đổi model — không Paywall, dịch lại ngay | Bottom Sheet model đang mở, tài khoản Premium | 1. Tap chọn model khác (VD: Mazii NMT) | 1. Không hiện Paywall<br>2. Bottom Sheet tự đóng<br>3. Dịch lại ngay theo model mới<br>4. Badge trên Result Card cập nhật tên model mới | Critical | Tài khoản: Premium |

---

## PART 3 — M5: Advanced Features + M7: Translation History

| TC ID | Module | Risk | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|------|-----------|--------------|-----------|----------------|---------|----------|
| MAZ_DICH_TC_036 | M5.1 Grammar | MEDIUM | Mở Drawer Kiểm tra ngữ pháp | Đang có Result Card với kết quả dịch | 1. Tap nút "Kiểm tra ngữ pháp" trên Result Card | 1. Drawer Kiểm tra ngữ pháp trượt lên<br>2. Hiển thị phân tích ngữ pháp câu đang dịch | High | Nhập: "私は毎日日本語を勉強します" |
| MAZ_DICH_TC_037 | M5.1 Grammar | MEDIUM | Ngữ pháp liên quan hiển thị khi nguồn là Tiếng Nhật | Đang có kết quả dịch, nguồn Tiếng Nhật | 1. Quan sát Result Card sau khi dịch | 1. Section "Ngữ pháp liên quan" hiển thị trong kết quả | Medium | Nguồn: Tiếng Nhật |
| MAZ_DICH_TC_038 | M5.1 Grammar | MEDIUM | Ngữ pháp liên quan KHÔNG hiển thị khi nguồn không phải Nhật | Đang có kết quả dịch, nguồn Tiếng Việt | 1. Dịch text Tiếng Việt sang Nhật<br>2. Quan sát Result Card | 1. Section "Ngữ pháp liên quan" KHÔNG xuất hiện | Medium | Nguồn: Tiếng Việt → Nhật |
| MAZ_DICH_TC_039 | M5.2 Tương phản | MEDIUM | Nhấn button "Tương phản" — hiển thị bản dịch | Đang có Result Card với kết quả dịch | 1. Tap button "Tương phản" trên Result Card | 1. Bản dịch hiển thị ra ngay lập tức<br>2. Nội dung dịch nghĩa xuất hiện đối chiếu với văn bản nguồn | Medium | Nhập: "ありがとうございます" · Nguồn: Nhật → Việt |
| MAZ_DICH_TC_040 | M5.3 Phân tích | MEDIUM | Kích hoạt Phân tích từ vựng — loading + mở Drawer | Đang có Result Card | 1. Tap nút ✨ Phân tích trên Result Card | 1. Loading skeleton hoặc icon xoay hiển thị<br>2. Drawer "Kết quả phân tích" mở rộng bên dưới<br>3. Bảng 3 cột: Từ gốc/Kanji — Phiên âm — Ý nghĩa | High | Nhập: "東京は大きい都市です" |
| MAZ_DICH_TC_041 | M5.3 Phân tích | MEDIUM | Phân tích từ vựng đủ 3 cột dữ liệu chính xác | Drawer Phân tích đang mở | 1. Quan sát bảng kết quả phân tích | 1. Mỗi hàng có đủ: Từ gốc, Phiên âm, Ý nghĩa<br>2. Không có ô trống hoặc "undefined" | Medium | Câu: "東京は大きい都市です" |
| MAZ_DICH_TC_042 | M7 History | LOW | Xem danh sách lịch sử dịch | Đã thực hiện ít nhất 1 lần dịch thành công | 1. Tap icon Lịch sử trên màn Dịch | 1. Màn hình lịch sử mở ra<br>2. Danh sách phiên dịch hiển thị theo thứ tự mới nhất | Medium | — |
| MAZ_DICH_TC_043 | M7 History | LOW | Chọn lại phiên dịch cũ | Đang ở màn Lịch sử, có ít nhất 1 phiên | 1. Tap vào 1 phiên dịch trong danh sách | 1. Màn lịch sử đóng<br>2. Màn Dịch hiển thị lại kết quả tương ứng của phiên đó | Medium | — |
| MAZ_DICH_TC_044 | M7 History | LOW | Xóa 1 phiên dịch cụ thể | Đang ở màn Lịch sử | 1. Tap icon Xóa trên 1 phiên dịch cụ thể | 1. Phiên đó bị xóa khỏi danh sách<br>2. Danh sách cập nhật ngay lập tức | Low | — |
| MAZ_DICH_TC_045 | M7 History | LOW | Xóa tất cả lịch sử — confirmation dialog + empty state | Đang ở màn Lịch sử, có nhiều phiên | 1. Tap nút "Xóa tất cả"<br>2. Xác nhận trong dialog | 1. Dialog xác nhận hiện ra<br>2. Sau xác nhận: toàn bộ lịch sử xóa<br>3. Hiển thị empty state "Chưa có lịch sử dịch" | Low | — |
| MAZ_DICH_TC_046 | M7 History | LOW | Empty state khi chưa có lịch sử | Tài khoản mới, chưa dịch lần nào | 1. Tap icon Lịch sử | 1. Hiển thị empty state với thông báo "Chưa có lịch sử dịch" | Low | — |
| MAZ_DICH_TC_047 | M7 History | LOW | Back từ màn Lịch sử — quay lại màn Dịch không thay đổi | Đang ở màn Lịch sử | 1. Tap Back | 1. Quay lại màn Dịch<br>2. Nội dung màn Dịch không bị thay đổi | Low | — |

---

## Traceability Matrix

| TC ID | UC/BR | Scenario gốc |
|-------|-------|-------------|
| TC_001 | UC1 Main Flow | Focus ô nhập liệu |
| TC_002–004 | UC1 Main Flow | Icon Clear, Xóa nhanh |
| TC_005 | UC1 Main Flow | Co giãn tự động |
| TC_006–007 | UC1 (BVA) | Boundary 500 ký tự |
| TC_008 | UC1 Main Flow | Button Dịch disabled khi rỗng |
| TC_009–011 | UC1 AF1, UC3 E2 | Voice Input + Mic permission |
| TC_012–013 | BR (Figma) | Disabled/Enabled Bộ thủ & Viết tay |
| TC_014 | UC4 Main Flow | Đổi ngôn ngữ nguồn |
| TC_015 | UC4 Main Flow | Đổi ngôn ngữ đích |
| TC_016–017 | UC4 AF1 | Swap ngôn ngữ |
| TC_018–019 | UC4 E1, AF2 | Tìm kiếm + Hủy chọn ngôn ngữ |
| TC_020–022 | UC1 Main Flow | Dịch thành công, Skeleton, Thủ công |
| TC_023–024 | UC1 E1, BR03 | Timeout + Mất mạng |
| TC_025–026 | UC1 AF3, AF4 | Copy + Phát âm |
| TC_027–029 | Figma (N6) | Furigana — hiển thị đúng điều kiện |
| TC_030 | Figma (N4) | Deep Lookup Kanji |
| TC_031–032 | UC2 Main Flow, AF1 | Mở/Đóng Bottom Sheet model |
| TC_033–034 | UC2 E1, BR01 | Paywall Free/Thường |
| TC_035 | UC2 Main Flow | Premium đổi model |
| TC_036–038 | Figma (N2, N7) | Kiểm tra ngữ pháp + Ngữ pháp liên quan |
| TC_039 | Figma (N5) | Button Tương phản |
| TC_040–041 | Figma (N5) | Phân tích từ vựng ✨ |
| TC_042–047 | UC5 | Lịch sử dịch |
