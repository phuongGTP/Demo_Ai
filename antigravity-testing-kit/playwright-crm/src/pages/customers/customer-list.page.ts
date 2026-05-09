import { Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';

export class CustomerListPage extends BasePage {
  readonly newCustomerBtn: Locator = this.page.getByRole('link', { name: /new customer/i });
  readonly importBtn: Locator = this.page.getByRole('link', { name: /import customers/i });
  readonly bulkActionsBtn: Locator = this.page.getByRole('button', { name: /bulk actions/i }).first();
  readonly exportBtn: Locator = this.page.getByRole('button', { name: /export/i });
  // DataTable filter search (NOT the global header search #search_input)
  readonly searchInput: Locator = this.page.locator('.dataTables_filter input, #clients_filter input').first();
  readonly paginationSelect: Locator = this.page.locator('select[name="clients_length"]');

  async goto(): Promise<void> {
    await this.navigate('/admin/clients');
    await this.waitForPageLoad();
  }

  async clickNewCustomer(): Promise<void> {
    await this.newCustomerBtn.click();
    await expect(this.page).toHaveURL(/\/admin\/clients\/client$/);
  }

  async search(keyword: string): Promise<void> {
    await this.searchInput.fill(keyword);
    await this.page.waitForLoadState('networkidle');
  }

  async getActiveToggle(rowIndex: number): Promise<Locator> {
    // Returns the hidden checkbox for state reading; use clickActiveToggle() to click
    return this.page.locator(`table tbody tr:nth-child(${rowIndex}) input[name="onoffswitch"]`);
  }

  async clickActiveToggle(rowIndex: number): Promise<void> {
    // The checkbox is hidden; click its associated label to toggle
    const input = this.page.locator(`table tbody tr:nth-child(${rowIndex}) input[name="onoffswitch"]`);
    const inputId = await input.getAttribute('id');
    if (inputId) {
      await this.page.locator(`label[for="${inputId}"]`).click();
    } else {
      await input.click({ force: true });
    }
  }

  async selectCheckbox(company: string): Promise<void> {
    const row = this.page.locator('table tbody tr').filter({ hasText: company });
    await row.locator('input[type="checkbox"]').first().check();
  }

  async isCompanyVisible(company: string): Promise<boolean> {
    return this.page.getByRole('link', { name: company }).isVisible();
  }

  async setPaginationSize(size: 25 | 50 | 100): Promise<void> {
    await this.paginationSelect.selectOption(String(size));
  }
}
