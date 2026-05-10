# Test Cases — Luyện tập – Mobile App Mazii

> **Version:** v5  |  **Ngày cập nhật:** 15/04/2026  |  **Tổng số TC:** 131

---

## ⚠️ Open Questions (cần confirm trước khi thực thi)

1. TC-LT-001: Xác nhận SRS ON/OFF ảnh hưởng thế nào đến danh sách chế độ luyện tập.
2. MSG_L_15: Xác nhận cùng mã lỗi hay 2 mã riêng cho Flashcard (≥2) và Quiz/Luyện nói (≥4).
3. TC-LT-021: Xác nhận cấu trúc card Ngữ pháp với Designer (cấu trúc + ví dụ câu).
4. TC-LT-034: Auto-play luôn ghi nhận AGAIN có phải thiết kế đúng ý không?
5. TC-LT-049: Timer 1.5s cho dạng Viết bắt đầu từ khi nào? Threshold có khả thi không?
6. TC-LT-082: "Không biết đáp án" → AGAIN hay Skip (không tính FSRS)?
7. TC-LT-059/060: Cần clarify số items SRS-due vs config Số câu Quiz.
8. TC-LT-123/126: Verify copy text popup chính xác với Designer.
9. Chức năng Thi thử (xuất hiện trong TC-LT-002/003): có nằm trong scope không?
10. Group T & U: Coverage hiện tại rất mỏng, cần bổ sung trước release.

---

## 📋 Test Scenarios Overview

| Group | Số TC | Priority cao | Loại test chính |
|-------|-------|-------------|-----------------|
| A. UI Chọn chế độ | 11 | 11 High | Negative / Positive |
| B. Flashcard – Điều kiện mở | 6 | 3 High | Negative / Edge Case / Boundary / Positive |
| C. Flashcard – UI & Hiển thị | 8 | 5 High | Edge Case / Positive |
| D. Flashcard – Thao tác & Chức năng | 11 | 6 High | Positive |
| E. Flashcard – Cài đặt | 3 | 0 High | Positive |
| F. Rating Logic (BR008) | 11 | 6 High | Negative / Positive |
| G. Quiz – Điều kiện & Quyền | 10 | 9 High | Negative / Boundary / Positive |
| H. Quiz – UI Tùy chọn | 5 | 2 High | Negative / Positive |
| I. Quiz – Dạng Bài chọn | 5 | 3 High | Negative / Positive |
| J. Quiz – Dạng Nghe | 7 | 5 High | Negative / Edge Case / Positive |
| K. Quiz – Dạng Luyện viết | 6 | 5 High | Negative / Positive |
| L. Quiz – Dạng Điền chỗ trống | 5 | 5 High | Negative / Positive |
| M. Quiz – Dạng Hoàn thành câu | 6 | 3 High | Negative / Positive |
| N. Quiz – Dạng Nối | 4 | 2 High | Negative / Positive |
| O. Quiz – Màn kết quả | 6 | 5 High | Positive |
| P. Luyện nói | 12 | 9 High | Negative / Positive |
| S. Mini test – UI & Thao tác | 11 | 7 High | Positive |
| T. Xem đáp án & Giải nghĩa | 2 | 2 High | Positive |
| U. Cài đặt SRS | 2 | 0 High | Positive |

**Tổng hợp theo Type:**

| Type | Số TC | % |
|------|-------|---|
| Positive | 103 | 78.6% |
| Negative | 23 | 17.6% |
| Edge Case | 3 | 2.3% |
| Boundary | 2 | 1.5% |

**Tổng hợp theo Priority:**

| Priority | Số TC | % |
|----------|-------|---|
| High | 88 | 67.2% |
| Medium | 40 | 30.5% |
| Low | 3 | 2.3% |

---

## 🧪 Test Cases Chi Tiết

### A. UI Chọn chế độ

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-001 | Positive | High | Sổ tay CỦA MÌNH (SRS BẬT): hiển thị đủ 5 tùy chọn bao gồm Học lặp lại | Người dùng đã đăng nhập. Mở sổ tay CỦA MÌNH (tự tạo). Sổ tay có ≥1 item. Chế độ lặp lại = BẬT. | 1. Vào tab Học tập<br>2. Chọn sổ tay của mình<br>3. Nhấn nút Ôn tập sổ tay | Màn chọn chế độ hiển thị đúng 5 mục theo thứ tự: Học lặp lại (có badge SRS), Flashcard, Quiz, Luyện nói – viết, Mini test. Không hiển thị mục nào thừa hoặc thiếu. |
| TC-LT-002 | Positive | High | Sổ tay KHÁM PHÁ: KHÔNG hiển thị Học lặp lại | Người dùng đã đăng nhập. Đang xem sổ tay thuộc danh mục Khám phá. | 1. Vào Học tập<br>2. Chọn sổ tay Khám phá<br>3. Nhấn Luyện tập | Màn chọn chế độ hiển thị 4 mục: Flashcard, Quiz, Luyện nói – viết, Mini test. Mục Học lặp lại KHÔNG xuất hiện. |
| TC-LT-003 | Positive | High | Sổ tay THƯ VIỆN: KHÔNG hiển thị Học lặp lại | Người dùng đăng nhập. Đang xem sổ tay thuộc Thư viện. | 1. Vào Học tập<br>2. Chọn sổ tay Thư viện<br>3. Nhấn Luyện tập | Màn chọn chế độ hiển thị 4 mục: Flashcard, Quiz, Luyện nói – viết, Mini test. Mục Học lặp lại KHÔNG xuất hiện. |
| TC-LT-004 | Positive | High | Nhấn Học lặp lại từ sổ tay của mình → vào màn ôn tập SRS | Sổ tay của mình có ≥2 items với SRS due hôm nay. | 1. Vào Luyện tập sổ tay của mình<br>2. Nhấn Học lặp lại | App điều hướng vào màn Ôn tập SRS. Hiển thị số từ cần ôn và nút bắt đầu. |
| TC-LT-005 | Positive | High | Nhấn Flashcard → điều hướng đến màn Flashcard | Màn chọn chế độ đang hiển thị. Sổ tay có ≥2 items. | 1. Nhấn mục Flashcard | App điều hướng đến màn Flashcard. Header hiển thị tên sổ tay. |
| TC-LT-006 | Positive | High | Nhấn Quiz với TK Premium → điều hướng đến Tùy chọn Quiz | TK Premium. Sổ tay có ≥4 items. | 1. Nhấn mục Quiz | App điều hướng đến màn Tùy chọn Quiz (chọn dạng bài, số câu, âm thanh). |
| TC-LT-007 | Negative | High | Nhấn Quiz với TK Free → paywall | TK Thường (Free). Sổ tay có ≥4 items. | 1. Nhấn mục Quiz | Màn paywall/upgrade hiển thị. Không vào được Quiz. |
| TC-LT-008 | Positive | High | Nhấn Luyện nói, viết với TK Premium hoặc Mazii-AI → điều hướng vào | TK Premium hoặc Mazii-AI. Sổ tay ≥4 items. | 1. Nhấn mục Luyện nói, viết | App điều hướng vào màn Luyện nói, viết (cả Premium và Mazii-AI đều được phép). |
| TC-LT-009 | Negative | High | Nhấn Luyện nói, viết với TK Free → paywall | TK Thường (Free). Sổ tay có ≥4 items. | 1. Nhấn mục Luyện nói, viết | Màn paywall/upgrade hiển thị. Không vào được Luyện nói, viết. |
| TC-LT-010 | Positive | High | Nhấn Mini test với TK Premium hoặc Mazii-AI → điều hướng vào màn Mini test | TK Premium hoặc Mazii-AI. Sổ tay ≥4 items. | 1. Nhấn mục Mini test | App điều hướng đến màn Mini test |
| TC-LT-011 | Negative | High | Nhấn Mini test với TK Free → paywall | TK Thường (Free). Sổ tay có ≥4 items. | 1. Nhấn mục Mini test | Màn paywall/upgrade hiển thị. |

### B. Flashcard – Điều kiện mở

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-012 | Positive | High | Mở Flashcard thành công – SRS ON và ≥2 items đến hạn | Người dùng đã đăng nhập (bất kỳ loại TK). Sổ tay Từ vựng có ≥2 items với SRS due date = hôm nay. Chế độ lặp lại = BẬT. | 1. Mở app Mazii<br>2. Vào tab Học tập<br>3. Chọn sổ tay có ≥2 items đến hạn SRS<br>4. Nhấn Luyện tập<br>5. Nhấn Flashcard | Màn Flashcard mở thành công. Card đầu tiên hiển thị mặt trước. Progress bar hiển thị số cards đến hạn. |
| TC-LT-013 | Positive | Medium | Mở Flashcard với SRS OFF → hiển thị toàn bộ sổ tay | Người dùng đã đăng nhập. Sổ tay 20 items, 3 items SRS due. Chế độ lặp lại = TẮT. | 1. Tắt Chế độ lặp lại<br>2. Chọn sổ tay<br>3. Nhấn Luyện tập → Flashcard | Flashcard hiển thị toàn bộ 20 items. Progress hiển thị '1/20', không lọc theo SRS. |
| TC-LT-014 | Negative | High | Mở Flashcard khi sổ tay < 2 items → MSG_L_15 | Người dùng đã đăng nhập. Sổ tay chỉ có 1 item. | 1. Chọn sổ tay có 1 item<br>2. Nhấn Luyện tập → Flashcard | Thông báo MSG_L_15: 'Cần ít nhất 2 mục trong sổ tay để sử dụng tính năng này'. Không mở Flashcard. |
| TC-LT-015 | Negative | High | Mở Flashcard – SRS ON không có items đến hạn → MSG_L_16 | Người dùng đã đăng nhập. Sổ tay ≥2 items nhưng tất cả đã ôn gần đây (không có SRS due hôm nay). Chế độ lặp lại = BẬT. | 1. Chọn sổ tay<br>2. Nhấn Luyện tập → Flashcard | Thông báo MSG_L_16: 'Không có mục nào cần ôn tập hôm nay'. Không mở Flashcard. |
| TC-LT-016 | Boundary | Medium | Mở Flashcard với đúng 2 items (biên dưới) → thành công | Người dùng đã đăng nhập. Sổ tay có đúng 2 items. SRS OFF hoặc cả 2 items đến hạn. | 1. Chọn sổ tay có đúng 2 items<br>2. Nhấn Luyện tập → Flashcard | Flashcard mở thành công. Progress '1/2'. Không hiện MSG_L_15. |
| TC-LT-017 | Edge Case | Medium | Mở Flashcard khi offline → sử dụng dữ liệu local cache | Người dùng đã đăng nhập. Sổ tay đã được tải về thiết bị. Tắt kết nối internet. | 1. Tắt internet trên thiết bị<br>2. Mở app Mazii<br>3. Chọn sổ tay<br>4. Nhấn Luyện tập → Flashcard | Flashcard mở từ dữ liệu local. Kết quả lưu local, đồng bộ khi online trở lại. |

### C. Flashcard – UI & Hiển thị

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-018 | Positive | High | UI Flashcard: Header đúng – tiêu đề, nút Back, nút cài đặt | Đang ở màn hình Flashcard. | 1. Mở Flashcard<br>2. Quan sát header | Header: nút Back (←) góc trái, "Flashcard - STT câu hiện tại/tổng số câu" ở giữa, icon cài đặt (⚙) góc phải. |
| TC-LT-019 | Positive | High | UI Flashcard sổ tay Từ vựng: card hiển thị từ, phiên âm, nghĩa | Sổ tay loại Từ vựng. Đang ở Flashcard. | 1. Mở Flashcard sổ tay Từ vựng<br>2. Quan sát card mặt trước và mặt sau | Mặt trước: phiên âm, nghĩa, icon loa<br>Mặt sau: từ vựng, phiên âm, nghĩa, btn Ghi chú |
| TC-LT-020 | Positive | High | UI Flashcard sổ tay Hán tự: card hiển thị kanji, âm on/kun, nghĩa | Sổ tay loại Hán tự. Đang ở Flashcard. | 1. Mở Flashcard sổ tay Hán tự<br>2. Quan sát card | Mặt trước: chữ hán, âm kunyomi, Onuomi, icon loa<br>Mặt sau: khung vẽ kanji, nghĩa, âm kun, on, ví dụ (nếu có), btn Ghi chú |
| TC-LT-021 | Positive | High | UI Flashcard sổ tay Ngữ pháp: card hiển thị cấu trúc và ví dụ | Sổ tay loại Ngữ pháp. Đang ở Flashcard. | 1. Mở Flashcard sổ tay Ngữ pháp<br>2. Quan sát card | Mặt trước: cấu trúc ngữ pháp (pattern/form), ví dụ câu minh họa, icon loa.<br>Mặt sau: giải thích ý nghĩa / cách dùng, thêm ví dụ ứng dụng, btn Ghi chú.<br><br>⚠️ Lưu ý: Cần xác nhận lại nội dung chính xác với Designer vì cấu trúc card Ngữ pháp khác với Từ vựng. |
| TC-LT-022 | Positive | Medium | Progress bar và số thứ tự card cập nhật chính xác | Đang ở Flashcard với 10 cards. | 1. Quan sát progress bar<br>2. Chuyển qua 3 cards | Progress bar cập nhật sau mỗi card. Chỉ số hiển thị '1/10' → '2/10' → '3/10'. |
| TC-LT-023 | Positive | Medium | Hiển thị các button dưới card (Chưa thuộc, Thuộc lờ mờ, Đã thuộc) và nút Back | Đang ở Flashcard với 10 cards | 1. Quan sát dưới card | Màn Flashcard hiển thị 3 nút phía dưới card theo thứ tự: [Chưa thuộc] (trái) – [Thuộc lờ mờ] (giữa) – [Đã thuộc] (phải). Nút Back (quay về card trước) hiển thị góc trên trái. |
| TC-LT-024 | Positive | High | Loa phát âm: nhấn icon loa → phát âm từ | Đang xem card Flashcard. Volume thiết bị > 0. | 1. Nhấn icon loa (🔊) trên card | App phát âm từ tiếng Nhật. Icon loa có animation khi đang phát. |
| TC-LT-025 | Edge Case | Low | Loa phát âm khi thiết bị im lặng → không crash, không phát âm | Thiết bị đang ở chế độ im lặng. Đang xem card Flashcard. | 1. Thiết bị silent mode<br>2. Nhấn icon loa | App không crash. Không có âm thanh (do silent mode). Icon loa vẫn hoạt động. |

### D. Flashcard – Thao tác & Chức năng

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-026 | Positive | High | Lật card: nhấn vào card → hiển thị mặt sau | Đang xem mặt trước card Flashcard. | 1. Nhấn vào card | Card lật với animation hiệu ứng flip.<br>Sau khi lật, mặt sau hiển thị: từ vựng (chữ JP lớn), phiên âm, nghĩa tiếng Việt, icon loa, btn Ghi chú.<br>Các nút rating xuất hiện bên dưới card: [Chưa thuộc] (trái) – [Thuộc lờ mờ] (giữa) – [Đã thuộc] (phải). |
| TC-LT-027 | Positive | High | Vuốt phải → ghi nhận 'Đã thuộc', chuyển card tiếp | Đang xem card Flashcard. | 1. Vuốt card sang phải | Card trượt phải với hiệu ứng xanh lá (✓). Ghi nhận Đã thuộc. Chuyển card tiếp theo. |
| TC-LT-028 | Positive | High | Vuốt trái → ghi nhận 'Chưa thuộc', chuyển card tiếp | Đang xem card Flashcard. | 1. Vuốt card sang trái | Card trượt trái với hiệu ứng đỏ (✗). Ghi nhận Chưa thuộc. Chuyển card tiếp theo. |
| TC-LT-029 | Positive | High | Nhấn button 'Chưa thuộc' → ghi nhận và chuyển card | Đang xem mặt sau card Flashcard. | 1. Nhấn nút 'Chưa thuộc' | Ghi nhận Chưa thuộc (Again). Chuyển sang card tiếp theo. Card có thể xuất hiện lại cuối phiên. |
| TC-LT-030 | Positive | Medium | Nhấn button 'Thuộc lờ mờ' → ghi nhận Hard và chuyển card | Đang xem mặt sau card Flashcard. | 1. Nhấn nút 'Thuộc lờ mờ' | Ghi nhận Hard. Chuyển card tiếp theo. |
| TC-LT-031 | Positive | High | Nhấn button 'Đã thuộc' → ghi nhận Easy/Good và chuyển card | Đang xem mặt sau card Flashcard. | 1. Nhấn nút 'Đã thuộc' | Ghi nhận Easy hoặc Good (tùy thời gian phản hồi). Chuyển card tiếp theo. |
| TC-LT-032 | Positive | Medium | Nhấn button Back trên card → quay về card trước | Đang xem card thứ 3 trở đi. Đã đánh giá card trước. | 1. Nhấn nút Back (←) trên card (khác với nút Back ở header)<br>2. Quan sát | Quay lại card trước đó. Đánh giá của card trước bị hủy để đánh giá lại. |
| TC-LT-033 | Positive | Medium | Shuffle: nhấn shuffle → xáo trộn thứ tự cards chưa học | Đang ở Flashcard với ≥5 cards chưa học. | 1. Nhấn nút Shuffle (🔀)<br>2. Quan sát thứ tự cards | Thứ tự cards thay đổi so với trước khi shuffle. Tiếp tục từ card đầu thứ tự mới. |
| TC-LT-034 | Positive | Medium | Play tự động: bật → card tự lật sau vài giây và chuyển tiếp | Đang ở Flashcard. Chức năng Play tự động có sẵn. | 1. Nhấn Play tự động (▶)<br>2. Quan sát hành vi card | Card tự lật mặt sau sau vài giây. Sau đó tự nghiêng trái (Chưa thuộc) và chuyển card tiếp. Có thể dừng bằng Pause (⏸). |
| TC-LT-035 | Positive | Medium | Tra từ: nhấn tra từ → mở popup/màn chi tiết từ | Đang xem card Flashcard. | 1. Nhấn icon tra từ hoặc nhấn giữ vào từ trên card | Popup hoặc màn chi tiết từ mở ra: nghĩa đầy đủ, ví dụ, conjugation. Có nút đóng để quay lại Flashcard. |
| TC-LT-036 | Positive | High | Hoàn thành toàn bộ phiên Flashcard → màn hình kết quả | Đang ở Flashcard với 5 cards. Đã đánh giá 4 cards. | 1. Đánh giá card thứ 5 (card cuối cùng) | Màn kết quả: tổng số cards, số Đã thuộc/Thuộc lờ mờ/Chưa thuộc. Nút 'Học lại' và 'Hoàn thành'. |

### E. Flashcard – Cài đặt

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-037 | Positive | Medium | Mở màn cài đặt Flashcard → hiển thị các tùy chọn | Đang ở màn hình Flashcard. | 1. Nhấn icon cài đặt (⚙) trên header | Hiện bottom sheet cài đặt hiển thị mặt trước card<br>- Từ vựng<br>- Hán tự<br>- Ngữ pháp<br>nút Lưu thay đôit<br>Mỗi section chọn tối thiểu 1 mục |
| TC-LT-038 | Positive | Medium | Tích chọn hiển thị hết | Đang ở cài đặt Flashcard. | 1. Tích chọn hiện tất cả<br>2. Thoát cài đặt<br>3. Quan sát card | Mặt trước hiển thị tất cả dữ liệu: Từ vựng, phiên âm, nghĩa |
| TC-LT-039 | Positive | Medium | Tắt hiển thị Từ vựng → card không hiển thị Từ vựng ở mặt trước | Đang ở cài đặt Flashcard. Sổ tay có từ vựng. | 1. Tắt 'Hiển thị từ vựng'<br>2. Quay lại Flashcard | Card mặt trước chỉ hiển thị phiên âm và nghĩa, không có từ vựng |

### F. Rating Logic (BR008)

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-040 | Positive | High | Flashcard – Đã thuộc + thời gian < 1.5s → EASY | Đang xem mặt trước card Flashcard. Ghi chú: đồng hồ bắt đầu tính từ khi card hiển thị mặt sau. | 1. Lật card sang mặt sau<br>2. Trong < 1.5 giây, nhấn 'Đã thuộc' | FSRS ghi nhận rating = EASY. Interval tiếp theo tăng theo thuật toán FSRS với Easy rating. |
| TC-LT-041 | Positive | High | Flashcard – Đã thuộc + thời gian ≥ 1.5s → GOOD | Đang xem mặt trước card Flashcard. Ghi chú: đồng hồ bắt đầu tính từ khi card hiển thị mặt sau. | 1. Lật card sang mặt sau<br>2. Chờ ≥ 1.5 giây<br>3. Nhấn 'Đã thuộc' | FSRS ghi nhận rating = GOOD. Interval ngắn hơn Easy nhưng dài hơn Hard. |
| TC-LT-042 | Positive | Medium | Flashcard – Nhấn "Thuộc lờ mờ" (rating HARD) → FSRS ghi nhận HARD | Đang xem mặt sau card Flashcard. | 1. Lật card sang mặt sau<br>2. Nhấn nút "Thuộc lờ mờ" (không nhấn Chưa thuộc hay Đã thuộc) | FSRS ghi nhận rating = HARD. Interval tiếp theo ngắn hơn GOOD, ôn lại sớm hơn. |
| TC-LT-043 | Positive | High | Flashcard – Chưa thuộc → AGAIN (reset) | Đang xem mặt sau card Flashcard. | 1. Nhấn nút 'Chưa thuộc' | FSRS ghi nhận rating = AGAIN. Card bị reset về trạng thái Learning. Interval = 1 ngày hoặc theo config. |
| TC-LT-044 | Positive | High | Quiz/Ôn tập – Trả lời đúng + thời gian < 1.5s → EASY | Đang ở màn Quiz với TK Premium. Câu hỏi đang hiển thị. | 1. Trong < 1.5 giây kể từ khi câu hỏi xuất hiện, nhấn đáp án đúng | FSRS ghi nhận rating = EASY cho từ tương ứng. |
| TC-LT-045 | Positive | High | Quiz/Ôn tập – Trả lời đúng + thời gian ≥ 1.5s → GOOD | Đang ở màn Quiz với TK Premium. | 1. Chờ ≥ 1.5 giây<br>2. Nhấn đáp án đúng | FSRS ghi nhận rating = GOOD. |
| TC-LT-046 | Negative | High | Quiz/Ôn tập – Trả lời sai → AGAIN | Đang ở màn Quiz với TK Premium. | 1. Nhấn vào đáp án sai | FSRS ghi nhận rating = AGAIN. Màn hiển thị đáp án đúng. |
| TC-LT-047 | Positive | Medium | Luyện nói – Phát âm đúng < 1.5s → EASY | Đang ở Luyện nói với TK Premium/Mazii-AI. | 1. Nhấn mic ngay khi từ hiển thị<br>2. Phát âm đúng trong < 1.5s<br>3. Dừng ghi âm | FSRS rating = EASY. Chuyển từ tiếp theo. |
| TC-LT-048 | Positive | Medium | Luyện nói – Phát âm đúng ≥ 1.5s → GOOD | Đang ở Luyện nói với TK Premium/Mazii-AI. | 1. Nhấn mic<br>2. Chờ ≥ 1.5s<br>3. Phát âm đúng<br>4. Dừng ghi âm | FSRS rating = GOOD. |
| TC-LT-049 | Positive | Medium | Viết – Nhập đúng < 1.5s → EASY | Đang ở chế độ Viết (nhập nghĩa) với TK Premium/Mazii-AI.<br>⚠️ Lưu ý: Cần xác nhận với Dev thời điểm bắt đầu tính timer cho dạng Viết (khi câu hỏi hiển thị hay khi ô nhập focus). Threshold 1.5s có thể không thực tế với dạng bài này. | 1. Câu hỏi Viết hiển thị<br>2. Nhập đúng đáp án ngay trong < 1.5 giây kể từ khi câu hỏi xuất hiện<br>3. Nhấn Submit | FSRS rating = EASY. |
| TC-LT-050 | Positive | Medium | FSRS cập nhật sau rating – interval thay đổi theo thuật toán | TK đã đăng nhập. Sổ tay có 1 từ với SRS data: interval = 7 ngày. | 1. Flashcard từ đó<br>2. Đánh giá Easy<br>3. Xem lại SRS data của từ | SRS data cập nhật: interval mới > 7 ngày, stability tăng. Next review date thay đổi theo FSRS. |

### G. Quiz – Điều kiện & Quyền

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-051 | Positive | High | Mở Quiz thành công – TK Premium, ≥4 items, SRS OFF | TK Premium. Sổ tay ≥4 items. Chế độ lặp lại = TẮT. | 1. Luyện tập → Quiz | Điều hướng đến màn Tùy chọn Quiz thành công. |
| TC-LT-052 | Positive | High | Mở Quiz thành công – TK Mazii-AI, ≥4 items | TK Mazii-AI. Sổ tay ≥4 items. | 1. Luyện tập → Quiz | Điều hướng đến màn Tùy chọn Quiz thành công. |
| TC-LT-053 | Negative | High | Mở Quiz – TK Free → paywall (BR001, UC-018 A1) | TK Thường (Free). Sổ tay ≥4 items. | 1. Luyện tập → Quiz | Màn paywall/upgrade. Không vào được Quiz. |
| TC-LT-054 | Negative | High | Mở Quiz – TK Khách → paywall | TK Khách (Guest). | 1. Luyện tập → Quiz | Màn paywall. Không vào được Quiz. |
| TC-LT-055 | Negative | High | Mở Quiz – Premium, < 4 items → MSG_L_15 (UC-018 A2) | TK Premium. Sổ tay 3 items. | 1. Chọn sổ tay 3 items → Luyện tập → Quiz | MSG_L_15: 'Cần ít nhất 4 mục'. Không mở Quiz. |
| TC-LT-056 | Boundary | Medium | Mở Quiz – đúng 4 items (biên dưới) → thành công | TK Premium. Sổ tay đúng 4 items. SRS OFF. | 1. Chọn sổ tay 4 items → Luyện tập → Quiz | Vào được Quiz. Không hiện lỗi. |
| TC-LT-057 | Negative | High | Mở Quiz – SRS ON, không có items đến hạn → MSG_L_16 (UC-018 A3) | TK Premium. Sổ tay ≥4 items nhưng không có SRS due hôm nay. Chế độ lặp lại = BẬT. | 1. Chọn sổ tay → Luyện tập → Quiz | MSG_L_16: 'Không có mục nào cần ôn tập hôm nay'. |
| TC-LT-058 | Negative | High | Mở Quiz – TK Premium, sổ tay < 4 items, SRS OFF → MSG_L_15 | TK Premium. Sổ tay 3 items. SRS OFF (Chế độ lặp lại = TẮT). | 1. Chọn sổ tay 3 items<br>2. Vào Luyện tập → Quiz | MSG_L_15: 'Cần ít nhất 4 mục trong sổ tay để sử dụng tính năng này'. Không mở được Quiz.<br><br>⚠️ Lưu ý: Khi SRS OFF, hệ thống kiểm tra số lượng items tối thiểu (≥4), không phải SRS-due items. Vì vậy Expected là MSG_L_15, không phải MSG_L_16. |
| TC-LT-059 | Positive | High | Quiz – sổ tay >25 items, SRS ON → giới hạn tối đa 25 câu hỏi | TK Premium. Sổ tay 26 items. Chế độ lặp lại = BẬT (SRS ON). Trong đó ≥25 items có SRS due date = hôm nay (để đảm bảo số câu vượt ngưỡng 25). | 1. Chọn sổ tay → Luyện tập → Quiz | Bài Quiz hiện max 25 câu |
| TC-LT-060 | Positive | High | Quiz – sổ tay >25 items, SRS OFF → hiển thị toàn bộ items | TK Premium. Sổ tay 26 items. SRS OFF (Chế độ lặp lại = TẮT). Màn Tùy chọn Quiz: Số câu hỏi = mặc định (không giới hạn / All) hoặc ≥ 26. | 1. Tắt SRS (Cài đặt → tắt Chế độ lặp lại)<br>2. Chọn sổ tay → Luyện tập → Quiz | Bài Quiz hiện đủ 26 câu |

### H. Quiz – UI Tùy chọn

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-061 | Positive | High | Màn Tùy chọn Quiz: hiển thị 6 dạng bài + cài đặt Âm thanh + Số câu | TK Premium. Vừa nhấn Quiz từ sổ tay ≥4 items. | 1. Quan sát màn Tùy chọn Quiz | Hiện bottom sheet "Tùy chọn Quiz"<br>Mô tả "Tuỳ chỉnh dạng câu hỏi, số lượng và <br>cách luyện tập theo mục tiêu của bạn."<br>Chọn dạng bài: 6 dạng bài với toggle bật/tắt: Bài chọn, Nghe, Luyện viết (2 dạng), Điền vào chỗ trống, Hoàn thành câu, Nối. <br>Cài đặt thêm: Âm thanh (bật/tắt), Số câu hỏi (slider/dropdown). |
| TC-LT-062 | Positive | High | Bật/tắt dạng bài → chỉ câu hỏi thuộc dạng đã bật xuất hiện | Đang ở Tùy chọn Quiz. | 1. Tắt tất cả trừ 'Bài chọn'<br>2. Bắt đầu Quiz<br>3. Quan sát loại câu hỏi | Tất cả câu hỏi chỉ thuộc dạng Bài chọn. Không xuất hiện dạng khác. |
| TC-LT-063 | Positive | Medium | Cài đặt Số câu hỏi = 10 → bài Quiz có đúng 10 câu | TK Premium. Sổ tay có ≥10 items. Đang ở màn Tùy chọn Quiz. Giá trị Số câu hỏi mặc định hiển thị. | 1. Đặt số câu = 10<br>2. Bắt đầu Quiz<br>3. Đếm số câu | Bài Quiz có đúng 10 câu. Sau câu 10 → màn kết quả. |
| TC-LT-064 | Positive | Low | Cài đặt Âm thanh tắt → Quiz không phát âm khi chọn đáp án | Đang ở Tùy chọn Quiz. Âm thanh đang BẬT. | 1. Tắt Âm thanh<br>2. Bắt đầu Quiz<br>3. Chọn đáp án đúng và sai | Không có âm thanh phát ra khi chọn đáp án. |
| TC-LT-065 | Negative | Medium | Tắt tất cả 6 dạng bài -> hiện msg "Vui lòng chọn ít nhất 1 dạng bài để bắt đầu" | Đang ở Tùy chọn Quiz. | 1. Tắt toggle tất cả 6 dạng bài | hiện msg "Vui lòng chọn ít nhất 1 dạng bài để bắt đầu" |

### I. Quiz – Dạng Bài chọn

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-066 | Positive | High | Bài chọn: hiển thị câu hỏi và 4 lựa chọn | TK Premium. Đang trong Quiz dạng Bài chọn. | 1. Quan sát màn câu hỏi dạng Bài chọn | Header: Nút back <<br>Text "Quiz-tiến trình câu 01/25"<br>Nội dung: Câu hỏi ở trên (từ/nghĩa/kanji). 4 lựa chọn dạng button bên dưới. Mỗi lựa chọn rõ ràng, không trùng. |
| TC-LT-067 | Positive | High | Bài chọn: chọn đúng → highlight xanh, ghi điểm | Đang ở câu hỏi Bài chọn. | 1. Nhấn vào đáp án đúng<br>2. Nhấn Kiểm tra | 1. Đáp án được chọn highlight xanh dương<br>2. Đáp án đúng highlight xanh lá. Âm thanh 'đúng' (nếu bật).Hiện button Tiếp tục để chuyên câu |
| TC-LT-068 | Negative | High | Bài chọn: chọn sai → highlight đỏ, hiện đáp án đúng | Đang ở câu hỏi Bài chọn. | 1. Nhấn vào đáp án sai<br>2. Nhấn Kiểm tra | 1. Đáp án được chọn highlight xanh dương<br>2. Đáp án sai highlight đỏ. Âm thanh 'sai' (nếu bật).<br>Hiện hyperlink "Xem đáp án đúng" và nút Tiếp tục |
| TC-LT-069 | Positive | Medium | Bài chọn: Nhấn "Xem đáp án đúng" | Đang ở Bài chọn. Đã chọn sai | 1. Nhấn "Xem đáp án đúng" | 1. Đáp án đúng highlight xanh lá |
| TC-LT-070 | Positive | Medium | Bài chọn: Nhấn "Tiếp tục" chuyển sang câu tiếp theo | Đang ở Bài chọn. Đã hiện kết quả | 1. Nhấn "Tiếp tục" | 1. Chuyển sang câu tiếp theo |

### J. Quiz – Dạng Nghe

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-071 | Positive | High | Dạng Nghe: hiển thị nút Play audio và 4 lựa chọn (không hiện từ) | TK Premium. Đang trong Quiz dạng Nghe. | 1. Quan sát màn câu hỏi dạng Nghe | Nút Play (▶) ở trên, 4 lựa chọn bên dưới. Không hiển thị từ tiếng Nhật (chỉ nghe audio). |
| TC-LT-072 | Positive | High | Dạng Nghe: nhấn Play → phát âm từ; nhấn lại → phát lại | Đang ở câu hỏi Nghe. | 1. Nhấn Play<br>2. Nghe xong, nhấn Play lần 2 | Lần 1: audio phát. Lần 2: audio phát lại. Play được nhấn nhiều lần trước khi chọn đáp án. |
| TC-LT-073 | Positive | High | Dạng Nghe: nghe và chọn đúng → đáp án đúng xanh | Đang ở câu hỏi Nghe. | 1. Nhấn Play nghe từ<br>2. Chọn đáp án đúng | 1. Đáp án được chọn highlight xanh dương<br>2. Đáp án đúng highlight xanh lá. Âm thanh 'đúng' (nếu bật).Hiện button Tiếp tục để chuyên câu |
| TC-LT-074 | Negative | High | Dạng Nghe: chọn sai → đỏ, hiện đáp án đúng | Đang ở câu hỏi Nghe. | 1. Nhấn Play<br>2. Chọn đáp án sai | 1. Đáp án được chọn highlight xanh dương<br>2. Đáp án sai highlight đỏ. Âm thanh 'sai' (nếu bật).<br>Hiện hyperlink "Xem đáp án đúng" và nút Tiếp tục |
| TC-LT-075 | Positive | High | Dạng nghe: Nhấn "Xem đáp án đúng" | Đang ở câu hỏi Nghe.. Đã chọn sai | 1. Nhấn "Xem đáp án đúng" | 1. Đáp án đúng highlight xanh lá |
| TC-LT-076 | Positive | Medium | Dạng nghe: Nhấn "Tiếp tục" chuyển sang câu tiếp theo | Đang ở Dạng nghe Đã hiện kết quả | 1. Nhấn "Tiếp tục" | 1. Chuyển sang câu tiếp theo |
| TC-LT-077 | Edge Case | Low | Dạng Nghe: chưa Play trước khi chọn → vẫn cho phép (không bắt buộc nghe) | Đang ở câu hỏi Nghe. Chưa nhấn Play. | 1. Không nhấn Play<br>2. Nhấn trực tiếp vào một đáp án | Hệ thống cho phép chọn đáp án mà không cần nghe trước. Đáp án được ghi nhận và hiển thị kết quả đúng/sai bình thường. Không có thông báo bắt buộc nghe. App không crash. |

### K. Quiz – Dạng Luyện viết

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-078 | Positive | High | Luyện viết dạng 1 – Vẽ chữ: hiển thị stroke guide, người dùng vẽ theo nét | TK Premium. Đang trong Quiz dạng Luyện viết (vẽ chữ). Card hiển thị từ tiếng Nhật + nghĩa. | 1. Quan sát màn hình: Kanji/Kana mờ làm nền (stroke guide)<br>2. Dùng ngón tay vẽ theo nét hướng dẫn<br>3. Nhấn 'Kiểm tra' | Stroke guide hiển thị rõ. Vẽ đúng hướng nét → kết quả Đúng. Nút 'Bỏ qua' cho phép bỏ qua và chuyển tiếp. |
| TC-LT-079 | Positive | Medium | Luyện viết dạng 1 – Vẽ chữ: nhấn Bỏ qua → bỏ từ, chuyển tiếp | Đang ở câu Luyện viết (vẽ chữ). Chưa vẽ. | 1. Nhấn nút 'Bỏ qua' | Từ bị bỏ qua. App chuyển sang câu tiếp theo. Không tính điểm cho từ này. |
| TC-LT-132 | Negative | High | Luyện viết dạng 1 – Vẽ chữ sai nét → overlay Đáp án Sai, hiển thị stroke guide đúng | TK Premium. Đang ở câu Luyện viết dạng Vẽ chữ (stroke order). Màn hiển thị canvas vẽ và stroke guide. | 1. Vẽ chữ sai nét (sai stroke order hoặc sai hình dạng)<br>2. Nhấn "Kiểm tra" | Overlay "Đáp án Sai" (đỏ, icon ✗) hiển thị. Stroke guide đúng hiện rõ để người dùng tham khảo. Nút "Tiếp tục" cho phép chuyển sang câu tiếp theo. |
| TC-LT-080 | Positive | High | Luyện viết dạng 2 – Nhập nghĩa: hiển thị từ JP, người dùng nhập nghĩa/đọc rồi kiểm tra | TK Premium. Đang trong Quiz dạng Luyện viết (nhập nghĩa). Ví dụ: màn hiển thị 'さんしずい' yêu cầu nhập đáp án. | 1. Đọc từ tiếng Nhật hiển thị<br>2. Nhập nghĩa/cách đọc vào ô nhập<br>3. Nhấn 'Kiểm tra' | Ô nhập hiện với bàn phím. Có nút 'Không biết đáp án?'. Nhập đúng → overlay 'Đáp án Đúng' (xanh, icon ✓) + nút Tiếp tục. |
| TC-LT-081 | Negative | High | Luyện viết dạng 2 – Nhập nghĩa sai → overlay 'Đáp án Sai', hiện đáp án đúng | Đang ở câu Luyện viết (nhập nghĩa). | 1. Nhập sai nghĩa/đọc<br>2. Nhấn 'Kiểm tra' | Overlay 'Đáp án Sai' (đỏ, icon ✗). Hiển thị đáp án đúng. Nút 'Tiếp tục' để sang câu tiếp. |
| TC-LT-082 | Positive | High | Luyện viết dạng 2- Nhấn "Không biết đáp án" > Hiện đáp án đúng | TK Premium. Đang trong Quiz dạng Luyện viết (nhập nghĩa). Ví dụ: màn hiển thị 'さんしずい' yêu cầu nhập đáp án. | 1. Nhấn "Không biết đáp án" | Hiển thị đáp án đúng kèm btn Sao chép.<br>FSRS ghi nhận rating = AGAIN (tương đương trả lời sai) cho từ hiện tại.<br>⚠️ Cần xác nhận với BA: "Không biết đáp án" có được tính là AGAIN hay Skip (không cập nhật SRS). |

### L. Quiz – Dạng Điền chỗ trống

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-084 | Positive | High | Điền chỗ trống: hiển thị câu có ___ và 4 lựa chọn | TK Premium. Đang trong Quiz dạng Điền chỗ trống. | 1. Quan sát màn câu hỏi | Câu tiếng Nhật với 1 vị trí trống (___). 4 lựa chọn từ để điền. |
| TC-LT-085 | Positive | High | Điền chỗ trống: chọn đúng → câu hoàn chỉnh, ghi đúng | Đang ở câu hỏi Điền chỗ trống. | 1. Đọc câu ví dụ<br>2. Chọn từ đúng để điền vào<br>3. Nhấn Kiểm tra | Từ điền vào vị trí trống. Câu hoàn chỉnh hiển thị. Ghi nhận đúng. |
| TC-LT-086 | Negative | High | Điền chỗ trống: chọn sai → highlight đỏ, hiện câu đúng | Đang ở câu hỏi Điền chỗ trống. | 1. Chọn từ sai để điền<br>2. Nhấn Kiểm tra | Lựa chọn sai highlight đỏ. Hiện hyperlink "Xem đáp án đúng" |
| TC-LT-087 | Positive | High | Điền chỗ trống: chọn sai → nhấn "Xem đáp án đúng" | Chọn sai | 1. Nhấn "Xem đáp án đúng" | Đáp án đúng higlight xanh, sai đỏ. Nội dung kết quả hiện đáp án đúng |
| TC-LT-088 | Positive | High | Điền chỗ trống: Nhấn Tiếp tục chuyển cau | Hiện kết quả | 1. Nhấn Tiếp tục | Chuyển câu tiếp theo |

### M. Quiz – Dạng Hoàn thành câu

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-089 | Positive | High | Hoàn thành câu: hiển thị nghĩa VN và các từ JP rời để sắp xếp | TK Premium. Đang trong Quiz dạng Hoàn thành câu. | 1. Quan sát màn câu hỏi | Nghĩa tiếng Việt ở trên. Các từ JP rời dạng chip/button bên dưới để sắp xếp thành câu đúng.<br>Không hiện nút Kiểm tra |
| TC-LT-090 | Positive | High | Hoàn thành câu: sắp xếp đúng thứ tự → câu đúng | Đang ở câu hỏi Hoàn thành câu. | 1. Nhấn các từ theo thứ tự đúng<br>2. Submit/Kiểm tra | 1. Câu hình thành đúng thứ tự. Hiện nút Kiểm tra<br>2. Ghi nhận đúng. |
| TC-LT-091 | Negative | High | Hoàn thành câu: sắp xếp sai → hiển thị câu đúng | Đang ở câu hỏi Hoàn thành câu. | 1. Sắp xếp từ sai thứ tự<br>2. Submit | Các từ sắp xếp sai thứ tự.<br>Sau khi nhấn Kiểm tra:<br>- Câu sắp xếp sai được hiển thị highlight đỏ / chỉ ra lỗi.<br>- Hiện hyperlink "Xem đáp án đúng" để người dùng xem thứ tự đúng.<br>- Nút "Tiếp tục" xuất hiện để chuyển sang câu tiếp theo.<br>- FSRS ghi nhận rating AGAIN cho từ liên quan. |
| TC-LT-092 | Positive | Medium | Hoàn thành câu: Nhấn "Xem đáp án đúng" | Đang ở câu hỏi Hoàn thành câu. Đã sắp xếp sai thứ tự từ. Đã nhấn Kiểm tra. | 1. Nhấn "Xem đáp án đúng" | 1. Đáp án đúng highlight xanh lá |
| TC-LT-093 | Positive | Medium | Hoàn thành câu: Nhấn "Tiếp tục" chuyển sang câu tiếp theo | Đang ở Dạng Hoàn thành câu. Đã hiện kết quả (đúng hoặc sai). | 1. Nhấn "Tiếp tục" | 1. Chuyển sang câu tiếp theo |
| TC-LT-094 | Positive | Medium | Hoàn thành câu: nhấn từ đã chọn → xóa khỏi câu, trả về pool | Đang ở Hoàn thành câu. Đã đặt 2 từ vào câu. | 1. Nhấn vào 1 từ đã đặt trong câu | Từ bị xóa khỏi câu, trả về pool bên dưới. Câu chỉ còn 1 từ. |

### N. Quiz – Dạng Nối

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-095 | Positive | High | Dạng Nối: hiển thị 2 cột từ và nghĩa cần ghép cặp | TK Premium. Đang trong Quiz dạng Nối. | 1. Quan sát màn câu hỏi Nối | 2 cột: trái (từ tiếng Nhật), phải (nghĩa tiếng Việt). Mỗi cột 4–6 mục. Đường nối xuất hiện khi chọn. |
| TC-LT-096 | Positive | High | Dạng Nối: nối đúng tất cả cặp → kết quả đúng 100% | Đang ở câu hỏi Nối. | 1. Nhấn từ bên trái<br>2. Nhấn nghĩa đúng bên phải<br>3. Lặp lại cho đến hết | Mỗi cặp đúng: đường nối màu xanh. sau đó disable<br>Hoàn thành tất cả đúng → hiện nút Tiếp tục |
| TC-LT-097 | Negative | Medium | Dạng Nối: nối sai → đường nối đỏ, có thể sửa | Đang ở câu hỏi Nối. | 1. Nhấn từ A<br>2. Nhấn nghĩa sai | Đường nối sai màu đỏ. Người dùng chọn lại |
| TC-LT-098 | Positive | Medium | Dạng nối: Nhấn "Tiếp tục" chuyển sang câu tiếp theo | Đang ở Dạng Nối. Đã nối xong tất cả cặp. Đã hiện kết quả. | 1. Nhấn "Tiếp tục" | 1. Chuyển sang câu tiếp theo |

### O. Quiz – Màn kết quả

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-099 | Positive | High | Kết quả Quiz – 100% đúng → màn 'Tuyệt vời!' | TK Premium. Vừa hoàn thành Quiz với tất cả câu trả lời đúng (100%). | 1. Hoàn thành câu cuối<br>2. Quan sát màn kết quả | Hiển thị tiêu đề 'Tuyệt vời!' + thông điệp khích lệ + số câu đúng (VD: 50/50). Hai nút: 'Thoát' và 'Thử lại'. |
| TC-LT-100 | Positive | High | Kết quả Quiz – 70% ≤ đúng < 100% → màn 'Làm rất tốt!' | TK Premium. Vừa hoàn thành Quiz, tỉ lệ đúng trong khoảng 70–99%. | 1. Hoàn thành Quiz với ~70–99% câu đúng<br>2. Quan sát màn kết quả | Hiển thị tiêu đề 'Làm rất tốt!' + thông điệp khuyến khích luyện thêm + số câu đúng. Hai nút: 'Thoát' và 'Thử lại'. |
| TC-LT-101 | Positive | High | Kết quả Quiz – 50% ≤ đúng < 70% → màn 'Bạn đang tiến bộ.' | TK Premium. Vừa hoàn thành Quiz, tỉ lệ đúng trong khoảng 50–69%. | 1. Hoàn thành Quiz với ~50–69% câu đúng<br>2. Quan sát màn kết quả | Hiển thị tiêu đề 'Bạn đang tiến bộ.' + thông điệp nhắc luyện thêm + số câu đúng. Hai nút: 'Thoát' và 'Thử lại'. |
| TC-LT-102 | Positive | High | Kết quả Quiz – đúng < 50% → màn 'Hoàn thành luyện tập' (kết quả không tốt) | TK Premium. Vừa hoàn thành Quiz, tỉ lệ đúng dưới 50%. | 1. Hoàn thành Quiz với < 50% câu đúng<br>2. Quan sát màn kết quả | Hiển thị tiêu đề 'Hoàn thành luyện tập' + thông điệp động viên + số câu đúng thấp, thanh tiến độ màu đỏ. Hai nút: 'Thoát' và 'Thử lại'. |
| TC-LT-103 | Positive | High | Nút Thử lại → reset về đầu Quiz, làm lại toàn bộ câu hỏi | Đang ở màn kết quả Quiz (bất kỳ tỉ lệ nào). | 1. Nhấn nút 'Thử lại' | App reset phiên Quiz hiện tại: về lại câu đầu tiên, điểm = 0, tất cả câu đánh dấu chưa làm. KHÔNG có màn 'Xem giải thích'. |
| TC-LT-104 | Positive | Medium | Nút Thoát → thoát khỏi Quiz, quay về màn chi tiết sổ tay | Đang ở màn kết quả Quiz. | 1. Nhấn nút 'Thoát' | App thoát khỏi phiên Quiz. Quay về màn chi tiết sổ tay |

### P. Luyện nói

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-105 | Positive | High | Mở Luyện nói – TK Premium, ≥4 items → thành công | TK Premium. Sổ tay ≥4 items. SRS OFF. | 1. Luyện tập → Luyện nói | Màn Luyện nói mở. |
| TC-LT-106 | Positive | High | Mở Luyện nói – TK Mazii-AI, ≥4 items → thành công | TK Mazii-AI. Sổ tay ≥4 items. | 1. Luyện tập → Luyện nói | Màn Luyện nói mở. |
| TC-LT-107 | Negative | High | Mở Luyện nói – TK Free → paywall | TK Thường (Free). | 1. Luyện tập → Luyện nói | Màn paywall/upgrade. Không vào được Luyện nói. |
| TC-LT-108 | Negative | High | Mở Luyện nói – Premium, < 4 items → MSG_L_15 | TK Premium. Sổ tay 3 items. | 1. Chọn sổ tay 3 items → Luyện tập → Luyện nói | MSG_L_15. Không mở Luyện nói. |
| TC-LT-109 | Positive | Medium | UI Luyện nói: hiển thị từ, nút Mic, nút Bỏ qua | Đang ở màn Luyện nói. | 1. Quan sát UI | Header: nút back <<br>Tiêu đề: Luyện nói - tiến trình câu<br>Hiển thị từ/câu cần đọc (câu tiếng nhật nghĩa), <br>nút mic (🎤<br>nút Bỏ qua |
| TC-LT-110 | Positive | High | Nhấn Mic → ghi âm, phát âm đúng → kết quả đúng | Đang ở Luyện nói với TK Premium/Mazii-AI. Mic được cấp phép. | 1. Nhấn Mic<br>2. Đọc từ/câu đúng<br>3. Dừng ghi âm | AI nhận diện đúng. Kết quả 'Đúng' hoặc điểm cao. FSRS cập nhật. |
| TC-LT-111 | Negative | High | Nhấn Mic → phát âm sai → kết quả sai, FSRS Again | Đang ở Luyện nói. | 1. Nhấn Mic<br>2. Đọc sai từ/câu<br>3. Dừng ghi âm | AI nhận diện sai. Hiện nút "Nói lại" |
| TC-LT-112 | Positive | High | Phát âm sai → bấm (Nói lại) → quay lại màn ban đầu của từ để thử lại | Đang ở Luyện nói. Vừa phát âm sai | 1. Quan sát kết quả<br>2. Nhấn "Nói lại" | App quay lại màn ban đầu của từ hiện tại (Nói lại từ đó). Người dùng có thể nhấn Mic thêm lần nữa để thử phát âm lại. Không chuyển sang từ mới. |
| TC-LT-113 | Positive | Medium | Nhấn Bỏ qua → bỏ từ hiện tại, chuyển từ tiếp | Đang ở Luyện nói. | 1. Nhấn nút 'Bỏ qua' | Từ hiện tại bị bỏ qua. Chuyển từ tiếp theo. Không cộng điểm cho từ này. |
| TC-LT-114 | Positive | Medium | Nhấn Tiếp tục -> chuyển câu tiếp | Đang ở Luyện nói. | 1. Nhấn nút 'Tiếp tục' | Chuyển sang câu tiếp theo |
| TC-LT-115 | Positive | High | Hoàn thành Luyện nói → màn kết quả với điểm phát âm | Vừa hoàn thành từ cuối ở Luyện nói. | 1. Hoàn thành tất cả từ | Màn kết quả: số từ đúng/sai/bỏ qua, điểm phát âm trung bình. |
| TC-LT-116 | Negative | High | Mở Luyện nói – SRS ON, không có items đến hạn → MSG_L_16 | TK Premium. Sổ tay ≥4 items nhưng không có SRS due. Chế độ lặp lại = BẬT. | 1. Luyện tập → Luyện nói | MSG_L_16. Không mở Luyện nói. |

### S. Mini test – UI & Thao tác

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-117 | Positive | High | UI Mini test: 2 vùng scroll – vùng câu hỏi và vùng danh sách đáp án hiển thị tách biệt | TK Premium. Đang trong bài Mini test (ví dụ N3). Câu hỏi có đoạn văn dài + nhiều câu con (1.1, 1.2…). | 1. Mở bài Mini test<br>2. Quan sát bố cục màn hình | Màn chia 2 vùng: Vùng trên = nội dung câu hỏi (đoạn văn/đề bài), Vùng dưới = danh sách câu con + đáp án. Giữa 2 vùng có thanh phân cách (divider). |
| TC-LT-118 | Positive | High | Scroll vùng câu hỏi độc lập – vùng đáp án không bị ảnh hưởng | Đang trong Mini test. Vùng câu hỏi có nội dung dài hơn chiều cao vùng. | 1. Đặt ngón tay vào vùng câu hỏi (phía trên thanh phân cách)<br>2. Vuốt lên/xuống để scroll | Chỉ vùng câu hỏi scroll. Vùng danh sách đáp án bên dưới vẫn giữ nguyên vị trí. Có thể đọc toàn bộ đề bài. |
| TC-LT-119 | Positive | High | Scroll vùng đáp án độc lập – vùng câu hỏi không bị ảnh hưởng | Đang trong Mini test. Danh sách câu con (1.1, 1.2, …) dài hơn chiều cao vùng đáp án. | 1. Đặt ngón tay vào vùng đáp án (phía dưới thanh phân cách)<br>2. Vuốt lên/xuống để scroll | Chỉ vùng đáp án scroll. Vùng câu hỏi phía trên vẫn giữ nguyên. Có thể xem toàn bộ các câu con. |
| TC-LT-120 | Positive | Medium | Kéo thanh phân cách lên → vùng đáp án rộng hơn, vùng câu hỏi thu nhỏ | Đang trong Mini test. Thanh phân cách giữa 2 vùng đang ở vị trí mặc định (70/30 hoặc tương tự). | 1. Nhấn và kéo thanh phân cách lên phía trên | Vùng đáp án (bên dưới) mở rộng. Vùng câu hỏi (bên trên) thu nhỏ. Cả 2 vùng vẫn scroll được. Tỉ lệ thay đổi theo vị trí thả. |
| TC-LT-121 | Positive | Medium | Kéo thanh phân cách xuống → vùng câu hỏi rộng hơn, vùng đáp án thu nhỏ | Đang trong Mini test. Thanh phân cách ở vị trí mặc định. | 1. Nhấn và kéo thanh phân cách xuống phía dưới | Vùng câu hỏi (bên trên) mở rộng. Vùng đáp án (bên dưới) thu nhỏ. Cả 2 vùng vẫn scroll được. |
| TC-LT-122 | Positive | Medium | Vuốt ngang trái → quay về câu trước, đáp án cũ vẫn giữ | Đang ở câu hỏi thứ 3. | 1. Vuốt ngang sang trái | App quay về câu 2. Đáp án đã chọn ở câu 2 vẫn được giữ. |
| TC-LT-123 | Positive | High | Popup nộp bài giữa chừng → xác nhận trước khi nộp | Đang làm Mini test, mới trả lời 5/20 câu. | 1. Nhấn nút 'Nộp bài' | Popup xác nhận hiển thị:<br>Tiêu đề: 'Bạn chắc chắn muốn nộp bài?'<br>Nội dung: 'Vẫn còn nhiều câu hỏi đang chờ bạn.'<br>Hai nút: [Hủy] và [Xác nhận]<br><br>⚠️ Lưu ý: Cần verify lại copy text chính xác với Designer. Nếu UI hiển thị sai chính tả, cần log bug riêng. |
| TC-LT-124 | Positive | High | Nhấn Xác nhận > hiện màn kết quả | Hiện popup xác nhận | 1. Nhấn Xác nhận | 1. Hiện màn Kết quả |
| TC-LT-125 | Positive | Medium | Nhấn Hủy > Tiếp tục làm bài | Hiện popup xác nhận | 1. Nhấn Hủy | 1. tắt popup, tiếp tục làm bài |
| TC-LT-126 | Positive | High | Popup Back giữa chừng → cảnh báo trước khi thoát | Đang làm Mini test, chưa hoàn thành. | 1. Nhấn nút Back (←) header | Popup cảnh báo hiển thị:<br>Tiêu đề: 'Bài test chưa hoàn thành'<br>Nội dung: 'Bạn chưa làm xong bài test này. Bạn có chắc chắn muốn thoát không?'<br>Hai nút: [Thoát] và [Tiếp tục làm bài]<br><br>⚠️ Lưu ý: Cần verify lại copy text chính xác với Designer. |
| TC-LT-127 | Positive | High | Hoàn thành bài thi → màn kết quả tổng hợp | Vừa hoàn thành Mini test. | 1. Hoàn thành câu cuối<br>2. Nhấn Nộp bài | Màn kết quả: tổng điểm, số đúng/sai, % đúng. 'Thử lại'. |

### T. Xem đáp án & Giải nghĩa

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-128 | Positive | High | Xem đúng/sai từng câu: icon ✓/✗ và màu đỏ/xanh chính xác | Người dùng đã hoàn thành bài thi/quiz. Đang ở màn kết quả. | 1. Nhấn 'Xem đáp án'<br>2. Scroll qua danh sách câu hỏi | Mỗi câu: câu hỏi, đáp án người dùng chọn (đỏ nếu sai), đáp án đúng (xanh). Câu đúng icon ✓, sai icon ✗. |
| TC-LT-129 | Positive | High | Premium xem giải nghĩa sau thi/quiz → đầy đủ chi tiết (UC-020) | TK Premium. Đang ở màn kết quả Quiz/Mini test. | 1. Nhấn 1 câu kết quả<br>2. Nhấn Giải nghĩa | 1. Giải nghĩa đầy đủ: đáp án đúng màu xanh, sai màu đỏ, Button giải nghĩa<br>2. Hiện giải nghĩa chi tiết |

### U. Cài đặt SRS

| ID | Type | Priority | Title | Precondition & Test Data | Steps | Expected Result |
|----|------|----------|-------|--------------------------|-------|-----------------|
| TC-LT-130 | Positive | Medium | Bật 'Chế độ lặp lại' → chỉ lấy items SRS due hôm nay | Sổ tay 20 items, 5 items SRS due hôm nay. | 1. Cài đặt → Bật 'Chế độ lặp lại ngày hôm nay'<br>2. Mở Flashcard | Flashcard chỉ hiển thị 5 items. Progress '1/5'. |
| TC-LT-131 | Positive | Medium | Tắt 'Chế độ lặp lại' → toàn bộ sổ tay | Sổ tay 20 items, 5 SRS due. | 1. Tắt 'Chế độ lặp lại'<br>2. Mở Flashcard | Flashcard hiển thị 20 items. Progress '1/20'. |

---

## 📝 Changelog v4 → v5

| Severity | TC ID | Thay đổi |
|----------|-------|----------|
| 🔴 Critical | TC-LT-001 | Sửa Title (bỏ mâu thuẫn SRS OFF), bổ sung Pre: SRS BẬT |
| 🔴 Critical | TC-LT-058 | Sửa Title, Pre (TK Premium), Expected (MSG_L_15 không phải MSG_L_16) |
| 🟠 High | TC-LT-002/003 | Thay "Thi thử" → "Mini test" trong Expected |
| 🟠 High | TC-LT-010 | Sửa Title khớp Expected (bỏ "Tùy chọn Quiz") |
| 🟠 High | TC-LT-021 | Cập nhật Expected card Ngữ pháp đúng cấu trúc |
| 🟠 High | TC-LT-023 | Sửa "Không thuộc" → "Chưa thuộc" trong Title |
| 🟠 High | TC-LT-026 | Bổ sung Expected chi tiết mặt sau card |
| 🟠 High | TC-LT-042 | Sửa "(bỏ qua không rating)" → "(rating HARD)" |
| 🟠 High | TC-LT-059 | Bổ sung số items SRS-due vào Precondition |
| 🟠 High | TC-LT-091 | Bổ sung Expected: highlight, Xem đáp án, FSRS AGAIN |
| 🟡 Medium | TC-LT-049 | Bổ sung note timer clarification vào Pre + Steps |
| 🟡 Medium | TC-LT-060 | Bổ sung config Số câu vào Precondition |
| 🟡 Medium | TC-LT-082 | Bổ sung FSRS AGAIN impact vào Expected |
| 🟢 Low | TC-LT-039 | Sửa typo "Từ vựngg" → "Từ vựng" |
| 🟢 Low | TC-LT-102 | Đổi Type "Negative" → "Positive" |
| 🟢 Low | TC-LT-123/126 | Sửa typo popup text + thêm note verify Designer |
