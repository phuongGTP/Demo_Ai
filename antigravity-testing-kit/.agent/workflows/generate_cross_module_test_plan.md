---
description: Phân tích tính năng phức tạp có nhiều modules liên kết, xây dựng Data Flow Map và ma trận kết hợp điều kiện (Combinatorial Matrix). Hỗ trợ Pairwise, Business-critical, Full Cartesian.
skills:
  - qa_automation_engineer
  - requirements_analyzer
  - ui_debug_agent
  - test_data_generator
---

# /generate_cross_module_test_plan — Phân Tích Cross-Module & Sinh Ma Trận Kết Hợp

> **Dùng khi:** Tính năng cần test đi qua **nhiều modules nối tiếp nhau**, mỗi module có nhiều lựa chọn (dimensions), và bộ kết hợp các lựa chọn quyết định output cuối cùng (template, công thức, business rules khác nhau).

> **BẮT BUỘC (MANDATORY):** Trước khi bắt đầu, PHẢI nạp và đọc kỹ:
> - **Skill:** `.agent/skills/qa_automation_engineer/SKILL.md` — Workflow routing + automation rules
> - **Skill:** `.agent/skills/requirements_analyzer/SKILL.md` — Phân tích requirements từ UI
> - **Skill:** `.agent/skills/ui_debug_agent/SKILL.md` — Inspect DOM thực tế
> - **Skill:** `.agent/skills/test_data_generator/SKILL.md` — Sinh test data (xem phần Multi-Step Pipeline)

---

## Khi nào dùng workflow này?

| Tình huống | Dùng workflow này? |
|------------|-------------------|
| Tính năng đi qua **1 module/form** | ❌ Dùng `/generate_manual_testcases_rbt` |
| Tính năng đi qua **nhiều modules**, mỗi module **độc lập** | ⚠️ Có thể — nhưng `/generate_application_test_plan` đủ |
| Tính năng đi qua **nhiều modules NỐI TIẾP**, output phụ thuộc **bộ kết hợp điều kiện** | ✅ **Đúng workflow này** |
| Cần **ma trận kết hợp** (Pairwise / Decision Table đa chiều) | ✅ **Đúng workflow này** |

**Ví dụ thực tế:**
- Biên bản thanh toán đối tác (loại đối tác × loại thanh toán × thuế × công nợ × nguồn tài sản)
- Hợp đồng bảo hiểm (loại BH × đối tượng × gói × kỳ hạn × phương thức thanh toán)
- Đơn hàng xuất khẩu (thị trường × loại hàng × vận chuyển × thanh toán × chứng từ)
- Quy trình phê duyệt (loại yêu cầu × phòng ban × cấp × số tiền → flow phê duyệt khác nhau)

---

## Input cần từ User

| Input | Bắt buộc | Mô tả |
|-------|----------|-------|
| **Tên tính năng / luồng** | ✅ | VD: "Biên bản thanh toán cho đối tác" |
| **URL ứng dụng** | ⚠️ Nên có | Để agent inspect DOM thực tế |
| **Danh sách modules tham gia** | ⚠️ Nên có | Nếu không có → agent tự khám phá |
| **Danh sách dimensions (chiều kết hợp)** | ⚠️ Nên có | VD: loại đối tác, loại thuế... Nếu không có → agent tự extract |
| **Business rules / công thức** | ❌ | Nếu có → agent map vào ma trận; nếu không → agent phát hiện qua UI |
| **Credentials** | ❌ | Nếu app cần đăng nhập |
| **Chiến lược ma trận** | ❌ | `pairwise` (mặc định), `business-critical`, hoặc `full-cartesian` |

---

## Các bước thực hiện

### Bước 1: Multi-Module Recon — Khám phá từng Module (MANDATORY BROWSER)

> ⚡ Agent PHẢI dùng MCP browser để inspect DOM thực tế, KHÔNG ĐOÁN.

1. **Nhận danh sách modules** từ user hoặc tự khám phá qua navigation
2. **Với mỗi module** trong chuỗi, thực hiện:
   ```
   browser_navigate → URL module
   browser_resize → 1920 × 1080
   browser_wait_for → page load
   browser_snapshot → thu thập DOM
   ```
3. **Thu thập cho mỗi module:**

   | Thông tin | Cách lấy | Ví dụ |
   |-----------|----------|-------|
   | **Tên module** | Tiêu đề trang / breadcrumb | "Quản lý Đối tác" |
   | **Fields / Controls** | Snapshot → phân tích input, select, radio... | Dropdown "Loại đối tác": [Tổ chức, Cá nhân, HKD] |
   | **Giá trị có thể chọn** | Mở dropdown → đọc options | `["Tổ chức", "Cá nhân", "Hộ kinh doanh"]` |
   | **Validation rules** | Kiểm tra required, format, min/max | "Mã số thuế: required, 10-13 số" |
   | **Output / Kết quả** | Data module này tạo ra | "ID đối tác, Tên, Loại, MST" |
   | **Trigger / Điều kiện sang module tiếp** | Button, link, auto-redirect | "Click 'Tạo thanh toán' → sang Module 2" |

4. **Ghi nhận vào bảng Module Inventory:**

   ```markdown
   | # | Module | URL / Path | Inputs | Key Dimensions | Output | → Next Module |
   |---|--------|-----------|--------|---------------|--------|---------------|
   | 1 | Quản lý Đối tác | /partners | Tên, MST, Loại | **Loại đối tác** (3 values) | Partner ID | → Thanh toán |
   | 2 | Tạo Thanh toán | /payments/new | Số tiền, Loại | **Loại TT** (2 values) | Payment ID | → Thuế |
   | 3 | Cấu hình Thuế | /tax-config | Loại thuế, % | **Loại thuế** (4 values) | Tax Config ID | → Công nợ |
   | ...| ... | ... | ... | ... | ... | ... |
   ```

---

### Bước 2: Data Flow Mapping — Vẽ bản đồ luồng dữ liệu (CHECKPOINT ⏸️)

1. **Xác định Data Flow** giữa các modules:
   - Module A **output** gì?
   - Output đó trở thành **input/điều kiện** module B như thế nào?
   - Có transform/mapping nào giữa chúng không?

2. **Vẽ Data Flow Diagram** dạng Mermaid:

   ```markdown
   ```mermaid
   graph LR
       M1["Module 1: Đối tác<br/>Output: Partner{type, id, tax_id}"]
       M2["Module 2: Thanh toán<br/>Input: Partner.type<br/>Output: Payment{type, currency, amount}"]
       M3["Module 3: Thuế<br/>Input: Partner.type + Payment.currency<br/>Output: TaxConfig{type, rate}"]
       M4["Module 4: Biên bản<br/>Input: ALL above<br/>Output: Template + Formula"]
       
       M1 -->|"Partner.type<br/>Partner.id"| M2
       M2 -->|"Payment.currency<br/>Payment.amount"| M3
       M1 -->|"Partner.type"| M3
       M1 & M2 & M3 -->|"Bộ kết hợp đầy đủ"| M4
   ```

3. **Xác định Dependencies Matrix:**

   ```markdown
   | Module đích | Phụ thuộc vào | Trường phụ thuộc | Loại phụ thuộc |
   |-------------|--------------|-----------------|----------------|
   | Thanh toán | Đối tác | Partner.type | Lọc options thanh toán |
   | Thuế | Đối tác + Thanh toán | Partner.type, Payment.currency | Quyết định loại thuế áp dụng |
   | Biên bản | Tất cả | ALL | Quyết định template + công thức |
   ```

4. **⏸️ DỪNG LẠI — Trình bày cho user:**
   - Data Flow Diagram
   - Dependencies Matrix
   - Hỏi: "Bản đồ này đã đúng chưa? Có dependency nào thiếu?"
   - **Chờ user xác nhận** trước khi sang Bước 3

---

### Bước 3: Dimension Extraction — Liệt kê tất cả chiều kết hợp

1. **Extract "Dimensions"** — các biến số quyết định output:

   ```markdown
   | # | Dimension (Chiều) | Module nguồn | Giá trị có thể | Số values |
   |---|------------------|-------------|----------------|-----------|
   | D1 | Loại đối tác | Đối tác | Tổ chức, Cá nhân, Hộ KD | 3 |
   | D2 | Loại thanh toán | Thanh toán | VND, USD | 2 |
   | D3 | Loại thuế | Thuế | PIT, VAT, Nhà thầu, Miễn thuế | 4 |
   | D4 | Loại công nợ | Công nợ | Thường, Tạm ứng, Điều chỉnh | 3 |
   | D5 | Nguồn tài sản | Tài sản | Quỹ A, Quỹ B, Quỹ C | 3 |
   ```

2. **Tính tổng tổ hợp tiềm năng:**
   ```
   Full Cartesian: D1 × D2 × D3 × D4 × D5 = 3 × 2 × 4 × 3 × 3 = 216 bộ kết hợp
   ```

3. **Xác định constraints** (bộ kết hợp không hợp lệ):
   ```markdown
   | Constraint | Mô tả | Loại bỏ |
   |-----------|-------|---------|
   | C1 | Cá nhân + USD → không áp dụng Nhà thầu | 1 bộ |
   | C2 | Hộ KD + Miễn thuế → không tồn tại | 3 bộ |
   | ... | | |
   ```
   → Sau loại bỏ: **216 - 4 = 212 bộ hợp lệ**

---

### Bước 4: Sinh Ma Trận Kết Hợp (CORE OUTPUT ⭐)

Agent hỗ trợ **3 chiến lược** — user chọn hoặc agent đề xuất:

#### 4A. Pairwise Testing (Mặc định — KHUYẾN NGHỊ)

> Đảm bảo mọi **cặp 2 giá trị** từ 2 dimensions bất kỳ đều được test ít nhất 1 lần.
> Giảm 216 bộ → ~15-25 bộ mà vẫn cover 100% pairs.

**Thuật toán Pairwise:**
1. Liệt kê tất cả **pairs** cần cover: C(k,2) × average(|Di| × |Dj|) pairs
2. Chọn bộ kết hợp sao cho mỗi bộ cover nhiều pairs nhất (greedy algorithm)
3. Lặp cho đến khi tất cả pairs được cover
4. Loại bỏ bộ vi phạm constraints

**Tài liệu tham chiếu cho Pairwise:**
- Kỹ thuật IPOG (In-Parameter-Order-General) — mở rộng từng dimension
- Hoặc dùng công cụ: PICT (Microsoft), AllPairs

**Agent thực hiện:**
- Sinh bảng pairwise thủ công bằng greedy approach
- Hoặc nếu số dimensions ≤ 6 → agent tự tính

#### 4B. Business-Critical Only

> Chỉ chọn các bộ kết hợp **quan trọng nhất** theo business risk.

**Tiêu chí chọn:**
- Bộ kết hợp hay gặp nhất trong thực tế (theo user confirm)
- Bộ kết hợp liên quan đến tiền, thuế, quyết định tài chính → High Risk
- Bộ kết hợp biên (edge cases giữa các loại)

**Số lượng:** Thường 8-15 bộ.

#### 4C. Full Cartesian (Đầy đủ)

> Test TẤT CẢ bộ kết hợp hợp lệ. Dùng khi hệ thống critical (tài chính, y tế).

**Khi nào dùng:** Tổng bộ ≤ 50 hoặc user yêu cầu coverage 100%.

#### Output Bước 4 — Bảng Ma Trận:

```markdown
## Ma Trận Kết Hợp (Pairwise — 20 bộ)

| # | D1: Đối tác | D2: Thanh toán | D3: Thuế | D4: Công nợ | D5: Nguồn TS | → Expected Template | → Expected Formula | Risk |
|---|------------|---------------|---------|------------|-------------|--------------------|--------------------|------|
| 1 | Tổ chức | VND | VAT 10% | Thường | Quỹ A | BB_TC_VND_VAT | Amount × 1.10 | High |
| 2 | Tổ chức | USD | PIT 10% | Tạm ứng | Quỹ B | BB_TC_USD_PIT | (Amount × Rate) × 0.90 - Advance | High |
| 3 | Cá nhân | VND | PIT 10% | Thường | Quỹ A | BB_CN_VND_PIT | Amount × 0.90 | High |
| 4 | Cá nhân | USD | VAT 10% | Điều chỉnh | Quỹ C | BB_CN_USD_VAT | (Amount × Rate) × 1.10 + Adj | Medium |
| 5 | Hộ KD | VND | Nhà thầu 2% | Thường | Quỹ B | BB_HKD_VND_NT | Amount × 0.98 | Medium |
| ... | ... | ... | ... | ... | ... | ... | ... | ... |
```

> **Cột "Expected Template" và "Expected Formula":**
> - Nếu user cung cấp business rules → agent map từ rules
> - Nếu không → agent ghi `[Cần user xác nhận]` và hỏi user

---

### Bước 5: Expected Output Mapping & Delivery (CHECKPOINT ⏸️)

1. **Với mỗi bộ kết hợp trong ma trận**, xác định:

   | Thuộc tính | Mô tả | Nguồn |
   |-----------|-------|-------|
   | **Expected Template** | Biên bản dùng template nào | User / business rules doc |
   | **Expected Formula** | Công thức tính toán | User / business rules doc |
   | **Expected Fields** | Biên bản hiển thị fields nào | DOM inspection |
   | **Expected Behavior** | Hành vi đặc biệt (approval flow, notification...) | User input |
   | **Test Priority** | P1-P4 (dựa trên risk + tần suất sử dụng) | Agent đánh giá |

2. **Sinh artifact output:**

   **File 1: `cross_module_test_plan_<feature>.md`**
   - Module Inventory (Bước 1)
   - Data Flow Diagram (Bước 2)
   - Dimension Catalog (Bước 3)
   - **Ma Trận Kết Hợp** (Bước 4) — ĐÂY LÀ OUTPUT CHÍNH
   - Expected Output Mapping (Bước 5)
   - Constraints & Invalid Combinations

   **File 2: `combinatorial_matrix_<feature>.md`** (hoặc `.json` / `.csv`)
   - Chỉ bảng ma trận — dễ import vào Excel/Jira

3. **⏸️ DỪNG LẠI — Trình bày cho user:**
   - Ma trận kết hợp hoàn chỉnh
   - Hỏi: "Có bộ kết hợp nào thiếu? Expected template/formula đã đúng chưa?"
   - **Chờ user xác nhận** trước khi chuyển sang automation

---

## Bước tiếp theo sau workflow này

Sau khi user xác nhận ma trận, có thể chuyển tiếp:

| Mục tiêu | Workflow tiếp theo |
|----------|-------------------|
| Sinh **test cases chi tiết** cho từng bộ kết hợp | `/generate_manual_testcases_rbt` — input = ma trận |
| Sinh **test data** bằng pipeline chạy thật qua modules | `/generate_combinatorial_test_data` — input = ma trận |
| Sinh **automation scripts** cho từng bộ | `/generate_automation_from_testcases` — input = test cases |

---

## NGHIÊM CẤM

| ❌ Không được làm | ✅ Thay thế đúng |
|-------------------|-----------------|
| Đoán giá trị dimensions không inspect DOM | Inspect thật qua MCP browser |
| Tự quyết expected template/formula không hỏi user | Hỏi user xác nhận business rules |
| Sinh Full Cartesian mặc định khi dimensions lớn | Dùng Pairwise — giảm 80-90% bộ kết hợp |
| Bỏ qua constraints (bộ kết hợp không hợp lệ) | Phải xác định và loại bỏ invalid combinations |
| Gộp nhiều bước, không có checkpoint | PHẢI dừng ở Bước 2 và Bước 5 chờ user |

---

## Checklist cuối

- [ ] Đã inspect DOM thực tế cho TỪNG module trong chuỗi
- [ ] Đã vẽ Data Flow Diagram + Dependencies Matrix
- [ ] User đã xác nhận Data Flow ở Bước 2
- [ ] Đã extract đầy đủ dimensions + values
- [ ] Đã xác định constraints (invalid combinations)
- [ ] Đã chọn chiến lược ma trận phù hợp (pairwise / business-critical / full)
- [ ] Ma trận có cột Expected Template + Formula
- [ ] User đã xác nhận ma trận cuối ở Bước 5
- [ ] Artifact output đã lưu vào đúng vị trí project
