import { Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';

// 19 customer profile sidebar tabs (from recon)
const SIDEBAR_TABS = [
  'Profile', 'Contacts', 'Notes', 'Statement', 'Invoices',
  'Payments', 'Proposals', 'Credit Notes', 'Estimates', 'Subscriptions',
  'Expenses', 'Contracts', 'Projects', 'Tasks', 'Tickets',
  'Files', 'Vault', 'Reminders', 'Map',
];

export class CustomerDetailPage extends BasePage {
  readonly companyInput: Locator = this.page.locator('#company');
  // Same two distinct save buttons as the form page
  readonly saveBtn: Locator = this.page.locator('button.only-save.customer-form-submiter');

  // Perfex CRM renders validation errors as .help-block inside the parent .form-group
  readonly companyError: Locator = this.page.locator('.form-group:has(#company) .help-block, #company ~ small, #company ~ .text-danger');

  // Delete: the detail page shows a delete confirmation modal triggered by a header dropdown
  readonly deleteLink: Locator = this.page.locator('a.text-danger._delete, a[href*="/admin/clients/delete/"]').first();
  readonly confirmDeleteBtn: Locator = this.page.getByRole('button', { name: /^(yes|confirm|delete|ok)$/i });

  async gotoById(id: number): Promise<void> {
    await this.navigate(`/admin/clients/client/${id}`);
    await this.waitForPageLoad();
  }

  async confirmDelete(): Promise<void> {
    // Delete link is visible in the action dropdown on the detail header
    // Try direct delete link first (Perfex shows it on hover in action menu)
    const headerDeleteLink = this.page.locator('[data-toggle="dropdown"] ~ ul a.text-danger, .dropdown-menu a.text-danger').first();
    const isDropdownDelete = await headerDeleteLink.isVisible().catch(() => false);

    if (isDropdownDelete) {
      await this.page.locator('[data-toggle="dropdown"]').first().click();
      await headerDeleteLink.click();
    } else {
      // Navigate to delete URL directly if on detail page
      const currentUrl = this.page.url();
      const match = currentUrl.match(/\/admin\/clients\/client\/(\d+)/);
      if (match) {
        this.page.on('dialog', async (dialog) => { await dialog.accept(); });
        await this.page.goto(`/admin/clients/delete/${match[1]}`);
        return;
      }
      await this.deleteLink.click();
    }

    // Handle confirmation modal if shown
    const modal = this.page.locator('.modal:visible, [role="dialog"]:visible').first();
    const hasModal = await modal.isVisible().catch(() => false);
    if (hasModal) {
      await this.confirmDeleteBtn.click();
    }
  }

  async editCompany(newName: string): Promise<void> {
    await this.companyInput.fill('');
    await this.companyInput.fill(newName);
    await this.saveBtn.click();
  }

  async clearCompanyAndSave(): Promise<void> {
    await this.companyInput.fill('');
    await this.saveBtn.click();
  }

  async getCompanyErrorText(): Promise<string> {
    await expect(this.companyError).toBeVisible({ timeout: 5000 });
    return this.companyError.innerText();
  }

  async getSidebarTabNames(): Promise<string[]> {
    // Customer profile sidebar links all contain the customer ID in their href
    // e.g. /admin/clients/client/9165?group=contacts
    const currentUrl = this.page.url();
    const match = currentUrl.match(/\/admin\/clients\/client\/(\d+)/);
    if (!match) return [];
    const customerId = match[1];
    const links = this.page.locator(`a[href*="/admin/clients/client/${customerId}"]`);
    const count = await links.count();
    const names: string[] = [];
    for (let i = 0; i < count; i++) {
      const text = (await links.nth(i).innerText()).trim();
      if (text && !names.includes(text)) names.push(text);
    }
    return names;
  }

  static get expectedSidebarTabs(): string[] {
    return SIDEBAR_TABS;
  }
}
