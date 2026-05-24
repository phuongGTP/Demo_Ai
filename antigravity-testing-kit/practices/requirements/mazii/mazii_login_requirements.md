# TÀI LIỆU YÊU CẦU (REQUIREMENTS) - CHỨC NĂNG ĐĂNG NHẬP WEB
**Website:** Mazii (beta.mazii.net)  
**Ngày tạo:** 2026-05-24  
**Phiên bản:** 1.0

---

## 1. TỔNG QUAN (OVERVIEW)

### Mục đích
Chức năng Đăng nhập (Login) cung cấp cơ chế xác thực cho người dùng nhằm truy cập các tính năng và dữ liệu cá nhân trên nền tảng Mazii. Người dùng có thể đăng nhập thông qua:
- Địa chỉ Email + Mật khẩu (Traditional Login)
- Tài khoản Google (OAuth)
- Tài khoản Apple (OAuth)

### Định vị trong ứng dụng
- **URL:** `https://beta.mazii.net/vi-VN/user/login`
- **Truy cập từ:** Nút "Đăng nhập" ở góc trên cùng bên phải của trang chủ
- **UI Container:** Modal/Dialog centered trên màn hình
- **Ngôn ngữ:** Tiếng Việt (có dấu)

---

## 2. YÊU CẦU CHỨC NĂNG (FUNCTIONAL REQUIREMENTS)

### 2.1 User Story 1: Đăng nhập bằng Email & Mật khẩu
**Tên tính năng:** Đăng nhập với Email/Mật khẩu

**Mô tả:**  
Là một người dùng, tôi muốn nhập địa chỉ email và mật khẩu của mình để có thể truy cập vào tài khoản cá nhân của mình trên Mazii.

**Acceptance Criteria:**
1. Người dùng có thể nhập email hợp lệ vào trường "Email"
2. Người dùng có thể nhập mật khẩu vào trường "Mật khẩu"
3. Người dùng có thể xem/ẩn mật khẩu bằng nút eye icon
4. Khi cả hai trường được điền đúng, nút "Đăng nhập" trở nên active (enabled)
5. Khi click "Đăng nhập", hệ thống gửi request xác thực
6. Nếu xác thực thành công, người dùng được chuyển hướng đến trang chủ hoặc trang mà họ đã yêu cầu trước đó
7. Nếu xác thực thất bại, hiển thị thông báo lỗi chi tiết

### 2.2 User Story 2: Xác thực dữ liệu nhập liệu (Validation)
**Tên tính năng:** Kiểm tra tính hợp lệ của dữ liệu nhập

**Mô tả:**  
Là một người dùng, tôi muốn hệ thống hiển thị thông báo lỗi rõ ràng khi tôi nhập sai định dạng hoặc bỏ trống các trường bắt buộc.

**Acceptance Criteria:**
1. Hiển thị lỗi khi email bị bỏ trống
2. Hiển thị lỗi khi email không đúng định dạng (không có @ hoặc không có tên miền)
3. Hiển thị lỗi khi mật khẩu bị bỏ trống
4. Hiển thị thông báo lỗi sử dụng mã màu đỏ và icon cảnh báo
5. Lỗi được hiển thị dưới trường input tương ứng
6. Nút "Đăng nhập" bị disable nếu form không hợp lệ

### 2.3 User Story 3: Quên Mật khẩu
**Tên tính năng:** Chức năng Quên mật khẩu

**Mô tả:**  
Là một người dùng, nếu tôi quên mật khẩu, tôi muốn có thể click vào liên kết "Quên mật khẩu?" để khôi phục lại mật khẩu của mình.

**Acceptance Criteria:**
1. Liên kết "Quên mật khẩu?" hiển thị ở dưới trường mật khẩu
2. Khi click, người dùng được chuyển hướng tới trang reset mật khẩu
3. Liên kết có thể nhận biết được (styling khác biệt, màu xanh dương)

### 2.4 User Story 4: Đăng ký Tài khoản Mới
**Tên tính năng:** Liên kết tới trang Đăng ký

**Mô tả:**  
Là một người dùng mới, nếu tôi chưa có tài khoản, tôi muốn click vào liên kết "Đăng ký ngay" để tạo một tài khoản mới.

**Acceptance Criteria:**
1. Văn bản "Bạn chưa có tài khoản? Đăng ký ngay" hiển thị phía trên form đăng nhập
2. Liên kết "Đăng ký ngay" có thể click được
3. Khi click, người dùng được chuyển hướng tới trang đăng ký (`/vi-VN/user/register`)

### 2.5 User Story 5: Đăng nhập Xã hội (Social Login)
**Tên tính năng:** Đăng nhập nhanh với Google/Apple

**Mô tả:**  
Là một người dùng, tôi muốn có thể đăng nhập nhanh chóng bằng tài khoản Google hoặc Apple của mình mà không cần nhập email và mật khẩu.

**Acceptance Criteria:**
1. Có hàng chữ "Hoặc đăng nhập nhanh bằng" ở giữa form
2. Nút Google có icon và text "Google" hiển thị rõ
3. Nút Apple có icon và text "Apple" hiển thị rõ
4. Khi click nút Google, được chuyển hướng tới trang xác thực Google OAuth
5. Khi click nút Apple, được chuyển hướng tới trang xác thực Apple OAuth
6. Sau xác thực thành công, tài khoản được tạo/liên kết và chuyển hướng về trang chủ

### 2.6 User Story 6: Bảo mật (reCAPTCHA)
**Tên tính năng:** Xác thực reCAPTCHA

**Mô tả:**  
Hệ thống cần xác thực rằng người dùng không phải là bot bằng cách sử dụng reCAPTCHA.

**Acceptance Criteria:**
1. Widget reCAPTCHA hiển thị ở góc dưới bên phải của form
2. Người dùng phải hoàn thành reCAPTCHA trước khi submit form
3. Nếu không hoàn thành reCAPTCHA, nút "Đăng nhập" bị disable hoặc hiển thị lỗi

---

## 3. ĐẶC TẢ TRƯỜNG DỮ LIỆU (FIELD SPECIFICATIONS)

| # | Tên Trường | Label | Loại | Validation Rules | Bắt buộc | Ghi chú |
|---|-----------|-------|------|------------------|----------|---------|
| 1 | email | Email | Text Input | - Không trống<br>- Phải có định dạng email hợp lệ (chứa @ và domain)<br>- Min length: 5 ký tự<br>- Max length: 255 ký tự | Có | Placeholder: "Nhập email của bạn" |
| 2 | password | Mật khẩu | Password Input | - Không trống<br>- Min length: (theo policy) | Có | Placeholder: "Nhập mật khẩu"<br>Có nút eye icon để toggle hiển thị/ẩn |
| 3 | recaptcha | reCAPTCHA | Widget | - Phải được hoàn thành | Có | Google reCAPTCHA v3 hoặc v2 |
| 4 | login_button | (Button) | Button | - Enabled khi form valid<br>- Disabled khi form invalid hoặc đang loading | - | Text: "Đăng nhập" |
| 5 | forgot_pwd_link | Quên mật khẩu? | Hyperlink | - Navigable | - | Link tới `/vi-VN/user/forgot-password` |
| 6 | signup_link | Đăng ký ngay | Hyperlink | - Navigable | - | Link tới `/vi-VN/user/register` |
| 7 | google_btn | Google | Social Login Button | - Clickable | - | OAuth redirect |
| 8 | apple_btn | Apple | Social Login Button | - Clickable | - | OAuth redirect |

---

## 4. QUY TẮC XỬ LÝ VÀ CÁC THÔNG BÁO LỖI (BUSINESS RULES & VALIDATIONS)

### 4.1 Validation Messages (Thông báo Validation)

#### Email Field Validations:

| Scenario | Điều kiện | Thông báo Lỗi | Biểu diễn |
|----------|----------|-------|----------|
| Empty Email | Email trống | "Bạn chưa nhập địa chỉ email" | Red border + icon cảnh báo + text màu đỏ |
| Invalid Email Format | Email không có @, hoặc không có domain name | "Không đúng định dạng email" | Red border + icon cảnh báo + text màu đỏ |
| Valid Email | Email có định dạng đúng (xxx@domain.xxx) | (Không hiển thị) | Green border hoặc border bình thường |

#### Password Field Validations:

| Scenario | Điều kiện | Thông báo Lỗi | Biểu diễn |
|----------|----------|-------|----------|
| Empty Password | Mật khẩu trống | (Dự kiến) "Vui lòng nhập mật khẩu" | Red border + icon cảnh báo |
| Short Password | Mật khẩu quá ngắn (nếu có min length requirement) | (Dự kiến) "Mật khẩu phải có ít nhất X ký tự" | Red border + icon cảnh báo |
| Valid Password | Mật khẩu hợp lệ | (Không hiển thị) | Green border hoặc border bình thường |

#### Form-level Validation:

| Scenario | Điều kiện | Thông báo Lỗi | Biểu diễn |
|----------|----------|-------|----------|
| Login Failed - Invalid Credentials | Email hoặc mật khẩu sai | "Email hoặc mật khẩu không đúng" | Toast/Alert notification (toàn màn hình) |
| Login Failed - Account Locked | Tài khoản bị khóa do nhiều lần đăng nhập sai | "Tài khoản của bạn đã bị khóa. Vui lòng reset mật khẩu" | Toast/Alert notification |
| Login Failed - Account Inactive | Tài khoản chưa được kích hoạt | "Vui lòng kích hoạt tài khoản qua email" | Toast/Alert notification |
| reCAPTCHA Failed | reCAPTCHA không được hoàn thành hoặc failed | "Vui lòng hoàn thành xác thực reCAPTCHA" | Toast/Alert notification |
| Network Error | Không kết nối được server | "Lỗi kết nối. Vui lòng thử lại" | Toast/Alert notification |

### 4.2 Form State Management

- **Pristine (Trạng thái ban đầu):** Form chưa được thay đổi
- **Dirty (Đã thay đổi):** Một hoặc nhiều field đã được người dùng chỉnh sửa
- **Touched (Đã chạm):** Người dùng đã tương tác (focus/blur) với ít nhất một field
- **Validation State:** 
  - Lỗi chỉ hiển thị khi field vừa được touched AND dirty
  - Field không được disable dù có lỗi, cho phép người dùng sửa

### 4.3 Button States

| Trạng thái | Điều kiện | Hiển thị |
|-----------|----------|---------|
| Enabled (Active) | Form valid + reCAPTCHA completed | Button có màu xanh dương, cursor: pointer |
| Disabled | Form invalid hoặc reCAPTCHA incomplete | Button mờ/xám, cursor: not-allowed |
| Loading | Request đang được xử lý | Button disabled + spinner loader |
| Error | Login failed | Button enabled lại, hiển thị error message |

### 4.4 Password Visibility Toggle

- **Default:** Password input hiển thị dạng dots/asterisks (ẩn)
- **Toggle:** Khi click eye icon, mật khẩu được hiển thị dưới dạng text
- **Icon:** Eye icon hiển thị ở bên phải của password field

### 4.5 Form Submission Behavior

1. User nhập email, password, hoàn thành reCAPTCHA
2. Click nút "Đăng nhập"
3. Button vào trạng thái loading (disabled + spinner)
4. Hệ thống gửi POST request tới server với email & password
5. **Nếu thành công:**
   - Lưu session/token
   - Chuyển hướng tới trang chủ (/) hoặc trang được yêu cầu trước đó
6. **Nếu thất bại:**
   - Hiển thị error message
   - Button quay về trạng thái enabled
   - Clear password field (nếu policy yêu cầu)

---

## 5. YÊU CẦU PHI CHỨC NĂNG (NON-FUNCTIONAL REQUIREMENTS)

### 5.1 Bảo mật (Security)
- Mật khẩu được gửi qua HTTPS only
- Mật khẩu không được log hoặc lưu trữ dưới dạng plain text
- Session token được lưu an toàn (HttpOnly cookie hoặc secure storage)
- Implement rate limiting để chống brute-force attack
- reCAPTCHA giúp phòng chống automated login attempts

### 5.2 Hiệu năng (Performance)
- Form loading time < 2 giây
- Validation response < 100ms
- Login request response < 3 giây (tính cả network latency)

### 5.3 Tương thích (Compatibility)
- Hỗ trợ desktop browsers: Chrome, Firefox, Safari, Edge (phiên bản 2+ gần nhất)
- Responsive design cho mobile (breakpoint: < 768px)
- Hỗ trợ iOS & Android browsers

### 5.4 Accessibility (A11y)
- Form fields có label rõ ràng, liên kết với `<label>` tag
- Buttons có accessible text hoặc aria-label
- Keyboard navigation hỗ trợ (Tab, Shift+Tab, Enter)
- Error messages có màu sắc khác biệt (không chỉ dựa vào màu)
- Color contrast ratio ≥ 4.5:1 (WCAG AA)

### 5.5 Internationalization (i18n)
- Tất cả text hiển thị bằng Tiếng Việt
- Hỗ trợ unicode (có dấu)
- RTL (Right-to-Left) text không yêu cầu

---

## 6. LUỒNG XỬ LÝ (WORKFLOWS)

### 6.1 Happy Path: Đăng nhập thành công
```
1. User truy cập /vi-VN/user/login hoặc click "Đăng nhập" ở trang chủ
2. Form đăng nhập được hiển thị
3. User nhập email hợp lệ
4. User nhập mật khẩu
5. User hoàn thành reCAPTCHA
6. Nút "Đăng nhập" becomes enabled
7. User click "Đăng nhập"
8. Button vào trạng thái loading
9. Server xác thực email + password
10. Xác thực thành công → Tạo session/token
11. Redirect tới /vi-VN (trang chủ)
12. User đã logged in, có thể truy cập các tính năng
```

### 6.2 Alternative Path: Email sai định dạng
```
1. User nhập email: "invalidemail" (thiếu @)
2. User blur khỏi email field
3. Validation trigger: Hiển thị "Không đúng định dạng email"
4. Email field có red border
5. Nút "Đăng nhập" disabled
6. User sửa email thành "user@example.com"
7. Validation clear, button becomes enabled
8. User tiếp tục quy trình bình thường
```

### 6.3 Alternative Path: Email trống
```
1. User click "Đăng nhập" mà chưa nhập email
2. Form validation trigger
3. Hiển thị lỗi: "Bạn chưa nhập địa chỉ email"
4. Email field focus tự động
5. Button vẫn disabled
6. User nhập email
7. Tiếp tục quy trình
```

### 6.4 Alternative Path: Đăng nhập với Google
```
1. User click nút "Google"
2. Redirect tới Google OAuth login
3. User xác thực với Google account
4. Google return authorization code
5. Server xác thực code với Google
6. Nếu thành công: Tạo/link account, tạo session
7. Redirect tới /vi-VN (trang chủ)
```

### 6.5 Alternative Path: Quên mật khẩu
```
1. User click "Quên mật khẩu?"
2. Redirect tới /vi-VN/user/forgot-password
3. User nhập email để reset password
4. Hệ thống gửi reset link tới email
5. User click link, tạo mật khẩu mới
6. Quay lại login với mật khẩu mới
```

### 6.6 Error Path: Đăng nhập thất bại
```
1. User nhập email: user@example.com
2. User nhập mật khẩu: wrongpassword
3. User click "Đăng nhập"
4. Server kiểm tra credentials
5. Email/password không khớp
6. Server return error response
7. Button quay về enabled state
8. Toast message hiển thị: "Email hoặc mật khẩu không đúng"
9. Password field được clear
10. User có thể thử lại
```

---

## 7. CÂU HỎI & LÀM RÕ VỚI STAKEHOLDER

1. **Độ dài tối thiểu mật khẩu:** Hiện tại chưa thấy validation. Cần xác nhận minimum password length requirement?
2. **Số lần đăng nhập sai tối đa:** Có limit bao nhiêu lần đăng nhập sai trước khi khóa tài khoản?
3. **Session timeout:** Sau bao lâu session hết hạn nếu không có activity?
4. **Remember me:** Có tính năng "Ghi nhớ tôi" (Remember me) hay không?
5. **Two-factor authentication (2FA):** Có yêu cầu 2FA cho tài khoản nào không?
6. **Email verification:** Tài khoản mới cần xác thực email trước khi login?
7. **Social login linking:** Người dùng có thể link Google/Apple account vào email account hiện có?
8. **Password reset:** Reset password link có hết hạn không? Bao lâu?

---

## 8. KỸ THUẬT THAM KHẢO

### 8.1 DOM Elements
- Email input: `<input id="email" type="text" placeholder="Nhập email của bạn">`
- Password input: `<input id="password" type="password" placeholder="Nhập mật khẩu">`
- Login button: `<button>Đăng nhập</button>`
- Password toggle: Eye icon button
- Form element: `<form class="ng-pristine">`

### 8.2 Technology Stack (Observed)
- Framework: Angular 18.2.14 (theo app-root)
- Form validation: Reactive Forms (Angular)
- Captcha: Google reCAPTCHA
- HTTP Client: Angular HttpClient

### 8.3 API Endpoints (Inferred)
- POST `/vi-VN/user/login` - Login request
- POST `/vi-VN/user/login/google` - Google OAuth
- POST `/vi-VN/user/login/apple` - Apple OAuth

---

**Tài liệu này được tạo từ phân tích live UI tại: https://beta.mazii.net**
