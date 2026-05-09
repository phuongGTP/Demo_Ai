# MANUAL TEST CASES — MODULE CUSTOMERS
## Hệ thống: Perfex CRM — Anh Tester Demo
## URL: https://crm.anhtester.com/admin/clients
## Ngày sinh: 2026-05-09 | Phương pháp: AI-RBT (Risk-Based Testing)

---

## Thông tin chung

| Mục | Chi tiết |
|-----|----------|
| Dự án | Perfex CRM — Anh Tester Demo |
| Module | Customers (Clients) |
| Tổng số TC | 37 |
| Phương pháp | AI-RBT 6 bước (FULL RBT Mode) |
| Kỹ thuật áp dụng | EP, BVA, Decision Table, State Transition |

### Tài khoản Test

| Loại | Email | Password |
|------|-------|----------|
| Valid Admin | `admin@example.com` | `123456` |

### Assumptions đã xác nhận

| # | Nội dung |
|---|----------|
| A1 | Trường **Company** là trường bắt buộc duy nhất, không có giới hạn maxlength rõ ràng |
| A2 | Trường **Phone**, **Website**, **VAT** không có validation định dạng — nhận text tự do |
| A3 | Toggle Active/Inactive lưu ngay lập tức, không cần nhấn Save |
| A4 | Import CSV có email trùng → bỏ qua dòng trùng, tiếp tục import dòng còn lại |
| A5 | Simulate Import kiểm tra cả cấu trúc file lẫn trùng email |
| A6 | Import file không đúng UTF-8 → báo lỗi encoding, không import |
| A7 | Xóa Customer → cascade delete toàn bộ dữ liệu liên quan |
| A8 | Back browser sau xóa → redirect về danh sách hoặc hiển thị 404 |
| A9 | Bulk Actions Mass Delete → có flash message thông báo kết quả |
| A10 | Bulk Actions khi chưa chọn record → nút disabled hoặc thông báo yêu cầu chọn |
| A11 | Toggle Inactive một Customer → Contacts không bị ảnh hưởng tự động |
| A12 | Tab Customer Admins cho phép gán/gỡ Staff làm admin phụ trách customer |

---

## Bảng Test Cases

| TC ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data |
|-------|--------|-----------|------------|---------------|------------|-----------------|----------|-----------|
| CRM_CUST_TC_001 | MOD-01-A · Summary | 🟡 Medium | Verify trang danh sách hiển thị đủ 6 chỉ số Summary | Đã đăng nhập Admin, truy cập `/admin/clients` | 1. Quan sát khu vực đầu trang<br>2. Đếm số chỉ số hiển thị | 1. Hiển thị đủ 6 chỉ số: `Total Customers`, `Active Customers`, `Inactive Customers`, `Active Contacts`, `Inactive Contacts`, `Contacts Logged In Today`<br>2. Mỗi chỉ số là số nguyên ≥ 0 | Medium | N/A |
| CRM_CUST_TC_002 | MOD-01-B · DataTable | 🟡 Medium | Verify DataTable hiển thị đủ 8 cột đúng spec | Đã đăng nhập Admin, trang Customers có ít nhất 1 record | 1. Quan sát header bảng dữ liệu | 1. Bảng có đủ 8 cột theo thứ tự: Checkbox, `#`, `Company`, `Primary Contact`, `Primary Email`, `Phone`, `Active`, `Groups`, `Date Created`<br>2. Header có checkbox "Chọn tất cả"<br>3. Cột `Company` hiển thị dạng link<br>4. Cột `Phone` hiển thị dạng link<br>5. Cột `Active` hiển thị toggle switch<br>6. Cột `Groups` hiển thị badge/tag | Medium | N/A |
| CRM_CUST_TC_003 | MOD-01-B · Phân trang | 🟡 Medium | Verify dropdown phân trang hiển thị đúng 25/50/100 record | Đã đăng nhập Admin, bảng có >25 records | 1. Tìm dropdown số lượng hiển thị (mặc định 25)<br>2. Chọn `50`, quan sát số dòng bảng<br>3. Chọn `100`, quan sát số dòng bảng<br>4. Chọn lại `25` | 1. Dropdown có đúng 3 tùy chọn: 25, 50, 100<br>2. Chọn 50: bảng hiển thị tối đa 50 dòng<br>3. Chọn 100: bảng hiển thị tối đa 100 dòng<br>4. Thông tin phân trang (`Showing X of Y`) cập nhật tương ứng | Medium | N/A |
| CRM_CUST_TC_004 | MOD-01-B · Sort | 🟡 Medium | Verify sort cột Company đảo chiều A→Z và Z→A khi click | Đã đăng nhập Admin, bảng có ≥2 records | 1. Click vào tiêu đề cột `Company`<br>2. Ghi nhận thứ tự dòng đầu/cuối<br>3. Click lại cột `Company` lần 2<br>4. Ghi nhận thứ tự dòng đầu/cuối | 1. Lần click 1: Danh sách sắp xếp A→Z (hoặc Z→A)<br>2. Icon mũi tên trên cột thể hiện chiều sort<br>3. Lần click 2: Thứ tự đảo ngược hoàn toàn<br>4. Dữ liệu không bị mất hoặc lặp | Medium | N/A |
| CRM_CUST_TC_005 | MOD-01-C · Toggle | 🔴 High | Verify toggle Active→Inactive lưu ngay lập tức không cần nhấn Save | Đã đăng nhập Admin, có customer đang ở trạng thái Active | 1. Tại bảng danh sách, tìm customer đang Active (toggle bật)<br>2. Click toggle switch trên cột `Active` để tắt<br>3. Quan sát phản hồi ngay sau click (không reload trang) | 1. Toggle chuyển sang Inactive ngay lập tức<br>2. Không xuất hiện form Save hay nút xác nhận<br>3. Không reload toàn trang<br>4. Có thể có loading spinner ngắn rồi toggle ổn định ở trạng thái mới | High | Customer đang Active (ví dụ: Company `Test Toggle Co`) |
| CRM_CUST_TC_006 | MOD-01-C · Toggle | 🔴 High | Verify trạng thái toggle được lưu bền vững sau khi refresh trang | Đã thực hiện CRM_CUST_TC_005 thành công | 1. Sau khi toggle Customer X sang Inactive<br>2. Nhấn F5 refresh trang<br>3. Tìm lại Customer X trong bảng | 1. Sau refresh: Customer X vẫn hiển thị trạng thái Inactive<br>2. Giá trị đã được lưu vào database, không bị reset | High | Customer đã toggle ở TC005 |
| CRM_CUST_TC_007 | MOD-02-C · Happy Path | 🔴 High | Verify tạo mới customer thành công chỉ với trường Company bắt buộc | Đã đăng nhập Admin | 1. Truy cập `/admin/clients`<br>2. Click nút `+ New Customer`<br>3. Xác nhận URL chuyển sang `/admin/clients/client`<br>4. Xác nhận tab `Customer Details` đang active<br>5. Nhập Company: `Antigravity Test Co 001`<br>6. Để trống tất cả trường còn lại<br>7. Click nút `Save` | 1. Bước 2: Chuyển trang thành công<br>2. Bước 3: URL đúng `/admin/clients/client`<br>3. Bước 4: Tab `Customer Details` active mặc định<br>4. Bước 7: Lưu thành công, không có thông báo lỗi<br>5. Redirect về `/admin/clients/client/{id}` với ID mới<br>6. Header hiển thị `#{ID} Antigravity Test Co 001` | Critical | Company: `Antigravity Test Co 001` |
| CRM_CUST_TC_008 | MOD-02-A · Validation | 🔴 High | Verify thông báo lỗi khi submit form tạo mới mà trường Company trống | Đã đăng nhập Admin, đang ở form tạo mới `/admin/clients/client` | 1. Để trống trường `Company`<br>2. Nhập VAT Number: `VN0123456789`<br>3. Nhập Phone: `0909111222`<br>4. Click nút `Save` | 1. Form KHÔNG submit thành công<br>2. Thông báo lỗi màu đỏ xuất hiện bên dưới trường Company: `"This field is required."`<br>3. Trang vẫn ở `/admin/clients/client`, không redirect<br>4. Dữ liệu đã nhập ở VAT và Phone vẫn còn trong form | Critical | Company: *(để trống)*<br>VAT: `VN0123456789`<br>Phone: `0909111222` |
| CRM_CUST_TC_009 | MOD-02-A · Form UI | 🟡 Medium | Verify form tạo mới có 2 tab và tab Customer Details là mặc định khi mở | Đã đăng nhập Admin | 1. Click `+ New Customer`<br>2. Quan sát cấu trúc tab ngay khi form mở<br>3. Click sang tab `Billing & Shipping`<br>4. Click về tab `Customer Details` | 1. Form hiển thị đúng 2 tab: `Customer Details` và `Billing & Shipping`<br>2. Tab `Customer Details` active (highlighted) mặc định<br>3. Click `Billing & Shipping`: nội dung chuyển sang form địa chỉ<br>4. Click về `Customer Details`: form fields ban đầu hiển thị trở lại | Medium | N/A |
| CRM_CUST_TC_010 | MOD-02-B · Copy Address | 🟡 Medium | Verify nút "Same as Customer Info" copy địa chỉ từ Customer Details vào Billing Address | Đã đăng nhập Admin, đang ở form tạo mới | 1. Tab `Customer Details`: nhập Address `123 Nguyen Hue`, City `Ho Chi Minh`, State `HCM`, Zip `70000`<br>2. Chuyển sang tab `Billing & Shipping`<br>3. Click nút `Same as Customer Info`<br>4. Quan sát các field Billing Address | 1. Billing Street tự điền: `123 Nguyen Hue`<br>2. Billing City: `Ho Chi Minh`<br>3. Billing State: `HCM`<br>4. Billing Zip: `70000`<br>5. Các field Shipping Address không bị ảnh hưởng | Medium | Address: `123 Nguyen Hue`<br>City: `Ho Chi Minh`<br>State: `HCM`<br>Zip: `70000` |
| CRM_CUST_TC_011 | MOD-02-B · Copy Address | 🟡 Medium | Verify nút "Copy Billing Address" copy Billing → Shipping | Đã đăng nhập Admin, tab Billing & Shipping đã có dữ liệu Billing | 1. Tab `Billing & Shipping`: nhập Billing Street `456 Le Loi`, Billing City `Da Nang`<br>2. Click nút `Copy Billing Address`<br>3. Quan sát các field Shipping Address | 1. Shipping Street tự điền: `456 Le Loi`<br>2. Shipping City: `Da Nang`<br>3. Toàn bộ Billing fields được copy sang Shipping tương ứng | Medium | Billing Street: `456 Le Loi`<br>Billing City: `Da Nang` |
| CRM_CUST_TC_012 | MOD-02-C · Save | 🔴 High | Verify nút Save redirect về trang chi tiết customer vừa tạo | Đã đăng nhập Admin, form tạo mới đã điền Company | 1. Nhập Company: `Save Redirect Test Co`<br>2. Click nút `Save` (không phải Save and create contact) | 1. Lưu thành công<br>2. Redirect về `/admin/clients/client/{id}` (ID do hệ thống tạo)<br>3. Header trang chi tiết hiển thị `#{ID} Save Redirect Test Co`<br>4. Không có thông báo lỗi | High | Company: `Save Redirect Test Co` |
| CRM_CUST_TC_013 | MOD-02-C · Save and Contact | 🔴 High | Verify nút "Save and create contact" lưu customer rồi mở form tạo Contact | Đã đăng nhập Admin, form tạo mới đã điền Company | 1. Nhập Company: `Save And Contact Test Co`<br>2. Click nút `Save and create contact` | 1. Customer được lưu thành công<br>2. Hệ thống chuyển sang form tạo Contact mới<br>3. Form Contact đã liên kết với customer `Save And Contact Test Co`<br>4. Không có thông báo lỗi | High | Company: `Save And Contact Test Co` |
| CRM_CUST_TC_014 | MOD-03-A · Header | 🟡 Medium | Verify trang chi tiết hiển thị đúng header `#{ID} {Company}` và dropdown Delete | Đã đăng nhập Admin, có customer `Antigravity Test Co 001` từ TC007 | 1. Từ danh sách, click vào link `Antigravity Test Co 001`<br>2. Quan sát header trang chi tiết<br>3. Click vào dropdown button bên cạnh header | 1. URL có dạng `/admin/clients/client/{id}`<br>2. Header hiển thị `#{ID} Antigravity Test Co 001`<br>3. Label `Profile` hiển thị phía bên phải header<br>4. Dropdown mở ra có option `Delete` | Medium | Customer: `Antigravity Test Co 001` |
| CRM_CUST_TC_015 | MOD-03-B · Sidebar | 🔴 High | Verify sidebar trang chi tiết có đủ 19 tab theo đúng thứ tự | Đã đăng nhập Admin, đang ở trang chi tiết bất kỳ customer | 1. Quan sát toàn bộ sidebar trái<br>2. Đếm và ghi nhận tên từng tab theo thứ tự | 1. Sidebar hiển thị đúng 19 tab theo thứ tự: Profile, Contacts, Notes, Statement, Invoices, Payments, Proposals, Credit Notes, Estimates, Subscriptions, Expenses, Contracts, Projects, Tasks, Tickets, Files, Vault, Reminders, Map<br>2. Mỗi tab click được | High | N/A |
| CRM_CUST_TC_016 | MOD-03-C · Sub-tabs | 🟡 Medium | Verify tab Profile có 3 sub-tab và Customer Details là mặc định | Đã đăng nhập Admin, đang ở trang chi tiết customer | 1. Click tab `Profile` trong sidebar<br>2. Quan sát khu vực nội dung bên phải<br>3. Click lần lượt sang `Billing & Shipping`, rồi `Customer Admins` | 1. Hiển thị 3 sub-tab: `Customer Details`, `Billing & Shipping`, `Customer Admins`<br>2. Sub-tab `Customer Details` active mặc định<br>3. Mỗi sub-tab click được và hiển thị nội dung tương ứng | Medium | N/A |
| CRM_CUST_TC_017 | MOD-03-C · Chỉnh sửa | 🔴 High | Verify chỉnh sửa Company và Save cập nhật header ngay lập tức | Đã đăng nhập Admin, customer `Save Redirect Test Co` từ TC012 | 1. Truy cập trang chi tiết customer `Save Redirect Test Co`<br>2. Tab Profile → Customer Details<br>3. Xóa nội dung Company, nhập: `Save Redirect Test Co EDITED`<br>4. Click nút `Save` | 1. Lưu thành công (flash message xác nhận hoặc không có lỗi)<br>2. Header ngay lập tức cập nhật: `#{ID} Save Redirect Test Co EDITED`<br>3. Không bị redirect ra ngoài trang | High | Company mới: `Save Redirect Test Co EDITED` |
| CRM_CUST_TC_018 | MOD-03-C · Validation Edit | 🔴 High | Verify validation khi xóa trắng Company khi chỉnh sửa customer | Đã đăng nhập Admin, đang ở form chỉnh sửa customer | 1. Tab Profile → Customer Details<br>2. Xóa toàn bộ nội dung trường `Company` (để trống)<br>3. Click nút `Save` | 1. Form KHÔNG lưu thành công<br>2. Thông báo lỗi màu đỏ bên dưới Company: `"This field is required."`<br>3. Trang vẫn ở form chỉnh sửa, không redirect<br>4. Dữ liệu gốc không bị thay đổi | Critical | Company: *(xóa trắng)* |
| CRM_CUST_TC_019 | MOD-04-A · Xóa | 🔴 High | Verify xóa customer thành công sau khi xác nhận trong dialog | Đã đăng nhập Admin, tạo sẵn customer `Delete Me Co` để xóa | 1. Truy cập trang chi tiết customer `Delete Me Co`<br>2. Click dropdown button bên cạnh header<br>3. Chọn `Delete`<br>4. Quan sát hộp thoại xác nhận<br>5. Click nút xác nhận xóa trong dialog | 1. Bước 3: Hộp thoại xác nhận xuất hiện trước khi xóa<br>2. Bước 5: Customer bị xóa thành công<br>3. Hệ thống redirect về `/admin/clients`<br>4. Customer `Delete Me Co` không còn xuất hiện trong danh sách | High | Company: `Delete Me Co` |
| CRM_CUST_TC_020 | MOD-04-A · Hủy xóa | 🟡 Medium | Verify hủy xóa bằng cách đóng dialog không xóa customer | Đã đăng nhập Admin, đang ở trang chi tiết bất kỳ customer | 1. Click dropdown button bên cạnh header<br>2. Chọn `Delete`<br>3. Hộp thoại xuất hiện<br>4. Click `Cancel` hoặc `Close` (X) để đóng dialog | 1. Bước 3: Dialog xác nhận hiển thị<br>2. Bước 4: Dialog đóng lại<br>3. Customer KHÔNG bị xóa<br>4. Vẫn ở trang chi tiết customer, không redirect | Medium | N/A |
| CRM_CUST_TC_021 | MOD-04-B · Back Button | 🟡 Medium | Verify nhấn Back browser sau khi xóa customer không gây crash | Đã thực hiện xóa customer thành công (TC019), đang ở trang danh sách | 1. Sau khi xóa customer `Delete Me Co` và được redirect về `/admin/clients`<br>2. Nhấn nút Back của trình duyệt<br>3. Quan sát phản hồi trang | 1. Hệ thống KHÔNG crash hoặc hiển thị lỗi 500<br>2. Trả về lỗi 404 hoặc redirect về `/admin/clients`<br>3. Không hiển thị trang chi tiết của customer đã bị xóa với dữ liệu cũ | Medium | Customer đã xóa ở TC019 |
| CRM_CUST_TC_022 | MOD-05-A · Contacts Tab | 🟡 Medium | Verify tab Contacts trong chi tiết customer hiển thị đủ cột và empty state | Đã đăng nhập Admin, có customer chưa có contact nào | 1. Truy cập trang chi tiết customer chưa có contact<br>2. Click tab `Contacts` trong sidebar<br>3. Quan sát bảng dữ liệu | 1. Bảng hiển thị đủ 6 cột: `Full Name`, `Email`, `Position`, `Phone`, `Active` (toggle), `Last Login`<br>2. Nút `New Contact` hiển thị trên bảng<br>3. Nút `Export` hiển thị<br>4. Có thanh tìm kiếm `Search`<br>5. Dropdown số lượng hiển thị mặc định 25<br>6. Bảng hiển thị thông báo `"No entries found"` | Medium | Customer không có contact |
| CRM_CUST_TC_023 | MOD-05-B · New Contact | 🟡 Medium | Verify click nút New Contact mở form tạo contact liên kết với customer hiện tại | Đã đăng nhập Admin, đang ở tab Contacts của customer `Antigravity Test Co 001` | 1. Click nút `New Contact`<br>2. Quan sát form/popup xuất hiện | 1. Form tạo Contact mới mở ra (modal popup hoặc chuyển trang)<br>2. Form đã gắn sẵn tên customer `Antigravity Test Co 001`<br>3. Các trường Contact cần nhập: First Name, Last Name, Email, Phone... | Medium | Customer: `Antigravity Test Co 001` |
| CRM_CUST_TC_024 | MOD-05-C · All Contacts | 🟡 Medium | Verify trang Contacts tổng hợp hiển thị đúng cột và toggle Active hoạt động | Đã đăng nhập Admin, hệ thống có ít nhất 1 contact | 1. Truy cập trang danh sách Customers `/admin/clients`<br>2. Click nút `Contacts` trên toolbar<br>3. Quan sát bảng tổng hợp<br>4. Click toggle `Active` của 1 contact để đổi trạng thái | 1. Bước 2: Chuyển sang trang Contacts tổng hợp<br>2. Bảng hiển thị đủ 8 cột: `First Name`, `Last Name`, `Email`, `Company`, `Phone`, `Position`, `Last Login`, `Active`<br>3. Cột `Active` là toggle switch<br>4. Tên contact là link dẫn đến trang chi tiết<br>5. Bước 4: Toggle đổi trạng thái ngay lập tức, không cần Save | Medium | N/A |
| CRM_CUST_TC_025 | MOD-06-A · Import UI | 🟡 Medium | Verify trang Import Customers hiển thị đầy đủ các thành phần UI | Đã đăng nhập Admin | 1. Truy cập `/admin/clients`<br>2. Click nút `Import Customers`<br>3. Quan sát toàn bộ trang Import tại `/admin/clients/import` | 1. URL chuyển về `/admin/clients/import`<br>2. Có phần hướng dẫn: yêu cầu UTF-8, format ngày `Y-m-d`, tránh email trùng<br>3. Nút `Download Sample` hiển thị<br>4. Danh sách cột bắt buộc có dấu `*`: `Firstname*`, `Lastname*`, `Email*`, `Company*`<br>5. Trường chọn file `Choose CSV File`<br>6. Dropdown `Groups`<br>7. Trường `Default password for all contacts`<br>8. Nút `Import` và nút `Simulate Import` đều hiển thị | Medium | N/A |
| CRM_CUST_TC_026 | MOD-06-B · Simulate | 🔴 High | Verify Simulate Import với CSV hợp lệ không tạo dữ liệu thật trong hệ thống | Đã đăng nhập Admin, chuẩn bị file CSV hợp lệ | 1. Truy cập `/admin/clients/import`<br>2. Upload file `simulate_valid.csv` (2 dòng dữ liệu hợp lệ)<br>3. Click `Simulate Import`<br>4. Quan sát kết quả mô phỏng<br>5. Vào `/admin/clients` kiểm tra danh sách | 1. Bước 3: Hệ thống xử lý và hiển thị kết quả mô phỏng<br>2. Kết quả báo không có lỗi với file hợp lệ<br>3. Bước 5: Danh sách customer KHÔNG có thêm 2 customer từ file CSV<br>4. Simulate không tạo dữ liệu thật | High | File `simulate_valid.csv`:<br>`Firstname,Lastname,Email,Company`<br>`Simulate,UserOne,sim.one@test.com,Simulate Co One`<br>`Simulate,UserTwo,sim.two@test.com,Simulate Co Two` |
| CRM_CUST_TC_027 | MOD-06-C · Import thực | 🔴 High | Verify Import CSV hợp lệ tạo customer mới xuất hiện trong danh sách | Đã đăng nhập Admin, email trong file chưa tồn tại trong hệ thống | 1. Truy cập `/admin/clients/import`<br>2. Upload file `import_valid.csv` (1 dòng dữ liệu)<br>3. Click `Import`<br>4. Quan sát thông báo kết quả<br>5. Vào `/admin/clients`, tìm kiếm `Import Real Co` | 1. Bước 3: Quá trình import hoàn thành<br>2. Bước 4: Thông báo import thành công hiển thị<br>3. Bước 5: Customer `Import Real Co` xuất hiện trong danh sách<br>4. Contact có email `import.real@test.com` được tạo kèm theo | High | File `import_valid.csv`:<br>`Firstname,Lastname,Email,Company`<br>`Import,Real,import.real@test.com,Import Real Co` |
| CRM_CUST_TC_028 | MOD-06-D · Import lỗi field | 🔴 High | Verify Import CSV thiếu cột Email bắt buộc báo lỗi và không import | Đã đăng nhập Admin | 1. Truy cập `/admin/clients/import`<br>2. Upload file `import_missing_email.csv` thiếu cột `Email`<br>3. Click `Simulate Import`<br>4. Quan sát thông báo | 1. Hệ thống phát hiện thiếu cột bắt buộc<br>2. Hiển thị thông báo lỗi rõ ràng<br>3. Không có dữ liệu nào được import vào hệ thống | High | File `import_missing_email.csv`:<br>`Firstname,Lastname,Company`<br>`No,Email,Missing Email Co` |
| CRM_CUST_TC_029 | MOD-06-D · Import trùng email | 🔴 High | Verify Import CSV có email trùng bỏ qua dòng trùng và tiếp tục import dòng hợp lệ | Đã đăng nhập Admin, `import.real@test.com` đã tồn tại từ TC027 | 1. Truy cập `/admin/clients/import`<br>2. Upload file `import_duplicate.csv` gồm 2 dòng: 1 email trùng + 1 email mới<br>3. Click `Import`<br>4. Quan sát kết quả và danh sách customer | 1. Hệ thống xử lý không crash<br>2. Dòng có email `import.real@test.com` (trùng) bị bỏ qua<br>3. Dòng có email `import.new@test.com` (mới) được import thành công<br>4. Thông báo kết quả hiển thị số dòng thành công / số dòng bị bỏ qua | High | File `import_duplicate.csv`:<br>`Firstname,Lastname,Email,Company`<br>`Import,Real,import.real@test.com,Dup Co`<br>`Import,New,import.new@test.com,New Import Co` |
| CRM_CUST_TC_030 | MOD-07-A · Export Excel | 🟢 Low | Verify Export Excel tải về file .xlsx chứa dữ liệu đúng | Đã đăng nhập Admin, bảng có ít nhất 2 records | 1. Truy cập `/admin/clients`<br>2. Click nút `Export` trên bảng<br>3. Quan sát dropdown<br>4. Chọn `Excel`<br>5. Mở file tải về | 1. Bước 2: Dropdown xuất hiện với 4 tùy chọn: `Excel`, `CSV`, `PDF`, `Print`<br>2. Bước 4: File `.xlsx` được tải xuống<br>3. Bước 5: File mở được, chứa dữ liệu customer khớp với bảng trên UI<br>4. Các cột trong file khớp với cột trong DataTable | Low | N/A |
| CRM_CUST_TC_031 | MOD-07-A · Export CSV & Print | 🟢 Low | Verify Export CSV tải về file .csv và Print mở print dialog | Đã đăng nhập Admin, bảng có dữ liệu | 1. Click `Export` → chọn `CSV`<br>2. Kiểm tra file tải về<br>3. Click `Export` → chọn `Print` | 1. Bước 1: File `.csv` được tải xuống<br>2. Bước 2: File mở được bằng text editor, có header và dữ liệu CSV đúng cấu trúc<br>3. Bước 3: Print dialog của trình duyệt xuất hiện | Low | N/A |
| CRM_CUST_TC_032 | MOD-08-A · Điều kiện | 🔴 High | Verify nút Bulk Actions bị blocked/disabled khi chưa chọn record nào | Đã đăng nhập Admin, trang Customers có dữ liệu | 1. Truy cập `/admin/clients`<br>2. Đảm bảo KHÔNG có checkbox nào được tick<br>3. Click nút `Bulk Actions` hoặc quan sát trạng thái nút | 1. Nút `Bulk Actions` ở trạng thái disabled (không click được) hoặc<br>2. Nếu click được: hệ thống hiển thị thông báo yêu cầu chọn ít nhất 1 record<br>3. Modal KHÔNG mở khi chưa có record nào được chọn | High | N/A |
| CRM_CUST_TC_033 | MOD-08-B · Mass Delete | 🔴 High | Verify Bulk Actions Mass Delete xóa đúng các customer đã chọn và hiển thị flash message | Đã đăng nhập Admin, tạo sẵn `Bulk Delete Co A` và `Bulk Delete Co B` | 1. Truy cập `/admin/clients`<br>2. Tick checkbox `Bulk Delete Co A` và `Bulk Delete Co B`<br>3. Click nút `Bulk Actions`<br>4. Quan sát modal dialog<br>5. Tick checkbox `Mass Delete`<br>6. Click `Confirm`<br>7. Quan sát kết quả và danh sách | 1. Bước 3: Modal mở với: checkbox `Mass Delete`, dropdown `Groups`, cảnh báo, nút `Confirm`, nút `Close`<br>2. Bước 6: Xóa thành công, modal đóng<br>3. Flash message thông báo kết quả xóa hiển thị<br>4. Bước 7: `Bulk Delete Co A` và `Bulk Delete Co B` không còn trong danh sách<br>5. Các customer khác không bị ảnh hưởng | High | Company A: `Bulk Delete Co A`<br>Company B: `Bulk Delete Co B` |
| CRM_CUST_TC_034 | MOD-08-C · Bulk Groups | 🟡 Medium | Verify Bulk Actions gán Group hàng loạt cho các customer đã chọn | Đã đăng nhập Admin, có Group `Test Group` và 2 customer chưa có group | 1. Tick checkbox `Group Test Co A` và `Group Test Co B`<br>2. Click `Bulk Actions`<br>3. Trong dropdown `Groups`, chọn `Test Group`<br>4. KHÔNG tick `Mass Delete`<br>5. Click `Confirm`<br>6. Quan sát cột `Groups` trên bảng | 1. Bước 5: Thao tác thực hiện thành công<br>2. Bước 6: Cả 2 customer hiển thị badge `Test Group` trong cột `Groups`<br>3. Không có customer nào bị xóa | Medium | Group: `Test Group`<br>Company A: `Group Test Co A`<br>Company B: `Group Test Co B` |
| CRM_CUST_TC_035 | MOD-08-C · Bulk Warning | 🟡 Medium | Verify cảnh báo "all groups will be removed" hiển thị trong modal khi không chọn group | Đã đăng nhập Admin, modal Bulk Actions đang mở | 1. Tick checkbox ít nhất 1 customer<br>2. Click `Bulk Actions`<br>3. Quan sát nội dung modal, KHÔNG chọn group nào trong dropdown `Groups`<br>4. Đọc nội dung cảnh báo hiển thị | 1. Modal hiển thị cảnh báo đúng nội dung: `"If you do not select any group all groups assigned to the selected customers will be removed."`<br>2. Cảnh báo hiển thị rõ ràng, không bị ẩn hay cắt bớt | Medium | N/A |
| CRM_CUST_TC_036 | MOD-09-A · Quick Search | 🟡 Medium | Verify ô Search lọc danh sách real-time khi nhập keyword và khôi phục khi xóa | Đã đăng nhập Admin, bảng có customer `Antigravity Test Co 001` | 1. Click vào ô `Search` góc phải trên bảng<br>2. Nhập từng ký tự: `A`, `n`, `t`, `i`<br>3. Quan sát bảng sau mỗi ký tự nhập<br>4. Xóa toàn bộ nội dung ô Search | 1. Bước 2: Bảng lọc kết quả ngay khi nhập, không cần Enter<br>2. Chỉ hiển thị customer có tên khớp từ khóa `Anti`<br>3. Customer `Antigravity Test Co 001` hiển thị trong kết quả<br>4. Bước 4: Xóa keyword → bảng hiển thị lại toàn bộ danh sách | Medium | Keyword: `Anti`<br>Customer có sẵn: `Antigravity Test Co 001` |
| CRM_CUST_TC_037 | MOD-09-B · Advanced Filter | 🟡 Medium | Verify Advanced Filter với 1 rule lọc đúng kết quả trên bảng | Đã đăng nhập Admin, bảng có customer ở cả trạng thái Active và Inactive | 1. Click biểu tượng filter (hình phễu) góc phải trên cùng<br>2. Chọn `New Filter`<br>3. Click `Add Rule`<br>4. Chọn tiêu chí: `Active` = `Yes`<br>5. Áp dụng filter<br>6. Quan sát kết quả bảng | 1. Bước 2: Giao diện tạo bộ lọc hiển thị<br>2. Bước 3: Rule mới được thêm vào<br>3. Bước 5: Bảng lọc chỉ hiển thị customer đang Active<br>4. Customer ở trạng thái Inactive KHÔNG xuất hiện trong kết quả<br>5. Không có lỗi trong quá trình filter | Medium | Tiêu chí filter: `Active = Yes` |

---

## Traceability Matrix — Requirements Coverage

| REQ ID | Module | Mô tả Yêu cầu | TC Coverage |
|--------|--------|----------------|-------------|
| REQ-01 | MOD-01-A | Hiển thị 6 chỉ số Summary | TC001 |
| REQ-02 | MOD-01-B | DataTable đủ 8 cột, phân trang, sort | TC002, TC003, TC004 |
| REQ-03 | MOD-01-C | Toggle Active/Inactive lưu ngay | TC005, TC006 |
| REQ-04 | MOD-02-A | Tab Customer Details, Company bắt buộc | TC008, TC009 |
| REQ-05 | MOD-02-B | Tab Billing & Shipping, copy address | TC010, TC011 |
| REQ-06 | MOD-02-C | Save → redirect; Save and create contact | TC007, TC012, TC013 |
| REQ-07 | MOD-03-A | Header `#{ID} {Company}`, dropdown Delete | TC014 |
| REQ-08 | MOD-03-B | Sidebar đủ 19 tab | TC015 |
| REQ-09 | MOD-03-C | Profile 3 sub-tab, lưu chỉnh sửa | TC016, TC017, TC018 |
| REQ-10 | MOD-04-A | Xóa có confirm, sau xóa về danh sách | TC019, TC020 |
| REQ-11 | MOD-04-B | Back browser sau xóa → không crash | TC021 |
| REQ-12 | MOD-05-A | Bảng Contacts đủ cột, empty state | TC022 |
| REQ-13 | MOD-05-B | Tạo Contact mới từ tab Contacts | TC023 |
| REQ-14 | MOD-05-C | Trang Contacts tổng hợp, toggle Active | TC024 |
| REQ-15 | MOD-06-A | Trang Import đủ thành phần UI | TC025 |
| REQ-16 | MOD-06-B | Simulate Import không tạo dữ liệu thật | TC026 |
| REQ-17 | MOD-06-C | Import CSV hợp lệ tạo customer | TC027 |
| REQ-18 | MOD-06-D | Import lỗi: thiếu field / email trùng | TC028, TC029 |
| REQ-19 | MOD-07-A | Export 4 định dạng hoạt động đúng | TC030, TC031 |
| REQ-20 | MOD-08-A | Bulk Actions blocked khi chưa chọn record | TC032 |
| REQ-21 | MOD-08-B | Mass Delete, có flash message | TC033 |
| REQ-22 | MOD-08-C | Gán Groups hàng loạt, cảnh báo không chọn group | TC034, TC035 |
| REQ-23 | MOD-09-A | Quick Search real-time | TC036 |
| REQ-24 | MOD-09-B | Advanced Filter với Add Rule | TC037 |

---

## Summary

| Module | Risk | TC IDs | Số TC |
|--------|------|--------|-------|
| MOD-01 · Danh Sách & Toggle | 🟡/🔴 | TC001–TC006 | 6 |
| MOD-02 · Tạo Mới | 🔴 | TC007–TC013 | 7 |
| MOD-03 · Chi Tiết & Chỉnh Sửa | 🟡/🔴 | TC014–TC018 | 5 |
| MOD-04 · Xóa | 🟡/🔴 | TC019–TC021 | 3 |
| MOD-05 · Contacts | 🟡 | TC022–TC024 | 3 |
| MOD-06 · Import CSV | 🔴 | TC025–TC029 | 5 |
| MOD-07 · Export | 🟢 | TC030–TC031 | 2 |
| MOD-08 · Bulk Actions | 🔴 | TC032–TC035 | 4 |
| MOD-09 · Tìm Kiếm & Lọc | 🟡 | TC036–TC037 | 2 |
| **TỔNG** | | **TC001–TC037** | **37** |

**Phân bổ Priority:** 3 Critical · 18 High · 14 Medium · 2 Low
