# TEST CASES - CHỨC NĂNG ĐĂNG NHẬP MAZII
**Website:** Mazii (beta.mazii.net)  
**Module:** Login  
**URL:** https://beta.mazii.net/vi-VN/user/login  
**Ngày tạo:** 2026-05-24  
**Phiên bản:** 1.0  

---

## TÓMO TẮT TEST CASES

| Loại Path | Số lượng TC | Priority |
|-----------|------------|----------|
| Happy Path (Luồng chính) | 4 TC | Critical / High |
| Negative Path (Dữ liệu sai) | 9 TC | High / Medium |
| Boundary & Edge Cases | 7 TC | Medium / Low |
| **TỔNG CỘNG** | **20 TC** | - |

---

## BẢNG TEST CASES CHI TIẾT

| TC ID | Module | Test Scenario | Pre-Condition | Test Steps | Test Data | Expected Result | Priority |
|-------|--------|---------------|---------------|-----------|----------|-----------------|----------|
| MAZII_LOGIN_TC_001 | Login | Đăng nhập thành công với Email & Password hợp lệ | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email vào trường "Email"<br>2. Nhập password vào trường "Mật khẩu"<br>3. Hoàn thành reCAPTCHA<br>4. Click nút "Đăng nhập" | Email: user_test_01@gmail.com<br>Password: Test@123456 | 1. Form được gửi thành công<br>2. Button vào trạng thái loading<br>3. Redirect tới trang chủ /vi-VN<br>4. User đã authenticated, có thể truy cập các tính năng | Critical |
| MAZII_LOGIN_TC_002 | Login | Đăng nhập thành công với "Ghi nhớ tôi" được check | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user_test_01@gmail.com<br>2. Nhập password: Test@123456<br>3. Tick checkbox "Ghi nhớ tôi"<br>4. Hoàn thành reCAPTCHA<br>5. Click "Đăng nhập"<br>6. Đóng trình duyệt hoàn toàn<br>7. Mở lại trình duyệt và truy cập beta.mazii.net | Email: user_test_01@gmail.com<br>Password: Test@123456 | 1. Đăng nhập thành công<br>2. Session được lưu<br>3. Mở lại trang, user vẫn authenticated (không cần login lại) | High |
| MAZII_LOGIN_TC_003 | Login | Đăng nhập thành công với Google OAuth | Trình duyệt mở URL /vi-VN/user/login, tài khoản Google hợp lệ | 1. Click nút "Google"<br>2. Chuyển hướng tới Google login page<br>3. Nhập email Google: testuser_mazii@gmail.com<br>4. Nhập password Google<br>5. Hoàn thành xác thực Google<br>6. Chấp nhận permission khi được yêu cầu | Google Email: testuser_mazii@gmail.com<br>Google Password: GoogleTest@2026 | 1. Google auth thành công<br>2. Tài khoản Mazii được tạo/liên kết với Google<br>3. Redirect tới trang chủ /vi-VN<br>4. User authenticated | High |
| MAZII_LOGIN_TC_004 | Login | Đăng nhập thành công với Apple OAuth | Trình duyệt mở URL /vi-VN/user/login, tài khoản Apple hợp lệ | 1. Click nút "Apple"<br>2. Chuyển hướng tới Apple login page<br>3. Nhập Apple ID: testuser.mazii@icloud.com<br>4. Nhập password Apple<br>5. Hoàn thành xác thực Apple<br>6. Chấp nhận permission khi được yêu cầu | Apple ID: testuser.mazii@icloud.com<br>Apple Password: AppleTest@2026 | 1. Apple auth thành công<br>2. Tài khoản Mazii được tạo/liên kết với Apple<br>3. Redirect tới trang chủ /vi-VN<br>4. User authenticated | High |
| MAZII_LOGIN_TC_005 | Login | Để trống trường Email | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Để trống trường "Email"<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: (trống)<br>Password: Test@123456 | 1. Email field có red border<br>2. Hiển thị error: "Bạn chưa nhập địa chỉ email"<br>3. Icon cảnh báo đỏ hiển thị<br>4. Nút "Đăng nhập" disabled | High |
| MAZII_LOGIN_TC_006 | Login | Để trống trường Password | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user_test_01@gmail.com<br>2. Để trống trường "Mật khẩu"<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: user_test_01@gmail.com<br>Password: (trống) | 1. Password field có red border<br>2. Hiển thị error: "Bạn chưa nhập mật khẩu" (hoặc tương tự)<br>3. Icon cảnh báo đỏ hiển thị<br>4. Nút "Đăng nhập" disabled | High |
| MAZII_LOGIN_TC_007 | Login | Để trống cả Email và Password | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Để trống trường "Email"<br>2. Để trống trường "Mật khẩu"<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: (trống)<br>Password: (trống) | 1. Cả 2 fields có red border<br>2. Hiển thị lỗi email: "Bạn chưa nhập địa chỉ email"<br>3. Hiển thị lỗi password<br>4. Nút "Đăng nhập" disabled | High |
| MAZII_LOGIN_TC_008 | Login | Email không có ký tự @ | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: invalidemailgmail.com (thiếu @)<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: invalidemailgmail.com<br>Password: Test@123456 | 1. Email field có red border<br>2. Hiển thị error: "Không đúng định dạng email"<br>3. Icon cảnh báo đỏ hiển thị<br>4. Nút "Đăng nhập" disabled | High |
| MAZII_LOGIN_TC_009 | Login | Email không có domain name | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user@.com (thiếu domain)<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: user@.com<br>Password: Test@123456 | 1. Email field có red border<br>2. Hiển thị error: "Không đúng định dạng email"<br>3. Icon cảnh báo đỏ hiển thị<br>4. Nút "Đăng nhập" disabled | High |
| MAZII_LOGIN_TC_010 | Login | Email đúng, Password sai | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user_test_01@gmail.com<br>2. Nhập password: wrongpassword<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: user_test_01@gmail.com<br>Password: wrongpassword | 1. Form được submit<br>2. Button vào loading state<br>3. Server trả về error<br>4. Hiển thị toast/alert: "Email hoặc mật khẩu không đúng"<br>5. Vẫn ở lại trang login<br>6. Password field được clear | Medium |
| MAZII_LOGIN_TC_011 | Login | Email sai, Password đúng | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: wrong_user@gmail.com<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: wrong_user@gmail.com<br>Password: Test@123456 | 1. Form được submit<br>2. Button vào loading state<br>3. Server trả về error<br>4. Hiển thị toast/alert: "Email hoặc mật khẩu không đúng"<br>5. Vẫn ở lại trang login<br>6. Password field được clear | Medium |
| MAZII_LOGIN_TC_012 | Login | Email sai và Password sai | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: wrong_user@gmail.com<br>2. Nhập password: wrongpassword<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: wrong_user@gmail.com<br>Password: wrongpassword | 1. Form được submit<br>2. Button vào loading state<br>3. Server trả về error<br>4. Hiển thị toast/alert: "Email hoặc mật khẩu không đúng"<br>5. Vẫn ở lại trang login<br>6. Password field được clear | Medium |
| MAZII_LOGIN_TC_013 | Login | Submit form mà chưa hoàn thành reCAPTCHA | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user_test_01@gmail.com<br>2. Nhập password: Test@123456<br>3. KHÔNG hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: user_test_01@gmail.com<br>Password: Test@123456 | 1. Nút "Đăng nhập" bị disable hoặc không respond<br>2. Hiển thị error: "Vui lòng hoàn thành xác thực reCAPTCHA"<br>3. Form không được submit | Medium |
| MAZII_LOGIN_TC_014 | Login | Toggle Password Visibility (Eye Icon) | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập password: Test@123456<br>2. Password field hiển thị dạng dots (•••)<br>3. Click eye icon (toggle)<br>4. Xem password<br>5. Click eye icon lần 2<br>6. Password lại ẩn | Password: Test@123456 | 1. Ban đầu password hiển thị dạng masked dots<br>2. Sau click eye icon, password hiển thị dạng text: "Test@123456"<br>3. Sau click lần 2, password lại ẩn dạng dots<br>4. Eye icon thay đổi hình ảnh (mở/đóng) tương ứng | Medium |
| MAZII_LOGIN_TC_015 | Login | Click link "Quên mật khẩu?" | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Tìm và click link "Quên mật khẩu?" (Forgot Password)<br>2. Chờ trang load | - | 1. Redirect tới trang: /vi-VN/user/forgot-password<br>2. Form "Khôi phục mật khẩu" được hiển thị<br>3. Có trường email để nhập<br>4. Có nút "Xác nhận" (Confirm) | Medium |
| MAZII_LOGIN_TC_016 | Login | Click link "Đăng ký ngay" | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Tìm và click link "Đăng ký ngay" (Sign up)<br>2. Chờ trang load | - | 1. Redirect tới trang: /vi-VN/user/register<br>2. Form "Đăng ký tài khoản mới" được hiển thị<br>3. Có các trường: Email, Password, Confirm Password | Medium |
| MAZII_LOGIN_TC_017 | Login | Email với khoảng trắng ở đầu/cuối | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: "  user_test_01@gmail.com  " (có space đầu/cuối)<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: "  user_test_01@gmail.com  " (với spaces)<br>Password: Test@123456 | 1. System tự động trim spaces (hoặc yêu cầu xóa)<br>2. Xử lý được như email: user_test_01@gmail.com<br>3. Đăng nhập thành công (hoặc hiển thị error nếu không hỗ trợ trim) | Low |
| MAZII_LOGIN_TC_018 | Login | Email dài gần đến giới hạn max length | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email dài: verylongemailaddresswithnumbersandcharacters12345678901234567890@verylongdomainname.com<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: verylongemailaddresswithnumbersandcharacters12345678901234567890@verylongdomainname.com<br>Password: Test@123456 | 1. System chấp nhận email (max 255 chars)<br>2. Email được nhập đầy đủ không bị cắt ngắn<br>3. Đăng nhập thành công nếu email tồn tại | Low |
| MAZII_LOGIN_TC_019 | Login | Email với ký tự đặc biệt hợp lệ | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Nhập email: user.name+test@gmail.com<br>2. Nhập password: Test@123456<br>3. Hoàn thành reCAPTCHA<br>4. Click "Đăng nhập" | Email: user.name+test@gmail.com<br>Password: Test@123456 | 1. Email được chấp nhận (. và + là ký tự hợp lệ)<br>2. Không hiển thị lỗi validation<br>3. Đăng nhập thành công nếu email tồn tại | Low |
| MAZII_LOGIN_TC_020 | Login | Email phân biệt chữ hoa/thường (Case Sensitivity) | Trình duyệt mở URL /vi-VN/user/login, chưa đăng nhập | 1. Đăng ký tài khoản với email: User_Test_01@Gmail.com<br>2. Logout<br>3. Login với email: user_test_01@gmail.com (chữ thường)<br>4. Nhập password: Test@123456<br>5. Hoàn thành reCAPTCHA<br>6. Click "Đăng nhập" | Email: user_test_01@gmail.com (thường)<br>Password: Test@123456 | 1. Email không phân biệt chữ hoa/thường<br>2. System chuẩn hóa email thành chữ thường<br>3. Đăng nhập thành công | Low |

---

## GIẢI THÍCH KỸ THUẬT THIẾT KẾ TEST CASE

### 1. **Equivalence Partitioning (EP)**

Chia input thành các nhóm tương đương:

- **Email:**
  - Nhóm 1: Email rỗng → TC_005, TC_007
  - Nhóm 2: Email sai định dạng (không @, không domain) → TC_008, TC_009
  - Nhóm 3: Email hợp lệ → TC_001, TC_002, TC_003, TC_004

- **Password:**
  - Nhóm 1: Password rỗng → TC_006, TC_007
  - Nhóm 2: Password sai → TC_010, TC_011, TC_012
  - Nhóm 3: Password đúng → TC_001, TC_002

### 2. **Boundary Value Analysis (BVA)**

Test tại giá trị ranh giới:

- **Email length:**
  - Min boundary: Email rỗng (0 ký tự) → TC_005
  - Normal: 5-50 ký tự → TC_001
  - Max boundary: ~250 ký tự → TC_018

- **Email format:**
  - Thiếu @: "invalidemail" → TC_008
  - Thiếu domain: "user@.com" → TC_009
  - Hợp lệ: "user@domain.com" → TC_001

### 3. **Decision Table**

| Email | Password | reCAPTCHA | Result |
|-------|----------|-----------|--------|
| Valid | Valid | Complete | Success (TC_001) |
| Valid | Valid | Incomplete | Error (TC_013) |
| Empty | Valid | Complete | Error (TC_005) |
| Valid | Empty | Complete | Error (TC_006) |
| Invalid | Valid | Complete | Error (TC_008) |
| Valid | Invalid | Complete | Error (TC_010) |

### 4. **State Transition**

```
[Initial State]
    ↓
[Form Fields Empty] → TC_005, TC_006, TC_007
    ↓
[Fill Valid Data] → TC_001, TC_002
    ↓
[Button Enabled] → TC_001, TC_002
    ↓
[Click Login + reCAPTCHA Complete]
    ├→ [Success] → Redirect Dashboard (TC_001, TC_002, TC_003, TC_004)
    └→ [Failed] → Error Message (TC_010, TC_011, TC_012)
```

---

## QUY TẮC ƯU TIÊN TEST (Test Execution Priority)

### **Critical Priority - Chạy đầu tiên**
- TC_001: Happy path cơ bản (Email + Password)
- TC_005, TC_006, TC_007: Validation cơ bản
- TC_008, TC_009: Email format validation

### **High Priority - Chạy tiếp theo**
- TC_002: Remember me functionality
- TC_003, TC_004: Social login (Google/Apple)
- TC_010, TC_011, TC_012: Wrong credentials handling
- TC_013: reCAPTCHA validation

### **Medium Priority - Chạy bình thường**
- TC_014: Password visibility toggle
- TC_015, TC_016: Navigation links (Forgot password, Sign up)

### **Low Priority - Chạy sau cùng**
- TC_017: Email with spaces
- TC_018: Long email address
- TC_019: Special characters in email
- TC_020: Case sensitivity

---

## GHI CHÚ THỰC HIỆN

1. **Test Data:** Tất cả email/password trong test cases đều là dữ liệu cụ thể, không phải placeholder. Cần chuẩn bị test account trước khi thực hiện.

2. **Environment:** Tất cả test chạy trên https://beta.mazii.net/vi-VN/user/login

3. **Browser:** Chrome, Firefox, Safari, Edge (phiên bản mới nhất)

4. **Assertion Points:** Mỗi TC có Expected Results cụ thể, dùng để verify

5. **Automation Readiness:** Test cases này sẵn sàng để convert thành automation test (Selenium, Cypress, Playwright)

---

**Tài liệu này được tạo theo mode QUICK của skill RBT Manual Testing**
