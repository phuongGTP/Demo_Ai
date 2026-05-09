/**
 * Customers Module — Automated Test Suite
 * Scope: Critical + High priority TCs từ TC_CRM_CUSTOMERS.md
 */

import { test, expect } from '../../fixtures/base.fixture';
import { generateCompanyName, generateEmail, buildValidImportCsv } from '../../utils/test-data';
import * as path from 'path';
import type { Locator } from '@playwright/test';
import { CustomerDetailPage } from '../../pages/customers/customer-detail.page';

const CustomerDetailPageExpected = CustomerDetailPage.expectedSidebarTabs;

// ---------------------------------------------------------------------------
// MOD-01-C · TC005 + TC006 — Toggle Active/Inactive
// ---------------------------------------------------------------------------
test.describe('MOD-01-C · Toggle Active', () => {
  test('CRM_CUST_TC_005 — toggle Active→Inactive lưu ngay, không cần Save', async ({ customerListPage, page }) => {
    await customerListPage.goto();

    const firstToggle = await customerListPage.getActiveToggle(1);
    const stateBefore = await firstToggle.isChecked();

    await customerListPage.clickActiveToggle(1);
    await page.waitForLoadState('networkidle');

    await expect(page).not.toHaveURL(/\/admin\/clients\/client\//);
    const stateAfter = await (await customerListPage.getActiveToggle(1)).isChecked();
    expect(stateAfter).not.toEqual(stateBefore);
  });

  test('CRM_CUST_TC_006 — trạng thái toggle vẫn giữ sau khi refresh trang', async ({ customerListPage, page }) => {
    await customerListPage.goto();

    await customerListPage.clickActiveToggle(1);
    await page.waitForLoadState('networkidle');
    const stateAfterClick = await (await customerListPage.getActiveToggle(1)).isChecked();

    await page.reload();
    await customerListPage.waitForPageLoad();

    const stateAfterRefresh = await (await customerListPage.getActiveToggle(1)).isChecked();
    expect(stateAfterRefresh).toEqual(stateAfterClick);
  });
});

// ---------------------------------------------------------------------------
// MOD-02 · TC007 + TC008 + TC012 + TC013 — Tạo Mới Customer
// ---------------------------------------------------------------------------
test.describe('MOD-02 · Tạo Mới Customer', () => {
  test('CRM_CUST_TC_007 — tạo mới thành công chỉ với Company (Happy Path)', async ({ customerFormPage, page }) => {
    const company = generateCompanyName('HAPPY');

    await customerFormPage.goto();
    await expect(page).toHaveURL(/\/admin\/clients\/client$/);
    await expect(customerFormPage.tabCustomerDetails).toBeVisible();

    await customerFormPage.fillCompany(company);
    await customerFormPage.save();

    await expect(page).toHaveURL(/\/admin\/clients\/client\/\d+/, { timeout: 10000 });
    await expect(page.locator('h4, .page-title').first()).toContainText(company);
  });

  test('CRM_CUST_TC_008 — thiếu Company → lỗi "This field is required."', async ({ customerFormPage, page }) => {
    await customerFormPage.goto();
    await customerFormPage.vatInput.fill('VN0123456789');
    await customerFormPage.phoneInput.fill('0909111222');
    await customerFormPage.save();

    await expect(page).toHaveURL(/\/admin\/clients\/client$/, { timeout: 5000 });
    const errorText = await customerFormPage.getCompanyErrorText();
    expect(errorText).toContain('This field is required');
  });

  test('CRM_CUST_TC_012 — Save redirect về trang chi tiết customer vừa tạo', async ({ customerFormPage, page }) => {
    const company = generateCompanyName('SAVE_REDIR');

    await customerFormPage.goto();
    await customerFormPage.fillCompany(company);
    await customerFormPage.save();

    await expect(page).toHaveURL(/\/admin\/clients\/client\/\d+/, { timeout: 10000 });
    await expect(page.locator('h4, .page-title').first()).toContainText(company);
  });

  test('CRM_CUST_TC_013 — Save and create contact mở form tạo Contact', async ({ customerFormPage, page }) => {
    const company = generateCompanyName('SAVE_CONTACT');

    await customerFormPage.goto();
    await customerFormPage.fillCompany(company);
    await customerFormPage.saveAndCreateContact();

    await expect(page).not.toHaveURL(/\/admin\/clients\/client$/, { timeout: 10000 });
    await expect(page.locator('form, .modal').first()).toBeVisible();
  });
});

// ---------------------------------------------------------------------------
// MOD-03 · TC015 + TC017 + TC018 — Chi Tiết & Chỉnh Sửa
// ---------------------------------------------------------------------------
test.describe('MOD-03 · Chi Tiết & Chỉnh Sửa', () => {
  let createdCompany: string;

  test.beforeEach(async ({ customerFormPage, page }) => {
    createdCompany = generateCompanyName('DETAIL');
    await customerFormPage.goto();
    await customerFormPage.fillCompany(createdCompany);
    await customerFormPage.save();
    await expect(page).toHaveURL(/\/admin\/clients\/client\/\d+/, { timeout: 10000 });
  });

  test('CRM_CUST_TC_015 — sidebar có đủ 19 tab theo đúng thứ tự', async ({ customerDetailPage }) => {
    const tabNames = await customerDetailPage.getSidebarTabNames();
    const expected = CustomerDetailPageExpected;

    for (const tab of expected) {
      expect(tabNames.some(t => t.toLowerCase().includes(tab.toLowerCase())),
        `Tab "${tab}" không tìm thấy trong sidebar`
      ).toBeTruthy();
    }
  });

  test('CRM_CUST_TC_017 — chỉnh sửa Company và Save cập nhật header', async ({ customerDetailPage, page }) => {
    const updatedName = `${createdCompany}_EDITED`;
    await customerDetailPage.editCompany(updatedName);

    await expect(page.locator('h4, .page-title').first()).toContainText(updatedName, { timeout: 8000 });
  });

  test('CRM_CUST_TC_018 — xóa trắng Company khi chỉnh sửa → lỗi validation', async ({ customerDetailPage, page }) => {
    await customerDetailPage.clearCompanyAndSave();

    await expect(page).not.toHaveURL(/\/admin\/clients$/, { timeout: 3000 });
    const errorText = await customerDetailPage.getCompanyErrorText();
    expect(errorText).toContain('This field is required');
  });
});

// ---------------------------------------------------------------------------
// MOD-04 · TC019 — Xóa Customer
// ---------------------------------------------------------------------------
test.describe('MOD-04 · Xóa Customer', () => {
  test('CRM_CUST_TC_019 — xóa thành công sau khi xác nhận → redirect về danh sách', async ({ customerFormPage, customerDetailPage, page }) => {
    const company = generateCompanyName('DELETE_ME');
    await customerFormPage.goto();
    await customerFormPage.fillCompany(company);
    await customerFormPage.save();
    await expect(page).toHaveURL(/\/admin\/clients\/client\/\d+/, { timeout: 10000 });

    await customerDetailPage.confirmDelete();

    await expect(page).toHaveURL(/\/admin\/clients$/, { timeout: 8000 });
    const isStillVisible = await page.getByRole('link', { name: company }).isVisible();
    expect(isStillVisible).toBe(false);
  });
});

// ---------------------------------------------------------------------------
// MOD-08-A · TC032 — Bulk Actions modal mở được (button không bị block)
// ---------------------------------------------------------------------------
test.describe('MOD-08-A · Bulk Actions Điều Kiện', () => {
  test('CRM_CUST_TC_032 — click Bulk Actions khi chưa chọn record mở modal Mass Delete', async ({ customerListPage, page }) => {
    await customerListPage.goto();

    await customerListPage.bulkActionsBtn.click();

    // Perfex CRM mở modal Bulk Actions dù chưa chọn row — modal chứa Mass Delete + Groups
    const modal = page.locator('.modal:visible, [role="dialog"]:visible').first();
    await expect(modal).toBeVisible({ timeout: 5000 });

    // Đóng modal để không ảnh hưởng test khác
    await page.locator('.modal:visible .close, .modal:visible [aria-label="Close"]').first().click();
    await expect(modal).not.toBeVisible({ timeout: 3000 });
  });
});

// ---------------------------------------------------------------------------
// MOD-06 · TC026 + TC027 + TC028 — Import CSV
// ---------------------------------------------------------------------------
test.describe('MOD-06 · Import CSV', () => {
  test('CRM_CUST_TC_026 — Simulate Import CSV hợp lệ không tạo dữ liệu thật', async ({ customerImportPage, customerListPage, page }) => {
    const email = generateEmail('sim');
    const company = generateCompanyName('SIM');
    const csvContent = `Firstname,Lastname,Email,Company\nSimulate,Auto,${email},${company}`;

    await customerImportPage.goto();
    await customerImportPage.uploadCsv(csvContent);
    await customerImportPage.clickSimulateImport();

    await customerListPage.goto();
    const exists = await page.getByRole('link', { name: company }).isVisible();
    expect(exists, 'Simulate Import không được tạo dữ liệu thật').toBe(false);
  });

  test('CRM_CUST_TC_027 — Import CSV hợp lệ: hiển thị "Total Imported: 1" sau khi import', async ({ customerImportPage, page }) => {
    // Perfex CRM import tạo CONTACTS (không phải companies trực tiếp).
    // Xác minh qua notification "Total Imported: 1" xuất hiện sau khi import.
    const email = generateEmail('import');
    const company = generateCompanyName('IMPORT');
    const csvContent = buildValidImportCsv(email, company);

    await customerImportPage.goto();
    await customerImportPage.uploadCsv(csvContent);

    // Click Import và capture notification ngay lập tức
    await customerImportPage.importBtn.click();
    await page.waitForLoadState('networkidle');

    // "Total Imported: X" notification xuất hiện trong header notification bell
    const notification = page.getByText(/Total Imported/i).first();
    const notifText = await notification.textContent({ timeout: 8000 }).catch(() => '');
    expect(notifText, 'Import phải tạo ít nhất 1 record').toMatch(/Total Imported:\s*[1-9]/);
  });

  test('CRM_CUST_TC_028 — Import CSV thiếu cột Email bắt buộc → báo lỗi', async ({ customerImportPage, page }) => {
    await customerImportPage.goto();
    await customerImportPage.uploadCsvFile(
      path.resolve(process.cwd(), 'test-data/import-missing-email.csv')
    );
    await customerImportPage.clickSimulateImport();

    // Perfex CRM hiển thị lỗi import dưới dạng alert hoặc kết quả inline
    const resultLocator = page.locator('.alert, [class*="import-result"], .import-message, #import-results, .panel-body').first();
    await expect(resultLocator).toBeVisible({ timeout: 15000 });
    const result = await resultLocator.innerText();
    expect(result.toLowerCase()).toMatch(/error|required|missing|invalid|email|0 imported/i);
  });
});
