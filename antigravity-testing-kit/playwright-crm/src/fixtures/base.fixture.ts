import { test as base } from '@playwright/test';
import { loginAsAdmin } from './auth.fixture';
import { CustomerListPage } from '../pages/customers/customer-list.page';
import { CustomerFormPage } from '../pages/customers/customer-form.page';
import { CustomerDetailPage } from '../pages/customers/customer-detail.page';
import { CustomerImportPage } from '../pages/customers/customer-import.page';

type Fixtures = {
  customerListPage: CustomerListPage;
  customerFormPage: CustomerFormPage;
  customerDetailPage: CustomerDetailPage;
  customerImportPage: CustomerImportPage;
};

export const test = base.extend<Fixtures>({
  customerListPage: async ({ page }, use) => {
    await loginAsAdmin(page);
    await use(new CustomerListPage(page));
  },
  customerFormPage: async ({ page }, use) => {
    await loginAsAdmin(page);
    await use(new CustomerFormPage(page));
  },
  customerDetailPage: async ({ page }, use) => {
    await loginAsAdmin(page);
    await use(new CustomerDetailPage(page));
  },
  customerImportPage: async ({ page }, use) => {
    await loginAsAdmin(page);
    await use(new CustomerImportPage(page));
  },
});

export { expect } from '@playwright/test';
