import { Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';

export class CustomerFormPage extends BasePage {
  readonly companyInput: Locator = this.page.locator('#company');
  readonly vatInput: Locator = this.page.locator('#vat');
  readonly phoneInput: Locator = this.page.locator('#phonenumber');
  readonly websiteInput: Locator = this.page.locator('#website');
  readonly addressInput: Locator = this.page.locator('#address');
  readonly cityInput: Locator = this.page.locator('#city');
  readonly stateInput: Locator = this.page.locator('#state');
  readonly zipInput: Locator = this.page.locator('#zip');

  // Two distinct save buttons — use specific classes to avoid ambiguity
  readonly saveBtn: Locator = this.page.locator('button.only-save.customer-form-submiter');
  readonly saveAndContactBtn: Locator = this.page.locator('button.save-and-add-contact.customer-form-submiter');

  // Form tabs (anchors within the form)
  readonly tabCustomerDetails: Locator = this.page.locator('a[href="#contact_info"]');
  readonly tabBillingShipping: Locator = this.page.locator('a[href="#billing_and_shipping"]');

  // Billing & Shipping
  readonly billingStreet: Locator = this.page.locator('#billing_street');
  readonly billingCity: Locator = this.page.locator('#billing_city');
  readonly shippingStreet: Locator = this.page.locator('#shipping_street');
  readonly shippingCity: Locator = this.page.locator('#shipping_city');
  readonly sameAsCustomerInfoBtn: Locator = this.page.locator('a.billing-same-as-customer');
  readonly copyBillingBtn: Locator = this.page.locator('a.customer-copy-billing-address');

  // Perfex CRM renders validation errors as .help-block inside the parent .form-group
  readonly companyError: Locator = this.page.locator('.form-group:has(#company) .help-block, #company ~ small, #company ~ .text-danger');

  async goto(): Promise<void> {
    await this.navigate('/admin/clients/client');
    await this.waitForPageLoad();
  }

  async fillCompany(name: string): Promise<void> {
    await this.companyInput.fill(name);
  }

  async save(): Promise<void> {
    await this.saveBtn.click();
  }

  async saveAndCreateContact(): Promise<void> {
    await this.saveAndContactBtn.click();
  }

  async getCompanyErrorText(): Promise<string> {
    await expect(this.companyError).toBeVisible({ timeout: 5000 });
    return this.companyError.innerText();
  }
}
